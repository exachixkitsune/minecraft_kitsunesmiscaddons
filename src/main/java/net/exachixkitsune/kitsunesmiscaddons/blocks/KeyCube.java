package net.exachixkitsune.kitsunesmiscaddons.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class KeyCube extends Block {
	
	private static final double distance_from_edge = 4;
	private static final double height = 8;
	
	private static final VoxelShape cube_shape = Block.box(
			distance_from_edge, 0.0, distance_from_edge,
			16.0-distance_from_edge, height, 16.0-distance_from_edge);
	

	public KeyCube(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return cube_shape;
	}

}
