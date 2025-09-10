package Game;

import javax.swing.*;
import java.awt.*;


public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame main = new MainFrame();
                main.show();
            }
        });


    }
}