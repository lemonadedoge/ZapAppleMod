package com.chiorichan.ZapApples.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum BlockDictionary
{
	air( 0 ), stone( 1 ), grass( 2 ), dirt( 3 ), cobblestone( 4 ), planks( 5 ), water( 8 ), waterStill( 9 ), log( 17 ), leaves( 18 ), glass( 20 ), snow( 78 ), coloredGlass( 95 ), glassColored( 95 ), thinGlass( 102 ), glassThin( 102 ), vine( 106 );
	
	private Block block;
	
	private BlockDictionary(int blockId)
	{
		block = Block.getBlockById( blockId );
	}
	
	public int getId()
	{
		return Block.getIdFromBlock( block );
	}
	
	public Block getBlock()
	{
		return block;
	}
	
	public Item getItem()
	{
		return Item.getItemFromBlock( block );
	}
	
	public ItemStack getItemStack()
	{
		return new ItemStack( getItem() );
	}
	
	public ItemStack getItemStack( int count )
	{
		return new ItemStack( getItem(), count );
	}
}
