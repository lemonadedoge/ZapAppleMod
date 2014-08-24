package com.chiorichan.ZapApples.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FoodStats;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemJamFood extends ItemFood
{
  public int[] heal = { 7 };
  public float[] saturation = { 0.8F };

  public ItemJamFood(int id)
  {
    super(id, 0, 0.0F, false);
    setUnlocalizedName("jamFood");
  }

  public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
  {
    stack.stackSize -= 1;

    player.getFoodStats().addStats(heal[stack.getItemDamage()], saturation[stack.getItemDamage()]);

    world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
    onFoodEaten(stack, world, player);
    return stack;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    itemIcon = register.registerIcon("zapapples:jamSandwich");
  }
}