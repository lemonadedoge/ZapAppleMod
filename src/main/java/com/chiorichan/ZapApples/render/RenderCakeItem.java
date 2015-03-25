package com.chiorichan.ZapApples.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.chiorichan.ZapApples.EasyTess;
import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.blocks.BlockCake.CakeIngredientMap;

public class RenderCakeItem implements IItemRenderer
{
	@Override
	public boolean handleRenderType( ItemStack itemStack, ItemRenderType type )
	{
		return type == ItemRenderType.INVENTORY;
	}
	
	@Override
	public boolean shouldUseRenderHelper( ItemRenderType type, ItemStack item, ItemRendererHelper helper )
	{
		return false;
	}
	
	@Override
	public void renderItem( ItemRenderType type, ItemStack itemStack, Object... data )
	{
		RenderBlocks renderer = (RenderBlocks) data[0];
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setNormal( 0.0F, -1.0F, 0.0F );
		
		Block block = ZapApples.cake;
		String base = "2";
		String frosting = "6";
		
		GL11.glPushMatrix();
		//GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		
		CakeIngredientMap ingred = ZapApples.cake.getBaseIngredient( base ) == null ? ZapApples.cake.getBaseIngredient( "plain" ) : ZapApples.cake.getBaseIngredient( base );
		IIcon icon = ingred.getIcon( 3, true );
		//renderer.renderFaceZNeg( block, -0.5D, -0.5D, -0.5D, icon );
		//renderer.renderFaceZPos( block, -0.5D, -0.5D, -0.5D, icon );
		//renderer.renderFaceXNeg( block, -0.5D, -0.5D, -0.5D, ingred.getIcon( 4, true ) );
		//renderer.renderFaceXPos( block, -0.5D, -0.5D, -0.5D, icon );
		//renderer.renderFaceYNeg( block, -0.5D, -0.5D, -0.5D, ingred.getIcon( 0, true ) );
		
		ingred = ZapApples.cake.getFrostIngredient( frosting ) == null ? ZapApples.cake.getFrostIngredient( "plain" ) : ZapApples.cake.getFrostIngredient( frosting );
		icon = ingred.getIcon( 3, true );
		//renderer.renderFaceZNeg( block, -0.5D, -0.5D, -0.5D, icon );
		//renderer.renderFaceZPos( block, -0.5D, -0.5D, -0.5D, icon );
		//renderer.renderFaceXNeg( block, -0.5D, -0.5D, -0.5D, ingred.getIcon( 4, true ) );
		//renderer.renderFaceXPos( block, -0.5D, -0.5D, -0.5D, icon );
		//renderer.renderFaceYPos( block, -0.5D, -0.5D, -0.5D, ingred.getIcon( 1, true ) );
		
		EasyTess.renderCube( 0, 0, 0, 500, 500, 500, 0, 0, icon, 0 );
		
		tessellator.draw();
		
		GL11.glPopMatrix();
	}
}
