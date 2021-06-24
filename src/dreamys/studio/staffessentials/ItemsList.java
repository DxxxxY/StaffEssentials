package dreamys.studio.staffessentials;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class ItemsList {
    public static ItemStack banWand() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bWand of Banning");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Left-Click on someone to ban them!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack vanishPotion() {
        ItemStack item = new ItemStack(Material.POTION, 1, (short) PotionType.INVISIBILITY.ordinal());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bPotion of Vanishing");
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Right-Click to vanish/reveal yourself!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack clearInv() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bClear Inventory");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Right-Click to clear your inventory!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
