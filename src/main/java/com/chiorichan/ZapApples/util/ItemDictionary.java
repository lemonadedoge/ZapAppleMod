package com.chiorichan.ZapApples.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Yes this enum is far from complete, I just added them as I needed them.
 * 
 * @author Chiori Greene
 */
public enum ItemDictionary
{
	coal( 263 ), diamond( 264 ), ingotIron( 265 ), ingotGold( 266 ), wheat( 296 ), bread( 297 ), bucketEmpty( 325 ), waterBucket( 326 ), lavaBucket( 327 ), bucketMilk( 335 ), milkBucket( 335 ), dyePowder( 351 ), bone( 352 ), sugar( 353 );
	
	private Item item;
	
	private ItemDictionary(int itemId)
	{
		item = Item.getItemById( itemId );
	}
	
	public int getId()
	{
		return Item.getIdFromItem( item );
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public ItemStack getItemStack()
	{
		return new ItemStack( item );
	}
	
	public ItemStack getItemStack( int count )
	{
		return new ItemStack( item, count );
	}
}
