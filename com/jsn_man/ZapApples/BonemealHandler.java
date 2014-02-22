package com.jsn_man.ZapApples;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealHandler{
	
	@ForgeSubscribe
	public void onUseBonemeal(BonemealEvent event){
		if(!event.world.isRemote && event.ID == ZapApples.zapAppleSapling.blockID){
			ZapApples.zapAppleSapling.growTree(event.world, event.X, event.Y, event.Z, event.world.rand);
			ItemStack stack = event.entityPlayer.inventory.getCurrentItem();
			
			if(stack != null && stack.stackSize > 1){
				stack.stackSize--;
			}else{
				stack = null;
			}
			
			if(!event.entityPlayer.capabilities.isCreativeMode){
				event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, stack);
			}
		}
	}
	
}