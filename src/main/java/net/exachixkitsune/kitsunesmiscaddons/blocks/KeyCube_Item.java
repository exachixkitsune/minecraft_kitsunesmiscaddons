package net.exachixkitsune.kitsunesmiscaddons.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KeyCube_Item extends BlockItem {
	
	public KeyCube_Item(Block blockIn, Properties propertiesIn) {
		super(blockIn, propertiesIn);
	}

	@Override
	public ActionResultType useOn(ItemUseContext useContextIn) {

		World world = useContextIn.getLevel();
		BlockPos pos = useContextIn.getClickedPos();

		// Is the target block the correct block?
		BlockState targetBlockState = world.getBlockState(pos);
		Block targetBlock = targetBlockState.getBlock();
		if (targetBlock instanceof Receptacle) {
			Receptacle targetReceptacle = (Receptacle)targetBlock;
			if (targetReceptacle.canAcceptKeyCube(targetBlockState, this)) {
				// Only do the modifications serverside
				
				if (!world.isClientSide()) {
					// Do the thing
					world.setBlockAndUpdate(pos, targetBlockState.setValue(Receptacle.OCCUPIED, true));
					// Reduce stack
					useContextIn.getItemInHand().shrink(1);
				}

				// Stop processing
				return ActionResultType.SUCCESS;
			}
		}

		return super.useOn(useContextIn);
	}
	
	

}
