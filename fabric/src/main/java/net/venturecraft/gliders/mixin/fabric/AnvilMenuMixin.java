package net.venturecraft.gliders.mixin.fabric;



import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.item.ItemRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow
    @Final
    private DataSlot cost;

    protected AnvilMenuMixin(@Nullable MenuType<?> type, int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(type, containerId, playerInventory, access);
    }

    /**
     * Credits to <a href="https://github.com/hiisuuii/infinicore/blob/master/src%2Fmain%2Fjava%2Fhisui%2Finfinicore%2Fmixin%2FInfinicoreMixin.java#L27">Infinicore's Implementation of Anvil Recipes</a>
     * for the correct mixin target.
     */
    @Inject(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isDamageableItem()Z", ordinal = 0), cancellable = true)
    private void upgradeGlider(CallbackInfo ci) {

        ItemStack left = inputSlots.getItem(0);
        ItemStack right = inputSlots.getItem(1);

        if(left.getItem() instanceof GliderItem gliderItem) {

            // Glider Repair
            if (gliderItem.isValidRepairItem(left, right)) {
                ItemStack data = left.copy();
                GliderItem.setBroken(data, false);
                data.setDamageValue(0);
                cost.set(5);
                resultSlots.setItem(0, data);
                ci.cancel();
                return;
            }

            if (right.getItem() == ItemRegistry.COPPER_UPGRADE.get()) {
                ItemStack data = left.copy();
                GliderItem.setCopper(data, true);
                cost.set(10);
                resultSlots.setItem(0, data);
                ci.cancel();
                return;
            }

            if(right.getItem() == ItemRegistry.NETHER_UPGRADE.get()) {
                ItemStack data = left.copy();
                GliderItem.setNether(data, true);
                cost.set(10);
                resultSlots.setItem(0, data);
                ci.cancel();
            }
        }
    }
}