package kacov.totemoptimizer.mixin;

import kacov.totemoptimizer.TotemOptimizer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public class OffhandBlockerMixin {

    @Inject(
            method = "keyPressed",
            at = @At("HEAD"),
            cancellable = true
    )
    private void blockOffhandSwap(int keyCode, int scanCode, int modifiers,
                                  CallbackInfoReturnable<Boolean> cir) {

        if (!TotemOptimizer.enabled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        
        KeyBinding swapKey = client.options.swapHandsKey;
        if (!swapKey.matchesKey(keyCode, scanCode)) return;

        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;

        
        Slot hovered = ((HandledScreenAccessor) screen).getFocusedSlot();
        if (hovered == null) return;

        boolean slotEmpty = hovered.getStack().isEmpty();
        boolean offhandTotem = client.player
                .getEquippedStack(EquipmentSlot.OFFHAND)
                .isOf(Items.TOTEM_OF_UNDYING);

        
        if (slotEmpty && offhandTotem) {
            cir.setReturnValue(true); // cancel the F action
        }
    }
}
