package odes.visualization;

public class Camera {
    private float x, y, z, distance, angleX, angleY;
    private float speed = 1;

    public Camera(float x, float y, float z, float distance, float angleX, float angleY) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = distance;
        this.angleX = angleX;
        this.angleY = angleY;
    }

    public void moveForward(float d) {
        x -= Math.sin(angleY * Math.PI / 180) * d * speed;
        z += Math.cos(angleY * Math.PI / 180) * d * speed;
    }

    public void moveRight(float d) {
        z += Math.sin(-angleY * Math.PI / 180) * d * speed;
        x -= Math.cos(angleY * Math.PI / 180) * d * speed;
    }

    public void moveUp(float d) {
        y += d * speed;
    }

    public void addDistance(float distance) {
        this.distance += distance;
        this.distance = Math.min(this.distance, 1000);
        this.distance = Math.max(this.distance, 0);
    }

    public void addAngleX(float angleX) {
        this.angleX += angleX;
        this.angleX = Math.min(this.angleX, 90);
        this.angleX = Math.max(this.angleX, -90);
    }

    public void addAngleY(float angleY) {
        this.angleY += angleY;
    }

    public void changeSpeed(boolean inc) {
        if (inc) {
            if (speed < 10000)
                speed *= 1.5;
        } else {
            if (speed > 0.0001)
                speed /= 1.5;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getDistance() {
        return distance;
    }

    public float getAngleX() {
        return angleX;
    }

    public float getAngleY() {
        return angleY;
    }
}
