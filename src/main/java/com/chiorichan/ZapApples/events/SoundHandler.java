package com.chiorichan.ZapApples.events;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SoundHandler
{
	@SubscribeEvent
	public void onSound( SoundLoadEvent event )
	{
		try
		{
			// event.manager.soundPoolSounds.addSound("mob/wolf/zaphowl.wav");
			
		}
		catch ( Exception e )
		{
			FMLLog.severe( "Zap Apples could not register a sound.", e );
		}
	}
	
	@SubscribeEvent
	public void onSoundLoad( SoundLoadEvent event )
	{
	}
}
