package com.chiorichan.ZapApples.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.chiorichan.ZapApples.tileentity.TileEntityZapAppleLog;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZapAppleLog extends BlockRotatedPillerContainer
{
	
	@SideOnly( Side.CLIENT )
	protected IIcon sideIcon;
	
	@SideOnly( Side.CLIENT )
	protected IIcon topIcon;
	
	public BlockZapAppleLog()
	{
		super( Material.wood );
		setCreativeTab( CreativeTabs.tabBlock );
		setHardness( 2.0F );
		setStepSound( Block.soundTypeWood );
		setBlockName( "zapAppleLog" );
	}
	
	@Override
	public int getRenderType()
	{
		return 31;
	}
	
	@Override
	public int damageDropped( int meta )
	{
		return 0;
	}
	
	@Override
	@SideOnly( Side.CLIENT )
	protected IIcon getSideIcon( int par1 )
	{
		return sideIcon;
	}
	
	@Override
	@SideOnly( Side.CLIENT )
	protected IIcon getEndIcon( int par1 )
	{
		return topIcon;
	}
	
	@Override
	@SideOnly( Side.CLIENT )
	public void registerBlockIcons( IIconRegister register )
	{
		sideIcon = register.registerIcon( "zapapples:zapapple_log_side" );
		topIcon = register.registerIcon( "zapapples:zapapple_log_top" );
	}
	
	@Override
	public void onBlockPlacedBy( World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack )
	{
		int dir = BlockPistonBase.determineOrientation( world, x, y, z, entity );
		byte meta = 0;
		
		if ( ( dir == 0 ) || ( dir == 1 ) )
		{
			meta = 0;
		}
		else if ( ( dir == 2 ) || ( dir == 3 ) )
		{
			meta = 8;
		}
		else if ( ( dir == 4 ) || ( dir == 5 ) )
		{
			meta = 4;
		}
		
		world.setBlock( x, y, z, this, meta, 2 );
	}
	
	@Override
	public void breakBlock( World world, int x, int y, int z, Block block, int meta )
	{
		super.breakBlock( world, x, y, z, block, meta );
		byte var7 = 1;
		int var8 = var7 + 1;
		
		if ( world.checkChunksExist( x - var8, y - var8, z - var8, x + var8, y + var8, z + var8 ) )
		{
			for ( int var9 = -var7; var9 <= var7; var9++ )
			{
				for ( int var10 = -var7; var10 <= var7; var10++ )
				{
					for ( int var11 = -var7; var11 <= var7; var11++ )
					{
						Block var12 = world.getBlock( x + var9, y + var10, z + var11 );
						
						if ( var12 != null )
							var12.beginLeavesDecay( world, x + var9, y + var10, z + var11 );
					}
				}
			}
		}
	}
	
	public void onBlockPreDestroy( World world, int x, int y, int z, int i )
	{
		// Called before block is removed, no matter by who or what.
	}
	
	public void onBlockHarvested( World world, int x, int y, int z, int i, EntityPlayer player )
	{
		// Hmm? How is this different from PreDestroy?
		TileEntity _tile = world.getTileEntity( x, y, z );
		
		FMLLog.info( "Block at " + x + "," + y + "," + z + " was removed! " + _tile );
		
		if ( _tile != null && _tile instanceof TileEntityZapAppleLog )
		{
			TileEntityZapAppleLog tile = (TileEntityZapAppleLog) _tile;
			tile.notifyRemoval();
		}
	}
	
	public void onBlockDestroyedByPlayer( World world, int x, int y, int z, int i )
	{
		
	}
	
	@Override
	protected ItemStack createStackedBlock( int meta )
	{
		return new ItemStack( this, 1 );
	}
	
	@Override
	public boolean canSustainLeaves( IBlockAccess world, int x, int y, int z )
	{
		return true;
	}
	
	@Override
	public boolean isWood( IBlockAccess world, int x, int y, int z )
	{
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity( World world, int i )
	{
		// return new TileEntityZapAppleLog(); // Tile Entity is set by the tree generator. Too many entities lags peoples games.
		return null;
	}
}
