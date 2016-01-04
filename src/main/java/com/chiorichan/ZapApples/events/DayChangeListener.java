package com.chiorichan.ZapApples.events;

import net.minecraft.world.World;

public interface DayChangeListener
{
	public void onDayChange();
	
	public World getWorld();
}
