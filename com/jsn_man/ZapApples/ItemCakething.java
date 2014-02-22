package com.jsn_man.ZapApples;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCakething extends ItemBlock{
	
	public ItemCakething(int id){
		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag){
		CakethingRegistry.addInfo(list, stack);
	}
	
	public String getItemNameIS(ItemStack stack){
		return getItemName() + "." + getIdentifier(stack.getItemDamage());
	}
	
	public String getIdentifier(int meta){
		if(meta == 0){
			return "applePie";
		}else if(meta == 1){
			return "zapplePie";
		}else{
			return "cake";
		}
	}
	
	public int getMetadata(int meta){
		return meta;
	}
	
	public String getTextureFile(){
		return ZapApples.cakeTextures;
	}
}