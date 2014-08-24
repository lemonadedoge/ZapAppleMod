package com.chiorichan.ZapApples.tiles;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FoodStats;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet132TileEntityData;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class TileEntityPie extends TileEntity
{
  public short stage;
  protected boolean hasUpdate;

  public void eatSlice(EntityPlayer player)
  {
    player.getFoodStats().addStats(5, 0.5F);
    if (stage + 1 >= 4)
    {
      getBlockType().breakBlock(worldObj, xCoord, yCoord, zCoord, 0, getBlockMetadata());
      worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    }
    else
    {
      setData(stage + 1);
    }
  }

  public void setData(int s)
  {
    stage = ((short)s);
    hasUpdate = true;
  }

  public void updateEntity()
  {
    if ((!worldObj.isRemote) && (hasUpdate))
    {
      worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
      hasUpdate = false;
    }
  }

  public Packet getDescriptionPacket()
  {
    NBTTagCompound tag = new NBTTagCompound();
    writeToNBT(tag);
    Packet packet = new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    packet.isChunkDataPacket = true;
    return packet;
  }

  public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
  {
    worldObj.getBlockTileEntity(xCoord, yCoord, zCoord).readFromNBT(packet.data);
    worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
  }

  public void readFromNBT(NBTTagCompound tag)
  {
    super.readFromNBT(tag);

    stage = tag.getShort("stage");
  }

  public void writeToNBT(NBTTagCompound tag)
  {
    super.writeToNBT(tag);

    tag.setShort("stage", stage);
  }
}