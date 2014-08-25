package com.chiorichan.ZapApples.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemJamFood extends ItemFood
{
	public int[] heal = { 7 };
	public float[] saturation = { 0.8F };
	
	public ItemJamFood()
	{
		super( 0, 0.0F, false );
		setUnlocalizedName( "jamFood" );
	}
	
	public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
	{
		stack.stackSize -= 1;
		
		player.getFoodStats().addStats( heal[stack.getItemDamage()], saturation[stack.getItemDamage()] );
		
		world.playSoundAtEntity( player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F );
		onFoodEaten( stack, world, player );
		return stack;
	}
	
	@SideOnly( Side.CLIENT )
	public void registerIcons( IIconRegister register )
	{
		itemIcon = register.registerIcon( "zapapples:jamSandwich" );
	}
}
