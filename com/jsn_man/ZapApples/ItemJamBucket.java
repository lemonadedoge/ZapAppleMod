package com.jsn_man.ZapApples;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;

public class ItemJamBucket extends ItemBucket{
	
	public ItemJamBucket(int id, int fluidID){
		super(id, fluidID);
		setCreativeTab(CreativeTabs.tabFood);
		setContainerItem(Item.bucketEmpty);
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}