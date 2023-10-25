package alabaster.crabbersdelight.common.entity;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModEntities;
import alabaster.crabbersdelight.common.registry.ModItems;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;

public class CrabEntity extends Animal implements GeoEntity, Bucketable{
    private static final EntityDataAccessor<Integer> VARIANT_ID = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);
    public static final String VARIANT_TAG = "Variant";
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private Ingredient temptationItems;

    public CrabEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT_ID, 0);
        this.entityData.define(FROM_BUCKET, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant().getId());
        compound.putBoolean("FromBucket", this.fromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f).build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, getTemptationItems(), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.5D));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
        return ModEntities.CRAB.get().create(level);
    }

    private Ingredient getTemptationItems() {
        if (temptationItems == null)
            temptationItems = Ingredient.of(
                    CDModTags.CRAB_TEMPT_ITEM);

        return temptationItems;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if (tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.crab.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.crab.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Nonnull
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public Axolotl.Variant getVariant() {
        return Axolotl.Variant.byId(this.entityData.get(VARIANT_ID));
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT_ID, variant);
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean p_148834_) {
        this.entityData.set(FROM_BUCKET, true);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return !stack.isEmpty() && getTemptationItems().test(stack);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void saveToBucketTag(@Nonnull ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("Age", this.getAge());
        tag.putInt("Variant", this.getVariant().getId());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void loadFromBucketTag(@Nonnull CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);

        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));
        }
        entityData.set(VARIANT_ID, tag.getInt("Variant"));
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.CRAB_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_AXOLOTL;
    }

    public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        var result = Bucketable.bucketMobPickup(player, hand, this);
        if (result.isPresent()) {
            return result.get();
        }

        return super.mobInteract(player, hand);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @javax.annotation.Nullable SpawnGroupData spawnData, @javax.annotation.Nullable CompoundTag dataTag) {
        Holder<Biome> holder = level.getBiome(this.blockPosition());
        if (holder.is(BiomeTags.IS_BEACH)) {
            this.setVariant(2);
        } else if (holder.is(BiomeTags.IS_JUNGLE)) {
            this.setVariant(1);
        } else if (holder.is(Biomes.COLD_OCEAN)) {
            this.setVariant(6);
        } else if (holder.is(Biomes.DESERT)) {
            this.setVariant(4);
        } else if (holder.is(BiomeTags.IS_DEEP_OCEAN)) {
            this.setVariant(7);
        } else if (holder.is(Biomes.WARM_OCEAN)) {
            this.setVariant(5);
        } else if (holder.is(Biomes.RIVER)) {
            this.setVariant(3);
        } else {
            this.setVariant(0);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }
}

