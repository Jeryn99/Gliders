package net.venturecraft.gliders.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.core.NonNullList;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.item.ItemRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class MixinAnvilMenu {

    @Shadow @Final
    private SimpleContainer inputSlots;

    @Shadow @Final
    private DataSlot cost;

    @Shadow
    private String itemName;

    @Shadow
    public abstract void setItemName(String name);

    @Shadow @Final
    private NonNullList<Slot> slots;

    @Shadow
    private final Player player;

    public MixinAnvilMenu(int syncId, Inventory inv) {
        this.player = inv.player;
    }

    @Inject(method = "createResult", at = @At("TAIL"))
    private void gliders$injectCustomAnvilLogic(CallbackInfo ci) {
        ItemStack left = inputSlots.getItem(0);
        ItemStack right = inputSlots.getItem(1);

        if (!(left.getItem() instanceof GliderItem gliderItem)) return;

        ItemStack result = ItemStack.EMPTY;

        // Glider repair
        if (gliderItem.isValidRepairItem(left, right)) {
            result = left.copy();
            GliderItem.setBroken(result, false);
            result.setDamageValue(0);
            cost.set(5);
        }

        // Copper upgrade
        if (right.getItem() == ItemRegistry.COPPER_UPGRADE.get()) {
            result = GliderEvents.makeResult(left.copy(), "copper");
            cost.set(10);
        }

        // Nether upgrade
        if (right.getItem() == ItemRegistry.NETHER_UPGRADE.get()) {
            result = GliderEvents.makeResult(left.copy(), "nether");
            cost.set(10);
        }

        if (!result.isEmpty()) {
            if (itemName != null && !itemName.isBlank() && !itemName.equals(left.getHoverName().getString())) {
                result.setHoverName(Component.literal(itemName));
            }
            slots.get(2).set(result); // Output slot
        }
    }
}
