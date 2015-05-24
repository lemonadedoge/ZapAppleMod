package com.chiorichan.ZapApples.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings( {"unchecked", "rawtypes"} )
public class BlockZapApplePlanks extends Block
{
	public BlockZapApplePlanks()
	{
		super( Material.wood );
		setCreativeTab( CreativeTabs.tabBlock );
		setHardness( 2.0F );
		setResistance( 5.0F );
		setStepSound( Block.soundTypeWood );
		setBlockName( "zapPlanks" );
		setBlockTextureName( "zapapples:zapapple_planks" );
	}
	
	@Override
	public int damageDropped( int par1 )
	{
		return par1;
	}
	
	@Override
	@SideOnly( Side.CLIENT )
	public void getSubBlocks( Item par1, CreativeTabs par2CreativeTabs, List par3List )
	{
		par3List.add( new ItemStack( par1, 1, 0 ) );
	}
}
