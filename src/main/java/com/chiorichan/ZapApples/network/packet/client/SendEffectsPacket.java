package com.chiorichan.ZapApples.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class SendEffectsPacket implements IMessage
{
	int x, y, z, meta, msgId;
	Block block;
	
	public SendEffectsPacket( int _msgId, int _x, int _y, int _z, Block _block, int _meta)
	{
		msgId = _msgId;
		x = _x;
		y = _y;
		z = _z;
		block = _block;
		meta = _meta;
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
