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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A static database of all available dishes
 */
public class RecipeBook {

    private static final Map<String, Recipe> MENU = new HashMap<>();

    private static final List<String>  MENU_KEYS  = new ArrayList<>();

    static {
        registerRecipes();
    }

    private static void registerRecipes() {

        // burger
        // Requirements:
        // 1 Bun (RAW), 1 Patty (COOKED)

        List<Ingredient> burgerIngredients = List.of(
                new Ingredient("Bun", IngredientState.RAW),
                new Ingredient("Patty", IngredientState.COOKED)
        );
        addRecipe(new Recipe("Burger", burgerIngredients));

        // salad
        // Requirements:
        // 1 lettuce (RAW), 1 tomato (RAW)
        List<Ingredient> saladIngredients = List.of(
                new Ingredient("Lettuce", IngredientState.RAW),
                new Ingredient("Tomato", IngredientState.RAW)
        );
        addRecipe(new Recipe("Salad", saladIngredients));

        // BLT
        // Requirements
        // 1 bread (RAW), 1 Lettuce (RAW), 1 Tomato (RAW), 1 Bacon (COOKED)
        List<Ingredient> BLTIngredients = List.of(
                new Ingredient("Bread", IngredientState.RAW),
                new Ingredient("Lettuce", IngredientState.RAW),
                new Ingredient("Tomato", IngredientState.RAW),
                new Ingredient("Bacon", IngredientState.COOKED)
        );
        addRecipe(new Recipe("BLT", BLTIngredients));



    }

    /**
     *
     * adds recipe to map and the key to the list
     *
     * @param recipe
     * recipe to add
     */
    private static void addRecipe(Recipe recipe) {
        MENU.put(recipe.getName().toLowerCase(), recipe);
        MENU_KEYS.add(recipe.getName());
    }

    /**
     *
     * Looks up a recipe by name.
     * Usage: compile("burger") -> verify the recipe and compile with ingredients in RAM
     *
     * @param name
     * name to look up
     *
     * @return
     * recipe for that name
     */
    public static Recipe getRecipe(String name) {
        return MENU.get(name.toLowerCase());
    }

    /**
     * Get a random dish name
     * Usage: To be used in spawning a new order
     *
     * @return
     * random dish name
     */
    public static String getRandomDishName() {
        if (MENU_KEYS.isEmpty()) return "null_dish";
        int randomIndex = (int) (Math.random() * MENU_KEYS.size());
        return MENU_KEYS.get(randomIndex);
    }



}
