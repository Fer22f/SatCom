package fer22f.mods.satcom.block;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import fer22f.mods.satcom.SatCom;

public class NiobiumGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId == 0){
			for(int k = 0; k < 10; k++) {
				int firstBlockXCoord = (chunkX * 16) + random.nextInt(16);
				int firstBlockYCoord = random.nextInt(30);
				int firstBlockZCoord = (chunkZ * 16) + random.nextInt(16);
								
				(new WorldGenMinable(SatCom.BlockNiobiumOre.blockID, 5)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
			}
		}
	}

}
