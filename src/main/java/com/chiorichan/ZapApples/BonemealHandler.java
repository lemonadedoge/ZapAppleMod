package com.chiorichan.ZapApples;

import com.chiorichan.ZapApples.blocks.BlockZapAppleSapling;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PlayerCapabilities;
import net.minecraft.src.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealHandler
{
  @ForgeSubscribe
  public void onUseBonemeal(BonemealEvent event)
  {
    if ((!event.world.isRemote) && (event.ID == ZapApples.zapAppleSapling.blockID))
    {
      if (event.world.rand.nextFloat() < 0.1D) {
        ZapApples.zapAppleSapling.growTree(event.world, event.X, event.Y, event.Z, event.world.rand);
      }
      event.world.playAuxSFX(2005, event.X, event.Y, event.Z, 0);

      ItemStack stack = event.entityPlayer.inventory.getCurrentItem();

      if ((stack != null) && (stack.stackSize > 1))
      {
        stack.stackSize -= 1;
      }
      else
      {
        stack = null;
      }

      if (!event.entityPlayer.capabilities.isCreativeMode)
      {
        event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, stack);
      }
    }
  }
}