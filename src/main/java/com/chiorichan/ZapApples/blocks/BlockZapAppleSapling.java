package com.chiorichan.ZapApples.blocks;

import com.chiorichan.ZapApples.WorldGenZapAppleTree;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.World;

public class BlockZapAppleSapling extends BlockFlower
{

  @SideOnly(Side.CLIENT)
  protected Icon icon;

  public BlockZapAppleSapling(int id)
  {
    super(id);
    float var3 = 0.4F;
    setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
    setCreativeTab(CreativeTabs.tabDecorations);
    setHardness(0.0F);
    setStepSound(Block.soundGrassFootstep);
    setUnlocalizedName("zapAppleSapling");
  }

  public Icon getIcon(int side, int meta)
  {
    return icon;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    icon = register.registerIcon("zapapples:zapapple_sapling");
  }

  public void updateTick(World world, int x, int y, int z, Random rand)
  {
    if (!world.isRemote)
    {
      super.updateTick(world, x, y, z, rand);

      if ((world.getBlockLightValue(x, y + 1, z) >= 9) && (rand.nextInt(7) == 0))
      {
        growTree(world, x, y, z, rand);
      }
    }
  }

  public void growTree(World world, int x, int y, int z, Random rand)
  {
    WorldGenZapAppleTree gen = new WorldGenZapAppleTree(true);
    world.setBlock(x, y, z, 0);
    if (!gen.generate(world, rand, x, y, z))
    {
      world.setBlock(x, y, z, blockID);
    }
  }
}