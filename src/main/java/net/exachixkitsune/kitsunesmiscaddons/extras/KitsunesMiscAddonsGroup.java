package net.exachixkitsune.kitsunesmiscaddons.extras;

import net.exachixkitsune.kitsunesmiscaddons.KitsunesMiscAddons;
import net.exachixkitsune.kitsunesmiscaddons.setup.BlockRegister;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

// Code here very inspired by https://github.com/Vazkii/Botania/

public class KitsunesMiscAddonsGroup extends ItemGroup {
	
	public static final KitsunesMiscAddonsGroup GROUP = new KitsunesMiscAddonsGroup();
	
	public KitsunesMiscAddonsGroup() {
		super(KitsunesMiscAddons.MODID);
		hideTitle();
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(BlockRegister.XP_COLLECTOR_ITEM.get());
	}
	
}