package dev.mrsterner.nyctoplus.common.block.blockentity;

import dev.mrsterner.nyctoplus.common.registry.NPBlockEntityTypes;
import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HellsGateBlockEntity extends BlockEntity {
	private boolean loaded = false;
	public long age = 0;
	public HellsGateBlockEntity( BlockPos blockPos, BlockState blockState) {
		super(NPBlockEntityTypes.HELLS_GATE_BLOCK_ENTITY, blockPos, blockState);
	}

	public static void tick(World world, BlockPos pos, BlockState blockState, HellsGateBlockEntity blockEntity) {
		if (world != null && blockState.isOf(NPObjects.HELLS_GATE_BLOCK)) {
			if (!blockEntity.loaded) {
				blockEntity.markDirty();
				blockEntity.loaded = true;
			}
			blockEntity.age++;
		}
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.age = nbt.getLong(Constants.NBT.AGE);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putLong(Constants.NBT.AGE, this.age);
	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (world != null && !world.isClient) {
			world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
			toUpdatePacket();
		}
	}

	public void sync(World world, BlockPos pos) {
		if (world != null && !world.isClient) {
			world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);

		}
	}

	@Override
	public NbtCompound toSyncedNbt() {
		NbtCompound nbt = super.toSyncedNbt();
		writeNbt(nbt);
		return nbt;
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.of(this);
	}
}
