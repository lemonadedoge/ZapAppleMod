package com.jsn_man.ZapApples;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemIcing extends Item{
	
	public ItemIcing(int id, int texture){
		super(id);
		setCreativeTab(CreativeTabs.tabFood);
		setHasSubtypes(true);
		setMaxDamage(0);
		iconIndex = texture;
	}
	
	public int getMetadata(int meta){
		return MathHelper.clamp_int(meta, 0, 15);
	}
	
	public int getIconFromDamage(int meta){
		return iconIndex + MathHelper.clamp_int(meta, 0, 15);
	}
	
	public String getItemNameIS(ItemStack stack){
		return getItemName() + "." + MathHelper.clamp_int(stack.getItemDamage(), 0, 15);
	}
	
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List itemList){
		for(int i = 0; i < 16; i++){
			itemList.add(new ItemStack(this, 1, i));
		}
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}