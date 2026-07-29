package alabaster.crabbersdelight.common.block.entity;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.container.TackleBoxMenu;
import alabaster.crabbersdelight.common.block.entity.inventory.TackleBoxItemHandler;
import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TackleBoxBlockEntity extends BlockEntity implements MenuProvider {
    private static final Component TACKLE_BOX_NAME = Component.translatable("block.crabbersdelight.tackle_box");

    public final TackleBoxItemHandler handler = new TackleBoxItemHandler();

    public TackleBoxBlockEntity(BlockPos pos, BlockState state) {
        super(CDModBlockEntity.TACKLE_BOX.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("handler", this.handler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.handler.deserializeNBT(registries, tag.getCompound("handler"));
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CDModBlockEntity.TACKLE_BOX.get(),
                (blockEntity, side) -> blockEntity.handler);
    }

    @Override
    public Component getDisplayName() {
        return TACKLE_BOX_NAME;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
        return new TackleBoxMenu(id, playerInv, this.handler, ContainerLevelAccess.NULL);
    }
}