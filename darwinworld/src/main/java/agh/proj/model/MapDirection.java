package agh.proj.model;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    private final static MapDirection[] ORIENTATIONS = new MapDirection[]{
            NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST};

    public static MapDirection[] getOrientations() {
        return ORIENTATIONS;
    }

    private Vector2d toVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);
        };
    }

    public int toNumber() {
        return switch (this) {
            case NORTH -> 0;
            case NORTH_EAST -> 1;
            case EAST -> 2;
            case SOUTH_EAST -> 3;
            case SOUTH -> 4;
            case SOUTH_WEST -> 5;
            case WEST -> 6;
            case NORTH_WEST -> 7;
        };
    }

    public String toString() {
        return switch (this) {
            case NORTH -> "⭡";
            case NORTH_EAST -> "⭧";
            case EAST -> "⭢";
            case SOUTH_EAST -> "⭨";
            case SOUTH -> "⭣";
            case SOUTH_WEST -> "⭩";
            case WEST -> "⭠";
            case NORTH_WEST -> "⭦";
        };
    }

    public Vector2d transform(Vector2d position) {
        return position.add(this.toVector());
    }
}
