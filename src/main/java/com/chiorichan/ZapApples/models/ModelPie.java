package com.chiorichan.ZapApples.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelPie extends ModelBase
{
	ModelRenderer rim;
	ModelRenderer base;
	ModelRenderer top;
	
	public ModelPie()
	{
		textureWidth = 256;
		textureHeight = 256;
		
		top = new ModelRenderer( this, 4, 208 );
		top.addBox( -4.0F, -4.0F, -4.0F, 6, 8, 6 );
		top.setRotationPoint( 4.0F, 4.0F, 4.0F );
		top.setTextureSize( 256, 256 );
		rim = new ModelRenderer( this, 32, 208 );
		rim.addBox( -4.0F, 0.0F, -4.0F, 8, 1, 8 );
		rim.setRotationPoint( 4.0F, 3.0F, 4.0F );
		rim.setTextureSize( 256, 256 );
		base = new ModelRenderer( this, 66, 208 );
		base.addBox( -4.0F, 0.0F, -4.0F, 7, 4, 7 );
		base.setRotationPoint( 4.0F, 4.0F, 4.0F );
		base.setTextureSize( 256, 256 );
	}
	
	public void renderAll()
	{
		rim.render( 0.0625F );
		base.render( 0.0625F );
		top.render( 0.0625F );
	}
}
