package com.chiorichan.ZapApples.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import net.minecraft.src.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderTimberWolf extends RenderLiving
{
  private static final ResourceLocation texture = new ResourceLocation("ZapApples:textures/entity/timber_wolf.png");

  public RenderTimberWolf(ModelBase par1ModelBase, float par2)
  {
    super(par1ModelBase, par2);
  }

  protected ResourceLocation getEntityTexture(Entity par1Entity)
  {
    return texture;
  }
}