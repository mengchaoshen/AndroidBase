package com.smc.androidbase.gl;

import android.opengl.EGLSurface;
import android.view.Surface;


/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class GlSurface {

    public final static int TYPE_WINDOW_SURFACE = 1;
    public final static int TYPE_PBUFFER_SURFACE = 2;
    public final static int TYPE_PIXMAP_SURFACE = 3;

    public Object surface;
    public EGLSurface eglSurface;
    public ViewPort viewPort = new ViewPort();
    public int type;

    public GlSurface(Surface surface, int width, int height){
        this.surface = surface;
        this.viewPort = new ViewPort(0, 0, width, height);
        this.type = TYPE_WINDOW_SURFACE;
    }

    public class ViewPort {
        public int x;
        public int y;
        public int width;
        public int height;

        ViewPort() {
        }

        ViewPort(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
