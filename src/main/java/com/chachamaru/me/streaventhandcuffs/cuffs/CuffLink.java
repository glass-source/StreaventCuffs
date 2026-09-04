package com.chachamaru.me.streaventhandcuffs.cuffs;

import com.chachamaru.me.streaventhandcuffs.Streaventhandcuffs;
import com.chachamaru.me.streaventhandcuffs.item.HandCuffsItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class CuffLink {

    public static final String CUFFED_PARTNER = "CuffedPartner";
    public static final String TAG_TYPE = "CuffedType";

    private static final int OFFHAND_SLOT = 40;

    private CuffLink() {
        // Empty constructor
    }

    public static boolean isBound(ItemStack stack) {
        if (!(stack.getItem() instanceof HandCuffsItem)) return false;
        CompoundTag tag = stack.getTag();
        return tag != null && tag.hasUUID(CUFFED_PARTNER);
    }

    public static CuffType getTypeFromPlayer(Player player) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.OFFHAND);
        if (stack.getTag() == null) return CuffType.FRONT;
        return isBound(stack) ? CuffType.getTypeByName(stack.getTag().getString(TAG_TYPE)) : CuffType.FRONT;
    }

    @Nullable
    public static ServerPlayer getPartner(ServerPlayer player) {
        UUID partner = getPartnerUUID(player);
        if (partner == null) return null;
        MinecraftServer server = player.getServer();
        if (server == null) return null;
        return server.getPlayerList().getPlayer(partner);
    }

    @Nullable
    public static UUID getPartnerUUID(Player player) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.OFFHAND);
        if (stack.getTag() == null) return null;
        return isBound(stack) ? stack.getTag().getUUID(CUFFED_PARTNER) : null;
    }

    public static boolean isPlayerCuffed(Player player) {
        return isBound(player.getItemBySlot(EquipmentSlot.OFFHAND));
    }

    public static void bindPlayers(ServerPlayer a, ServerPlayer b, CuffType type) {
        setBind(a, b, type);
        setBind(b, a, type);
        a.inventoryMenu.broadcastChanges();
        b.inventoryMenu.broadcastChanges();
    }

    private static void setBind(ServerPlayer owner, ServerPlayer victim, CuffType type) {
        // A player must end up with exactly one bound stack. Any left over elsewhere in the
        // inventory can be promoted back into the off-hand later by enforceOffHand, which is how
        // the two ends of a link end up carrying different types.
        clearBoundStacks(owner);

        ItemStack displaced = owner.getItemBySlot(EquipmentSlot.OFFHAND).copy();
        owner.getInventory().setItem(OFFHAND_SLOT, ItemStack.EMPTY);

        if (!displaced.isEmpty() && !owner.getInventory().add(displaced)) {
            // The three argument overload creates the ItemEntity directly. Going through
            // drop(stack, boolean) would fire ItemTossEvent, which CuffEvents cancels for cuffed
            // players - and a cancelled toss destroys the stack, since it has already left the
            // inventory by then.
            owner.drop(displaced, false, false);
        }

        // Only now is the bind applied. Bailing out midway used to leave the off-hand cleared and
        // this end of the link unbound, while the other end was already cuffed.
        ItemStack cuffs = new ItemStack(Streaventhandcuffs.HANDCUFFS.get());
        CompoundTag tag = cuffs.getOrCreateTag();
        tag.putUUID(CUFFED_PARTNER, victim.getUUID());
        tag.putString(TAG_TYPE, type.getType());
        owner.getInventory().setItem(OFFHAND_SLOT, cuffs);
    }

    private static void clearBoundStacks(ServerPlayer owner) {
        Inventory inventory = owner.getInventory();

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (isBound(inventory.getItem(i))) {
                inventory.setItem(i, ItemStack.EMPTY);
            }
        }
    }

    public static void enforceOffHand(ServerPlayer player) {
        Inventory inventory = player.getInventory();

        if (isBound(inventory.getItem(OFFHAND_SLOT))) {
            return;
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            if (isBound(stack)) {
                ItemStack movedStack = inventory.getItem(OFFHAND_SLOT).copy();
                inventory.setItem(OFFHAND_SLOT, stack);
                inventory.setItem(i, movedStack);
                player.inventoryMenu.broadcastChanges();
                return;
            }

        }
    }

    public static void releasePlayers(ServerPlayer player) {
        if (!isPlayerCuffed(player)) return;

        ServerPlayer partner = getPartner(player);
        removeCuffs(player);

        if (partner != null) {
            removeCuffs(partner);
        }
    }

    public static void validateLink(ServerPlayer player) {
        if (!isPlayerCuffed(player)) return;

        ServerPlayer partner = getPartner(player);

        if (partner == null
                || !isPlayerCuffed(partner)
                || !player.getUUID().equals(getPartnerUUID(partner))) {
            removeCuffs(player);
        }
    }

    public static void removeCuffs(Player player) {
        if (isBound(player.getItemBySlot(EquipmentSlot.OFFHAND))) {
            player.getInventory().setItem(OFFHAND_SLOT, ItemStack.EMPTY);
            player.inventoryMenu.broadcastChanges();
        }
    }


}
