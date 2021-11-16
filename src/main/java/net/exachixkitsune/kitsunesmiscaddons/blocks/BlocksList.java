package net.exachixkitsune.kitsunesmiscaddons.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
//import net.minecraftforge.common.ToolType;

public class BlocksList {
	private BlocksList() {}

	public static final AbstractBlock.Properties common_properties(MaterialColor Colour) {
		return Block.Properties.of(Material.METAL, Colour)
				.strength(1.0F);
				//.requiresCorrectToolForDrops()
				//.harvestTool(ToolType.PICKAXE)
				//.harvestLevel(0); // Null+
	}
	
	public static final Block xp_collector = new XpCollector(common_properties( MaterialColor.METAL).lightLevel((val_in) -> { return 10; }));
	public static final Block xp_collector_enhanced = new XpCollectorEnhanced(common_properties( MaterialColor.METAL).lightLevel((val_in) -> { return 10; }));
}
