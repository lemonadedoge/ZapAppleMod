package com.chiorichan.ZapApples.items;

import java.util.List;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemPie extends ItemBlock
{
  public ItemPie(int id)
  {
    super(id);
    setHasSubtypes(true);
    setMaxDamage(0);
  }

  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
  {
  }

  public int getMetadata(int meta)
  {
    return meta;
  }
}