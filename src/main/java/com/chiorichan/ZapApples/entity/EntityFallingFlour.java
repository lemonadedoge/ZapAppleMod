package com.chiorichan.ZapApples.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

public class EntityFallingFlour extends EntityFallingBlock
{
	public EntityFallingFlour(World world)
	{
		super( world );
	}
	
	public EntityFallingFlour(World world, double x, double y, double z, Block block, int blockMeta)
	{
		super( world, x, y, z, block, blockMeta );
	}
}
