package com.chiorichan.ZapApples.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.chiorichan.ZapApples.EasyTess;
import com.chiorichan.ZapApples.ZapApples;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderApple implements ISimpleBlockRenderingHandler
{
	public RenderApple()
	{
		ZapApples.idRenderApple = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler( this );
	}
	
	public void renderInventoryBlock( Block block, int metadata, int modelID, RenderBlocks renderer )
	{
	}
	
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer )
	{
		if ( modelId == getRenderId() )
		{
			if ( ( block == ZapApples.grayApple ) || ( block == ZapApples.zapApple ) )
			{
				renderApple( world, x, y, z, block, renderer, world.getBlockMetadata( x, y, z ) );
				return true;
			}
		}
		return false;
	}
	
	public boolean shouldRender3DInInventory( int arg0 )
	{
		return false;
	}
	
	public int getRenderId()
	{
		return ZapApples.idRenderApple;
	}
	
	public void renderApple( Block block, int meta )
	{
		//Tessellator tessellator = Tessellator.instance;
		IIcon icon = block.getBlockTextureFromSide( 0 );
		
		float x = 0.5F;
		float y = 0.5F;
		float z = 0.5F;
		
		switch ( meta )
		{
			case 0:
				x = ( float ) ( x + 0.25D );
				z = ( float ) ( z + 0.25D );
				break;
			case 1:
				x = ( float ) ( x + 0.25D );
				z = ( float ) ( z + 0.6D );
				y = ( float ) ( y + 0.25D );
				break;
			case 4:
				x = ( float ) ( x + 0.25D );
				z = ( float ) ( z - 0.1D );
				y = ( float ) ( y + 0.25D );
				break;
			case 8:
				x = ( float ) ( x + 0.6D );
				z = ( float ) ( z + 0.25D );
				y = ( float ) ( y + 0.25D );
				break;
			case 2:
				x = ( float ) ( x - 0.1D );
				z = ( float ) ( z + 0.25D );
				y = ( float ) ( y + 0.25D );
				break;
			case 10:
				x = ( float ) ( x + 0.25D );
				z = ( float ) ( z + 0.25D );
				y = ( float ) ( y + 0.5D );
			case 3:
			case 5:
			case 6:
			case 7:
			case 9:
		}
		EasyTess.renderCube( x, y, z, 8, 8, 8, 0, 0, 0.125D, icon, 0, 16, 16 );
		EasyTess.renderCube( x, y, z, 8, 8, 8, 0, 0, 0.34D, icon, 0, 16, 16 );
	}
	
	public void renderApple( IBlockAccess world, float x, float y, float z, Block block, RenderBlocks renderer, int meta )
	{
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.setBrightness( block.getMixedBrightnessForBlock( world, ( int ) x, ( int ) y, ( int ) z ) );
		float var6 = 1.0F;
		int var7 = block.colorMultiplier( world, ( int ) x, ( int ) y, ( int ) z );
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
		
		IIcon icon = block.getBlockTextureFromSide( 0 );
		
		switch ( meta )
		{
			case 0:
				x = ( float ) ( x + 0.25D );
				z = ( float ) ( z + 0.25D );
				break;
			case 1:
				x = ( float ) ( x + 0.25D );
				z = ( float ) ( z + 0.6D );
				y = ( float ) ( y + 0.25D );
				break;
			case 4:
				x = ( float ) ( x + 0.25D );
				z = ( float ) ( z - 0.1D );
				y = ( float ) ( y + 0.25D );
				break;
			case 8:
				x = ( float ) ( x + 0.6D );
				z = ( float ) ( z + 0.25D );
				y = ( float ) ( y + 0.25D );
				break;
			case 2:
				x = ( float ) ( x - 0.1D );
				z = ( float ) ( z + 0.25D );
				y = ( float ) ( y + 0.25D );
				break;
			case 10:
				x = ( float ) ( x + 0.25D );
				z = ( float ) ( z + 0.25D );
				y = ( float ) ( y + 0.5D );
			case 3:
			case 5:
			case 6:
			case 7:
			case 9:
		}
		EasyTess.renderCube( x, y, z, 8, 8, 8, 0, 0, 0.125D, icon, 0, 8, 8 );
		EasyTess.renderCube( x, y, z, 8, 8, 8, 0, 0, 0.34D, icon, 0, 8, 8 );
	}
}
