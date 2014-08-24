package com.chiorichan.ZapApples.models;

import com.chiorichan.ZapApples.mobs.EntityTimberWolf;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelTimberWolf extends ModelBase
{
  public ModelRenderer wolfHeadMain;
  public ModelRenderer wolfBody;
  public ModelRenderer wolfLeg1;
  public ModelRenderer wolfLeg2;
  public ModelRenderer wolfLeg3;
  public ModelRenderer wolfLeg4;
  ModelRenderer wolfTail;
  ModelRenderer wolfMane;

  public ModelTimberWolf()
  {
    float f = 0.0F;
    float f1 = 13.5F;
    wolfHeadMain = new ModelRenderer(this, 0, 0);
    wolfHeadMain.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4, f);
    wolfHeadMain.setRotationPoint(-1.0F, f1, -7.0F);
    wolfBody = new ModelRenderer(this, 18, 14);
    wolfBody.addBox(-4.0F, -2.0F, -3.0F, 6, 9, 6, f);
    wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
    wolfMane = new ModelRenderer(this, 21, 0);
    wolfMane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, f);
    wolfMane.setRotationPoint(-1.0F, 14.0F, 2.0F);
    wolfLeg1 = new ModelRenderer(this, 0, 18);
    wolfLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
    wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
    wolfLeg2 = new ModelRenderer(this, 0, 18);
    wolfLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
    wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
    wolfLeg3 = new ModelRenderer(this, 0, 18);
    wolfLeg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
    wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
    wolfLeg4 = new ModelRenderer(this, 0, 18);
    wolfLeg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
    wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
    wolfTail = new ModelRenderer(this, 9, 18);
    wolfTail.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
    wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
    wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2, 2, 1, f);
    wolfHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5.0F, 0.0F, 2, 2, 1, f);
    wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0.0F, -5.0F, 3, 3, 4, f);
  }

  public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
  {
    super.render(par1Entity, par2, par3, par4, par5, par6, par7);
    setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

    if (isChild)
    {
      float f6 = 2.0F;
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 5.0F * par7, 2.0F * par7);
      wolfHeadMain.renderWithRotation(par7);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
      GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
      wolfBody.render(par7);
      wolfLeg1.render(par7);
      wolfLeg2.render(par7);
      wolfLeg3.render(par7);
      wolfLeg4.render(par7);
      wolfTail.renderWithRotation(par7);
      wolfMane.render(par7);
      GL11.glPopMatrix();
    }
    else
    {
      wolfHeadMain.renderWithRotation(par7);
      wolfBody.render(par7);
      wolfLeg1.render(par7);
      wolfLeg2.render(par7);
      wolfLeg3.render(par7);
      wolfLeg4.render(par7);
      wolfTail.renderWithRotation(par7);
      wolfMane.render(par7);
    }
  }

  public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
  {
    EntityTimberWolf entitywolf = (EntityTimberWolf)par1EntityLivingBase;

    if (entitywolf.isAngry())
    {
      wolfTail.rotateAngleY = 0.0F;
    }
    else
    {
      wolfTail.rotateAngleY = (MathHelper.cos(par2 * 0.6662F) * 1.4F * par3);
    }

    if (entitywolf.isSitting())
    {
      wolfMane.setRotationPoint(-1.0F, 16.0F, -3.0F);
      wolfMane.rotateAngleX = 1.256637F;
      wolfMane.rotateAngleY = 0.0F;
      wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
      wolfBody.rotateAngleX = 0.7853982F;
      wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
      wolfLeg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
      wolfLeg1.rotateAngleX = 4.712389F;
      wolfLeg2.setRotationPoint(0.5F, 22.0F, 2.0F);
      wolfLeg2.rotateAngleX = 4.712389F;
      wolfLeg3.rotateAngleX = 5.811947F;
      wolfLeg3.setRotationPoint(-2.49F, 17.0F, -4.0F);
      wolfLeg4.rotateAngleX = 5.811947F;
      wolfLeg4.setRotationPoint(0.51F, 17.0F, -4.0F);
    }
    else
    {
      wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
      wolfBody.rotateAngleX = 1.570796F;
      wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
      wolfMane.rotateAngleX = wolfBody.rotateAngleX;
      wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
      wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
      wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
      wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
      wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
      wolfLeg1.rotateAngleX = (MathHelper.cos(par2 * 0.6662F) * 1.4F * par3);
      wolfLeg2.rotateAngleX = (MathHelper.cos(par2 * 0.6662F + 3.141593F) * 1.4F * par3);
      wolfLeg3.rotateAngleX = (MathHelper.cos(par2 * 0.6662F + 3.141593F) * 1.4F * par3);
      wolfLeg4.rotateAngleX = (MathHelper.cos(par2 * 0.6662F) * 1.4F * par3);
    }

    wolfHeadMain.rotateAngleZ = (entitywolf.getInterestedAngle(par4) + entitywolf.getShakeAngle(par4, 0.0F));
    wolfMane.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.08F);
    wolfBody.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.16F);
    wolfTail.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.2F);

    wolfHeadMain.rotateAngleZ = (entitywolf.getInterestedAngle(par4) + entitywolf.getShakeAngle(par4, 0.0F));
    wolfMane.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.08F);
    wolfBody.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.16F);
    wolfTail.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.2F);
  }

  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
  {
    super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
    wolfHeadMain.rotateAngleX = (par5 / 57.295776F);
    wolfHeadMain.rotateAngleY = (par4 / 57.295776F);
    wolfTail.rotateAngleX = par3;
  }
}