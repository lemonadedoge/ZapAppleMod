package com.chiorichan.ZapApples.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZapApple extends BlockGrayApple
{
	
	@SideOnly( Side.CLIENT )
	protected IIcon icon;
	
	public BlockZapApple()
	{
		super();
		setResistance( 0.0F );
		setHardness( 0.0F );
		setStepSound( Block.soundTypeGrass );
		setBlockName( "zapApple" );
	}
	
	public IIcon getIcon( int side, int meta )
	{
		return icon;
	}
	
	@SideOnly( Side.CLIENT )
	public void registerBlockIcons( IIconRegister register )
	{
		icon = register.registerIcon( "zapapples:zapapple_mature" );
	}
	
	@Override
	public Item getItemDropped( int par1, Random rand, int par3 )
	{
		return new ItemStack( this, 1 ).getItem();
	}
	
	public int quantityDropped( Random rand )
	{
		return 1;
	}
}
