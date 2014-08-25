package com.chiorichan.ZapApples.mobs;

import com.chiorichan.ZapApples.util.ItemDictionary;

import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTimberWolf extends EntityTameable
{
	private float field_70926_e;
	private float field_70924_f;
	private boolean isShaking;
	private boolean field_70928_h;
	private float timeWolfIsShaking;
	private float prevTimeWolfIsShaking;
	
	public EntityTimberWolf(World par1World)
	{
		super( par1World );
		setSize( 1.2F, 1.6F );
		getNavigator().setAvoidsWater( true );
		tasks.addTask( 1, new EntityAISwimming( this ) );
		tasks.addTask( 2, aiSit );
		tasks.addTask( 3, new EntityAILeapAtTarget( this, 0.4F ) );
		tasks.addTask( 4, new EntityAIAttackOnCollide( this, 1.0D, true ) );
		
		tasks.addTask( 6, new EntityAIMate( this, 1.0D ) );
		tasks.addTask( 7, new EntityAIWander( this, 1.0D ) );
		
		tasks.addTask( 9, new EntityAIWatchClosest( this, EntityPlayer.class, 8.0F ) );
		tasks.addTask( 9, new EntityAILookIdle( this ) );
		
		targetTasks.addTask( 3, new EntityAIHurtByTarget( this, true ) );
		
		setTamed( false );
	}
	
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute( SharedMonsterAttributes.movementSpeed ).setBaseValue( 0.300000011920929D );
		
		if ( isTamed() )
		{
			getEntityAttribute( SharedMonsterAttributes.maxHealth ).setBaseValue( 50.0D );
		}
		else
		{
			getEntityAttribute( SharedMonsterAttributes.maxHealth ).setBaseValue( 50.0D );
		}
	}
	
	public boolean isAIEnabled()
	{
		return true;
	}
	
	public void setAttackTarget( EntityLivingBase par1EntityLivingBase )
	{
		super.setAttackTarget( par1EntityLivingBase );
		
		if ( par1EntityLivingBase == null )
		{
			setAngry( false );
		}
		else if ( !isTamed() )
		{
			setAngry( true );
		}
	}
	
	protected void updateAITick()
	{
		dataWatcher.updateObject( 18, Float.valueOf( getHealth() ) );
	}
	
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject( 18, new Float( getHealth() ) );
		dataWatcher.addObject( 19, new Byte( (byte) 0 ) );
		dataWatcher.addObject( 20, new Byte( (byte) 1 ) );
	}
	
	protected void playStepSound( int par1, int par2, int par3, int par4 )
	{
		playSound( "mob.wolf.step", 0.15F, 1.0F );
	}
	
	public void writeEntityToNBT( NBTTagCompound par1NBTTagCompound )
	{
		super.writeEntityToNBT( par1NBTTagCompound );
		par1NBTTagCompound.setBoolean( "Angry", isAngry() );
		par1NBTTagCompound.setByte( "CollarColor", (byte) getCollarColor() );
	}
	
	public void readEntityFromNBT( NBTTagCompound par1NBTTagCompound )
	{
		super.readEntityFromNBT( par1NBTTagCompound );
		setAngry( par1NBTTagCompound.getBoolean( "Angry" ) );
		
		if ( par1NBTTagCompound.hasKey( "CollarColor" ) )
		{
			setCollarColor( par1NBTTagCompound.getByte( "CollarColor" ) );
		}
	}
	
	protected String getLivingSound()
	{
		return rand.nextInt( 3 ) == 0 ? "mob.wolf.panting" : ( isTamed() ) && ( dataWatcher.getWatchableObjectFloat( 18 ) < 10.0F ) ? "mob.wolf.whine" : isAngry() ? "mob.wolf.growl" : "mob.wolf.bark";
	}
	
	protected String getHurtSound()
	{
		return "mob.wolf.hurt";
	}
	
	protected String getDeathSound()
	{
		return "mob.wolf.death";
	}
	
	protected float getSoundVolume()
	{
		return 0.4F;
	}
	
	protected int getDropItemId()
	{
		return -1;
	}
	
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if ( ( !worldObj.isRemote ) && ( isShaking ) && ( !field_70928_h ) && ( !hasPath() ) && ( onGround ) )
		{
			field_70928_h = true;
			timeWolfIsShaking = 0.0F;
			prevTimeWolfIsShaking = 0.0F;
			worldObj.setEntityState( this, (byte) 8 );
		}
	}
	
	public void onUpdate()
	{
		super.onUpdate();
		field_70924_f = field_70926_e;
		
		if ( func_70922_bv() )
		{
			field_70926_e += ( 1.0F - field_70926_e ) * 0.4F;
		}
		else
		{
			field_70926_e += ( 0.0F - field_70926_e ) * 0.4F;
		}
		
		if ( func_70922_bv() )
		{
			numTicksToChaseTarget = 10;
		}
		
		if ( isWet() )
		{
			isShaking = true;
			field_70928_h = false;
			timeWolfIsShaking = 0.0F;
			prevTimeWolfIsShaking = 0.0F;
		}
		else if ( ( ( isShaking ) || ( field_70928_h ) ) && ( field_70928_h ) )
		{
			if ( timeWolfIsShaking == 0.0F )
			{
				playSound( "mob.wolf.shake", getSoundVolume(), ( rand.nextFloat() - rand.nextFloat() ) * 0.2F + 1.0F );
			}
			
			prevTimeWolfIsShaking = timeWolfIsShaking;
			timeWolfIsShaking += 0.05F;
			
			if ( prevTimeWolfIsShaking >= 2.0F )
			{
				isShaking = false;
				field_70928_h = false;
				prevTimeWolfIsShaking = 0.0F;
				timeWolfIsShaking = 0.0F;
			}
			
			if ( timeWolfIsShaking > 0.4F )
			{
				float f = (float) boundingBox.minY;
				int i = (int) ( MathHelper.sin( ( timeWolfIsShaking - 0.4F ) * 3.141593F ) * 7.0F );
				
				for ( int j = 0; j < i; j++ )
				{
					float f1 = ( rand.nextFloat() * 2.0F - 1.0F ) * width * 0.5F;
					float f2 = ( rand.nextFloat() * 2.0F - 1.0F ) * width * 0.5F;
					worldObj.spawnParticle( "splash", posX + f1, f + 0.8F, posZ + f2, motionX, motionY, motionZ );
				}
			}
		}
	}
	
	@SideOnly( Side.CLIENT )
	public boolean getWolfShaking()
	{
		return isShaking;
	}
	
	@SideOnly( Side.CLIENT )
	public float getShadingWhileShaking( float par1 )
	{
		return 0.75F + ( prevTimeWolfIsShaking + ( timeWolfIsShaking - prevTimeWolfIsShaking ) * par1 ) / 2.0F * 0.25F;
	}
	
	@SideOnly( Side.CLIENT )
	public float getShakeAngle( float par1, float par2 )
	{
		float f2 = ( prevTimeWolfIsShaking + ( timeWolfIsShaking - prevTimeWolfIsShaking ) * par1 + par2 ) / 1.8F;
		
		if ( f2 < 0.0F )
		{
			f2 = 0.0F;
		}
		else if ( f2 > 1.0F )
		{
			f2 = 1.0F;
		}
		
		return MathHelper.sin( f2 * 3.141593F ) * MathHelper.sin( f2 * 3.141593F * 11.0F ) * 0.15F * 3.141593F;
	}
	
	@SideOnly( Side.CLIENT )
	public float getInterestedAngle( float par1 )
	{
		return ( field_70924_f + ( field_70926_e - field_70924_f ) * par1 ) * 0.15F * 3.141593F;
	}
	
	public float getEyeHeight()
	{
		return height * 0.8F;
	}
	
	public int getVerticalFaceSpeed()
	{
		return isSitting() ? 20 : super.getVerticalFaceSpeed();
	}
	
	public boolean attackEntityFrom( DamageSource par1DamageSource, float par2 )
	{
		if ( isEntityInvulnerable() )
		{
			return false;
		}
		
		Entity entity = par1DamageSource.getEntity();
		aiSit.setSitting( false );
		
		if ( ( entity != null ) && ( !( entity instanceof EntityPlayer ) ) && ( !( entity instanceof EntityArrow ) ) )
		{
			par2 = ( par2 + 1.0F ) / 2.0F;
		}
		
		return super.attackEntityFrom( par1DamageSource, par2 );
	}
	
	public boolean attackEntityAsMob( Entity par1Entity )
	{
		int i = isTamed() ? 4 : 2;
		return par1Entity.attackEntityFrom( DamageSource.causeMobDamage( this ), i );
	}
	
	public void setTamed( boolean par1 )
	{
		super.setTamed( par1 );
		
		if ( par1 )
		{
			getEntityAttribute( SharedMonsterAttributes.maxHealth ).setBaseValue( 20.0D );
		}
		else
		{
			getEntityAttribute( SharedMonsterAttributes.maxHealth ).setBaseValue( 8.0D );
		}
	}
	
	public boolean interact( EntityPlayer par1EntityPlayer )
	{
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
		
		if ( isTamed() )
		{
			if ( itemstack != null )
			{
				if ( ( itemstack.getItem() instanceof ItemFood ) )
				{
					ItemFood itemfood = (ItemFood) itemstack.getItem();
					
					if ( ( itemfood.isWolfsFavoriteMeat() ) && ( dataWatcher.getWatchableObjectFloat( 18 ) < 20.0F ) )
					{
						if ( !par1EntityPlayer.capabilities.isCreativeMode )
						{
							itemstack.stackSize -= 1;
						}
						
						//heal( itemfood.getHealAmount() ); // XXX Find out what happen to this method.
						
						if ( itemstack.stackSize <= 0 )
						{
							par1EntityPlayer.inventory.setInventorySlotContents( par1EntityPlayer.inventory.currentItem, (ItemStack) null );
						}
						
						return true;
					}
				}
				else if ( itemstack.getItem() == ItemDictionary.dyePowder.getItem() )
				{
					int i = BlockColored.getBlockFromDye( itemstack.getItemDamage() );
					
					if ( i != getCollarColor() )
					{
						setCollarColor( i );
						
						if ( !par1EntityPlayer.capabilities.isCreativeMode )
							if ( --itemstack.stackSize <= 0 )
							{
								par1EntityPlayer.inventory.setInventorySlotContents( par1EntityPlayer.inventory.currentItem, (ItemStack) null );
							}
						
						return true;
					}
				}
			}
			
			if ( ( par1EntityPlayer.getCommandSenderName().equalsIgnoreCase( getOwnerName() ) ) && ( !worldObj.isRemote ) && ( !isBreedingItem( itemstack ) ) )
			{
				aiSit.setSitting( !isSitting() );
				isJumping = false;
				setPathToEntity( (PathEntity) null );
				setTarget( (Entity) null );
				setAttackTarget( (EntityLivingBase) null );
			}
		}
		else if ( ( itemstack != null ) && ( itemstack.getItem() == ItemDictionary.bone.getItem() ) && ( !isAngry() ) )
		{
			if ( !par1EntityPlayer.capabilities.isCreativeMode )
			{
				itemstack.stackSize -= 1;
			}
			
			if ( itemstack.stackSize <= 0 )
			{
				par1EntityPlayer.inventory.setInventorySlotContents( par1EntityPlayer.inventory.currentItem, (ItemStack) null );
			}
			
			if ( !worldObj.isRemote )
			{
				if ( rand.nextInt( 3 ) == 0 )
				{
					setTamed( true );
					setPathToEntity( (PathEntity) null );
					setAttackTarget( (EntityLivingBase) null );
					aiSit.setSitting( true );
					setHealth( 20.0F );
					setOwner( par1EntityPlayer.getCommandSenderName() );
					playTameEffect( true );
					worldObj.setEntityState( this, (byte) 7 );
				}
				else
				{
					playTameEffect( false );
					worldObj.setEntityState( this, (byte) 6 );
				}
			}
			
			return true;
		}
		
		return super.interact( par1EntityPlayer );
	}
	
	@SideOnly( Side.CLIENT )
	public void handleHealthUpdate( byte par1 )
	{
		if ( par1 == 8 )
		{
			field_70928_h = true;
			timeWolfIsShaking = 0.0F;
			prevTimeWolfIsShaking = 0.0F;
		}
		else
		{
			super.handleHealthUpdate( par1 );
		}
	}
	
	@SideOnly( Side.CLIENT )
	public float getTailRotation()
	{
		return isTamed() ? ( 0.55F - ( 20.0F - dataWatcher.getWatchableObjectFloat( 18 ) ) * 0.02F ) * 3.141593F : isAngry() ? 1.53938F : 0.6283186F;
	}
	
	public boolean isBreedingItem( ItemStack par1ItemStack )
	{
		return !( Item.itemsList[par1ItemStack.itemID] instanceof ItemFood ) ? false : par1ItemStack == null ? false : ( (ItemFood) Item.itemsList[par1ItemStack.itemID] ).isWolfsFavoriteMeat();
	}
	
	public int getMaxSpawnedInChunk()
	{
		return 8;
	}
	
	public boolean isAngry()
	{
		return ( dataWatcher.getWatchableObjectByte( 16 ) & 0x2 ) != 0;
	}
	
	public void setAngry( boolean par1 )
	{
		byte b0 = dataWatcher.getWatchableObjectByte( 16 );
		
		if ( par1 )
		{
			dataWatcher.updateObject( 16, Byte.valueOf( (byte) ( b0 | 0x2 ) ) );
		}
		else
		{
			dataWatcher.updateObject( 16, Byte.valueOf( (byte) ( b0 & 0xFFFFFFFD ) ) );
		}
	}
	
	public int getCollarColor()
	{
		return dataWatcher.getWatchableObjectByte( 20 ) & 0xF;
	}
	
	public void setCollarColor( int par1 )
	{
		dataWatcher.updateObject( 20, Byte.valueOf( (byte) ( par1 & 0xF ) ) );
	}
	
	public EntityWolf spawnBabyAnimal( EntityAgeable par1EntityAgeable )
	{
		EntityWolf entitywolf = new EntityWolf( worldObj );
		String s = getOwnerName();
		
		if ( ( s != null ) && ( s.trim().length() > 0 ) )
		{
			entitywolf.setOwner( s );
			entitywolf.setTamed( true );
		}
		
		return entitywolf;
	}
	
	public void func_70918_i( boolean par1 )
	{
		if ( par1 )
		{
			dataWatcher.updateObject( 19, Byte.valueOf( (byte) 1 ) );
		}
		else
		{
			dataWatcher.updateObject( 19, Byte.valueOf( (byte) 0 ) );
		}
	}
	
	public boolean canMateWith( EntityAnimal par1EntityAnimal )
	{
		if ( par1EntityAnimal == this )
		{
			return false;
		}
		if ( !isTamed() )
		{
			return false;
		}
		if ( !( par1EntityAnimal instanceof EntityWolf ) )
		{
			return false;
		}
		
		EntityWolf entitywolf = (EntityWolf) par1EntityAnimal;
		return entitywolf.isTamed();
	}
	
	public boolean func_70922_bv()
	{
		return dataWatcher.getWatchableObjectByte( 19 ) == 1;
	}
	
	protected boolean canDespawn()
	{
		return ( !isTamed() ) && ( ticksExisted > 2400 );
	}
	
	public boolean func_142018_a( EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase )
	{
		if ( ( !( par1EntityLivingBase instanceof EntityCreeper ) ) && ( !( par1EntityLivingBase instanceof EntityGhast ) ) )
		{
			if ( ( par1EntityLivingBase instanceof EntityWolf ) )
			{
				EntityWolf entitywolf = (EntityWolf) par1EntityLivingBase;
				
				if ( ( entitywolf.isTamed() ) && ( entitywolf.func_130012_q() == par2EntityLivingBase ) )
				{
					return false;
				}
			}
			
			return ( !( par1EntityLivingBase instanceof EntityPlayer ) ) || ( !( par2EntityLivingBase instanceof EntityPlayer ) ) || ( ( (EntityPlayer) par2EntityLivingBase ).canAttackPlayer( (EntityPlayer) par1EntityLivingBase ) );
		}
		
		return false;
	}
	
	public EntityAgeable createChild( EntityAgeable par1EntityAgeable )
	{
		return null;
	}
}
