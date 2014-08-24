package com.chiorichan.ZapApples.items;

import java.util.List;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumChatFormatting;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.StringUtils;

public class ItemJar extends ItemBlock
{
  public ItemJar(int id)
  {
    super(id);
    setMaxDamage(0);
  }

  public int getMetadata(int meta)
  {
    return meta;
  }

  public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par)
  {
    NBTTagCompound tag = itemstack.getTagCompound();

    if ((tag != null) && (tag.hasKey("Jar")))
    {
      tag = tag.getCompoundTag("Jar");

      if (tag.hasKey("Empty"))
      {
        list.add(EnumChatFormatting.RED + "Empty Jar!");
      }
      else
      {
        FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag);

        if ((fluid != null) && (fluid.getFluid() != null))
        {
          list.add(EnumChatFormatting.DARK_PURPLE + "Fluid Type: " + StringUtils.capitalize(fluid.getFluid().getName()));
          list.add(EnumChatFormatting.DARK_PURPLE + "Fluid Level: " + fluid.amount + "mb");
        }
        else
        {
          list.add(EnumChatFormatting.RED + "Empty Jar!");
        }
      }
    }
    else
    {
      list.add(EnumChatFormatting.RED + "Empty Jar!");
    }
  }
}