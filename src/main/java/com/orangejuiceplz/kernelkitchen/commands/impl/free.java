package com.orangejuiceplz.kernelkitchen.commands.impl;

import com.orangejuiceplz.kernelkitchen.commands.Command;
import com.orangejuiceplz.kernelkitchen.commands.CommandResult;
import com.orangejuiceplz.kernelkitchen.logic.GameState;
import com.orangejuiceplz.kernelkitchen.struct.RAMSlot;

import java.util.List;

public class free implements Command {

    @Override
    public CommandResult execute(GameState state, List<String> args) {

        if (args.isEmpty()) {
            return new CommandResult(false, "");
        }

        String address = args.getFirst();
        RAMSlot slot = state.getSlot(address);

        if (slot == null) {
            return new CommandResult(false, "Segmentation Fault: Invalid address at " + address);
        }

        if (slot.isEmpty()) {
            return new CommandResult(false, "Address " + address + " is already empty or has already been freed"); // penalize via time because commands take time ofc
        }

        slot.clear();
        return new CommandResult(true, "Memory at " + address + " has been freed and is available for use");


    }

}
