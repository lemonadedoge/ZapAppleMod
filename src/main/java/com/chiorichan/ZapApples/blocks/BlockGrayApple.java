package com.chiorichan.ZapApples.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.network.PacketHandler;
import com.chiorichan.ZapApples.network.packet.client.SendEffectsPacket;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGrayApple extends Block
{
	
	@SideOnly( Side.CLIENT )
	protected IIcon icon;
	
	public BlockGrayApple()
	{
		super( Material.plants );
		setBlockUnbreakable();
		setResistance( 6000000.0F );
		setCreativeTab( CreativeTabs.tabFood );
		setStepSound( Block.soundTypeGrass );
		setBlockName( "grayApple" );
		
		float var12 = 0.3F;
		setBlockBounds( var12, 0.25F, var12, 1.0F - var12, 0.75F, 1.0F - var12 );
	}
	
	@Override
	public void setBlockBoundsBasedOnState( IBlockAccess world, int x, int y, int z )
	{
		int meta = world.getBlockMetadata( x, y, z );
		
		switch ( meta )
		{
			case 0:
				setBlockBounds( 0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F );
				break;
			case 1:
				setBlockBounds( 0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F );
				break;
			case 4:
				setBlockBounds( 0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F );
				break;
			case 8:
				setBlockBounds( 0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F );
				break;
			case 2:
				setBlockBounds( 0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F );
				break;
			case 10:
				setBlockBounds( 0.25F, 0.5F, 0.25F, 0.75F, 1.0F, 0.75F );
			case 3:
			case 5:
			case 6:
			case 7:
			case 9:
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World par1World, int par2, int par3, int par4 )
	{
		setBlockBoundsBasedOnState( par1World, par2, par3, par4 );
		return super.getCollisionBoundingBoxFromPool( par1World, par2, par3, par4 );
	}
	
	@Override
	public IIcon getIcon( int side, int meta )
	{
		return icon;
	}
	
	@Override
	@SideOnly( Side.CLIENT )
	public void registerBlockIcons( IIconRegister register )
	{
		icon = register.registerIcon( "zapapples:zapapple_premature" );
	}
	
	private boolean canAppleStay( World world, int x, int y, int z )
	{
		return isBlockAt( world, x + 1, y, z ) || isBlockAt( world, x - 1, y, z ) || isBlockAt( world, x, y + 1, z ) || isBlockAt( world, x, y - 1, z ) || isBlockAt( world, x, y, z + 1 ) || isBlockAt( world, x, y, z - 1 );
	}
	
	private boolean isBlockAt( World world, int x, int y, int z )
	{
		Block b = world.getBlock( x, y, z );
		return b == ZapApples.zapAppleLeaves;
	}
	
	@Override
	public void onNeighborBlockChange( World world, int x, int y, int z, Block block )
	{
		if ( !world.isRemote && !canAppleStay( world, x, y, z ) )
		{
			dropBlockAsItem( world, x, y, z, 0, 0 );
			PacketHandler.sendToDimension( new SendEffectsPacket( 0, x, y, z, ZapApples.zapApple, 0 ), world.provider.dimensionId );
			world.setBlockToAir( x, y, z );
		}
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
		return ZapApples.idRenderApple;
	}
	
	@Override
	public int quantityDropped( Random rand )
	{
		return 0;
	}
	
	@Override
	public Item getItemDropped( int par1, Random rand, int par3 )
	{
		return null;
	}
	
	public void updateBlockMetadata( World world, int x, int y, int z, int side, float par6, float par7, float par8 )
	{
		byte meta = 0;
		
		switch ( side )
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
		
		if ( meta != 0 )
		{
			world.setBlock( x, y, z, world.getBlock( x, y, z ), meta, 0 );
		}
	}
}
