package com.chiorichan.ZapApples.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import com.chiorichan.ZapApples.ZapApples;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings( {"unchecked", "rawtypes"} )
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
	
	@Override
	public int getMetadata( int meta )
	{
		return MathHelper.clamp_int( meta, 0, 15 );
	}
	
	@Override
	public void getSubItems( Item par1, CreativeTabs par2CreativeTabs, List itemList )
	{
		for ( int i = 0; i < 16; i++ )
		{
			itemList.add( new ItemStack( this, 1, i ) );
		}
	}
	
	@Override
	public String getItemStackDisplayName( ItemStack stack )
	{
		int meta = stack.getItemDamage();
		return ZapApples.icings[meta] + " Icing";
	}
	
	@Override
	public String getUnlocalizedName( ItemStack stack )
	{
		int meta = stack.getItemDamage();
		return ZapApples.icings[meta].toLowerCase().replace( " ", "_" );
	}
	
	@Override
	@SideOnly( Side.CLIENT )
	public void registerIcons( IIconRegister register )
	{
		for ( int i = 0; i < 16; i++ )
		{
			cz[i] = register.registerIcon( "zapapples:icing" + i );
		}
	}
	
	@Override
	public IIcon getIconFromDamage( int par1 )
	{
		if ( par1 > 15 )
		{
			return cz[0];
		}
		return cz[par1];
	}
}
