package com.chiorichan.ZapApples.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZapApplePlanks extends Block
{
	
	@SideOnly( Side.CLIENT )
	private IIcon icon;
	
	public BlockZapApplePlanks()
	{
		super( Material.wood );
		setCreativeTab( CreativeTabs.tabBlock );
		setHardness( 2.0F );
		setResistance( 5.0F );
		setStepSound( Block.soundTypeWood );
		setBlockName( "wood" );
	}
	
	@SideOnly( Side.CLIENT )
	public IIcon getIcon( int par1, int par2 )
	{
		return icon;
	}
	
	public int damageDropped( int par1 )
	{
		return par1;
	}
	
	@SideOnly( Side.CLIENT )
	public void getSubBlocks( Item par1, CreativeTabs par2CreativeTabs, List par3List )
	{
		par3List.add( new ItemStack( par1, 1, 0 ) );
	}
	
	@SideOnly( Side.CLIENT )
	public void registerIcons( IIconRegister register )
	{
		icon = register.registerIcon( "zapapples:zapapple_planks" );
	}
}
