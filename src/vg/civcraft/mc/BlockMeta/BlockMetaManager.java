package vg.civcraft.mc.BlockMeta;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by isaac on 2/18/15.
 */
public class BlockMetaManager implements Listener{
    Map<ItemStack, BlockType> registry = new HashMap<ItemStack, BlockType>();

    public void registerItemToBlock(ItemStack item, BlockType block) {
        registry.put(item, block);
    }

    public BlockType getBlockForItem(ItemStack item) {
        return registry.get(item);
    }

    @EventHandler(priority= EventPriority.LOWEST)
    public void onBlockPlaced(BlockPlaceEvent event) {
        getBlockForItem(event.getItemInHand()).changeBlockTo(event.getBlock());
    }
}
