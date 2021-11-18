package net.exachixkitsune.kitsunesmiscaddons;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.Codec;

import net.exachixkitsune.kitsunesmiscaddons.setup.BlockRegister;
import net.exachixkitsune.kitsunesmiscaddons.setup.ClientSetup;
import net.exachixkitsune.kitsunesmiscaddons.setup.StructureConfiguredRegister;
import net.exachixkitsune.kitsunesmiscaddons.setup.StructureRegister;
import net.exachixkitsune.kitsunesmiscaddons.setup.TileEntityRegister;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

//Primary Mod Class
@Mod(KitsunesMiscAddons.MODID)
public class KitsunesMiscAddons {
	public static final String MODID = "kitsunesmiscaddons";
    public static final String MODNAME = "Kitsunes Misc Addons";
    public static final String VERSION = "1.16.5-1.2";
    
    // Constructor
 	public KitsunesMiscAddons() {
		// Registry
		BlockRegister.registerBlocks();
		TileEntityRegister.registerTileEntities();

		// Client Setup
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

		// Relating to structures:
		// For registration and init stuff.
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		StructureRegister.STRUCTURE.register(modEventBus);
		modEventBus.addListener(this::setup);

		// For events that happen after initialization. This is probably going to be use a lot.
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

		// The comments for BiomeLoadingEvent and StructureSpawnListGatherEvent says to do HIGH for additions.
		forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
 	}

 	public void setup(final FMLCommonSetupEvent event)
 	{
 		event.enqueueWork(() -> {
 			StructureRegister.setupStructures();
 			StructureConfiguredRegister.registerConfiguredStructures();
 		});
 	}
 	
 	public void biomeModification(final BiomeLoadingEvent event) {
 		if ((event.getCategory() == Category.NONE) ||
 				(event.getCategory() == Category.THEEND) ||
 				(event.getCategory() == Category.NETHER) ||
 				(event.getCategory() == Category.OCEAN)) {
 			// Don't add to this biome at all
 			return;
 		}
 		// Only add the garden if the biome is wet
 		if (event.getClimate().downfall > 0.35) {
 			event.getGeneration().getStructures().add(() -> StructureConfiguredRegister.CONFIGURED_GARDEN  );
 	 		event.getGeneration().getStructures().add(() -> StructureConfiguredRegister.CONFIGURED_CROPGARDEN );
 		}
 		
 		if (event.getCategory() == Category.PLAINS) {
 			event.getGeneration().getStructures().add(() -> StructureConfiguredRegister.CONFIGURED_DUNGEON );
 		}
 		event.getGeneration().getStructures().add(() -> StructureConfiguredRegister.CONFIGURED_LAMPPOST );
 		event.getGeneration().getStructures().add(() -> StructureConfiguredRegister.CONFIGURED_RESTSTOP );
		event.getGeneration().getStructures().add(() -> StructureConfiguredRegister.CONFIGURED_TOWER );
 	}
 	
 	private static Method GETCODEC_METHOD;
    @SuppressWarnings("resource")
	public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();
            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                @SuppressWarnings("unchecked")
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                //StructureTutorialMain.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }
            
            // Skips placement during superflat
            if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)){
                return;
            }
            
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(StructureRegister.GARDEN.get(),     DimensionStructuresSettings.DEFAULTS.get(StructureRegister.GARDEN.get()));
            tempMap.putIfAbsent(StructureRegister.LAMPPOST.get(),   DimensionStructuresSettings.DEFAULTS.get(StructureRegister.LAMPPOST.get()));
            tempMap.putIfAbsent(StructureRegister.RESTSTOP.get(),   DimensionStructuresSettings.DEFAULTS.get(StructureRegister.RESTSTOP.get()));
            tempMap.putIfAbsent(StructureRegister.CROPGARDEN.get(), DimensionStructuresSettings.DEFAULTS.get(StructureRegister.CROPGARDEN.get()));
            tempMap.putIfAbsent(StructureRegister.DUNGEON.get(),    DimensionStructuresSettings.DEFAULTS.get(StructureRegister.DUNGEON.get()));
            tempMap.putIfAbsent(StructureRegister.TOWER.get(),      DimensionStructuresSettings.DEFAULTS.get(StructureRegister.TOWER.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }
}