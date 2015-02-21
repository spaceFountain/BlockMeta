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

    public BlockType(Material type, byte data, String meta) {
        this.meta = meta;
        this.type = type;
        this.data = data;
    }

    public BlockType(Block block) {
        this.meta = BlockMetaPlugin.db.getMetaForLocation(block.getLocation());
        BlockMetaPlugin.logger.info("meta was "+meta);
        this.type = block.getType();
        this.data = block.getData();
    }

    public BlockType(Material type) {
        this.meta = "";
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
        if (meta != null && meta != "") {
            BlockMetaPlugin.logger.info("added meta at "+block.getLocation());
            BlockMetaPlugin.db.insertBlock(block.getLocation(), meta);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlockType){
            BlockType other = (BlockType) obj;
            return other.getMeta().equals(getMeta()) && getType() == getType() && getData() == getData();
        }

        return super.equals(obj);
    }

    public String toString() {
        return String.format("type %s with data %d and meta \"%s\"", type.toString(), data, meta);
    }
}
