package com.chiorichan.ZapApples;

import cpw.mods.fml.common.IWorldGenerator;
import java.io.PrintStream;
import java.util.Random;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldProvider;

public class TreeGenerator
  implements IWorldGenerator
{
  public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
  {
    if (world.provider.dimensionId == 0)
    {
      BiomeGenBase b = world.getBiomeGenForCoords(chunkX * 16, chunkZ * 16);
      if ((b != null) && (b.biomeName != null) && ((b.biomeName.equalsIgnoreCase("Forest")) || (b.biomeName.equalsIgnoreCase("Taiga")) || (b.biomeName.equalsIgnoreCase("Sky")) || (b.biomeName.equalsIgnoreCase("ForestHills")) || (b.biomeName.equalsIgnoreCase("TaigaHills")) || (b.biomeName.equalsIgnoreCase("Jungle")) || (b.biomeName.equalsIgnoreCase("JungleHills")) || (b.biomeName.equalsIgnoreCase("Swampland"))))
      {
        if (rand.nextInt(10) == 1)
        {
          for (int x = chunkX * 16; x < chunkX * 16 + 16; x++)
          {
            for (int z = chunkZ * 16; z < chunkZ * 16 + 16; z++)
            {
              int y = world.getHeightValue(x, z);
              int id = world.getBlockId(x, y, z);

              if ((id == Block.tallGrass.blockID) || (id == Block.snow.blockID))
              {
                world.setBlock(x, z, z, 0, 0, 2);
                id = world.getBlockId(x, y - 1, z);
              }
              else {
                id = world.getBlockId(x, ++y - 1, z);
              }

              if ((id == Block.dirt.blockID) || (id == Block.grass.blockID))
              {
                WorldGenZapAppleTree gen = new WorldGenZapAppleTree(true);
                if (gen.generate(world, rand, x, y, z))
                {
                  System.out.println("Zap Apple Tree generated at " + x + ", " + y + ", " + z);
                  x = chunkX * 16 + 16;
                  z = chunkZ * 16 + 16;
                }
              }
            }
          }
        }
      }
    }
  }
}