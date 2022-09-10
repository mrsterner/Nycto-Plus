package dev.mrsterner.nyctoplus.common.world;

import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPWorldState extends PersistentState {
    public final Map<UUID, BlockPos> link = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList linkList = new NbtList();
        link.forEach(((uuid, blockPos) -> {
            NbtCompound linkTag = new NbtCompound();
            linkTag.putUuid(Constants.NBT.PLAYER, uuid);
            linkTag.put(Constants.NBT.POS, NbtHelper.fromBlockPos(blockPos));
            linkList.add(linkTag);
        }));
        nbt.put(Constants.NBT.LINK, linkList);
        return nbt;
    }

    public void addLink(UUID uuid, BlockPos pos){
        link.put(uuid, pos);
        markDirty();
    }

    public void removeLink(UUID uuid){
        link.remove(uuid);
        markDirty();
    }

    public static NPWorldState readNbt(NbtCompound tag) {
        NPWorldState worldState = new NPWorldState();
        NbtList linkList = tag.getList(Constants.NBT.LINK, 10);
        for (NbtElement nbt : linkList) {
            NbtCompound linkTag = (NbtCompound) nbt;
            worldState.link.put(linkTag.getUuid(Constants.NBT.PLAYER), NbtHelper.toBlockPos(linkTag.getCompound(Constants.NBT.POS)));
        }
        return worldState;
    }

    public static NPWorldState get(World world) {
        return ((ServerWorld) world).getPersistentStateManager().getOrCreate(NPWorldState::readNbt, NPWorldState::new, Constants.MODID + "_universal");
    }
}
