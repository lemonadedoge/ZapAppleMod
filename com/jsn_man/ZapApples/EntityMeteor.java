package com.jsn_man.ZapApples;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityMeteor extends Entity{
	
	public EntityMeteor(World world){
		super(world);
		setSize(1F, 1F);
		noClip = true;
	}
	
	public void entityInit(){
		
	}
	
	public void onUpdate(){
		if(!worldObj.isRemote){
			setVelocity(-0.05, -0.05, -0.05);
			setPosition(posX + motionX, posY + motionY, posZ + motionZ);
			setPosition(posX + motionX, posY + motionY, posZ + motionZ);
			if(posY <= 10){
				setDead();
			}
		}
	}
	
	public void readEntityFromNBT(NBTTagCompound tag){
		
	}
	
	public void writeEntityToNBT(NBTTagCompound tag){
		
	}
	
	public AxisAlignedBB getBoundingBox(){
		return null;
	}
	
	public AxisAlignedBB getCollisionBox(Entity entity){
		return null;
	}
}