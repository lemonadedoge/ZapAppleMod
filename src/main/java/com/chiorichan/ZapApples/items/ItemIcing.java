package com.chiorichan.ZapApples.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIcing extends Item
{
	protected IIcon[] cz = new IIcon[16];
	
	public ItemIcing()
	{
		super();
		setCreativeTab( CreativeTabs.tabFood );
		setHasSubtypes( true );
		setMaxDamage( 0 );
		setUnlocalizedName( "icing" );
	}
	
	public int getMetadata( int meta )
	{
		return MathHelper.clamp_int( meta, 0, 15 );
	}
	
	public void getSubItems( int par1, CreativeTabs par2CreativeTabs, List itemList )
	{
		for ( int i = 0; i < 16; i++ )
		{
			itemList.add( new ItemStack( this, 1, i ) );
		}
	}
	
	public String getItemDisplayName( ItemStack stack )
	{
		int meta = stack.getItemDamage();
		return com.chiorichan.ZapApples.ZapApples.icings[meta] + " Icing";
	}
	
	@SideOnly( Side.CLIENT )
	public void registerIcons( IIconRegister register )
	{
		for ( int i = 0; i < 16; i++ )
		{
			cz[i] = register.registerIcon( "zapapples:icing" + i );
		}
	}
	
	public IIcon getIconFromDamage( int par1 )
	{
		if ( par1 > 15 )
		{
			return cz[0];
		}
		return cz[par1];
	}
}
