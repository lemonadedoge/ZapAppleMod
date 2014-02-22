package com.jsn_man.ZapApples;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class Render3D implements ISimpleBlockRenderingHandler{
	
	public Render3D(){
		ZapApples.idRender3D = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(this);
		
		pie = new ModelPie();
	}
	
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer){
		if(block.blockID == ZapApples.jar.blockID){
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderJar(null, -0.5F, -0.5F, -0.5F, block, renderer, metadata);
	        tessellator.draw();
		}else if(block.blockID == ZapApples.pie.blockID){
			if(metadata >= 2){
				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				block.setBlockBoundsForItemRender();
				renderer.updateCustomBlockBounds(block);
				
				ItemStack stack = new ItemStack(block, 1, metadata);
				short base = (short)(CakethingRegistry.getBaseInt(stack) - 2);
				short topping = (short)(CakethingRegistry.getToppingInt(stack) - 2);
				short side = (short)CakethingRegistry.getTextureIndexOfBase(3, base, true);
				renderer.renderSouthFace(block, -0.5F, -0.5F, -0.5F, side);
		        renderer.renderNorthFace(block, -0.5F, -0.5F, -0.5F, side);
		        renderer.renderWestFace(block, -0.5F, -0.5F, -0.5F, side);
		        renderer.renderEastFace(block, -0.5F, -0.5F, -0.5F, side);
		        renderer.renderBottomFace(block, -0.5F, -0.5F, -0.5F, CakethingRegistry.getTextureIndexOfBase(0, base, true));
		        
		        side = (short)CakethingRegistry.getTextureIndexOfTopping(3, topping, true);
		        renderer.renderSouthFace(block, -0.5F, -0.5F, -0.5F, side);
		        renderer.renderNorthFace(block, -0.5F, -0.5F, -0.5F, side);
		        renderer.renderWestFace(block, -0.5F, -0.5F, -0.5F, side);
		        renderer.renderEastFace(block, -0.5F, -0.5F, -0.5F, side);
		        renderer.renderTopFace(block, -0.5F, -0.5F, -0.5F, CakethingRegistry.getTextureIndexOfTopping(1, topping, true));
				
				tessellator.draw();
			}else{
				GL11.glPushMatrix();
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		        GL11.glScalef(1.0F, -1.0F, -1.0F);
		        
		        pie.renderAll();
	        	GL11.glRotatef(90F, 0F, 1F, 0F);
	        	pie.renderAll();
	        	GL11.glRotatef(180F, 0F, 1F, 0F);
	        	pie.renderAll();
	        	GL11.glRotatef(270F, 0F, 1F, 0F);
	        	pie.renderAll();
		        
		        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glPopMatrix();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}
	
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer){
		if(modelID == getRenderId()){
			if(block.blockID == ZapApples.jar.blockID){
				renderJar(world, x, y, z, block, renderer, world.getBlockMetadata(x, y, z));
			}else if(block.blockID == ZapApples.pie.blockID){
				TileEntityCakething tile = (TileEntityCakething)world.getBlockTileEntity(x, y, z);
				if(tile != null && tile.base >= 2){
					renderCakething(world, x, y, z, block, renderer);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean shouldRender3DInInventory(){
		return true;
	}
	
	public int getRenderId(){
		return ZapApples.idRender3D;
	}
	
	private void renderCakething(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer){
		BlockCakething cake = (BlockCakething)block;
		cake.setBlockBoundsBasedOnState(world, x, y, z);
		renderer.updateCustomBlockBounds(cake);
		//renderer.renderStandardBlock(cake, x, y, z);
		
		TileEntityCakething tile = (TileEntityCakething)world.getBlockTileEntity(x, y, z);
		if(tile != null){
			short side = (short)CakethingRegistry.getTextureIndexOfBase(3, tile.base - 2, tile.stage < 1);
			renderer.renderSouthFace(cake, (double)x, (double)y, (double)z, side);
	        renderer.renderNorthFace(cake, (double)x, (double)y, (double)z, CakethingRegistry.getTextureIndexOfBase(4, tile.base - 2, tile.stage < 1));
	        renderer.renderWestFace(cake, (double)x, (double)y, (double)z, side);
	        renderer.renderEastFace(cake, (double)x, (double)y, (double)z, side);
	        renderer.renderBottomFace(cake, (double)x, (double)y, (double)z, CakethingRegistry.getTextureIndexOfBase(0, tile.base - 2, tile.stage < 1));
	        
	        side = (short)CakethingRegistry.getTextureIndexOfTopping(3, tile.top - 2, tile.stage < 1);
	        renderer.renderSouthFace(cake, (double)x, (double)y, (double)z, side);
	        renderer.renderNorthFace(cake, (double)x, (double)y, (double)z, CakethingRegistry.getTextureIndexOfTopping(4, tile.top - 2, tile.stage < 1));
	        renderer.renderWestFace(cake, (double)x, (double)y, (double)z, side);
	        renderer.renderEastFace(cake, (double)x, (double)y, (double)z, side);
	        renderer.renderTopFace(cake, (double)x, (double)y, (double)z, CakethingRegistry.getTextureIndexOfTopping(1, tile.top - 2, tile.stage < 1));
		}
	}
	
	private void renderJar(IBlockAccess world, float x, float y, float z, Block block, RenderBlocks renderer, int meta){
		Tessellator var5 = Tessellator.instance;
		float var12;
		if(world != null){
	        var5.setBrightness(block.getMixedBrightnessForBlock(world, (int)x, (int)y, (int)z));
	        float var6 = 1.0F;
	        int var7 = block.colorMultiplier(world, (int)x, (int)y, (int)z);
	        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
	        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
	        float var10 = (float)(var7 & 255) / 255.0F;
	
	        if (EntityRenderer.anaglyphEnable)
	        {
	            float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
	            var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
	            float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
	            var8 = var11;
	            var9 = var12;
	            var10 = var13;
	        }
	        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
		}
		
        short lid = (short)block.blockIndexInTexture;
        renderer.renderTopFace(block, (double)x, (double)y, (double)z, lid);
        renderer.renderBottomFace(block, (double)x, (double)((float)y + 0.8125F), (double)z, lid);
        
        renderer.renderSouthFace(block, (double)x, (double)y, (double)z, --lid);
        renderer.renderNorthFace(block, (double)x, (double)y, (double)z, lid);
        renderer.renderWestFace(block, (double)x, (double)y, (double)z, lid);
        renderer.renderEastFace(block, (double)x, (double)y, (double)z, lid);
        
        var12 = 0.0625F;
        renderer.renderSouthFace(block, (double)((float)x - var12), (double)y, (double)z, --lid);
        renderer.renderNorthFace(block, (double)((float)x + var12), (double)y, (double)z, lid);
        renderer.renderWestFace(block, (double)x, (double)y, (double)((float)z - var12), lid);
        renderer.renderEastFace(block, (double)x, (double)y, (double)((float)z + var12), lid);
        
        renderer.renderBottomFace(block, (double)x, (double)y, (double)z, lid + 3);
        
        if(meta == 1){
        	var12 *= 2;
        	lid = (short)block.getBlockTextureFromSideAndMetadata(2, 1);
        	renderer.setRenderMinMax(var12, 0.0625F, var12, 1F - var12, 0.8125F, 1F - var12);
        	renderer.renderSouthFace(block, (double)x, (double)y, (double)z, lid);
	        renderer.renderNorthFace(block, (double)x, (double)y, (double)z, lid);
	        renderer.renderWestFace(block, (double)x, (double)y, (double)z, lid);
	        renderer.renderEastFace(block, (double)x, (double)y, (double)z, lid);
	        
	        renderer.renderTopFace(block, (double)x, (double)y, (double)z, block.getBlockTextureFromSideAndMetadata(1, 1));
	        renderer.renderBottomFace(block, (double)x, (double)y, (double)z, block.getBlockTextureFromSideAndMetadata(0, 1));
        }
	}
	
	private ModelPie pie;
}