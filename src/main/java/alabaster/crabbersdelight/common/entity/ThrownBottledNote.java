package alabaster.crabbersdelight.common.entity;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.item.component.SignedNoteContent;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import alabaster.crabbersdelight.common.registry.CDModEntities;
import alabaster.crabbersdelight.common.registry.CDModItems;
import alabaster.crabbersdelight.common.world.BottledNoteSavedData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownBottledNote extends ThrowableItemProjectile {
    private String title = "";
    private String text = "";
    private String author = "";
    private int generation = 0;
    private ItemStack reward = ItemStack.EMPTY;
    private boolean resolved = false;

    public ThrownBottledNote(EntityType<? extends ThrownBottledNote> type, Level level) {
        super(type, level);
    }

    public ThrownBottledNote(Level level, LivingEntity thrower, String title, String text, String author, int generation) {
        super(CDModEntities.THROWN_BOTTLED_NOTE.get(), thrower, level);
        this.title = title;
        this.text = text;
        this.author = author;
        this.generation = generation;
        CrabbersDelight.LOGGER.info("ThrownBottledNote spawned by {} at {}", author, position());
    }

    public void setReward(ItemStack reward) {
        this.reward = reward;
    }

    @Override
    protected Item getDefaultItem() {
        return CDModItems.BOTTLED_NOTE.get();
    }

    @Override
    public void tick() {
        super.tick();
        if (!resolved && !level().isClientSide && isInWater()) {
            CrabbersDelight.LOGGER.info("ThrownBottledNote resolving via tick() water check at tick {} pos {}", tickCount, position());
            resolved = true;
            landInWater((ServerLevel) level());
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (resolved || level().isClientSide) {
            return;
        }
        resolved = true;

        ServerLevel serverLevel = (ServerLevel) level();
        boolean inWater = isInWater();
        CrabbersDelight.LOGGER.info("ThrownBottledNote onHit fired at tick {} pos {} isInWater={} hitResult={}", tickCount, position(), inWater, result);
        if (inWater) {
            landInWater(serverLevel);
        } else {
            ItemStack fallback = new ItemStack(CDModItems.SIGNED_NOTE.get());
            fallback.set(CDModDataComponents.SIGNED_NOTE_CONTENT.get(), new SignedNoteContent(title, text, author, generation));
            serverLevel.addFreshEntity(new ItemEntity(serverLevel, position().x, position().y, position().z, fallback));
            if (!reward.isEmpty()) {
                serverLevel.addFreshEntity(new ItemEntity(serverLevel, position().x, position().y, position().z, reward.copy()));
            }
            discard();
        }
    }

    private void landInWater(ServerLevel serverLevel) {
        BottledNoteSavedData.get(serverLevel).addNote(title, text, author, generation, reward.copy());
        serverLevel.sendParticles(ParticleTypes.SPLASH, position().x, position().y, position().z, 8, 0.2, 0.1, 0.2, 0.05);
        discard();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("NoteTitle", title);
        tag.putString("NoteText", text);
        tag.putString("NoteAuthor", author);
        tag.putInt("NoteGeneration", generation);
        if (!reward.isEmpty() && level() != null) {
            tag.put("NoteReward", reward.save(level().registryAccess()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        title = tag.getString("NoteTitle");
        text = tag.getString("NoteText");
        author = tag.getString("NoteAuthor");
        generation = tag.getInt("NoteGeneration");
        if (tag.contains("NoteReward") && level() != null) {
            reward = ItemStack.parseOptional(level().registryAccess(), tag.getCompound("NoteReward"));
        }
    }
}