package com.chiorichan.ZapApples.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class SendEffectsPacket implements IMessage
{
	int x, y, z, meta, msgId;
	Block block;
	
	public SendEffectsPacket()
	{
		
	}
	
	public SendEffectsPacket( int msgId, int x, int y, int z, Block block, int meta )
	{
		this.msgId = msgId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.meta = meta;
	}
	
	@Override
	public void fromBytes( ByteBuf arg0 )
	{
		msgId = arg0.readInt();
		x = arg0.readInt();
		y = arg0.readInt();
		z = arg0.readInt();
		meta = arg0.readInt();
		block = Block.getBlockById( arg0.readInt() );
		
		if ( block == null )
			block = Blocks.air;
	}
	
	@Override
	public void toBytes( ByteBuf arg0 )
	{
		arg0.writeInt( msgId );
		arg0.writeInt( x );
		arg0.writeInt( y );
		arg0.writeInt( z );
		arg0.writeInt( meta );
		arg0.writeInt( Block.getIdFromBlock( block ) );
	}
}
