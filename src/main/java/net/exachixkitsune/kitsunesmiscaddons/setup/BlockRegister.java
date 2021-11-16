package net.exachixkitsune.kitsunesmiscaddons.setup;

import net.exachixkitsune.kitsunesmiscaddons.KitsunesMiscAddons;
import net.exachixkitsune.kitsunesmiscaddons.blocks.BlocksList;
import net.exachixkitsune.kitsunesmiscaddons.extras.KitsunesMiscAddonsGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegister {
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, KitsunesMiscAddons.MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KitsunesMiscAddons.MODID);
	
	private static final int maxStackSize = 64;
	private static final Item.Properties baseProperties = new Item.Properties().stacksTo(maxStackSize).tab(KitsunesMiscAddonsGroup.GROUP);
	
	public static final RegistryObject<Block> XP_COLLECTOR = BLOCKS.register("xp_collector",
			() -> BlocksList.xp_collector);
	public static final RegistryObject<Item> XP_COLLECTOR_ITEM = ITEMS.register("xp_collector",
			() -> new BlockItem(XP_COLLECTOR.get(), baseProperties));
	public static final RegistryObject<Block> XP_COLLECTOR_ENHANCED = BLOCKS.register("xp_collector_enhanced",
			() -> BlocksList.xp_collector_enhanced);
	public static final RegistryObject<Item> XP_COLLECTOR_ENHANCED_ITEM = ITEMS.register("xp_collector_enhanced",
			() -> new BlockItem(XP_COLLECTOR_ENHANCED.get(), baseProperties));
	
	public static void registerBlocks() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}