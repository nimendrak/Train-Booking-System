import java.util.*;

public class PassengerQueue {
    private Passenger[] queueArray = new Passenger[42];
    private int firstPassenger;
    private int lastPassenger;
    private int maxLength;
    private int maxStayInQueue;

    int i = 0;
    int totalRandomTurns = 0;

    public Passenger[] getQueueArray() {
        return queueArray;
    }

    public int getFirstPassenger() {
        return firstPassenger;
    }

    public void setFirstPassenger(int firstPassenger) {
        this.firstPassenger = firstPassenger;
    }

    public int getLastPassenger() {
        return lastPassenger;
    }

    public int getMaxStayInQueue() {
        return maxStayInQueue;
    }

    public void setLastPassenger(int lastPassenger) {
        this.lastPassenger = lastPassenger;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setMaxStayInQueue(int maxStayInQueue) {
        this.maxStayInQueue = maxStayInQueue;
    }

    private void add(Passenger[] waitingRoom) {
        Random random = new Random();
        int randomPassengers = random.nextInt((6));
        totalRandomTurns = totalRandomTurns + randomPassengers;

        i = +i;

        System.out.println("\ni - " + i);
        System.out.println("random - " + randomPassengers);
        System.out.println("total - " + totalRandomTurns);

        for (Passenger p : waitingRoom) {
            if (p != null && i < totalRandomTurns) {
                queueArray[i] = p;
                    for (int j = 0; j < 41; j++) {
                        if (waitingRoom[j] == p) {
                            waitingRoom[j] = null;
                        }
                    }
                i++;
            }
        }
        System.out.println("i++ - " + i);
    }

    private void remove() {
        Scanner sc = new Scanner(System.in);
        String nic;
        String checkRemoveName;
        int removedSeatNumber;

        List<String> checkRemoveNicList = new ArrayList<>();
        List<Integer> checkRemoveSeat = new ArrayList<>();
        for (Passenger p : queueArray) {
            if (p != null) {
                checkRemoveNicList.add(p.getNic());
                checkRemoveSeat.add(p.getSeat());
            }
        }

        System.out.print("Prompt your First Name : ");
        checkRemoveName = sc.next();

        System.out.print("Prompt your NIC : ");
        nic = sc.next();

        if (checkRemoveNicList.contains(nic)) {
            try {
                System.out.println("\033[4;37m" + "\nYou have booked following seats" + "\033[0m");
                for (Passenger p : queueArray) {
                    if (p.getNic().equals(nic) & p.getFirstName().equals(checkRemoveName)) {
                        System.out.print("\033[1;31m" + "#" + p.getSeat() + "\033[0m" + " ");
                    }

                    System.out.print("\nWhich seat do you want to delete (Prompt a Seat Number) : ");
                    //loop till user enters a integer for seat number
                    while (!sc.hasNextInt()) {
                        System.out.println("Prompt Integers to proceed!!\n");
                        System.out.print("Which seat do you want to delete (Prompt a Seat Number) : ");
                        sc.next();
                    }
                    removedSeatNumber = sc.nextInt();

                    if (checkRemoveSeat.contains(removedSeatNumber)) {
                        queueArray[removedSeatNumber] = null;
                    }
                }
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                //
            }
        } else {
            System.out.println("No seats booked under " + nic);
        }
    }
}
