package net.exachixkitsune.kitsunesmiscaddons.blocks;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Receptacle extends Block {

	private static final double receptacle_height = 10;
	private static final double inset_fromedge = 4;
	private static final double inset_depth = 2;
	
	private static final double cube_height = 8;
	
	private static final VoxelShape main_box = Block.box(
			0.0, 0.0, 0.0,
			16.0, receptacle_height, 16.0);
	private static final VoxelShape inset_box = Block.box(
			inset_fromedge,			receptacle_height, 				inset_fromedge,
			16.0-inset_fromedge, 	receptacle_height-inset_depth, 	16.0-inset_fromedge);
	private static final VoxelShape cube_box = Block.box(
			inset_fromedge, 		receptacle_height-inset_depth, 				inset_fromedge,
			16.0-inset_fromedge,	cube_height+receptacle_height-inset_depth, 	16.0-inset_fromedge);
	
	// Combine, using only main_box.
	private static final VoxelShape combined_box_empty = VoxelShapes.join(main_box, inset_box, IBooleanFunction.ONLY_FIRST);
	private static final VoxelShape combined_box_full  = VoxelShapes.join(combined_box_empty, cube_box, IBooleanFunction.OR);
	
	public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
	
	private final Block keyCube;
	private final Supplier<Item> keyCubeItemSupplier;
	
	public Receptacle(Properties properties,
			Block keyCubeIn,
			Supplier<Item> keyCubeItemSupplierIn) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(OCCUPIED, false));
		this.keyCube = keyCubeIn;
		this.keyCubeItemSupplier = keyCubeItemSupplierIn;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(OCCUPIED);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.getValue(OCCUPIED)) {
			return combined_box_full;
		}
		else {
			return combined_box_empty;
		}
	}
	
	//
	
	@Override
	public boolean isSignalSource(BlockState p_149744_1_) {
		// Presumed canProvidePower
		return true;
	}

	@Override
	public int getSignal(BlockState stateIn, IBlockReader blockReaderIn, BlockPos posIn,
			Direction directionIn) {
		// Presumed weak redstone
		// Output full level when signal is full
		if (stateIn.getValue(OCCUPIED)) {
			return 16;
		}
		else {
			return 0;
		}
	}

	@Override
	public int getDirectSignal(BlockState stateIn, IBlockReader blockReaderIn, BlockPos posIn,
			Direction directionIn) {
		// presuming direct signal is strong redstone
		return 0;
	}
	
	// Use command - cube
	// If a player uses a cube; they should lose the cube and thus 
	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState stateIn, World world, BlockPos myPos,
			PlayerEntity player, Hand userhand, BlockRayTraceResult raytrace) {
		if (stateIn.getValue(OCCUPIED)) {
			// Pop out the cube
			ItemEntity spawnedblock = new ItemEntity(world,
					myPos.getX(), myPos.getY(), myPos.getZ(),
					this.keyCubeItemSupplier.get().getDefaultInstance());
			world.addFreshEntity(spawnedblock);
			// Update block state
			world.setBlockAndUpdate(myPos, stateIn.setValue(OCCUPIED, false));
			
			return ActionResultType.SUCCESS;
		}
		
		return super.use(stateIn, world, myPos, player, userhand, raytrace);
	}
	
	public boolean canAcceptKeyCube(BlockState stateIn, KeyCube_Item ItemIn) {
		if (!stateIn.getValue(OCCUPIED)) {
			// Get the block from the ItemIn
			Block KeyCube_Block = ItemIn.getBlock();
			if (KeyCube_Block == this.keyCube) {
				return true;
			}
		}
		return false;
	}

}
