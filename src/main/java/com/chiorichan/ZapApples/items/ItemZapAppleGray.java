package com.chiorichan.ZapApples.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemZapAppleGray extends ItemBlock
{
	protected int healAmount;
	protected float saturationModifier;
	private int potionDuration;
	private int potionAmplifier;
	private float potionEffectProbability;
	private int potionId;
	private boolean alwaysEdible;
	private boolean isPlacable;
	
	public ItemZapAppleGray(Block block)
	{
		super( block );
		healAmount = -6;
		saturationModifier = 0.0F;
		
		setPotionEffect( Potion.hunger.id, 15, 6, 1.0F );
		setAlwaysEdible();
	}
	
	@SideOnly( Side.CLIENT )
	public void registerIcons( IIconRegister register )
	{
		itemIcon = register.registerIcon( "zapapples:zapapple_premature" );
	}
	
	@Override
	public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
	{
		stack.stackSize -= 1;
		player.getFoodStats().addStats( healAmount, saturationModifier );
		
		if ( healAmount < 0 )
		{
			player.setHealth( player.getHealth() + healAmount );
		}
		world.playSoundAtEntity( player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F );
		onFoodEaten( stack, world, player );
		return stack;
	}
	
	protected void onFoodEaten( ItemStack stack, World world, EntityPlayer player )
	{
		if ( ( !world.isRemote ) && ( potionId > 0 ) && ( world.rand.nextFloat() < potionEffectProbability ) )
		{
			player.addPotionEffect( new PotionEffect( potionId, potionDuration * 20, potionAmplifier ) );
		}
	}
	
	@Override
	public int getMaxItemUseDuration( ItemStack stack )
	{
		return 32;
	}
	
	public EnumAction getItemUseAction( ItemStack stack )
	{
		return EnumAction.eat;
	}
	
	public boolean onItemUse( ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10 )
	{
		if ( !isPlacable )
		{
			return false;
		}
		return super.onItemUse( par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10 );
	}
	
	public ItemStack onItemRightClick( ItemStack stack, World world, EntityPlayer player )
	{
		if ( player.canEat( alwaysEdible ) )
		{
			player.setItemInUse( stack, getMaxItemUseDuration( stack ) );
		}
		return stack;
	}
	
	public ItemBlock setPotionEffect( int par1, int par2, int par3, float par4 )
	{
		potionId = par1;
		potionDuration = par2;
		potionAmplifier = par3;
		potionEffectProbability = par4;
		return this;
	}
	
	public ItemBlock setAlwaysEdible()
	{
		alwaysEdible = true;
		return this;
	}
	
	public ItemBlock setPlacable()
	{
		isPlacable = true;
		return this;
	}
	
	public void addInformation( ItemStack itemstack, EntityPlayer player, List list, boolean par )
	{
		list.add( EnumChatFormatting.DARK_GRAY + player.getDisplayName() + " is a cheater!" );
	}
}
