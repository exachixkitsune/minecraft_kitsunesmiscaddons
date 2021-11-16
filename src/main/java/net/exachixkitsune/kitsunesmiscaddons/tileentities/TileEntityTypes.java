package net.exachixkitsune.kitsunesmiscaddons.tileentities;

import net.minecraft.tileentity.TileEntityType;

//Similar method to botania at https://github.com/Vazkii/Botania/
import static net.exachixkitsune.kitsunesmiscaddons.blocks.BlocksList.*;

public class TileEntityTypes {
	private TileEntityTypes() {}
	
	public static final TileEntityType<XpCollector_Tile> XPCOLLECTORTILE
		= TileEntityType.Builder.of(XpCollector_Tile::new, xp_collector).build(null);
	public static final TileEntityType<XpCollectorEnhanced_Tile> XPCOLLECTORENHANCEDTILE
		= TileEntityType.Builder.of(XpCollectorEnhanced_Tile::new, xp_collector_enhanced).build(null);
}
