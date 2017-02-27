package net.halalaboos.mcwrapper.impl.mixin.entity.living;

import net.halalaboos.mcwrapper.api.entity.living.data.HealthData;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.potion.PotionEffect;
import net.halalaboos.mcwrapper.impl.mixin.entity.MixinEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.*;

import java.util.Collection;
import java.util.Map;

@Mixin(net.minecraft.entity.EntityLivingBase.class)
@Implements(@Interface(iface = Living.class, prefix = "api$"))
public abstract class MixinEntityLiving extends MixinEntity implements Living {

	@Shadow public abstract float getHealth();
	@Shadow public abstract float getMaxHealth();
	@Shadow public abstract boolean isOnLadder();
	@Shadow protected abstract void shadow$jump();
	@Shadow public abstract net.minecraft.item.ItemStack getHeldItem(EnumHand hand);
	@Shadow public int maxHurtResistantTime;
	@Shadow public float jumpMovementFactor;
	@Shadow protected int activeItemStackUseCount;
	@Shadow @Final private Map<Potion, net.minecraft.potion.PotionEffect> activePotionsMap;

	@Override
	public HealthData getHealthData() {
		return new HealthData(getHealth(), getMaxHealth());
	}

	@Intrinsic
	public void api$jump() {
		shadow$jump();
	}

	@Override
	public boolean isClimbing() {
		return isOnLadder();
	}

	@Override
	public ItemStack getHeldItem(Hand hand) {
		return (ItemStack)(Object)getHeldItem(EnumHand.values()[hand.ordinal()]);
	}

	@Override
	public int getMaxHurtResistantTime() {
		return maxHurtResistantTime;
	}

	@Override
	public float getJumpMovementFactor() {
		return jumpMovementFactor;
	}

	@Override
	public void setJumpMovementFactor(float movementFactor) {
		this.jumpMovementFactor = movementFactor;
	}

	@Override
	public int getItemUseTicks() {
		return activeItemStackUseCount;
	}

	@Override
	public Collection<PotionEffect> getEffects() {
		return ((Collection<PotionEffect>)(Object) activePotionsMap.values());
	}
}
