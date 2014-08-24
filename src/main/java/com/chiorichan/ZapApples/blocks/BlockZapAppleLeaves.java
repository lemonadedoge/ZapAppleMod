package com.chiorichan.ZapApples.blocks;

import abw;
import com.chiorichan.ZapApples.ZapApples;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraftforge.common.IShearable;
import ye;

public class BlockZapAppleLeaves extends Block
  implements IShearable
{

  @SideOnly(Side.CLIENT)
  protected Icon icon;
  int[] adjacentTreeBlocks;

  public BlockZapAppleLeaves(int id)
  {
    super(id, Material.leaves);
    setTickRandomly(true);
    setCreativeTab(CreativeTabs.tabDecorations);
    setHardness(0.2F);
    setLightOpacity(1);
    setStepSound(Block.soundGrassFootstep);
    setUnlocalizedName("zapAppleLeaves");
  }

  public Icon getIcon(int side, int meta)
  {
    return icon;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    icon = register.registerIcon("zapapples:zapapple_leaves");
  }

  public int idDropped(int par1, Random par2Random, int par3)
  {
    return ZapApples.zapAppleSapling.blockID;
  }

  public void randomDisplayTick(World world, int x, int y, int z, Random rand)
  {
    if ((world.isRaining()) && (!world.doesBlockHaveSolidTopSurface(x, y - 1, z)) && (rand.nextInt(15) == 1))
    {
      double dropX = x + rand.nextFloat();
      double dropY = y - 0.05D;
      double dropZ = z + rand.nextFloat();
      world.spawnParticle("dripWater", dropX, dropY, dropZ, 0.0D, 0.0D, 0.0D);
    }
  }

  public void breakBlock(World world, int x, int y, int z, int side, int meta)
  {
    byte var7 = 1;
    int var8 = var7 + 1;

    if (world.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8))
    {
      for (int var9 = -var7; var9 <= var7; var9++)
      {
        for (int var10 = -var7; var10 <= var7; var10++)
        {
          for (int var11 = -var7; var11 <= var7; var11++)
          {
            int var12 = world.getBlockId(x + var9, y + var10, z + var11);

            if (Block.blocksList[var12] != null)
            {
              Block.blocksList[var12].beginLeavesDecay(world, x + var9, y + var10, z + var11);
            }
          }
        }
      }
    }
  }

  public void updateTick(World world, int x, int y, int z, Random rand)
  {
    if (!world.isRemote)
    {
      int meta = world.getBlockMetadata(x, y, z);

      if (((meta & 0x8) != 0) && ((meta & 0x4) == 0))
      {
        byte var7 = 4;
        int var8 = var7 + 1;
        byte var9 = 32;
        int var10 = var9 * var9;
        int var11 = var9 / 2;

        if (adjacentTreeBlocks == null)
        {
          adjacentTreeBlocks = new int[var9 * var9 * var9];
        }

        if (world.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8))
        {
          for (int var12 = -var7; var12 <= var7; var12++)
          {
            for (int var13 = -var7; var13 <= var7; var13++)
            {
              for (int var14 = -var7; var14 <= var7; var14++)
              {
                int var15 = world.getBlockId(x + var12, y + var13, z + var14);

                Block block = Block.blocksList[var15];

                if ((block != null) && (block.canSustainLeaves(world, x + var12, y + var13, z + var14)))
                {
                  adjacentTreeBlocks[((var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11)] = 0;
                } else if ((block != null) && (block.isLeaves(world, x + var12, y + var13, z + var14)))
                {
                  adjacentTreeBlocks[((var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11)] = -2;
                }
                else {
                  adjacentTreeBlocks[((var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11)] = -1;
                }
              }
            }
          }

          for (var12 = 1; var12 <= 4; var12++)
          {
            for (int var13 = -var7; var13 <= var7; var13++)
            {
              for (int var14 = -var7; var14 <= var7; var14++)
              {
                for (int var15 = -var7; var15 <= var7; var15++)
                {
                  if (adjacentTreeBlocks[((var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11)] == var12 - 1)
                  {
                    if (adjacentTreeBlocks[((var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11)] == -2)
                    {
                      adjacentTreeBlocks[((var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11)] = var12;
                    }

                    if (adjacentTreeBlocks[((var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11)] == -2)
                    {
                      adjacentTreeBlocks[((var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11)] = var12;
                    }

                    if (adjacentTreeBlocks[((var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11)] == -2)
                    {
                      adjacentTreeBlocks[((var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11)] = var12;
                    }

                    if (adjacentTreeBlocks[((var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11)] == -2)
                    {
                      adjacentTreeBlocks[((var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11)] = var12;
                    }

                    if (adjacentTreeBlocks[((var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1))] == -2)
                    {
                      adjacentTreeBlocks[((var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1))] = var12;
                    }

                    if (adjacentTreeBlocks[((var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1)] == -2)
                    {
                      adjacentTreeBlocks[((var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1)] = var12;
                    }
                  }
                }
              }
            }
          }
        }

        int var12 = adjacentTreeBlocks[(var11 * var10 + var11 * var9 + var11)];

        if (var12 >= 0)
        {
          world.setBlock(x, y, z, blockID, meta & 0xFFFFFFF7, 1);
        }
        else
          removeLeaves(world, x, y, z);
      }
    }
  }

  public void removeLeaves(World world, int x, int y, int z)
  {
    world.setBlockToAir(x, y, z);
  }

  public int quantityDropped(Random rand)
  {
    return rand.nextInt(60) == 0 ? 1 : 0;
  }

  public boolean isOpaqueCube()
  {
    return false;
  }

  public boolean isShearable(ItemStack item, World world, int x, int y, int z)
  {
    return true;
  }

  public ArrayList<ye> onSheared(ye item, abw world, int x, int y, int z, int fortune)
  {
    ArrayList ret = new ArrayList();
    ret.add(new ItemStack(this, 1));
    return ret;
  }

  public boolean isLeaves(World world, int x, int y, int z)
  {
    return true;
  }
}