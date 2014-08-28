package com.chiorichan.ZapApples.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.chiorichan.ZapApples.ZapApples;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZapAppleJam extends BlockFluidClassic
{
	
	@SideOnly( Side.CLIENT )
	protected IIcon stillIcon;
	
	@SideOnly( Side.CLIENT )
	protected IIcon flowingIcon;
	
	public BlockZapAppleJam(Fluid fluid, Material material)
	{
		super( fluid, material );
		setCreativeTab( CreativeTabs.tabMisc );
		setBlockName( "zapapplejam" );
	}
	
	public IIcon a( int side, int meta )
	{
		return ( side == 0 ) || ( side == 1 ) ? stillIcon : flowingIcon;
	}
	
	@SideOnly( Side.CLIENT )
	public void registerIcon( IIconRegister register )
	{
		stillIcon = register.registerIcon( "zapapples:zapapplejamstill" );
		flowingIcon = register.registerIcon( "zapapples:zapapplejamflowing" );
		
		ZapApples.zapAppleJam.setIcons( stillIcon, flowingIcon );
	}
	
	public void onEntityCollidedWithBlock( World world, int x, int y, int z, Entity entity )
	{
		if ( world.isRemote )
		{
			return;
		}
		
		if ( ( entity instanceof EntityLivingBase ) )
		{
			EntityLivingBase ent = (EntityLivingBase) entity;
			ent.addPotionEffect( new PotionEffect( Potion.digSpeed.id, 120, 0 ) );
			ent.addPotionEffect( new PotionEffect( Potion.moveSpeed.id, 240, 0 ) );
		}
		
		super.onEntityCollidedWithBlock( world, x, y, z, entity );
	}
	
	public boolean canDisplace( IBlockAccess world, int x, int y, int z )
	{
		if ( world.getBlock( x, y, z ).getMaterial().isLiquid() )
			return false;
		return super.canDisplace( world, x, y, z );
	}
	
	public boolean displaceIfPossible( World world, int x, int y, int z )
	{
		if ( world.getBlock( x, y, z ).getMaterial().isLiquid() )
			return false;
		return super.displaceIfPossible( world, x, y, z );
	}
}
