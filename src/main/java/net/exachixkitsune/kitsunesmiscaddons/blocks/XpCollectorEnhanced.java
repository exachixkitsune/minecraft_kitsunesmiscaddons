package net.exachixkitsune.kitsunesmiscaddons.blocks;

import net.exachixkitsune.kitsunesmiscaddons.tileentities.XpCollectorEnhanced_Tile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class XpCollectorEnhanced extends XpCollector {
	
	public XpCollectorEnhanced(Properties in_properties) {
		super(in_properties);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new XpCollectorEnhanced_Tile();
	}
}
