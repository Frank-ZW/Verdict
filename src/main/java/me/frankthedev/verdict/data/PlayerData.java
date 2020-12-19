package me.frankthedev.verdict.data;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.Check;
import me.frankthedev.verdict.check.impl.aimassist.*;
import me.frankthedev.verdict.check.impl.autoclicker.AutoClickerA;
import me.frankthedev.verdict.check.impl.autoclicker.AutoClickerB;
import me.frankthedev.verdict.check.impl.badpackets.BadPacketsA;
import me.frankthedev.verdict.check.impl.badpackets.BadPacketsB;
import me.frankthedev.verdict.check.impl.badpackets.BadPacketsC;
import me.frankthedev.verdict.check.impl.fly.*;
import me.frankthedev.verdict.check.impl.inventory.InventoryA;
import me.frankthedev.verdict.check.impl.killaura.*;
import me.frankthedev.verdict.check.impl.nofall.NoFallA;
import me.frankthedev.verdict.check.impl.nuker.NukerA;
import me.frankthedev.verdict.check.impl.phase.PhaseA;
import me.frankthedev.verdict.check.impl.pingspoof.PingSpoofA;
import me.frankthedev.verdict.check.impl.reach.ReachA;
import me.frankthedev.verdict.check.impl.servercrasher.ServerCrasherA;
import me.frankthedev.verdict.check.impl.speed.SpeedA;
import me.frankthedev.verdict.check.impl.timer.TimerA;
import me.frankthedev.verdict.data.manager.PlayerManager;
import me.frankthedev.verdict.log.Log;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.*;
import me.frankthedev.verdict.util.bukkit.Velocity;
import me.frankthedev.verdict.util.bukkit.BlockPosition;
import me.frankthedev.verdict.util.bukkit.BlockUtil;
import me.frankthedev.verdict.util.bukkit.MutableBlockLocation;
import me.frankthedev.verdict.util.java.JavaUtil;
import me.frankthedev.verdict.util.MathUtil;
import me.frankthedev.verdict.util.java.Pair;
import me.frankthedev.verdict.util.location.PlayerLocation;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class PlayerData {

    private final Player player;
    private final UUID uniqueId;
    private final String name;

    private final Queue<Log> logQueue = new ConcurrentLinkedDeque<>();
    private final Map<UUID, Deque<PlayerLocation>> entityMoveMap = new ConcurrentHashMap<>();
    private final Map<Integer, Long> keepAliveTimestamps = new ConcurrentHashMap<>();
    private final Queue<BiConsumer<Integer, Double>> pingQueue = new ConcurrentLinkedQueue<>();
    private final Queue<PlayerLocation> teleportQueue = new ConcurrentLinkedQueue<>();
    private final Queue<Integer> connectionFrequencyQueue = new ConcurrentLinkedQueue<>();
    private final Queue<Velocity> velocityQueue = new ConcurrentLinkedQueue<>();

    private final PlayerLocation location;
    private PlayerLocation lastLocation;
    private PlayerLocation lastLastLocation;
    private final CheckData checkData;
    private PlayerData lastAttacked;
    private PlayerLocation lastDelayed;
    private MutableBlockLocation lastBlockPlaced;
    private Entity lastAttackedEntity;

    private int totalTicks;
    private int swingTicks;
    private int lastPlaceTicks;
    private int flyingTicks;
    private int vehicleTicks;
    private int survivalTicks;
    private int allowFlightTicks;
    private int blockingTicks;
    private int lastAttackTicks;
    private int teleportTicks;
    private int velocityTicks;
    private int groundTicks;
    private int packets;
    private int ping;
    private int lastPing;
    private int averagePing;
    private int cps;

    private double lastGroundY;
    private double velX;
    private double velY;
    private double velZ;
    private double lastVelX;
    private double lastVelY;
    private double lastVelZ;

    private long lastTeleportTimestamp;
    private long lastAttackTimestamp;
    private long lastDelayedTimestamp;
    private long lastKeepAliveTimestamp;
    private long lastFlyingTimestamp;
    private long lastLastFlyingTimestamp;
    private long lastFastTimestamp;

    private boolean receiveAlerts;
    private boolean onGround;
    private boolean inVehicle;
    private boolean inLiquid;
    private boolean inClimable;
    private boolean inWeb;
    private boolean onIce;
    private boolean onStairs;
    private boolean onSlime;
    private boolean underBlock;
    private boolean fallFlying;
    private boolean blocking;
    private boolean placing;
    private boolean digging;
    private boolean sprinting;
    private boolean sneaking;
    private boolean inBanWave;

    public PlayerData(Player player) {
        Verdict plugin = Verdict.getInstance();
        this.player = player;
        this.uniqueId = player.getUniqueId();
        this.name = player.getName();
        this.location = PlayerLocation.fromBukkitLocation(player.getLocation());
        this.lastLocation = this.location;
        this.lastLastLocation = this.location;
        this.receiveAlerts = true;

        List<Class<? extends Check>> checkClasses = Arrays.asList(
                FlyA.class, FlyB.class, FlyC.class, FlyD.class, FlyE.class, FlyF.class, FlyG.class, FlyH.class, FlyI.class,
                NoFallA.class,
                AutoClickerA.class, AutoClickerB.class,
                AimAssistA.class, AimAssistC.class, AimAssistD.class, AimAssistE.class,
                BadPacketsA.class, BadPacketsB.class, BadPacketsC.class,
                InventoryA.class,
                KillAuraA.class, KillAuraB.class, KillAuraC.class, KillAuraD.class, KillAuraF.class, KillAuraG.class,
                PhaseA.class,
                PingSpoofA.class,
                ReachA.class,
                ServerCrasherA.class,
                SpeedA.class,
                TimerA.class,
                NukerA.class
        );

        List<Check> checks = new ArrayList<>();
        for (Class<? extends Check> clazz : checkClasses) {
            try {
                checks.add(clazz.asSubclass(Check.class).getConstructor(Verdict.class, PlayerData.class).newInstance(plugin, this));
            } catch (ReflectiveOperationException e) {
                plugin.getLogger().log(Level.WARNING, "Failed to instantiate " + clazz.getSimpleName() + " check for " + this.name, e);
            }
        }

        this.checkData = new CheckData(checks);
    }

    public void addLog(Log log) {
        this.logQueue.add(log);
    }

    /**
     * @param targetUUID    The UUID of the player attacked.
     * @param location      The most recent location of the target.
     */
    public void addEntityMove(UUID targetUUID, PlayerLocation location) {
        Deque<PlayerLocation> targetLocations = this.entityMoveMap.get(targetUUID);
        if (targetLocations == null) {
            targetLocations = new ConcurrentLinkedDeque<>();
        }

        targetLocations.add(location);
        if (targetLocations.size() > 35) {
            JavaUtil.trim(targetLocations, 35);
        }
        
        this.entityMoveMap.put(targetUUID, targetLocations);
    }
    /**
     * @param targetUUID    The target player's UUID
     * @return              True if the PlayerData instance target move map contains a queue for the target and false otherwise.
     */
    public boolean hasEntityMoveQueue(UUID targetUUID) {
        return this.entityMoveMap.containsKey(targetUUID);
    }

    /**
     * Returns a queue of the locations the target specified was at while attacked by the player.
     *
     * @param targetUUID    The target player's UUID.
     * @return              The double ended queue of the target's past locations when attacked.
     */
    public Deque<PlayerLocation> getEntityMoveQueue(UUID targetUUID) {
        return this.entityMoveMap.get(targetUUID);
    }

    /**
     * @return  The player object.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @return  The player's UUID.
     */
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    /**
     * @return  The player's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return  Whether or not the player is lagging from their most recent flying packet.
     */
    public boolean hasLag() {
        return this.hasLag(this.lastFlyingTimestamp);
    }

    /**
     * Returns true if the player is lagging at the time specified and false if the player
     * was not lagging at timestamp.
     *
     * @param timestamp The timestamp to determine whether the player is lagging.
     * @return          Whether or not the player is lagging at the time specified.
     */
    public boolean hasLag(long timestamp) {
        return this.lastFlyingTimestamp != 0L && this.lastDelayedTimestamp != 0L && timestamp - this.lastDelayedTimestamp < 110L;
    }

    /**
     * Returns the player's average connection ping. This value is calculated off of
     * a rolling average, meaning it may not be accurate.
     *
     * @return  The player's average ping.
     */
    public int getAveragePing() {
        return this.averagePing;
    }

    /**
     * @return  True if the player will receive anticheat alerts and false if not.
     */
    public boolean isReceiveAlerts() {
        return this.receiveAlerts;
    }

    /**
     * @param receiveAlerts Toggle whether or not a player will see anticheat alert violations.
     */
    public void setReceiveAlerts(boolean receiveAlerts) {
        this.receiveAlerts = receiveAlerts;
    }

    /**
     * @return  True if the player is under a block and false if not.
     */
    public boolean isUnderBlock() {
        return this.underBlock;
    }

    /**
     * @return  True if the player is standing on a stair and false if not.
     */
    public boolean isOnStairs() {
        return this.onStairs;
    }

    /**
     * @return  True if the player is standing on ice and false if not.
     */
    public boolean isOnIce() {
        return this.onIce;
    }

    /**
     * @return  True if the player is on ice and false if not.
     */
    public boolean isInWeb() {
        return this.inWeb;
    }

    /**
     * @return  True if the player is in a water or lava and false if not.
     */
    public boolean isInLiquid() {
        return this.inLiquid;
    }

    /**
     * @return  True if the player is in a vehicle and false if not.
     */
    public boolean isInVehicle() {
        return this.inVehicle;
    }

    /**
     * @return  Whether or not the player is on the ground.
     */
    public boolean isOnGround() {
        return this.onGround;
    }

    /**
     * @return  Whether or not the player is in a vine or a ladder.
     */
    public boolean isInClimable() {
        return this.inClimable;
    }

    /**
     * @return  Whether or not the player is digging.
     */
    public boolean isDigging() {
        return this.digging;
    }

    /**
     * @return  Whether or not the player is placing.
     */
    public boolean isPlacing() {
        return this.placing;
    }

    /**
     * @return  Whether or not the player is climbing in a vine or ladder.
     */
    public boolean isClimbing() {
        return this.isInClimable() && this.location.getY() > this.lastLocation.getY();
    }

    /**
     * @return  The amount of ticks that have passed since the player last attacked another entity.
     */
    public int getLastAttackTicks() {
        return this.lastAttackTicks;
    }

    /**
     * @return  The last location y-value where the player was on the ground.
     */
    public double getLastGroundY() {
        return this.lastGroundY;
    }

    /**
     * @return  The total number of in-game ticks that have passed since the player joined.
     */
    public int getTotalTicks() {
        return this.totalTicks;
    }

    /**
     * The clicks per second returned is calculated off of
     * a rolling average, meaning the value may not be
     * accurate.
     *
     * @return  The player's average clicks per second.
     */
    public int getCps() {
        return this.cps;
    }

    /**
     * Returns the average clicks per second of the player. This
     * value is calculated off of a rolling average, meaning the
     * value may not be accurate.
     *
     * @param cps   The clicks per second of the player.
     */
    public void setCps(int cps) {
        this.cps = cps;
    }

    /**
     * @return  The current location of the player.
     */
    public PlayerLocation getLocation() {
        return this.location;
    }

    /**
     * @return  The last location of the player.
     */
    public PlayerLocation getLastLocation() {
        return this.lastLocation;
    }

    /**
     * @return  The location of the player two ticks, or 100ms, ago
     */
    public PlayerLocation getLastLastLocation() {
        return this.lastLastLocation;
    }

    /**
     * Returns a PlayerLocation of the last delayed flying packet location. A
     * flying packet is considered delayed when the difference in time between
     * the last flying packet and the current packet is greater than 110ms.
     *
     * @return  The last delayed flying packet location.
     */
    public PlayerLocation getLastDelayed() {
        return this.lastDelayed;
    }

    /**
     * Returns the timestamp of the last delayed flying packet. A flying
     * packet is considered delayed when the difference in time between
     * the last flying packet and the current packet is greater than 110ms.
     *
     * @return  The last delayed flying packet timestamp
     */
    public long getLastDelayedTimestamp() {
        return this.lastDelayedTimestamp;
    }

    public int getMoveTicks() {
        return (int) Math.floor(Math.min(this.ping, this.averagePing) / 1250.D);
    }

    /**
     * @return  The number of ticks since the player has last teleported.
     */
    public int getTeleportTicks() {
        return this.teleportTicks;
    }

    /**
     * Returns the player's maximum ping, taken from the average, last, and current ping,
     * and converts it into ticks.
     *
     * @return  The player's ping in in-game ticks.
     */
    public int getMaxPingTicks() {
        return this.getMaxPing() / 50 + 3;
    }

    /**
     * Returns the player's maximum ping calculated from the average ping, the latest ping,
     * and the player's last ping.
     *
     * @return  The player's greatest ping.
     */
    public int getMaxPing() {
        return (int) MathUtil.highest(this.averagePing, this.ping, this.lastPing);
    }

    /**
     * Returns the number of ticks that have passed since the player's last velocity
     * packet.
     *
     * @return  The number of ticks since the last velocity packet.
     */
    public int getVelocityTicks() {
        return this.velocityTicks;
    }

    /**
     * @return  The timestamp of the last time the player attacked an entity.
     */
    public long getLastAttackTimestamp() {
        return this.lastAttackTimestamp;
    }

    /**
     * @return  The timestamp of the last flying packet.
     */
    public long getLastLastFlyingTimestamp() {
        return this.lastLastFlyingTimestamp;
    }

    /**
     *
     * @return  The timestamp of the most recent flying packet received.
     */
    public long getLastFlyingTimestamp() {
        return this.lastFlyingTimestamp;
    }

    public boolean isOnSlime() {
        return this.onSlime;
    }

    /**
     * @return  The player's most recent x-velocity.
     */
    public double getVelX() {
        return this.velX;
    }

    /**
     * @return  The player's most recent y-velocity.
     */
    public double getVelY() {
        return this.velY;
    }

    /**
     * @return  The player's most recent z-velocity.
     */
    public double getVelZ() {
        return this.velZ;
    }

    /**
     * @return  The player's last x-velocity.
     */
    public double getLastVelX() {
        return this.lastVelX;
    }

    /**
     * @return  The player's last y-velocity.
     */
    public double getLastVelY() {
        return this.lastVelY;
    }

    /**
     * @return  The player's last z-velocity.
     */
    public double getLastVelZ() {
        return this.lastVelZ;
    }

    /**
     * Returns whether or not the player's most recent PacketPlayInFlying received was
     * a fast packet and whether or not the difference in time between the current
     * timestamp and the last fast timestamp is less than 110ms. A flying packet is
     * considered fast when the difference in time between the last flying packet
     * and the current flying packet is less than 15ms.
     *
     * @return  Whether or not the player recently received a fast packet.
     */
    public boolean hasFast() {
        return this.hasFast(this.lastFlyingTimestamp);
    }

    /**
     * Returns whether or not the player recently received a fast packet from the timestamp
     * specified. A flying packet is considered fast when the difference in time between the
     * last flying packet and the current flying packet is less than 15ms.
     *
     * @param timestamp The timestamp to determine whether the player was receiving a fast packet.
     * @return          Whether or not the player recently received a fast packet from timestamp.
     */
    public boolean hasFast(long timestamp) {
        return this.lastFlyingTimestamp != 0L && this.lastFastTimestamp != 0L && timestamp - this.lastFastTimestamp < 110L;
    }

    public Queue<Log> getLogQueue() {
        return this.logQueue;
    }

    /**
     * The return value can be null.
     *
     * @return  The PlayerData object of the last player attacked.
     */
    @Nullable
    public PlayerData getLastAttacked() {
        return this.lastAttacked;
    }

    public boolean isTeleporting() {
        return this.teleportTicks <= this.getMaxPingTicks();
    }

    /**
     * @return  Whether or not the player will be considered in any upcoming or ongoing banwaves.
     */
    public boolean isInBanWave() {
        return this.inBanWave;
    }

    /**
     * @param inBanWave Toggle whether the player should be considered in any upcoming or ongoing banwaves.
     */
    public void setInBanWave(boolean inBanWave) {
        this.inBanWave = inBanWave;
    }

    public boolean isFallFlying() {
        return this.fallFlying;
    }

    /**
     * Returns true if the player has permission to fly and returns false if the player does not have
     * permission to fly.
     * <p>
     * The value returned is calculated off of the last time the player was allowed to fly. If the ticks
     * since the player was last allowed to fly is greater than the player's max ping tick tripled, then
     * the player cannot fly.
     *
     * @return  Whether or not the player can fly.
     */
    public boolean canFly() {
        return this.allowFlightTicks <= this.getMaxPingTicks() * 3;
    }

    public int getPackets() {
        return this.packets;
    }

    /**
     * Resets the values for the velocities back to zero.
     */
    public void resetVelocity() {
        this.lastVelX = 0.0D;
        this.lastVelY = 0.0D;
        this.lastVelZ = 0.0D;
        this.velX = 0.0D;
        this.velY = 0.0D;
        this.velZ = 0.0D;
    }

    /**
     * @return  The velocity queue for the player.
     */
    public Queue<Velocity> getVelocityQueue() {
        return this.velocityQueue;
    }

    /**
     * @return  True if the player is sprinting and false when the player stops sprinting.
     */
    public boolean isSprinting() {
        return this.sprinting;
    }

    public void processPacket(APacket aPacket, boolean serverbound) {
        long currentTimeMillis = System.currentTimeMillis();
        if (serverbound) {
            if (aPacket instanceof APacketPlayInFlying) {
                APacketPlayInFlying packet = (APacketPlayInFlying) aPacket;

                this.lastLastLocation = this.lastLocation.cloneLocation();
                this.lastLocation = this.location.cloneLocation();
                this.location.setTimestamp(currentTimeMillis);
                this.location.setTotalTicks(this.totalTicks);
                this.location.setOnGround(packet.isOnGround());
                if (packet.isPos()) {
                    this.location.setX(packet.getX());
                    this.location.setY(packet.getY());
                    this.location.setZ(packet.getZ());
                }

                if (packet.isLook()) {
                    this.location.setYaw(packet.getYaw());
                    this.location.setPitch(packet.getPitch());
                }

                long flyingDelay = currentTimeMillis - this.lastFlyingTimestamp;
                if (flyingDelay > 110L) {
                    this.lastDelayedTimestamp = currentTimeMillis;
                    this.lastDelayed = this.location;
                }

                if (flyingDelay < 15L) {
                    this.lastFastTimestamp = currentTimeMillis;
                }

                // Flying packets sent every 50ms
                // connectionFrequency lists the delay of flying packet
                this.connectionFrequencyQueue.add(50 - (int) flyingDelay);
                this.lastLastFlyingTimestamp = this.lastFlyingTimestamp;
                this.lastFlyingTimestamp = currentTimeMillis;
                if (this.placing) {
                    this.placing = false;
                }

                PlayerLocation lastTeleport = this.teleportQueue.peek();
                if (lastTeleport != null) {
                    int deltaTickTime = this.totalTicks - lastTeleport.getTotalTicks();
                    if (packet.isPos() && lastTeleport.sameLocation(this.location) && deltaTickTime >= this.getMoveTicks()) {
                        this.teleportQueue.poll();
                        this.lastVelY = 0.0D;
                        this.velX = 0.0D;
                        this.velY = 0.0D;
                        this.velZ = 0.0D;
                        this.lastLocation = this.location.cloneLocation();
                        this.lastLastLocation = this.location.cloneLocation();
                        this.lastTeleportTimestamp = currentTimeMillis;
                        this.teleportTicks = 0;
                        return;
                    }

                    if (deltaTickTime > (packet.isPos() ? this.getMaxPingTicks() * 2 : this.getMaxPingTicks() * 4)) {
                        this.teleportQueue.poll();
                    }
                }

                if (this.lastVelY == 0.0D && this.velY != 0.0D) {
                    if (packet.isOnGround()) {
                        this.velY = 0.0D;
                    } else {
                        this.velY = (this.velY - 0.8D) * 0.9800000190734863D;
                    }
                }

                if (packet.isOnGround()) {
                    this.fallFlying = false;
                }

                this.packets = !this.inVehicle ? (packet.isPos() ? 0 : ++this.packets) : 0;
                this.groundTicks = packet.isOnGround() ? ++this.groundTicks : 0;
                this.blockingTicks = this.blocking ? ++this.blockingTicks : 0;
                this.flyingTicks = this.player.isFlying() ? 0 : ++this.flyingTicks;
                this.allowFlightTicks = this.player.getAllowFlight() ? 0 : ++this.allowFlightTicks;
                this.vehicleTicks = this.player.getVehicle() != null ? ++this.vehicleTicks : 0;
                this.survivalTicks = this.player.getGameMode().equals(GameMode.SURVIVAL) || this.player.getGameMode().equals(GameMode.ADVENTURE) ? ++this.survivalTicks : 0;
                if (this.player.getGameMode().equals(GameMode.SPECTATOR)) {
                    this.sprinting = false;
                }

                this.lastPlaceTicks++;
                this.lastAttackTicks++;
                this.velocityTicks++;
                this.totalTicks++;

                Location bukkitLocation = this.location.toBukkitLocation(this.player.getWorld());
                this.inClimable = BlockUtil.isInClimable(bukkitLocation, 1) ||  BlockUtil.isInClimable(bukkitLocation, 0) || BlockUtil.isInClimable(bukkitLocation, -1);
                this.inLiquid = BlockUtil.isOnLiquid(bukkitLocation, 0) || BlockUtil.isOnLiquid(bukkitLocation, 1);
                this.inWeb = BlockUtil.isOnWeb(bukkitLocation, 0);
                this.onIce = BlockUtil.isOnIce(bukkitLocation, 1) || BlockUtil.isOnIce(bukkitLocation, 2);
                this.onSlime = BlockUtil.isOnSlime(bukkitLocation, 1);
                this.onStairs = BlockUtil.isOnStairs(bukkitLocation, 0) || BlockUtil.isOnStairs(bukkitLocation, 1);
                this.underBlock = BlockUtil.isOnGround(bukkitLocation, -2);
                this.onGround = this.location.isOnGround();
                if (this.onGround) {
                    this.lastGroundY = this.location.getY();
                }

                boolean moved = !this.location.sameLocation(this.lastLocation);
                boolean aimed = !this.location.sameDirection(this.lastLocation);
                if (moved) {
                    this.checkData.getMovementChecks().forEach(check -> check.handle(this.location, this.lastLocation, currentTimeMillis));
                }

                if (aimed) {
                    this.checkData.getRotationChecks().forEach(check -> check.handle(this.location, this.lastLocation, currentTimeMillis));
                }

                if (this.lastAttackTicks <= 1 && this.lastAttackedEntity != null && this.lastAttacked != null && !this.hasLag() && !this.hasFast() && !this.player.getGameMode().equals(GameMode.CREATIVE)) {
                    PlayerLocation locationClone = this.location.cloneLocation();
                    PlayerLocation lastLocationClone = this.lastLocation.cloneLocation();
                    Deque<PlayerLocation> entityRecentMoves = this.entityMoveMap.get(this.lastAttacked.getUniqueId());
                    if (entityRecentMoves != null && !entityRecentMoves.isEmpty() && entityRecentMoves.size() > 10) {
                        List<PlayerLocation> recentMoves = entityRecentMoves.stream().map(PlayerLocation::cloneLocation).collect(Collectors.toList());
                        this.pingQueue.add((ping, connectionDeviation) -> {
                            List<PlayerLocation> possibleMoves = recentMoves.stream().filter(location -> currentTimeMillis - location.getTimestamp() <= ping + connectionDeviation + 100L).collect(Collectors.toList());
                            if (!possibleMoves.isEmpty()) {
                                PlayerLocation earliestLocation = possibleMoves.stream().min(Comparator.comparingLong(PlayerLocation::getTimestamp)).get();
                                int index = possibleMoves.indexOf(earliestLocation);
                                if (index > 0) {
                                    possibleMoves.add(recentMoves.get(index - 1));
                                }

                                List<DistanceData> distanceDatas = possibleMoves.parallelStream()
                                        .map(PlayerLocation::getHitbox)
                                        .map(hitbox -> {
                                            Pair<PlayerLocation, Double> closestPair = hitbox.getClosestPlayerLocation(locationClone, lastLocationClone);
                                            PlayerLocation closest = closestPair.getFirst();
                                            double groundDistance = closestPair.getSecond();
                                            double centerDistanceX = MathUtil.lowestAbsolute(locationClone.getX() - hitbox.getCenterX(), lastLocationClone.getX() - hitbox.getCenterX());
                                            double centerDistanceY = MathUtil.lowestAbsolute(locationClone.getY() - hitbox.getCenterY(), lastLocationClone.getY() - hitbox.getCenterY());
                                            double centerDistanceZ = MathUtil.lowestAbsolute(locationClone.getZ() - hitbox.getCenterZ(), lastLocationClone.getZ() - hitbox.getCenterZ());
                                            return new DistanceData(closest, centerDistanceX, centerDistanceY, centerDistanceZ, groundDistance, hitbox);
                                        })
                                        .collect(Collectors.toList());
                                Optional<DistanceData> optional = distanceDatas.parallelStream().min(Comparator.comparingDouble(DistanceData::getDistance));
                                if (optional.isPresent()) {
                                    List<Double> distances = distanceDatas.parallelStream().map(DistanceData::getDistance).collect(Collectors.toList());
                                    DistanceData minDistanceData = optional.get();
                                    PlayerLocation closestLocation = minDistanceData.getLocation();
                                    double minDistance = minDistanceData.getDistance();
                                    double maxDistance = MathUtil.highest(distances);
                                    double cornerX = minDistanceData.getHitbox().getClosestCornerX(closestLocation);
                                    double cornerZ = minDistanceData.getHitbox().getClosestCornerZ(closestLocation);
                                    double cornerDistanceX = Math.abs(cornerX - closestLocation.getX());
                                    double cornerDistanceZ = Math.abs(cornerZ - closestLocation.getZ());
                                    float playerYaw = MathUtil.normalizeAngle180(closestLocation.getYaw());
                                    float cornerYaw = MathUtil.normalizeAngle180(MathUtil.calculatePositionalYaw(closestLocation, cornerX, cornerZ));

                                    double horizontal = 0.0D;
                                    double vertical = 0.0D;
                                    if (playerYaw > cornerYaw) {
                                        if (playerYaw > 90.0F && cornerYaw > 90.0F) {
                                            if (closestLocation.getX() < cornerX && closestLocation.getZ() > cornerZ) {
                                                horizontal = cornerDistanceX * Math.abs(Math.tan(Math.toRadians(playerYaw - 90.0F)));
                                                vertical = cornerDistanceX;
                                            }
                                        }
                                    } else {
                                        if (playerYaw > 90.0F && cornerYaw > 90.0F) {
                                            if (closestLocation.getX() < cornerX && closestLocation.getZ() > cornerZ) {
                                                horizontal = cornerDistanceZ;
                                                vertical = cornerDistanceZ * Math.abs(-Math.tan(Math.toRadians(playerYaw)));
                                            }
                                        }
                                    }

                                    if (horizontal != 0.0D && vertical != 0.0D){
                                        double groundReach = Math.hypot(horizontal, vertical);
                                        double extra = groundReach * Math.abs(Math.tan(Math.toRadians(Math.abs(closestLocation.getPitch()))));
                                        double reach = Math.hypot(extra, groundReach) - 0.01D;
                                        if (reach > 6.0D) {
                                            return;
                                        }
                                        
                                        ReachData reachData = new ReachData(this, closestLocation, minDistanceData, maxDistance, minDistance, horizontal, vertical, extra, reach);
                                        this.checkData.getReachChecks().forEach(check -> check.handle(reachData, currentTimeMillis));
                                    }
                                }
                            }
                        });
                    }
                }
            } else if (aPacket instanceof APacketPlayInKeepAlive) {
                APacketPlayInKeepAlive packet = (APacketPlayInKeepAlive) aPacket;
                Long timestamp = this.keepAliveTimestamps.remove(packet.getId());
                if (timestamp != null) {
                    this.lastPing = this.ping;
                    this.ping = (int) (currentTimeMillis - timestamp);
                    this.averagePing = (this.averagePing * 9 + this.ping) / 10;
                    this.lastKeepAliveTimestamp = currentTimeMillis;

                    double averageConnectionFrequency = MathUtil.getVariance(0, this.connectionFrequencyQueue);
                    BiConsumer<Integer, Double> biConsumer = this.pingQueue.poll();
                    while (biConsumer != null) {
                        biConsumer.accept(this.ping, averageConnectionFrequency);
                        biConsumer = this.pingQueue.poll();
                    }

                    this.connectionFrequencyQueue.clear();
                }
            } else if (aPacket instanceof APacketPlayInUseEntity) {
                APacketPlayInUseEntity packet = (APacketPlayInUseEntity) aPacket;
                APacketPlayInUseEntity.EntityUseAction action = packet.getAction();
                if (action.isAttack()) {
                    Entity targetEntity = packet.getEntity(this.player.getWorld());
                    this.lastAttackedEntity = targetEntity;
                    this.lastAttackTicks = 0;
                    if (targetEntity instanceof Player) {
                        Player target = (Player) targetEntity;
                        PlayerData targetData = PlayerManager.getInstance().getPlayerData(target);
                        this.lastAttackTimestamp = currentTimeMillis;
                        this.lastAttacked = targetData;
                        this.addEntityMove(target.getUniqueId(), targetData == null ? null : targetData.getLocation());
                    }
                }
            } else if (aPacket instanceof APacketPlayInBlockDig) {
                APacketPlayInBlockDig packet = (APacketPlayInBlockDig) aPacket;
                APacketPlayInBlockDig.PlayerDigType type = packet.getType();
                switch (type) {
                    case START_DESTROY_BLOCK:
                        this.digging = true;
                        break;
                    case STOP_DESTROY_BLOCK:
                    case ABORT_DESTROY_BLOCK:
                        this.digging = false;
                        break;
                    default:
                        break;
                }
            } else if (aPacket instanceof APacketPlayInBlockPlace) {
                APacketPlayInBlockPlace packet = (APacketPlayInBlockPlace) aPacket;
                if (packet.isBlockPlaced()) {
                    BlockPosition position = packet.getPosition();
                    this.lastBlockPlaced = new MutableBlockLocation(position.getBlockX(), position.getBlockY(), position.getBlockZ());
                    this.placing = true;
                }
            } else if (aPacket instanceof APacketPlayInEntityAction) {
                APacketPlayInEntityAction packet = (APacketPlayInEntityAction) aPacket;
                APacketPlayInEntityAction.PlayerAction action = packet.getAction();
                switch (action.getType()) {
                    case SPRINTING:
                        this.sprinting = action.isValue();
                        break;
                    case SNEAKING:
                        this.sneaking = action.isValue();
                        break;
                    case FALL_FLYING:
                        this.fallFlying = action.isValue();
                        break;
                    default:
                        break;
                }
            }
        } else if (aPacket instanceof APacketPlayOutKeepAlive) {
            this.keepAliveTimestamps.put(((APacketPlayOutKeepAlive) aPacket).getID(), currentTimeMillis);
        } else if (aPacket instanceof APacketPlayOutExplosion) {
            APacketPlayOutExplosion packet = (APacketPlayOutExplosion) aPacket;
            this.lastVelY = this.velY;
            this.velX = packet.getMotionX();
            this.velY = packet.getMotionY();
            this.velZ = packet.getMotionZ();
            this.velocityTicks = Math.min(this.velocityTicks, 0);
        } else if (aPacket instanceof APacketPlayOutEntityVelocity) {
            APacketPlayOutEntityVelocity packet = (APacketPlayOutEntityVelocity) aPacket;
            if (this.player.getEntityId() == packet.getEntityID()) {
                this.lastVelX = this.velX;
                this.lastVelY = this.velY;
                this.lastVelZ = this.velZ;
                this.velX = packet.getMotionX();
                this.velY = packet.getMotionY();
                this.velZ = packet.getMotionZ();
                Velocity velocity = new Velocity(this.velX, this.velY, this.velZ, this.totalTicks, System.currentTimeMillis());
                this.velocityQueue.add(velocity);
                this.velocityTicks = Math.min(this.velocityTicks, 0) - (int) Math.ceil(Math.pow(MathUtil.squaredHypot(this.velX, this.velY, this.velZ) * 2.0D, 1.75D) * 4.0D);
            }
        } else if (aPacket instanceof APacketPlayOutPosition) {
            APacketPlayOutPosition packet = (APacketPlayOutPosition) aPacket;
            this.teleportQueue.add(packet.toPlayerLocation(this.onGround, this.totalTicks));
            this.teleportTicks = 0;
            this.lastTeleportTimestamp = currentTimeMillis;
            this.resetVelocity();
        } else if (aPacket instanceof APacketPlayOutSpawnPosition) {
            APacketPlayOutSpawnPosition packet = (APacketPlayOutSpawnPosition) aPacket;
            this.teleportQueue.add(packet.toPlayerLocation(this.onGround, this.totalTicks));
            this.teleportTicks = 0;
            this.lastTeleportTimestamp = currentTimeMillis;
            this.resetVelocity();
        }

        this.checkData.getPacketChecks().forEach(check -> check.handle(aPacket, currentTimeMillis));
    }
}
