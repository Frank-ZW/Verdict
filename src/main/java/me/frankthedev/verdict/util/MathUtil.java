package me.frankthedev.verdict.util;

import me.frankthedev.verdict.util.location.PlayerLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class MathUtil {

    /**
     * Calculates the deviation of a list of numbers from a pre-set mean value.
     *
     * @param n         The pre-determined mean value to calculate the deviation from.
     * @param numbers   The list of numbers to be calculated.
     * @return          The deviation of the list of numbers from n.
     */
    public static double getVariance(Number n, Iterable<? extends Number> numbers) {
        int length = 0;
        double sum = 0.0D;
        for (Number number : numbers) {
            sum += Math.pow(number.doubleValue() - n.doubleValue(), 2.0D);
            length++;
        }

        return Math.sqrt(sum / ((double) length - 1));
    }

    /**
     * Calculates the standard deviation of a list of numbers.
     *
     * @param numbers   The list of numbers to calculate the deviation from.
     * @return          The standard deviation of the list of numbers.
     */
    public static double getStandardDeviation(Iterable<? extends Number> numbers) {
        double sum = 0.0D;
        int length = 0;
        for (Number number : numbers) {
            sum += number.doubleValue();
            length++;
        }

        double mean = sum / length;
        double meanDifference = 0.0D;
        for (Number number : numbers) {
            meanDifference += Math.pow(number.doubleValue() - mean, 2.0D);
        }

        return Math.sqrt(meanDifference / ((double) length - 1));
    }

    public static double getAverage(Iterable<? extends Number> numbers) {
        double sum = 0.0D;
        int length = 0;
        for (Number number : numbers) {
            sum += number.doubleValue();
            length++;
        }

        return sum / length;
    }

//    @Nullable
//    public static Entity raytrace(Player player, double range) {
//        Entity targetEntity = null;
//        Ray ray = Ray.fromPlayer(player);
//        double distance = -1.0D;
//        for (Entity entity : player.getNearbyEntities(range, range, range)) {
//            if (!(entity instanceof LivingEntity)) {
//                continue;
//            }
//
//            double collisionDist = HitBox.fromEntity(entity).getCollisionDistance(ray, 0, range);
//            if (collisionDist != -1.0D) {
//                if (collisionDist < distance || distance == -1.0D) {
//                    distance = collisionDist;
//                    targetEntity = entity;
//                }
//            }
//        }
//
//        return targetEntity;
//    }

//    public static double getCollisionDistance(Player player, Entity target) {
//        Ray ray = Ray.fromPlayer(player);
//        return HitBox.fromEntity(target).getCollisionDistance(ray, 0, 6.0D);
//    }

    /**
     * Returns the number of degrees rotated between angle1 and angle2.
     *
     * @param angle1    The from angle in degrees.
     * @param angle2    The to angle in degrees.
     * @return          The distance rotated in degrees between the two angles.
     */
    public static double getAngleSeparation(float angle1, float angle2) {
        float delta = Math.abs(angle1 - angle2) % 360.0F;
        if (delta > 180.0F) {
            delta = 360.0F - delta;
        }

        return delta;
    }

    public static float[] calculateRotationalPosition(PlayerLocation location1, PlayerLocation location2) {
        double deltaX = location1.getX() - location2.getX();
        double deltaZ = location1.getZ() - location2.getZ();
        double yaw = Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI - 90.0D;
        double pitch = -Math.atan2(location2.getY() + 0.81 - location1.getY() - 1.2, location1.getGroundDistance(location2)) * 180.0D / Math.PI;
        return new float[] {(float) yaw, (float) pitch};
    }

//    public static Entity raytracePlayer(PlayerData playerData, double distance) {
//        PlayerLocation playerLocation = playerData.getLocation();
//        Entity target = null;
//        float lowestFov = Float.MAX_VALUE;
//        List<Entity> nearbyEntities = playerData.getPlayer().getNearbyEntities(distance, distance, distance);
//        for (Entity entity : nearbyEntities) {
//            PlayerLocation entityLocation = PlayerLocation.fromBukkitPlayer(entity.getLocation());
//            float fov = MathUtil.getRotationFromPosition(playerLocation, entityLocation)[0] - playerLocation.getYaw();
//            double groundDistance = playerLocation.getGroundDistance(entityLocation);
//            if (fov < lowestFov && (double) fov < groundDistance + 2.0D) {
//                target = entity;
//                lowestFov = fov;
//            }
//        }
//
//        return target;
//    }

    public static float[] getRotationFromPosition(PlayerLocation playerLocation, PlayerLocation entityLocation) {
        double deltaX = entityLocation.getX() - playerLocation.getX();
        double deltaZ = entityLocation.getZ() - playerLocation.getZ();
        float yaw = (float) Math.toRadians(Math.atan2(deltaZ, deltaX)) - 90.0F;
        float pitch = (float) Math.toRadians(-Math.atan2(entityLocation.getY() + 0.81D - playerLocation.getY() - 1.2D, Math.hypot(deltaX, deltaZ)));
        return new float[] {yaw, pitch};
    }

    public static float normalizeAngle180(float angle) {
        angle = MathUtil.normalizeAngle360(angle);
        return angle > 180.0F ? 360 - angle : Math.abs(angle);
    }

    /**
     * Clamps the angle between 0 degrees and +360 degrees.
     *
     * @param angle The original angle
     * @return      The principle value of the angle between 0 and +360 degrees.
     */
    public static float normalizeAngle360(float angle) {
        angle = angle % 360.0F;
        return angle < 0 ? 360.0F + angle : angle;
    }

    public static double getDistanceBetweenAngles(double angle1, double angle2) {
        double abs = Math.abs(angle1 % 360.0F - angle2 % 360.0F);
        return Math.abs(Math.min(360.0D - abs, abs));
    }

    /**
     * Returns the yaw of the player starting from playerLocation and looking at
     * the coordinates (x, z)
     *
     * @param playerLocation    The starting location the player is looking from.
     * @param x                 The x-coordinate of the object the player is looking at.
     * @param z                 The z-coordinate of the object the player is looking at.
     * @return                  The yaw of the player.
     */
    public static float calculatePositionalYaw(PlayerLocation playerLocation, double x, double z) {
        float yaw = (float) Math.toDegrees(-Math.atan2(x - playerLocation.getX(), z - playerLocation.getZ()));
        return yaw < 0 ? 360.0F - yaw : yaw;
    }

    public static Double getMinimumYawDistance(PlayerLocation location, Iterable<? extends PlayerLocation> others) {
        Double angle = null;
        for (PlayerLocation other : others) {
            double angleRotation = MathUtil.getDistanceBetweenAngles(location.getYaw(), Math.toDegrees((Math.atan2(location.getZ() - other.getZ(), location.getX() - other.getX()))) - 90.0D);
            if (angle == null || angleRotation < angle) {
                angle = angleRotation;
            }
        }

        return angle == null ? 0.0D : angle;
    }

    public static long gcdLong(List<Long> numbers) {
        long divisor = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            divisor = MathUtil.gcdLong(numbers.get(i), divisor);
            if (divisor == 1L) {
                return divisor;
            }
        }

        return divisor;
    }

    public static long gcdLong(long n1, long n2, long n3) {
        return n3 <= n1 ? n2 : MathUtil.gcdLong(n1, n3, n2 % n3);
    }

    public static long gcdLong(long n1, long n2) {
        if (n1 == 0L) {
            return n2;
        }

        return MathUtil.gcdLong(n2 % n1, n1);
    }

    public static float gcdFloat(List<Float> numbers) {
        float divisor = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            divisor = MathUtil.gcdFloat(numbers.get(i), divisor);
            if (divisor == 1.0F) {
                return divisor;
            }
        }

        return divisor;
    }

    public static float gcdFloat(float n1, float n2) {
        if (n1 < n2) {
            return MathUtil.gcdFloat(n2, n1);
        }

        if (Math.abs(n2) < 0.001F) {
            return n1;
        }

        return MathUtil.gcdFloat(n2, (float) (n1 - Math.floor(n1 / n2) * n2));
    }

    public static int gcdInteger(int n1, int n2) {
        if (n1 == 0) {
            return n2;
        }

        return MathUtil.gcdInteger(n2 % n1, n1);
    }

    public static double squaredHypot(@Nonnull Number ... numbers) {
        double sum = 0.0D;
        for (Number number : numbers) {
            sum += Math.pow(number.doubleValue(), 2.0D);
        }

        return sum;
    }

    public static double hypot(double x, double y, double z) {
        return Math.sqrt(Math.pow(x, 2.0D) + Math.pow(y, 2.0D) + Math.pow(z, 2.0D));
    }

    public static double hypot(Double... numbers) {
        double sum = 0.0D;
        for (Number number : numbers) {
            sum += Math.pow(number.doubleValue(), 2.0D);
        }

        return Math.sqrt(sum);
    }

    public static double lowest(Number... numbers) {
        switch (numbers.length) {
            case 0: return Double.MIN_VALUE;
            case 1: return numbers[0].doubleValue();
            default:
                double value = numbers[0].doubleValue();
                for (int i = 1; i < numbers.length; i++) {
                    if (numbers[i].doubleValue() < value) {
                        value = numbers[i].doubleValue();
                    }
                }

                return value;
        }
    }

    public static double lowest(List<? extends Number> entries) {
        switch (entries.size()) {
            case 0: return Double.MIN_VALUE;
            case 1: return entries.get(0).doubleValue();
            default:
                double value = entries.get(0).doubleValue();
                for (Number number : entries) {
                    if (number.doubleValue() < value) {
                        value = number.doubleValue();
                    }
                }

                return value;
        }
    }

    public static double highest(Number... numbers) {
        switch (numbers.length) {
            case 0: return Double.MAX_VALUE;
            case 1: return numbers[0].doubleValue();
            default:
                double value = numbers[0].doubleValue();
                for (int i = 1; i < numbers.length; i++) {
                    if (numbers[i].doubleValue() > value) {
                        value = numbers[i].doubleValue();
                    }
                }

                return value;
        }
    }

    public static double highest(List<? extends Number> entries) {
        switch (entries.size()) {
            case 0:
                return Double.MAX_VALUE;
            case 1:
                return entries.get(0).doubleValue();
            default:
                double value = entries.get(0).doubleValue();
                for (Number number : entries) {
                    if (number.doubleValue() > value) {
                        value = number.doubleValue();
                    }
                }

                return value;
        }
    }

    public static double lowestAbsolute(Number... numbers) {
        switch (numbers.length) {
            case 0: return Double.MIN_VALUE;
            case 1: return numbers[0].doubleValue();
            default:
                Double value = null;
                for (Number number : numbers) {
                    if (value == null || value > Math.abs(number.doubleValue())) {
                        value = Math.abs(number.doubleValue());
                    }
                }

                return value;
        }
    }
}
