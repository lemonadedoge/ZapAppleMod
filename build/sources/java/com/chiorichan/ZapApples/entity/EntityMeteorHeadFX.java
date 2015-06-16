package com.chiorichan.ZapApples.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

@SideOnly( Side.CLIENT )
public class EntityMeteorHeadFX extends EntityFX
{
	// private Entity theEntity;
	
	public EntityMeteorHeadFX( World world, Entity entity )
	{
		super( world, entity.posX, entity.posY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ );
		// theEntity = entity;
		motionX = entity.motionX;
		motionY = entity.motionY;
		motionZ = entity.motionZ;
		particleMaxAge = 20;
	}
	
	@Override
	public void renderParticle( Tessellator tessellator, float par2, float par3, float par4, float par5, float par6, float par7 )
	{
		super.renderParticle( tessellator, par2, par3, par4, par5, par6, par7 );
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		setParticleTextureIndex( 7 - particleAge * 8 / particleMaxAge );
	}
}
