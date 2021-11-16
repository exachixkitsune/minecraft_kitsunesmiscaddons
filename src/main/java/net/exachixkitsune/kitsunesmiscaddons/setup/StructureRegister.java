package net.exachixkitsune.kitsunesmiscaddons.setup;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.exachixkitsune.kitsunesmiscaddons.KitsunesMiscAddons;
import net.exachixkitsune.kitsunesmiscaddons.world.*;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class StructureRegister {
	public static final DeferredRegister<Structure<?>> STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, KitsunesMiscAddons.MODID);

	public static final RegistryObject<Structure<NoFeatureConfig>> GARDEN     =
			STRUCTURE.register("garden",     () -> (new Garden(NoFeatureConfig.CODEC)));
	public static final RegistryObject<Structure<NoFeatureConfig>> LAMPPOST   =
			STRUCTURE.register("lamppost",   () -> (new LampPost(NoFeatureConfig.CODEC)));
	public static final RegistryObject<Structure<NoFeatureConfig>> RESTSTOP   =
			STRUCTURE.register("reststop",   () -> (new RestStop(NoFeatureConfig.CODEC)));
	public static final RegistryObject<Structure<NoFeatureConfig>> CROPGARDEN =
			STRUCTURE.register("cropgarden", () -> (new CropGarden(NoFeatureConfig.CODEC)));
	
	public static void setupStructures() {
        setupMapSpacingAndLand(
        		GARDEN.get(), /* The instance of the structure */
                new StructureSeparationSettings(15 /* average distance apart in chunks between spawn attempts */,
                        13 /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE*/,
                        1234567890 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);
        
        setupMapSpacingAndLand(
        		LAMPPOST.get(), /* The instance of the structure */
                new StructureSeparationSettings(15 /* average distance apart in chunks between spawn attempts */,
                        10 /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE*/,
                        1345678901 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);
        setupMapSpacingAndLand(
        		RESTSTOP.get(), /* The instance of the structure */
                new StructureSeparationSettings(40 /* average distance apart in chunks between spawn attempts */,
                        20 /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE*/,
                        1345678905 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);
        setupMapSpacingAndLand(
        		CROPGARDEN.get(), /* The instance of the structure */
                new StructureSeparationSettings(20 /* average distance apart in chunks between spawn attempts */,
                        18 /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE*/,
                        1843503642 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);
    }
	
	public static <F extends Structure<?>> void setupMapSpacingAndLand(
            F structure,
            StructureSeparationSettings structureSeparationSettings,
            boolean transformSurroundingLand)
    {
		Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
		
		if(transformSurroundingLand){
            Structure.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }
		
		DimensionStructuresSettings.DEFAULTS =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.DEFAULTS)
                        .put(structure, structureSeparationSettings)
                        .build();
		
		WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();

            if(structureMap instanceof ImmutableMap){
               Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
               tempMap.put(structure, structureSeparationSettings);
               settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, structureSeparationSettings);
            }
        });
    }
}
