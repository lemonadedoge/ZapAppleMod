package com.chiorichan.ZapApples.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemZapAppleMushed extends Item
{
	public ItemZapAppleMushed()
	{
		setCreativeTab( CreativeTabs.tabFood );
		setUnlocalizedName( "zapAppleMushed" );
	}
	
	@SideOnly( Side.CLIENT )
	public void registerIcons( IIconRegister register )
	{
		itemIcon = register.registerIcon( "zapapples:zapapple_mushed" );
	}
}
