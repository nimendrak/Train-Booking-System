import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.*;

public class TrainStation extends Application {

    static final int SEATING_CAPACITY = 42;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("\n*********************************************");
        System.out.println("*** " + "\033[1;93m" + " DENUWARA MANIKE TRAIN QUEUE PROGRAM " + "\033[0m" + " ***");
        System.out.println("*********************************************");

        Scanner sc = new Scanner(System.in);
        LocalDate localDate = LocalDate.now();

        //store booking data in this multi-dimensional array
        String[][][][] passengersArray = new String[5][31][SEATING_CAPACITY][4];

        //creating 42 different objects as passengers for each destination
        Passenger[] colomboToHattonWaitingRoom = new Passenger[42];
        Passenger[] colomboToEllaWaitingRoom = new Passenger[42];
        Passenger[] colomboToBadullaWaitingRoom = new Passenger[42];
        Passenger[] badullaToHattonWaitingRoom = new Passenger[42];
        Passenger[] badullaToEllaWaitingRoom = new Passenger[42];
        Passenger[] badullaToColomboWaitingRoom = new Passenger[42];

        //boarded passengers
        Passenger[] boardedPassengers = new Passenger[42];

        //PassengerQueue 01
        PassengerQueue passengerQueueOne = new PassengerQueue();
        //PassengerQueue 02
        PassengerQueue passengerQueueTwo = new PassengerQueue();

        //load booking data which was stored in CWK01
        try {
            loadBookingsData(0, passengersArray);
            loadBookingsData(1, passengersArray);
            loadBookingsData(2, passengersArray);
            loadBookingsData(3, passengersArray);
            loadBookingsData(4, passengersArray);
            loadBookingsData(5, passengersArray);
            System.out.println("Loading data...");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("\nBooking data on " + "\033[1;31m" + localDate + "\033[0m" + " successfully loaded!");
        } catch (Exception e) {
            System.out.println("File is corrupted!");
        }

        int station;
        do {
            System.out.print("\nSelect a Destination to enter to the Waiting Room\n01 Colombo to Hatton\n02 Colombo to Ella\n03 Colombo to Badulla\n04 Badulla to Hatton\n05 Badulla to Ella\n06 Badulla to Colombo\n\nPrompt 1 - 6 to proceed : ");
            while (!sc.hasNextInt()) {
                System.out.println("Prompt Integers to proceed!!");
                System.out.print("\nSelect a Destination to enter to the Waiting Room\n01 Colombo to Hatton\n02 Colombo to Ella\n03 Colombo to Badulla\n04 Badulla to Hatton\n05 Badulla to Ella\n06 Badulla to Colombo\n\nPrompt 1 - 6 to proceed : ");
                sc.next();
            }
            station = (sc.nextInt() - 1);
        } while (station != 0 & station != 1 & station != 2 & station != 3 & station != 4 & station != 5 & station != 6);

