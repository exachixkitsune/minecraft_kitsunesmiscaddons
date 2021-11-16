package net.exachixkitsune.kitsunesmiscaddons.setup;

import net.exachixkitsune.kitsunesmiscaddons.KitsunesMiscAddons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class StructureConfiguredRegister {
	public static StructureFeature<?, ?> CONFIGURED_GARDEN     = StructureRegister.GARDEN.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_LAMPPOST   = StructureRegister.LAMPPOST.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_RESTSTOP   = StructureRegister.RESTSTOP.get().configured(IFeatureConfig.NONE);
	public static StructureFeature<?, ?> CONFIGURED_CROPGARDEN = StructureRegister.CROPGARDEN.get().configured(IFeatureConfig.NONE);
	
	public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        
        
        Registry.register(registry, new ResourceLocation(KitsunesMiscAddons.MODID, "configured_garden"), CONFIGURED_GARDEN);
        Registry.register(registry, new ResourceLocation(KitsunesMiscAddons.MODID, "configured_lamppost"), CONFIGURED_LAMPPOST);
        Registry.register(registry, new ResourceLocation(KitsunesMiscAddons.MODID, "configured_reststop"), CONFIGURED_RESTSTOP);
        Registry.register(registry, new ResourceLocation(KitsunesMiscAddons.MODID, "configured_cropgarden"), CONFIGURED_CROPGARDEN);
        
        
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegister.GARDEN.get(), CONFIGURED_GARDEN);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegister.LAMPPOST.get(), CONFIGURED_LAMPPOST);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegister.RESTSTOP.get(), CONFIGURED_RESTSTOP);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegister.CROPGARDEN.get(), CONFIGURED_CROPGARDEN);
    }
}
