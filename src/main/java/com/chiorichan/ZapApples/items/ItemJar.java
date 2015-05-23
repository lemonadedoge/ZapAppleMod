package com.chiorichan.ZapApples.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings( {"unchecked", "rawtypes"} )
public class ItemJar extends ItemBlock
{
	public ItemJar(Block block)
	{
		super( block );
		setMaxDamage( 0 );
	}
	
	public int getMetadata( int meta )
	{
		return meta;
	}
	
	@Override
	public boolean isFull3D()
	{
		return true;
	}
	
	public void addInformation( ItemStack itemstack, EntityPlayer player, List list, boolean par )
	{
		NBTTagCompound tag = itemstack.getTagCompound();
		
		if ( ( tag != null ) && ( tag.hasKey( "Jar" ) ) )
		{
			tag = tag.getCompoundTag( "Jar" );
			
			if ( tag.hasKey( "Empty" ) )
			{
				list.add( EnumChatFormatting.RED + "Empty Jar!" );
			}
			else
			{
				FluidStack fluid = FluidStack.loadFluidStackFromNBT( tag );
				
				if ( ( fluid != null ) && ( fluid.getFluid() != null ) )
				{
					list.add( EnumChatFormatting.DARK_PURPLE + "Fluid Type: " + StringUtils.capitalize( fluid.getFluid().getName() ) );
					list.add( EnumChatFormatting.DARK_PURPLE + "Fluid Level: " + fluid.amount + "mb" );
				}
				else
				{
					list.add( EnumChatFormatting.RED + "Empty Jar!" );
				}
			}
		}
		else
		{
			list.add( EnumChatFormatting.RED + "Empty Jar!" );
		}
	}
}
