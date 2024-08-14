package src.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    public static final int SCREEN = 800;
    public static final int ARRAY_LENGTH = 200;
    public static final int ARRAY_MULTIPLIER = (int) SCREEN / ARRAY_LENGTH;
    public static final long TIME_DELAY = 25; 
    public ArrayList<ArrayList<Boolean>> screen;

    public MainPanel() {
        this.setPreferredSize(new Dimension(SCREEN, SCREEN));
        screen = new ArrayList<ArrayList<Boolean>>();
        initArray();
    }

    public void paint(Graphics g) {
        g = (Graphics2D) g;

        for (int i = 0; i < ARRAY_LENGTH; i++) {
            ArrayList<Boolean> temp = screen.get(i);
            for (int j = 0; j < ARRAY_LENGTH; j++) {
                if (temp.get(j)) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect((i - 1) * ARRAY_MULTIPLIER, (j - 1) * ARRAY_MULTIPLIER, ARRAY_MULTIPLIER,
                ARRAY_MULTIPLIER);
            }
        }
    }

    private void initArray() {
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            ArrayList<Boolean> temp = new ArrayList<>();
            for (int j = 0; j < ARRAY_LENGTH; j++) {
                temp.add(false);
            }
            screen.add(temp);
        }

        screen.get(100).set(100, true);
        screen.get(100).set(101, true);
        screen.get(100).set(99, true);
        screen.get(99).set(100, true);
        screen.get(101).set(101, true);
    }

    public void loop() {
        while (true) {
            try {
                Thread.sleep(TIME_DELAY);
            } catch (InterruptedException e) {
                System.out.println("Caught InterruptedException");
                System.exit(0);
            }
            screen = updateArray();
            repaint();
        }
    }

    private ArrayList<ArrayList<Boolean>> updateArray() {
        ArrayList<ArrayList<Boolean>> temp = new ArrayList<ArrayList<Boolean>>();
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            temp.add(new ArrayList<>());
            for (int j = 0; j < ARRAY_LENGTH; j++) {
                temp.get(i).add(screen.get(i).get(j));
                int neighbours = checkNeighbours(i, j);
                if (neighbours < 2) {
                    temp.get(i).set(j, false);
                } else if (neighbours == 3) {
                    temp.get(i).set(j, true);
                } else if (neighbours > 3) {
                    temp.get(i).set(j, false);
                }
            }
        }

        return temp;
    }

    private int checkNeighbours(int i, int j) {
        int neighbours = 0;

        if (i - 1 >= 0 && screen.get(i - 1).get(j)) {
            neighbours++;
        }
        if (i + 1 < ARRAY_LENGTH && screen.get(i + 1).get(j)) {
            neighbours++;
        }
        if (j - 1 >= 0 && screen.get(i).get(j - 1)) {
            neighbours++;
        }
        if (j + 1 < ARRAY_LENGTH && screen.get(i).get(j + 1)) {
            neighbours++;
        }
        if (i + 1 < ARRAY_LENGTH && j + 1 < ARRAY_LENGTH && screen.get(i + 1).get(j + 1)) {
            neighbours++;
        }
        if (i - 1 >= 0 && j + 1 < ARRAY_LENGTH && screen.get(i - 1).get(j + 1)) {
            neighbours++;
        }
        if (i + 1 < ARRAY_LENGTH && j - 1 >= 0 && screen.get(i + 1).get(j - 1)) {
            neighbours++;
        }
        if (i - 1 >= 0 && j - 1 >= 0 && screen.get(i - 1).get(j - 1)) {
            neighbours++;
        }

        return neighbours;
    }
}
