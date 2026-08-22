package alabaster.crabbersdelight.common.mixin;

import alabaster.crabbersdelight.common.item.component.FishSize;

public final class CuttingBoardSizeContext {
    private static FishSize pendingSize = null;

    private CuttingBoardSizeContext() {
    }

    public static void set(FishSize size) {
        pendingSize = size;
    }

    public static FishSize consume() {
        FishSize size = pendingSize;
        pendingSize = null;
        return size;
    }
}