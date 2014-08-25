package com.chiorichan.ZapApples.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class Render2D implements ISimpleBlockRenderingHandler
{
	public Render2D()
	{
		com.chiorichan.ZapApples.ZapApples.idRender2D = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler( this );
	}
	
	public void renderInventoryBlock( Block block, int metadata, int modelID, RenderBlocks renderer )
	{
	}
	
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer )
	{
		return false;
	}
	
	@Override
	public boolean shouldRender3DInInventory( int arg0 )
	{
		return false;
	}
	
	public int getRenderId()
	{
		return 0;
	}
}
