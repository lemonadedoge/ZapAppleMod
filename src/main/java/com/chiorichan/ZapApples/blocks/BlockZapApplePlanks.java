package com.chiorichan.ZapApples.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;

public class BlockZapApplePlanks extends Block
{

  @SideOnly(Side.CLIENT)
  private Icon icon;

  public BlockZapApplePlanks(int par1)
  {
    super(par1, Material.wood);
    setCreativeTab(CreativeTabs.tabBlock);
    setHardness(2.0F);
    setResistance(5.0F);
    setStepSound(Block.soundWoodFootstep);
    setUnlocalizedName("wood");
  }

  @SideOnly(Side.CLIENT)
  public Icon getIcon(int par1, int par2)
  {
    return icon;
  }

  public int damageDropped(int par1)
  {
    return par1;
  }

  @SideOnly(Side.CLIENT)
  public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
  {
    par3List.add(new ItemStack(par1, 1, 0));
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    icon = register.registerIcon("zapapples:zapapple_planks");
  }
}