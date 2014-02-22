package com.jsn_man.ZapApples;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelPie extends ModelBase{
	
    ModelRenderer rim;
    ModelRenderer base;
    ModelRenderer top;
    
    public ModelPie(){
	    textureWidth = 256;
	    textureHeight = 256;
	    
	    top = new ModelRenderer(this, 4, 208);
	    top.addBox(-4F, -4F, -4F, 6, 8, 6);
	    top.setRotationPoint(4F, 4F, 4F);
	    top.setTextureSize(256, 256);
	    rim = new ModelRenderer(this, 32, 208);
	    rim.addBox(-4F, 0F, -4F, 8, 1, 8);
	    rim.setRotationPoint(4F, 3F, 4F);
	    rim.setTextureSize(256, 256);
	    base = new ModelRenderer(this, 66, 208);
	    base.addBox(-4F, 0F, -4F, 7, 4, 7);
	    base.setRotationPoint(4F, 4F, 4F);
	    base.setTextureSize(256, 256);
    }
    
    public void renderAll(){
    	rim.render(0.0625F);
    	base.render(0.0625F);
    	top.render(0.0625F);
    }
}