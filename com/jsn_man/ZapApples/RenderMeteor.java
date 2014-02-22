package com.jsn_man.ZapApples;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class RenderMeteor extends Render{
	
	public void doRender(Entity entity, double var2, double var4, double var6, float var8, float var9){
		entity.worldObj.spawnParticle("crit", entity.posX, entity.posY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ);
		entity.worldObj.spawnParticle("crit", entity.posX + 0.5D, entity.posY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ);
		entity.worldObj.spawnParticle("crit", entity.posX - 0.5D, entity.posY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ);
		entity.worldObj.spawnParticle("crit", entity.posX, entity.posY, entity.posZ + 0.5D, entity.motionX, entity.motionY, entity.motionZ);
		entity.worldObj.spawnParticle("crit", entity.posX, entity.posY, entity.posZ - 0.5D, entity.motionX, entity.motionY, entity.motionZ);
	}
}