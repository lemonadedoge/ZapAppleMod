package com.jsn_man.ZapApples;

import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.world.World;

public class EntityFallingFlour extends EntityFallingSand{
	
	public EntityFallingFlour(World world){
		super(world);
	}
	
	public EntityFallingFlour(World world, double x, double y, double z, int blockID, int blockMeta){
		super(world, x, y, z, blockID, blockMeta);
	}
}