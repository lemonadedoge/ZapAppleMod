package com.chiorichan.ZapApples;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class InvUtils
{
  public static ItemStack consumeItem(ItemStack stack)
  {
    if (stackSize == 1)
    {
      if (stack.getItem().hasContainerItem())
      {
        return stack.getItem().getContainerItemStack(stack);
      }

      return null;
    }

    stack.splitStack(1);

    return stack;
  }
}