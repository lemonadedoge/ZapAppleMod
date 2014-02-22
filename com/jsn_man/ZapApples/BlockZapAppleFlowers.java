package com.jsn_man.ZapApples;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class BlockZapAppleFlowers extends Block implements IShearable{
	
	public BlockZapAppleFlowers(int id, int texture){
		super(id, texture, Material.vine);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	public void setBlockBoundsForItemRender(){
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public int getRenderType(){
		return 20;
	}
	
	public boolean isOpaqueCube(){
		return false;
	}
	
	public boolean renderAsNormalBlock(){
		return false;
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
		int var6 = world.getBlockMetadata(x, y, z);
        float var7 = 1.0F;
        float var8 = 1.0F;
        float var9 = 1.0F;
        float var10 = 0.0F;
        float var11 = 0.0F;
        float var12 = 0.0F;
        boolean var13 = var6 > 0;

        if ((var6 & 2) != 0)
        {
            var10 = Math.max(var10, 0.0625F);
            var7 = 0.0F;
            var8 = 0.0F;
            var11 = 1.0F;
            var9 = 0.0F;
            var12 = 1.0F;
            var13 = true;
        }

        if ((var6 & 8) != 0)
        {
            var7 = Math.min(var7, 0.9375F);
            var10 = 1.0F;
            var8 = 0.0F;
            var11 = 1.0F;
            var9 = 0.0F;
            var12 = 1.0F;
            var13 = true;
        }

        if ((var6 & 4) != 0)
        {
            var12 = Math.max(var12, 0.0625F);
            var9 = 0.0F;
            var7 = 0.0F;
            var10 = 1.0F;
            var8 = 0.0F;
            var11 = 1.0F;
            var13 = true;
        }

        if ((var6 & 1) != 0)
        {
            var9 = Math.min(var9, 0.9375F);
            var12 = 1.0F;
            var7 = 0.0F;
            var10 = 1.0F;
            var8 = 0.0F;
            var11 = 1.0F;
            var13 = true;
        }

        if (!var13 && this.canBePlacedOn(world.getBlockId(x, y + 1, z)))
        {
            var8 = Math.min(var8, 0.9375F);
            var11 = 1.0F;
            var7 = 0.0F;
            var10 = 1.0F;
            var9 = 0.0F;
            var12 = 1.0F;
        }

        this.setBlockBounds(var7, var8, var9, var10, var11, var12);
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z){
		return null;
	}
	
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int par5){
		switch(par5){
            case 1:
                return canBePlacedOn(world.getBlockId(x, y + 1, z));
            case 2:
                return canBePlacedOn(world.getBlockId(x, y, z + 1));
            case 3:
                return canBePlacedOn(world.getBlockId(x, y, z - 1));
            case 4:
                return canBePlacedOn(world.getBlockId(x + 1, y, z));
            case 5:
                return canBePlacedOn(world.getBlockId(x - 1, y, z));
            default:
                return false;
        }
	}
	
	private boolean canBePlacedOn(int id){
		if(id == 0){
			return false;
		}else{
			Block block = Block.blocksList[id];
			return block.renderAsNormalBlock() && block.blockMaterial.blocksMovement();
		}
	}
	
	private boolean canFlowerStay(World world, int x, int y, int z){
		int meta = world.getBlockMetadata(x, y, z);
        int var6 = meta;

        if(meta > 0){
            for(int i = 0; i <= 3; ++i){
                int var8 = 1 << i;

                if((meta & var8) != 0 && !canBePlacedOn(world.getBlockId(x + Direction.offsetX[i], y, z + Direction.offsetZ[i])) && (world.getBlockId(x, y + 1, z) != this.blockID || (world.getBlockMetadata(x, y + 1, z) & var8) == 0)){
                    var6 &= ~var8;
                }
            }
        }

        if(var6 == 0 && !this.canBePlacedOn(world.getBlockId(x, y + 1, z))){
            return false;
        }else{
            if(var6 != meta){
                world.setBlockMetadataWithNotify(x, y, z, var6);
            }

            return true;
        }
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, int meta){
		if(!world.isRemote && !canFlowerStay(world, x, y, z)){
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockAndMetadata(x, y, z, 0, 0);
		}
	}
	
	public void updateBlockMetadata(World world, int x, int y, int z, int side, float par6, float par7, float par8){
		byte meta = 0;
		
		switch(side){
            case 2:
                meta = 1;
                break;
            case 3:
                meta = 4;
                break;
            case 4:
                meta = 8;
                break;
            case 5:
                meta = 2;
        }

        if(meta != 0){
            world.setBlockMetadataWithNotify(x, y, z, meta);
        }
	}
	
	public int idDropped(int par1, Random rand, int par3){
		return 0;
	}
	
	public int quantityDropped(Random rand){
		return 0;
	}
	
	public boolean isShearable(ItemStack item, World world, int x, int y, int z){
		return true;
	}
	
	public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune){
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1));
		return ret;
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}