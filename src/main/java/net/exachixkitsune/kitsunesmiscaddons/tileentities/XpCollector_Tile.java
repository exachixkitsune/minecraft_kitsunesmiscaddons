package net.exachixkitsune.kitsunesmiscaddons.tileentities;


import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class XpCollector_Tile extends TileEntity implements ITickableTileEntity {

	public XpCollector_Tile() {
		super(TileEntityTypes.XPCOLLECTORTILE);
	}
	public XpCollector_Tile(TileEntityType<?> Type) {
		super(Type);
	}

	// NBT Keys
	private static String KEY_timerProcessing = "TimerProcessing";
	private static String KEY_XPCollected = "XPCollected";
	
	// Tick every 5 seconds
	protected final int TicksBetweenProcessing = 5 * 20;
	// Radius of 4 blocks - makes a 9x9 box around the tileentity
	protected final int CaptureRadius = 4;
	// Cost in XP to stop a rainstorm
	protected final int XPCostRainstormStop = 150;
	// Cost in XP to stop a thunderstorm
	protected final int XPCostThunderstormStop = 250;
	
	protected int TicksRemainingProcessing = -1;
	protected int XpCollected = 0;
	
	// NBT Communication functionality
	@Override
	public CompoundNBT save(CompoundNBT compoundNBTData) {
		compoundNBTData = super.save(compoundNBTData);
		compoundNBTData.putInt(KEY_timerProcessing, TicksRemainingProcessing);
		compoundNBTData.putInt(KEY_XPCollected, XpCollected);
		return compoundNBTData;
	}
	@Override
	public void load(BlockState state, CompoundNBT compoundNBTData) {
		super.load(state, compoundNBTData);
		TicksRemainingProcessing = compoundNBTData.getInt(KEY_timerProcessing);
		XpCollected = compoundNBTData.getInt(KEY_XPCollected);
	}
	
	// set by the block upon creation
	public void setup()
	{
		TicksRemainingProcessing = -1;
		XpCollected = 0;
	}
	
	// Obtain XpCollected and empty it
	public int emptyXpCollected() {
		int XpCollected_now = XpCollected;
		return XpCollected_now;
	}
	
	// Operating
	@Override
	public void tick() {
		if (!this.hasLevel()) return;  // prevent crash
		World world = this.getLevel();
		if (world.isClientSide()) return;   // don't bother doing anything on the client side.
		
		if (TicksRemainingProcessing < 0) {
			collectOrbs(world);
			
			// Reset Timer
			TicksRemainingProcessing = TicksBetweenProcessing;
		}
		else {
			// Decrement Timer
			--TicksRemainingProcessing;
		}
	}

	protected void collectOrbs(World thisWorld) {
		// Check area around me
		BlockPos myPos = this.getBlockPos();
		AxisAlignedBB area = new AxisAlignedBB(
				myPos.offset(-CaptureRadius,-CaptureRadius,-CaptureRadius),
				myPos.offset( CaptureRadius, CaptureRadius, CaptureRadius));
		List<ExperienceOrbEntity> list_exporbs = thisWorld.getEntitiesOfClass(ExperienceOrbEntity.class, area);
		
		for (ExperienceOrbEntity exporb : list_exporbs) {
			// Is orb is dead don't orb
			// Look botania does this so
			// (https://github.com/VazkiiMods/Botania/blob/cd95a884ec686cbc901c24b80fc10f9883c6fc63/src/main/java/vazkii/botania/common/block/subtile/generating/SubTileArcaneRose.java#L65)
			if (exporb.isAlive()) {
				// Collect value
				XpCollected += exporb.getValue();
				exporb.remove();
			}
		}
	}

}
