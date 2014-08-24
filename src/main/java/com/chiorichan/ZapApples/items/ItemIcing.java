package com.chiorichan.ZapApples.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;

public class ItemIcing extends Item
{
  protected Icon[] cz = new Icon[16];

  public ItemIcing(int id)
  {
    super(id);
    setCreativeTab(CreativeTabs.tabFood);
    setHasSubtypes(true);
    setMaxDamage(0);
    setUnlocalizedName("icing");
  }

  public int getMetadata(int meta)
  {
    return MathHelper.clamp_int(meta, 0, 15);
  }

  public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List itemList)
  {
    for (int i = 0; i < 16; i++)
    {
      itemList.add(new ItemStack(this, 1, i));
    }
  }

  public String getItemDisplayName(ItemStack stack)
  {
    int meta = stack.getItemDamage();
    return com.chiorichan.ZapApples.ZapApples.icings[meta] + " Icing";
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    for (int i = 0; i < 16; i++)
    {
      cz[i] = register.registerIcon("zapapples:icing" + i);
    }
  }

  public Icon getIconFromDamage(int par1)
  {
    if (par1 > 15) {
      return cz[0];
    }
    return cz[par1];
  }
}