import javax.swing.JComponent;
import java.util.Stack;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class MainComponent extends JComponent {
    //Constants
    private static final Color[] COLOR_ARRAY = {Color.BLUE, Color.CYAN, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.RED};
    private static final int rodLength = 200;
    private static final int rodGap = 150;
    private static final int xBegin = 50;
    private static final int sideGap = 75;
    private static final int xEnd = 500;
    private static final int yEnd = 300;
    private static final int thickness = 3;
    private static final int adjustString = 30;
    
    //Instance Variabless
    private Rods rods;

    public MainComponent(Rods rods) {
        this.rods = rods;
    }

    public void update() {
        removeAll();
        revalidate();
        repaint();
    }

    public void updateRods(Rods newRods) {
        rods = newRods;
        removeAll();
        revalidate();
        repaint();
    }


    public void paintComponent(Graphics g) {
        paintRods(g);
        
        Rod[] rodArray =  rods.getRodArray();
        for (int i = 0; i < rodArray.length; i++) {
            Stack<Disk> disksOnTop  = rodArray[i].getDisksOnTop();
            for (int j = 0; j < disksOnTop.size(); j++) {
                paintDisk(disksOnTop.get(j), i, 1 + j, g);
            }
        } 
        
        g.setColor(Color.GRAY);
        g.drawString("Start", xBegin + sideGap - adjustString / 2, yEnd + adjustString);
        g.drawString("Auxiliary", xBegin + sideGap + rodGap - adjustString / 2, yEnd + adjustString);
        g.drawString("Destination", xEnd - sideGap - adjustString, yEnd + adjustString);
    }

    private void paintDisk(Disk toDraw, int rodNumber, int order, Graphics g) {
        int diskSize = toDraw.getSize();
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(thickness - 1));
        g.setColor(Color.BLACK);
        int diskWidth = diskSize * Disk.THICKNESS;
        int diskHeight = Disk.THICKNESS;

        int x = xBegin + sideGap + rodGap * rodNumber - (diskWidth / 2);
        int y = yEnd - Disk.THICKNESS * order;

        g.drawRect(x, y, diskWidth, diskHeight);

        // Set all disks to be the same color (e.g., red)
        g.setColor(Color.RED);
        g.fillRect(x, y, diskWidth, diskHeight);
    }

    private void paintRods(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(thickness));
        g.setColor(Color.DARK_GRAY);
        
        //Draw the platform 
        g.drawLine(xBegin, yEnd, xEnd, yEnd);
        //Draw the rods
        g.drawLine(xBegin + sideGap, yEnd - rodLength, xBegin + sideGap, yEnd);
        g.drawLine(xBegin + sideGap + rodGap, yEnd - rodLength, xBegin + sideGap + rodGap, yEnd);
        g.drawLine(xEnd - sideGap, yEnd - rodLength, xEnd - sideGap, yEnd);
    }

}