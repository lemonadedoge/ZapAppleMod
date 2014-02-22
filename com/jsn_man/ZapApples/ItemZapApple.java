package com.jsn_man.ZapApples;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemZapApple extends ItemGrayApple{
	
	public ItemZapApple(int id){
		super(id);
		healAmount = 10;
		saturationModifier = 0.8F;
	}
	
	protected void func_77849_c(ItemStack stack, World world, EntityPlayer player){
		if(!world.isRemote){
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 600, 0));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 600, 0));
			player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 600, 0));
		}
	}
	
	public boolean hasEffect(ItemStack stack){
		return true;
	}
}