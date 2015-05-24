package com.chiorichan.ZapApples.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.tileentity.TileEntityJar;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class Render3D implements ISimpleBlockRenderingHandler
{
	public Render3D()
	{
		ZapApples.idRender3D = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler( this );
	}
	
	public void renderInventoryBlock( Block block, int metadata, int modelID, RenderBlocks renderer )
	{
		if ( block == ZapApples.jar )
		{
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.setNormal( 0.0F, -1.0F, 0.0F );
			renderJar( null, -0.5F, -0.5F, -0.5F, block, renderer, metadata, null );
			tessellator.draw();
		}
	}
	
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer )
	{
		if ( modelID == getRenderId() )
		{
			if ( block == ZapApples.jar )
			{
				TileEntityJar tile = (TileEntityJar) world.getTileEntity( x, y, z );
				if ( tile != null )
				{
					renderJar( world, x, y, z, block, renderer, world.getBlockMetadata( x, y, z ), tile );
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean shouldRender3DInInventory( int arg0 )
	{
		return true;
	}
	
	public int getRenderId()
	{
		return ZapApples.idRender3D;
	}
	
	private void renderJar( IBlockAccess world, float x, float y, float z, Block block, RenderBlocks renderer, int meta, TileEntityJar tile )
	{
		Tessellator var5 = Tessellator.instance;
		if ( world != null )
		{
			var5.setBrightness( block.getMixedBrightnessForBlock( world, (int) x, (int) y, (int) z ) );
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
			var5.setColorOpaque_F( var6 * var8, var6 * var9, var6 * var10 );
		}
		
		IIcon iconLidTop = ZapApples.jar.textureTop;
		IIcon iconSide = ZapApples.jar.textureSide;
		IIcon iconBottom = ZapApples.jar.textureBottom;
		IIcon iconLidEdge = ZapApples.jar.textureLidEdge;
		
		renderer.renderFaceYPos( block, x, y, z, iconLidTop );
		renderer.renderFaceYNeg( block, x, y + 0.8125F, z, iconLidTop );
		
		renderer.renderFaceZPos( block, x, y, z, iconLidEdge );
		renderer.renderFaceZNeg( block, x, y, z, iconLidEdge );
		renderer.renderFaceXPos( block, x, y, z, iconLidEdge );
		renderer.renderFaceXNeg( block, x, y, z, iconLidEdge );
		
		float var12 = 0.0625F;
		renderer.renderFaceZPos( block, x, y, z - var12, iconSide );
		renderer.renderFaceZNeg( block, x, y, z + var12, iconSide );
		renderer.renderFaceXNeg( block, x + var12, y, z, iconSide );
		renderer.renderFaceXPos( block, x - var12, y, z, iconSide );
		
		renderer.renderFaceYNeg( block, x, y, z, iconBottom );
		if ( tile != null )
		{
			IIcon fluidTexture = tile.getFluidTexture();
			if ( fluidTexture != null )
			{
				float fluidLevel = 0.0625F + 0.75F * (float) tile.getPercentFull();
				
				var12 *= 2.0F;
				renderer.setRenderBounds( var12, 0.0625D, var12, 1.0F - var12, fluidLevel, 1.0F - var12 );
				renderer.renderFaceXNeg( block, x, y, z, fluidTexture );
				renderer.renderFaceXPos( block, x, y, z, fluidTexture );
				renderer.renderFaceZNeg( block, x, y, z, fluidTexture );
				renderer.renderFaceZPos( block, x, y, z, fluidTexture );
				renderer.renderFaceYPos( block, x, y, z, fluidTexture );
				renderer.renderFaceYNeg( block, x, y, z, fluidTexture );
			}
		}
	}
}
