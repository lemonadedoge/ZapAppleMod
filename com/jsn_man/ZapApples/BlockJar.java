package com.jsn_man.ZapApples;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockJar extends Block{
	
	public BlockJar(int id, int texture){
		super(id, texture, Material.glass);
		setCreativeTab(CreativeTabs.tabFood);
	}
	
	public int getBlockTextureFromSideAndMetadata(int side, int meta){
		if(side == 1){
			return ZapApples.jamStill.blockIndexInTexture - 1;
		}else if(side == 0){
			return ZapApples.jamStill.blockIndexInTexture + 15;
		}else{
			return ZapApples.jamStill.blockIndexInTexture;
		}
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
	
	public int damageDropped(int meta){
		return meta;
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9){
		ItemStack stack = player.inventory.getCurrentItem();
		if(stack != null && stack.getItem().shiftedIndex == ZapApples.jamBucket.shiftedIndex){
			if(world.getBlockMetadata(x, y, z) == 0){
				if(!world.isRemote){
					world.setBlockMetadataWithNotify(x, y, z, 1);
					
					if(stack.stackSize > 1){
						stack.stackSize--;
					}else{
						stack = null;
					}
					
					if(!player.capabilities.isCreativeMode){
						player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
					}
					if(!player.inventory.addItemStackToInventory(new ItemStack(Item.bucketEmpty))){
						player.dropPlayerItem(new ItemStack(Item.bucketEmpty));
					}
				}
			}
			return true;
		}else if(stack != null && stack.getItem().shiftedIndex == Item.bucketEmpty.shiftedIndex){
			if(world.getBlockMetadata(x, y, z) == 1){
				if(!world.isRemote){
					world.setBlockMetadataWithNotify(x, y, z, 0);
					
					if(stack != null && stack.stackSize > 1){
						stack.stackSize--;
					}else{
						stack = null;
					}
					
					if(!player.capabilities.isCreativeMode){
						player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
					}
					if(!player.inventory.addItemStackToInventory(new ItemStack(ZapApples.jamBucket))){
						player.dropPlayerItem(new ItemStack(ZapApples.jamBucket));
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}