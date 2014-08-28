package com.chiorichan.ZapApples.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.chiorichan.ZapApples.ZapApples;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDoughFluid extends BlockFluidClassic
{
	@SideOnly( Side.CLIENT )
	protected IIcon stillIcon;
	
	@SideOnly( Side.CLIENT )
	protected IIcon flowingIcon;
	
	public BlockDoughFluid(Fluid fluid, Material material)
	{
		super( fluid, material );
		setCreativeTab( CreativeTabs.tabMisc );
		setBlockName( "doughfluid" );
	}
	
	public IIcon getIcon( int side, int meta )
	{
		return ( side == 0 ) || ( side == 1 ) ? stillIcon : flowingIcon;
	}
	
	@SideOnly( Side.CLIENT )
	public void registerIcon( IIconRegister register )
	{
		stillIcon = register.registerIcon( "zapapples:doughstill" );
		flowingIcon = register.registerIcon( "zapapples:doughflowing" );
		
		ZapApples.doughFluid.setIcons( stillIcon, flowingIcon );
	}
	
	public boolean canDisplace( IBlockAccess world, int x, int y, int z )
	{
		if ( world.getBlock( x, y, z ).getMaterial().isLiquid() )
			return false;
		return super.canDisplace( world, x, y, z );
	}
	
	public boolean displaceIfPossible( World world, int x, int y, int z )
	{
		if ( world.getBlock( x, y, z ).getMaterial().isLiquid() )
			return false;
		return super.displaceIfPossible( world, x, y, z );
	}
}
