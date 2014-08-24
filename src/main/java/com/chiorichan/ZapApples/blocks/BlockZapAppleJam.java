package com.chiorichan.ZapApples.blocks;

import com.chiorichan.ZapApples.ZapApples;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Material;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockZapAppleJam extends BlockFluidClassic
{

  @SideOnly(Side.CLIENT)
  protected Icon stillIcon;

  @SideOnly(Side.CLIENT)
  protected Icon flowingIcon;

  public BlockZapAppleJam(int id, Fluid fluid, Material material)
  {
    super(id, fluid, material);
    c("zapapplejam");
    a(CreativeTabs.tabMisc);
  }

  public Icon a(int side, int meta)
  {
    return (side == 0) || (side == 1) ? stillIcon : flowingIcon;
  }

  @SideOnly(Side.CLIENT)
  public void a(IconRegister register)
  {
    stillIcon = register.registerIcon("zapapples:zapapplejamstill");
    flowingIcon = register.registerIcon("zapapples:zapapplejamflowing");

    ZapApples.zapAppleJam.setIcons(stillIcon, flowingIcon);
  }

  public void a(World world, int x, int y, int z, Entity entity)
  {
    if (world.isRemote) {
      return;
    }
    if ((entity instanceof EntityLivingBase))
    {
      EntityLivingBase ent = (EntityLivingBase)entity;
      ent.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 120, 0));
      ent.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 240, 0));
    }
    super.a(world, x, y, z, entity);
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