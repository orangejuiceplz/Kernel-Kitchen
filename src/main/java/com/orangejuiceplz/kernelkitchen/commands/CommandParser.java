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

package com.orangejuiceplz.kernelkitchen.commands;

import com.orangejuiceplz.kernelkitchen.commands.impl.compile;
import com.orangejuiceplz.kernelkitchen.commands.impl.fetch;
import com.orangejuiceplz.kernelkitchen.commands.impl.make;
import com.orangejuiceplz.kernelkitchen.logic.GameState;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

public class CommandParser {

    private final Map<String, Command> commands = new HashMap<>();

    public CommandParser() {

        // TODO:
        // put commands here

        commands.put("fetch", new fetch());
        commands.put("make", new make());
        commands.put("compile", new compile());
        commands.put("gc", new gc());

    }

    public CommandResult parseAndExecute(GameState state, String input) {
        if (input == null || input.isBlank()) return new CommandResult(false, "");

        String[] parts = input.trim().split("\\s+"); // split up the String's words into separate strings in an array.
        String cmdName = parts[0].toLowerCase(); // if i'm feeling devious, we could enforce case matching

        List<String> args = Arrays.asList(parts).subList(1, parts.length);

        Command command = commands.get(cmdName);

        if (command == null) return new CommandResult(false, "Unknown command: " + cmdName);

        return command.execute(state, args);


    }


}
