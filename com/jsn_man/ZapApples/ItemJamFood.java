package com.jsn_man.ZapApples;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

public class ItemJamFood extends ItemFood{
	
	public ItemJamFood(int id, int texture){
		super(id, 0, 0, false);
		iconIndex = texture;
	}
	
	public ItemStack onFoodEaten(ItemStack stack, World world, EntityPlayer player){
		try{
			--stack.stackSize;
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			player.getFoodStats().addStats(heal[stack.getItemDamage()], saturation[stack.getItemDamage()]);
			func_77849_c(stack, world, player);
		}catch(ArrayIndexOutOfBoundsException e){
			FMLLog.info("The damged item consumed was not supported by the ItemJamFood class. Invalid damage number: " + stack.getItemDamage());
		}
		
		return stack;
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
	
	public int[] heal = {
			7
	};
	
	public float[] saturation = {
			0.8F
	};
}