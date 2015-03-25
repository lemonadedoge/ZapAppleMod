package com.chiorichan.ZapApples;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;

public class TreeGenerator implements IWorldGenerator
{
	public void generate( Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider )
	{
		if ( !ZapApples.disableZapAppleTreesInDimensions.contains( world.provider.dimensionId ) )
		{
			BiomeGenBase b = world.getBiomeGenForCoords( chunkX * 16, chunkZ * 16 );
			if ( ( b != null ) && ( b.biomeName != null ) && ( ( b.biomeName.equalsIgnoreCase( "Savanna" ) ) || ( b.biomeName.equalsIgnoreCase( "Forest" ) ) || ( b.biomeName.equalsIgnoreCase( "Taiga" ) ) || ( b.biomeName.equalsIgnoreCase( "Sky" ) ) || ( b.biomeName.equalsIgnoreCase( "ForestHills" ) ) || ( b.biomeName.equalsIgnoreCase( "TaigaHills" ) ) || ( b.biomeName.equalsIgnoreCase( "Jungle" ) ) || ( b.biomeName.equalsIgnoreCase( "JungleHills" ) ) || ( b.biomeName.equalsIgnoreCase( "Swampland" ) ) ) )
			{
				if ( rand.nextInt( 70 ) == 1 )
				{
					for ( int x = chunkX * 16; x < chunkX * 16 + 16; x++ )
					{
						for ( int z = chunkZ * 16; z < chunkZ * 16 + 16; z++ )
						{
							int y = world.getHeightValue( x, z );
							Block block = world.getBlock( x, y, z );
							
							if ( ( block == Blocks.tallgrass ) || ( block == Blocks.snow ) )
							{
								world.setBlockToAir( x, y, z );
								block = world.getBlock( x, y - 1, z );
							}
							else
							{
								block = world.getBlock( x, ++y - 1, z );
							}
							
							if ( ( block == Blocks.dirt ) || ( block == Blocks.grass ) )
							{
								WorldGenZapAppleTree gen = new WorldGenZapAppleTree( true );
								if ( gen.generate( world, rand, x, y, z ) )
								{
									FMLLog.info( "Zap Apple Tree generated at " + x + ", " + y + ", " + z );
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
