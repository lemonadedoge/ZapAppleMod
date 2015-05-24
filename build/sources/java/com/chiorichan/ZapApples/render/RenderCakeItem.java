package com.chiorichan.ZapApples.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.blocks.BlockCake.CakeIngredientMap;

public class RenderCakeItem implements IItemRenderer
{
	public enum TransformationTypes
	{
		NONE, DROPPED, INVENTORY;
	}
	
	static boolean wrongRendererMsgWritten = false;
	
	@Override
	public boolean handleRenderType( ItemStack itemStack, ItemRenderType type )
	{
		switch ( type )
		{
			case ENTITY:
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON:
			case INVENTORY:
				return true;
			default:
				return false;
		}
	}
	
	@Override
	public boolean shouldUseRenderHelper( ItemRenderType type, ItemStack item, ItemRendererHelper helper )
	{
		switch ( type )
		{
			case ENTITY:
			{
				return ( helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.BLOCK_3D );
			}
			case EQUIPPED:
			{
				return ( helper == ItemRendererHelper.BLOCK_3D || helper == ItemRendererHelper.EQUIPPED_BLOCK );
			}
			case EQUIPPED_FIRST_PERSON:
			{
				return helper == ItemRendererHelper.EQUIPPED_BLOCK;
			}
			case INVENTORY:
			{
				return helper == ItemRendererHelper.INVENTORY_BLOCK;
			}
			default:
			{
				return false;
			}
		}
	}
	
