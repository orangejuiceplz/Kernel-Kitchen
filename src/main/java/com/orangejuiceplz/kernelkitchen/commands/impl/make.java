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

package com.orangejuiceplz.kernelkitchen.commands.impl;

import com.orangejuiceplz.kernelkitchen.commands.Command;
import com.orangejuiceplz.kernelkitchen.commands.CommandResult;
import com.orangejuiceplz.kernelkitchen.logic.GameState;
import com.orangejuiceplz.kernelkitchen.struct.Ingredient;
import com.orangejuiceplz.kernelkitchen.struct.IngredientState;
import com.orangejuiceplz.kernelkitchen.struct.RAMSlot;

import java.util.List;

public class make implements Command {

    @Override
    public CommandResult execute(GameState state, List<String> args) {

        if (args.isEmpty()) {
            return new CommandResult(false, "Usage: make [address]");
        }

        String target = args.getFirst();
        RAMSlot slot = state.getSlot(target);

        if (slot == null) {
            return new CommandResult(false, "Segmentation Fault: Address " + target +
                    " does not exist or you do not have sufficient permissions to view this address.");
        }

        if (slot.isEmpty()) {
            return new CommandResult(false, "Null Pointer Exception: " + target + " does not exist.");
        }

        Ingredient food = slot.getContent();
        String oldState = food.getState().toString();

        if (food.getState() == IngredientState.RAW) {
            food.setState(IngredientState.COOKED);
            state.setLockout(2000);
            return new CommandResult(true, "Making " + food.getName() + "... [RAW -> COOKED"); // of course, I'll add an animation or something here later when I switch to Lanterna

        } else if (food.getState() == IngredientState.COOKED) {
            food.setState(IngredientState.BURNT);
            state.setLockout(2000);
            return new CommandResult(true, "WARNING: ADDRESS " +
                    target + " HAS BEEN OVERHEATED. " + food.getName() + " IS NOW BURNT");
        } else {
            state.setLockout(2000);
            return new CommandResult(false,  "Error: " + food.getName() + " is already BURNT. This is UNUSABLE");
        }

    }

}
