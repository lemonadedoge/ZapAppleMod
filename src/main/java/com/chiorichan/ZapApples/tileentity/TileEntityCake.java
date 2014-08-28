package com.chiorichan.ZapApples.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCake extends TileEntity
{
	public String base = "plain";
	public String frost = "plain";
	public short stage;
	protected boolean hasUpdate;
	
	public void eatSlice( EntityPlayer player )
	{
		player.getFoodStats().addStats( 5, 0.5F );
		if ( stage + 1 >= 6 )
		{
			//getBlockType().breakBlock( worldObj, xCoord, yCoord, zCoord, 0, getBlockMetadata() );
			worldObj.setBlockToAir( xCoord, yCoord, zCoord );
		}
		else
		{
			setData( base, frost, stage + 1 );
		}
	}
	
	public void setData( String down, String up, int s )
	{
		base = down;
		frost = up;
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
	
	public void readFromNBT( NBTTagCompound tag )
	{
		super.readFromNBT( tag );
		
		base = tag.getString( "base" );
		frost = tag.getString( "frost" );
		stage = tag.getShort( "stage" );
		
		if ( ( frost == null ) || ( frost.isEmpty() ) )
		{
			frost = "plain";
		}
		if ( ( base == null ) || ( base.isEmpty() ) )
			base = "plain";
	}
	
	public void writeToNBT( NBTTagCompound tag )
	{
		super.writeToNBT( tag );
		
		if ( ( base != null ) && ( !base.isEmpty() ) )
			tag.setString( "base", base );
		if ( ( frost != null ) && ( !frost.isEmpty() ) )
			tag.setString( "frost", frost );
		tag.setShort( "stage", stage );
	}
	
	public void readItemNBT( NBTTagCompound tag )
	{
		NBTTagCompound data = tag.getCompoundTag( "Cake" );
		readFromNBT( data );
	}
	
	public NBTTagCompound getItemNBT()
	{
		NBTTagCompound data = new NBTTagCompound();
		writeToNBT( data );
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag( "Cake", data );
		return nbt;
	}
}
