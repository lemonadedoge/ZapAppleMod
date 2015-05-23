package com.chiorichan.ZapApples.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import com.chiorichan.ZapApples.ZapApples;
import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZapAppleLeaves extends Block implements IShearable
{
	
	@SideOnly( Side.CLIENT )
	protected IIcon icon;
	int[] adjacentTreeBlocks;
	
	public BlockZapAppleLeaves()
	{
		super( Material.leaves );
		setTickRandomly( true );
		setCreativeTab( CreativeTabs.tabDecorations );
		setHardness( 0.2F );
		setLightOpacity( 1 );
		setStepSound( Block.soundTypeGrass );
		setBlockName( "zapAppleLeaves" );
	}
	
	@Override
	public IIcon getIcon( int side, int meta )
	{
		return icon;
	}
	
	@Override
	@SideOnly( Side.CLIENT )
	public void registerBlockIcons( IIconRegister register )
	{
		icon = register.registerIcon( "zapapples:zapapple_leaves" );
	}
	
	@Override
	public Item getItemDropped( int par1, Random par2Random, int par3 )
	{
		return ZapApples.zapAppleSapling.getItemDropped( par1, par2Random, par3 );
	}
	
	public void randomDisplayTick( World world, int x, int y, int z, Random rand )
	{
		if ( ( world.isRaining() ) && ( !World.doesBlockHaveSolidTopSurface( world, x, y - 1, z ) ) && ( rand.nextInt( 15 ) == 1 ) )
		{
			double dropX = x + rand.nextFloat();
			double dropY = y - 0.05D;
			double dropZ = z + rand.nextFloat();
			world.spawnParticle( "dripWater", dropX, dropY, dropZ, 0.0D, 0.0D, 0.0D );
		}
	}
	
	@Override
	public void breakBlock( World world, int x, int y, int z, Block block, int meta )
	{
		byte var7 = 1;
		int var8 = var7 + 1;
		
		if ( world.checkChunksExist( x - var8, y - var8, z - var8, x + var8, y + var8, z + var8 ) )
		{
			for ( int var9 = -var7; var9 <= var7; var9++ )
			{
				for ( int var10 = -var7; var10 <= var7; var10++ )
				{
					for ( int var11 = -var7; var11 <= var7; var11++ )
					{
						Block b = world.getBlock( x + var9, y + var10, z + var11 );
						
						if ( b != null )
							b.beginLeavesDecay( world, x + var9, y + var10, z + var11 );
					}
				}
			}
		}
	}
	
	@Override
	public void updateTick( World world, int x, int y, int z, Random rand )
	{
		if ( !world.isRemote )
		{
			int meta = world.getBlockMetadata( x, y, z );
			
			if ( ( ( meta & 0x8 ) != 0 ) && ( ( meta & 0x4 ) == 0 ) )
			{
				byte var7 = 4;
				int var8 = var7 + 1;
				byte var9 = 32;
				int var10 = var9 * var9;
				int var11 = var9 / 2;
				
				if ( adjacentTreeBlocks == null )
				{
					adjacentTreeBlocks = new int[var9 * var9 * var9];
				}
				
				if ( world.checkChunksExist( x - var8, y - var8, z - var8, x + var8, y + var8, z + var8 ) )
				{
					for ( int var12 = -var7; var12 <= var7; var12++ )
					{
						for ( int var13 = -var7; var13 <= var7; var13++ )
						{
							for ( int var14 = -var7; var14 <= var7; var14++ )
							{
								Block block = world.getBlock( x + var12, y + var13, z + var14 );
								
								if ( ( block != null ) && ( block.canSustainLeaves( world, x + var12, y + var13, z + var14 ) ) )
								{
									adjacentTreeBlocks[ ( ( var12 + var11 ) * var10 + ( var13 + var11 ) * var9 + var14 + var11 )] = 0;
								}
								else if ( ( block != null ) && ( block.isLeaves( world, x + var12, y + var13, z + var14 ) ) )
								{
									adjacentTreeBlocks[ ( ( var12 + var11 ) * var10 + ( var13 + var11 ) * var9 + var14 + var11 )] = -2;
								}
								else
								{
									adjacentTreeBlocks[ ( ( var12 + var11 ) * var10 + ( var13 + var11 ) * var9 + var14 + var11 )] = -1;
								}
							}
						}
					}
					
					for ( int var12 = 1; var12 <= 4; var12++ )
					{
						for ( int var13 = -var7; var13 <= var7; var13++ )
						{
							for ( int var14 = -var7; var14 <= var7; var14++ )
							{
								for ( int var15 = -var7; var15 <= var7; var15++ )
								{
									if ( adjacentTreeBlocks[ ( ( var13 + var11 ) * var10 + ( var14 + var11 ) * var9 + var15 + var11 )] == var12 - 1 )
									{
										if ( adjacentTreeBlocks[ ( ( var13 + var11 - 1 ) * var10 + ( var14 + var11 ) * var9 + var15 + var11 )] == -2 )
										{
											adjacentTreeBlocks[ ( ( var13 + var11 - 1 ) * var10 + ( var14 + var11 ) * var9 + var15 + var11 )] = var12;
										}
										
										if ( adjacentTreeBlocks[ ( ( var13 + var11 + 1 ) * var10 + ( var14 + var11 ) * var9 + var15 + var11 )] == -2 )
										{
											adjacentTreeBlocks[ ( ( var13 + var11 + 1 ) * var10 + ( var14 + var11 ) * var9 + var15 + var11 )] = var12;
										}
										
										if ( adjacentTreeBlocks[ ( ( var13 + var11 ) * var10 + ( var14 + var11 - 1 ) * var9 + var15 + var11 )] == -2 )
										{
											adjacentTreeBlocks[ ( ( var13 + var11 ) * var10 + ( var14 + var11 - 1 ) * var9 + var15 + var11 )] = var12;
										}
										
										if ( adjacentTreeBlocks[ ( ( var13 + var11 ) * var10 + ( var14 + var11 + 1 ) * var9 + var15 + var11 )] == -2 )
										{
											adjacentTreeBlocks[ ( ( var13 + var11 ) * var10 + ( var14 + var11 + 1 ) * var9 + var15 + var11 )] = var12;
										}
										
										if ( adjacentTreeBlocks[ ( ( var13 + var11 ) * var10 + ( var14 + var11 ) * var9 + ( var15 + var11 - 1 ) )] == -2 )
										{
											adjacentTreeBlocks[ ( ( var13 + var11 ) * var10 + ( var14 + var11 ) * var9 + ( var15 + var11 - 1 ) )] = var12;
										}
										
										if ( adjacentTreeBlocks[ ( ( var13 + var11 ) * var10 + ( var14 + var11 ) * var9 + var15 + var11 + 1 )] == -2 )
										{
											adjacentTreeBlocks[ ( ( var13 + var11 ) * var10 + ( var14 + var11 ) * var9 + var15 + var11 + 1 )] = var12;
										}
									}
								}
							}
						}
					}
				}
				
				int var12 = adjacentTreeBlocks[ ( var11 * var10 + var11 * var9 + var11 )];
				
				if ( var12 >= 0 )
				{
					world.setBlock( x, y, z, this, meta & 0xFFFFFFF7, 1 );
				}
				else
					removeLeaves( world, x, y, z );
			}
		}
	}
	
	public void removeLeaves( World world, int x, int y, int z )
	{
		world.setBlockToAir( x, y, z );
	}
	
	@Override
	public int quantityDropped( Random rand )
	{
		return rand.nextInt( 60 ) == 0 ? 1 : 0;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean isShearable( ItemStack arg0, IBlockAccess arg1, int arg2, int arg3, int arg4 )
	{
		return true;
	}
	
	@Override
	public ArrayList<ItemStack> onSheared( ItemStack arg0, IBlockAccess arg1, int arg2, int arg3, int arg4, int arg5 )
	{
		ArrayList<ItemStack> ret = Lists.newArrayList();
		ret.add( new ItemStack( this, 1 ) );
		return ret;
	}
}
