package me.frankthedev.verdict.util.bukkit;

import me.frankthedev.verdict.packet.manager.NMSManager;
import org.bukkit.Material;
import org.bukkit.World;

public class MutableBlockLocation {

    private int x;
    private int y;
    private int z;

    public MutableBlockLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MutableBlockLocation add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public MutableBlockLocation incrementX() {
        return this.add(1, 0, 0);
    }

    public MutableBlockLocation incrementY() {
        return this.add(0, 1, 0);
    }

    public MutableBlockLocation incrementZ() {
        return this.add(0, 0, 1);
    }

    public Material getType(World world) {
        return NMSManager.getInstance().getType(world, this);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MutableBlockLocation)) {
            return false;
        }

        MutableBlockLocation otherLocation = (MutableBlockLocation) other;
        return this.x == otherLocation.getX() && this.y == otherLocation.getY() && this.z == otherLocation.getZ();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.x;
        result = prime * result + this.y;
        result = prime * result + this.z;
        return result;
    }
}
