package com.jsn_man.ZapApples;

import java.util.Random;
import java.util.logging.Level;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "ZapApples", name = "Zap Apple Mod", version = "1.6")
@NetworkMod(channels = {"zap"}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class ZapApples{
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event){
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try{
			config.load();
			zapAppleLogID = config.getBlock("zapAppleLogID", 1551).getInt(1551);
			zapAppleSaplingID = config.getBlock("zapAppleSaplingID", 1552).getInt(1552);
			zapAppleLeavesID = config.getBlock("zapAppleLeavesID", 1553).getInt(1553);
			zapAppleFlowersID = config.getBlock("zapAppleFlowersID", 1554).getInt(1554);
			grayAppleID = config.getBlock("grayAppleID", 1555).getInt(1555);
			zapAppleID = config.getBlock("zapAppleID", 1556).getInt(1556);
			jarID = config.getBlock("jarID", 1557).getInt(1557);
			pieID = config.getBlock("pieID", 1558).getInt(1558);
			jamMovingID = config.getBlock("jamMovingID", 1559).getInt(1559);
			jamStillID = config.getBlock("jamStillID", 1560).getInt(1560);
			doughMovingID = config.getBlock("doughMovingID", 1562).getInt(1562);
			doughStillID = config.getBlock("doughStillID", 1563).getInt(1563);
			flourID = config.getBlock("flourID", 1561).getInt(1561);
			industrialCauldronID = config.getBlock("industrailCauldronID", 1564).getInt(1564);
			foodID = config.getItem("foodID", 8370).getInt(8370);
			icingID = config.getItem("icingID", 8371).getInt(8371);
			jamBucketID = config.getItem("jamBucketID", 8372).getInt(8372);
			doughBucketID = config.getItem("doughBucketID", 8373).getInt(8373);
			industrialCauldronItemID = config.getItem("industrialCauldronItemID", 8374).getInt(8374);
			lightningEffect = config.get(Configuration.CATEGORY_GENERAL, "lightningEffect", true).getBoolean(true);
		}catch(Exception e){
			FMLLog.log(Level.SEVERE, e, "Zap Apples could not load its config properly.");
		}finally{
			config.save();
			
			if(jamMovingID + 1 != jamStillID){
				FMLLog.log(Level.SEVERE, "Zap Apples's jamMovingID must be 1 less than the jamStillID. Please fix!");
			}
			
			if(doughMovingID + 1 != doughStillID){
				FMLLog.log(Level.SEVERE, "Zap Apples's doughMovingID must be 1 less than the doughStillID. Please fix!");
			}
			
			MinecraftForge.EVENT_BUS.register(new BonemealHandler());
			MinecraftForge.EVENT_BUS.register(new BucketHandler());
			proxy.registerRenderInformation();
		}
	}
	
	@Init
	public void load(FMLInitializationEvent event){
		zapAppleLog = (BlockZapAppleLog)new BlockZapAppleLog(zapAppleLogID, 0).setHardness(2.0F).setStepSound(Block.soundWoodFootstep).setBlockName("zapAppleLog");
		zapAppleLeaves = (BlockZapAppleLeaves)new BlockZapAppleLeaves(zapAppleLeavesID, 2).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setBlockName("zapAppleLeaves");
		zapAppleSapling = (BlockZapAppleSapling)new BlockZapAppleSapling(zapAppleSaplingID, 3).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("zapAppleSapling");
		zapAppleFlowers = (BlockZapAppleFlowers)new BlockZapAppleFlowers(zapAppleFlowersID, 16).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setBlockName("zapAppleFlowers");
		grayApple = (BlockGrayApple)new BlockGrayApple(grayAppleID, 17).setStepSound(Block.soundGrassFootstep).setBlockName("grayApple");
		zapApple = (BlockZapApple)new BlockZapApple(zapAppleID, 18).setStepSound(Block.soundGrassFootstep).setBlockName("zapApple");
		jar = (BlockJar)new BlockJar(jarID, 36/*245*/).setHardness(0.3F).setStepSound(Block.soundGlassFootstep).setBlockName("jar");
		pie = (BlockCakething)new BlockCakething(pieID, 48).setHardness(0.5F).setStepSound(Block.soundClothFootstep).setBlockName("zapplePie");
		flour = (BlockFlour)new BlockFlour(flourID, 48).setHardness(0.5F).setStepSound(Block.soundSandFootstep).setBlockName("flour");
		industrialCauldron = (BlockIndustrialCauldron)new BlockIndustrialCauldron(industrialCauldronID).setHardness(3.0F).setBlockName("industrialCauldron");
		
		jamStill = (BlockJamStationary)new BlockJamStationary(jamStillID, Material.water).setHardness(100.0F).setLightOpacity(3).setBlockName("jam");
		jamMoving = (BlockJamFlowing)new BlockJamFlowing(jamMovingID, Material.water).setHardness(100.0F).setLightOpacity(3).setBlockName("jam");
		doughStill = (BlockDoughStationary)new BlockDoughStationary(doughStillID, Material.water).setHardness(100.0F).setLightOpacity(3).setBlockName("dough");
		doughMoving = (BlockDoughFlowing)new BlockDoughFlowing(doughMovingID, Material.water).setHardness(100.0F).setLightOpacity(3).setBlockName("dough");
		
		jamFood = (ItemJamFood)new ItemJamFood(foodID, 32).setItemName("jamFood");
		jamBucket = (ItemJamBucket)new ItemJamBucket(jamBucketID, jamMoving.blockID).setIconIndex(jamStill.blockIndexInTexture - 2).setItemName("jam");
		doughBucket = (ItemJamBucket)new ItemJamBucket(doughBucketID, doughMoving.blockID).setIconIndex(doughStill.blockIndexInTexture - 2).setItemName("dough");
		industrialCauldronItem = (ItemIndustrialCauldron)new ItemIndustrialCauldron(industrialCauldronItemID).setIconIndex(flour.blockIndexInTexture + 17).setItemName("industrialCauldron");
		icing = (ItemIcing)new ItemIcing(icingID, 96).setItemName("icing");
		
		CakethingRegistry.initialize();
		
		CakethingRegistry.registerBase("Plain", null, 48);
		CakethingRegistry.registerTopping("Plain", null, 144);
		
		CakethingRegistry.registerBase("Zap Apple Jam", new ItemStack(jamBucket), 49);
		
		for(int i = 0; i < 16; i++){
			CakethingRegistry.registerBase(icings[i], new ItemStack(icing, 1, i), i);
			CakethingRegistry.registerTopping(icings[i], new ItemStack(icing, 1, i), i + 96);
		}
		
		CakethingRegistry.endRegistry();
		
		GameRegistry.registerBlock(jamMoving, "jamMoving");
		GameRegistry.registerBlock(jamStill, "jamStill");
		GameRegistry.registerBlock(doughMoving, "doughMoving");
		GameRegistry.registerBlock(doughStill, "doughStill");
		GameRegistry.registerBlock(zapAppleLog, "zapAppleLog");
		GameRegistry.registerBlock(zapAppleLeaves, "zapAppleLeaves");
		GameRegistry.registerBlock(zapAppleSapling, "zapAppleSapling");
		GameRegistry.registerBlock(zapAppleFlowers, "zapAppleFlowers");
		GameRegistry.registerBlock(flour, "flour");
		GameRegistry.registerBlock(industrialCauldron, "industrialCauldron");
		GameRegistry.registerBlock(grayApple, ItemGrayApple.class, "grayApple");
		GameRegistry.registerBlock(zapApple, ItemZapApple.class, "zapApple");
		GameRegistry.registerBlock(jar, ItemJar.class, "jar");
		GameRegistry.registerBlock(pie, ItemCakething.class, "pie");
		
		GameRegistry.registerItem(jamFood, "jamFood");
		GameRegistry.registerItem(jamBucket, "jamBucket");
		GameRegistry.registerItem(doughBucket, "doughBucket");
		GameRegistry.registerItem(industrialCauldronItem, "industrialCauldronItem");
		GameRegistry.registerItem(icing, "icing");
		
		GameRegistry.registerTileEntity(TileEntityZapAppleLog.class, "ZapAppleLog");
		GameRegistry.registerTileEntity(TileEntityCakething.class, "ZapAppleCakething");
		GameRegistry.registerTileEntity(TileEntityIndustrialCauldron.class, "IndustrialCauldron");
		
		LanguageRegistry.addName(zapAppleLog, "Zap Apple Log");
		LanguageRegistry.addName(zapAppleLeaves, "Zap Apple Leaves");
		LanguageRegistry.addName(zapAppleSapling, "Zapling");
		LanguageRegistry.addName(zapAppleFlowers, "Zap Apple Flowers");
		LanguageRegistry.addName(grayApple, "Premature Zap Apple");
		LanguageRegistry.addName(zapApple, "Zap Apple");
		LanguageRegistry.addName(jamFood, "Zap Apple Bread");
		LanguageRegistry.addName(flour, "Flour");
		LanguageRegistry.addName(industrialCauldron, "Industrial Cauldron");
		LanguageRegistry.addName(industrialCauldronItem, "Industrial Cauldron");
		LanguageRegistry.addName(jamStill, "Zap Apple Jam");
		LanguageRegistry.addName(jamBucket, "Zap Apple Jam");
		LanguageRegistry.addName(doughStill, "Dough");
		LanguageRegistry.addName(doughBucket, "Dough");
		
		LanguageRegistry.instance().addStringLocalization(jar.getBlockName() + ".0.name", "Jar");
		LanguageRegistry.instance().addStringLocalization(jar.getBlockName() + ".1.name", "Zap Apple Jam");
		LanguageRegistry.instance().addStringLocalization(pie.getBlockName() + ".applePie.name", "Apple Pie");
		LanguageRegistry.instance().addStringLocalization(pie.getBlockName() + ".zapplePie.name", "Zap Apple Pie");
		LanguageRegistry.instance().addStringLocalization(pie.getBlockName() + ".cake.name", "Cake");
		
		for(int i = 0; i < 16; i++){
			LanguageRegistry.instance().addStringLocalization(icing.getItemName() + "." + i + ".name", icings[i] + " Icing");
		}
		
		LiquidDictionary.getOrCreateLiquid("jam", new LiquidStack(jamStill, LiquidContainerRegistry.BUCKET_VOLUME));
		LiquidDictionary.getOrCreateLiquid("dough", new LiquidStack(doughStill, LiquidContainerRegistry.BUCKET_VOLUME));
		LiquidDictionary.getOrCreateLiquid("flour", new LiquidStack(flour, LiquidContainerRegistry.BUCKET_VOLUME));
		
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(new LiquidStack(jamStill, LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(jamBucket), new ItemStack(Item.bucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(new LiquidStack(doughStill, LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(doughBucket), new ItemStack(Item.bucketEmpty)));
		//LiquidContainerRegistry.registerLiquid(new LiquidContainerData(new LiquidStack(flour, LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(flour), null));
		
		//EntityRegistry.registerGlobalEntityID(EntityMeteor.class, "ZapMeteor", id);
		//EntityRegistry.registerModEntity(EntityMeteor.class, "ZapMeteor", id, this, 32, 10, true);
		
		GameRegistry.registerWorldGenerator(new TreeGenerator());
		
		GameRegistry.addRecipe(new ItemStack(jar), new Object[] {" I ", "G G", " G ", 'I', Item.ingotIron, 'G', Block.thinGlass});
		GameRegistry.addRecipe(new ItemStack(pie, 1, 1), new Object[]{"WSW", "MJM", "BBB", 'W', Item.wheat, 'S', Item.sugar, 'M', Item.bucketMilk, 'J', new ItemStack(jar, 1, 1), 'B', Item.bread});
		GameRegistry.addRecipe(new ItemStack(industrialCauldronItem), new Object[]{"IGI", "GCG", "III", 'I', Item.ingotIron, 'G', Block.glass, 'C', Item.cauldron});
		GameRegistry.addRecipe(new ItemStack(pie, 1, 2), new Object[]{" M ", "WWW", 'M', Item.bucketMilk, 'W', Item.wheat});
		GameRegistry.addRecipe(new ItemStack(icing, 1, 15), new Object[]{"   ", "SMS", "   ", 'S', Item.sugar, 'M', Item.bucketMilk});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 1, 5), new ItemStack(zapAppleLog));
		GameRegistry.addShapelessRecipe(new ItemStack(jamFood, 3, 0), new ItemStack(Item.bread), new ItemStack(Item.bread), new ItemStack(Item.bread), new ItemStack(jar, 1, 1));
		
		for(int i = 0; i < 16; i++){
			GameRegistry.addShapelessRecipe(new ItemStack(icing, 1, i), new ItemStack(Item.dyePowder, 1, i), new ItemStack(icing, 1, 15));
			GameRegistry.addShapelessRecipe(new ItemStack(icing, 1, 15), new ItemStack(Item.dyePowder, 1, 15), new ItemStack(icing, 1, i));
		}
		
		proxy.addFX();
	}
	
	int zapAppleLogID, zapAppleLeavesID, zapAppleSaplingID, zapAppleFlowersID, grayAppleID, zapAppleID, jarID, pieID, jamMovingID, jamStillID, doughMovingID, doughStillID, flourID, industrialCauldronID, foodID, jamBucketID, doughBucketID, industrialCauldronItemID, icingID;
	public static boolean lightningEffect;
	public static int idRender2D, idRender3D;
	public static BlockZapAppleLog zapAppleLog;
	public static BlockZapAppleLeaves zapAppleLeaves;
	public static BlockZapAppleSapling zapAppleSapling;
	public static BlockZapAppleFlowers zapAppleFlowers;
	public static BlockGrayApple grayApple;
	public static BlockZapApple zapApple;
	public static BlockJar jar;
	public static BlockCakething pie;	
	public static BlockJamStationary jamStill;
	public static BlockJamFlowing jamMoving;
	public static BlockFlour flour;
	public static BlockDoughStationary doughStill;
	public static BlockDoughFlowing doughMoving;
	public static BlockIndustrialCauldron industrialCauldron;
	public static ItemJamFood jamFood;
	public static ItemJamBucket jamBucket, doughBucket;
	public static ItemIndustrialCauldron industrialCauldronItem;
	public static ItemIcing icing;
	public static final String textures = "/com/jsn_man/ZapApples/textures.png", cakeTextures = "/com/jsn_man/ZapApples/cakes.png";
	public static String[] icings = {
		"Black", "Red", "Green", "Chocolate", "Blue", "Purple", "Cyan", "Silver", "Gray", "Pink", "Lime", "Yellow", "Light Blue", "Magenta", "Orange", "Vanilla"
	};
	
	@Instance("ZapApples")
	public static ZapApples instance;
	
	@SidedProxy(clientSide = "com.jsn_man.ZapApples.ProxyClient", serverSide = "com.jsn_man.ZapApples.Proxy")
	public static Proxy proxy;
}