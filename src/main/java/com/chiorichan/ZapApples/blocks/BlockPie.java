package com.chiorichan.ZapApples.blocks;

import com.chiorichan.ZapApples.tiles.TileEntityPie;
import java.util.List;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockPie extends BlockContainer
{
  public BlockPie(int id)
  {
    super(id, Material.cake);
    setCreativeTab(CreativeTabs.tabFood);
    setHardness(0.5F);
    setStepSound(Block.soundClothFootstep);
    setUnlocalizedName("zapplePie");
  }

  public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
  {
    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
  }

  public void setBlockBoundsForItemRender()
  {
    float slice = 0.0625F;
    setBlockBounds(slice, 0.0F, slice, 1.0F - slice, 0.5F, 1.0F - slice);
  }

  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9)
  {
    if (!world.isRemote)
    {
      eatSlice(world, x, y, z, player);
      return true;
    }
    return true;
  }

  public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
  {
    if (!world.isRemote)
    {
      eatSlice(world, x, y, z, player);
    }
  }

  private void eatSlice(World world, int x, int y, int z, EntityPlayer player)
  {
    if (player.canEat(false))
    {
      TileEntityPie tile = (TileEntityPie)world.getBlockTileEntity(x, y, z);
      if (tile != null)
      {
        tile.eatSlice(player);
      }
    }
  }

  public boolean canPlaceBlockAt(World world, int x, int y, int z)
  {
    return !super.canPlaceBlockAt(world, x, y, z) ? false : canBlockStay(world, x, y, z);
  }

  public boolean canBlockStay(World world, int x, int y, int z)
  {
    return world.getBlockMaterial(x, y - 1, z).isSolid();
  }

  public void onNeighborBlockChange(World world, int x, int y, int z, int meta)
  {
    if (!canBlockStay(world, x, y, z))
    {
      dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
      world.setBlockToAir(x, y, z);
    }
  }

  public int idDropped(int par1, Random rand, int par3)
  {
    return 0;
  }

  public int quantityDropped(Random rand)
  {
    return 0;
  }

  public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list)
  {
    list.add(new ItemStack(blockID, 1, 0));
  }

  public int onBlockPlaced(World world, int x, int y, int z, int hitX, float hitY, float hitZ, float block, int meta)
  {
    return meta;
  }

  public TileEntity createNewTileEntity(World world)
  {
    return new TileEntityPie();
  }

  public boolean isOpaqueCube()
  {
    return false;
  }

  public boolean renderAsNormalBlock()
  {
    return false;
  }

  public int getRenderType()
  {
    return -1;
  }

  public void registerIcons(IconRegister icon)
  {
    blockIcon = icon.registerIcon("zapapples:pie");
  }
}