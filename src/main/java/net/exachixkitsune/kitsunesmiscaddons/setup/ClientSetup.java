package net.exachixkitsune.kitsunesmiscaddons.setup;

import net.exachixkitsune.kitsunesmiscaddons.KitsunesMiscAddons;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

// This class holds all the object registration that is client-size
@Mod.EventBusSubscriber(modid = KitsunesMiscAddons.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
	
	public static void init(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(BlockRegister.XP_COLLECTOR.get(), RenderType.solid());
	}
}