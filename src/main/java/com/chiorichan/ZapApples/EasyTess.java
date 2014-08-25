package com.chiorichan.ZapApples;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class EasyTess
{
	//private static final float px = 1F/16F;
	private static int xDimI;
	private static int zDimI;
	
	public static void renderCube( double x, double y, double z, int xDim, int yDim, int zDim, int offsetU, int offsetV, IIcon icon, int flag, int textureWidth, int textureHeight )
	{
		Tessellator tes = Tessellator.instance;
		double minU = icon.getMinU();
		double maxU = icon.getMaxU();
		double minV = icon.getMinV();
		double maxV = icon.getMaxV();
		double pu = ( maxU - minU ) / textureWidth;
		double pv = ( maxV - minV ) / textureHeight;
		
		if ( flag > 5 )
		{
			System.out.println( "Wrong flag (" + flag + ")used when using EasyTess! Using flag 0 instead." );
			flag = 0;
		}
		
		switch ( flag )
		{
			case 3:
			case 5:
				xDimI = zDim;
				zDimI = xDim;
				break;
			default:
				xDimI = xDim;
				zDimI = zDim;
		}
		
		double U = minU + offsetU * pu;
		double V = minV + offsetV * pv;
		
		renderPosX( tes, x, y, z, xDim, yDim, zDim, U, V, pu, pv, flag );
		renderNegX( tes, x, y, z, xDim, yDim, zDim, U, V, pu, pv, flag );
		renderPosZ( tes, x, y, z, xDim, yDim, zDim, U, V, pu, pv, flag );
		renderNegZ( tes, x, y, z, xDim, yDim, zDim, U, V, pu, pv, flag );
		renderPosY( tes, x, y, z, xDim, yDim, zDim, U, V, pu, pv, flag );
		renderNegY( tes, x, y, z, xDim, yDim, zDim, U, V, pu, pv, flag );
	}
	
	public static void renderCube( double x, double y, double z, int xDim, int yDim, int zDim, int offsetU, int offsetV, double moveInwards, IIcon icon, int flag, int textureWidth, int textureHeight )
	{
		Tessellator tes = Tessellator.instance;
		double minU = icon.getMinU();
		double maxU = icon.getMaxU();
		double minV = icon.getMinV();
		double maxV = icon.getMaxV();
		double pu = ( maxU - minU ) / textureWidth;
		double pv = ( maxV - minV ) / textureHeight;
		
		if ( flag > 5 )
		{
			System.out.println( "Wrong flag (" + flag + ")used when using EasyTess! Using flag 0 instead." );
			flag = 0;
		}
		
		switch ( flag )
		{
			case 3:
			case 5:
				xDimI = zDim;
				zDimI = xDim;
				break;
			default:
				xDimI = xDim;
				zDimI = zDim;
		}
		
		double U = minU + offsetU * pu;
		double V = minV + offsetV * pv;
		
		renderPosX( tes, x - moveInwards, y, z, xDim, yDim, zDim, U, V, pu, pv, flag );
		renderNegX( tes, x + moveInwards, y, z, xDim, yDim, zDim, U, V, pu, pv, flag );
		renderPosZ( tes, x, y, z - moveInwards, xDim, yDim, zDim, U, V, pu, pv, flag );
		renderNegZ( tes, x, y, z + moveInwards, xDim, yDim, zDim, U, V, pu, pv, flag );
	}
	
	public static void renderCube( double x, double y, double z, int xDim, int yDim, int zDim, int offsetU, int offsetV, IIcon icon, int flag )
	{
		renderCube( x, y, z, xDim, yDim, zDim, offsetU, offsetV, icon, flag, 16, 16 );
	}
	
	public static void renderPosX( Tessellator tes, double x, double y, double z, double xDim, double yDim, double zDim, double u, double v, double pu, double pv, int flag )
	{
		x += xDim * 0.0625D;
		switch ( flag )
		{
			case 0:
				break;
			case 1:
				v += zDimI * pv;
				break;
			case 2:
				v += zDimI * pv;
				break;
			case 3:
				u += zDimI * pu;
				v += zDimI * pv;
				break;
			case 4:
				u += ( zDimI + xDimI ) * pu;
				v += zDimI * pv;
				break;
			case 5:
				u += ( zDimI + xDimI + zDimI ) * pu;
				v += zDimI * pv;
				break;
		}
		
		double maxU = u + zDim * pu;
		double maxV = v + yDim * pv;
		xDim *= 0.0625D;
		yDim *= 0.0625D;
		zDim *= 0.0625D;
		tes.addVertexWithUV( x, y, z, u, maxV );
		tes.addVertexWithUV( x, y + yDim, z, u, v );
		tes.addVertexWithUV( x, y + yDim, z + zDim, maxU, v );
		tes.addVertexWithUV( x, y, z + zDim, maxU, maxV );
	}
	
	public static void renderNegX( Tessellator tes, double x, double y, double z, double xDim, double yDim, double zDim, double u, double v, double pu, double pv, int flag )
	{
		switch ( flag )
		{
			case 0:
				break;
			case 1:
				v += zDimI * pv;
				break;
			case 2:
				u += ( xDimI + zDimI ) * pu;
				v += zDimI * pv;
				break;
			case 3:
				u += ( zDimI + xDimI + zDimI ) * pu;
				v += zDimI * pv;
				break;
			case 4:
				v += zDimI * pv;
				break;
			case 5:
				u += zDimI * pu;
				v += zDimI * pv;
				break;
		}
		
		double maxU = u + zDim * pu;
		double maxV = v + yDim * pv;
		xDim *= 0.0625D;
		yDim *= 0.0625D;
		zDim *= 0.0625D;
		tes.addVertexWithUV( x, y, z + zDim, maxU, maxV );
		tes.addVertexWithUV( x, y + yDim, z + zDim, maxU, v );
		tes.addVertexWithUV( x, y + yDim, z, u, v );
		tes.addVertexWithUV( x, y, z, u, maxV );
	}
	
	public static void renderPosZ( Tessellator tes, double x, double y, double z, double xDim, double yDim, double zDim, double u, double v, double pu, double pv, int flag )
	{
		x += xDim * 0.0625D;
		z += zDim * 0.0625D;
		switch ( flag )
		{
			case 0:
				break;
			case 1:
				v += zDimI * pv;
				break;
			case 2:
				u += zDimI * pu;
				v += zDimI * pv;
				break;
			case 3:
				u += ( xDimI + zDimI ) * pu;
				v += zDimI * pv;
				break;
			case 4:
				u += ( zDimI + xDimI + zDimI ) * pu;
				v += zDimI * pv;
				break;
			case 5:
				v += zDimI * pv;
				break;
		}
		
		double maxU = u + xDim * pu;
		double maxV = v + yDim * pv;
		xDim *= 0.0625D;
		yDim *= 0.0625D;
		zDim *= 0.0625D;
		tes.addVertexWithUV( x, y, z, u, maxV );
		tes.addVertexWithUV( x, y + yDim, z, u, v );
		tes.addVertexWithUV( x - xDim, y + yDim, z, maxU, v );
		tes.addVertexWithUV( x - xDim, y, z, maxU, maxV );
	}
	
	public static void renderNegZ( Tessellator tes, double x, double y, double z, double xDim, double yDim, double zDim, double u, double v, double pu, double pv, int flag )
	{
		x += xDim * 0.0625D;
		switch ( flag )
		{
			case 0:
				break;
			case 1:
				v += zDimI * pv;
				break;
			case 2:
				u += ( zDimI + xDimI + zDimI ) * pu;
				v += zDimI * pv;
				break;
			case 3:
				v += zDimI * pv;
				break;
			case 4:
				u += zDimI * pu;
				v += zDimI * pv;
				break;
			case 5:
				u += ( xDimI + zDimI ) * pu;
				v += zDimI * pv;
				break;
		}
		
		double maxU = u + xDim * pu;
		double maxV = v + yDim * pv;
		xDim *= 0.0625D;
		yDim *= 0.0625D;
		zDim *= 0.0625D;
		tes.addVertexWithUV( x - xDim, y, z, maxU, maxV );
		tes.addVertexWithUV( x - xDim, y + yDim, z, maxU, v );
		tes.addVertexWithUV( x, y + yDim, z, u, v );
		tes.addVertexWithUV( x, y, z, u, maxV );
	}
	
	public static void renderPosY( Tessellator tes, double x, double y, double z, double xDim, double yDim, double zDim, double u, double v, double pu, double pv, int flag )
	{
		y += yDim * 0.0625D;
		u += zDimI * pu;
		switch ( flag )
		{
			case 0:
				u -= zDimI * pu;
				break;
			case 1:
				u -= zDimI * pu;
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
		}
		
		double maxU = u + xDimI * pu;
		double maxV = v + zDimI * pv;
		
		xDim *= 0.0625D;
		yDim *= 0.0625D;
		zDim *= 0.0625D;
		{
			double U = u;
			double V = maxV;
			switch ( flag )
			{
				case 2:
					break;
				case 3:
					U = u;
					V = v;
					break;
				case 4:
					U = maxU;
					V = v;
					break;
				case 5:
					U = maxU;
					V = maxV;
			}
			tes.addVertexWithUV( x, y, z, U, V );
		}
		{
			double U = u;
			double V = v;
			switch ( flag )
			{
				case 2:
					break;
				case 3:
					U = maxU;
					V = v;
					break;
				case 4:
					U = maxU;
					V = maxV;
					break;
				case 5:
					U = u;
					V = maxV;
			}
			tes.addVertexWithUV( x, y, z + zDim, U, V );
		}
		{
			double U = maxU;
			double V = v;
			switch ( flag )
			{
				case 2:
					break;
				case 3:
					U = maxU;
					V = maxV;
					break;
				case 4:
					U = u;
					V = maxV;
					break;
				case 5:
					U = u;
					V = v;
			}
			tes.addVertexWithUV( x + xDim, y, z + zDim, U, V );
		}
		{
			double U = maxU;
			double V = maxV;
			switch ( flag )
			{
				case 2:
					break;
				case 3:
					U = u;
					V = maxV;
					break;
				case 4:
					U = u;
					V = v;
					break;
				case 5:
					U = maxU;
					V = v;
			}
			tes.addVertexWithUV( x + xDim, y, z, U, V );
		}
	}
	
	public static void renderNegY( Tessellator tes, double x, double y, double z, double xDim, double yDim, double zDim, double u, double v, double pu, double pv, int flag )
	{
		u += ( zDimI + xDimI ) * pu;
		switch ( flag )
		{
			case 0:
				u -= ( zDimI + xDimI ) * pu;
				break;
			case 1:
				u -= ( zDimI + xDimI ) * pu;
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
		}
		
		double maxU = u + xDimI * pu;
		double maxV = v + zDimI * pv;
		xDim *= 0.0625D;
		yDim *= 0.0625D;
		zDim *= 0.0625D;
		{
			double U = maxU;
			double V = maxV;
			switch ( flag )
			{
				case 2:
					break;
				case 3:
					U = u;
					V = maxV;
					break;
				case 4:
					U = u;
					V = v;
					break;
				case 5:
					U = maxU;
					V = v;
			}
			tes.addVertexWithUV( x + xDim, y, z, U, V );
		}
		{
			double U = maxU;
			double V = v;
			switch ( flag )
			{
				case 2:
					break;
				case 3:
					U = maxU;
					V = maxV;
					break;
				case 4:
					U = u;
					V = maxV;
					break;
				case 5:
					U = u;
					V = v;
			}
			tes.addVertexWithUV( x + xDim, y, z + zDim, U, V );
		}
		{
			double U = u;
			double V = v;
			switch ( flag )
			{
				case 2:
					break;
				case 3:
					U = maxU;
					V = v;
					break;
				case 4:
					U = maxU;
					V = maxV;
					break;
				case 5:
					U = u;
					V = maxV;
			}
			tes.addVertexWithUV( x, y, z + zDim, U, V );
		}
		{
			double U = u;
			double V = maxV;
			switch ( flag )
			{
				case 2:
					break;
				case 3:
					U = u;
					V = v;
					break;
				case 4:
					U = maxU;
					V = v;
					break;
				case 5:
					U = maxU;
					V = maxV;
			}
			tes.addVertexWithUV( x, y, z, U, V );
		}
	}
}
