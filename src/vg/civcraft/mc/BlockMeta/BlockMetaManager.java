package vg.civcraft.mc.BlockMeta;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by isaac on 2/18/15.
 */
public class BlockMetaManager implements Listener{
    Map<ItemType, BlockType> registry = new HashMap<ItemType, BlockType>();

    public void registerItemToBlock(ItemType item, BlockType block) {
        BlockMetaPlugin.logger.info("registered "+item +" to "+block);
        registry.put(item, block);
    }

    public BlockType getBlockForItem(ItemType item) {
        return registry.get(item);
    }

    @EventHandler(priority= EventPriority.LOWEST)
    public void onBlockPlaced(BlockPlaceEvent event) {
        BlockType block = getBlockForItem(new ItemType(event.getItemInHand()));
        BlockMetaPlugin.logger.info("placed "+block +" because was holding  "+new ItemType(event.getItemInHand()) +
                " and registry was " + registry);


        if (block != null)
            block.changeBlockTo(event.getBlock());
    }
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event){
        BlockMetaPlugin.db.deleteMetaForLocation(event.getBlock().getLocation());
    }
}
