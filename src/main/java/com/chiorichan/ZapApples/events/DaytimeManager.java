package com.chiorichan.ZapApples.events;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class DaytimeManager
{
	private static int index;
	public static DaytimeWatcher[] watchers = new DaytimeWatcher[0];
	
	public DaytimeManager()
	{
		FMLCommonHandler.instance().bus().register( this );
	}

	@SubscribeEvent
	public void onServerTick( TickEvent.ServerTickEvent event )
	{
		if ( Minecraft.getMinecraft().getIntegratedServer() == null )
		{
			possibleClose();
			return;
		}
		
		if ( watchers.length != Minecraft.getMinecraft().getIntegratedServer().worldServers.length )
		{
			createNewWatchers();
			return;
		}
		
		watchers[( index++ )].tick();
		
		if ( index >= watchers.length )
		{
			index = 0;
		}
	}
	
	public static void possibleClose()
	{
		if ( watchers.length > 0 )
		{
			for ( DaytimeWatcher dw : watchers )
			{
				dw.close();
			}
			watchers = new DaytimeWatcher[0];
			index = 0;
		}
	}
	
	public static int getDay( World world )
	{
		for ( DaytimeWatcher dw : watchers )
		{
			if ( dw.name.equalsIgnoreCase( world.getSaveHandler().getWorldDirectoryName() ) )
			{
				return dw.day;
			}
		}
		return 0;
	}
	
	private static void createNewWatchers()
	{
		World[] worlds = Minecraft.getMinecraft().getIntegratedServer().worldServers;
		if ( worlds.length > 0 )
		{
			for ( DaytimeWatcher dw : watchers )
			{
				dw.close();
			}
			watchers = new DaytimeWatcher[worlds.length];
			for ( int i = 0; i < worlds.length; i++ )
			{
				watchers[i] = new DaytimeWatcher( worlds[i] );
			}
			index = 0;
		}
	}
}
