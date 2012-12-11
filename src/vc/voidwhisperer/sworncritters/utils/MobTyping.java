package vc.voidwhisperer.sworncritters.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.EntityType;

public class MobTyping {
	static List<EntityType> types;
	public static EntityType getCommonMob()
	{
		types = new ArrayList<EntityType>();
		//types.add(EntityType.BLAZE);
		//types.add(EntityType.GHAST);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.PIG);
		types.add(EntityType.COW);
		types.add(EntityType.SLIME);
		types.add(EntityType.WOLF);
		types.add(EntityType.SHEEP);
		//types.add(EntityType.GIANT);
		//types.add(EntityType.PIG_ZOMBIE);
		types.add(EntityType.ZOMBIE);
		//types.add(EntityType.SILVERFISH);
		types.add(EntityType.SKELETON);
		types.add(EntityType.SPIDER);
		//types.add(EntityType.CAVE_SPIDER);
		Random r = new Random();
		int one = r.nextInt(types.size());
		return types.get(one);
	}
	
	public static EntityType getUncommonMob()
	{
		types = new ArrayList<EntityType>();
		types.add(EntityType.ENDERMAN);
		types.add(EntityType.MAGMA_CUBE);
		types.add(EntityType.CREEPER);
		types.add(EntityType.PIG_ZOMBIE);
		types.add(EntityType.WOLF);
		types.add(EntityType.SILVERFISH);
		types.add(EntityType.MUSHROOM_COW);
		types.add(EntityType.SPIDER);
		Random r = new Random();
		int one = r.nextInt(types.size());
		return types.get(one);
	}
	
	public static EntityType getRareMob()
	{
		types = new ArrayList<EntityType>();
		types.add(EntityType.GHAST);
		types.add(EntityType.BLAZE);
		types.add(EntityType.IRON_GOLEM);
		types.add(EntityType.SNOWMAN);
		types.add(EntityType.VILLAGER);
		types.add(EntityType.OCELOT);
		types.add(EntityType.CREEPER);
		Random r = new Random();
		int one = r.nextInt(types.size());
		return types.get(one);
	}
	
	public static EntityType getPassiveMob()
	{
		types = new ArrayList<EntityType>();
		/*types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.PIG);
		types.add(EntityType.PIG);
		types.add(EntityType.PIG);
		types.add(EntityType.PIG);
		types.add(EntityType.PIG);
		types.add(EntityType.PIG);
		types.add(EntityType.PIG);
		types.add(EntityType.PIG);
		types.add(EntityType.PIG);
		types.add(EntityType.COW);
		types.add(EntityType.COW);
		types.add(EntityType.COW);
		types.add(EntityType.COW);
		types.add(EntityType.COW);
		types.add(EntityType.COW);
		types.add(EntityType.COW);
		types.add(EntityType.SHEEP);
		types.add(EntityType.SHEEP);
		types.add(EntityType.SHEEP);
		types.add(EntityType.SHEEP);
		types.add(EntityType.SHEEP);
		types.add(EntityType.SHEEP);
		types.add(EntityType.SHEEP);
		types.add(EntityType.SHEEP);
		types.add(EntityType.SHEEP);
		types.add(EntityType.SHEEP);
		types.add(EntityType.SHEEP);*/
		//types.add(EntityType.WOLF);
		types.add(EntityType.CHICKEN);
		types.add(EntityType.COW);
		types.add(EntityType.PIG);
		types.add(EntityType.SHEEP);
		types.add(EntityType.MUSHROOM_COW);
		types.add(EntityType.IRON_GOLEM);
		types.add(EntityType.SNOWMAN);
		types.add(EntityType.VILLAGER);
		//types.add(EntityType.OCELOT);
		Random r = new Random();
		int one = r.nextInt(types.size());
		return types.get(one);
	}
	
	public static EntityType getHostileMob()
	{
		types = new ArrayList<EntityType>();
		types.add(EntityType.GHAST);
		types.add(EntityType.BLAZE);
		types.add(EntityType.MAGMA_CUBE);
		types.add(EntityType.SPIDER);
		Random r = new Random();
		int one = r.nextInt(types.size());
		return types.get(one);
	}
}
