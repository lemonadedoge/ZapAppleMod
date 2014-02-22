package com.jsn_man.ZapApples;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCakething extends BlockContainer{
	
	public BlockCakething(int id, int texture){
		super(id, texture, Material.cake);
		setCreativeTab(CreativeTabs.tabFood);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
		TileEntityCakething tile = (TileEntityCakething)world.getBlockTileEntity(x, y, z);
		if(tile != null){
			if(tile.base > 1){
				float slice = 0.0625F;
				float delta = (float)(1 + tile.stage * 2) / 16.0F;
				setBlockBounds(delta, 0, slice, 1 - slice, 0.5F, 1 - slice);
			}else{
				setBlockBounds(0, 0, 0, 1, 0.5F, 1);
			}
		}else{
			setBlockBounds(0, 0, 0, 1, 0.5F, 1);
		}
	}
	
	public void setBlockBoundsForItemRender(){
		float slice = 0.0625F;
		setBlockBounds(slice, 0, slice, 1 - slice, 0.5F, 1 - slice);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9){
		if(!world.isRemote){
			eatSlice(world, x, y, z, player);
			return true;
		}
		return true;
	}
	
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player){
		if(!world.isRemote){
			eatSlice(world, x, y, z, player);
		}
	}
	
	private void eatSlice(World world, int x, int y, int z, EntityPlayer player){
		if(player.canEat(false)){
			TileEntityCakething tile = (TileEntityCakething)world.getBlockTileEntity(x, y, z);
			if(tile != null){
				tile.eatSlice(player);
			}
		}
	}
	
	public boolean canPlaceBlockAt(World world, int x, int y, int z){
		return !super.canPlaceBlockAt(world, x, y, z) ? false : canBlockStay(world, x, y, z);
	}
	
	public boolean canBlockStay(World world, int x, int y, int z){
		return world.getBlockMaterial(x, y - 1, z).isSolid();
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, int meta){
		if(!canBlockStay(world, x, y, z)){
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockWithNotify(x, y, z, 0);
		}
	}
	
	public int idDropped(int par1, Random rand, int par3){
		return 0;
	}
	
	public int quantityDropped(Random rand){
		return 0;
	}
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list){
		list.add(new ItemStack(blockID, 1, 0));
		list.add(new ItemStack(blockID, 1, 1));
		list.add(new ItemStack(blockID, 1, 2));
	}
	
	@Override
	public void func_85105_g(World world, int x, int y, int z, int meta){
		//if(!world.isRemote){
			TileEntityCakething tile = (TileEntityCakething)world.getBlockTileEntity(x, y, z);
			if(tile != null){
				tile.setData(meta);
				world.setBlockMetadata(x, y, z, 0);
			}
		//}
	}
	
	public TileEntity createNewTileEntity(World world){
		return new TileEntityCakething();
	}
	
	public boolean isOpaqueCube(){
		return false;
	}
	
	public boolean renderAsNormalBlock(){
		return false;
	}
	
	public int getRenderType(){
		return ZapApples.idRender3D;
	}
	
	public String getTextureFile(){
		return ZapApples.cakeTextures;
	}
}