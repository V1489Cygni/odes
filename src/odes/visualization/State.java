package odes.visualization;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.Sphere;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class State {
    private Camera camera;

    private boolean mouseLastDown;
    private int mouseX, mouseY;

    private double[] x, y, z;

    public State(double[] x, double[] y, double[] z) throws IOException {
        camera = new Camera(0, 0, 0, 10, 0, 180);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void update(int delta) {
        updateCamera(delta);
    }

    private void updateCamera(int delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            camera.moveForward(delta / 100f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            camera.moveForward(-delta / 100f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            camera.moveRight(delta / 100f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            camera.moveRight(-delta / 100f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            camera.moveUp(delta / 100f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            camera.moveUp(-delta / 100f);
        }
        if (Mouse.isButtonDown(0)) {
            if (mouseLastDown) {
                camera.addAngleY((Mouse.getX() - mouseX) * delta / 100f);
                camera.addAngleX((mouseY - Mouse.getY()) * delta / 100f);
            }
            mouseX = Mouse.getX();
            mouseY = Mouse.getY();
        }
        mouseLastDown = Mouse.isButtonDown(0);
        camera.addDistance(-Mouse.getDWheel() / 100f);
    }

    public Camera getCamera() {
        return camera;
    }

    public void render() {
        glLineWidth(3);
        glColor3f(1, 1, 1);

        glBegin(GL_LINE_STRIP);

        for (int i = 0; i < x.length; i++) {
            glVertex3d(x[i], y[i], z[i]);
        }

        glEnd();

        glColor3f(1, 0, 0);

        glBegin(GL_LINE_STRIP);

        glVertex3f(-100, 0, 0);
        glVertex3f(100, 0, 0);

        glEnd();

        glColor3f(0, 1, 0);

        glBegin(GL_LINE_STRIP);

        glVertex3f(0, -100, 0);
        glVertex3f(0, 100, 0);

        glEnd();

        glColor3f(0, 0, 1);

        glBegin(GL_LINE_STRIP);

        glVertex3f(0, 0, -100);
        glVertex3f(0, 0, 100);

        glEnd();

        glColor3f(1, 1, 1);

        Sphere sphere = new Sphere();
        sphere.draw(0.05f, 100, 100);

        drawSphere(0.05f, (float) x[0], (float) y[0], (float) z[0], 1, 1, 0, sphere);
        int n = x.length - 1;
        drawSphere(0.05f, (float) x[n], (float) y[n], (float) z[n], 1, 0, 1, sphere);

        drawSphere(0.05f, 1, 0, 0, 0, 1, 0, sphere);
        drawSphere(0.05f, 0, 1, 0, 0, 0, 1, sphere);
        drawSphere(0.05f, 0, 0, 1, 1, 0, 0, sphere);

        drawSphere(0.05f, -1, 0, 0, 1, 0, 0, sphere);
        drawSphere(0.05f, 0, -1, 0, 0, 1, 0, sphere);
        drawSphere(0.05f, 0, 0, -1, 0, 0, 1, sphere);
    }

    private void drawSphere(float radius, float x, float y, float z, float r, float g, float b, Sphere s) {
        glColor3f(r, g, b);
        glTranslatef(x, y, z);
        s.draw(radius, 100, 100);
        glTranslatef(-x, -y, -z);
    }
}
