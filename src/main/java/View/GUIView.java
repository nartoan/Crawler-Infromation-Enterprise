package View;

import javax.swing.*;

/**
 * Created by DucToan on 15/03/2017.
 */
class GUIView extends JFrame {
    JFrame frameTool;

    public static void main(String[] args) {
        GUIView guiView = new GUIView();
    }

    public GUIView() {
        frameTool = new JFrame("GET INFOR ENTERPRISE");
        frameTool.setSize(500,400);
        frameTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frameTool.pack();
        frameTool.setLocationRelativeTo(null);
        frameTool.setVisible(true);
    }
}
