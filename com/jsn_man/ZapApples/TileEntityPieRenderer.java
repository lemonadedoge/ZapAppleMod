package com.jsn_man.ZapApples;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityPieRenderer extends TileEntitySpecialRenderer{
	
	public TileEntityPieRenderer(){
		pie = new ModelPie();
	}
	
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8){
		renderTileEntityPieAt((TileEntityCakething)tile, par2, par4, par6, par8);
	}
	
	public void renderTileEntityPieAt(TileEntityCakething tile, double par2, double par4, double par6, float par8){
		bindTextureByName(ZapApples.cakeTextures);
		
		if(tile.base > 1){
			return;
		}
		
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        
        
        if(tile.stage < 4){
        	pie.renderAll();
        }
        if(tile.stage < 3){
        	GL11.glRotatef(90F, 0F, 1F, 0F);
        	pie.renderAll();
        }
        if(tile.stage < 2){
        	GL11.glRotatef(180F, 0F, 1F, 0F);
        	pie.renderAll();
        }
        if(tile.stage < 1){
        	GL11.glRotatef(270F, 0F, 1F, 0F);
        	pie.renderAll();
        }
        
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private ModelPie pie;
}