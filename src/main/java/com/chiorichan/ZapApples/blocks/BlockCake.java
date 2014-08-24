package com.chiorichan.ZapApples.blocks;

import abw;
import com.chiorichan.ZapApples.InvUtils;
import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.tiles.TileEntityCake;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
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
import net.minecraft.src.PlayerCapabilities;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import ye;

public class BlockCake extends BlockContainer
{
  public ItemStack cachedItemStack;
  protected Map<String, CakeIngredientMap> baseIngredients = Maps.newLinkedHashMap();

  protected Map<String, CakeIngredientMap> frostIngredients = Maps.newLinkedHashMap();

  protected boolean postRender = false;

  public BlockCake(int id)
  {
    super(id, Material.cake);
    setCreativeTab(CreativeTabs.tabFood);
    setHardness(0.5F);
    setStepSound(Block.soundClothFootstep);
    setUnlocalizedName("zapplePie");
  }

  public void registerBaseOption(String key, String title, ItemStack activator)
  {
    if (postRender) {
      return;
    }
    baseIngredients.put(key, new CakeIngredientMap(key, title, activator));
  }

  public void registerFrostOption(String key, String title, ItemStack activator)
  {
    if (postRender) {
      return;
    }
    frostIngredients.put(key, new CakeIngredientMap(key, title, activator));
  }

  public boolean isOpaqueCube()
  {
    return false;
  }

  public boolean renderAsNormalBlock()
  {
    return false;
  }

  public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
  {
    TileEntityCake tile = (TileEntityCake)world.getBlockTileEntity(x, y, z);
    if (tile != null)
    {
      float slice = 0.0625F;
      float delta = (1 + tile.stage * 2) / 16.0F;
      setBlockBounds(delta, 0.0F, slice, 1.0F - slice, 0.5F, 1.0F - slice);
    }
    else
    {
      setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }
  }

  public void setBlockBoundsForItemRender()
  {
    float slice = 0.0625F;
    setBlockBounds(slice, 0.0F, slice, 1.0F - slice, 0.5F, 1.0F - slice);
  }

  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9)
  {
    ItemStack current = player.inventory.getCurrentItem();

    if (!world.isRemote) {
      if (current != null)
      {
        for (Map.Entry ent : frostIngredients.entrySet())
        {
          CakeIngredientMap val = (CakeIngredientMap)ent.getValue();

          if ((val.activator != null) && (current.itemID == val.activator.itemID) && (current.getItemDamage() == val.activator.getItemDamage()))
          {
            TileEntityCake cake = (TileEntityCake)world.getBlockTileEntity(x, y, z);

            if (cake != null)
            {
              cake.setData(cake.base, val.key, cake.stage);

              if (!player.capabilities.isCreativeMode) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, InvUtils.consumeItem(current));
              }
              world.markBlockForRenderUpdate(x, y, z);

              break;
            }
          }
        }
      }
      else
      {
        eatSlice(world, x, y, z, player);
        return true;
      }
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
      TileEntityCake tile = (TileEntityCake)world.getBlockTileEntity(x, y, z);
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
    list.add(new ItemStack(par1, 1, 0));
  }

  public int onBlockPlaced(World world, int x, int y, int z, int hitX, float hitY, float hitZ, float block, int meta)
  {
    return meta;
  }

  public TileEntity createNewTileEntity(World world)
  {
    return new TileEntityCake();
  }

  public int getRenderType()
  {
    return ZapApples.idRenderCake;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister register)
  {
    postRender = true;

    for (Map.Entry ent : baseIngredients.entrySet())
    {
      Icon iconTop = register.registerIcon("zapapples:cakes/base_top_" + (String)ent.getKey());
      Icon iconSide = register.registerIcon("zapapples:cakes/base_side_" + (String)ent.getKey());
      Icon iconEaten = register.registerIcon("zapapples:cakes/base_eaten_" + (String)ent.getKey());

      ((CakeIngredientMap)ent.getValue()).setIcons(null, iconTop, iconSide, iconEaten);
    }

    for (Map.Entry ent : frostIngredients.entrySet())
    {
      Icon iconTop = register.registerIcon("zapapples:cakes/topping_top_" + (String)ent.getKey());
      Icon iconSide = register.registerIcon("zapapples:cakes/topping_side_" + (String)ent.getKey());
      Icon iconEaten = register.registerIcon("zapapples:cakes/topping_eaten_" + (String)ent.getKey());

      ((CakeIngredientMap)ent.getValue()).setIcons(iconTop, null, iconSide, iconEaten);
    }
  }

  @SideOnly(Side.CLIENT)
  public Icon getIcon(int side, int meta)
  {
    return ((CakeIngredientMap)baseIngredients.get("plain")).getIcon(side, true);
  }

  public CakeIngredientMap getBaseIngredient(String key)
  {
    if (baseIngredients.containsKey(key)) {
      return (CakeIngredientMap)baseIngredients.get(key);
    }
    return null;
  }

  public CakeIngredientMap getFrostIngredient(String key)
  {
    if (frostIngredients.containsKey(key)) {
      return (CakeIngredientMap)frostIngredients.get(key);
    }
    return null;
  }

  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
  {
    TileEntityCake tile = (TileEntityCake)world.getBlockTileEntity(x, y, z);

    if ((tile != null) && (stack.getTagCompound() != null))
    {
      tile.readItemNBT(stack.getTagCompound());
    }
  }

  public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
  {
    ItemStack result = new ItemStack(this);
    TileEntityCake tile = (TileEntityCake)world.getBlockTileEntity(x, y, z);
    if (tile != null)
    {
      result.setTagCompound(tile.getItemNBT());
    }
    return result;
  }

  public void breakBlock(World world, int x, int y, int z, int oldId, int newId)
  {
    ItemStack result = new ItemStack(this);
    TileEntityCake tile = (TileEntityCake)world.getBlockTileEntity(x, y, z);
    if (tile != null)
    {
      result.setTagCompound(tile.getItemNBT());
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

  public class CakeIngredientMap
  {

    @SideOnly(Side.CLIENT)
    public Icon top;

    @SideOnly(Side.CLIENT)
    public Icon bottom;

    @SideOnly(Side.CLIENT)
    public Icon side;

    @SideOnly(Side.CLIENT)
    public Icon eaten;
    public String key;
    public String title;
    public ItemStack activator;

    public CakeIngredientMap(String _key, String _title, ItemStack _activator)
    {
      key = _key;
      title = _title;
      activator = _activator;
    }

    public void setIcons(Icon textureTop, Icon textureBottom, Icon textureSide, Icon textureEaten)
    {
      top = textureTop;
      bottom = textureBottom;
      side = textureSide;
      eaten = textureEaten;
    }

    public Icon getIcon(int dir, boolean isFull)
    {
      if (dir == 0)
      {
        return bottom;
      }
      if (dir == 1)
      {
        return top;
      }
      if ((!isFull) && (dir == 4))
      {
        return eaten;
      }

      return side;
    }
  }
}