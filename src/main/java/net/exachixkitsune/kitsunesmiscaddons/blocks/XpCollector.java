package net.exachixkitsune.kitsunesmiscaddons.blocks;

import net.exachixkitsune.kitsunesmiscaddons.tileentities.XpCollector_Tile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class XpCollector extends HorizontalFaceBlock {
	private static final double cnr = 2;	// Corner offset
	private static final double depth = 1;
	private static final double z_S = 0.0;
	private static final double z_N = 16.0;
	private static final double x_W = 16.0;
	private static final double x_E = 0.0;
	private static final double lowerD = 4;
	private static final double upperD = 16;
	
	private static final VoxelShape N_SHAPE = Block.box(
			x_W - cnr, lowerD, z_N,
			x_E + cnr, upperD, z_N - depth);
	private static final VoxelShape E_SHAPE = Block.box(
			x_E,         lowerD, z_S + cnr,
			x_E + depth, upperD, z_N - cnr);
	private static final VoxelShape S_SHAPE = Block.box(
			x_W - cnr, lowerD, z_S,
			x_E + cnr, upperD, z_S + depth);
	private static final VoxelShape W_SHAPE = Block.box(
			x_W,         lowerD, z_S + cnr,
			x_W - depth, upperD, z_N - cnr);
	private static final VoxelShape D_SHAPE = Block.box(
			x_W - cnr, 0.0,   z_S + cnr,
			x_E + cnr, depth, z_N - cnr);
	
	public XpCollector(Properties in_properties) {
		super(in_properties);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(FACING, Direction.NORTH)
				.setValue(FACE, AttachFace.WALL));
		
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, FACE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		// Generate the shape for this block
		AttachFace this_face = state.getValue(FACE);
		switch (this_face) {
		case WALL:
			Direction this_facing = state.getValue(FACING);
			switch (this_facing) {
			case NORTH:
				return N_SHAPE;
			case EAST:
				return E_SHAPE;
			case SOUTH:
				return S_SHAPE;
			case WEST:
				return W_SHAPE;
			default:
				return N_SHAPE;
			}
		case CEILING:
			Direction this_facing2 = state.getValue(FACING);
			switch (this_facing2) {
			case NORTH:
				return N_SHAPE;
			case EAST:
				return E_SHAPE;
			case SOUTH:
				return S_SHAPE;
			case WEST:
				return W_SHAPE;
			default:
				return N_SHAPE;
			}
		case FLOOR:
			return D_SHAPE;
		default:
			return N_SHAPE;
		}
	}

	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new XpCollector_Tile();
	}

	// Copied from https://github.com/TheGreyGhost/MinecraftByExample/ - lightly modified
	// Called just after the player places a block.  Start the tileEntity's timer
	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		TileEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof XpCollector_Tile) { // prevent a crash if not the right type, or is null
			XpCollector_Tile tileEntityData = (XpCollector_Tile)tileentity;
			tileEntityData.setup();
		}
	}

	// When this block is destroyed, make an orb.
	// I'm extrapolating from how https://github.com/TheGreyGhost/MinecraftByExample/blob/e9862e606f6306463fccde5e3ebe576ea88f0745/src/main/java/minecraftbyexample/mbe30_inventory_basic/BlockInventoryBasic.java#L90 works
	@SuppressWarnings("deprecation") // Suppressing warning on onRemove
	@Override
	public void onRemove(BlockState stateIn, World worldIn, BlockPos posIn, BlockState state2In,
			boolean boolIn) { 
		// Generate orb
		TileEntity thisTileEntity = worldIn.getBlockEntity(posIn);
		if (thisTileEntity instanceof XpCollector_Tile) {
			int thisXpCount = ((XpCollector_Tile) thisTileEntity).emptyXpCollected();
			if (thisXpCount > 0) {
				// Make the orb
				worldIn.addFreshEntity(new ExperienceOrbEntity(worldIn, posIn.getX(), posIn.getY(), posIn.getZ(), thisXpCount));
			}
		}
		
		// Then do super.
		super.onRemove(stateIn, worldIn, posIn, state2In, boolIn);
	}

}
