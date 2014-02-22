package com.jsn_man.ZapApples;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemJar extends ItemBlock{
	
	public ItemJar(int id){
		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	public int getMetadata(int meta){
		return meta;
	}
	
	public String getItemNameIS(ItemStack stack){
		return getItemName() + "." + stack.getItemDamage();
	}
	
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List itemList){
		itemList.add(new ItemStack(this, 1, 0));
		itemList.add(new ItemStack(this, 1, 1));
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}