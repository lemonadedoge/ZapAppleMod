package com.chiorichan.ZapApples.events;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class DaytimeManager
{
	public static Map<World, DaytimeWatcher> watchers = Maps.newHashMap();
	public static Map<DayChangeListener, World> listeners = Maps.newHashMap();
	
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
	
	public static void unregisterListener( DayChangeListener listener )
	{
		listeners.remove( listener );
	}
	
	public static void registerListener( DayChangeListener listener, World forWorld )
	{
		listeners.put( listener, forWorld );
	}
	
	protected static void onDayChange( World fromWorld )
	{
		FMLLog.info( "[ZapApples] The day has changed in world '" + fromWorld.getWorldInfo().getWorldName() + "'" );
		
		for ( Entry<DayChangeListener, World> e : listeners.entrySet() )
			if ( e.getValue() == fromWorld )
				e.getKey().onDayChange();
	}
}
