package vg.civcraft.mc.BlockMeta;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.Serializable;

/**
 * Created by isaac on 2/15/15.
 */
public class BlockType implements Serializable{
    private String meta;
    private Material type;
    private byte data;

    public BlockType(String meta, Material type, byte data) {
        this.meta = meta;
        this.type = type;
        this.data = data;
    }

    public BlockType(Block block) {
        this.meta = BlockMetaPlugin.db.getMetaForLocation(block.getLocation());
        this.type = block.getType();
        this.data = block.getData();
    }

    public BlockType(Material type) {
        this.meta = null;
        this.type = type;
        this.data = 0;
    }


    public String getMeta() {
        return meta;
    }

    public Material getType() {
        return type;
    }

    public byte getData() {
        return data;
    }

    public void changeBlockTo(Block block) {
        block.setType(type);
        block.setData(data);

        BlockMetaPlugin.db.deleteMetaForLocation(block.getLocation());
        if (meta != null && meta != "")
            BlockMetaPlugin.db.insertBlock(block.getLocation(), meta);
    }

    public String toString() {
        return String.format("type %s with data %d and meta \"%s\"", type.toString(), data, meta);
    }
}
