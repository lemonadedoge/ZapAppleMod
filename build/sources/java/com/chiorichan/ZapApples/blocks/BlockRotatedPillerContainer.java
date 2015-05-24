package com.chiorichan.ZapApples.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockRotatedPillerContainer extends BlockContainer
{
	
	@SideOnly( Side.CLIENT )
	protected IIcon field_111051_a;
	
	protected BlockRotatedPillerContainer(Material par2Material)
	{
		super( par2Material );
	}
	
	public int getRenderType()
	{
		return 31;
	}
	
	public int onBlockPlaced( World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9 )
	{
		int j1 = par9 & 0x3;
		byte b0 = 0;
		
		switch ( par5 )
		{
			case 0:
			case 1:
				b0 = 0;
				break;
			case 2:
			case 3:
				b0 = 8;
				break;
			case 4:
			case 5:
				b0 = 4;
		}
		
		return j1 | b0;
	}
	
	@SideOnly( Side.CLIENT )
	public IIcon getIcon( int par1, int par2 )
	{
		int k = par2 & 0xC;
		int l = par2 & 0x3;
		return ( k == 8 ) && ( ( par1 == 2 ) || ( par1 == 3 ) ) ? getEndIcon( l ) : ( k == 4 ) && ( ( par1 == 5 ) || ( par1 == 4 ) ) ? getEndIcon( l ) : ( k == 0 ) && ( ( par1 == 1 ) || ( par1 == 0 ) ) ? getEndIcon( l ) : getSideIcon( l );
	}
	
	public int damageDropped( int par1 )
	{
		return par1 & 0x3;
	}
	
	@SideOnly( Side.CLIENT )
	protected abstract IIcon getSideIcon( int paramInt );
	
	@SideOnly( Side.CLIENT )
	protected IIcon getEndIcon( int par1 )
	{
		return field_111051_a;
	}
	
	public int func_111050_e( int par1 )
	{
		return par1 & 0x3;
	}
	
	protected ItemStack createStackedBlock( int par1 )
	{
		return new ItemStack( this, 1, func_111050_e( par1 ) );
	}
}
