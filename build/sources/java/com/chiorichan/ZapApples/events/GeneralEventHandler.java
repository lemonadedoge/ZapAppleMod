package com.chiorichan.ZapApples.events;

import net.minecraftforge.client.event.TextureStitchEvent;

import com.chiorichan.ZapApples.ZapApples;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GeneralEventHandler
{
	@SideOnly( Side.CLIENT )
	@SubscribeEvent
	public void postStitch( TextureStitchEvent.Post event )
	{
		ZapApples.zapAppleJam.setIcons( ZapApples.zapAppleJamBlock.getIcon( 0, 0 ), ZapApples.zapAppleJamBlock.getIcon( 1, 0 ) );
	}
}
