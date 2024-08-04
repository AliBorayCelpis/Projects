import java.util.Stack;

public class Rod {
    //Constants
    private static final int diskCapacity = 9;
    
    //Instance variables
    private Stack<Disk> disksOnTop;

    public Rod() {
        disksOnTop = new Stack<Disk>();
    }

    public Rod(int numberOfDisks) {
        disksOnTop = new Stack<Disk>();
        for (int i = 0; i < numberOfDisks; i++) {
            disksOnTop.push(new Disk(diskCapacity - i));
        }
    }

    public Stack<Disk> getDisksOnTop() {
        return disksOnTop;
    }
}