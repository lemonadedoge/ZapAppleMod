package com.chiorichan.ZapApples.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.chiorichan.ZapApples.ZapApples;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlour extends BlockFalling
{
	
	@SideOnly( Side.CLIENT )
	protected IIcon icon;
	
	public BlockFlour()
	{
		super();
		setCreativeTab( CreativeTabs.tabFood );
		setHardness( 0.5F );
		setStepSound( Block.soundTypeSand );
		setBlockName( "flour" );
	}
	
	public void onBlockAdded( World world, int x, int y, int z )
	{
		super.onBlockAdded( world, x, y, z );
		if ( ( adjacentWater( world, x - 1, y, z ) ) || ( adjacentWater( world, x + 1, y, z ) ) || ( adjacentWater( world, x, y - 1, z ) ) || ( adjacentWater( world, x, y + 1, z ) ) || ( adjacentWater( world, x, y, z - 1 ) ) || ( adjacentWater( world, x, y, z + 1 ) ) )
		{
			world.setBlock( x, y, z, ZapApples.doughFluidBlock );
			world.markBlockForUpdate( x, y, z );
		}
	}
	
	public void onNeighborBlockChange( World world, int x, int y, int z, Block b )
	{
		super.onNeighborBlockChange( world, x, y, z, b );
		if ( ( adjacentWater( world, x - 1, y, z ) ) || ( adjacentWater( world, x + 1, y, z ) ) || ( adjacentWater( world, x, y - 1, z ) ) || ( adjacentWater( world, x, y + 1, z ) ) || ( adjacentWater( world, x, y, z - 1 ) ) || ( adjacentWater( world, x, y, z + 1 ) ) )
		{
			world.setBlock( x, y, z, ZapApples.doughFluidBlock );
			world.markBlockForUpdate( x, y, z );
		}
	}
	
	protected boolean adjacentWater( World world, int x, int y, int z )
	{
		Block b = world.getBlock( x, y, z );
		return ( b == Blocks.water || b == ZapApples.doughFluidBlock );
	}
	
	public IIcon getIcon( int side, int meta )
	{
		return icon;
	}
	
	@SideOnly( Side.CLIENT )
	public void registerBlockIcons( IIconRegister register )
	{
		icon = register.registerIcon( "zapapples:flour" );
	}
}
