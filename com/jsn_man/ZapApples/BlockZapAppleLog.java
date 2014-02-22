package com.jsn_man.ZapApples;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockZapAppleLog extends BlockContainer{
	
	public BlockZapAppleLog(int id, int texture){
		super(id, texture, Material.wood);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public int getRenderType(){
		return 31;
	}
	
	public int damageDropped(int meta){
		return 0;
	}
	
	public int getBlockTextureFromSideAndMetadata(int side, int meta){
		return (meta == 0 || meta == 9) && (side == 1 || side == 0) ? blockIndexInTexture + 1 : (meta == 4 && (side == 5 || side == 4) ? blockIndexInTexture + 1 : (meta == 8 && (side == 2 || side == 3) ? blockIndexInTexture + 1 : blockIndexInTexture));
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity){
		int dir = BlockPistonBase.determineOrientation(world, x, y, z, (EntityPlayer)entity);
		byte meta = 0;
		
		if(dir == 0 || dir == 1){
			meta = 0;
		}else if(dir == 2 || dir == 3){
			meta = 8;
		}else if(dir == 4 || dir == 5){
			meta = 4;
		}
		
		world.setBlockMetadataWithNotify(x, y, z, meta);
	}
	
	public void breakBlock(World world, int x, int y, int z, int side, int meta){
		super.breakBlock(world, x, y, z, side, meta);
		byte var7 = 1;
        int var8 = var7 + 1;

        if(world.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8)){
            for(int var9 = -var7; var9 <= var7; ++var9){
                for(int var10 = -var7; var10 <= var7; ++var10){
                    for(int var11 = -var7; var11 <= var7; ++var11){
                        int var12 = world.getBlockId(x + var9, y + var10, z + var11);

                        if(Block.blocksList[var12] != null){
                            Block.blocksList[var12].beginLeavesDecay(world, x + var9, y + var10, z + var11);
                        }
                    }
                }
            }
        }
	}
	
	public TileEntity createNewTileEntity(World world){
		return new TileEntityZapAppleLog();
	}
	
	protected ItemStack createStackedBlock(int meta){
		return new ItemStack(this, 1);
	}
	
	public boolean canSustainLeaves(World world, int x, int y, int z){
		return true;
	}
	
	public boolean isWood(World world, int x, int y, int z){
		return true;
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}