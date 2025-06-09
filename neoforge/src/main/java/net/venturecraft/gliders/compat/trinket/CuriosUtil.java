package net.venturecraft.gliders.compat.trinket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsSlotInv;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CuriosUtil extends CuriosTrinketsUtil {

    public static void init(IEventBus eventBus) {
        CuriosTrinketsUtil.setInstance(new CuriosUtil());
        eventBus.addListener(CuriosUtil::interModQueue);
    }

    public static void interModQueue(InterModEnqueueEvent e) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("glider").size(1).icon(VCGliders.id("item/glider_slot")).build());
    }

    @Override
    public boolean isCurios() {
        return true;
    }

    @Override
    public CuriosTrinketsSlotInv getSlot(LivingEntity entity, String slot) {
        final CuriosTrinketsSlotInv[] slotHandler = {CuriosTrinketsSlotInv.EMPTY};
        CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(curios -> {
            curios.getStacksHandler(slot).ifPresent(stacks -> {
                slotHandler[0] = new SlotInv(stacks.getStacks());
            });
        });
        return slotHandler[0];
    }

    public static class SlotInv implements CuriosTrinketsSlotInv {

        private final IDynamicStackHandler stackHandler;

        public SlotInv(IDynamicStackHandler stackHandler) {
            this.stackHandler = stackHandler;
        }

        @Override
        public int getSlots() {
            return this.stackHandler.getSlots();
        }

        @Override
        public ItemStack getStackInSlot(int index) {
            return this.stackHandler.getStackInSlot(index);
        }

        @Override
        public void setStackInSlot(int index, ItemStack stack) {
            this.stackHandler.setStackInSlot(index, stack);
        }
    }


}