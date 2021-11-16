package net.exachixkitsune.kitsunesmiscaddons.tileentities;

import net.minecraft.world.World;

public class XpCollectorEnhanced_Tile extends XpCollector_Tile {
	
	public XpCollectorEnhanced_Tile() {
		super(TileEntityTypes.XPCOLLECTORENHANCEDTILE);
	}
	
	@Override
	public void tick() {
		if (!this.hasLevel()) return;  // prevent crash
		World world = this.getLevel();
		if (world.isClientSide()) return;   // don't bother doing anything on the client side.

		if (TicksRemainingProcessing < 0) {
			collectOrbs(world);
			
			if ((XPCostThunderstormStop < XpCollected) && world.isThundering()) {
				world.getLevelData().setRaining(false);
				XpCollected -= XPCostThunderstormStop;
			}
			else if ((XPCostRainstormStop < XpCollected) && world.isRaining()) {
				world.getLevelData().setRaining(false);
				XpCollected -= XPCostRainstormStop;
			}
			
			// Reset Timer
			TicksRemainingProcessing = TicksBetweenProcessing;
		}
		else {
			// Decrement Timer
			--TicksRemainingProcessing;
		}
	}
}
