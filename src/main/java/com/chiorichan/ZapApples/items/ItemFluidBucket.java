package com.chiorichan.ZapApples.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBucket;

public class ItemFluidBucket extends ItemBucket
{
  public String iconName;
  public int fluidId;

  public ItemFluidBucket(int itemId, int _fluidId, String unlocalizedName, String _iconName)
  {
    super(itemId, _fluidId);
    fluidId = _fluidId;
    iconName = _iconName;

    setMaxStackSize(1);
    setCreativeTab(CreativeTabs.tabMisc);
    setUnlocalizedName(unlocalizedName);

    setContainerItem(Item.bucketEmpty);
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    itemIcon = register.registerIcon("zapapples:" + iconName);
  }
}