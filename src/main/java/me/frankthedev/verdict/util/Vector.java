package me.frankthedev.verdict.util;

public class Vector {

    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double dot(Vector other) {
        return this.x * other.getX() + this.y * other.getY() + this.z * other.getZ();
    }

    public double length() {
        return Math.sqrt(Math.pow(this.x, 2.0D) + Math.pow(this.y, 2.0D) + Math.pow(this.z, 2.0D));
    }

    public float angle(Vector other) {
        return (float) Math.acos(this.dot(other) / (this.length() * other.length()));
    }

    public Vector normalize() {
        double length = this.length();
        this.x /= length;
        this.y /= length;
        this.z /= length;
        return this;
    }

    public Vector subtract(Vector other) {
        this.x -= other.getX();
        this.y -= other.getY();
        this.z -= other.getZ();
        return this;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
