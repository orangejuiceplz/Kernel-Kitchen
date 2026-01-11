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
import com.orangejuiceplz.kernelkitchen.struct.*;

import java.util.ArrayList;
import java.util.List;

public class compile implements Command {

    @Override
    public CommandResult execute(GameState state, List<String> args) {

        if (args.isEmpty()) {
            return new CommandResult(false, "Usage: compile [PID]");
        }

        int pid;
        try {
            pid = Integer.parseInt(args.getFirst());
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Error: PID must be an integer");
        }

        Order order = state.getOrder(pid);
        if (order == null) {
            return new CommandResult(false, "OrderNotFoundException with PID: "
                    + pid + ". Order does not exist");
        }

        Recipe recipe = RecipeBook.getRecipe(order.getDishName());
        if (recipe == null) {
            return new CommandResult(false, "Recipe corrupted or missing for " + order.getDishName());
        }

        List<RAMSlot> loadedRAM = new ArrayList<>();

        for (Ingredient required : recipe.getIngredientList()) {
            RAMSlot foundSlot = state.findIngredientInRAM(required, loadedRAM);

            if (foundSlot == null) {
                state.penalize(10);
                return new CommandResult(false, "Compilation Error: Missing Dependency [" + required.toString() + "]" );
            }
        }

        for (RAMSlot slot : loadedRAM) {
            slot.clear();
        }

        state.completeOrder(order);
        return new CommandResult(true, "SUCCESS: Compiled order " + order.getDishName() + " for PID " + pid);

    }

}
