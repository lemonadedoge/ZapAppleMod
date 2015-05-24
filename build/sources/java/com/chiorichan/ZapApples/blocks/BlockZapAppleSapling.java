package com.chiorichan.ZapApples.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.chiorichan.ZapApples.WorldGenZapAppleTree;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZapAppleSapling extends BlockBush implements IGrowable
{
	@SideOnly( Side.CLIENT )
	protected IIcon icon;
	
	public BlockZapAppleSapling()
	{
		float var3 = 0.4F;
		setBlockBounds( 0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3 );
		setCreativeTab( CreativeTabs.tabDecorations );
		setHardness( 0.0F );
		setStepSound( Block.soundTypeGrass );
		setBlockName( "zapAppleSapling" );
	}
	
	public void updateTick( World world, int x, int y, int z, Random rand )
	{
		if ( !world.isRemote )
		{
			super.updateTick( world, x, y, z, rand );
			
			if ( ( world.getBlockLightValue( x, y + 1, z ) >= 9 ) && ( rand.nextInt( 7 ) == 0 ) )
			{
				growTree( world, x, y, z, rand );
			}
		}
	}
	
	public IIcon getIcon( int side, int meta )
	{
		return icon;
	}
	
	@SideOnly( Side.CLIENT )
	public void registerBlockIcons( IIconRegister register )
	{
		icon = register.registerIcon( "zapapples:zapapple_sapling" );
	}
	
	@Override
	public boolean func_149851_a( World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_ )
	{
		return true;
	}
	
	@Override
	public boolean func_149852_a( World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_ )
	{
		return (double) p_149852_1_.rand.nextFloat() < 0.45D;
	}
	
	@Override
	public void func_149853_b( World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_ )
	{
		growTree( p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_ );
	}
	
	public void growTree( World world, int x, int y, int z, Random rand )
	{
		WorldGenZapAppleTree gen = new WorldGenZapAppleTree( true );
		world.setBlock( x, y, z, Block.getBlockById( 0 ) );
		if ( !gen.generate( world, rand, x, y, z ) )
		{
			world.setBlock( x, y, z, this );
		}
	}
}
