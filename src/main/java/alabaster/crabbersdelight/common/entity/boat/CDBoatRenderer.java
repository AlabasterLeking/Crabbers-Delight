package alabaster.crabbersdelight.common.entity.boat;

import alabaster.crabbersdelight.CrabbersDelight;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import java.util.Map;
import java.util.stream.Stream;

public class CDBoatRenderer extends BoatRenderer {
    private final Map<CDBoatEntity.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public CDBoatRenderer(EntityRendererProvider.Context context, boolean chestBoat) {
        super(context, chestBoat);
        this.boatResources = Stream.of(CDBoatEntity.Type.values()).collect(
                ImmutableMap.toImmutableMap(
                        type -> type,
                        type -> Pair.of(
                                new ResourceLocation(CrabbersDelight.MODID, getTextureLocation(type, chestBoat)),
                                this.createBoatModel(context, type, chestBoat)
                        )
                )
        );
    }

    private static String getTextureLocation(CDBoatEntity.Type type, boolean chestBoat) {
        return chestBoat
                ? "textures/entity/chest_boat/" + type.getName() + ".png"
                : "textures/entity/boat/" + type.getName() + ".png";
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, CDBoatEntity.Type type, boolean chestBoat) {
        ModelLayerLocation modelLayerLocation = chestBoat
                ? CDBoatRenderer.createChestBoatModelName(type)
                : CDBoatRenderer.createBoatModelName(type);

        ModelPart modelPart = context.bakeLayer(modelLayerLocation);
        return chestBoat ? new ChestBoatModel(modelPart) : new BoatModel(modelPart);
    }

    public static ModelLayerLocation createBoatModelName(CDBoatEntity.Type type) {
        return createLocation("boat/" + type.getName(), "main");
    }

    public static ModelLayerLocation createChestBoatModelName(CDBoatEntity.Type type) {
        return createLocation("chest_boat/" + type.getName(), "main");
    }

    private static ModelLayerLocation createLocation(String path, String model) {
        return new ModelLayerLocation(new ResourceLocation(CrabbersDelight.MODID, path), model);
    }

    @Override
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        if (boat instanceof CDBoatEntity modBoat) {
            return this.boatResources.get(modBoat.getModVariant());
        } else if (boat instanceof CDChestBoatEntity modChestBoatEntity) {
            return this.boatResources.get(modChestBoatEntity.getModVariant());
        } else {
            return null;
        }
    }
}
