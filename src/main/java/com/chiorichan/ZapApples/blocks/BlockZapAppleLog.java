package com.chiorichan.ZapApples.blocks;

import com.chiorichan.ZapApples.tiles.TileEntityZapAppleLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.BlockPistonBase;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockZapAppleLog extends BlockRotatedPillerContainer
{

  @SideOnly(Side.CLIENT)
  protected Icon sideIcon;

  @SideOnly(Side.CLIENT)
  protected Icon topIcon;

  public BlockZapAppleLog(int id)
  {
    super(id, Material.wood);
    setCreativeTab(CreativeTabs.tabBlock);
    setHardness(2.0F);
    setStepSound(Block.soundWoodFootstep);
    setUnlocalizedName("zapAppleLog");
  }

  public int getRenderType()
  {
    return 31;
  }

  public int damageDropped(int meta)
  {
    return 0;
  }

  @SideOnly(Side.CLIENT)
  protected Icon getSideIcon(int par1)
  {
    return sideIcon;
  }

  @SideOnly(Side.CLIENT)
  protected Icon getEndIcon(int par1)
  {
    return topIcon;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    sideIcon = register.registerIcon("zapapples:zapapple_log_side");
    topIcon = register.registerIcon("zapapples:zapapple_log_top");
  }

  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity)
  {
    int dir = BlockPistonBase.determineOrientation(world, x, y, z, entity);
    byte meta = 0;

    if ((dir == 0) || (dir == 1))
    {
      meta = 0;
    }
    else if ((dir == 2) || (dir == 3))
    {
      meta = 8;
    }
    else if ((dir == 4) || (dir == 5))
    {
      meta = 4;
    }

    world.setBlock(x, y, z, blockID, meta, 2);
  }

  public void breakBlock(World world, int x, int y, int z, int side, int meta)
  {
    super.breakBlock(world, x, y, z, side, meta);
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

  protected ItemStack createStackedBlock(int meta)
  {
    return new ItemStack(this, 1);
  }

  public boolean canSustainLeaves(World world, int x, int y, int z)
  {
    return true;
  }

  public boolean isWood(World world, int x, int y, int z)
  {
    return true;
  }

  public TileEntity createNewTileEntity(World world)
  {
    return new TileEntityZapAppleLog();
  }
}