        String userOption;
        do {
            System.out.println("\nChoose a option, which mentioned below\n");
            System.out.println("Prompt \"W\" to enter to the Waiting Room");
            System.out.println("Prompt \"A\" to add passenger to the Train Queue");
            System.out.println("Prompt \"V\" to display Train Queue");
            System.out.println("Prompt \"D\" to delete passenger from Train Queue");
            System.out.println("Prompt \"S\" to store program data into a file");
            System.out.println("Prompt \"L\" to load program data from a file");
            System.out.println("Prompt \"R\" to run a simulation and generate the report");
            System.out.println("Prompt \"Q\" to exit from the program");

            System.out.print("\nPrompt your option : ");
            userOption = sc.next();

            switch (userOption) {
                case "W":
                case "w":
                    if (station == 0) {
                        addArrivedPassengersToWaitingRoomGUI(station, passengersArray, colomboToHattonWaitingRoom, passengerQueueOne, passengerQueueTwo, boardedPassengers);
                    } else if (station == 1) {
                        addArrivedPassengersToWaitingRoomGUI(station, passengersArray, colomboToEllaWaitingRoom, passengerQueueOne, passengerQueueTwo, boardedPassengers);
                    } else if (station == 2) {
                        addArrivedPassengersToWaitingRoomGUI(station, passengersArray, colomboToBadullaWaitingRoom, passengerQueueOne, passengerQueueTwo, boardedPassengers);
                    } else if (station == 3) {
                        addArrivedPassengersToWaitingRoomGUI(station, passengersArray, badullaToHattonWaitingRoom, passengerQueueOne, passengerQueueTwo, boardedPassengers);
                    } else if (station == 4) {
                        addArrivedPassengersToWaitingRoomGUI(station, passengersArray, badullaToEllaWaitingRoom, passengerQueueOne, passengerQueueTwo, boardedPassengers);
                    } else if (station == 5) {
                        addArrivedPassengersToWaitingRoomGUI(station, passengersArray, badullaToColomboWaitingRoom, passengerQueueOne, passengerQueueTwo, boardedPassengers);
                    }
                    break;

                case "A":
                case "a":
                    if (station == 0) {
                        addPassengersToQueue(colomboToHattonWaitingRoom, passengerQueueOne, passengerQueueTwo);
                    } else if (station == 1) {
                        addPassengersToQueue(colomboToEllaWaitingRoom, passengerQueueOne, passengerQueueTwo);
                    } else if (station == 2) {
                        addPassengersToQueue(colomboToBadullaWaitingRoom, passengerQueueOne, passengerQueueTwo);
                    } else if (station == 3) {
                        addPassengersToQueue(badullaToHattonWaitingRoom, passengerQueueOne, passengerQueueTwo);
                    } else if (station == 4) {
                        addPassengersToQueue(badullaToEllaWaitingRoom, passengerQueueOne, passengerQueueTwo);
                    } else if (station == 5) {
                        addPassengersToQueue(badullaToColomboWaitingRoom, passengerQueueOne, passengerQueueTwo);
                    }
                    break;

                case "V":
                case "v":
                    if (station == 0) {
                        displayAll(0, passengersArray, colomboToHattonWaitingRoom, boardedPassengers, passengerQueueOne, passengerQueueTwo);
                    } else if (station == 1) {
                        displayAll(1, passengersArray, colomboToEllaWaitingRoom, boardedPassengers, passengerQueueOne, passengerQueueTwo);
                    } else if (station == 2) {
                        displayAll(2, passengersArray, colomboToEllaWaitingRoom, boardedPassengers, passengerQueueOne, passengerQueueTwo);
                    } else if (station == 3) {
                        displayAll(3, passengersArray, colomboToEllaWaitingRoom, boardedPassengers, passengerQueueOne, passengerQueueTwo);
                    } else if (station == 4) {
                        displayAll(4, passengersArray, colomboToEllaWaitingRoom, boardedPassengers, passengerQueueOne, passengerQueueTwo);
                    } else if (station == 5) {
                        displayAll(5, passengersArray, colomboToEllaWaitingRoom, boardedPassengers, passengerQueueOne, passengerQueueTwo);
                    }
                    break;

                case "D":
                case "d":
                    deletePassengerFromQueue(passengerQueueOne, passengerQueueTwo);
                    break;

                case "S":
                case "s":
                    storeData(station, passengerQueueOne, passengerQueueTwo);
                    break;

                case "L":
                case "l":
                    loadData(passengerQueueOne, passengerQueueTwo);
                    break;

                case "R":
                case "r":
                    generateReport(station, passengerQueueOne, passengerQueueTwo, boardedPassengers);
                    break;

                case "q":
                case "Q":
                    System.out.print("\nIf you want to store data before, you exit from the program\nPrompt \"S\" or prompt any key to Exit : ");
                    String option = sc.next();
                    if (option.equalsIgnoreCase("s")) {
                        storeData(station, passengerQueueOne, passengerQueueTwo);
                    } else {
                        System.out.println("\nProgram is now Exiting..");
                    }
                    break;

                default:
                    System.out.println("\nYou have entered a Invalid Input!");
                    System.out.println("---------------------------------");
            }
        } while (true);
    }

    int[] index = new int[1];
    int totalRandomTurns = 0;
    double sumOfTime = 0;
    int i = 0;

    //type one alert box act as a confirmation box for the quit the current stage
    private void quitAlertWindow(Stage window) {
        Stage alertBoxWindow = new Stage();

        //Block events to other windows
        alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
        alertBoxWindow.setTitle("Alert!");

        Image windowIcon = new Image(getClass().getResourceAsStream("alertIcon.png"));
        alertBoxWindow.getIcons().add(windowIcon);

        alertBoxWindow.setMinWidth(300);
        alertBoxWindow.setMinHeight(150);

        VBox layout = new VBox(10);
        GridPane gridPane = new GridPane();
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alertBoxWindow.setScene(scene);

        Button okBtn = new Button("OK");
        new ButtonFX().addBtn(okBtn);
        okBtn.setPrefWidth(50);

        Button cancelBtn = new Button("Cancel");
        new ButtonFX().closeBtn(cancelBtn);

        Label label = new Label("Do you want to exit?");
        label.setFont(new Font("Arial Bold", 16));

        okBtn.setOnAction(event -> {
            alertBoxWindow.close();
            window.close();
        });

        cancelBtn.setOnAction(event -> alertBoxWindow.hide());

        layout.getChildren().add(label);
        layout.getChildren().add(gridPane);

        gridPane.add(okBtn, 0, 0);
        gridPane.add(cancelBtn, 1, 0);

        gridPane.setPadding(new Insets(0, 0, 0, 75));
        gridPane.setHgap(5);

        alertBoxWindow.showAndWait();
    }

    //type two alert box popup a new window and show messages according to the the parameters
    private void alertWindow(String title, String message, String iconType) {
        Stage alertBoxWindow = new Stage();

        //Block events to other windows
        alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
        alertBoxWindow.setTitle(title);

        if (iconType.equals("1")) {
            Image windowIcon = new Image(getClass().getResourceAsStream("alertIcon.png"));
            alertBoxWindow.getIcons().add(windowIcon);
        } else {
            Image windowIcon = new Image(getClass().getResourceAsStream("confirmIcon.png"));
            alertBoxWindow.getIcons().add(windowIcon);
        }

        alertBoxWindow.setMinWidth(350);
        alertBoxWindow.setMinHeight(150);

        Label label = new Label();
        label.setFont(new Font("Arial Bold", 16));
        label.setPadding(new Insets(0, 0, 5, 0));
        label.setText(message);

        Button closeButton = new Button("Close");
        new ButtonFX().closeBtn(closeButton);
        closeButton.setOnAction(e -> alertBoxWindow.close());

        VBox layout = new VBox(5);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        alertBoxWindow.setScene(scene);
        alertBoxWindow.showAndWait();
    }

    private void loadBookingsData(int station, String[][][][] passengersArray) {
        //get current date
        LocalDate localDate = LocalDate.now();

        //initializing text file
        File file = null;

        if (station == 0) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\storeData\\ColomboToHatton.txt");
        } else if (station == 1) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\storeData\\ColomboToElla.txt");
        } else if (station == 2) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\storeData\\ColomboToBadulla.txt");
        } else if (station == 3) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\storeData\\BadullaToHatton.txt");
        } else if (station == 4) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\storeData\\BadullaToElla.txt");
        } else {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\storeData\\BadullaToColombo.txt");
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            //create BufferedReader object from the File
            String line;

            //read file line by line
            while ((line = bufferedReader.readLine()) != null) {
                //split the line by | and put it to a string array
                String[] parts = line.split(" ");

                String bookedDate = parts[9];
                String passengerName = parts[14];
                String passengerSurname = parts[15];
                String nic = parts[19];
                String[] seatNumber = parts[22].split("#");

                //add current date's booking data to the multi-dimensional array
                if (String.valueOf(localDate).equals(bookedDate)) {
                    passengersArray[station][(parseInt(bookedDate.substring(8, 10))) - 1][parseInt(seatNumber[1]) - 1][0] = passengerName;
                    passengersArray[station][(parseInt(bookedDate.substring(8, 10))) - 1][parseInt(seatNumber[1]) - 1][1] = passengerSurname;
                    passengersArray[station][(parseInt(bookedDate.substring(8, 10))) - 1][parseInt(seatNumber[1]) - 1][2] = nic;
                    passengersArray[station][(parseInt(bookedDate.substring(8, 10))) - 1][parseInt(seatNumber[1]) - 1][3] = seatNumber[1];
                }
            }
            try {
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int secondsInQueue() {
        Random random = new Random();

        int randomSecondsOne = random.nextInt(6) + 1;
        int randomSecondsTwo = random.nextInt(6) + 1;
        int randomSecondsThree = random.nextInt(6) + 1;

        return randomSecondsOne + randomSecondsTwo + randomSecondsThree;
    }

    private void bookingDetailsAndSeatColor(int station, String[][][][] passengersArray, PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo,
                                            Passenger[] boardedPassengers, int i, ToggleButton seat, ToggleButton btnOne, ToggleButton btnTwo,
                                            String actionType, VBox row) {
        LocalDate localDate = LocalDate.now();
        int localDateInt = parseInt(String.valueOf(localDate).substring(8, 10)) - 1;

        List<Integer> bookedSeats = new ArrayList<>();
        List<Integer> boardedPassengersSeats = new ArrayList<>();

        int queueOneCounter = 0;
        int queueTwoCounter = 0;
        int boardedCounter = 0;

        for (Passenger p : boardedPassengers) {
            if (p != null) {
                boardedPassengersSeats.add(p.getSeat());
            }
        }

        //booked passengers
        try {
            for (int j = 1; j <= SEATING_CAPACITY; j++) {
                if (passengersArray[station][localDateInt][i - 1][3] != null) {
                    bookedSeats.add(parseInt(passengersArray[station][localDateInt][i - 1][3]));
                    seat.setStyle("-fx-background-color: null; -fx-border-color: rgba(0,166,156,0.8); -fx-border-width: 3; -fx-border-radius: 8");
                } else {
                    btnOne.setDisable(true);
                    btnTwo.setDisable(true);
                    seat.setStyle("-fx-background-color: null; -fx-border-color: #E3236D; -fx-border-width: 3; -fx-border-radius: 8");
                }
            }
        } catch (Exception e) {
            //passengerQueue is empty at the first run
        }

        //queueOne passengers
        try {
            for (Passenger q : passengerQueueOne.getQueueArray()) {
                if (q.getSeat() == i) {
                    System.out.println("i - " + i);
                    queueOneCounter++;
                    seat.setStyle("-fx-background-color: null; -fx-border-color: #2e8ce1; -fx-border-width: 3; -fx-border-radius: 8");
                }
            }
        } catch (Exception e) {
            //passengerQueue is empty at the first run
        }
        //queueTwo passengers
        try {
            for (Passenger r : passengerQueueTwo.getQueueArray()) {
                if (r.getSeat() == i) {
                    System.out.println("i - " + i);
                    queueTwoCounter++;
                    seat.setStyle("-fx-background-color: null; -fx-border-color: #2e8ce1; -fx-border-width: 3; -fx-border-radius: 8");
                }
            }
        } catch (Exception e) {
            //passengerQueue is empty at the first run
        }
        //boarded passengers
        try {
            for (Passenger p : boardedPassengers) {
                if (p.getSeat() == i) {
                    System.out.println("boarded passengers - " + i);
                    boardedCounter++;
                    seat.setStyle("-fx-background-color: null; -fx-border-color: #2e8ce1; -fx-border-width: 3; -fx-border-radius: 8");
                }
            }
        } catch (Exception e) {
            //passengerQueue is empty at the first run
        }

        if (actionType.equals("displaySlotName")) {
            Label slotName;
            if (passengersArray[station][localDateInt][i - 1][3] == null) {
                slotName = new Label("   Absent");
                slotName.setFont(new Font("Arial Bold", 14));
                slotName.setTextFill(Paint.valueOf("#E3236D"));
                row.getChildren().addAll(slotName);
            } else {
                if (boardedCounter != 0) {
                    //boarded passengers
                    try {
                        for (Passenger p : boardedPassengers) {
                            if (i == p.getSeat()) {
                                slotName = new Label("    Moved");
                                slotName.setFont(new Font("Arial Bold", 14));
                                slotName.setTextFill(Paint.valueOf("#2e8ce1"));
                                row.getChildren().addAll(slotName);
                            }
                        }
                    } catch (Exception e) {
                        //passengerQueue is empty at the first run
                    }
                } else if (queueOneCounter == 0 & queueTwoCounter == 0) {
                    slotName = new Label("   Pending");
                    slotName.setFont(new Font("Arial Bold", 14));
                    slotName.setTextFill(Paint.valueOf("#00A69C"));
                    row.getChildren().addAll(slotName);
                } else {
                    //queueOne passengers
                    try {
                        for (Passenger p : passengerQueueOne.getQueueArray()) {
                            if (i == p.getSeat()) {
                                slotName = new Label("    Moved");
                                slotName.setFont(new Font("Arial Bold", 14));
                                slotName.setTextFill(Paint.valueOf("#2e8ce1"));
                                row.getChildren().addAll(slotName);
                            }
                        }
                    } catch (Exception e) {
                        //passengerQueue is empty at the first run
                    }
                    //queueTwo passengers
                    try {
                        for (Passenger q : passengerQueueTwo.getQueueArray()) {
                            if (i == q.getSeat()) {
                                slotName = new Label("    Moved");
                                slotName.setFont(new Font("Arial Bold", 14));
                                slotName.setTextFill(Paint.valueOf("#2e8ce1"));
                                row.getChildren().addAll(slotName);
                            }
                        }
                    } catch (Exception e) {
                        //
                    }
                }
            }
        }

        seat.setOnAction(event -> {
            if (bookedSeats.contains(i) | boardedPassengersSeats.contains(i)) {
                if (seat.isSelected()) {
                    Stage passengerDetails = new Stage();
                    passengerDetails.initModality(Modality.APPLICATION_MODAL);

                    FlowPane flowPane = new FlowPane();
                    flowPane.setPadding(new Insets(30));

                    Image windowIcon = new Image(getClass().getResourceAsStream("pendingIcon.png"));
                    passengerDetails.getIcons().add(windowIcon);

                    passengerDetails.setTitle("Booking Details");

                    Label headerConfirmationBox = new Label("Booking Details");
                    headerConfirmationBox.setStyle("-fx-underline: true");
                    headerConfirmationBox.setFont(new Font("Arial Bold", 22));
                    headerConfirmationBox.setTextFill(Paint.valueOf("#414141"));
                    headerConfirmationBox.setPadding(new Insets(0, 0, 30, 0));

                    HBox destinationBox = new HBox();
                    HBox bookedDateBox = new HBox();
                    HBox passengerNameBox = new HBox();
                    HBox passengerNicBox = new HBox();
                    passengerNicBox.setPadding(new Insets(0, 0, 25, 0));
                    VBox seatBox = new VBox();

                    Label empty = new Label();
                    empty.setPadding(new Insets(0, 0, 0, 100));

                    Button closeBtn = new Button("Close");
                    new ButtonFX().closeBtn(closeBtn);

                    Text destinationText = new Text("Destinations           - ");
                    Text bookedDateText = new Text("Booked Date          -  ");
                    Text passengerNameText = new Text("Passenger Name    -  ");
                    Text passengerNicText = new Text("Passenger NIC        -  ");

                    Label destinationLabel = null;
                    Label bookedDateLabel = null;
                    Label passengerNameLabel = null;
                    Label passengerNicLabel = null;
                    Label status = null;

                    String destination;
                    if (station == 0) {
                        destination = " Badulla to Colombo";
                    } else {
                        destination = " Colombo to Badulla";
                    }

                    Label seatText = new Label("SEAT");
                    seatText.setFont(new Font("Arial Bold", 100));
                    Label seatLabel;

                    if (i < 10) {
                        seatLabel = new Label("#0" + i);
                    } else {
                        seatLabel = new Label("#" + i);
                    }

                    for (int j = 1; j <= SEATING_CAPACITY; j++) {
                        try {
                            if (passengersArray[station][localDateInt][i - 1][3] != null) {
                                status = new Label("Status => Pending..");
                                status.setTextFill(Paint.valueOf("#00A69C"));
                                seatText.setTextFill(Paint.valueOf("#00A69C"));
                                seatLabel.setTextFill(Paint.valueOf("#00A69C"));

                                destinationLabel = new Label(destination);
                                bookedDateLabel = new Label(String.valueOf(localDate));
                                passengerNameLabel = new Label(passengersArray[station][localDateInt][i - 1][0] + " " + passengersArray[station][localDateInt][i - 1][1]);
                                passengerNicLabel = new Label(passengersArray[station][localDateInt][i - 1][2]);
                            }
                        } catch (IndexOutOfBoundsException | NullPointerException | IllegalArgumentException e) {
                            //
                        }
                    }

                    //queueOne passengers
                    try {
                        for (Passenger p : passengerQueueOne.getQueueArray()) {
                            if (p.getSeat() == i) {
                                status = new Label("Status => Moved to the Train Queue");
                                status.setTextFill(Paint.valueOf("#2e8ce1"));
                                seatText.setTextFill(Paint.valueOf("#2e8ce1"));
                                seatLabel.setTextFill(Paint.valueOf("#2e8ce1"));
                            }
                        }
                    } catch (Exception e) {
                        // }
                    }
                    //queueTwo passengers
                    try {
                        for (Passenger q : passengerQueueTwo.getQueueArray()) {
                            if (q.getSeat() == i) {
                                status = new Label("Status => Moved to the Train Queue");
                                status.setTextFill(Paint.valueOf("#2e8ce1"));
                                seatText.setTextFill(Paint.valueOf("#2e8ce1"));
                                seatLabel.setTextFill(Paint.valueOf("#2e8ce1"));
                            }
                        }
                    } catch (Exception e) {
                        //
                    }
                    //boarded passengers
                    try {
                        for (Passenger p : boardedPassengers) {
                            if (p.getSeat() == i) {
                                status = new Label("Status => Boarded to the Train");
                                status.setTextFill(Paint.valueOf("#2e8ce1"));
                                seatText.setTextFill(Paint.valueOf("#2e8ce1"));
                                seatLabel.setTextFill(Paint.valueOf("#2e8ce1"));
                            }
                        }
                    } catch (Exception e) {
                        //passengerQueue is empty at the first run
                    }

                    if (actionType.equals("trainSeatsDisplay")) {
                        //boarded passengers
                        try {
                            for (Passenger p : boardedPassengers) {
                                if (p.getSeat() == i) {
                                    status = new Label("Status => Boarded to the Train");
                                    status.setTextFill(Paint.valueOf("#E3236D"));
                                    seatText.setTextFill(Paint.valueOf("#E3236D"));
                                    seatLabel.setTextFill(Paint.valueOf("#E3236D"));
                                }
                            }
                        } catch (Exception e) {
                            //passengerQueue is empty at the first run
                        }
                    }

                    status.setFont(new Font("Arial Bold", 16));
                    seatLabel.setPadding(new Insets(-40, 0, 0, 0));
                    seatLabel.setFont(new Font("Arial Bold", 165));

                    destinationBox.getChildren().addAll(destinationText, destinationLabel);
                    bookedDateBox.getChildren().addAll(bookedDateText, bookedDateLabel);
                    passengerNameBox.getChildren().addAll(passengerNameText, passengerNameLabel);
                    passengerNicBox.getChildren().addAll(passengerNicText, passengerNicLabel);
                    seatBox.getChildren().addAll(status, seatText, seatLabel);

                    closeBtn.setOnAction(event1 -> {
                        passengerDetails.close();
                    });

                    flowPane.getChildren().addAll(headerConfirmationBox, destinationBox, bookedDateBox, passengerNameBox, passengerNicBox, seatBox, empty, closeBtn);
                    Scene passengerDetailScene = new Scene(flowPane, 350, 550);
                    passengerDetails.setScene(passengerDetailScene);
                    passengerDetails.showAndWait();
                }
            }
        });
    }

    private void addArrivedPassengersToWaitingRoom(int station, String[][][][] passengersArray, Passenger[] waitingRoom,
                                                   ToggleButton toggleButtonOne, ToggleButton toggleButtonTwo, ToggleButton seat, int i) {

        LocalDate localDate = LocalDate.now();
        int localDateInt = parseInt(String.valueOf(localDate).substring(8, 10)) - 1;
        List<Integer> tempSeats = new ArrayList<>();

        toggleButtonTwo.setOnAction(event -> {
            System.out.println("\nbutton two clicked");
            String firstName;
            String surname;
            String nic;
            int seatNum;

            System.out.println(index[0]);

            try {
                if (passengersArray[station][localDateInt][i - 1][3] != null) {
                    firstName = passengersArray[station][localDateInt][i - 1][0];
                    surname = passengersArray[station][localDateInt][i - 1][1];
                    nic = passengersArray[station][localDateInt][i - 1][2];
                    seatNum = parseInt(passengersArray[station][localDateInt][i - 1][3]);

                    try {
                        for (Passenger p : waitingRoom) {
                            if (!tempSeats.contains(p.getSeat())) {
                                tempSeats.add(p.getSeat());
                            }
                        }
                    } catch (Exception ignored) {
                    }

                    System.out.println("temp seats - " + tempSeats);

                    if (!tempSeats.contains(seatNum)) {
                        waitingRoom[index[0]] = new Passenger();
                        waitingRoom[index[0]].setFirstName(firstName);
                        waitingRoom[index[0]].setSurname(surname);
                        waitingRoom[index[0]].setNic(nic);
                        waitingRoom[index[0]].setSeat(seatNum);
                        waitingRoom[index[0]].setSecondsInQueue(secondsInQueue());

                        seat.setStyle("-fx-background-color: #00A69C; -fx-background-radius: 8; -fx-border-color: #00A69C; -fx-border-radius: 8; -fx-border-width: 3;");

                        index[0]++;

                    } else {
                        alertWindow("Alert!", "You have already added this passenger!", "1");
                    }

                    System.out.println("\nwaiting room\n" + Arrays.toString(waitingRoom));
                }
            } catch (Exception e) {
                System.out.println("exeception");
            }
        });

        toggleButtonOne.setOnAction(event1 -> {
            seat.setStyle("-fx-background-color: null; -fx-border-color: #00A69C; -fx-border-width: 3; -fx-border-radius: 8");
            try {
                System.out.println("\nbutton one clicked");
                System.out.println("\ncurrent index - " + index[0]);
                tempSeats.remove(index[0] - 1);
                for (int j = (index[0] - 1); j < waitingRoom.length - 1; j++) {
                    waitingRoom[j] = waitingRoom[j + 1];
                }
                System.out.println("waiting room\n" + Arrays.toString(waitingRoom));
            } catch (Exception ignored) {

            }
        });
    }

    private void seatsDisplay(int station, String[][][][] passengersArray, PassengerQueue
            passengerQueueOne, PassengerQueue passengerQueueTwo, Passenger[] waitingRoom, Passenger[] boardedPassengers, VBox
                                      rowOne, VBox rowTwo, VBox rowThree, VBox rowFour, VBox rowFive, VBox rowSix, HBox panelTwo, String actionType) throws Exception {

        class trainSeatsDisplay {
            ToggleButton seat;
            ToggleButton toggleButtonOne;
            ToggleButton toggleButtonTwo;
            HBox panelTwo;

            public void trainSeatsRow(int j, int k, VBox row) {

                for (int i = j; i <= k; i++) {
                    seat = new ToggleButton("Seat " + String.format("%02d", i));
                    seat.setId(Integer.toString(i));

                    if (actionType.equals("seatsWithToggle")) {
                        try {
                            for (Passenger r : boardedPassengers) {
                                if ((r.getSeat() + 1) == i) {
                                    toggleButtonOne.setDisable(true);
                                    toggleButtonTwo.setDisable(true);
                                }
                            }
                        } catch (Exception e) {
                            //
                        }
                        try {
                            for (Passenger p : passengerQueueOne.getQueueArray()) {
                                if ((p.getSeat() + 1) == i) {
                                    toggleButtonOne.setDisable(true);
                                    toggleButtonTwo.setDisable(true);
                                }
                            }
                        } catch (Exception e) {
                            //
                        }
                        try {
                            for (Passenger q : passengerQueueTwo.getQueueArray()) {
                                if ((q.getSeat() + 1) == i) {
                                    toggleButtonOne.setDisable(true);
                                    toggleButtonTwo.setDisable(true);
                                }
                            }
                        } catch (Exception e) {
                            //
                        }
                        selectionPanels(i);
                        addArrivedPassengersToWaitingRoom(station, passengersArray, waitingRoom, toggleButtonOne, toggleButtonTwo, seat, i);
                        bookingDetailsAndSeatColor(station, passengersArray, passengerQueueOne, passengerQueueTwo, boardedPassengers, i, seat, toggleButtonOne, toggleButtonTwo, "", row);
                        row.getChildren().addAll(seat, panelTwo);
                    }

                    if (actionType.equals("displayAll")) {
                        row.getChildren().addAll(seat);
                        seat.setStyle("-fx-background-color: null; -fx-border-color: #E3236D; -fx-border-width: 3; -fx-border-radius: 8");
                        bookingDetailsAndSeatColor(station, passengersArray, passengerQueueOne, passengerQueueTwo, boardedPassengers, i, seat, toggleButtonOne, toggleButtonTwo, "displaySlotName", row);
                    }

                    if (actionType.equals("trainSeatsDisplay")) {
                        row.getChildren().addAll(seat);
                        bookingDetailsAndSeatColor(station, passengersArray, passengerQueueOne, passengerQueueTwo, boardedPassengers, i, seat, toggleButtonOne, toggleButtonTwo, "trainSeatsDisplay", row);
                        seat.setStyle("-fx-background-color: #00A69C; -fx-background-radius: 8; -fx-border-color: #00A69C; -fx-border-radius: 8; -fx-border-width: 2;");
                        //boarded passengers
                        try {
                            for (Passenger p : boardedPassengers) {
                                if (p.getSeat() == i) {
                                    seat.setStyle("-fx-background-color: #E3236D; -fx-background-radius: 8; -fx-border-color: #E3236D; -fx-border-radius: 8; -fx-border-width: 2;");
                                }
                            }
                        } catch (Exception e) {
                            //passengerQueue is empty at the first run
                        }

                    }
                    row.setSpacing(5);
                    seat.setCursor(Cursor.HAND);
                }
            }

            public void selectionPanels(int i) {
                panelTwo = new HBox();
                panelTwo.setId(Integer.toString(i));
                toggleButtonOne = new ToggleButton();
                toggleButtonTwo = new ToggleButton();
                toggleButtonOne.setId(Integer.toString(i));
                toggleButtonTwo.setId(Integer.toString(i));
                new ButtonFX().selectionPanel(panelTwo, toggleButtonOne, toggleButtonTwo);
            }
        }

        if (actionType.equals("seatsWithToggle") | actionType.equals("displayAll")) {
            //rowOne
            new trainSeatsDisplay().trainSeatsRow(1, 7, rowOne);
            //rowTwo
            new trainSeatsDisplay().trainSeatsRow(8, 14, rowTwo);
            //rowThree
            new trainSeatsDisplay().trainSeatsRow(15, 21, rowThree);
            //rowFour
            new trainSeatsDisplay().trainSeatsRow(22, 28, rowFour);
            rowFour.setPadding(new Insets(0, 0, 0, 75));
            //rowFive
            new trainSeatsDisplay().trainSeatsRow(29, 35, rowFive);
            //rowSix
            new trainSeatsDisplay().trainSeatsRow(36, SEATING_CAPACITY, rowSix);

            Button colorOneButton = new Button();
            colorOneButton.setStyle("-fx-background-color: null; -fx-border-color: #E3236D; -fx-border-width: 3; -fx-border-radius: 8");
            colorOneButton.setPrefSize(34, 5);

            Label colorOneLabel = new Label("Available Seats");
            colorOneLabel.setPadding(new Insets(7, 0, 0, 3));

            Button colorTwoButton = new Button();
            colorTwoButton.setStyle("-fx-background-color: null; -fx-border-color: #00A69C; -fx-border-width: 3; -fx-border-radius: 8");
            colorTwoButton.setPrefSize(34, 5);

            Label colorTwoLabel = new Label("Booked Seats");
            colorTwoLabel.setPadding(new Insets(7, 0, 0, 3));

            Button colorThreeButton = new Button();
            colorThreeButton.setStyle("-fx-background-color: null; -fx-border-color: #2e8ce1; -fx-border-width: 3; -fx-border-radius: 8");
            colorThreeButton.setPrefSize(34, 5);

            Label colorThreeLabel = new Label("Moved Passengers");
            colorThreeLabel.setPadding(new Insets(7, 0, 0, 3));

            panelTwo.setStyle("-fx-background-color: rgba(0,0,0,0.04); -fx-background-radius: 15;");
            panelTwo.setPadding(new Insets(15));
            panelTwo.setSpacing(10);
            panelTwo.getChildren().addAll(colorOneButton, colorOneLabel, colorTwoButton, colorTwoLabel, colorThreeButton, colorThreeLabel);
        }

        if (actionType.equals("trainSeatsDisplay")) {
            //rowOne
            new trainSeatsDisplay().trainSeatsRow(1, 11, rowOne);
            //rowTwo
            new trainSeatsDisplay().trainSeatsRow(12, 21, rowTwo);
            //rowThree
            new trainSeatsDisplay().trainSeatsRow(22, 31, rowThree);
            rowThree.setPadding(new Insets(0, 0, 0, 75));
            //rowFour
            new trainSeatsDisplay().trainSeatsRow(32, SEATING_CAPACITY, rowFour);

            Button colorOneButton = new Button();
            colorOneButton.setStyle("-fx-background-color: rgba(227,35,109,0.8); -fx-background-radius: 5");
            colorOneButton.setPrefSize(34, 5);

            Label colorOneLabel = new Label("Boarded Passengers");
            colorOneLabel.setPadding(new Insets(7, 0, 0, 3));

            Button colorTwoButton = new Button();
            colorTwoButton.setStyle("-fx-background-color: rgba(0,166,156,0.8); -fx-background-radius: 5");
            colorTwoButton.setPrefSize(34, 5);

            Label colorTwoLabel = new Label("Available Seats");
            colorTwoLabel.setPadding(new Insets(7, 0, 0, 3));

            panelTwo.setStyle("-fx-background-color: rgba(0,0,0,0.04); -fx-background-radius: 15;");
            panelTwo.setPadding(new Insets(15));
            panelTwo.setSpacing(10);
            panelTwo.getChildren().addAll(colorOneButton, colorOneLabel, colorTwoButton, colorTwoLabel);
        }

    }

    private void addArrivedPassengersToWaitingRoomGUI(int station, String[][][][] passengersArray, Passenger[]
            waitingRoom, PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo, Passenger[] boardedPassengers) throws Exception {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********************************");
        System.out.println("\033[1;93m" + "ADD PASSENGERS TO THE WAITING ROOM" + "\033[0m");
        System.out.println("**********************************");

        Stage addPassengersWaitingRoom = new Stage();
        addPassengersWaitingRoom.initModality(Modality.WINDOW_MODAL);
        if (station == 0) {
            addPassengersWaitingRoom.setTitle("Destination - Colombo to Hatton");
        } else if (station == 1) {
            addPassengersWaitingRoom.setTitle("Destination - Colombo to Ella");
        } else if (station == 2) {
            addPassengersWaitingRoom.setTitle("Destination - Colombo to Badulla");
        } else if (station == 3) {
            addPassengersWaitingRoom.setTitle("Destination - Badulla to Hatton");
        } else if (station == 4) {
            addPassengersWaitingRoom.setTitle("Destination - Badulla to Ella");
        } else if (station == 5) {
            addPassengersWaitingRoom.setTitle("Destination - Badulla to Colombo");
        }
        addPassengersWaitingRoom.initModality(Modality.WINDOW_MODAL);
        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        addPassengersWaitingRoom.getIcons().add(windowIcon);
        addPassengersWaitingRoom.setOnCloseRequest(event -> {
            event.consume();
            quitAlertWindow(addPassengersWaitingRoom);
        });

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 635, 890);

        Label header = new Label("Add passengers to the Waiting Room");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 200, 30, 90));

        VBox rowOne = new VBox();
        VBox rowTwo = new VBox();
        VBox rowThree = new VBox();
        VBox rowFour = new VBox();
        VBox rowFive = new VBox();
        VBox rowSix = new VBox();

        HBox waitingRoomLowerPanel = new HBox();
        seatsDisplay(station, passengersArray, passengerQueueOne, passengerQueueTwo, waitingRoom, boardedPassengers, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, waitingRoomLowerPanel, "seatsWithToggle");

        Button closeBtn = new Button("Close");
        new ButtonFX().closeBtn(closeBtn);
        closeBtn.setOnAction(event -> addPassengersWaitingRoom.close());

        VBox lowerPanel = new VBox();
        lowerPanel.setAlignment(Pos.CENTER);
        lowerPanel.setSpacing(10);
        lowerPanel.setPadding(new Insets(20, 0, 0, 30));
        lowerPanel.getChildren().addAll(waitingRoomLowerPanel, closeBtn);

        flowPane.getChildren().addAll(header, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, lowerPanel);

        addPassengersWaitingRoom.setScene(scene);
        addPassengersWaitingRoom.showAndWait();

    }

    public Pane trainQueueDisplay(PassengerQueue passengerQueue) {
        Pane trainQueue = new Pane();

        int queueArrayLength = 0;
        for (Passenger p : passengerQueue.getQueueArray()) {
            if (p != null) {
                queueArrayLength++;
            }
        }

        HBox queueHeaders = new HBox();
        Label orderNumLabel = new Label("##");
        orderNumLabel.setPadding(new Insets(0, 0, 30, 0));
        orderNumLabel.setFont(new Font("Arial Bold", 16));
        orderNumLabel.setStyle("-fx-underline: true;");

        Label nameLabel = new Label("Passenger's Name");
        nameLabel.setPadding(new Insets(0, 0, 0, 40));
        nameLabel.setFont(new Font("Arial Bold", 16));
        nameLabel.setStyle("-fx-underline: true;");

        Label seatNumLabel = new Label("Seat");
        seatNumLabel.setPadding(new Insets(0, 0, 0, 125));
        seatNumLabel.setFont(new Font("Arial Bold", 16));
        seatNumLabel.setStyle("-fx-underline: true;");

        queueHeaders.getChildren().addAll(orderNumLabel, nameLabel, seatNumLabel);

        Label queueOrderLabel;
        Label passengerSeatLabel;
        Label passengerNameLabel;

        VBox queueOrder = new VBox();
        queueOrder.setPadding(new Insets(40, 0, 0, 0));
        VBox nameBox = new VBox();
        nameBox.setPadding(new Insets(40, 0, 0, 60));
        VBox seatBox = new VBox();
        seatBox.setPadding(new Insets(40, 0, 0, 330));

        if (!passengerQueue.isEmpty()) {
            try {
                trainQueue.getChildren().addAll(queueHeaders);
                for (int i = 1; i <= queueArrayLength; i++) {
                    queueOrderLabel = new Label(String.format("%02d", i));
                    queueOrderLabel.setFont(new Font("Arial Bold", 16));
                    queueOrder.getChildren().addAll(queueOrderLabel);
                }
                for (Passenger p : passengerQueue.getQueueArray()) {
                    if (p != null) {
                        if (p.getSeat() < 10) {
                            passengerSeatLabel = new Label("0" + p.getSeat());
                        } else {
                            passengerSeatLabel = new Label(String.valueOf(p.getSeat()));
                        }
                        passengerNameLabel = new Label(p.getFirstName() + " " + p.getSurname());
                        nameBox.getChildren().addAll(passengerNameLabel);
                        passengerNameLabel.setFont(new Font("Arial Bold", 16));
                        passengerSeatLabel.setFont(new Font("Arial Bold", 16));
                        seatBox.getChildren().addAll(passengerSeatLabel);
                    }
                }
                trainQueue.getChildren().addAll(queueOrder, nameBox, seatBox);
            } catch (Exception e) {
                //
            }
        } else {
            alertWindow("Alert!", "Train Queue is Empty!", "1");
        }
        return trainQueue;
    }

    private void addPassengersToQueue(Passenger[] waitingRoom, PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo) {
        int queueOneLen = 0;
        int queueTwoLen = 0;

        class queueAction {
            private void addQueue(PassengerQueue passengerQueue, int queueLen) {
                Random random = new Random();
                int randomPassengers = random.nextInt(6) + 1;
                totalRandomTurns = totalRandomTurns + randomPassengers;

                //adding passengers to a unsorted array
                try {
                    for (Passenger p : waitingRoom) {
                        if (p != null && i < totalRandomTurns) {
                            /*
                             * add randomly generated number of passengers to the train queue
                             * according to the length of the queues
                             * */
                            passengerQueue.add(p);
                            for (int j = 0; j < 42; j++) {
                                if (waitingRoom[j] == p) {
                                    waitingRoom[j] = null;
                                }
                            }
                            i++;
                        }
                    }

                    for (Passenger p : passengerQueue.getQueueArray()) {
                        if (p != null) {
                            queueLen++;
                        }
                    }

                    System.out.println("\nunsorted\n" + Arrays.toString(passengerQueue.getQueueArray()));
                    for (Passenger p : passengerQueue.getQueueArray()) {
                        if (p != null) {
                            System.out.println(p.getSeat());
                        }
                    }

                    //sorting the queue array by seat number
                    Passenger temp;
                    for (int i = 0; i < queueLen; i++) {
                        for (int j = i + 1; j < queueLen; j++) {
                            if (passengerQueue.getQueueArray()[i].getSeat() > passengerQueue.getQueueArray()[j].getSeat()) {
                                temp = passengerQueue.getQueueArray()[i];
                                passengerQueue.getQueueArray()[i] = passengerQueue.getQueueArray()[j];
                                passengerQueue.getQueueArray()[j] = temp;
                            }
                        }
                    }
                } catch (Exception e) {
                    //
                }

                System.out.println("\nsorted\n" + Arrays.toString(passengerQueue.getQueueArray()));
                for (Passenger p : passengerQueue.getQueueArray()) {
                    if (p != null) {
                        System.out.println(p.getSeat());
                    }
                }
            }
        }

        for (Passenger p : passengerQueueOne.getQueueArray()) {
            if (p != null) {
                queueOneLen++;
            }
        }

        for (Passenger p : passengerQueueTwo.getQueueArray()) {
            if (p != null) {
                queueTwoLen++;
            }
        }

        System.out.println("queueOneLen - " + queueOneLen);
        System.out.println("queueTwoLen - " + queueTwoLen);
        System.out.println("\nwaitingRoom - \n" + Arrays.toString(waitingRoom));

        //add passengers to the train queue
        if (queueOneLen > queueTwoLen) {
            new queueAction().addQueue(passengerQueueTwo, queueTwoLen);
        } else if (queueOneLen < queueTwoLen) {
            new queueAction().addQueue(passengerQueueOne, queueOneLen);
        } else {
            //start moving passengers to the train queue, from queue01
            new queueAction().addQueue(passengerQueueOne, queueOneLen);
        }

        System.out.println("\nwaitingRoom - \n" + Arrays.toString(waitingRoom));
        System.out.println("\nqueue 01 - \n" + Arrays.toString(passengerQueueOne.getQueueArray()));
        System.out.println("\nqueue 02 - \n" + Arrays.toString(passengerQueueTwo.getQueueArray()));

        Stage viewTrainQueueStage = new Stage();
        viewTrainQueueStage.setTitle("Train Queue");
        Image windowIcon = new Image(getClass().getResourceAsStream("mainIcon.png"));
        viewTrainQueueStage.getIcons().add(windowIcon);

        FlowPane flowPaneQueue = new FlowPane();
        flowPaneQueue.setHgap(10);
        flowPaneQueue.setVgap(10);
        flowPaneQueue.setPadding(new Insets(30));

        Label headerQueue = new Label("Train Queue");
        headerQueue.setFont(new Font("Arial Bold", 22));
        headerQueue.setTextFill(Paint.valueOf("#414141"));
        headerQueue.setPadding(new Insets(0, 200, 20, 120));

        Scene sceneQueue = new Scene(flowPaneQueue, 430, 175);

        Label emptySpace = new Label();
        emptySpace.setPadding(new Insets(0, 0, 0, 40));

        Button queueOne = new Button("Queue 01");
        new ButtonFX().addBtn(queueOne);
        queueOne.setPrefWidth(100);

        Button queueTwo = new Button("Queue 02");
        new ButtonFX().addBtn(queueTwo);
        queueTwo.setPrefWidth(100);

        Button closeBtnQueue = new Button("Close");
        new ButtonFX().closeBtn(closeBtnQueue);
        closeBtnQueue.setOnAction(event1 -> viewTrainQueueStage.close());

        HBox queueHeaders = new HBox();
        queueHeaders.setPadding(new Insets(15, 0, 0, 50));

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10));
        buttons.setAlignment(Pos.CENTER);
        buttons.setStyle("-fx-background-color: rgba(0,0,0,0.04); -fx-background-radius: 30;");
        buttons.getChildren().addAll(queueOne, queueTwo);

        Pane trainQueueSection = new Pane();
        trainQueueSection.setPrefWidth(375);

        flowPaneQueue.getChildren().addAll(headerQueue, buttons, emptySpace, closeBtnQueue, trainQueueSection);

        queueOne.setOnMouseClicked(event -> {
            try {
                trainQueueSection.getChildren().addAll(trainQueueDisplay(passengerQueueOne));
                if (!passengerQueueOne.isEmpty()) {
                    FlowPane flowPaneQueueTwo = new FlowPane();
                    flowPaneQueueTwo.setHgap(10);
                    flowPaneQueueTwo.setVgap(10);
                    flowPaneQueueTwo.setPadding(new Insets(30));
                    Scene sceneQueueTwo = new Scene(flowPaneQueueTwo, 425, 700);
                    flowPaneQueueTwo.getChildren().addAll(headerQueue, buttons, emptySpace, closeBtnQueue, trainQueueSection);
                    viewTrainQueueStage.setScene(sceneQueueTwo);
                    viewTrainQueueStage.showAndWait();
                }
            } catch (Exception e) {
                //
            }
        });

        queueTwo.setOnMouseClicked(event -> {
            try {
                trainQueueSection.getChildren().addAll(trainQueueDisplay(passengerQueueTwo));
                if (!passengerQueueTwo.isEmpty()) {
                    FlowPane flowPaneQueueTwo = new FlowPane();
                    flowPaneQueueTwo.setHgap(10);
                    flowPaneQueueTwo.setVgap(10);
                    flowPaneQueueTwo.setPadding(new Insets(30));
                    Scene sceneQueueTwo = new Scene(flowPaneQueueTwo, 425, 700);
                    flowPaneQueueTwo.getChildren().addAll(headerQueue, buttons, emptySpace, closeBtnQueue, trainQueueSection);
                    viewTrainQueueStage.setScene(sceneQueueTwo);
                    viewTrainQueueStage.showAndWait();
                }
            } catch (Exception e) {
                //
            }
        });

        viewTrainQueueStage.setScene(sceneQueue);
        viewTrainQueueStage.showAndWait();
    }

    private void deletePassengerFromQueue(PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo) throws Exception {
        System.out.println("--------------------------------------------------");

        System.out.println("\n******************");
        System.out.println("\033[1;93m" + "DELETE A PASSENGER" + "\033[0m");
        System.out.println("******************\n");

        System.out.println("before delete queue 01\n" + Arrays.toString(passengerQueueOne.getQueueArray()));
        System.out.println("\nbefore delete queue 02\n" + Arrays.toString(passengerQueueTwo.getQueueArray()));
        System.out.println();

        List<String> checkRemoveNameListQueueOne = new ArrayList<>();
        List<String> checkRemoveNameListQueueTwo = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String checkRemoveName;

        for (Passenger p : passengerQueueOne.getQueueArray()) {
            if (p != null) {
                checkRemoveNameListQueueOne.add(p.getFirstName());
            }
        }

        for (Passenger p : passengerQueueTwo.getQueueArray()) {
            if (p != null) {
                checkRemoveNameListQueueTwo.add(p.getFirstName());
            }
        }

        class deleteFromQueue {
            private void delete(PassengerQueue passengerQueue) {
                Scanner sc = new Scanner(System.in);
                String nic = null;
                int removedSeatNumber;

                List<String> checkRemoveNicList = new ArrayList<>();

                for (Passenger p : passengerQueue.getQueueArray()) {
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
                            System.out.println("\033[4;37m" + "\nYou have booked following seat/ seats" + "\033[0m");
                            for (Passenger p : passengerQueue.getQueueArray()) {
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
                        for (Passenger p : passengerQueue.getQueueArray()) {
                            if (p != null) {
                                for (int i = 0; i < 21; i++) {
                                    if (removedSeatNumber == p.getSeat()) {
                                        System.out.println("seat - " + p.getSeat());
                                        System.out.println("passenger" + p);
                                        System.out.println("index - " + findIndex(passengerQueue.getQueueArray(), p));
                                        index = findIndex(passengerQueue.getQueueArray(), p);
                                        passengerQueue.remove(index);

                                        System.out.println("\nSeat #" + removedSeatNumber + " has successfully deleted!");
                                        break;
                                    }
                                }
                            }
                        }

                        for (int i = index; i <= passengerQueue.getQueueArray().length; i++) {
                            passengerQueue.getQueueArray()[i] = passengerQueue.getQueueArray()[i + 1];
                        }

                    }
                } catch (Exception ignored) {
                }
            }

            // Linear-search function to find the index of an element
            public int findIndex(Passenger[] arr, Passenger t) {

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

        System.out.print("Prompt your First Name : ");
        checkRemoveName = sc.next();

        if (checkRemoveNameListQueueOne.contains(checkRemoveName)) {
            System.out.println("queueOne");
            new deleteFromQueue().delete(passengerQueueOne);
        } else if (checkRemoveNameListQueueTwo.contains(checkRemoveName)) {
            System.out.println("queueTwo");
            new deleteFromQueue().delete(passengerQueueTwo);
        } else {
            System.out.println("\nNo bookings were made by " + checkRemoveName);
        }

        System.out.println("\nafter delete queue 01\n" + Arrays.toString(passengerQueueOne.getQueueArray()));
        System.out.println("\nafter delete queue 02\n" + Arrays.toString(passengerQueueTwo.getQueueArray()));

        System.out.println();
    }

    private void displayAll(int station, String[][][][] passengersArray, Passenger[] waitingRoom, Passenger[]
            boardedPassengers, PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo) throws Exception {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*****************");
        System.out.println("\033[1;93m" + "DISPLAY ALL SLOTS" + "\033[0m");
        System.out.println("*****************\n");

        Stage window = new Stage();
        if (station == 0) {
            window.setTitle("Destination - Colombo to Hatton");
        } else if (station == 1) {
            window.setTitle("Destination - Colombo to Ella");
        } else if (station == 2) {
            window.setTitle("Destination - Colombo to Badulla");
        } else if (station == 3) {
            window.setTitle("Destination - Badulla to Hatton");
        } else if (station == 4) {
            window.setTitle("Destination - Badulla to Ella");
        } else if (station == 5) {
            window.setTitle("Destination - Badulla to Colombo");
        }

        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        window.getIcons().add(windowIcon);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(40));

        Scene scene = new Scene(flowPane, 1650, 700);

        Label header1 = new Label("Waiting Room");
        header1.setFont(new Font("Arial Bold", 22));
        header1.setTextFill(Paint.valueOf("#414141"));
        header1.setPadding(new Insets(0, 0, 25, 0));

        Label header2 = new Label("Train Queue");
        header2.setFont(new Font("Arial Bold", 22));
        header2.setTextFill(Paint.valueOf("#414141"));
        header2.setPadding(new Insets(0, 0, 25, 520));

        Label header3 = new Label("Train");
        header3.setFont(new Font("Arial Bold", 22));
        header3.setTextFill(Paint.valueOf("#414141"));
        header3.setPadding(new Insets(0, 999, 25, 100));

        Button queueOne = new Button("Queue 01");
        new ButtonFX().addBtn(queueOne);
        queueOne.setPrefWidth(100);

        Button queueTwo = new Button("Queue 02");
        new ButtonFX().addBtn(queueTwo);
        queueTwo.setPrefWidth(100);

        HBox headers = new HBox();
        headers.setSpacing(15);
        headers.getChildren().addAll(header1, header2, queueOne, queueTwo, header3);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setPadding(new Insets(0, 40, 0, 40));

        Separator separatorTwo = new Separator();
        separatorTwo.setOrientation(Orientation.VERTICAL);
        separatorTwo.setPadding(new Insets(0, 40, 0, 40));

        VBox rowOne = new VBox();
        VBox rowTwo = new VBox();
        VBox rowThree = new VBox();
        VBox rowFour = new VBox();
        VBox rowFive = new VBox();
        VBox rowSix = new VBox();

        VBox trainSeatsRowOne = new VBox();
        VBox trainSeatsRowTwo = new VBox();
        VBox trainSeatsRowThree = new VBox();
        VBox trainSeatsRowFour = new VBox();
        VBox trainSeatsRowFive = new VBox();
        VBox trainSeatsRowSix = new VBox();

        HBox waitingRoomLowerPanel = new HBox();
        HBox waitingRoomLowerPanelTwo = new HBox();

        Label emptySpace = new Label();
        emptySpace.setPadding(new Insets(0, 0, 0, 325));

        Label emptySpaceTwo = new Label();
        emptySpaceTwo.setPadding(new Insets(0, 0, 0, 240));

        Button emptySpaceThree = new Button();
        emptySpaceThree.setStyle("-fx-background-color: null");
        emptySpaceThree.setPrefSize(1500, 30);

        seatsDisplay(station, passengersArray, passengerQueueOne, passengerQueueTwo, waitingRoom, boardedPassengers, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, waitingRoomLowerPanel, "displayAll");
        seatsDisplay(station, passengersArray, passengerQueueOne, passengerQueueTwo, waitingRoom, boardedPassengers, trainSeatsRowOne, trainSeatsRowTwo, trainSeatsRowThree, trainSeatsRowFour, trainSeatsRowFive, trainSeatsRowSix, waitingRoomLowerPanelTwo, "trainSeatsDisplay");

        Button closeBtn = new Button("Close");
        new ButtonFX().closeBtn(closeBtn);
        closeBtn.setOnAction(event -> window.close());

        Pane trainQueueSection = new Pane();
        trainQueueSection.setPrefWidth(375);

        queueOne.setOnMouseClicked(event -> {
            trainQueueSection.getChildren().addAll(trainQueueDisplay(passengerQueueOne));
        });

        queueTwo.setOnMouseClicked(event -> {
            trainQueueSection.getChildren().addAll(trainQueueDisplay(passengerQueueTwo));
        });

        flowPane.getChildren().addAll(headers, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, separator, trainQueueSection, separatorTwo, trainSeatsRowOne, trainSeatsRowTwo, trainSeatsRowThree, trainSeatsRowFour, trainSeatsRowFive, trainSeatsRowSix);
        flowPane.getChildren().addAll(emptySpaceThree, waitingRoomLowerPanel, emptySpace, closeBtn, emptySpaceTwo, waitingRoomLowerPanelTwo);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("--------------------------------------------------");
    }

    private void generateReport(int station, PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo, Passenger[] boardedPassengers) throws Exception {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*******************");
        System.out.println("\033[1;93m" + "GENERATE THE REPORT" + "\033[0m");
        System.out.println("*******************");

        int maxStayInQueueOne = 0;
        int maxStayInQueueTwo = 0;
        int queueOneLen = 0;
        int queueTwoLen = 0;

        int minTimeQueueOne = 0;
        int minTimeQueueTwo = 0;
        try {
            minTimeQueueOne = passengerQueueOne.getQueueArray()[0].getSecondsInQueue();
            minTimeQueueTwo = passengerQueueTwo.getQueueArray()[0].getSecondsInQueue();
        } catch (Exception ignored) {
        }

        for (Passenger p : passengerQueueOne.getQueueArray()) {
            if (p != null) {
                maxStayInQueueOne += p.getSecondsInQueue();
                queueOneLen++;
            }
        }
        for (Passenger p : passengerQueueTwo.getQueueArray()) {
            if (p != null) {
                maxStayInQueueTwo += p.getSecondsInQueue();
                queueTwoLen++;
            }
        }

        passengerQueueOne.setMaxStayInQueue(maxStayInQueueOne);
        passengerQueueTwo.setMaxStayInQueue(maxStayInQueueTwo);

        passengerQueueOne.setMaxLength(queueOneLen);
        passengerQueueTwo.setMaxLength(queueTwoLen);

        System.out.println("\nminTimeQueueOne - " + minTimeQueueOne);
        System.out.println("maxStayInQueueOne - " + maxStayInQueueOne);
        System.out.println("queueOneLen - " + queueOneLen);
        System.out.println();
        System.out.println("minTimeQueueTwo - " + minTimeQueueTwo);
        System.out.println("maxStayInQueueTwo - " + maxStayInQueueTwo);
        System.out.println("queueTwoLen - " + queueTwoLen);

        if (station == 0) {
            generateReportMain(0, passengerQueueOne, passengerQueueTwo, boardedPassengers, minTimeQueueOne, minTimeQueueTwo);
        } else {
            generateReportMain(1, passengerQueueOne, passengerQueueTwo, boardedPassengers, minTimeQueueOne, minTimeQueueTwo);
        }

        System.out.println("\n--------------------------------------------------");
    }

    private void generateReportMain(int station, PassengerQueue passengerQueueOne, PassengerQueue
            passengerQueueTwo, Passenger[] boardedPassengers, int minTimeQueueOne, int minTimeQueueTwo) throws Exception {
        LocalDate localDate = LocalDate.now();

        System.out.println("sumOfTime - " + sumOfTime);

        class queueReport {
            public void queue(PassengerQueue passengerQueue, int minTime) throws InterruptedException {
                if (passengerQueue == passengerQueueOne) {
                    System.out.println("\033[4;37m" + "\nQueue 01\n" + "\033[0m");
                } else {
                    System.out.println("\033[4;37m" + "\nQueue 02\n" + "\033[0m");
                }
                wrapper:
                while (true) {
                    for (Passenger o : passengerQueue.getQueueArray()) {
                        if (o != null) {
                            sumOfTime += (o.getSecondsInQueue());
                            passengerQueue.setMaxStayInQueue((int) sumOfTime);
                            if (minTime > sumOfTime) {
                                minTime = (int) sumOfTime;
                            }
                            boardedPassengers[o.getSeat() - 1] = o;
                            boardedPassengers[o.getSeat() - 1].setSecondsInQueue((int) sumOfTime);
                            TimeUnit.SECONDS.sleep(1);

                            System.out.println(o.getFirstName() + " " + o.getSurname() + " boarded to the train => seat #" + o.getSeat());
                            for (int i = 0; i <= 19; i++) {
                                passengerQueue.getQueueArray()[i] = passengerQueue.getQueueArray()[i + 1];
                            }
                            passengerQueue.getQueueArray()[20] = null;
                            //passengerQueueOne.getQueueArray()[i]--;
                            break;
                        } else {
                            break wrapper;
                        }
                    }
                }
            }
        }
        new queueReport().queue(passengerQueueOne, minTimeQueueOne);
        new queueReport().queue(passengerQueueTwo, minTimeQueueTwo);

        int boardedArrayLen = 0;
        for (Passenger p : boardedPassengers) {
            if (p != null) {
                boardedArrayLen++;
            }
        }

        System.out.println("\nsumOfTime - " + sumOfTime);
        System.out.println("boardedArrayLen - " + boardedArrayLen);

        System.out.println("\nboardedPassengers\n" + Arrays.toString(boardedPassengers));
        System.out.println("\nqueue 01\n" + Arrays.toString(passengerQueueOne.getQueueArray()));
        System.out.println("\nqueue 02\n" + Arrays.toString(passengerQueueTwo.getQueueArray()));

        BufferedWriter bufferedWriter;
        File file;
        String destination;

        if (station == 0) {
            destination = "Colombo - Hatton";
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\ColomboToHattonQueueReport.txt");
        } else if (station == 1) {
            destination = "Colombo - Ella";
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\ColomboToEllaQueueReport.txt");
        } else if (station == 2) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\ColomboToBadullaQueueReport.txt");
            destination = "Destination - Colombo - Badulla";
        } else if (station == 3) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\BadullaToHattonQueueReport.txt");
            destination = "Destination - Badulla - Hatton";
        } else if (station == 4) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\BadullaToEllaQueueReport.txt");
            destination = "Destination - Badulla - Ella";
        } else {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\BadullaToColomboQueueReport.txt");
            destination = "Destination - Badulla - Colombo";
        }

        if (boardedArrayLen > 0) {
            while (true) {
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                    for (Passenger p : boardedPassengers) {
                        if (p != null) {
                            bufferedWriter.write("");
                            bufferedWriter.write("Destination - " + destination + " | Booked date - " + localDate +
                                    " | Passenger name - " + p.getFirstName() + " " + p.getSurname() +
                                    " | NIC - " + p.getNic() +
                                    " | Seat #" + p.getSeat());
                            bufferedWriter.newLine();
                        }
                    }

                    DecimalFormat f = new DecimalFormat("##.00");
                    double averageWaitingTimeQueueOne = sumOfTime / passengerQueueOne.getMaxLength();
                    double averageWaitingTimeQueueTwo = sumOfTime / passengerQueueTwo.getMaxLength();

                    bufferedWriter.write("\nQueue 01");
                    bufferedWriter.write("\nMaximum Queue Length      : " + passengerQueueOne.getMaxLength());
                    bufferedWriter.write("\nMaximum Waiting Time      : " + passengerQueueOne.getMaxStayInQueue());
                    bufferedWriter.write("\nMinimum Waiting Time      : " + minTimeQueueOne);
                    bufferedWriter.write("\nAverage Waiting Time      : " + f.format(averageWaitingTimeQueueOne));
                    bufferedWriter.newLine();
                    bufferedWriter.write("\nQueue 02");
                    bufferedWriter.write("\nMaximum Queue Length      : " + passengerQueueTwo.getMaxLength());
                    bufferedWriter.write("\nMaximum Waiting Time      : " + passengerQueueTwo.getMaxStayInQueue());
                    bufferedWriter.write("\nMinimum Waiting Time      : " + minTimeQueueTwo);
                    bufferedWriter.write("\nAverage Waiting Time      : " + f.format(averageWaitingTimeQueueTwo));
                    bufferedWriter.newLine();

                    bufferedWriter.flush();
                    bufferedWriter.close();

                    reportGUI(passengerQueueOne, passengerQueueTwo, boardedPassengers, minTimeQueueOne, minTimeQueueTwo, averageWaitingTimeQueueOne, averageWaitingTimeQueueTwo);
                    break;

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("File not found!\n");
                }
            }
        }
    }

    private void reportGUI(PassengerQueue passengerQueueOne, PassengerQueue
            passengerQueueTwo, Passenger[] boardedPassengers, int minTimeOne, int minTimeTwo, double avgQueueOne, double avgQueueTwo) {

        int queueArrayLength = 0;
        for (Passenger p : boardedPassengers) {
            if (p != null) {
                queueArrayLength++;
            }
        }
        System.out.println("queueArrayLength - " + queueArrayLength);

        Stage reportDisplay = new Stage();
        reportDisplay.setTitle("Boarded Passengers");
        reportDisplay.initModality(Modality.WINDOW_MODAL);
        Image windowIcon = new Image(getClass().getResourceAsStream("mainIcon.png"));
        reportDisplay.getIcons().add(windowIcon);

        FlowPane trainQueue = new FlowPane();
        trainQueue.setPadding(new Insets(30));

        Scene trainQueueScene;
        if (queueArrayLength > 2) {
            trainQueueScene = new Scene(trainQueue, 630, 430 + (10 * queueArrayLength));
        } else {
            trainQueueScene = new Scene(trainQueue, 630, 430);
        }

        HBox header = new HBox();
        header.setPadding(new Insets(0, 0, 30, 185));

        Label headerLabel = new Label("Boarded Passengers");
        headerLabel.setFont(new Font("Arial Bold", 20));
        header.getChildren().add(headerLabel);

        DecimalFormat f = new DecimalFormat("##.00");

        HBox sectionOne = new HBox();
        sectionOne.setPadding(new Insets(0, 0, 30, 0));
        sectionOne.setSpacing(50);

        VBox queueOneStatistics = new VBox();
        queueOneStatistics.setPrefWidth(260);
        queueOneStatistics.setStyle("-fx-background-color: rgba(0,0,0,0.04); -fx-background-radius: 15;");
        queueOneStatistics.setPadding(new Insets(15));

        Label headOne = new Label("Queue 01");
        headOne.setPadding(new Insets(0, 0, 20, 0));
        headOne.setTextFill(Paint.valueOf("#00A69C"));
        headOne.setFont(new Font("Arial Bold", 20));
        headOne.setAlignment(Pos.CENTER);

        Label lenOne = new Label("Maximum Queue Length : " + passengerQueueOne.getMaxLength());
        lenOne.setFont(new Font("Arial", 16));

        Label maxWaitingOne = new Label("Maximum Waiting Time   : " + passengerQueueOne.getMaxStayInQueue());
        maxWaitingOne.setFont(new Font("Arial", 16));

        Label minWaitingOne = new Label("Minimum Waiting Time    : " + minTimeOne);
        minWaitingOne.setFont(new Font("Arial", 16));

        Label avgOne = new Label("Average Waiting Time     : " + f.format(avgQueueOne));
        avgOne.setFont(new Font("Arial", 16));

        queueOneStatistics.getChildren().addAll(headOne, lenOne, maxWaitingOne, minWaitingOne, avgOne);

        VBox queueTwoStatistics = new VBox();
        queueTwoStatistics.setPrefWidth(260);
        queueTwoStatistics.setStyle("-fx-background-color: rgba(0,0,0,0.04); -fx-background-radius: 15;");
        queueTwoStatistics.setPadding(new Insets(15));

        Label headTwo = new Label("Queue 02");
        headTwo.setPadding(new Insets(0, 0, 20, 0));
        headTwo.setTextFill(Paint.valueOf("#00A69C"));
        headTwo.setFont(new Font("Arial Bold", 20));
        headTwo.setAlignment(Pos.CENTER);

        Label lenTwo = new Label("Maximum Queue Length : " + passengerQueueTwo.getMaxLength());
        lenTwo.setFont(new Font("Arial", 16));

        Label maxWaitingTwo = new Label("Maximum Waiting Time   : " + passengerQueueTwo.getMaxStayInQueue());
        maxWaitingTwo.setFont(new Font("Arial", 16));

        Label minWaitingTwo = new Label("Minimum Waiting Time    : " + minTimeTwo);
        minWaitingTwo.setFont(new Font("Arial", 16));

        Label avgTwo = new Label("Average Waiting Time     : " + f.format(avgQueueTwo));
        avgTwo.setFont(new Font("Arial", 16));

        queueTwoStatistics.getChildren().addAll(headTwo, lenTwo, maxWaitingTwo, minWaitingTwo, avgTwo);

        sectionOne.getChildren().addAll(queueOneStatistics, queueTwoStatistics);
        trainQueue.getChildren().addAll(header, sectionOne);

        HBox queueHeaders = new HBox();
        Label orderNumLabel = new Label("##");
        orderNumLabel.setPadding(new Insets(0, 0, 0, 0));
        orderNumLabel.setFont(new Font("Arial Bold", 16));
        orderNumLabel.setStyle("-fx-underline: true;");

        Label nameLabel = new Label("Boarded Passenger's Name");
        nameLabel.setPadding(new Insets(0, 0, 0, 40));
        nameLabel.setFont(new Font("Arial Bold", 16));
        nameLabel.setStyle("-fx-underline: true;");

        Label seatNumLabel = new Label("Seat");
        seatNumLabel.setPadding(new Insets(0, 999, 0, 265));
        seatNumLabel.setFont(new Font("Arial Bold", 16));
        seatNumLabel.setStyle("-fx-underline: true;");

        queueHeaders.getChildren().addAll(orderNumLabel, nameLabel, seatNumLabel);

        Label queueOrderLabel;
        Label passengerSeatLabel;
        Label passengerNameLabel;

        VBox queueOrder = new VBox();
        queueOrder.setPadding(new Insets(20, 0, 0, 0));
        VBox nameBox = new VBox();
        nameBox.setPadding(new Insets(20, 0, 0, 40));
        nameBox.setPrefWidth(520);
        VBox seatBox = new VBox();
        seatBox.setPadding(new Insets(20, 0, 0, 0));

        Button closeBtn = new Button("Close");
        new ButtonFX().closeBtn(closeBtn);
        closeBtn.setOnAction(event -> reportDisplay.close());

        HBox buttonPanel = new HBox();
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setPadding(new Insets(25, 0, 0, 250));
        buttonPanel.getChildren().add(closeBtn);

        if (queueArrayLength != 0) {
            try {
                trainQueue.getChildren().addAll(queueHeaders);
                for (int i = 1; i <= queueArrayLength; i++) {
                    queueOrderLabel = new Label(String.format("%02d", i));
                    queueOrderLabel.setFont(new Font("Arial Bold", 16));
                    queueOrder.getChildren().addAll(queueOrderLabel);
                }
                for (Passenger p : boardedPassengers) {
                    if (p != null) {
                        if (p.getSeat() < 10) {
                            passengerSeatLabel = new Label("0" + p.getSeat());
                        } else {
                            passengerSeatLabel = new Label(String.valueOf(p.getSeat()));
                        }
                        passengerNameLabel = new Label(p.getFirstName() + " " + p.getSurname());
                        nameBox.getChildren().addAll(passengerNameLabel);
                        passengerNameLabel.setFont(new Font("Arial Bold", 16));
                        passengerSeatLabel.setFont(new Font("Arial Bold", 16));
                        seatBox.getChildren().addAll(passengerSeatLabel);
                    }
                }
                trainQueue.getChildren().addAll(queueOrder, nameBox, seatBox);
                trainQueue.getChildren().add(buttonPanel);
            } catch (Exception e) {
                //
            }
        } else {
            alertWindow("Alert!", "Train Queue is Empty!", "1");
        }
        reportDisplay.setScene(trainQueueScene);
        reportDisplay.showAndWait();
    }

    private void storeData(int station, PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo) throws InterruptedException {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********");
        System.out.println("\033[1;93m" + "STORE DATA" + "\033[0m");
        System.out.println("**********\n");

        File file;
        String destination;

        LocalDate localDate = LocalDate.now();

        if (!passengerQueueOne.isEmpty() & !passengerQueueTwo.isEmpty()) {
            if (station == 0) {
                destination = "Colombo - Hatton";
                file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\ColomboToHattonQueue.txt");
            } else if (station == 1) {
                destination = "Colombo to Ella";
                file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\ColomboToEllaQueue.txt");
            } else if (station == 2) {
                file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\ColomboToBadullaQueue.txt");
                destination = "Destination - Colombo - Badulla";
            } else if (station == 3) {
                file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\BadullaToHattonQueue.txt");
                destination = "Destination - Badulla - Hatton";
            } else if (station == 4) {
                file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\BadullaToEllaQueue.txt");
                destination = "Destination - Badulla - Ella";
            } else {
                file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\BadullaToColomboQueue.txt");
                destination = "Destination - Badulla - Colombo";
            }
            storeQueueData(file, destination, 1, passengerQueueOne);
            storeQueueData(file, destination, 2, passengerQueueTwo);

            TimeUnit.SECONDS.sleep(1);
            System.out.println("Data on the following Queue has been successfully stored!");
            System.out.println(destination + " on " + "\033[1;31m" + localDate + "\033[0m");
        } else {
            System.out.println("Train Queue process is yet to be begin!");
        }

        System.out.println("\n--------------------------------------------------");
    }

    private void storeQueueData(File file, String destination, int queue, PassengerQueue passengerQueue) {
        LocalDate localDate = LocalDate.now();

        int boardedArrayLen = 0;
        for (Passenger p : passengerQueue.getQueueArray()) {
            if (p != null) {
                boardedArrayLen++;
            }
        }

        BufferedWriter bufferedWriter;

        if (boardedArrayLen > 0) {
            while (true) {
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                    for (Passenger p : passengerQueue.getQueueArray()) {
                        if (p != null) {
                            bufferedWriter.write("Queue " + queue + " | Destination - " + destination + " | Booked date - " + localDate +
                                    " | Passenger name - " + p.getFirstName() + " " + p.getSurname() +
                                    " | NIC - " + p.getNic() +
                                    " | Seat #" + p.getSeat());
                            bufferedWriter.newLine();
                        }
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    break;

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("File not found!\n");
                }
            }
        }
    }

    private void loadData(PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo) throws InterruptedException {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********************");
        System.out.println("\033[1;93m" + "LOAD PROGRAM FROM DATA" + "\033[0m");
        System.out.println("**********************");

        LocalDate localDate = LocalDate.now();

        File file;
        String destination;
        Scanner sc = new Scanner(System.in);

        int station;
        do {
            System.out.print("\nSelect a Queue to Load Data\n01 Colombo - Hatton\n02 Colombo - Ella\n\nPrompt 1 or 2 to proceed : ");
            while (!sc.hasNextInt()) {
                System.out.println("Prompt Integers to proceed!!");
                System.out.print("\nSelect a Queue to Load Data\n01 Colombo - Hatton\n02 Colombo - Ella\n\nPrompt 1 or 2 to proceed : ");
                sc.next();
            }
            station = (sc.nextInt() - 1);
        } while (station != 0 & station != 1);

        if (station == 0) {
            destination = "Colombo - Hatton";
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\ColomboToHattonQueue.txt");
        } else {
            destination = "Colombo to Ella";
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\ColomboToEllaQueue.txt");
        }
        loadQueueData(file, passengerQueueOne, passengerQueueTwo);

        System.out.println("\nloaded queue01\n" + Arrays.toString(passengerQueueOne.getQueueArray()));
        System.out.println("\nloaded queue02\n" + Arrays.toString(passengerQueueTwo.getQueueArray()));

        TimeUnit.SECONDS.sleep(1);
        System.out.println("Data on the following Queue has been successfully loaded!");
        System.out.print(destination + " on " + "\033[1;31m" + localDate + "\033[0m");

        System.out.println("\n--------------------------------------------------");
    }

    private void loadQueueData(File file, PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo) {
        //get current date
        LocalDate localDate = LocalDate.now();

        int indexQueueOne = 0;
        int indexQueueTwo = 0;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            //create BufferedReader object from the File
            String line;

            //read file line by line
            while ((line = bufferedReader.readLine()) != null) {
                //split the line by | and put it to a string array
                String[] parts = line.split(" ");

                try {
                    String queueType = parts[1];
                    String bookedDate = parts[12];
                    String passengerName = parts[17];
                    String passengerSurname = parts[18];
                    String nic = parts[22];
                    String[] seatNumber = parts[25].split("#");

                    //add queue 01 data to to the queue 01 array
                    if (String.valueOf(localDate).equals(bookedDate) & queueType.equalsIgnoreCase("1")) {
                        Passenger passenger = new Passenger();
                        passenger.setFirstName(passengerName);
                        passenger.setSurname(passengerSurname);
                        passenger.setNic(nic);
                        passenger.setSeat(Integer.parseInt(seatNumber[1]));

                        passengerQueueOne.getQueueArray()[indexQueueOne] = passenger;
                        indexQueueOne++;
                    }

                    //add queue 02 data to to the queue 02 array
                    if (String.valueOf(localDate).equals(bookedDate) & queueType.equalsIgnoreCase("2")) {
                        Passenger passenger = new Passenger();
                        passenger.setFirstName(passengerName);
                        passenger.setSurname(passengerSurname);
                        passenger.setNic(nic);
                        passenger.setSeat(Integer.parseInt(seatNumber[1]));

                        passengerQueueTwo.getQueueArray()[indexQueueTwo] = passenger;
                        indexQueueTwo++;
                    }
                } catch (Exception ignore) {
                }
            }
            try {
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

/*
 * remove unnecessary souts
 *   */
