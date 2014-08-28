package com.chiorichan.ZapApples.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFluidBucket extends ItemBucket
{
	public String iconName;
	
	public ItemFluidBucket(Block block, String unlocalizedName, String _iconName)
	{
		super( block );
		iconName = _iconName;
		
		setMaxStackSize( 1 );
		setCreativeTab( CreativeTabs.tabMisc );
		setUnlocalizedName( unlocalizedName );
		
		setContainerItem( Items.bucket );
	}
	
	@SideOnly( Side.CLIENT )
	public void registerIcons( IIconRegister register )
	{
		itemIcon = register.registerIcon( "zapapples:" + iconName );
	}
}
