package net.exachixkitsune.kitsunesmiscaddons.blocks;

import net.exachixkitsune.kitsunesmiscaddons.setup.BlockRegister;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
//import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.ToolType;

public class BlocksList {
	private BlocksList() {}
	
	public static final AbstractBlock.Properties common_properties_simple(MaterialColor Colour) {
		return Block.Properties.of(Material.METAL, Colour)
				.strength(1.0F);
	}
	public static final AbstractBlock.Properties common_properties(MaterialColor Colour) {
		return Block.Properties.of(Material.METAL, Colour)
				.strength(1.0F)
				.requiresCorrectToolForDrops()
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(0); // Null+
	}
	
	public static final Block xp_collector = new XpCollector(common_properties_simple( MaterialColor.METAL).lightLevel((val_in) -> { return 10; }));
	public static final Block xp_collector_enhanced = new XpCollectorEnhanced(common_properties_simple( MaterialColor.METAL).lightLevel((val_in) -> { return 10; }));
	
	public static final Block keycube_1 = new KeyCube(common_properties( MaterialColor.STONE ));
	public static final Block keycube_2 = new KeyCube(common_properties( MaterialColor.STONE ));
	
	public static final Block receptacle_1 = new Receptacle(common_properties( MaterialColor.STONE ), keycube_1, () -> (BlockRegister.KEYCUBE_1_ITEM.get()));
	public static final Block receptacle_2 = new Receptacle(common_properties( MaterialColor.STONE ), keycube_2, () -> (BlockRegister.KEYCUBE_2_ITEM.get()));
}
