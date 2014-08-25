package com.chiorichan.ZapApples.util;

import net.minecraft.item.ItemStack;

public class InventoryUtil
{
	public static ItemStack consumeItem( ItemStack stack )
	{
		if ( stack.stackSize == 1 )
		{
			if ( stack.getItem().hasContainerItem( stack ) )
			{
				return stack.getItem().getContainerItem( stack );
			}
			
			return null;
		}
		
		stack.splitStack( 1 );
		
		return stack;
	}
}
