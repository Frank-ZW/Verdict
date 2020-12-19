package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInUseEntity;
import me.frankthedev.verdict.util.java.ReflectionUtil;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;

public class VPacketPlayInUseEntity extends APacketPlayInUseEntity {

    private boolean foundEntity;
    private Entity entity;

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInUseEntity) {
            this.handle(((PacketPlayInUseEntity) packet));
        }
    }

    public void handle(PacketPlayInUseEntity packet) {
        this.entityID = ReflectionUtil.getLocalField(PacketPlayInUseEntity.class, packet, "a");
        this.action = EntityUseAction.values()[packet.a().ordinal()];
        this.foundEntity = false;
        if (this.action.isInteractAt()) {
            Vec3D vec3D = packet.b();
            this.blockX = vec3D.a;
            this.blockY = vec3D.b;
            this.blockZ = vec3D.c;
        } else {
            this.blockX = 0.0D;
            this.blockY = 0.0D;
            this.blockZ = 0.0D;
        }
    }

    @Override
    public Entity getEntity(World world) {
        if (!this.foundEntity) {
            this.entity = ((CraftWorld) world).getHandle().a(this.entityID) == null ? null : ((CraftWorld) world).getHandle().a(this.entityID).getBukkitEntity();
            this.foundEntity = true;
        }

        return this.entity;
    }
}
