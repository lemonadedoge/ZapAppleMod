package com.chiorichan.ZapApples.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly( Side.CLIENT )
public class RenderTimberWolf extends RenderLiving
{
	private static final ResourceLocation texture = new ResourceLocation( "ZapApples:textures/entity/timber_wolf.png" );
	
	public RenderTimberWolf(ModelBase par1ModelBase, float par2)
	{
		super( par1ModelBase, par2 );
	}
	
	protected ResourceLocation getEntityTexture( Entity par1Entity )
	{
		return texture;
	}
}