	@Override
	public void renderItem( ItemRenderType type, ItemStack item, Object... data )
	{
		// RenderBlocks renderer = (RenderBlocks) data[0];
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		
		TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
		switch ( type )
		{
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON:
			{
				break; // caller expects us to render over [0,0,0] to [1,1,1], no transformation necessary
			}
			case INVENTORY:
			{ // caller expects [-0.5, -0.5, -0.5] to [0.5, 0.5, 0.5]
				GL11.glTranslatef( -0.5F, -0.5F, -0.5F );
				break;
			}
			case ENTITY:
			{
				// translate our coordinates and scale so that [0,0,0] to [1,1,1] translates to the [-0.25, -0.25, -0.25] to [0.25, 0.25, 0.25] expected by the caller.
				// GL11.glScalef( 0.5F, 0.5F, 0.5F );
				GL11.glTranslatef( -0.5F, -0.5F, -0.5F );
				transformationToBeUndone = TransformationTypes.DROPPED;
				break;
			}
			default:
				break; // never here
		}
		
		NBTTagCompound tag = item.getTagCompound();
		String base;
		String frosting;
		if ( ( tag != null ) && ( tag.hasKey( "Cake" ) ) )
		{
			NBTTagCompound cake = tag.getCompoundTag( "Cake" );
			base = cake.getString( "base" );
			frosting = cake.getString( "frost" );
		}
		else
		{
			base = "2";
			frosting = "6";
		}
		
		CakeIngredientMap baseIcons = ZapApples.cake.getBaseIngredient( base ) == null ? ZapApples.cake.getBaseIngredient( "plain" ) : ZapApples.cake.getBaseIngredient( base );
		CakeIngredientMap frontingIcons = ZapApples.cake.getFrostIngredient( frosting ) == null ? ZapApples.cake.getFrostIngredient( "plain" ) : ZapApples.cake.getFrostIngredient( frosting );
		
		double pullInAmt = 0.938;
		
		// Base Rendering
		
		// xpos face
		IIcon icon = baseIcons.getIcon( 3, true );
		tessellator.setNormal( 1.0F, 0.0F, 0.0F );
		tessellator.addVertexWithUV( pullInAmt, 0.0, 0.0, ( double ) icon.getMaxU(), ( double ) icon.getMaxV() );
		tessellator.addVertexWithUV( pullInAmt, 1.0, 0.0, ( double ) icon.getMaxU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( pullInAmt, 1.0, 1.0, ( double ) icon.getMinU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( pullInAmt, 0.0, 1.0, ( double ) icon.getMinU(), ( double ) icon.getMaxV() );
		
		// xneg face
		tessellator.setNormal( -1.0F, 0.0F, 0.0F );
		tessellator.addVertexWithUV( 1 - pullInAmt, 0.0, 1.0, ( double ) icon.getMaxU(), ( double ) icon.getMaxV() );
		tessellator.addVertexWithUV( 1 - pullInAmt, 1.0, 1.0, ( double ) icon.getMaxU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( 1 - pullInAmt, 1.0, 0.0, ( double ) icon.getMinU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( 1 - pullInAmt, 0.0, 0.0, ( double ) icon.getMinU(), ( double ) icon.getMaxV() );
		
		// zpos face
		tessellator.setNormal( 0.0F, 0.0F, -1.0F );
		tessellator.addVertexWithUV( 1.0, 0.0, pullInAmt, ( double ) icon.getMaxU(), ( double ) icon.getMaxV() );
		tessellator.addVertexWithUV( 1.0, 1.0, pullInAmt, ( double ) icon.getMaxU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( 0.0, 1.0, pullInAmt, ( double ) icon.getMinU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( 0.0, 0.0, pullInAmt, ( double ) icon.getMinU(), ( double ) icon.getMaxV() );
		
		// zneg face
		tessellator.setNormal( 0.0F, 0.0F, -1.0F );
		tessellator.addVertexWithUV( 0.0, 0.0, 1 - pullInAmt, ( double ) icon.getMaxU(), ( double ) icon.getMaxV() );
		tessellator.addVertexWithUV( 0.0, 1.0, 1 - pullInAmt, ( double ) icon.getMaxU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( 1.0, 1.0, 1 - pullInAmt, ( double ) icon.getMinU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( 1.0, 0.0, 1 - pullInAmt, ( double ) icon.getMinU(), ( double ) icon.getMaxV() );
		
		// yneg face
		icon = baseIcons.getIcon( 0, true );
		tessellator.setNormal( 0.0F, -1.0F, 0.0F );
		tessellator.addVertexWithUV( 0.0, 0.0, 1.0, ( double ) icon.getMaxU(), ( double ) icon.getMaxV() );
		tessellator.addVertexWithUV( 0.0, 0.0, 0.0, ( double ) icon.getMaxU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( 1.0, 0.0, 0.0, ( double ) icon.getMinU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( 1.0, 0.0, 1.0, ( double ) icon.getMinU(), ( double ) icon.getMaxV() );
		
		// Frosting Rendering
		
		icon = frontingIcons.getIcon( 3, true );
		
		if ( frosting != null && !"plain".equalsIgnoreCase( frosting ) )
		{
			tessellator.setNormal( 1.0F, 0.0F, 0.0F );
			tessellator.addVertexWithUV( pullInAmt, 0.0, 0.0, ( double ) icon.getMaxU(), ( double ) icon.getMaxV() );
			tessellator.addVertexWithUV( pullInAmt, 1.0, 0.0, ( double ) icon.getMaxU(), ( double ) icon.getMinV() );
			tessellator.addVertexWithUV( pullInAmt, 1.0, 1.0, ( double ) icon.getMinU(), ( double ) icon.getMinV() );
			tessellator.addVertexWithUV( pullInAmt, 0.0, 1.0, ( double ) icon.getMinU(), ( double ) icon.getMaxV() );
			
			// xneg face
			tessellator.setNormal( -1.0F, 0.0F, 0.0F );
			tessellator.addVertexWithUV( 1 - pullInAmt, 0.0, 1.0, ( double ) icon.getMaxU(), ( double ) icon.getMaxV() );
			tessellator.addVertexWithUV( 1 - pullInAmt, 1.0, 1.0, ( double ) icon.getMaxU(), ( double ) icon.getMinV() );
			tessellator.addVertexWithUV( 1 - pullInAmt, 1.0, 0.0, ( double ) icon.getMinU(), ( double ) icon.getMinV() );
			tessellator.addVertexWithUV( 1 - pullInAmt, 0.0, 0.0, ( double ) icon.getMinU(), ( double ) icon.getMaxV() );
			
			// zpos face
			tessellator.setNormal( 0.0F, 0.0F, -1.0F );
			tessellator.addVertexWithUV( 1.0, 0.0, pullInAmt, ( double ) icon.getMaxU(), ( double ) icon.getMaxV() );
			tessellator.addVertexWithUV( 1.0, 1.0, pullInAmt, ( double ) icon.getMaxU(), ( double ) icon.getMinV() );
			tessellator.addVertexWithUV( 0.0, 1.0, pullInAmt, ( double ) icon.getMinU(), ( double ) icon.getMinV() );
			tessellator.addVertexWithUV( 0.0, 0.0, pullInAmt, ( double ) icon.getMinU(), ( double ) icon.getMaxV() );
			
			// zneg face
			tessellator.setNormal( 0.0F, 0.0F, -1.0F );
			tessellator.addVertexWithUV( 0.0, 0.0, 1 - pullInAmt, ( double ) icon.getMaxU(), ( double ) icon.getMaxV() );
			tessellator.addVertexWithUV( 0.0, 1.0, 1 - pullInAmt, ( double ) icon.getMaxU(), ( double ) icon.getMinV() );
			tessellator.addVertexWithUV( 1.0, 1.0, 1 - pullInAmt, ( double ) icon.getMinU(), ( double ) icon.getMinV() );
			tessellator.addVertexWithUV( 1.0, 0.0, 1 - pullInAmt, ( double ) icon.getMinU(), ( double ) icon.getMaxV() );
		}
		
		// ypos face
		icon = frontingIcons.getIcon( 1, true );
		tessellator.setNormal( 0.0F, 1.0F, 0.0F );
		tessellator.addVertexWithUV( 1.0, 0.5, 1.0, ( double ) icon.getMaxU(), ( double ) icon.getMaxV() );
		tessellator.addVertexWithUV( 1.0, 0.5, 0.0, ( double ) icon.getMaxU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( 0.0, 0.5, 0.0, ( double ) icon.getMinU(), ( double ) icon.getMinV() );
		tessellator.addVertexWithUV( 0.0, 0.5, 1.0, ( double ) icon.getMinU(), ( double ) icon.getMaxV() );
		
		tessellator.draw();
		
		switch ( transformationToBeUndone )
		{
			case NONE:
			{
				break;
			}
			case DROPPED:
			{
				GL11.glTranslatef( 0.5F, 0.5F, 0.0F );
				// GL11.glScalef( 2.0F, 2.0F, 2.0F );
				break;
			}
			case INVENTORY:
			{
				GL11.glTranslatef( 0.5F, 0.5F, 0.5F );
				break;
			}
			default:
				break;
		}
	}
}
