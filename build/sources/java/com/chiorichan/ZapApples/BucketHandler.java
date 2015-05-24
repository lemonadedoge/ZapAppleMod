package com.chiorichan.ZapApples;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BucketHandler
{
	public static BucketHandler INSTANCE = new BucketHandler();
	public Map<Block, Item> buckets = Maps.newHashMap();
	
	@SubscribeEvent
	public void onBucketFill( FillBucketEvent event )
	{
		ItemStack result = fillCustomBucket( event.world, event.target );
		
		if ( result != null )
		{
			event.result = result;
			event.setResult( Event.Result.ALLOW );
		}
	}
	
	private ItemStack fillCustomBucket( World world, MovingObjectPosition pos )
	{
		Block block = world.getBlock( pos.blockX, pos.blockY, pos.blockZ );
		
		Item bucket = (Item) buckets.get( block );
		if ( ( bucket != null ) && ( world.getBlockMetadata( pos.blockX, pos.blockY, pos.blockZ ) == 0 ) )
		{
			world.setBlock( pos.blockX, pos.blockY, pos.blockZ, Block.getBlockById( 0 ) );
			return new ItemStack( bucket );
		}
		return null;
	}
}
