/*
public class addTest {
    public static void main(String[] args) {
        //checking if both queues do not contains the maximum number of passengers - 42
        if (!trainQueue.isFull(trainQue1.size() + trainQue2.size())) {
            int roll = randomNum.nextInt(6) + 1;
            //checking the waiting room is not empty.
            if (!waitingRoom.isEmpty()) {
 */
/*if the generated random number is less than or equal to the size of waiting room, that
amount of passengers will be taken from the waiting room,
 * and each of those passengers will be added to the shortest queue.*//*

                if (roll <= waitingRoom.size()) {
                    for (int i = 0; i < roll; i++) {
                        if (trainQue1.size() <= trainQue2.size()) {
                            trainQueue.add(trainQue1, waitingRoom.get(i));
                            nicNumbersQ1.add(nicNumbers.get(i));
                            seatNumberTempQ1.add(seatNumberTemp.get(i));
                        } else {
                            trainQueue.add(trainQue2, waitingRoom.get(i));
                            nicNumbersQ2.add(nicNumbers.get(i));
                            seatNumberTempQ2.add(seatNumberTemp.get(i));
                        }
                    }
                    for (int i = 0; i < roll; i++) {
                        nicNumbers.remove(0);
                        seatNumberTemp.remove(0);
                        waitingRoom.remove(0);
                    }
                    // else all the passengers in the waiting room will be taken and, each of those passengers
                    // will be added to the shortest queue.
                } else {
                    for (int i = 0; i < waitingRoom.size(); i++) {
                        if (trainQue1.size() <= trainQue2.size()) {
                            trainQueue.add(trainQue1, waitingRoom.get(i));
                            nicNumbersQ1.add(nicNumbers.get(i));
                            seatNumberTempQ1.add(seatNumberTemp.get(i));
                        } else {
                            trainQueue.add(trainQue2, waitingRoom.get(i));
                            nicNumbersQ2.add(nicNumbers.get(i));
                            seatNumberTempQ2.add(seatNumberTemp.get(i));
                        }
                    }
                    waitingRoom.clear();
                }
            }
        }
*/
