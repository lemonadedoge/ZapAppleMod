package com.chiorichan.ZapApples.blocks;

import com.chiorichan.ZapApples.ZapApples;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.BlockSand;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockFlour extends BlockSand
{

  @SideOnly(Side.CLIENT)
  protected Icon icon;

  public BlockFlour(int id)
  {
    super(id, Material.sand);
    setCreativeTab(CreativeTabs.tabFood);
    setHardness(0.5F);
    setStepSound(Block.soundSandFootstep);
    setUnlocalizedName("flour");
  }

  public void onBlockAdded(World world, int x, int y, int z)
  {
    super.onBlockAdded(world, x, y, z);
    if ((adjacentWater(world, x - 1, y, z)) || (adjacentWater(world, x + 1, y, z)) || (adjacentWater(world, x, y - 1, z)) || (adjacentWater(world, x, y + 1, z)) || (adjacentWater(world, x, y, z - 1)) || (adjacentWater(world, x, y, z + 1)))
    {
      world.setBlockMetadataWithNotify(x, y, z, ZapApples.doughFluidBlock.cF, 0);
    }
  }

  public void onNeighborBlockChange(World world, int x, int y, int z, int i)
  {
    super.onNeighborBlockChange(world, x, y, z, i);
    if ((adjacentWater(world, x - 1, y, z)) || (adjacentWater(world, x + 1, y, z)) || (adjacentWater(world, x, y - 1, z)) || (adjacentWater(world, x, y + 1, z)) || (adjacentWater(world, x, y, z - 1)) || (adjacentWater(world, x, y, z + 1)))
    {
      world.setBlockMetadataWithNotify(x, y, z, ZapApples.doughFluidBlock.cF, 0);
    }
  }

  protected boolean adjacentWater(World world, int x, int y, int z)
  {
    int id = world.getBlockId(x, y, z);
    return (id == Block.waterMoving.blockID) || (id == Block.waterStill.blockID);
  }

  protected void onStartFalling(EntityFallingSand entity)
  {
  }

  public void onFinishFalling(World world, int x, int y, int z, int i)
  {
  }

  public Icon getIcon(int side, int meta)
  {
    return icon;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    icon = register.registerIcon("zapapples:flour");
  }
}