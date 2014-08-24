package com.chiorichan.ZapApples;

import aqz;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import yc;

public class BucketHandler
{
  public static BucketHandler INSTANCE = new BucketHandler();
  public Map<aqz, yc> buckets = new HashMap();

  @ForgeSubscribe
  public void onBucketFill(FillBucketEvent event)
  {
    ItemStack result = fillCustomBucket(event.world, event.target);

    if (result != null) {
      event.result = result;
      event.setResult(Event.Result.ALLOW);
    }
  }

  private ItemStack fillCustomBucket(World world, MovingObjectPosition pos)
  {
    int blockID = world.getBlockId(pos.blockX, pos.blockY, pos.blockZ);

    Item bucket = (Item)buckets.get(net.minecraft.src.Block.blocksList[blockID]);
    if ((bucket != null) && (world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0))
    {
      world.setBlock(pos.blockX, pos.blockY, pos.blockZ, 0);
      return new ItemStack(bucket);
    }
    return null;
  }
}