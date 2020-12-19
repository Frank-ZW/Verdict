package me.frankthedev.verdict.util.bukkit;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

public class BlockUtil {

    private static final Set<Material> blockSolidPassSet = new HashSet<>();
    private static final Set<Material> blockStairsSet = new HashSet<>();
    private static final Set<Material> blockLiquidsSet = new HashSet<>();
    private static final Set<Material> blockWebsSet = new HashSet<>();
    private static final Set<Material> blockIceSet = new HashSet<>();
    private static final Set<Material> blockClimableSet = new HashSet<>();
    private static final Set<Material> blockSlimeSet = new HashSet<>();
    private static final Set<Material> badVelocitySet = new HashSet<>();
    private static final Set<Material> badShapesSet = new HashSet<>();

    static {
        blockSolidPassSet.add(Material.AIR);
        blockSolidPassSet.add(Material.SAPLING);
        blockSolidPassSet.add(Material.WATER);
        blockSolidPassSet.add(Material.STATIONARY_WATER);
        blockSolidPassSet.add(Material.LAVA);
        blockSolidPassSet.add(Material.STATIONARY_LAVA);
        blockSolidPassSet.add(Material.POWERED_RAIL);
        blockSolidPassSet.add(Material.DETECTOR_RAIL);
        blockSolidPassSet.add(Material.DEAD_BUSH);
        blockSolidPassSet.add(Material.WEB);
        blockSolidPassSet.add(Material.GRASS);
        blockSolidPassSet.add(Material.LONG_GRASS);
        blockSolidPassSet.add(Material.YELLOW_FLOWER);
        blockSolidPassSet.add(Material.RED_ROSE);
        blockSolidPassSet.add(Material.RED_MUSHROOM);
        blockSolidPassSet.add(Material.BROWN_MUSHROOM);
        blockSolidPassSet.add(Material.TORCH);
        blockSolidPassSet.add(Material.REDSTONE_TORCH_ON);
        blockSolidPassSet.add(Material.REDSTONE_TORCH_OFF);
        blockSolidPassSet.add(Material.FIRE);
        blockSolidPassSet.add(Material.REDSTONE_WIRE);
        blockSolidPassSet.add(Material.CROPS);
        blockSolidPassSet.add(Material.WALL_SIGN);
        blockSolidPassSet.add(Material.SIGN);
        blockSolidPassSet.add(Material.RAILS);
        blockSolidPassSet.add(Material.LEVER);
        blockSolidPassSet.add(Material.STONE_PLATE);
        blockSolidPassSet.add(Material.GOLD_PLATE);
        blockSolidPassSet.add(Material.IRON_PLATE);
        blockSolidPassSet.add(Material.WOOD_PLATE);
        blockSolidPassSet.add(Material.STONE_BUTTON);
        blockSolidPassSet.add(Material.WOOD_BUTTON);
        blockSolidPassSet.add(Material.SNOW);
        blockSolidPassSet.add(Material.SUGAR_CANE_BLOCK);
        blockSolidPassSet.add(Material.PORTAL);
        blockSolidPassSet.add(Material.ENDER_PORTAL);
        blockSolidPassSet.add(Material.DIODE_BLOCK_ON);
        blockSolidPassSet.add(Material.DIODE_BLOCK_OFF);
        blockSolidPassSet.add(Material.PUMPKIN_STEM);
        blockSolidPassSet.add(Material.MELON_STEM);
        blockSolidPassSet.add(Material.WATER_LILY);
        blockSolidPassSet.add(Material.TRIPWIRE);
        blockSolidPassSet.add(Material.TRIPWIRE_HOOK);
        blockSolidPassSet.add(Material.CARROT);
        blockSolidPassSet.add(Material.REDSTONE_COMPARATOR_ON);
        blockSolidPassSet.add(Material.REDSTONE_COMPARATOR_OFF);
        blockSolidPassSet.add(Material.ACTIVATOR_RAIL);
        blockSolidPassSet.add(Material.CARPET);

        blockStairsSet.add(Material.DARK_OAK_STAIRS);
        blockStairsSet.add(Material.COBBLESTONE_STAIRS);
        blockStairsSet.add(Material.BRICK_STAIRS);
        blockStairsSet.add(Material.ACACIA_STAIRS);
        blockStairsSet.add(Material.BIRCH_WOOD_STAIRS);
        blockStairsSet.add(Material.JUNGLE_WOOD_STAIRS);
        blockStairsSet.add(Material.NETHER_BRICK_STAIRS);
        blockStairsSet.add(Material.QUARTZ_STAIRS);
        blockStairsSet.add(Material.RED_SANDSTONE_STAIRS);
        blockStairsSet.add(Material.SANDSTONE_STAIRS);
        blockStairsSet.add(Material.SMOOTH_STAIRS);
        blockStairsSet.add(Material.SPRUCE_WOOD_STAIRS);
        blockStairsSet.add(Material.WOOD_STAIRS);
        blockStairsSet.add(Material.STONE_SLAB2);

        blockLiquidsSet.add(Material.WATER);
        blockLiquidsSet.add(Material.LAVA);

        blockWebsSet.add(Material.WEB);

        blockIceSet.add(Material.ICE);
        blockIceSet.add(Material.PACKED_ICE);

        blockClimableSet.add(Material.LADDER);
        blockClimableSet.add(Material.VINE);

        blockSlimeSet.add(Material.SLIME_BLOCK);

        badVelocitySet.add(Material.WATER);
        badVelocitySet.add(Material.STATIONARY_WATER);
        badVelocitySet.add(Material.LAVA);
        badVelocitySet.add(Material.STATIONARY_LAVA);
        badVelocitySet.add(Material.WEB);
        badVelocitySet.add(Material.SLIME_BLOCK);
        badVelocitySet.add(Material.LADDER);
        badVelocitySet.add(Material.TRAP_DOOR);
        badVelocitySet.add(Material.IRON_TRAPDOOR);
        badVelocitySet.add(Material.VINE);
        badVelocitySet.add(Material.PISTON_EXTENSION);
        badVelocitySet.add(Material.PISTON_MOVING_PIECE);
        badVelocitySet.add(Material.SNOW);
        badVelocitySet.add(Material.FENCE);
        badVelocitySet.add(Material.STONE_SLAB2);
        badVelocitySet.add(Material.SOUL_SAND);
        badVelocitySet.add(Material.CHEST);
        badVelocitySet.add(Material.BOAT);
        badVelocitySet.add(Material.ICE);
        badVelocitySet.add(Material.PACKED_ICE);
        badVelocitySet.add(Material.HOPPER);
        badVelocitySet.add(Material.FLOWER_POT);
        badVelocitySet.add(Material.SKULL);
        badVelocitySet.add(Material.SKULL_ITEM);

        badShapesSet.addAll(badVelocitySet);
        badShapesSet.addAll(blockLiquidsSet);
        badShapesSet.add(Material.SNOW);
        badShapesSet.add(Material.STONE_SLAB2);
        badShapesSet.add(Material.STEP);
        badShapesSet.add(Material.CARPET);
        badShapesSet.add(Material.WOOD_STEP);
        badShapesSet.add(Material.CHEST);
        badShapesSet.add(Material.ENDER_CHEST);
        badShapesSet.add(Material.TRAPPED_CHEST);
        badShapesSet.add(Material.ENDER_PORTAL_FRAME);
        badShapesSet.add(Material.SLIME_BLOCK);
        badShapesSet.add(Material.TRAP_DOOR);
        badShapesSet.add(Material.IRON_TRAPDOOR);
        badShapesSet.add(Material.WATER_LILY);
        badShapesSet.add(Material.REDSTONE_COMPARATOR);
        badShapesSet.add(Material.REDSTONE_COMPARATOR_ON);
        badShapesSet.add(Material.REDSTONE_COMPARATOR_OFF);
        badShapesSet.add(Material.CAULDRON);
        badShapesSet.add(Material.FENCE);
        badShapesSet.add(Material.DARK_OAK_FENCE);
        badShapesSet.add(Material.SPRUCE_FENCE);
        badShapesSet.add(Material.JUNGLE_FENCE);
        badShapesSet.add(Material.BIRCH_FENCE);
        badShapesSet.add(Material.ACACIA_FENCE);
        badShapesSet.add(Material.IRON_FENCE);
        badShapesSet.add(Material.NETHER_FENCE);
        badShapesSet.add(Material.JUNGLE_FENCE_GATE);
        badShapesSet.add(Material.FENCE_GATE);
        badShapesSet.add(Material.ACACIA_FENCE_GATE);
        badShapesSet.add(Material.BIRCH_FENCE_GATE);
        badShapesSet.add(Material.SPRUCE_FENCE_GATE);
        badShapesSet.add(Material.DARK_OAK_FENCE_GATE);
        badShapesSet.add(Material.HOPPER);
        badShapesSet.add(Material.BREWING_STAND);
        badShapesSet.add(Material.BREWING_STAND_ITEM);
        badShapesSet.add(Material.COBBLE_WALL);
        badShapesSet.add(Material.DIODE_BLOCK_ON);
        badShapesSet.add(Material.DIODE_BLOCK_OFF);
        badVelocitySet.add(Material.CACTUS);
        badVelocitySet.add(Material.CAKE_BLOCK);
        badVelocitySet.add(Material.FLOWER_POT);
        badVelocitySet.add(Material.ENCHANTMENT_TABLE);
        badShapesSet.add(Material.SKULL);
        badShapesSet.add(Material.SKULL_ITEM);
        badShapesSet.add(Material.STAINED_GLASS_PANE);
    }

