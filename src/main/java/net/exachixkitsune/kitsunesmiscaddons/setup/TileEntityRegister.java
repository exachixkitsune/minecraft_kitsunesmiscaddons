package net.exachixkitsune.kitsunesmiscaddons.setup;

import net.exachixkitsune.kitsunesmiscaddons.KitsunesMiscAddons;
import net.exachixkitsune.kitsunesmiscaddons.tileentities.TileEntityTypes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegister {

	private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, KitsunesMiscAddons.MODID);

	public static final RegistryObject<TileEntityType<?>> XPCOLLECTOR = TILE_ENTITIES.register("xp_collector",
			() -> TileEntityTypes.XPCOLLECTORTILE);
	
	public static void registerTileEntities() {
		TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}