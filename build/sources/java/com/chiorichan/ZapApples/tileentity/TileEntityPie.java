package com.chiorichan.ZapApples.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPie extends TileEntity
{
	public short stage;
	protected boolean hasUpdate;
	
	public void eatSlice( EntityPlayer player )
	{
		player.getFoodStats().addStats( 5, 0.5F );
		if ( stage + 1 >= 4 )
		{
			getBlockType().breakBlock( worldObj, xCoord, yCoord, zCoord, Blocks.air, getBlockMetadata() );
			worldObj.setBlockToAir( xCoord, yCoord, zCoord );
		}
		else
		{
			setData( stage + 1 );
		}
	}
	
	public void setData( int s )
	{
		stage = ( (short) s );
		hasUpdate = true;
	}
	
	@Override
	public boolean canUpdate()
	{
		return true;
	}
	
	@Override
	public void updateEntity()
	{
		if ( ( !worldObj.isRemote ) && ( hasUpdate ) )
		{
			worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
			hasUpdate = false;
		}
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT( tag );
		return new S35PacketUpdateTileEntity( xCoord, yCoord, zCoord, 0, tag );
	}
	
	@Override
	public void onDataPacket( NetworkManager net, S35PacketUpdateTileEntity pkt )
	{
		readFromNBT( pkt.func_148857_g() );
		markForUpdate();
	}
	
	public void markForUpdate()
	{
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
	}
	
	@Override
	public void readFromNBT( NBTTagCompound tag )
	{
		super.readFromNBT( tag );
		
		stage = tag.getShort( "stage" );
	}
	
	@Override
	public void writeToNBT( NBTTagCompound tag )
	{
		super.writeToNBT( tag );
		
		tag.setShort( "stage", stage );
	}
}
