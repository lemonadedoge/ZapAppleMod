package com.chiorichan.ZapApples.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.chiorichan.ZapApples.WorldGenZapAppleTree;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZapAppleSapling extends BlockFlower
{
	
	@SideOnly( Side.CLIENT )
	protected IIcon icon;
	
	public BlockZapAppleSapling()
	{
		super( 0 );
		float var3 = 0.4F;
		setBlockBounds( 0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3 );
		setCreativeTab( CreativeTabs.tabDecorations );
		setHardness( 0.0F );
		setStepSound( Block.soundTypeGrass );
		setBlockName("zapAppleSapling");
	}
	
	@Override
	public IIcon getIcon( int side, int meta )
	{
		return icon;
	}
	
	@SideOnly( Side.CLIENT )
	public void registerIcons( IIconRegister register )
	{
		icon = register.registerIcon( "zapapples:zapapple_sapling" );
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
