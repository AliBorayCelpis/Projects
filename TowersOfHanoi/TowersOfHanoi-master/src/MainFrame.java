import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

public class MainFrame extends JFrame {    
    //Constants
    private static final int frameWidth = 600;
    private static final int frameHeight = 500;
    private static final int rodsWidth = 550;
    private static final int rodsHeight = 400;
    private static final int defaultDiskNumber = 4;
    private static final int initialRod = 0;
    private static final String titleOfApp = "Towers of Hanoi by Group 14";
    private static final String diskNumberName = "Select the number of disks:";
    private static final int delay = 1000;
    
    //Instance Variables
    private JPanel panel;
    private JButton nextButton;
    private JButton animateButton;
    private JLabel diskNumberLabel;
    private JComboBox<Integer> diskNumberSelection;
    private JComponent mainComponent;
    private Rods rods;
    private Timer timer;
    private LinkedList<Integer> movesToSolve;


    long startTime = System.nanoTime();
    public MainFrame() {
        rods = new Rods(defaultDiskNumber, initialRod);
        createComponents();
        setTitle(titleOfApp);
        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void createComponents() {
        timer = new Timer(delay, new AnimationListener());
        
        nextButton = new JButton("Next");
        animateButton = new JButton("Animate");
        nextButton.addActionListener(new NextButtonListener());
        animateButton.addActionListener(new AnimateButtonListener());
        
        panel = new JPanel();
        
        mainComponent = new MainComponent(rods);
        mainComponent.setPreferredSize(new Dimension(rodsWidth, rodsHeight));
        
        panel.add(mainComponent);
        
        diskNumberLabel = new JLabel(diskNumberName);
        
        diskNumberSelection = new JComboBox<Integer>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        diskNumberSelection.setSelectedItem(4);
        diskNumberSelection.addActionListener(new DiskNumberChoiceListener());

        
        rods.solveTowersOfHanoi(rods.getRodArray()[initialRod].getDisksOnTop().size(), initialRod, 1, 2);


        rods.initializeRods((int) diskNumberSelection.getSelectedItem(), initialRod);
        
        panel.add(diskNumberLabel);
        panel.add(diskNumberSelection);
        panel.add(nextButton);
        panel.add(animateButton); 
        add(panel);
    }

    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            timer.stop();
            movesToSolve = rods.getMovesToSolve();
            if (!movesToSolve.isEmpty()) {
                rods.moveDisk(movesToSolve.removeFirst(), movesToSolve.removeFirst());
                ((MainComponent) mainComponent).update();
            }
        }
    }
    

    private class AnimateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            timer.start();

        }
    }


    private class AnimationListener implements ActionListener {
        private long startTime;

        @Override
        public void actionPerformed(ActionEvent event) {
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }

            movesToSolve = rods.getMovesToSolve();
            if (!movesToSolve.isEmpty()) {
                rods.moveDisk(movesToSolve.removeFirst(), movesToSolve.removeFirst());
                ((MainComponent) mainComponent).update();
            } else {
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                System.out.println("Animation time: " + duration + " milliseconds");
                timer.stop();
            }
        }
    }

    private class DiskNumberChoiceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            timer.stop();
            timer = new Timer(delay, new AnimationListener());
            rods = new Rods((int) diskNumberSelection.getSelectedItem(), initialRod);
            ((MainComponent) mainComponent).updateRods(rods);



            rods.solveTowersOfHanoi(rods.getRodArray()[initialRod].getDisksOnTop().size(), initialRod, 1, 2);
            rods.initializeRods((int) diskNumberSelection.getSelectedItem(), initialRod);
        }
    } 
}
