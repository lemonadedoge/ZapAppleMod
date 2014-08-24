package com.chiorichan.ZapApples.blocks;

import abw;
import com.chiorichan.ZapApples.ZapApples;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import ye;

public class BlockGrayApple extends Block
{

  @SideOnly(Side.CLIENT)
  protected Icon icon;

  public BlockGrayApple(int id)
  {
    super(id, Material.plants);
    setBlockUnbreakable();
    setResistance(6000000.0F);
    setCreativeTab(CreativeTabs.tabFood);
    setStepSound(Block.soundGrassFootstep);
    setUnlocalizedName("grayApple");

    float var12 = 0.3F;
    setBlockBounds(var12, 0.25F, var12, 1.0F - var12, 0.75F, 1.0F - var12);
  }

  public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
  {
    int meta = world.getBlockMetadata(x, y, z);

    switch (meta)
    {
    case 0:
      setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
      break;
    case 1:
      setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
      break;
    case 4:
      setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
      break;
    case 8:
      setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
      break;
    case 2:
      setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
      break;
    case 10:
      setBlockBounds(0.25F, 0.5F, 0.25F, 0.75F, 1.0F, 0.75F);
    case 3:
    case 5:
    case 6:
    case 7:
    case 9: } 
  }
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) { setBlockBoundsBasedOnState(par1World, par2, par3, par4);
    return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
  }

  public Icon getIcon(int side, int meta)
  {
    return icon;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    icon = register.registerIcon("zapapples:zapapple_premature");
  }

  private boolean canAppleStay(World world, int x, int y, int z)
  {
    return (isBlockAt(world, x + 1, y, z)) || (isBlockAt(world, x - 1, y, z)) || (isBlockAt(world, x, y + 1, z)) || (isBlockAt(world, x, y - 1, z)) || (isBlockAt(world, x, y, z + 1)) || (isBlockAt(world, x, y, z - 1));
  }

  private boolean isBlockAt(World world, int x, int y, int z)
  {
    int id = world.getBlockId(x, y, z);
    return id == ZapApples.zapAppleLeaves.blockID;
  }

  public void onNeighborBlockChange(World world, int x, int y, int z, int meta)
  {
    if ((!world.isRemote) && (!canAppleStay(world, x, y, z)))
    {
      dropBlockAsItem(world, x, y, z, 0, 0);
      world.setBlockToAir(x, y, z);
    }
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
    return ZapApples.idRenderApple;
  }

  public int quantityDropped(Random rand)
  {
    return 0;
  }

  public void updateBlockMetadata(World world, int x, int y, int z, int side, float par6, float par7, float par8)
  {
    byte meta = 0;

    switch (side)
    {
    case 2:
      meta = 1;
      break;
    case 3:
      meta = 4;
      break;
    case 4:
      meta = 8;
      break;
    case 5:
      meta = 2;
    }

    if (meta != 0)
    {
      world.setBlock(x, y, z, world.getBlockId(x, y, z), meta, 0);
    }
  }

  public ArrayList<ye> getBlockDropped(abw world, int x, int y, int z, int metadata, int fortune)
  {
    ArrayList result = Lists.newArrayList();
    result.add(new ItemStack(this, 1, 0));
    return result;
  }
}