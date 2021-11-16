package net.exachixkitsune.kitsunesmiscaddons.world;

import com.mojang.serialization.Codec;

import net.exachixkitsune.kitsunesmiscaddons.KitsunesMiscAddons;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

import net.minecraft.world.gen.Heightmap;

// No small amount of this is found from https://github.com/TelepathicGrunt/StructureTutorialMod/blob/1.16.3-Forge-jigsaw/src/main/java/com/telepathicgrunt/structuretutorial/structures/RunDownHouseStructure.java

public class CropGarden extends Structure<NoFeatureConfig> {

	// Only call super
	public CropGarden(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return CropGarden.Start::new;
	}
	
	@Override
    public GenerationStage.Decoration step() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenIn, BiomeProvider biomeIn, long seedIn,
			SharedSeedRandom sharedSeedIn, int chunkXIn, int chunkZIn, Biome BiomeIn, ChunkPos chunkPosIn,
			NoFeatureConfig NoFeatConfigIn) {
		// In summary; is the middle block for this not a fluid.
		
		BlockPos centerOfChunk = new BlockPos(chunkXIn * 16, 0, chunkZIn * 16);
		
		int landHeight = chunkGenIn.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
		
		IBlockReader columnOfBlocks = chunkGenIn.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
		BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
		
		return topBlock.getFluidState().isEmpty();
	}

	public static class Start extends StructureStart<NoFeatureConfig>  {

		public Start(Structure<NoFeatureConfig> structureIn, int ChunkX, int ChunkZ,
				MutableBoundingBox mutableBoundingBoxIn, int referenceIn, long seedIn) {
			super(structureIn, ChunkX, ChunkZ, mutableBoundingBoxIn, referenceIn, seedIn);
		}

		@Override
		public void generatePieces(DynamicRegistries dynRegIn, ChunkGenerator chunkGenIn,
				TemplateManager templateManagerIn, int chunkXIn, int chunkZIn, Biome biomeIn,
				NoFeatureConfig configIn) {
			int blockX = chunkXIn * 16;
			int blockZ = chunkZIn * 16;
			BlockPos centerPos = new BlockPos(blockX, 0, blockZ);
			
			JigsawManager.addPieces(
					dynRegIn,
                    new VillageConfig(() -> dynRegIn.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                    		.get(new ResourceLocation(KitsunesMiscAddons.MODID, "cropgarden_pool")),
                    		1),
                    AbstractVillagePiece::new,
                    chunkGenIn,
                    templateManagerIn,
                    centerPos,
                    this.pieces,
                    this.random,
                    false,
                    true);
			
			Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();
            for(StructurePiece structurePiece : this.pieces){
                structurePiece.move(xOffset, 0, zOffset);
            }

            // Sets the bounds of the structure once you are finished.
            this.calculateBoundingBox();
		}
	
	}
}
