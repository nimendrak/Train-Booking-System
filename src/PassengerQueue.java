import java.util.*;

public class PassengerQueue {
    private final Passenger[] queueArray = new Passenger[21];
    private int maxLength;
    private int maxStayInQueue;

    int next = 0;
    int lenCounter = 0;

    public boolean isEmpty() {
        for (Passenger p : queueArray) {
            if (p != null) {
                lenCounter++;
            }
        }
        boolean isEmpty = false;
        if (lenCounter == 0) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public boolean isFull() {
        for (Passenger p : queueArray) {
            if (p != null) {
                lenCounter++;
            }
        }
        boolean isEmpty = false;
        if (lenCounter == 21) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public Passenger[] getQueueArray() {
        return queueArray;
    }

    public int getMaxStayInQueue() {
        return maxStayInQueue;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        if (maxLength > 0) {
            this.maxLength = maxLength;
        }
    }

    public void setMaxStayInQueue(int maxStayInQueue) {
        this.maxStayInQueue = maxStayInQueue;
    }

    public void add(Passenger passenger) {
        queueArray[next++] = passenger;
    }

    public void remove(int index) {
        queueArray[index] = null;
    }

}
