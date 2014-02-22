package com.jsn_man.ZapApples;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketHandler{
	
	@ForgeSubscribe
	public void onBucketFill(FillBucketEvent event){
		ItemStack result = fillCustomBucket(event.world, event.target);
		
		if(result != null){
			event.result = result;
			event.setResult(Result.ALLOW);
		}
	}
	
	public ItemStack fillCustomBucket(World world, MovingObjectPosition pos){
		int blockID = world.getBlockId(pos.blockX, pos.blockY, pos.blockZ);
		if((blockID == ZapApples.jamStill.blockID || blockID == ZapApples.jamMoving.blockID) && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0){
			world.setBlockWithNotify(pos.blockX, pos.blockY, pos.blockZ, 0);
			return new ItemStack(ZapApples.jamBucket);
		}else if((blockID == ZapApples.doughStill.blockID || blockID == ZapApples.doughMoving.blockID) && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0){
			world.setBlockWithNotify(pos.blockX, pos.blockY, pos.blockZ, 0);
			return new ItemStack(ZapApples.doughBucket);
		}else{
			return null;
		}
	}
}