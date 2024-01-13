package agh.proj.model;

import java.util.Objects;

public class Vector2d {
    private final int x, y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    private boolean equals_x(Vector2d other) {
        return this.x == other.x;
    }

    private boolean equals_y(Vector2d other) {
        return this.y == other.y;
    }

    @Override
    public boolean equals(Object o) {
        Vector2d other = (Vector2d) o;
        return this.equals_x(other) && this.equals_y(other);
    }
}