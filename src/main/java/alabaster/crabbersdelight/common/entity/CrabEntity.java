package alabaster.crabbersdelight.common.entity;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModEntities;
import alabaster.crabbersdelight.common.registry.ModItems;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CrabEntity extends Animal implements GeoEntity, Bucketable {
    private static final EntityDataAccessor<Integer> VARIANT_ID;
    private static EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private Ingredient temptationItems;

    public CrabEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public Color getCrabColor() {
        return CrabEntity.Color.BY_ID[this.entityData.get(VARIANT_ID)];
    }

    public void setCrabColor(CrabEntity.Color color) {
        this.entityData.set(VARIANT_ID, color.getId());
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(VARIANT_ID));
    }

    public void setColor(DyeColor color) {
        this.entityData.set(VARIANT_ID, color.getId());
    }

    public static boolean checkSpawnRules(EntityType<CrabEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos blockPos, RandomSource randomSource) {
        return level.getBlockState(blockPos.below()).is(CDModTags.CRAB_SPAWN_ON);
    }

    public enum Color {
        WHITE(0, "white", true),
        ORANGE(1, "orange", true),
        MAGENTA(2, "magenta", true),
        LIGHT_BLUE(3, "light_blue", true),
        YELLOW(4, "yellow", true),
        LIME(5, "lime", true),
        PINK(6, "pink", true),
        GRAY(7, "gray", true),
        LIGHT_GRAY(8, "light_gray", true),
        CYAN(9, "cyan", true),
        PURPLE(10, "purple", true),
        BLUE(11, "blue", true),
        BROWN(12, "brown", true),
        GREEN(13, "green", true),
        RED(14, "red", true),
        BLACK(15, "black", true);

        public static final CrabEntity.Color[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(CrabEntity.Color::getId)).toArray(CrabEntity.Color[]::new);
        private final int id;
        private final String name;

        private Color(int j, String name, boolean bool) {
            this.id = j;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public static CrabEntity.Color getTypeById(int id) {
            for (CrabEntity.Color type : values()) {
                if (type.id == id) return type;
            }
            return CrabEntity.Color.BLUE;
        }
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        Holder<Biome> holder = level.getBiome(this.blockPosition());
        if (holder.is(Biomes.MANGROVE_SWAMP)) {
            this.setVariant(11);
        } else if (holder.is(Biomes.BEACH)) {
            this.setVariant(14);
        } else if (holder.is(Biomes.SWAMP)) {
            this.setVariant(13);
        } else if (holder.is(Biomes.STONY_SHORE)) {
            this.setVariant(8);
        } else if (holder.is(Biomes.SNOWY_BEACH)) {
            this.setVariant(0);
        } else {
            this.setVariant(11);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT_ID, Color.BLUE.getId());
        builder.define(FROM_BUCKET, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getCrabColor().getId());
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
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f).build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.75, getTemptationItems(), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0));
    }

    //
    @Nullable
    @Override
    public CrabEntity getBreedOffspring(ServerLevel level, AgeableMob mob) {
        CrabEntity crab = ModEntities.CRAB.get().create(level);
        if (crab != null) {
            crab.setColor(this.getOffspringColor(this, (CrabEntity)mob));
        }
        return crab;
    }

    private static CraftingInput makeCraftInput(DyeColor color1, DyeColor color2) {
        return CraftingInput.of(2, 1, List.of(new ItemStack(DyeItem.byColor(color1)), new ItemStack(DyeItem.byColor(color2))));
    }

    private DyeColor getOffspringColor(Animal father, Animal mother) {
        DyeColor dyecolor = ((CrabEntity)father).getColor();
        DyeColor dyecolor1 = ((CrabEntity)mother).getColor();
        CraftingInput craftinginput = makeCraftInput(dyecolor, dyecolor1);
        return this.level()
                .getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, craftinginput, this.level())
                .map(p_352802_ -> p_352802_.value().assemble(craftinginput, this.level().registryAccess()))
                .map(ItemStack::getItem)
                .filter(DyeItem.class::isInstance)
                .map(DyeItem.class::cast)
                .map(DyeItem::getDyeColor)
                .orElseGet(() -> this.level().random.nextBoolean() ? dyecolor : dyecolor1);
    }

    private static CraftingContainer makeContainer(DyeColor color1, DyeColor color2) {
        CraftingContainer craftingcontainer = new TransientCraftingContainer(new AbstractContainerMenu((MenuType)null, -1) {
            public ItemStack quickMoveStack(Player player, int item) {
                return ItemStack.EMPTY;
            }

            public boolean stillValid(Player player) {
                return false;
            }
        }, 2, 1);
        craftingcontainer.setItem(0, new ItemStack(DyeItem.byColor(color1)));
        craftingcontainer.setItem(1, new ItemStack(DyeItem.byColor(color2)));
        return craftingcontainer;
    }

    private Ingredient getTemptationItems() {
        if (temptationItems == null)
            temptationItems = Ingredient.of(
                    CDModTags.CRAB_TEMPT_ITEM);

        return temptationItems;
    }

    //@Override
    //public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    //    controllers.add(new AnimationController<>(this, "controller", 0, state ->));
    //}

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Nonnull
    public MobCategory getMobType() {
        return MobCategory.CREATURE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT_ID, variant);
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
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
        CompoundTag tag = (CompoundTag) stack.getTags();
        tag.putInt("Age", this.getAge());
        tag.putInt("Variant", this.getCrabColor().getId());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void loadFromBucketTag(@Nonnull CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);

        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));
        }
        int i = tag.getInt("Variant");
        if (i >= 0 && i < CrabEntity.Color.BY_ID.length) {
            this.setCrabColor(CrabEntity.Color.BY_ID[i]);
        }
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

        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        label90: {
            if (!(item instanceof DyeItem)) {
                break label90;
            }

            DyeItem dyeItem = (DyeItem)item;

            DyeColor dyeColor = dyeItem.getDyeColor();
            if (dyeColor != this.getColor()) {
                this.setColor(dyeColor);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    static {
        VARIANT_ID = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);
        FROM_BUCKET = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
    }
}

