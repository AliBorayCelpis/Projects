import java.util.Stack;
import java.util.LinkedList;

public class Rods {
    //Constants
    private static final int rodNumber = 3;
    
    //Instance Variables
    private Rod[] rodArray;
    private LinkedList<Integer> movesToSolve;

    public Rods(int numberOfDisks, int initialRod) {
        Rod initial = new Rod(numberOfDisks);
        rodArray = new Rod[rodNumber];
        for (int i = 0; i < rodNumber; i++) {
            if (i == initialRod) {
                rodArray[i] = initial;
            } else { 
                rodArray[i] = new Rod();
            }
        }
        rodArray[initialRod] = initial;
        movesToSolve = new LinkedList<Integer>(); 
    }

    public void initializeRods(int numberOfDisks, int initialRod) {
        Rod initial = new Rod(numberOfDisks);
        rodArray = new Rod[rodNumber];
        for (int i = 0; i < rodNumber; i++) {
            if (i == initialRod) {
                rodArray[i] = initial;
            } else { 
                rodArray[i] = new Rod();
            }
        }
        rodArray[initialRod] = initial;
    }

    public Rod[] getRodArray() {
        return rodArray;
    }

    public void moveDisk(int from, int destination) {
        Stack<Disk> fromStack = rodArray[from].getDisksOnTop();
        if (!fromStack.isEmpty()) {
            rodArray[destination].getDisksOnTop().push(fromStack.pop());
        } else {
            System.out.println("Rod is empty, no disks to move...");
        }          
    }

    public void solveTowersOfHanoi(int disksOnTop, int from, int spare, int destination) {

        if (disksOnTop >= 1) {
            solveTowersOfHanoi(disksOnTop - 1, from, destination, spare);
            moveDisk(from, destination);
            movesToSolve.addLast(from);
            movesToSolve.addLast(destination);
            solveTowersOfHanoi(disksOnTop - 1, spare, from, destination);
        }

    }

    public LinkedList<Integer> getMovesToSolve() {
        return movesToSolve;
    }
}