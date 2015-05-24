package com.chiorichan.ZapApples.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.chiorichan.ZapApples.entity.EntityZapApple;

@SuppressWarnings( {"unchecked", "rawtypes"} )
public class ItemZapApple extends ItemZapAppleGray
{
	public ItemZapApple( Block block )
	{
		super( block );
		healAmount = 10;
		saturationModifier = 0.8F;
		
		setAlwaysEdible();
		setPotionEffect( Potion.field_76444_x.id, 60, 1, 1.0F );
	}
	
	public boolean hasEffect( ItemStack stack )
	{
		return true;
	}
	
	public void addInformation( ItemStack itemstack, EntityPlayer player, List list, boolean par )
	{
		list.add( EnumChatFormatting.YELLOW + "Gives 1 minute of absorption and 5 full meat pops!" );
	}
	
	public ItemStack onItemRightClick( ItemStack itemStack, World worldObj, EntityPlayer player )
	{
		if ( player.rotationPitch > 40 )
		{
			if ( !player.capabilities.isCreativeMode )
				--itemStack.stackSize;
			
			worldObj.playSoundAtEntity( player, "random.bow", 0.5F, 0.4F / ( itemRand.nextFloat() * 0.4F + 0.8F ) );
			
			if ( !worldObj.isRemote )
				worldObj.spawnEntityInWorld( new EntityZapApple( worldObj, player ) );
		}
		else if ( player.canEat( false ) )
		{
			player.setItemInUse( itemStack, getMaxItemUseDuration( itemStack ) );
		}
		
		return itemStack;
	}
}
