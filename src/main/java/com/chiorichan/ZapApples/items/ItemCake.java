package com.chiorichan.ZapApples.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import com.chiorichan.ZapApples.ZapApples;

public class ItemCake extends ItemBlock
{
	public ItemCake( Block block )
	{
		super( block );
		setHasSubtypes( true );
		setMaxDamage( 0 );
	}
	
	public int getMetadata( int meta )
	{
		return meta;
	}
	
	public String getItemDisplayName( ItemStack stack )
	{
		NBTTagCompound tag = stack.getTagCompound();
		
		if ( ( tag != null ) && ( tag.hasKey( "Cake" ) ) )
		{
			tag = tag.getCompoundTag( "Cake" );
			
			String baseKey = tag.getString( "base" );
			
			if ( ( baseKey != null ) && ( !baseKey.isEmpty() ) )
			{
				return ZapApples.cake.getBaseIngredient( baseKey ).title + " Cake";
			}
			return "Plain Cake";
		}
		
		return "Plain Cake";
	}
	
	public void addInformation( ItemStack itemstack, EntityPlayer player, List list, boolean par )
	{
		NBTTagCompound tag = itemstack.getTagCompound();
		
		if ( ( tag != null ) && ( tag.hasKey( "Cake" ) ) )
		{
			tag = tag.getCompoundTag( "Cake" );
			
			String frostKey = tag.getString( "frost" );
			
			if ( ( frostKey != null ) && ( !frostKey.isEmpty() ) )
				list.add( EnumChatFormatting.DARK_PURPLE + ZapApples.cake.getFrostIngredient( frostKey ).title + " Frosting" );
			else
				list.add( EnumChatFormatting.DARK_PURPLE + "No Frosting" );
		}
		else
		{
			list.add( EnumChatFormatting.DARK_PURPLE + "No Frosting" );
		}
	}
}
