package net.orcinus.hedgehog.entities.ai.hedgehog;

import com.google.common.collect.Lists;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Items;
import net.orcinus.hedgehog.entities.HedgehogEntity;

import java.util.List;

public class HedgehogPanicGoal extends Goal {
    private final HedgehogEntity hedgehog;

    public HedgehogPanicGoal(HedgehogEntity hedgehog) {
        this.hedgehog = hedgehog;
    }

    @Override
    public boolean canUse() {
        return this.hedgehog.isAlive() && this.isUserWithMilkAlive() != null && this.hedgehog.getScaredTicks() == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    @Override
    public void tick() {
        if (this.isUserWithMilkAlive() != null) {
            this.hedgehog.setTarget(null);
            this.hedgehog.getNavigation().stop();
            this.hedgehog.setJumping(false);
            this.hedgehog.setScaredTicks(100);
        }
    }

    private LivingEntity isUserWithMilkAlive() {
        List<LivingEntity> possibles = Lists.newArrayList();
        List<LivingEntity> list = this.hedgehog.level.getEntitiesOfClass(LivingEntity.class, this.hedgehog.getBoundingBox().inflate(3.0D));
        for (LivingEntity livingEntity : list) {
            if (livingEntity.isAlive()) {
                if (this.isScared(livingEntity)) {
                    possibles.add(livingEntity);
                }
            }
        }
        if (possibles.isEmpty()) return null;

        return possibles.get(hedgehog.getRandom().nextInt(possibles.size()));
    }

    private boolean isScared(LivingEntity livingEntity) {
        return livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(Items.SKELETON_SKULL) || livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(Items.WITHER_SKELETON_SKULL) || livingEntity.getItemInHand(InteractionHand.MAIN_HAND).is(Items.MILK_BUCKET) || livingEntity.getItemInHand(InteractionHand.OFF_HAND).is(Items.MILK_BUCKET);
    }
}
