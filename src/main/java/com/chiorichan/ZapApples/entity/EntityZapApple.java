package com.chiorichan.ZapApples.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.chiorichan.ZapApples.ZapApples;

public class EntityZapApple extends EntityThrowable
{
	public EntityZapApple( World worldObj )
	{
		super( worldObj );
	}
	
	public EntityZapApple( World worldObj, EntityLivingBase entity )
	{
		super( worldObj, entity );
	}
	
	public EntityZapApple( World worldObj, double x, double y, double z )
	{
		super( worldObj, x, y, z );
	}
	
	protected void onImpact( MovingObjectPosition mop )
	{
		if ( mop.entityHit != null )
			mop.entityHit.attackEntityFrom( DamageSource.causeThrownDamage( this, getThrower() ), 3 );
		
		if ( !worldObj.isRemote )
		{
			dropItem( ZapApples.zapAppleMushed, 1 );
			// PacketHandler.sendToDimension( new SendEffectsPacket( 0, ( int ) posX, ( int ) posY, ( int ) posZ, ZapApples.zapApple, 0 ), worldObj.provider.dimensionId );
			
			// if ( ZapApples.lightningEffect && new Random().nextInt( 5 ) == 0 )
			// worldObj.addWeatherEffect( new EntityLightningBolt( worldObj, posX, posY, posZ ) );
			
			setDead();
		}
	}
}
