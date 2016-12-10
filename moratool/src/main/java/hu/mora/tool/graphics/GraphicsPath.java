package hu.mora.tool.graphics;

import java.io.InputStream;

public class GraphicsPath {

    private static final String PRE = "/graphics/";

    public static InputStream okIcon() {
        return GraphicsPath.class.getResourceAsStream(PRE + "ok.png");
    }

    public static InputStream errorIcon() {
        return GraphicsPath.class.getResourceAsStream(PRE + "error.png");
    }
}
