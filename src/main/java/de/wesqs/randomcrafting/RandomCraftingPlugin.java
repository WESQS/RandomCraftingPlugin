package de.wesqs.randomcrafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class RandomCraftingPlugin extends JavaPlugin {

    private final Random random = new Random();
    private final List<Material> randomMaterials = Arrays.asList(
            Material.STONE,
            Material.DIRT,
            Material.COBBLESTONE,
            Material.SAND,
            Material.GRAVEL,
            Material.OAK_LOG,
            Material.BIRCH_LOG,
            Material.GLASS,
            Material.REDSTONE,
            Material.COAL,
            Material.IRON_INGOT,
            Material.GOLD_INGOT,
            Material.DIAMOND,
            Material.EMERALD,
            Material.BRICK,
            Material.EXPERIENCE_BOTTLE,
            Material.PUMPKIN,
            Material.MELON,
            Material.CARROT,
            Material.POTATO,
            Material.BEEF,
            Material.COOKED_BEEF,
            Material.STRING,
            Material.WHITE_WOOL,
            Material.TNT,
            Material.LAPIS_LAZULI,
            Material.WHEAT,
            Material.BOOK,
            Material.ENDER_PEARL,
            Material.PRISMARINE_SHARD
    );

    @Override
    public void onEnable() {
        randomizeRecipes();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, this::randomizeRecipes, 20L * 60L * 10L, 20L * 60L * 10L);
        getLogger().info("RandomCraftingPlugin aktiviert. Alle Crafting-Rezepte werden jetzt global zufällig neu generiert.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("randomcraft")) {
            return false;
        }

        randomizeRecipes();
        sender.sendMessage("§aNeue zufällige Crafting-Rezepte wurden global generiert.");
        return true;
    }

    private void randomizeRecipes() {
        removeAllRecipes();

        int recipeCount = 0;
        for (int i = 0; i < 35; i++) {
            if (registerRandomRecipe()) {
                recipeCount++;
            }
        }

        getLogger().info("RandomCraftingPlugin: " + recipeCount + " zufällige Crafting-Rezepte registriert.");
    }

    private void removeAllRecipes() {
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if (recipe == null || recipe.getKey() == null) {
                continue;
            }
            Bukkit.removeRecipe(recipe.getKey());
        }
    }

    private boolean registerRandomRecipe() {
        Material outputMaterial = getRandomMaterial();
        if (outputMaterial == null) {
            return false;
        }

        String[] pattern = buildRandomPattern();
        Map<Character, Material> ingredients = new HashMap<>();

        int ingredientIndex = 0;
        for (int row = 0; row < pattern.length; row++) {
            char[] chars = pattern[row].toCharArray();
            for (char c : chars) {
                if (c == ' ') {
                    continue;
                }
                if (!ingredients.containsKey(c)) {
                    char usedChar = (char) ('A' + ingredientIndex);
                    if (usedChar == c) {
                        ingredients.put(c, getRandomMaterial());
                    } else {
                        ingredients.put(c, getRandomMaterial());
                    }
                    ingredientIndex++;
                }
            }
        }

        if (ingredients.isEmpty()) {
            pattern = new String[] {"A"};
            ingredients.put('A', getRandomMaterial());
        }

        NamespacedKey key = new NamespacedKey(this, "random_recipe_" + System.currentTimeMillis() + "_" + random.nextInt(1_000_000));
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(outputMaterial));
        recipe.shape(pattern);

        for (Map.Entry<Character, Material> entry : ingredients.entrySet()) {
            recipe.setIngredient(entry.getKey(), entry.getValue());
        }

        return Bukkit.addRecipe(recipe);
    }

    private Material getRandomMaterial() {
        if (randomMaterials.isEmpty()) {
            return null;
        }
        return randomMaterials.get(random.nextInt(randomMaterials.size()));
    }

    private String[] buildRandomPattern() {
        int width = random.nextInt(3) + 1;
        int height = random.nextInt(3) + 1;

        List<Character> symbols = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'));
        String[] pattern = new String[height];

        for (int row = 0; row < height; row++) {
            StringBuilder rowBuilder = new StringBuilder();
            for (int col = 0; col < width; col++) {
                if (random.nextBoolean()) {
                    char symbol = symbols.get(random.nextInt(symbols.size()));
                    rowBuilder.append(symbol);
                } else {
                    rowBuilder.append(' ');
                }
            }
            pattern[row] = rowBuilder.toString();
        }

        if (pattern[0].trim().isEmpty()) {
            pattern = new String[] {"A"};
        }

        return pattern;
    }
}
