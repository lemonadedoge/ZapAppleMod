package com.chiorichan.ZapApples.blocks;

import abw;
import com.chiorichan.ZapApples.InvUtils;
import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.items.ItemZapApple;
import com.chiorichan.ZapApples.liquids.TankBase;
import com.chiorichan.ZapApples.tiles.TileEntityJar;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.PlayerCapabilities;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import ye;

public class BlockJar extends BlockContainer
{
  public ItemStack cachedItemStack;
  public Icon textureLidEdge;
  public Icon textureSide;
  public Icon textureBottom;
  public Icon textureTop;

  public BlockJar(int blockId)
  {
    super(blockId, Material.glass);

    setHardness(0.5F);
    setCreativeTab(CreativeTabs.tabMisc);
    setStepSound(Block.soundGlassFootstep);
    setUnlocalizedName("jar");
  }

  public boolean renderAsNormalBlock()
  {
    return false;
  }

  public boolean isOpaqueCube()
  {
    return false;
  }

  @SideOnly(Side.CLIENT)
  public Icon getIcon(int par1, int par2)
  {
    switch (par1)
    {
    case 0:
    case 1:
      return textureTop;
    }
    return textureBottom;
  }

  public int getRenderType()
  {
    return ZapApples.idRender3D;
  }

  public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
  {
    ItemStack current = entityplayer.inventory.getCurrentItem();

    if (current != null)
    {
      FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);

      if ((current.getItem() instanceof ItemZapApple))
      {
        liquid = new FluidStack(ZapApples.zapAppleJam, 1000);
      }

      TileEntityJar tank = (TileEntityJar)world.getBlockTileEntity(i, j, k);

      if (liquid != null)
      {
        int qty = tank.fill(ForgeDirection.UNKNOWN, liquid, true);

        if ((qty <= 0) && (!entityplayer.capabilities.isCreativeMode)) {
          entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, InvUtils.consumeItem(current));
        }
        world.markBlockForRenderUpdate(i, j, k);
        return true;
      }

      FluidStack available = tank.getTankInfo().fluid;

      if (available != null)
      {
        ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, current);

        liquid = FluidContainerRegistry.getFluidForFilledItem(filled);

        if (liquid != null)
        {
          if (!entityplayer.capabilities.isCreativeMode)
          {
            if (current.stackSize > 1)
            {
              if (!entityplayer.inventory.addItemStackToInventory(filled)) {
                return false;
              }

              entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, current);
            }
            else
            {
              entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, current);
              entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, filled);
            }
          }

          tank.drain(ForgeDirection.UNKNOWN, liquid.amount, true);

          world.markBlockForRenderUpdate(i, j, k);

          return true;
        }

      }

      world.markBlockForRenderUpdate(i, j, k);
    }

    return false;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    textureSide = register.registerIcon("zapapples:jar_side");
    textureBottom = register.registerIcon("zapapples:jar_bottom");
    textureTop = register.registerIcon("zapapples:jar_top");

    textureLidEdge = register.registerIcon("zapapples:jar_lidedge");
  }

  public int getLightValue(IBlockAccess world, int x, int y, int z)
  {
    TileEntity tile = world.getBlockTileEntity(x, y, z);

    if ((tile instanceof TileEntityJar))
    {
      TileEntityJar tank = (TileEntityJar)tile;
      return tank.getFluidLightLevel();
    }

    return super.getLightValue(world, x, y, z);
  }

  public TileEntity createNewTileEntity(World world)
  {
    return new TileEntityJar();
  }

  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
  {
    TileEntityJar tile = (TileEntityJar)world.getBlockTileEntity(x, y, z);

    if ((tile != null) && (stack.getTagCompound() != null))
    {
      tile.readFromNBT(stack.getTagCompound());
    }
  }

  public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
  {
    ItemStack result = new ItemStack(this);
    TileEntityJar tile = (TileEntityJar)world.getBlockTileEntity(x, y, z);
    if (tile != null)
    {
      result.setTagCompound(tile.getTank().writeToNBT(new NBTTagCompound()));
    }
    return result;
  }

  public void breakBlock(World world, int x, int y, int z, int oldId, int newId)
  {
    ItemStack result = new ItemStack(this);
    TileEntityJar tile = (TileEntityJar)world.getBlockTileEntity(x, y, z);
    if (tile != null)
    {
      result.setTagCompound(tile.getTank().writeToNBT(new NBTTagCompound()));
    }

    cachedItemStack = result;

    super.breakBlock(world, x, y, z, oldId, newId);
  }

  public ArrayList<ye> getBlockDropped(abw world, int x, int y, int z, int metadata, int fortune)
  {
    if (cachedItemStack == null) {
      return null;
    }
    ArrayList result = Lists.newArrayList();

    result.add(cachedItemStack);
    cachedItemStack = null;

    return result;
  }

  public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
  {
    par3List.add(new ItemStack(par1, 1, 0));
  }
}