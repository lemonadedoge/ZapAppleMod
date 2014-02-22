package com.jsn_man.ZapApples;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class CakethingRegistry{
	
	public static void initialize(){
		toppings = new LinkedList();
		bases = new LinkedList();
		damages = new LinkedList();
		toppingTextures = new LinkedList();
		baseTextures = new LinkedList();
		toppingMap = new HashMap<HashableItemStack, String>();
		baseMap = new HashMap<HashableItemStack, String>();
	}
	
	public static void registerTopping(String name, ItemStack stack, int textureIndexBase){
		if(!contains(toppings, stack)){
			toppingCount++;
			toppings.add(stack);
			toppingTextures.add(new CakeTexture(textureIndexBase, 0, textureIndexBase + 16, textureIndexBase + 32));
			toppingMap.put(new HashableItemStack(stack), name);
		}
	}
	
	public static void registerBase(String name, ItemStack stack, int textureIndexBase){
		if(!contains(bases, stack)){
			baseCount++;
			bases.add(stack);
			baseTextures.add(new CakeTexture(0, textureIndexBase, textureIndexBase + 16, textureIndexBase + 32));
			baseMap.put(new HashableItemStack(stack), name);
		}
	}
	
	public static void endRegistry(){		
		int index = 2;
		for(int i = 0; i < bases.size(); i++){
			for(int j = 0; j < toppings.size(); j++){
				ItemStack stack = new ItemStack(ZapApples.pie, 1, index++);
				ItemStack topping = toppings.get(j);
				ItemStack base = bases.get(i);
				damages.add(stack);
				
				if(topping != null){
					GameRegistry.addRecipe(stack, new Object[]{" T ", " C ", 'T', topping, 'C', new ItemStack(ZapApples.pie, 1, getBaseInt(stack))});
				}
				
				if(base != null){
					GameRegistry.addRecipe(stack, new Object[]{" C ", " B ", 'B', base, 'C', new ItemStack(ZapApples.pie, 1, getToppingInt(stack))});
				}
			}
		}
	}
	
	public static ItemStack getTopping(ItemStack stack){
		HashableItemStack hs = getToppingHashable(stack);
		return hs == null ? null : hs.stack;
	}
	
	public static ItemStack getBase(ItemStack stack){
		HashableItemStack hs = getBaseHashable(stack);
		return hs == null ? null : hs.stack;
	}
	
	public static HashableItemStack getToppingHashable(ItemStack stack){
		int index = indexOf(damages, stack);
		if(index != -1){
			return new HashableItemStack(toppings.get(index % toppingCount));
		}else{
			return null;
		}
	}
	
	public static HashableItemStack getBaseHashable(ItemStack stack){
		int index = indexOf(damages, stack);
		if(index != -1){
			return new HashableItemStack(bases.get(index / baseCount));
		}else{
			return null;
		}
	}
	
	public static int getToppingInt(ItemStack stack){
		int index = indexOf(damages, stack);
		return index == -1 ? index : (index % toppingCount) + 2;
	}
	
	public static int getBaseInt(ItemStack stack){
		int index = indexOf(damages, stack);
		return index == -1 ? index : (index / baseCount) + 2;
	}
	
	public static void addInfo(List list, ItemStack stack){
		if(stack.getItemDamage() >= 2){
			list.add(toppingMap.get(getToppingHashable(stack)) + " topping");
			list.add(baseMap.get(getBaseHashable(stack)) + " base");
		}
	}
	
	public static int getTextureIndexOfTopping(int side, int topping, boolean isFull){
		return toppingTextures.get(topping).getTextureIndex(side, isFull);
	}
	
	public static int getTextureIndexOfBase(int side, int base, boolean isFull){
		return baseTextures.get(base).getTextureIndex(side, isFull);
	}
	
	private static boolean contains(List<ItemStack> list, ItemStack stack){
		return indexOf(list, stack) != -1;
	}
	
	private static int indexOf(List<ItemStack> list, ItemStack stack){
		for(int i = 0; i < list.size(); i++){
			ItemStack is = list.get(i);
			if(stack == null && is == null){
				return i;
			}
			if(stack != null && is != null && stack.isItemEqual(is)){
				return i;
			}
		}
		return -1;
	}
	
	public static class CakeTexture{
		public CakeTexture(int textureTop, int textureBottom, int textureSide, int textureEaten){
			top = textureTop;
			bottom = textureBottom;
			side = textureSide;
			eaten = textureEaten;
		}
		
		public int getTextureIndex(int dir, boolean isFull){
			if(dir == 0){
				return bottom;
			}else if(dir == 1){
				return top;
			}else if(!isFull && dir == 4){
				return eaten;
			}else{
				return side;
			}
		}
		
		public int top, bottom, side, eaten;
	};
	
	public static int toppingCount, baseCount = -1;
	public static List<ItemStack> toppings, bases, damages;
	public static HashMap<HashableItemStack, String> toppingMap, baseMap;
	public static List<CakeTexture> toppingTextures, baseTextures;
}