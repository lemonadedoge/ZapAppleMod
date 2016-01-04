package com.chiorichan.ZapApples.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.tileentity.TileEntityJar;

public class RenderJarItem implements IItemRenderer
{
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
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		boolean undo = false;
		
		if ( type == ItemRenderType.INVENTORY )
			GL11.glTranslatef( -0.5F, -0.5F, -0.5F );
		
		if ( type == ItemRenderType.ENTITY )
		{
			GL11.glTranslatef( -0.5F, -0.5F, -0.5F );
			undo = true;
		}
		
		double pullInAmt = 0.938;
		
		// Glass Jar Rendering
		
		IIcon iconSide = ZapApples.jar.textureSide;
		
		// xpos face
		tessellator.setNormal( 1.0F, 0.0F, 0.0F );
		tessellator.addVertexWithUV( pullInAmt, 0.0, 0.0, ( double ) iconSide.getMaxU(), ( double ) iconSide.getMaxV() );
		tessellator.addVertexWithUV( pullInAmt, 1.0, 0.0, ( double ) iconSide.getMaxU(), ( double ) iconSide.getMinV() );
		tessellator.addVertexWithUV( pullInAmt, 1.0, 1.0, ( double ) iconSide.getMinU(), ( double ) iconSide.getMinV() );
		tessellator.addVertexWithUV( pullInAmt, 0.0, 1.0, ( double ) iconSide.getMinU(), ( double ) iconSide.getMaxV() );
		
		// xneg face
		tessellator.setNormal( -1.0F, 0.0F, 0.0F );
		tessellator.addVertexWithUV( 1 - pullInAmt, 0.0, 1.0, ( double ) iconSide.getMaxU(), ( double ) iconSide.getMaxV() );
		tessellator.addVertexWithUV( 1 - pullInAmt, 1.0, 1.0, ( double ) iconSide.getMaxU(), ( double ) iconSide.getMinV() );
		tessellator.addVertexWithUV( 1 - pullInAmt, 1.0, 0.0, ( double ) iconSide.getMinU(), ( double ) iconSide.getMinV() );
		tessellator.addVertexWithUV( 1 - pullInAmt, 0.0, 0.0, ( double ) iconSide.getMinU(), ( double ) iconSide.getMaxV() );
		
		// zpos face
		tessellator.setNormal( 0.0F, 0.0F, -1.0F );
		tessellator.addVertexWithUV( 1.0, 0.0, pullInAmt, ( double ) iconSide.getMaxU(), ( double ) iconSide.getMaxV() );
		tessellator.addVertexWithUV( 1.0, 1.0, pullInAmt, ( double ) iconSide.getMaxU(), ( double ) iconSide.getMinV() );
		tessellator.addVertexWithUV( 0.0, 1.0, pullInAmt, ( double ) iconSide.getMinU(), ( double ) iconSide.getMinV() );
		tessellator.addVertexWithUV( 0.0, 0.0, pullInAmt, ( double ) iconSide.getMinU(), ( double ) iconSide.getMaxV() );
		
		// zneg face
		tessellator.setNormal( 0.0F, 0.0F, -1.0F );
		tessellator.addVertexWithUV( 0.0, 0.0, 1 - pullInAmt, ( double ) iconSide.getMaxU(), ( double ) iconSide.getMaxV() );
		tessellator.addVertexWithUV( 0.0, 1.0, 1 - pullInAmt, ( double ) iconSide.getMaxU(), ( double ) iconSide.getMinV() );
		tessellator.addVertexWithUV( 1.0, 1.0, 1 - pullInAmt, ( double ) iconSide.getMinU(), ( double ) iconSide.getMinV() );
		tessellator.addVertexWithUV( 1.0, 0.0, 1 - pullInAmt, ( double ) iconSide.getMinU(), ( double ) iconSide.getMaxV() );
		
		IIcon iconBottom = ZapApples.jar.textureBottom;
		
		// yneg face
		tessellator.setNormal( 0.0F, -1.0F, 0.0F );
		tessellator.addVertexWithUV( 0.0, 0.0, 1.0, ( double ) iconBottom.getMaxU(), ( double ) iconBottom.getMaxV() );
		tessellator.addVertexWithUV( 0.0, 0.0, 0.0, ( double ) iconBottom.getMaxU(), ( double ) iconBottom.getMinV() );
		tessellator.addVertexWithUV( 1.0, 0.0, 0.0, ( double ) iconBottom.getMinU(), ( double ) iconBottom.getMinV() );
		tessellator.addVertexWithUV( 1.0, 0.0, 1.0, ( double ) iconBottom.getMinU(), ( double ) iconBottom.getMaxV() );
		
