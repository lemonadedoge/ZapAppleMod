package com.chiorichan.ZapApples.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.items.ItemZapApple;
import com.chiorichan.ZapApples.tileentity.TileEntityJar;
import com.chiorichan.ZapApples.util.InventoryUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockJar extends BlockContainer
{
	public ItemStack cachedItemStack;
	public IIcon textureLidEdge;
	public IIcon textureSide;
	public IIcon textureBottom;
	public IIcon textureTop;
	
	public BlockJar()
	{
		super( Material.glass );
		
		setHardness( 0.5F );
		setCreativeTab( CreativeTabs.tabMisc );
		setStepSound( Block.soundTypeGlass );
		setBlockName( "jar" );
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	@SideOnly( Side.CLIENT )
	public IIcon getIcon( int par1, int par2 )
	{
		switch ( par1 )
		{
			case 0:
			case 1:
				return textureTop;
		}
		return textureBottom;
	}
	
	@Override
	public int getRenderType()
	{
		return ZapApples.idRender3D;
	}
	
	@Override
	public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9 )
	{
		ItemStack current = entityplayer.inventory.getCurrentItem();
		
		if ( current != null )
		{
			FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem( current );
			
			if ( ( current.getItem() instanceof ItemZapApple ) )
			{
				liquid = new FluidStack( ZapApples.zapAppleJam, 1000 );
			}
			
			TileEntityJar tank = (TileEntityJar) world.getTileEntity( i, j, k );
			
			if ( liquid != null )
			{
				int qty = tank.fill( ForgeDirection.UNKNOWN, liquid, true );
				
				if ( ( qty <= 0 ) && ( !entityplayer.capabilities.isCreativeMode ) )
				{
					entityplayer.inventory.setInventorySlotContents( entityplayer.inventory.currentItem, InventoryUtil.consumeItem( current ) );
				}
				world.markBlockForUpdate( i, j, k );
				return true;
			}
			
			FluidStack available = tank.getTankInfo().fluid;
			
			if ( available != null )
			{
				ItemStack filled = FluidContainerRegistry.fillFluidContainer( available, current );
				
				liquid = FluidContainerRegistry.getFluidForFilledItem( filled );
				
				if ( liquid != null )
				{
					if ( !entityplayer.capabilities.isCreativeMode )
					{
						if ( current.stackSize > 1 )
						{
							if ( !entityplayer.inventory.addItemStackToInventory( filled ) )
							{
								return false;
							}
							
							entityplayer.inventory.setInventorySlotContents( entityplayer.inventory.currentItem, current );
						}
						else
						{
							entityplayer.inventory.setInventorySlotContents( entityplayer.inventory.currentItem, current );
							entityplayer.inventory.setInventorySlotContents( entityplayer.inventory.currentItem, filled );
						}
					}
					
					tank.drain( ForgeDirection.UNKNOWN, liquid.amount, true );
					
					world.markBlockForUpdate( i, j, k );
					
					return true;
				}
				
			}
			
			world.markBlockForUpdate( i, j, k );
		}
		
		return false;
	}
	
	@Override
	@SideOnly( Side.CLIENT )
	public void registerBlockIcons( IIconRegister register )
	{
		textureSide = register.registerIcon( "zapapples:jar_side" );
		textureBottom = register.registerIcon( "zapapples:jar_bottom" );
		textureTop = register.registerIcon( "zapapples:jar_top" );
		
		textureLidEdge = register.registerIcon( "zapapples:jar_lidedge" );
	}
	
	@Override
	public int getLightValue( IBlockAccess world, int x, int y, int z )
	{
		TileEntity tile = world.getTileEntity( x, y, z );
		
		if ( ( tile instanceof TileEntityJar ) )
		{
			TileEntityJar tank = (TileEntityJar) tile;
			return tank.getFluidLightLevel();
		}
		
		return super.getLightValue( world, x, y, z );
	}
	
	@Override
	public TileEntity createNewTileEntity( World arg0, int arg1 )
	{
		return new TileEntityJar();
	}
	
	@Override
	public void onBlockPlacedBy( World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack )
	{
		TileEntityJar tile = (TileEntityJar) world.getTileEntity( x, y, z );
		
		if ( ( tile != null ) && ( stack.getTagCompound() != null ) )
		{
			tile.readFromNBT( stack.getTagCompound() );
		}
	}
	
	@Override
	public ItemStack getPickBlock( MovingObjectPosition target, World world, int x, int y, int z )
	{
		ItemStack result = new ItemStack( this );
		TileEntityJar tile = (TileEntityJar) world.getTileEntity( x, y, z );
		if ( tile != null )
		{
			result.setTagCompound( tile.getTank().writeToNBT( new NBTTagCompound() ) );
		}
		return result;
	}
	
	@Override
	public void onBlockHarvested( World world, int x, int y, int z, int i, EntityPlayer player )
	{
		super.onBlockHarvested( world, x, y, z, i, player );
		
		if ( !player.capabilities.isCreativeMode )
		{
			ItemStack result = new ItemStack( this );
			TileEntityJar tile = (TileEntityJar) world.getTileEntity( x, y, z );
			if ( tile != null )
			{
				result.setTagCompound( tile.getTank().writeToNBT( new NBTTagCompound() ) );
			}
			
			cachedItemStack = result;
		}
		else
			cachedItemStack = null;
	}
	
	@Override
	public void breakBlock( World world, int x, int y, int z, Block oldBlock, int newId )
	{
		super.breakBlock( world, x, y, z, oldBlock, newId );
		if ( cachedItemStack != null )
			dropBlockAsItem( world, x, y, z, cachedItemStack );
		cachedItemStack = null;
	}
	
	/*
	 * public ArrayList<ItemStack> dropBlockAsItem( World world, int x, int y, int z, int metadata, int fortune )
	 * {
	 * if ( cachedItemStack == null )
	 * return null;
	 * ArrayList<ItemStack> result = Lists.newArrayList();
	 * result.add( cachedItemStack );
	 * cachedItemStack = null;
	 * return result;
	 * }
	 */
	
	@Override
	public void getSubBlocks( Item par1, CreativeTabs par2CreativeTabs, List par3List )
	{
		par3List.add( new ItemStack( par1, 1, 0 ) );
	}
}
