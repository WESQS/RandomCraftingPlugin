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

import java.util.*;

public class RandomCraftingPlugin extends JavaPlugin {

    private final Random random = new Random();
    private final List<Material> materials = Arrays.asList(
            Material.STONE, Material.DIRT, Material.COBBLESTONE, Material.SAND,
            Material.GRAVEL, Material.OAK_LOG, Material.BIRCH_LOG, Material.GLASS,
            Material.REDSTONE, Material.COAL, Material.IRON_INGOT, Material.GOLD_INGOT,
            Material.DIAMOND, Material.EMERALD, Material.BRICK, Material.TNT,
            Material.WHEAT, Material.BOOK, Material.ENDER_PEARL, Material.LAPIS_LAZULI,
            Material.CARROT, Material.POTATO, Material.BEEF, Material.COOKED_BEEF,
            Material.STRING, Material.WHITE_WOOL, Material.PUMPKIN
    );

    @Override
    public void onEnable() {
        getLogger().info("RandomCraftingPlugin gestartet");
        regenerateRecipes();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, this::regenerateRecipes, 12000L, 12000L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("randomcraft")) {
            regenerateRecipes();
            sender.sendMessage("§aNeue Rezepte generiert!");
            return true;
        }
        return false;
    }

    private void regenerateRecipes() {
        Iterator<Recipe> it = Bukkit.recipeIterator();
        while (it.hasNext()) {
            Recipe recipe = it.next();
            if (recipe != null && recipe.getKey() != null) {
                Bukkit.removeRecipe(recipe.getKey());
            }
        }

        for (int i = 0; i < 20; i++) {
            createRandomRecipe();
        }
    }

    private void createRandomRecipe() {
        Material output = materials.get(random.nextInt(materials.size()));
        String[] pattern = {"AB", "CD"};

        NamespacedKey key = new NamespacedKey(this, "rand_" + System.nanoTime());
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(output, 1));

        recipe.shape(pattern);
        recipe.setIngredient('A', materials.get(random.nextInt(materials.size())));
        recipe.setIngredient('B', materials.get(random.nextInt(materials.size())));
        recipe.setIngredient('C', materials.get(random.nextInt(materials.size())));
        recipe.setIngredient('D', materials.get(random.nextInt(materials.size())));

        Bukkit.addRecipe(recipe);
    }
}
