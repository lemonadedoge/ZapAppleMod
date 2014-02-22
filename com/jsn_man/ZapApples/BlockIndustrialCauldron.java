package com.jsn_man.ZapApples;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;

public class BlockIndustrialCauldron extends BlockContainer{
	
	public BlockIndustrialCauldron(int id){
		super(id, 49, Material.iron);
		setCreativeTab(CreativeTabs.tabBlock);
		setRequiresSelfNotify();
		disableStats();
	}
	
	public boolean isOpaqueCube(){
		return false;
	}
	
	public boolean renderAsNormalBlock(){
		return false;
	}
	
	public int getRenderType(){
		return ZapApples.idRender2D;
	}
	
	public TileEntity createNewTileEntity(World world){
		return new TileEntityIndustrialCauldron();
	}
	
	public int getBlockTextureFromSideAndMetadata(int side, int meta){
		if(side == 1){
			//top
			if(meta == 1 || meta == 6){
				return blockIndexInTexture + 17;
			}else if(meta == 2 || meta == 7){
				return blockIndexInTexture + 18;
			}else if(meta == 3 || meta == 8){
				return blockIndexInTexture + 33;
			}else if(meta == 4 || meta == 9){
				return blockIndexInTexture + 34;
			}else{
				return blockIndexInTexture + 1;
			}
		}else if(side == 0){
			//bottom
			if(meta > 4){
				return blockIndexInTexture + 3;
			}
			return blockIndexInTexture;
		}else{
			if((meta == 1 || meta == 6) && (side == 5 || side == 3)){
				return blockIndexInTexture + 3;
			}else if((meta == 2 || meta == 7) && (side == 3 || side == 4)){
				return blockIndexInTexture + 3;
			}else if((meta == 3 || meta == 8) && (side == 5 || side == 2)){
				return blockIndexInTexture + 3;
			}else if((meta == 4 || meta == 9) && (side == 2 || side == 4)){
				return blockIndexInTexture + 3;
			}
			return blockIndexInTexture + 2;
		}
	}
	
	public void addCollidingBlockToList(World world, int x, int y, int z, AxisAlignedBB box, List list, Entity entity){
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta < 5){
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
			super.addCollidingBlockToList(world, x, y, z, box, list, entity);
		}
		
		if(meta != 2 && meta != 4 && meta != 7 && meta != 9){
			setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
			super.addCollidingBlockToList(world, x, y, z, box, list, entity);
		}
		
		if(meta != 3 && meta != 4 && meta != 8 && meta != 9){
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
			super.addCollidingBlockToList(world, x, y, z, box, list, entity);
		}
		
		if(meta != 3 && meta != 1 && meta != 8 && meta != 6){
			setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.addCollidingBlockToList(world, x, y, z, box, list, entity);
		}
		