		// Lid Rendering
		
		IIcon iconLidEdge = ZapApples.jar.textureLidEdge;
		
		// xpos face
		tessellator.setNormal( 1.0F, 0.0F, 0.0F );
		tessellator.addVertexWithUV( 1.0, 0.0, 0.0, ( double ) iconLidEdge.getMaxU(), ( double ) iconLidEdge.getMaxV() );
		tessellator.addVertexWithUV( 1.0, 1.0, 0.0, ( double ) iconLidEdge.getMaxU(), ( double ) iconLidEdge.getMinV() );
		tessellator.addVertexWithUV( 1.0, 1.0, 1.0, ( double ) iconLidEdge.getMinU(), ( double ) iconLidEdge.getMinV() );
		tessellator.addVertexWithUV( 1.0, 0.0, 1.0, ( double ) iconLidEdge.getMinU(), ( double ) iconLidEdge.getMaxV() );
		
		// xneg face
		tessellator.setNormal( -1.0F, 0.0F, 0.0F );
		tessellator.addVertexWithUV( 0.0, 0.0, 1.0, ( double ) iconLidEdge.getMaxU(), ( double ) iconLidEdge.getMaxV() );
		tessellator.addVertexWithUV( 0.0, 1.0, 1.0, ( double ) iconLidEdge.getMaxU(), ( double ) iconLidEdge.getMinV() );
		tessellator.addVertexWithUV( 0.0, 1.0, 0.0, ( double ) iconLidEdge.getMinU(), ( double ) iconLidEdge.getMinV() );
		tessellator.addVertexWithUV( 0.0, 0.0, 0.0, ( double ) iconLidEdge.getMinU(), ( double ) iconLidEdge.getMaxV() );
		
		// zpos face
		tessellator.setNormal( 0.0F, 0.0F, -1.0F );
		tessellator.addVertexWithUV( 1.0, 0.0, 1.0, ( double ) iconLidEdge.getMaxU(), ( double ) iconLidEdge.getMaxV() );
		tessellator.addVertexWithUV( 1.0, 1.0, 1.0, ( double ) iconLidEdge.getMaxU(), ( double ) iconLidEdge.getMinV() );
		tessellator.addVertexWithUV( 0.0, 1.0, 1.0, ( double ) iconLidEdge.getMinU(), ( double ) iconLidEdge.getMinV() );
		tessellator.addVertexWithUV( 0.0, 0.0, 1.0, ( double ) iconLidEdge.getMinU(), ( double ) iconLidEdge.getMaxV() );
		
		// zneg face
		tessellator.setNormal( 0.0F, 0.0F, -1.0F );
		tessellator.addVertexWithUV( 0.0, 0.0, 0.0, ( double ) iconLidEdge.getMaxU(), ( double ) iconLidEdge.getMaxV() );
		tessellator.addVertexWithUV( 0.0, 1.0, 0.0, ( double ) iconLidEdge.getMaxU(), ( double ) iconLidEdge.getMinV() );
		tessellator.addVertexWithUV( 1.0, 1.0, 0.0, ( double ) iconLidEdge.getMinU(), ( double ) iconLidEdge.getMinV() );
		tessellator.addVertexWithUV( 1.0, 0.0, 0.0, ( double ) iconLidEdge.getMinU(), ( double ) iconLidEdge.getMaxV() );
		
		IIcon iconLidTop = ZapApples.jar.textureTop;
		
		// ypos face
		tessellator.setNormal( 0.0F, -1.0F, 0.0F );
		tessellator.addVertexWithUV( 0.0, 0.8215, 1.0, ( double ) iconLidTop.getMaxU(), ( double ) iconLidTop.getMaxV() );
		tessellator.addVertexWithUV( 0.0, 0.8215, 0.0, ( double ) iconLidTop.getMaxU(), ( double ) iconLidTop.getMinV() );
		tessellator.addVertexWithUV( 1.0, 0.8215, 0.0, ( double ) iconLidTop.getMinU(), ( double ) iconLidTop.getMinV() );
		tessellator.addVertexWithUV( 1.0, 0.8215, 1.0, ( double ) iconLidTop.getMinU(), ( double ) iconLidTop.getMaxV() );
		
