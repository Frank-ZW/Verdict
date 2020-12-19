package me.frankthedev.verdict.data;

import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.check.types.ReachCheck;
import me.frankthedev.verdict.check.types.RotationCheck;
import me.frankthedev.verdict.check.Check;

import java.util.ArrayList;
import java.util.List;

public class CheckData {

    private final List<Check> checks;
    private final List<PositionCheck> positionChecks;
    private final List<RotationCheck> rotationChecks;
    private final List<PacketCheck> packetChecks;
    private final List<ReachCheck> reachChecks;

    public CheckData(List<Check> checks) {
        this.checks = new ArrayList<>();
        this.positionChecks = new ArrayList<>();
        this.rotationChecks = new ArrayList<>();
        this.packetChecks = new ArrayList<>();
        this.reachChecks = new ArrayList<>();
        for (Check check : checks) {
            this.checks.add(check);
            if (check instanceof PositionCheck) {
                this.positionChecks.add((PositionCheck) check);
            } else if (check instanceof RotationCheck) {
                this.rotationChecks.add((RotationCheck) check);
            } else if (check instanceof PacketCheck) {
                this.packetChecks.add((PacketCheck) check);
            } else if (check instanceof ReachCheck) {
                this.reachChecks.add((ReachCheck) check);
            }
        }
    }

    public List<PositionCheck> getMovementChecks() {
        return this.positionChecks;
    }

    public List<RotationCheck> getRotationChecks() {
        return this.rotationChecks;
    }

    public List<PacketCheck> getPacketChecks() {
        return this.packetChecks;
    }

    public List<ReachCheck> getReachChecks() {
        return this.reachChecks;
    }

    public List<Check> getChecks() {
        return this.checks;
    }
}
