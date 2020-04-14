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
        } catch (Exception ignored) {
        }

        try {
            if (checkRemoveNicList.contains(nic)) {
                System.out.print("\n\nWhich seat do you want to delete (Prompt a Seat Number) : ");
                //loop till user enters a integer for seat number
                while (!sc.hasNextInt()) {
                    System.out.println("Prompt Integers to proceed!!\n");
                    System.out.print("Which seat do you want to delete (Prompt a Seat Number) : ");
                    sc.next();
                }
                removedSeatNumber = sc.nextInt();

                int index = 0;
                for (Passenger p : queueArray) {
                    if (p != null) {
                        for (int i = 0; i < 21; i++) {
                            if (removedSeatNumber == p.getSeat()) {
                                System.out.println("passenger" + p);
                                System.out.println("index - " + findIndex(queueArray, p));
                                index = findIndex(queueArray, p);
                                break;
                            }
                        }
                    }
                }

                for (int i = index; i <= queueArray.length; i++) {
                    queueArray[i] = queueArray[i + 1];
                }

            }
        } catch (Exception ignored) {
        }
    }

    // Linear-search function to find the index of an element
    public static int findIndex(Passenger[] arr, Passenger t) {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {
            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            } else {
                i = i + 1;
            }
        }
        return -1;
    }

}
