package com.chiorichan.ZapApples.items;

import java.util.List;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumChatFormatting;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Potion;

public class ItemZapApple extends ItemGrayApple
{
  public ItemZapApple(int id)
  {
    super(id);
    healAmount = 10;
    saturationModifier = 0.8F;

    setAlwaysEdible();
    setPotionEffect(Potion.field_76444_x.id, 60, 1, 1.0F);
  }

  public boolean hasEffect(ItemStack stack)
  {
    return true;
  }

  public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par)
  {
    list.add(EnumChatFormatting.YELLOW + "Give 1 Minute Absorption and 5 Full Meat Pops!");
  }
}