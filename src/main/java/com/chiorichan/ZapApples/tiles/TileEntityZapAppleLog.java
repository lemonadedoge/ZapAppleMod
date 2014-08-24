package com.chiorichan.ZapApples.tiles;

import com.chiorichan.ZapApples.DaytimeManager;
import com.chiorichan.ZapApples.OrderedTriple;
import com.chiorichan.ZapApples.PacketHandler;
import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.blocks.BlockGrayApple;
import com.chiorichan.ZapApples.blocks.BlockZapApple;
import com.chiorichan.ZapApples.blocks.BlockZapAppleFlowers;
import com.chiorichan.ZapApples.blocks.BlockZapAppleLeaves;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLightningBolt;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class TileEntityZapAppleLog extends TileEntity
{
  private Random rand;
  private int index;
  private int delay;
  private List<OrderedTriple> leafCopy;
  private List<OrderedTriple> appleCopy;
  public boolean isFunctional;
  public boolean[] finished;
  public List<OrderedTriple> leafPositions;
  public HashMap<OrderedTriple, Integer> applePositions;

  public void setIsFunctional()
  {
    isFunctional = true;
    leafPositions = new LinkedList();
    applePositions = new HashMap();
    finished = new boolean[6];
    delay = 5;
    rand = new Random();
  }

  public void updateEntity()
  {
    if ((isFunctional) && (--delay <= 0))
    {
      delay = 5;
      int day = DaytimeManager.getDay(worldObj);
      if (day == 0)
      {
        finished[0] = false;
        finished[1] = false;
        finished[2] = false;
        finished[3] = false;
        finished[4] = false;
        finished[5] = false;
      }
      else if ((day == 1) && (finished[0] == 0))
      {
        worldObj.playSoundEffect(xCoord, yCoord, zCoord, "mob.wolf.zaphowl", 1.0F, 1.0F);
        finished[0] = true;
      }
      else if ((day == 2) && (finished[1] == 0))
      {
        if (leafCopy == null)
        {
          index = 0;
          leafCopy = new ArrayList(leafPositions);
          if (ZapApples.lightningEffect)
          {
            int x = xCoord + rand.nextInt(21) - 9;
            int y = yCoord;
            int z = zCoord + rand.nextInt(21) - 9;
            worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, x, y, z));
          }
        }
        if (index >= leafCopy.size())
        {
          index = 0;
          leafCopy = null;
          finished[1] = true;
        }
        else
        {
          OrderedTriple pos = (OrderedTriple)leafCopy.get(index++);
          if ((worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == 0) || (worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == Block.snow.blockID) || (worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == Block.leaves.blockID) || (worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == Block.vine.blockID))
          {
            if ((ZapApples.lightningEffect) && (rand.nextInt(20) == 1))
            {
              int x = xCoord + rand.nextInt(21) - 9;
              int y = yCoord;
              int z = zCoord + rand.nextInt(21) - 9;
              worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, x, y, z));
            }
            worldObj.setBlock(pos.getX(), pos.getY(), pos.getZ(), ZapApples.zapAppleLeaves.blockID);
          }
        }
      }
      else if ((day == 3) && (finished[2] == 0))
      {
        if (appleCopy == null)
        {
          index = 0;
          appleCopy = new ArrayList(applePositions.keySet());
        }
        if (index >= appleCopy.size())
        {
          index = 0;
          appleCopy = null;
          finished[2] = true;
        }
        else
        {
          OrderedTriple pos = (OrderedTriple)appleCopy.get(index++);
          if (((worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == 0) || (worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == Block.snow.blockID) || (worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == Block.leaves.blockID) || (worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == Block.vine.blockID)) && (ZapApples.zapAppleFlowers.canPlaceBlockOnSide(worldObj, pos.getX(), pos.getY(), pos.getZ(), ((Integer)applePositions.get(pos)).intValue())))
          {
            worldObj.setBlock(pos.getX(), pos.getY(), pos.getZ(), ZapApples.zapAppleFlowers.blockID);
            ZapApples.zapAppleFlowers.updateBlockMetadata(worldObj, pos.getX(), pos.getY(), pos.getZ(), ((Integer)applePositions.get(pos)).intValue(), 0.0F, 0.0F, 0.0F);
          }
        }
      }
      else if ((day == 4) && (finished[3] == 0))
      {
        if (appleCopy == null)
        {
          index = 0;
          appleCopy = new ArrayList(applePositions.keySet());
        }
        if (index >= appleCopy.size())
        {
          index = 0;
          appleCopy = null;
          finished[3] = true;
        }
        else
        {
          OrderedTriple pos = (OrderedTriple)appleCopy.get(index++);
          if (worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == ZapApples.zapAppleFlowers.blockID)
          {
            worldObj.setBlock(pos.getX(), pos.getY(), pos.getZ(), ZapApples.grayApple.blockID);
            PacketHandler.sendDestroyEffectToPlayers(worldObj.playerEntities, pos.getX(), pos.getY(), pos.getZ(), ZapApples.zapAppleFlowers.blockID, 0);
            ZapApples.grayApple.updateBlockMetadata(worldObj, pos.getX(), pos.getY(), pos.getZ(), ((Integer)applePositions.get(pos)).intValue(), 0.0F, 0.0F, 0.0F);
          }
        }
      }
      else if ((day == 5) && (finished[4] == 0))
      {
        if (appleCopy == null)
        {
          index = 0;
          appleCopy = new ArrayList(applePositions.keySet());
          if (ZapApples.lightningEffect)
          {
            int x = xCoord + rand.nextInt(21) - 9;
            int y = yCoord;
            int z = zCoord + rand.nextInt(21) - 9;
            worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, x, y, z));
          }
        }
        if (index >= appleCopy.size())
        {
          index = 0;
          appleCopy = null;
          finished[4] = true;
        }
        else
        {
          OrderedTriple pos = (OrderedTriple)appleCopy.get(index++);
          if (worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == ZapApples.grayApple.blockID)
          {
            worldObj.setBlock(pos.getX(), pos.getY(), pos.getZ(), ZapApples.zapApple.blockID, 0, 2);
            PacketHandler.sendDestroyEffectToPlayers(worldObj.playerEntities, pos.getX(), pos.getY(), pos.getZ(), ZapApples.grayApple.blockID, 0);
            ZapApples.zapApple.updateBlockMetadata(worldObj, pos.getX(), pos.getY(), pos.getZ(), ((Integer)applePositions.get(pos)).intValue(), 0.0F, 0.0F, 0.0F);
            if (rand.nextBoolean())
            {
              int x = xCoord + rand.nextInt(21) - 9;
              int y = yCoord;
              int z = zCoord + rand.nextInt(21) - 9;
              worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, x, y, z));
            }
          }
        }
      }
      else if ((day == 6) && (finished[5] == 0))
      {
        if (leafCopy == null)
        {
          index = 0;
          leafCopy = new ArrayList(leafPositions);

          ZapApples.zapApple.isHarvestDay = false;
        }
        if (index >= leafCopy.size())
        {
          index = 0;
          leafCopy = null;
          finished[5] = true;
        }
        else
        {
          OrderedTriple pos = (OrderedTriple)leafCopy.get(index++);
          if (worldObj.getBlockId(pos.getX(), pos.getY(), pos.getZ()) == ZapApples.zapAppleLeaves.blockID)
          {
            ZapApples.zapAppleLeaves.removeLeaves(worldObj, pos.getX(), pos.getY(), pos.getZ());
          }
        }
      }

      ZapApples.zapApple.isHarvestDay = (day == 5);
    }
  }

  public void readFromNBT(NBTTagCompound tag)
  {
    super.readFromNBT(tag);

    isFunctional = tag.getBoolean("isFunctional");

    if (isFunctional)
    {
      setIsFunctional();
      index = tag.getInteger("index");

      finished[0] = tag.getBoolean("finished[0]");
      finished[1] = tag.getBoolean("finished[1]");
      finished[2] = tag.getBoolean("finished[2]");
      finished[3] = tag.getBoolean("finished[3]");
      finished[4] = tag.getBoolean("finished[4]");
      finished[5] = tag.getBoolean("finished[5]");

      NBTTagCompound leaves = tag.getCompoundTag("leaves");
      int leafSize = leaves.getInteger("size");
      for (int i = 0; i < leafSize; i++)
      {
        leafPositions.add(OrderedTriple.valueOf(leaves.getString("" + i)));
      }

      NBTTagCompound apples = tag.getCompoundTag("apples");
      int appleSize = apples.getInteger("size");
      for (int i = 0; i < appleSize; i++)
      {
        applePositions.put(OrderedTriple.valueOf(apples.getString("key:" + i)), Integer.valueOf(apples.getInteger("value:" + i)));
      }
    }
  }

  public void writeToNBT(NBTTagCompound tag)
  {
    super.writeToNBT(tag);

    tag.setBoolean("isFunctional", isFunctional);

    if (isFunctional)
    {
      tag.setInteger("index", index);

      tag.setBoolean("finished[0]", finished[0]);
      tag.setBoolean("finished[1]", finished[1]);
      tag.setBoolean("finished[2]", finished[2]);
      tag.setBoolean("finished[3]", finished[3]);
      tag.setBoolean("finished[4]", finished[4]);
      tag.setBoolean("finished[5]", finished[5]);

      NBTTagCompound leaves = new NBTTagCompound();
      leaves.setInteger("size", leafPositions.size());
      for (int i = 0; i < leafPositions.size(); i++)
      {
        leaves.setString("" + i, ((OrderedTriple)leafPositions.get(i)).toString());
      }
      tag.setCompoundTag("leaves", leaves);

      NBTTagCompound apples = new NBTTagCompound();
      List temp = new ArrayList(applePositions.keySet());
      apples.setInteger("size", temp.size());
      for (int i = 0; i < temp.size(); i++)
      {
        apples.setString("key:" + i, ((OrderedTriple)temp.get(i)).toString());
        apples.setInteger("value:" + i, ((Integer)applePositions.get(temp.get(i))).intValue());
      }
      tag.setCompoundTag("apples", apples);
    }
  }
}