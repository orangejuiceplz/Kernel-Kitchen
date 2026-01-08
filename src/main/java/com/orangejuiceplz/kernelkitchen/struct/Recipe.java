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

import java.util.List;

public class Recipe {

    private final String name;
    private final List<Ingredient> ingredientList; // AKA dependencies


    /**
     *
     * A static recipie definition to be added
     *
     * @param name
     * Name of the recipie
     * @param ingredientList
     * Strict list of required ingredients, including the ingredient's state
     */
    public Recipe(String name, List<Ingredient> ingredientList) {
        this.name = name;
        this.ingredientList = List.copyOf(ingredientList);
    }

    public String getName() {
        return this.name;
    }

    public List<Ingredient> getIngredientList() {
        return this.ingredientList;
    }

    @Override
    public String toString() {
        return "Recipe{" + "name='" + this.name + '\'' + ", dependencies=" + this.ingredientList + '}';
    }
}