		if(meta != 1 && meta != 2 && meta != 6 && meta != 7){
			setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F);
			super.addCollidingBlockToList(world, x, y, z, box, list, entity);
		}
		
		setBlockBoundsForItemRender();
	}
	
	public void setBlockBoundsForItemRender(){
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void onBlockAdded(World world, int x, int y, int z){
		if(!world.isRemote){
			updatePosition(world, x, y, z);
		}
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, int i){
		if(!world.isRemote){
			updatePosition(world, x, y, z);
		}
	}
	
	public void updatePosition(World world, int x, int y, int z){
		int meta = world.getBlockMetadata(x, y, z);
		int xPlus = world.getBlockId(x + 1, y, z);
		int xMinus = world.getBlockId(x - 1, y, z);
		int zPlus = world.getBlockId(x, y, z + 1);
		int zMinus = world.getBlockId(x, y, z - 1);
		int xPlusMeta = world.getBlockMetadata(x + 1, y, z);
		int xMinusMeta = world.getBlockMetadata(x - 1, y, z);
		int zPlusMeta = world.getBlockMetadata(x, y, z + 1);
		int zMinusMeta = world.getBlockMetadata(x, y, z - 1);
		
		if(xPlus == blockID && zMinus == blockID && world.getBlockId(x + 1, y, z - 1) == blockID && isProperLoose(xPlusMeta, 0, 2, 4) && isProperLoose(zMinusMeta, 0, 1, 2)){
			if(world.getBlockId(x, y - 1, z) == blockID && isProperLoose(xPlusMeta, 5, 7, 9) && isProperFine(zMinusMeta, 5, 6, 7)){
				setMeta(world, x, y, z, 8);
			}else if(isProperLoose(xPlusMeta, 0, 2, 4) && isProperFine(zMinusMeta, 0, 1, 2)){
				setMeta(world, x, y, z, 3);
			}
		}else if(xPlus == blockID && zPlus == blockID && world.getBlockId(x + 1, y, z + 1) == blockID && isProperLoose(xPlusMeta, 0, 2, 4) && isProperLoose(zPlusMeta, 0, 3, 4)){
			if(world.getBlockId(x, y - 1, z) == blockID && isProperFine(xPlusMeta, 5, 7, 9) && isProperFine(zPlusMeta, 5, 8, 9)){
				setMeta(world, x, y, z, 6);
			}else if(isProperFine(xPlusMeta, 0, 2, 4) && isProperFine(zPlusMeta, 0, 3, 4)){
				setMeta(world, x, y, z, 1);
			}
		}else if(xMinus == blockID && zMinus == blockID && world.getBlockId(x - 1, y, z - 1) == blockID && isProperLoose(xMinusMeta, 0, 3, 1) && isProperLoose(zMinusMeta, 0, 1, 2)){
			if(world.getBlockId(x, y - 1, z) == blockID && isProperFine(xMinusMeta, 5, 8, 6) && isProperFine(zMinusMeta, 5, 6, 7)){
				setMeta(world, x, y, z, 9);
			}else if(isProperFine(xMinusMeta, 0, 3, 1) && isProperFine(zMinusMeta, 0, 1, 2)){
				setMeta(world, x, y, z, 4);
			}
		}else if(xMinus == blockID && zPlus == blockID && world.getBlockId(x - 1, y, z + 1) == blockID && isProperLoose(xMinusMeta, 0, 3, 1) && isProperLoose(zPlusMeta, 0, 3, 4)){
			if(world.getBlockId(x, y - 1, z) == blockID && isProperFine(xMinusMeta, 5, 8, 6) && isProperFine(zPlusMeta, 5, 8, 9)){
				setMeta(world, x, y, z, 7);
			}else if(isProperFine(xMinusMeta, 0, 3, 1) && isProperFine(zPlusMeta, 0, 3, 4)){
				setMeta(world, x, y, z, 2);
			}
		}else{
			if(world.getBlockId(x, y - 1, z) == blockID && isProperFine(world.getBlockMetadata(x, y - 1, z), 0, 0, 5)){
				setMeta(world, x, y, z, 5);
				if(meta == 5 && world.getBlockId(x, y + 1, z) == blockID){
					world.setBlockAndMetadataWithNotify(x, y + 1, z, blockID, world.getBlockMetadata(x, y + 1, z));
				}
			}else{
				setMeta(world, x, y, z, 0);
			}
		}
	}
	
	protected void setMeta(World world, int x, int y, int z, int meta){
		if(world.getBlockMetadata(x, y, z) != meta){
			world.setBlockMetadataWithNotify(x, y, z, meta);
			//world.notifyBlockChange(x, y, z, blockID);
		}
	}
	
	protected boolean isProperLoose(int testing, int p1, int p2, int p3){
		return isProperFine(testing, p1, p2, p3) || isProperFine(testing, p1 + 5, p2 + 5, p3 + 5);
	}
	
	protected boolean isProperFine(int testing, int p1, int p2, int p3){
		return testing == 0 || testing == p1 || testing == p2 || testing == p3;
	}
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity){
		if(!world.isRemote && entity instanceof EntityLiving && entity.posY < entity.lastTickPosY && Math.abs(y - entity.posY) <= 0.0625){
			TileEntityIndustrialCauldron tile = (TileEntityIndustrialCauldron)world.getBlockTileEntity(x, y, z);
			
			if(tile != null){
				tile.breakDown();
			}
		}
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9){
		ItemStack stack = player.inventory.getCurrentItem();
		TileEntityIndustrialCauldron tile = (TileEntityIndustrialCauldron)world.getBlockTileEntity(x, y, z);
		if(stack != null && (stack.itemID == ZapApples.zapApple.blockID /*|| stack.itemID == Item.appleRed.shiftedIndex*/)){
			//if(!world.isRemote){
				if(tile.content == null){
					tile.setHoldingItem(stack.itemID, stack.getItemDamage());
					
					if(!player.capabilities.isCreativeMode){
						if(stack.stackSize > 1){
							stack.stackSize--;
						}else{
							stack = null;
						}
						
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
						if(stack != null){
							player.inventory.addItemStackToInventory(stack);
						}
					}
				}
			//}
			return true;
		}else if(stack != null){
			LiquidStack liquid = LiquidContainerRegistry.getLiquidForFilledItem(stack);
			if(liquid != null){
				int qty = tile.fill(ForgeDirection.UNKNOWN, liquid, true);
				
				if(qty != 0 && !player.capabilities.isCreativeMode){
					if(stack.stackSize > 1){
						stack.stackSize--;
					}else{
						stack = null;
					}
					
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					if(stack != null){
						player.inventory.addItemStackToInventory(stack);
					}
				}
				
				return true;
			}else{
				LiquidStack available = tile.getTanks(ForgeDirection.UNKNOWN)[0].getLiquid();
				if(available != null){
					ItemStack filled = LiquidContainerRegistry.fillLiquidContainer(available, stack);
					liquid = LiquidContainerRegistry.getLiquidForFilledItem(filled);
					
					if(liquid != null){
						if(!player.capabilities.isCreativeMode){
							if(stack.stackSize > 1){
								stack.stackSize--;
							}else{
								stack = null;
							}
							
							player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
							if(stack != null){
								player.inventory.addItemStackToInventory(stack);
							}
						}
						tile.drain(ForgeDirection.UNKNOWN, liquid.amount, true);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void fillWithRain(World world, int x, int y, int z){
		if(world.rand.nextInt(20) == 1){
			TileEntityIndustrialCauldron tile = (TileEntityIndustrialCauldron)world.getBlockTileEntity(x, y, z);
			
			if(tile != null){
				tile.fill(0, new LiquidStack(Block.waterStill, LiquidContainerRegistry.BUCKET_VOLUME), true);
			}
		}
	}
	
	public int idDropped(int par1, Random par2Random, int par3){
		return ZapApples.industrialCauldronItem.shiftedIndex;
	}
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list){
		
	}
	
	public int idPicked(World world, int x, int y, int z){
		return ZapApples.industrialCauldronItem.shiftedIndex;
	}
	
	public boolean isLadder(World world, int x, int y, int z){
		return true;
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}