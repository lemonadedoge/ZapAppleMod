package com.chiorichan.ZapApples.render;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraft.src.ResourceLocation;
import net.minecraft.src.World;

public class RenderMeteor extends Render
{
  public void doRender(Entity entity, double var2, double var4, double var6, float var8, float var9)
  {
    entity.worldObj.spawnParticle("crit", entity.posX, entity.posY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ);
    entity.worldObj.spawnParticle("crit", entity.posX + 0.5D, entity.posY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ);
    entity.worldObj.spawnParticle("crit", entity.posX - 0.5D, entity.posY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ);
    entity.worldObj.spawnParticle("crit", entity.posX, entity.posY, entity.posZ + 0.5D, entity.motionX, entity.motionY, entity.motionZ);
    entity.worldObj.spawnParticle("crit", entity.posX, entity.posY, entity.posZ - 0.5D, entity.motionX, entity.motionY, entity.motionZ);
  }

  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return null;
  }
}