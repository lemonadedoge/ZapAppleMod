package com.jsn_man.ZapApples;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class Render2D implements ISimpleBlockRenderingHandler{
	
	public Render2D(){
		ZapApples.idRender2D = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(this);
	}
	
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer){
		
	}
	
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer){
		if(modelID == getRenderId()){
			if(block.blockID == ZapApples.industrialCauldron.blockID){
				renderIndustrialCauldron(world, x, y, z, block, renderer);
			}
			return true;
		}
		return false;
	}
	
	public boolean shouldRender3DInInventory(){
		return false;
	}
	
	public int getRenderId(){
		return ZapApples.idRender2D;
	}
	
	private void renderIndustrialCauldron(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer){
		BlockIndustrialCauldron cauldron = (BlockIndustrialCauldron)block;
		renderer.renderStandardBlock(cauldron, x, y, z);
		Tessellator var5 = Tessellator.instance;
        var5.setBrightness(cauldron.getMixedBrightnessForBlock(world, x, y, z));
        float var6 = 1.0F;
        int var7 = cauldron.colorMultiplier(world, x, y, z);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;
        float var12;

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
        int meta = world.getBlockMetadata(x, y, z);
        short side = (short)(cauldron.blockIndexInTexture + 2);
        var12 = 0.0625F;
        
        if(meta != 2 && meta != 4 && meta != 7 && meta != 9){
        	renderer.renderSouthFace(cauldron, (double)((float)x - 1.0F + var12), (double)y, (double)z, side);
        }
        
        if(meta != 3 && meta != 1 && meta != 8 && meta != 6){
        	renderer.renderNorthFace(cauldron, (double)((float)x + 1.0F - var12), (double)y, (double)z, side);
        }
        
        if(meta != 3 && meta != 4 && meta != 8 && meta != 9){
        	renderer.renderWestFace(cauldron, (double)x, (double)y, (double)((float)z - 1.0F + var12), side);
        }
        
        if(meta != 1 && meta != 2 && meta != 6 && meta != 7){
        	renderer.renderEastFace(cauldron, (double)x, (double)y, (double)((float)z + 1.0F - var12), side);
        }
        
        if(meta < 5){
	        short bottom = (short)cauldron.blockIndexInTexture;
	        renderer.renderTopFace(cauldron, (double)x, (double)((float)y - 0.875F), (double)z, bottom);
	        renderer.renderBottomFace(cauldron, (double)x, (double)((float)y + 0.125F), (double)z, bottom);
        }
        
        TileEntityIndustrialCauldron tile = (TileEntityIndustrialCauldron)world.getBlockTileEntity(x, y, z);
        if(tile != null){
	        if(tile.content != null){
	        	if(tile.content.itemID < Block.blocksList.length && Block.blocksList[tile.content.itemID] != null && renderer.overrideBlockTexture == -1){
	        		Block b = Block.blocksList[tile.content.itemID];
	        		ForgeHooksClient.bindTexture(b.getTextureFile(), 0);
	        		renderer.setOverrideBlockTexture(b.getBlockTextureFromSideAndMetadata(0, tile.content.getItemDamage()));
	        	}else if(Item.itemsList[tile.content.itemID] != null && renderer.overrideBlockTexture == -1){
	        		Item i = Item.itemsList[tile.content.itemID];
	        		ForgeHooksClient.bindTexture(i.getTextureFile(), 0);
	        		renderer.setOverrideBlockTexture(i.getIconFromDamage(tile.content.getItemDamage()));
	        	}
				renderer.drawCrossedSquares(cauldron, tile.content.getItemDamage(), x, y, z, 1F);
				renderer.clearOverrideBlockTexture();
			}
	        if(tile.tank.getLiquid() != null){
	        	boolean shouldRenderTop = true;
	        	if(world.getBlockId(x, y + 1, z) == cauldron.blockID){
	        		TileEntityIndustrialCauldron t2 = (TileEntityIndustrialCauldron)world.getBlockTileEntity(x, y + 1, z);
	        		if(t2 != null && t2.tank.getLiquid() != null && t2.tank.getLiquid().itemID == tile.tank.getLiquid().itemID){
	        			shouldRenderTop = false;
	        		}
	        	}
	        	
	        	short index = 0;
	        	if(tile.tank.getLiquid().itemID < Block.blocksList.length && Block.blocksList[tile.tank.getLiquid().itemID] != null && renderer.overrideBlockTexture == -1){
	        		Block b = Block.blocksList[tile.tank.getLiquid().itemID];
	        		ForgeHooksClient.bindTexture(b.getTextureFile(), 0);
	        		index = (short)b.getBlockTextureFromSideAndMetadata(1, tile.tank.getLiquid().itemMeta);
	        		side = (short)b.getBlockTextureFromSideAndMetadata(2, tile.tank.getLiquid().itemMeta);
	        	}else if(Item.itemsList[tile.tank.getLiquid().itemID] != null && renderer.overrideBlockTexture == -1){
	        		Item i = Item.itemsList[tile.tank.getLiquid().itemID];
	        		ForgeHooksClient.bindTexture(i.getTextureFile(), 0);
	        		side = index = (short)i.getIconFromDamage(tile.tank.getLiquid().itemMeta);
	        	}
	        	
	        	if(shouldRenderTop){
	        		float point = (float)tile.tank.getLiquid().amount / (float)tile.tank.getCapacity();
		        	if(point > 0.96875F){
		        		point = 0.96875F;
		        	}else if(point < 0.125F){
		        		point = 0.125F;
		        	}
		        	
		        	renderer.renderTopFace(cauldron, (double)x, (double)((float)y -1F + point), (double)z, index);
		        	renderer.renderBottomFace(cauldron, (double)x, (double)((float)y + point), (double)z, index);
		        	
		        	if(point >= 0.96875F){
			        	if(meta != 2 && meta != 4 && meta != 7 && meta != 9){
				        	renderer.renderSouthFace(cauldron, (double)((float)x - 1.0F + var12), (double)y, (double)z, side);
				        }
				        
				        if(meta != 3 && meta != 1 && meta != 8 && meta != 6){
				        	renderer.renderNorthFace(cauldron, (double)((float)x + 1.0F - var12), (double)y, (double)z, side);
				        }
				        
				        if(meta != 3 && meta != 4 && meta != 8 && meta != 9){
				        	renderer.renderWestFace(cauldron, (double)x, (double)y, (double)((float)z - 1.0F + var12), side);
				        }
				        
				        if(meta != 1 && meta != 2 && meta != 6 && meta != 7){
				        	renderer.renderEastFace(cauldron, (double)x, (double)y, (double)((float)z + 1.0F - var12), side);
				        }
			       }
	        	}else{
	        		if(meta != 2 && meta != 4 && meta != 7 && meta != 9){
			        	renderer.renderSouthFace(cauldron, (double)((float)x - 1.0F + var12), (double)y, (double)z, side);
			        }
			        
			        if(meta != 3 && meta != 1 && meta != 8 && meta != 6){
			        	renderer.renderNorthFace(cauldron, (double)((float)x + 1.0F - var12), (double)y, (double)z, side);
			        }
			        
			        if(meta != 3 && meta != 4 && meta != 8 && meta != 9){
			        	renderer.renderWestFace(cauldron, (double)x, (double)y, (double)((float)z - 1.0F + var12), side);
			        }
			        
			        if(meta != 1 && meta != 2 && meta != 6 && meta != 7){
			        	renderer.renderEastFace(cauldron, (double)x, (double)y, (double)((float)z + 1.0F - var12), side);
			        }
	        	}
	        }
        }
	}
}