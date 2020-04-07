package org.sandboxpowered.sandbox.api.util.math;

/**
 * Use @{@link org.joml.Vector3dc} instead
 */
@Deprecated
public interface Vec3d {
    double getX();

    double getY();

    double getZ();

    Vec3d normalize();

    Vec3d add(double x, double y, double z);
}