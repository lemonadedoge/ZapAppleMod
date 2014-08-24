package com.chiorichan.ZapApples.blocks;

import com.chiorichan.ZapApples.ZapApples;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockDoughFluid extends BlockFluidClassic
{

  @SideOnly(Side.CLIENT)
  protected Icon stillIcon;

  @SideOnly(Side.CLIENT)
  protected Icon flowingIcon;

  public BlockDoughFluid(int id, Fluid fluid, Material material)
  {
    super(id, fluid, material);
    c("doughfluid");
    a(CreativeTabs.tabMisc);
  }

  public Icon a(int side, int meta)
  {
    return (side == 0) || (side == 1) ? stillIcon : flowingIcon;
  }

  @SideOnly(Side.CLIENT)
  public void a(IconRegister register)
  {
    stillIcon = register.registerIcon("zapapples:doughstill");
    flowingIcon = register.registerIcon("zapapples:doughflowing");

    ZapApples.doughFluid.setIcons(stillIcon, flowingIcon);
  }

  public boolean canDisplace(IBlockAccess world, int x, int y, int z)
  {
    if (world.getBlockMaterial(x, y, z).isLiquid())
      return false;
    return super.canDisplace(world, x, y, z);
  }

  public boolean displaceIfPossible(World world, int x, int y, int z)
  {
    if (world.getBlockMaterial(x, y, z).isLiquid())
      return false;
    return super.displaceIfPossible(world, x, y, z);
  }
}