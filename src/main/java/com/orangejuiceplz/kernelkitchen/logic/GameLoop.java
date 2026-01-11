// MIT License
//
// Copyright (c) 2026 orangejuiceplz
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.orangejuiceplz.kernelkitchen.logic;

import com.orangejuiceplz.kernelkitchen.commands.CommandParser;
import com.orangejuiceplz.kernelkitchen.commands.CommandResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


// import java.util.Scanner;

public class GameLoop {

    private final GameState gameState;
    private final CommandParser parser;
    private final BufferedReader reader;
    private boolean isRunning;


    public GameLoop() {
        this.gameState = new GameState();
        this.parser = new CommandParser();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.isRunning = true;
    }
    public void run() {
        System.out.println("Welcome, root!");

        long lastTime = System.currentTimeMillis();
        long timer = 0;

        while (isRunning) {
            long now = System.currentTimeMillis();
            long delta = now - lastTime;
            lastTime = now;

            gameState.tick(delta);

            if (gameState.getSystemIntegrity() <= 0) {
                gameOver();
                break;
            }

            // DEBUGGING
            // For now, print status 1 time per sec so console
            // isn't being flooded with messages

            try {
                if (reader.ready()) {
                    String input = reader.readLine();
                    processInput(input);
                    timer = 3000;
                }
            } catch (IOException e) {
                System.out.println("Error reading command line: " + e.getMessage());
            }

            timer += delta;

            if (timer >= 3000) {

                // this is just random difficulty scaling. I'll implement
                // this better later. I just want to quickly move things across
                // so i can focus on juicy stuff
                if (Math.random() < 0.05) {
                    gameState.spawnNewOrder();
                }

                cls();
                gameState.printStatus();
                System.out.print(" >");
                timer = 0;
            }

            // --- PLACEHOLDER
            // Since in the final product, we're going to use
            // Lanterna, I'm just defining something for a
            // debug loop. I can't block Svanner.nextLine()
            // s the next bext option is to sleep.

            try {
                Thread.sleep(50);
            } catch (InterruptedException exception) {
                System.out.println("We ran into an issue: ");
            }
        }
        System.out.println("Root, your system went down. Overall you socred a: " + gameState.getUptimeScore());
    }

    private void processInput(String input) {
        CommandResult result = parser.parseAndExecute(gameState, input);

        if (result.success()) {
            System.out.println(">> SUCCESS: " + result.message());
        } else {
            System.out.println(">> FAILURE: " + result.message());
        }

    }

    private void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void gameOver() {
        System.out.println("CRITICAL FAILURE: SYSTEM INTEGRITY 0%");
        gameState.printStatus();
        isRunning = false;
    }



    static void main() {
        new GameLoop().run();
    }
}
