package odes.visualization;

import org.lwjgl.opengl.Display;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Renderer {

    public Renderer() throws IOException {
        int width = Display.getDisplayMode().getWidth();
        int height = Display.getDisplayMode().getHeight();

        glViewport(0, 0, width, height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45.0f, ((float) width / (float) height), 0.1f, 1000);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glShadeModel(GL_SMOOTH);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClearDepth(1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    public void resize() {
        int height = Display.getHeight(), width = Display.getWidth();
        float aspect = (float) width / height;
        glViewport(0, 0, width, height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45.0f, aspect, 0.1f, 1000);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public void render(State state) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glLoadIdentity();

        Camera camera = state.getCamera();

        glLoadIdentity();
        glTranslatef(0, 0, -camera.getDistance());
        glRotatef(camera.getAngleX(), 1, 0, 0);
        glRotatef(camera.getAngleY(), 0, 1, 0);
        glTranslatef(camera.getX(), camera.getY(), camera.getZ());

        state.render();
    }
}
