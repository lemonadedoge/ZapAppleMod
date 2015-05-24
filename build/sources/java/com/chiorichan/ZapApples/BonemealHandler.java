package com.chiorichan.ZapApples;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.BonemealEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BonemealHandler
{
	@SubscribeEvent
	public void onUseBonemeal( BonemealEvent event )
	{
		if ( ( !event.world.isRemote ) && ( event.block == ZapApples.zapAppleSapling ) )
		{
			if ( event.world.rand.nextFloat() < 0.1D )
			{
				ZapApples.zapAppleSapling.growTree( event.world, event.x, event.y, event.z, event.world.rand );
			}
			event.world.playAuxSFX( 2005, event.x, event.y, event.z, 0 );
			
			ItemStack stack = event.entityPlayer.inventory.getCurrentItem();
			
			if ( ( stack != null ) && ( stack.stackSize > 1 ) )
			{
				stack.stackSize -= 1;
			}
			else
			{
				stack = null;
			}
			
			if ( !event.entityPlayer.capabilities.isCreativeMode )
			{
				event.entityPlayer.inventory.setInventorySlotContents( event.entityPlayer.inventory.currentItem, stack );
			}
		}
	}
}
