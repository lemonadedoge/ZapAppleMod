package com.chiorichan.ZapApples.entity;

import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.World;

public class EntityFallingFlour extends EntityFallingSand
{
  public EntityFallingFlour(World world)
  {
    super(world);
  }

  public EntityFallingFlour(World world, double x, double y, double z, int blockID, int blockMeta)
  {
    super(world, x, y, z, blockID, blockMeta);
  }
}