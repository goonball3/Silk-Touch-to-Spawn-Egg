package goonball3.silktouchtospawnegg.mixin;

import goonball3.silktouchtospawnegg.Config;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(
		method = "dropLoot",
		at = @At("HEAD"),
		cancellable = true
	)
	private void onDropLoot(ServerWorld world, DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
		Config config = Config.getInstance();
		
		if (!config.enabled) {
			return;
		}
		
		LivingEntity entity = (LivingEntity) (Object) this;
		
		if (!(damageSource.getAttacker() instanceof PlayerEntity player)) {
			return;
		}
		ItemStack weapon = player.getMainHandStack();

		if (weapon.isEmpty()) {
			return;
		}

		ServerWorld serverWorld = world;

		var enchantmentRegistry = serverWorld.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
		net.minecraft.enchantment.Enchantment silkTouch = enchantmentRegistry.get(Enchantments.SILK_TOUCH);
		
		if (silkTouch == null) {
			return;
		}
		
		RegistryEntry<net.minecraft.enchantment.Enchantment> silkTouchEntry = 
			enchantmentRegistry.getEntry(silkTouch);
		
		if (EnchantmentHelper.getLevel(silkTouchEntry, weapon) <= 0) {
			return;
		}

		// Generate a random number between 1-100 and check if it's within the chance percentage
		int randomChance = (int) (Math.random() * 100) + 1;
		if (randomChance <= config.chance) {
			EntityType<?> entityType = entity.getType();
			SpawnEggItem spawnEggItem = SpawnEggItem.forEntity(entityType);

			if (spawnEggItem != null) {
				ItemStack spawnEggStack = new ItemStack(spawnEggItem);
				net.minecraft.entity.ItemEntity itemEntity = new net.minecraft.entity.ItemEntity(
					serverWorld,
					entity.getX(),
					entity.getY(),
					entity.getZ(),
					spawnEggStack
				);
				serverWorld.spawnEntity(itemEntity);
			}
		}
	}
}
