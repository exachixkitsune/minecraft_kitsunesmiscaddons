package net.exachixkitsune.kitsunesmiscaddons.setup;

import net.exachixkitsune.kitsunesmiscaddons.KitsunesMiscAddons;
import net.exachixkitsune.kitsunesmiscaddons.blocks.BlocksList;
import net.exachixkitsune.kitsunesmiscaddons.blocks.KeyCube_Item;
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
	
	// XP Collectors
	public static final RegistryObject<Block> XP_COLLECTOR = BLOCKS.register("xp_collector",
			() -> BlocksList.xp_collector);
	public static final RegistryObject<Item> XP_COLLECTOR_ITEM = ITEMS.register("xp_collector",
			() -> new BlockItem(XP_COLLECTOR.get(), baseProperties));
	public static final RegistryObject<Block> XP_COLLECTOR_ENHANCED = BLOCKS.register("xp_collector_enhanced",
			() -> BlocksList.xp_collector_enhanced);
	public static final RegistryObject<Item> XP_COLLECTOR_ENHANCED_ITEM = ITEMS.register("xp_collector_enhanced",
			() -> new BlockItem(XP_COLLECTOR_ENHANCED.get(), baseProperties));
	
	// Key Cubes
	public static final RegistryObject<Block> KEYCUBE_1 = BLOCKS.register("keycube_1",
			() -> BlocksList.keycube_1);
	public static final RegistryObject<Item> KEYCUBE_1_ITEM = ITEMS.register("keycube_1",
			() -> new KeyCube_Item(KEYCUBE_1.get(), baseProperties));
	public static final RegistryObject<Block> KEYCUBE_2 = BLOCKS.register("keycube_2",
			() -> BlocksList.keycube_2);
	public static final RegistryObject<Item> KEYCUBE_2_ITEM = ITEMS.register("keycube_2",
			() -> new KeyCube_Item(KEYCUBE_2.get(), baseProperties));
	
	// Receptacles
	public static final RegistryObject<Block> RECEPTACLE_1 = BLOCKS.register("receptacle_1",
			() -> BlocksList.receptacle_1);
	public static final RegistryObject<Item> RECEPTACLE_1_ITEM = ITEMS.register("receptacle_1",
			() -> new BlockItem(RECEPTACLE_1.get(), baseProperties));
	public static final RegistryObject<Block> RECEPTACLE_2 = BLOCKS.register("receptacle_2",
			() -> BlocksList.receptacle_2);
	public static final RegistryObject<Item> RECEPTACLE_2_ITEM = ITEMS.register("receptacle_2",
			() -> new BlockItem(RECEPTACLE_2.get(), baseProperties));
	
	public static void registerBlocks() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}