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

import com.orangejuiceplz.kernelkitchen.struct.Order;
import com.orangejuiceplz.kernelkitchen.struct.RAMSlot;
import com.orangejuiceplz.kernelkitchen.struct.RecipeBook;

import java.util.LinkedList;
import java.util.Random;


public class GameState {

    private final RAMSlot[] ram;
    private final LinkedList<Order> orders;
    private int systemIntegrity; // 0 - 100
    private long uptimeScore; // seconds alive
    private long lockoutTimer = 0; // > 0 means player cannot type due to stack overflow or processes running
    private long accumulator;
    private String systemStatus = "IDLE"; // ui stuff

    private final Random rand = new Random();

    public GameState() {

        this.ram = new RAMSlot[4];
        for (int i = 0; i < this.ram.length; i++) {
            this.ram[i] = new RAMSlot("0x0" + (i + 1));
        }
        this.orders = new LinkedList<>();
        this.systemIntegrity = 100;
        this.uptimeScore = 0;

        for (int i = 0; i < 3; i++) {
            spawnNewOrder();
        }
    }

    public void spawnNewOrder() {
        String targetDish = RecipeBook.getRandomDishName();

        boolean shouldWeMakeThemCritical = rand.nextDouble() < 0.10;

        int pid;
        if (shouldWeMakeThemCritical) {
            pid = rand.nextInt(0, 100);
        } else {
            pid = rand.nextInt(100, 1000);
        }

        Order order = new Order(pid, targetDish, 15000, shouldWeMakeThemCritical);

        if (orders.size() == 5) {
            systemIntegrity = 0;
            systemStatus = "QUEUE OVERFLOW";
            return;
        } else {
            this.orders.add(order);
        }

        System.out.println("A new process spawned: " + targetDish); // DEBUGGING/LOGGING FOR NOW SINCE NOT USING LANTERNA FOR NOW

    }

    public void printStatus() {
        System.out.println("--- SYSTEM STATUS ---");
        System.out.println("Status: [" + systemStatus + "] | Integrity:  " + systemIntegrity + "% | Uptime " + uptimeScore );
        System.out.println("--- RAM DUMP ---");
        for (RAMSlot slot : this.ram) {
                System.out.println(slot);
        }
        System.out.println("--- PROC QUEUE ---");
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    public void tick(long millisPassed) {

        this.accumulator += millisPassed;

        if (this.accumulator >= 1000) {
            long secondsGained = this.accumulator / 1000;
            this.uptimeScore += secondsGained;
            this.accumulator %= 1000;
        }

        if (this.lockoutTimer > 0) {
            this.lockoutTimer -= millisPassed;
            if (this.lockoutTimer <= 0) {
                this.lockoutTimer = 0;
                this.systemStatus = "IDLE";
            }
        }



        var iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            order.tick(millisPassed);

            if (order.isExpired()) {
                iterator.remove();

                if (order.isCritical()) {
                    this.systemIntegrity = 0;
                    this.systemStatus = "PANIC";
                } else {
                    this.systemIntegrity -= 15;
                    if (this.systemIntegrity < 0) this.systemIntegrity = 0;
                }

            }
        }

        long currentTime = System.currentTimeMillis();
        for (RAMSlot slot : this.ram) {
            if (!slot.isEmpty() && !slot.isCorrupted()) {
                if (currentTime - slot.getTimeLoaded() > 20000) {
                    slot.markCorrupted();
                }
            }
        }

    }

    static void main() {
        GameState state = new GameState();
        state.printStatus();
        long simulatingSeconds = (int) (Math.random() * 31) + 1;
        System.out.println("\n... simulating " + simulatingSeconds +" seconds ...");
        state.tick(simulatingSeconds * 1000);
        state.printStatus();
    }


}
