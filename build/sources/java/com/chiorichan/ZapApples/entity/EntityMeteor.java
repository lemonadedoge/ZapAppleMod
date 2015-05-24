package com.chiorichan.ZapApples.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityMeteor extends Entity
{
	public EntityMeteor(World world)
	{
		super( world );
		setSize( 1.0F, 1.0F );
		noClip = true;
	}
	
	public void entityInit()
	{
	}
	
	public void onUpdate()
	{
		if ( !worldObj.isRemote )
		{
			setVelocity( -0.05D, -0.05D, -0.05D );
			setPosition( posX + motionX, posY + motionY, posZ + motionZ );
			setPosition( posX + motionX, posY + motionY, posZ + motionZ );
			if ( posY <= 10.0D )
				setDead();
		}
	}
	
	public void readEntityFromNBT( NBTTagCompound tag )
	{
	}
	
	public void writeEntityToNBT( NBTTagCompound tag )
	{
	}
	
	public AxisAlignedBB getBoundingBox()
	{
		return null;
	}
	
	public AxisAlignedBB getCollisionBox( Entity entity )
	{
		return null;
	}
}
