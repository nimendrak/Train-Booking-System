import java.util.*;

public class PassengerQueue {
    private Passenger[] queueArray = new Passenger[21];
    private int firstPassenger;
    private int lastPassenger;
    private int maxLength;
    private int maxStayInQueue;

    int i = 0;
    int totalRandomTurns = 0;
    int queueLen = 0;

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
        if (maxLength > 0) {
            this.maxLength = maxLength;
        } else {
            System.out.println("This Queue is Empty!");
        }
    }

    public void setMaxStayInQueue(int maxStayInQueue) {
        this.maxStayInQueue = maxStayInQueue;
    }

    public void add(Passenger[] waitingRoom) {
        Random random = new Random();
        int randomPassengers = random.nextInt(6) + 1;
        totalRandomTurns = totalRandomTurns + randomPassengers;

        i = +i;

        System.out.println("\ni - " + i);
        System.out.println("random - " + randomPassengers);
        System.out.println("total - " + totalRandomTurns);

        //adding passengers to a temp array
        try {
            for (Passenger p : waitingRoom) {
                if (p != null && i < totalRandomTurns) {
                    queueArray[i] = p;
                    for (int j = 0; j < 42; j++) {
                        if (waitingRoom[j] == p) {
                            waitingRoom[j] = null;
                        }
                    }
                    i++;
                }
            }

            for (Passenger p : queueArray) {
                if (p != null) {
                    queueLen++;
                }
            }

            System.out.println("\nunsorted\n" + Arrays.toString(queueArray));
            for (Passenger p : queueArray) {
                if (p != null) {
                    System.out.println(p.getSeat());
                }
            }

            //sorting the queue array by seat number
            Passenger temp;
            for (int i = 0; i < queueLen; i++) {
                for (int j = i + 1; j < queueLen; j++) {
                    if (queueArray[i].getSeat() > queueArray[j].getSeat()) {
                        temp = queueArray[i];
                        queueArray[i] = queueArray[j];
                        queueArray[j] = temp;
                    }
                }
            }
        } catch (Exception e) {
            //
        }

        System.out.println("\nsorted\n" + Arrays.toString(queueArray));
        for (Passenger p : queueArray) {
            if (p != null) {
                System.out.println(p.getSeat());
            }
        }
    }

    //sort karanna
    public void remove() {
        Scanner sc = new Scanner(System.in);
        String nic = null;
        int removedSeatNumber;

        List<String> checkRemoveNicList = new ArrayList<>();

        for (Passenger p : queueArray) {
            if (p != null) {
                checkRemoveNicList.add(p.getNic());
            }
        }

        try {
            if (!checkRemoveNicList.isEmpty()) {
                System.out.print("Prompt your NIC : ");
                nic = sc.next();

                if (!checkRemoveNicList.contains(nic)) {
                    System.out.println("No seats booked under " + nic);
                } else {
                    System.out.println("\033[4;37m" + "\nYou have booked following seats" + "\033[0m");
                    for (Passenger p : queueArray) {
                        System.out.print("\033[1;31m" + "#" + p.getSeat() + "\033[0m" + " ");
                    }
                }
            } else {
                System.out.println("Train Queue is Empty!");
            }

        } catch (NullPointerException e) {
            //
        }

        try {
            if (!checkRemoveNicList.isEmpty()) {
                System.out.print("\n\nWhich seat do you want to delete (Prompt a Seat Number) : ");
                //loop till user enters a integer for seat number
                while (!sc.hasNextInt()) {
                    System.out.println("Prompt Integers to proceed!!\n");
                    System.out.print("Which seat do you want to delete (Prompt a Seat Number) : ");
                    sc.next();
                }
                removedSeatNumber = sc.nextInt();

                for (int i = 0; i < 21; i++) {
                    if (removedSeatNumber == i) {
                        queueArray[i] = null;
                        break;
                    }
                }
            }
        } catch (NullPointerException e) {
            //
        }
    }
}
