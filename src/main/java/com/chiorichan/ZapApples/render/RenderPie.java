package com.chiorichan.ZapApples.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.chiorichan.ZapApples.models.ModelPie;
import com.chiorichan.ZapApples.tileentity.TileEntityPie;

public class RenderPie extends TileEntitySpecialRenderer
{
	private ModelPie pie;
	
	public RenderPie()
	{
		pie = new ModelPie();
	}
	
	public void renderTileEntityAt( TileEntity tileentity, double x, double y, double z, float f )
	{
		TileEntityPie tile = (TileEntityPie) tileentity;
		
		ResourceLocation textures = new ResourceLocation( "zapapples:textures/blocks/cakes.png" );
		Minecraft.getMinecraft().renderEngine.bindTexture( textures );
		
		GL11.glPushMatrix();
		GL11.glEnable( 32826 );
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
		GL11.glTranslatef( (float) x, (float) y + 1.0F, (float) z + 1.0F );
		GL11.glScalef( 1.0F, -1.0F, -1.0F );
		GL11.glTranslatef( 0.5F, 0.5F, 0.5F );
		
		if ( tile.stage < 4 )
		{
			pie.renderAll();
		}
		if ( tile.stage < 3 )
		{
			GL11.glRotatef( 90.0F, 0.0F, 1.0F, 0.0F );
			pie.renderAll();
		}
		if ( tile.stage < 2 )
		{
			GL11.glRotatef( 180.0F, 0.0F, 1.0F, 0.0F );
			pie.renderAll();
		}
		if ( tile.stage < 1 )
		{
			GL11.glRotatef( 270.0F, 0.0F, 1.0F, 0.0F );
			pie.renderAll();
		}
		
		GL11.glDisable( 32826 );
		GL11.glPopMatrix();
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
	}
}
