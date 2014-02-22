package com.jsn_man.ZapApples;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGrayApple extends ItemBlock{
	
	public ItemGrayApple(int id){
		super(id);
		healAmount = 5;
		saturationModifier = 0.1F;
	}
	
	public ItemStack onFoodEaten(ItemStack stack, World world, EntityPlayer player){
        --stack.stackSize;
        player.getFoodStats().addStats(healAmount, saturationModifier);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.func_77849_c(stack, world, player);
        return stack;
    }
	
	protected void func_77849_c(ItemStack stack, World world, EntityPlayer player){
		if(!world.isRemote){
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 300, 0));
			player.addPotionEffect(new PotionEffect(Potion.poison.id, 300, 0));
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 300, 0));
		}
	}
	
	public int getMaxItemUseDuration(ItemStack stack){
		return 32;
	}
	
	public EnumAction getItemUseAction(ItemStack stack){
		return EnumAction.eat;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if(player.canEat(true)){
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		}
		return stack;
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
	
	protected int healAmount;
	protected float saturationModifier;
}