    public static boolean isOnGround(Location location, int down) {
        double posX = location.getX();
        double posZ = location.getZ();
        double fracX = posX % 1.0D > 0.0D ? Math.abs(posX % 1.0D) : 1.0D - Math.abs(posX % 1.0D);
        double fracZ = posZ % 1.0D > 0.0D ? Math.abs(posZ % 1.0D) : 1.0D - Math.abs(posZ % 1.0D);
        int blockX = location.getBlockX();
        int blockY = location.getBlockY() - down;
        int blockZ = location.getBlockZ();
        World world = location.getWorld();
        if (world == null) {
            return false;
        }

        if (!blockSolidPassSet.contains(world.getBlockAt(blockX, blockY, blockZ).getType())) {
            return true;
        }

        if (fracX < 0.3D) {
            if (!blockSolidPassSet.contains(world.getBlockAt(blockX - 1, blockY, blockZ).getType())) {
                return true;
            }

            if (fracZ < 0.3D) {
                if (!blockSolidPassSet.contains(world.getBlockAt(blockX - 1, blockY, blockZ - 1).getType())) {
                    return true;
                }

                if (!blockSolidPassSet.contains(world.getBlockAt(blockX, blockY, blockZ - 1).getType())) {
                    return true;
                }

                return !blockSolidPassSet.contains(world.getBlockAt(blockX + 1, blockY, blockZ - 1).getType());
            } else if (fracZ > 0.7D) {
                if (!blockSolidPassSet.contains(world.getBlockAt(blockX - 1, blockY, blockZ + 1).getType())) {
                    return true;
                }

                if (!blockSolidPassSet.contains(world.getBlockAt(blockX, blockY, blockZ + 1).getType())) {
                    return true;
                }

                return !blockSolidPassSet.contains(world.getBlockAt(blockX + 1, blockY, blockZ + 1).getType());
            }
        } else if (fracX > 0.7D) {
            if (!blockSolidPassSet.contains(world.getBlockAt(blockX + 1, blockY, blockZ).getType())) {
                return true;
            }

            if (fracZ < 0.3D) {
                if (!blockSolidPassSet.contains(world.getBlockAt(blockX - 1, blockY, blockZ - 1).getType())) {
                    return true;
                }

                if (!blockSolidPassSet.contains(world.getBlockAt(blockX, blockY, blockZ - 1).getType())) {
                    return true;
                }

                return !blockSolidPassSet.contains(world.getBlockAt(blockX + 1, blockY, blockZ - 1).getType());
            } else if (fracZ > 0.7D) {
                if (!blockSolidPassSet.contains(world.getBlockAt(blockX - 1, blockY, blockZ + 1).getType())) {
                    return true;
                }

                if (!blockSolidPassSet.contains(world.getBlockAt(blockX, blockY, blockZ + 1).getType())) {
                    return true;
                }

                return !blockSolidPassSet.contains(world.getBlockAt(blockX + 1, blockY, blockZ + 1).getType());
            }
        } else if (fracZ < 0.3D) {
            return !blockSolidPassSet.contains(world.getBlockAt(blockX, blockY, blockZ - 1).getType());
        } else return fracZ > 0.7D && !blockSolidPassSet.contains(world.getBlockAt(blockX, blockY, blockZ + 1).getType());

        return false;
    }

