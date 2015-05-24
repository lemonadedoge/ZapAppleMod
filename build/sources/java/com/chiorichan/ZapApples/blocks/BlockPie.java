package com.chiorichan.ZapApples.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.chiorichan.ZapApples.tileentity.TileEntityPie;

@SuppressWarnings( {"unchecked", "rawtypes"} )
public class BlockPie extends BlockContainer
{
	public BlockPie()
	{
		super( Material.cake );
		setCreativeTab( CreativeTabs.tabFood );
		setHardness( 0.5F );
		setStepSound( Block.soundTypeCloth );
		setBlockName( "zapApplePie" );
	}
	
	@Override
	public void setBlockBoundsBasedOnState( IBlockAccess world, int x, int y, int z )
	{
		setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F );
	}
	
	@Override
	public void setBlockBoundsForItemRender()
	{
		float slice = 0.0625F;
		setBlockBounds( slice, 0.0F, slice, 1.0F - slice, 0.5F, 1.0F - slice );
	}
	
	@Override
	public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9 )
	{
		if ( !world.isRemote )
		{
			eatSlice( world, x, y, z, player );
			return true;
		}
		return true;
	}
	
	@Override
	public void onBlockClicked( World world, int x, int y, int z, EntityPlayer player )
	{
		if ( !world.isRemote )
		{
			eatSlice( world, x, y, z, player );
		}
	}
	
	private void eatSlice( World world, int x, int y, int z, EntityPlayer player )
	{
		if ( player.canEat( false ) )
		{
			TileEntityPie tile = ( TileEntityPie ) world.getTileEntity( x, y, z );
			if ( tile != null )
			{
				tile.eatSlice( player );
			}
		}
	}
	
	@Override
	public boolean canPlaceBlockAt( World world, int x, int y, int z )
	{
		return !super.canPlaceBlockAt( world, x, y, z ) ? false : canBlockStay( world, x, y, z );
	}
	
	@Override
	public boolean canBlockStay( World world, int x, int y, int z )
	{
		return world.getBlock( x, y - 1, z ).getMaterial().isSolid();
	}
	
	@Override
	public void onNeighborBlockChange( World world, int x, int y, int z, Block block )
	{
		if ( !canBlockStay( world, x, y, z ) )
		{
			dropBlockAsItem( world, x, y, z, world.getBlockMetadata( x, y, z ), 0 );
			world.setBlockToAir( x, y, z );
		}
	}
	
	@Override
	public Item getItemDropped( int par1, Random rand, int par3 )
	{
		return null;
	}
	
	@Override
	public int quantityDropped( Random rand )
	{
		return 0;
	}
	
	@Override
	public void getSubBlocks( Item par1, CreativeTabs par2CreativeTabs, List list )
	{
		list.add( new ItemStack( par1, 1, 0 ) );
	}
	
	@Override
	public int onBlockPlaced( World world, int x, int y, int z, int hitX, float hitY, float hitZ, float block, int meta )
	{
		return meta;
	}
	
	@Override
	public TileEntity createNewTileEntity( World world, int i )
	{
		return new TileEntityPie();
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public void registerBlockIcons( IIconRegister icon )
	{
		blockIcon = icon.registerIcon( "zapapples:pie" );
	}
}
