package com.chiorichan.ZapApples.events;

import java.util.Map;

import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class DaytimeManager
{
	public static Map<World, DaytimeWatcher> watchers = Maps.newHashMap();
	
	public DaytimeManager()
	{
		FMLCommonHandler.instance().bus().register( this );
	}

	@SubscribeEvent
	public void onWorldTick( TickEvent.WorldTickEvent event )
	{
		if ( !watchers.containsKey( event.world ) )
			watchers.put( event.world, new DaytimeWatcher( event.world ) );
		
		watchers.get( event.world ).tick();
	}
	
	@SubscribeEvent
	public void onWorldUnload( WorldEvent.Unload event )
	{
		watchers.remove( event.world );
	}
	
	public static int getDay( World world )
	{
		if ( !watchers.containsKey( world ) )
			return 0;
		
		return watchers.get( world ).day;
	}
}
