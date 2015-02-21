package vg.civcraft.mc.BlockMeta;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

/**
 * Created by isaac on 2/15/15.
 */
public class ItemType implements Serializable {
    private Material type;
    private short damage;
    private String lore;


    public ItemType(Material type, short damage, String lore) {
        this.type = type;
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

        this.type = item.getType();
        this.damage = item.getDurability();
        this.lore = lore;
    }

    public String toString() {
        return String.format("type %s with data %d  and lore \"%s\"", type.toString(), damage, lore);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemType){
            BlockMetaPlugin.logger.info("checking if they equal");
            ItemType other = (ItemType) obj;
            return other.lore.equals(lore) && other.type == type && other.damage == damage;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
