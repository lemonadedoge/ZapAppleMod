package com.chiorichan.ZapApples.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

@SuppressWarnings( {"rawtypes"} )
public class ItemPie extends ItemBlock
{
	public ItemPie(Block block)
	{
		super( block );
		setHasSubtypes( true );
		setMaxDamage( 0 );
	}
	
	public void addInformation( ItemStack stack, EntityPlayer player, List list, boolean flag )
	{
	}
	
	public int getMetadata( int meta )
	{
		return meta;
	}
}
