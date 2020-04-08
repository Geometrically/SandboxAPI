package org.sandboxpowered.api.util;

import com.google.common.collect.Iterators;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum Direction {
    DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vector3i(0, -1, 0)),
    UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vector3i(0, 1, 0)),
    NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vector3i(0, 0, -1)),
    SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vector3i(0, 0, 1)),
    WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vector3i(-1, 0, 0)),
    EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vector3i(1, 0, 0));
    private static final Direction[] ALL = values();
    private static final Map<String, Direction> NAME_MAP = Arrays.stream(ALL).collect(Collectors.toMap(Direction::getName, (dir) -> dir));
    private static final Direction[] ID_TO_DIRECTION = Arrays.stream(ALL).sorted(Comparator.comparingInt((dir) -> dir.id)).toArray(Direction[]::new);
    private static final Direction[] HORIZONTAL = Arrays.stream(ALL).filter((dir) -> dir.getAxis().isHorizontal()).sorted(Comparator.comparingInt((dir) -> dir.horizontalId)).toArray(Direction[]::new);
    private final int id;
    private final int invertedId;
    private final int horizontalId;
    private final String name;
    private final Axis axis;
    private final AxisDirection direction;
    private final Vector3i vector;

    Direction(int id, int horizontalId, int invertedId, String name, AxisDirection direction, Axis axis, Vector3i vector) {
        this.id = id;
        this.horizontalId = horizontalId;
        this.invertedId = invertedId;
        this.name = name;
        this.axis = axis;
        this.direction = direction;
        this.vector = vector;
    }

    @Nullable
    public static Direction byName(@Nullable String name) {
        return name == null ? null : NAME_MAP.get(name.toLowerCase(Locale.ROOT));
    }

    public static Direction byId(int index) {
        return ID_TO_DIRECTION[Math.abs(index % ID_TO_DIRECTION.length)];
    }

    public static Direction fromHorizontal(int horizontalIndex) {
        return HORIZONTAL[Math.abs(horizontalIndex % HORIZONTAL.length)];
    }

    public static Direction fromRotation(double rotation) {
        return fromHorizontal((int) Math.floor(rotation / 90.0D + 0.5D) & 3);
    }

    public static Direction from(Axis axis, AxisDirection direction) {
        switch (axis) {
            case X:
                return direction == AxisDirection.POSITIVE ? EAST : WEST;
            case Y:
                return direction == AxisDirection.POSITIVE ? UP : DOWN;
            case Z:
            default:
                return direction == AxisDirection.POSITIVE ? SOUTH : NORTH;
        }
    }

    public static Direction getFacing(double x, double y, double z) {
        return getFacing((float) x, (float) y, (float) z);
    }

    public static Direction getFacing(float x, float y, float z) {
        Direction outDir = NORTH;
        float val = Float.MIN_VALUE;

        for (Direction dir : ALL) {
            float dist = x * (float) dir.vector.x() + y * (float) dir.vector.y() + z * (float) dir.vector.z();
            if (dist > val) {
                val = dist;
                outDir = dir;
            }
        }

        return outDir;
    }

    public static Direction get(AxisDirection direction, Axis axis) {
        for (Direction dir : values()) {
            if (dir.getDirection() == direction && dir.getAxis() == axis) {
                return dir;
            }
        }

        throw new IllegalArgumentException(String.format("No such direction: %s %s", direction, axis));
    }

    public int getId() {
        return this.id;
    }

    public int getHorizontalId() {
        return this.horizontalId;
    }

    public AxisDirection getDirection() {
        return this.direction;
    }

    public Direction getOppositeDirection() {
        return byId(this.invertedId);
    }

    public Direction rotateClockwise(Axis axis) {
        switch (axis) {
            case X:
                if (this != WEST && this != EAST) {
                    return this.rotateXClockwise();
                }
                return this;
            case Y:
                if (this != UP && this != DOWN) {
                    return this.rotateYClockwise();
                }
                return this;
            case Z:
                if (this != NORTH && this != SOUTH) {
                    return this.rotateZClockwise();
                }
                return this;
            default:
                throw new IllegalStateException("Unable to get clockwise facing for axis " + axis);
        }
    }

    public Direction rotateYClockwise() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    private Direction rotateXClockwise() {
        switch (this) {
            case NORTH:
                return DOWN;
            default:
                throw new IllegalStateException("Unable to get X-rotated facing of " + this);
            case SOUTH:
                return UP;
            case UP:
                return NORTH;
            case DOWN:
                return SOUTH;
        }
    }

    private Direction rotateZClockwise() {
        switch (this) {
            case EAST:
                return DOWN;
            default:
                throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
            case WEST:
                return UP;
            case UP:
                return EAST;
            case DOWN:
                return WEST;
        }
    }

    public Direction rotateYCounterClockwise() {
        switch (this) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            default:
                throw new IllegalStateException("Unable to get CounterClockwise facing of " + this);
        }
    }

    public int getOffsetX() {
        return this.axis == Axis.X ? this.direction.offset() : 0;
    }

    public int getOffsetY() {
        return this.axis == Axis.Y ? this.direction.offset() : 0;
    }

    public int getOffsetZ() {
        return this.axis == Axis.Z ? this.direction.offset() : 0;
    }

    public String getName() {
        return this.name;
    }

    public Axis getAxis() {
        return this.axis;
    }

    public float asRotation() {
        return (float) ((this.horizontalId & 3) * 90);
    }

    public String toString() {
        return this.name;
    }

    public Vector3ic getVector() {
        return this.vector;
    }

    public enum Type implements Predicate<Direction>, Iterable<Direction> {
        HORIZONTAL(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}, new Axis[]{Axis.X, Axis.Z}),
        VERTICAL(new Direction[]{Direction.UP, Direction.DOWN}, new Axis[]{Axis.Y});

        private final Direction[] facingArray;
        private final Axis[] axisArray;

        Type(Direction[] directions, Axis[] axes) {
            this.facingArray = directions;
            this.axisArray = axes;
        }

        @Override
        public boolean test(@Nullable Direction direction) {
            return direction != null && direction.getAxis().getType() == this;
        }

        @Override
        @Nonnull
        public Iterator<Direction> iterator() {
            return Iterators.forArray(facingArray);
        }
    }

    public enum AxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(-1, "Towards negative");

        private final int offset;
        private final String description;

        AxisDirection(int offset, String description) {
            this.offset = offset;
            this.description = description;
        }

        public int offset() {
            return this.offset;
        }

        public String toString() {
            return this.description;
        }
    }

    public enum Axis implements Predicate<Direction> {
        X("x"),
        Y("y"),
        Z("z");

        private final String name;

        Axis(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public boolean isVertical() {
            return this == Y;
        }

        public boolean isHorizontal() {
            return this == X || this == Z;
        }

        public String toString() {
            return this.name;
        }

        @Override
        public boolean test(@Nullable Direction direction) {
            return direction != null && direction.getAxis() == this;
        }

        public Type getType() {
            switch (this) {
                case X:
                case Z:
                    return Type.HORIZONTAL;
                case Y:
                    return Type.VERTICAL;
                default:
                    throw new Error(String.format("Unknown Axis %s!", this.getName()));
            }
        }
    }

}