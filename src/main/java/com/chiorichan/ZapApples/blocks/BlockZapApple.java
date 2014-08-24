package com.chiorichan.ZapApples.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;

public class BlockZapApple extends BlockGrayApple
{

  @SideOnly(Side.CLIENT)
  protected Icon icon;
  public boolean isHarvestDay;

  public BlockZapApple(int id)
  {
    super(id);
    setResistance(0.0F);
    setHardness(0.1F);
    setStepSound(Block.soundGrassFootstep);
    setUnlocalizedName("zapApple");
  }

  public Icon getIcon(int side, int meta)
  {
    return icon;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    icon = register.registerIcon("zapapples:zapapple_mature");
  }

  public int quantityDropped(Random rand)
  {
    return isHarvestDay ? 1 : 0;
  }
}