    public static boolean isUnderBlock(Location location, Set<Material> materials, int down) {
        double posX = location.getX();
        double posZ = location.getZ();
        double fracX = posX % 1.0D > 0.0D ? Math.abs(posX % 1.0D) : 1.0D - Math.abs(posX % 1.0D);
        double fracZ = posZ % 1.0D > 0.0D ? Math.abs(posZ % 1.0D) : 1.0D - Math.abs(posZ % 1.0D);
        int blockX = location.getBlockX();
        int blockY = location.getBlockY() - down;
        int blockZ = location.getBlockZ();
        World world = location.getWorld();
        if (world == null) {
            return false;
        }

        if (materials.contains(world.getBlockAt(blockX, blockY, blockZ).getType())) {
            return true;
        }

        if (fracX < 0.3D) {
            if (materials.contains(world.getBlockAt(blockX - 1, blockY, blockZ).getType())) {
                return true;
            }

            if (fracZ < 0.3D) {
                if (materials.contains(world.getBlockAt(blockX - 1, blockY, blockZ - 1).getType())) {
                    return true;
                }

                if (materials.contains(world.getBlockAt(blockX, blockY, blockZ - 1).getType())) {
                    return true;
                }

                return materials.contains(world.getBlockAt(blockX + 1, blockY, blockZ - 1).getType());
            } else if (fracZ > 0.7D) {
                if (materials.contains(world.getBlockAt(blockX - 1, blockY, blockZ + 1).getType())) {
                    return true;
                }

                if (materials.contains(world.getBlockAt(blockX, blockY, blockZ + 1).getType())) {
                    return true;
                }

                return materials.contains(world.getBlockAt(blockX + 1, blockY, blockZ + 1).getType());
            }
        } else if (fracX > 0.7D) {
            if (materials.contains(world.getBlockAt(blockX + 1, blockY, blockZ).getType())) {
                return true;
            }

            if (fracZ < 0.3D) {
                if (materials.contains(world.getBlockAt(blockX - 1, blockY, blockZ - 1).getType())) {
                    return true;
                }

                if (materials.contains(world.getBlockAt(blockX, blockY, blockZ - 1).getType())) {
                    return true;
                }

                return materials.contains(world.getBlockAt(blockX + 1, blockY, blockZ - 1).getType());
            } else if (fracZ > 0.7D) {
                if (materials.contains(world.getBlockAt(blockX - 1, blockY, blockZ + 1).getType())) {
                    return true;
                }

                if (materials.contains(world.getBlockAt(blockX, blockY, blockZ + 1).getType())) {
                    return true;
                }

                return materials.contains(world.getBlockAt(blockX + 1, blockY, blockZ + 1).getType());
            }
        } else if (fracZ < 0.3D) {
            return materials.contains(world.getBlockAt(blockX, blockY, blockZ - 1).getType());
        } else return fracZ > 0.7D && materials.contains(world.getBlockAt(blockX, blockY, blockZ + 1).getType());

        return false;
    }

    public static boolean isOnStairs(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockStairsSet, down);
    }

    public static boolean isOnLiquid(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockLiquidsSet, down);
    }

    public static boolean isOnWeb(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockWebsSet, down);
    }

    public static boolean isOnIce(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockIceSet, down);
    }

    public static boolean isInClimable(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockClimableSet, down);
    }

    public static boolean isOnSlime(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockSlimeSet, down);
    }

    public static boolean isBadVelocityMaterial(Material material) {
        return badVelocitySet.contains(material);
    }

    public static boolean isBadShapeMaterial(Material material) {
        return badShapesSet.contains(material);
    }
}
