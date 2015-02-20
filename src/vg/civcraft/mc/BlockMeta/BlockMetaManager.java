package vg.civcraft.mc.BlockMeta;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;

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

    @EventHandler(priority=EventPriority.LOWEST)
    public void onBlockPlaced(BlockPlaceEvent event) {
        if (event.isCancelled())
            return;
        BlockType block = getBlockForItem(new ItemType(event.getItemInHand()));
        BlockMetaPlugin.logger.info("placed "+block +" because was holding  "+new ItemType(event.getItemInHand()) +
                " and registry was " + registry);


        if (block != null)
            block.changeBlockTo(event.getBlock());
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onBlockPlacedEventEnd(BlockPlaceEvent event) { //if the event gets canceled in the middle we need to clean up
        BlockType block = getBlockForItem(new ItemType(event.getItemInHand()));

        if (block != null && event.isCancelled())
            BlockMetaPlugin.db.deleteMetaForLocation(event.getBlock().getLocation());
    }



    @EventHandler(priority=EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event){
        BlockMetaPlugin.db.deleteMetaForLocation(event.getBlock().getLocation());
    }
    @EventHandler(priority=EventPriority.LOWEST)
    public void onPistonPushed(BlockPistonExtendEvent event){
        if (event.isCancelled())
            return;
        for (Block block: event.getBlocks()) {
            BlockType type = new BlockType((block));
            if (type.getMeta() != "" && type.getMeta() != null) {
                event.setCancelled(true); // cancelling is the easiest way to deal with it. It isn't fun and I'm sure someone won't like it but it works
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPistonPull(BlockPistonRetractEvent event){
        if (event.isCancelled())
            return;
        Block block = event.getRetractLocation().getBlock(); //when we update to 1.8 this will need changed to get a list of moving blocks
        BlockType type = new BlockType((block));
        if (type.getMeta() != "" && type.getMeta() != null) {
            event.setCancelled(true);
            return;
        }
    }
}
