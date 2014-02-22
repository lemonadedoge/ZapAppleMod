package com.jsn_man.ZapApples;

import java.util.Arrays;
import net.minecraft.item.ItemStack;

public class HashableItemStack{
	
	public HashableItemStack(ItemStack item){
		stack = item;
		if(stack != null){
			identifier = new int[]{stack.itemID, stack.getItemDamage()};
		}
	}
	
	public boolean equals(Object obj){
		if(obj instanceof HashableItemStack){
			return Arrays.equals(identifier, ((HashableItemStack)obj).identifier);
		}else if(obj instanceof ItemStack){
			return stack == null ? obj == null ? true : false : stack.isItemEqual((ItemStack)obj);
		}else{
			return false;
		}
	}
	
	public int hashCode(){
		return Arrays.hashCode(identifier);
	}
	
	private int[] identifier;
	public ItemStack stack;
}