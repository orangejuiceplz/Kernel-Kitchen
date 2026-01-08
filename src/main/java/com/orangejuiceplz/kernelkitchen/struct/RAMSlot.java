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

package com.orangejuiceplz.kernelkitchen.struct;
/**
 * Represents one of the RAM slots the user has.
 * Handles the state of ingredients and corruption
 * I don't know what else to put here.
 */
public class RAMSlot {

    private Ingredient content;
    private boolean isCorrupted;
    private long timeLoaded; // measured in ms from when the ingredient was added
    private final String hexAddress; // this NEVER changes

    public RAMSlot(String hexAddress) {
        this.content = null;
        this.isCorrupted = false;
        this.timeLoaded = 0;
        this.hexAddress = hexAddress;
    }

    public void load(Ingredient content) {
        this.content = content;
        this.isCorrupted = false;
        this.timeLoaded = System.currentTimeMillis();
    }

    public void markCorrupted() {
        if (!isEmpty()) {
            this.isCorrupted = true;
        }
    }

    public void clear() {
        this.content = null;
        this.isCorrupted = false;
        this.timeLoaded = 0;
    }

    public boolean isEmpty() {
        return this.content == null; // this means nothing is loaded!
    }

    public Ingredient getContent() {
        return this.content;
    }

    public boolean isCorrupted() {
        return this.isCorrupted;
    }

    public long getTimeLoaded() {
        return timeLoaded;
    }

    public String getHexAddress() {
        return hexAddress;
    }



}
