package com.chiorichan.ZapApples.events;

import net.minecraft.world.World;

import com.chiorichan.ZapApples.ZapApples;

public class DaytimeWatcher
{
	public boolean isDay;
	public World worldObj;
	public String name;
	
	public DaytimeWatcher(World world)
	{
		worldObj = world;
		isDay = worldObj.isDaytime();
		name = worldObj.getWorldInfo().getWorldName();
	}
	
	public void tick()
	{
		if ( isDay != worldObj.isDaytime() )
		{
			if ( worldObj.isDaytime() )
			{
				ZapApples.onDayChange( worldObj );
				DaytimeManager.onDayChange( worldObj );
			}
			isDay = worldObj.isDaytime();
		}
	}
}
