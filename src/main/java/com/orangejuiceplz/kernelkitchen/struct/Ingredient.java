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

import java.util.Objects;

/**
 * Represents a single ingredient that is to be stored in a RAM slot
 */
public class Ingredient {

    private final String name;
    private IngredientState state;

    public Ingredient(String name, IngredientState state) {
        this.name = name;
        this.state = state;
    }

    public Ingredient(String name) {
        this.name = name;
        this.state = IngredientState.RAW;
    }

    public String getName() {
        return this.name;
    }

    public IngredientState getState() {
        return this.state;
    }

    public void setState(IngredientState state) {
        this.state = state;
    }

    @Override
    public boolean equals (Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) return false;
        Ingredient other = (Ingredient) object;
        return Objects.equals(this.name, other.name) && state == other.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, state);
    }

    @Override
    public String toString() {
        return this.name + " (" + this.state + ")";
    }



}
