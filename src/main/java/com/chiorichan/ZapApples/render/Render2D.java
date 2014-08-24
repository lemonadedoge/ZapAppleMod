package com.chiorichan.ZapApples.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;

public class Render2D
  implements ISimpleBlockRenderingHandler
{
  public Render2D()
  {
    com.chiorichan.ZapApples.ZapApples.idRender2D = RenderingRegistry.getNextAvailableRenderId();
    RenderingRegistry.registerBlockHandler(this);
  }

  public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
  {
  }

  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
  {
    return false;
  }

  public boolean shouldRender3DInInventory()
  {
    return false;
  }

  public int getRenderId()
  {
    return 0;
  }
}