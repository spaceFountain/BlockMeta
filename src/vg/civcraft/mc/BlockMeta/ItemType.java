package vg.civcraft.mc.BlockMeta;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by isaac on 2/15/15.
 */
public class ItemType {
    private int type;
    private short damage;
    private String lore;


    public ItemType(Material type, short damage, String lore) {
        this.type = type.getId();
        this.damage = damage;
        this.lore = lore;
    }


    public ItemType(ItemStack item) {
        String lore = "";
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            StringBuilder loreBuilder = new StringBuilder();
            for (String loreLine : item.getItemMeta().getLore()) {
                loreBuilder = loreBuilder.append(loreLine).append('\n');
            }
            lore = loreBuilder.toString();
        }

        this.type = item.getType().getId();
        this.damage = item.getDurability();
        this.lore = lore;
    }
}
