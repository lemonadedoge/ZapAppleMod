package com.chiorichan.ZapApples.events;

import java.util.List;
import java.util.Map;

import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class DaytimeManager
{
	public static Map<World, DaytimeWatcher> watchers = Maps.newHashMap();
	public static List<DayChangeListener> listeners = Lists.newArrayList();
	
	public DaytimeManager()
	{
		FMLCommonHandler.instance().bus().register( this );
	}
	
	@SubscribeEvent
	public void onWorldTick( TickEvent.WorldTickEvent event )
	{
		try
		{
			if ( !watchers.containsKey( event.world ) )
				watchers.put( event.world, new DaytimeWatcher( event.world ) );
			
			watchers.get( event.world ).tick();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			FMLLog.severe( "[Zap Apples] Would you kindly report this exception to the Zap Apple mod developer!" );
			// Use this to prevent my mod from crashing someone's game.
		}
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
	
	public static void registerListener( DayChangeListener listener )
	{
		if ( listener == null )
			return;
		
		if ( !listeners.contains( listener ) )
			listeners.add( listener );
	}
	
	protected static void onDayChange( World fromWorld )
	{
		FMLLog.info( "[Zap Apples] The day has changed in world '" + fromWorld.getWorldInfo().getWorldName() + "'" );
		
		for ( DayChangeListener l : listeners )
			if ( l.getWorld() != null && l.getWorld() == fromWorld )
				l.onDayChange();
	}
}
