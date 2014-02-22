package com.jsn_man.ZapApples;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCakething extends TileEntity{
	
	public void eatSlice(EntityPlayer player){
		if(base == 0 || base == 1){
			player.getFoodStats().addStats(5, 0.5F);
			if(stage + 1 >= 4){
				getBlockType().breakBlock(worldObj, xCoord, yCoord, zCoord, 0, getBlockMetadata());
				worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
			}else{
				setData(base, top, stage + 1);
			}
		}else{
			player.getFoodStats().addStats(5, 0.5F);
			if(stage + 1 >= 6){
				getBlockType().breakBlock(worldObj, xCoord, yCoord, zCoord, 0, getBlockMetadata());
				worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
			}else{
				setData(base, top, stage + 1);
			}
		}
	}
	
	public void setData(int metadata){
		if(metadata < 2){
			setData(metadata, 0, 0);
		}else{
			ItemStack stack = new ItemStack(ZapApples.pie, 1, metadata);
			setData(CakethingRegistry.getBaseInt(stack), CakethingRegistry.getToppingInt(stack), 0);
		}
	}
	
	public void setData(int down, int up, int s){
		base = (short)down;
		top = (short)up;
		stage = (short)s;
		hasUpdate = true;
	}
	
	public void updateEntity(){
		if(!worldObj.isRemote && hasUpdate){
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			hasUpdate = false;
		}
	}
	
	public Packet getDescriptionPacket(){
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		Packet packet = new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
		packet.isChunkDataPacket = true;
		return packet;
	}
	
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet){
		worldObj.getBlockTileEntity(xCoord, yCoord, zCoord).readFromNBT(packet.customParam1);
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		
		base = tag.getShort("base");
		top = tag.getShort("top");
		stage = tag.getShort("stage");
		
		if(top == 0){
			top = 1;
		}
	}
	
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		
		tag.setShort("base", base);
		tag.setShort("top", top);
		tag.setShort("stage", stage);
	}
	
	public short base, top, stage;
	protected boolean hasUpdate;
}