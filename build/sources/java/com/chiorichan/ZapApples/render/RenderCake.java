package com.chiorichan.ZapApples.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.blocks.BlockCake;
import com.chiorichan.ZapApples.blocks.BlockCake.CakeIngredientMap;
import com.chiorichan.ZapApples.tileentity.TileEntityCake;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderCake implements ISimpleBlockRenderingHandler
{
	public RenderCake()
	{
		ZapApples.idRenderCake = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler( this );
	}
	
	@Override
	public void renderInventoryBlock( Block block, int metadata, int modelID, RenderBlocks renderer )
	{
		// Replaced by the RenderCakeItem renderer, which can render itemstack tag data.
	}
	
	@Override
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer )
	{
		if ( modelID == getRenderId() )
		{
			if ( block == ZapApples.cake )
			{
				TileEntityCake tile = (TileEntityCake) world.getTileEntity( x, y, z );
				
				if ( tile != null )
				{
					renderCake( world, x, y, z, block, renderer, world.getBlockMetadata( x, y, z ), tile );
				}
			}
			
			return true;
		}
		return false;
	}
	
	private void renderCake( IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer, int meta, TileEntityCake tile )
	{
		BlockCake cake = (BlockCake) block;
		cake.setBlockBoundsBasedOnState( world, x, y, z );
		
		if ( tile != null )
		{
			Tessellator tessellator = Tessellator.instance;
			
			tessellator.setBrightness( block.getMixedBrightnessForBlock( world, (int) x, (int) y, (int) z ) );
			float var6 = 1.0F;
			int var7 = block.colorMultiplier( world, (int) x, (int) y, (int) z );
			float var8 = ( var7 >> 16 & 0xFF ) / 255.0F;
			float var9 = ( var7 >> 8 & 0xFF ) / 255.0F;
			float var10 = ( var7 & 0xFF ) / 255.0F;
			if ( EntityRenderer.anaglyphEnable )
			{
				float var11 = ( var8 * 30.0F + var9 * 59.0F + var10 * 11.0F ) / 100.0F;
				float var12 = ( var8 * 30.0F + var9 * 70.0F ) / 100.0F;
				float var13 = ( var8 * 30.0F + var10 * 70.0F ) / 100.0F;
				var8 = var11;
				var9 = var12;
				var10 = var13;
			}
			tessellator.setColorOpaque_F( var6 * var8, var6 * var9, var6 * var10 );
			
			CakeIngredientMap baseIcons = ZapApples.cake.getBaseIngredient( tile.base ) == null ? ZapApples.cake.getBaseIngredient( "plain" ) : ZapApples.cake.getBaseIngredient( tile.base );
			CakeIngredientMap frontingIcons = ZapApples.cake.getFrostIngredient( tile.frost ) == null ? ZapApples.cake.getFrostIngredient( "plain" ) : ZapApples.cake.getFrostIngredient( tile.frost );
			
			IIcon icon = baseIcons.getIcon( 3, tile.stage < 1 );
			renderer.renderFaceZNeg( cake, x, y, z, icon );
			renderer.renderFaceZPos( cake, x, y, z, baseIcons.getIcon( 4, tile.stage < 1 ) );
			renderer.renderFaceXNeg( cake, x, y, z, icon );
			renderer.renderFaceXPos( cake, x, y, z, icon );
			renderer.renderFaceYNeg( cake, x, y, z, baseIcons.getIcon( 0, tile.stage < 1 ) );
			
			icon = frontingIcons.getIcon( 3, tile.stage < 1 );
			
			if ( tile.frost != null && !"plain".equalsIgnoreCase( tile.frost ) )
			{
				renderer.renderFaceZNeg( cake, x, y, z, icon );
				renderer.renderFaceZPos( cake, x, y, z, frontingIcons.getIcon( 4, tile.stage < 1 ) );
				renderer.renderFaceXNeg( cake, x, y, z, icon );
				renderer.renderFaceXPos( cake, x, y, z, icon );
			}
			
			renderer.renderFaceYPos( cake, x, y, z, frontingIcons.getIcon( 1, tile.stage < 1 ) );
		}
	}
	
	@Override
	public boolean shouldRender3DInInventory( int arg0 )
	{
		return true;
	}
	
	@Override
	public int getRenderId()
	{
		return ZapApples.idRenderCake;
	}
}