		// yneg face
		tessellator.setNormal( 0.0F, 1.0F, 0.0F );
		tessellator.addVertexWithUV( 1.0, 1.0, 1.0, ( double ) iconLidTop.getMaxU(), ( double ) iconLidTop.getMaxV() );
		tessellator.addVertexWithUV( 1.0, 1.0, 0.0, ( double ) iconLidTop.getMaxU(), ( double ) iconLidTop.getMinV() );
		tessellator.addVertexWithUV( 0.0, 1.0, 0.0, ( double ) iconLidTop.getMinU(), ( double ) iconLidTop.getMinV() );
		tessellator.addVertexWithUV( 0.0, 1.0, 1.0, ( double ) iconLidTop.getMinU(), ( double ) iconLidTop.getMaxV() );
		
		// Fluid Rendering
		
		NBTTagCompound tag = item.getTagCompound();
		if ( ( tag != null ) && ( tag.hasKey( "Jar" ) ) )
		{
			NBTTagCompound nbt = tag.getCompoundTag( "Jar" );
			if ( !nbt.hasKey( "Empty" ) )
			{
				FluidStack fluid = FluidStack.loadFluidStackFromNBT( nbt );
				IIcon fluidTexture = fluid.getFluid().getIcon();
				
				if ( fluidTexture != null )
				{
					float fluidLevel = 0.0625F + 0.75F * ( ( float ) fluid.amount / TileEntityJar.getTankCapacity() );
					double boundsP = 0.0625;
					double boundsN = 1 - boundsP;
					
					// xpos face
					tessellator.setNormal( 1.0F, 0.0F, 0.0F );
					tessellator.addVertexWithUV( boundsN, 0.0, boundsP, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMaxV() );
					tessellator.addVertexWithUV( boundsN, fluidLevel, boundsP, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsN, fluidLevel, boundsN, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsN, 0.0, boundsN, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMaxV() );
					
					// xneg face
					tessellator.setNormal( -1.0F, 0.0F, 0.0F );
					tessellator.addVertexWithUV( boundsP, 0.0, boundsN, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMaxV() );
					tessellator.addVertexWithUV( boundsP, fluidLevel, boundsN, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsP, fluidLevel, boundsP, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsP, 0.0, boundsP, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMaxV() );
					
					// zpos face
					tessellator.setNormal( 0.0F, 0.0F, -1.0F );
					tessellator.addVertexWithUV( boundsN, 0.0, boundsN, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMaxV() );
					tessellator.addVertexWithUV( boundsN, fluidLevel, boundsN, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsP, fluidLevel, boundsN, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsP, 0.0, boundsN, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMaxV() );
					
					// zneg face
					tessellator.setNormal( 0.0F, 0.0F, -1.0F );
					tessellator.addVertexWithUV( boundsP, 0.0, boundsP, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMaxV() );
					tessellator.addVertexWithUV( boundsP, fluidLevel, boundsP, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsN, fluidLevel, boundsP, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsN, 0.0, boundsP, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMaxV() );
					
					// ypos face
					tessellator.setNormal( 0.0F, 1.0F, 0.0F );
					tessellator.addVertexWithUV( boundsN, fluidLevel, boundsN, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMaxV() );
					tessellator.addVertexWithUV( boundsN, fluidLevel, boundsP, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsP, fluidLevel, boundsP, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsP, fluidLevel, boundsN, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMaxV() );
					
					// yneg face
					tessellator.setNormal( 0.0F, -1.0F, 0.0F );
					tessellator.addVertexWithUV( boundsP, boundsP, boundsN, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMaxV() );
					tessellator.addVertexWithUV( boundsP, boundsP, boundsP, ( double ) fluidTexture.getMaxU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsN, boundsP, boundsP, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMinV() );
					tessellator.addVertexWithUV( boundsN, boundsP, boundsN, ( double ) fluidTexture.getMinU(), ( double ) fluidTexture.getMaxV() );
				}
			}
		}
		
		tessellator.draw();
		
		if ( undo )
			GL11.glTranslatef( 0.5F, 0.5F, 0.0F );
	}
}
