package com.chiorichan.ZapApples.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
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
			getBlockType().breakBlock( worldObj, xCoord, yCoord, zCoord, 0, getBlockMetadata() );
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
	
	public void updateEntity()
	{
		if ( ( !worldObj.isRemote ) && ( hasUpdate ) )
		{
			worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
			hasUpdate = false;
		}
	}
	
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT( tag );
		Packet packet = new S35PacketUpdateTileEntity( xCoord, yCoord, zCoord, 1, tag );
		packet.isChunkDataPacket = true;
		return packet;
	}
	
	public void onDataPacket( INetworkManager net, S35PacketUpdateTileEntity packet )
	{
		worldObj.getTileEntity( xCoord, yCoord, zCoord ).readFromNBT( packet );
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord ); // RenderUpdate
	}
	
	public void readFromNBT( NBTTagCompound tag )
	{
		super.readFromNBT( tag );
		
		stage = tag.getShort( "stage" );
	}
	
	public void writeToNBT( NBTTagCompound tag )
	{
		super.writeToNBT( tag );
		
		tag.setShort( "stage", stage );
	}
}
