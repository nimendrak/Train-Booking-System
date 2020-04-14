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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        //store booking data in this multi-dimensional array
        String[][][][] passengersArray = new String[2][31][SEATING_CAPACITY][4];

        //creating 42 different objects as passengers for each destination
        Passenger[] badullaToColomboWaitingRoom = new Passenger[42];
        Passenger[] colomboToBadullaWaitingRoom = new Passenger[42];

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
        } catch (Exception e) {
            System.out.println("File is corrupted!");
        }

        int station = 1;
        while (station == 1 || station == 2) {
            System.out.print("\nSelect a Destination to enter to the Waiting Room\n01 Badulla - Colombo\n02 Colombo - Badulla\n\nPrompt 1 or 2 to proceed : ");
            while (!sc.hasNextInt()) {
                System.out.println("Prompt Integers to proceed!!");
                System.out.print("\nSelect a Destination to enter to the Waiting Room\n01 Badulla - Colombo\n02 Colombo - Badulla\n\nPrompt 1 or 2 to proceed : ");
                sc.next();
            }
            station = (sc.nextInt() - 1);
        }

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
                        addPassengersToWaitingRoom(station, passengersArray, badullaToColomboWaitingRoom, passengerQueueOne, passengerQueueTwo, boardedPassengers);
                    } else {
                        addPassengersToWaitingRoom(station, passengersArray, colomboToBadullaWaitingRoom, passengerQueueOne, passengerQueueTwo, boardedPassengers);
                    }
                    break;

                case "A":
                case "a":
                    if (station == 0) {
                        addPassengersToQueue(0, passengersArray, badullaToColomboWaitingRoom, passengerQueueOne, passengerQueueTwo);
                    } else {
                        addPassengersToQueue(1, passengersArray, colomboToBadullaWaitingRoom, passengerQueueOne, passengerQueueTwo);
                    }
                    break;

                case "V":
                case "v":
                    if (station == 0) {
                        displayAll(0, passengersArray, badullaToColomboWaitingRoom, boardedPassengers, passengerQueueOne, passengerQueueTwo);
                    } else {
                        displayAll(1, passengersArray, colomboToBadullaWaitingRoom, boardedPassengers, passengerQueueOne, passengerQueueTwo);
                    }
                    break;

                case "D":
                case "d":
                    deletePassengerFromQueue(passengerQueueOne, passengerQueueTwo);
                    break;

                case "S":
                case "s":
                    //storeData();
                    break;

                case "L":
                case "l":
                    //loadData();
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
                        //storeData(passengersArray, bookedDatesList);
                    } else {
                        System.out.println("\nProgram is now Exiting..");
                    }
                    break;

                default:
                    System.out.println("\nYou have entered a Invalid Input!");
                    System.out.println("---------------------------------");
            }
        } while (!userOption.equals("q"));
    }

    //type one alert box act as a confirmation box for the quit the current stage
    private void alertBoxWindowTypeOne(Stage window) {
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
        new ButtonFX().closeBtn(okBtn);
        Button cancelBtn = new Button("Cancel");
        new ButtonFX().addBtn(cancelBtn);

        Label label = new Label("Do you want to exit?");

        okBtn.setOnAction(event -> {
            alertBoxWindow.close();
            window.close();
        });

        cancelBtn.setOnAction(event -> alertBoxWindow.hide());

        layout.getChildren().add(label);
        layout.getChildren().add(gridPane);

        gridPane.add(okBtn, 0, 0);
        gridPane.add(cancelBtn, 1, 0);

        gridPane.setPadding(new Insets(0, 0, 0, 83));
        gridPane.setHgap(10);

        alertBoxWindow.showAndWait();
    }

    //type two alert box popup a new window and show messages according to the the parameters
    private void alertBoxWindowTypeTwo(String title, String message, String iconType) {
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

        alertBoxWindow.setMinWidth(300);
        alertBoxWindow.setMinHeight(165);

        Label label = new Label();
        label.setText(message);
        label.setFont(new Font("Arial Bold", 16));
        label.setTextFill(Paint.valueOf("#323232"));

        Button closeButton = new Button("Close");
        new ButtonFX().closeBtn(closeButton);
        closeButton.setOnAction(e -> alertBoxWindow.close());

        VBox layout = new VBox(15);
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
        File file;

        if (station == 0) {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\BadullaToColombo.txt");
        } else {
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\ColomboToBadulla.txt");
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

    private void bookingDetailsDisplay(int station, String[][][][] passengersArray, PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo,
                                       Passenger[] boardedPassengers, int i, ToggleButton seat, ToggleButton btnOne, ToggleButton btnTwo,
                                       String actionType, VBox row) {
        LocalDate localDate = LocalDate.now();
        int localDateInt = parseInt(String.valueOf(localDate).substring(8, 10)) - 1;

        List<Integer> bookedSeats = new ArrayList<>();
        int queueOneCounter = 0;
        int queueTwoCounter = 0;
        int boardedCounter = 0;

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
            if (bookedSeats.contains(i)) {
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
                                status = new Label("Status => Moved to the Train Queue");
                                status.setTextFill(Paint.valueOf("#2e8ce1"));
                                seatText.setTextFill(Paint.valueOf("#2e8ce1"));
                                seatLabel.setTextFill(Paint.valueOf("#2e8ce1"));
                            }
                        }
                    } catch (Exception e) {
                        //passengerQueue is empty at the first run
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

    int[] index = new int[1];

    private void addPassengers(int station, String[][][][] passengersArray, Passenger[] waitingRoom,
                               ToggleButton toggleButtonOne, ToggleButton toggleButtonTwo, int i) {

        LocalDate localDate = LocalDate.now();
        int localDateInt = parseInt(String.valueOf(localDate).substring(8, 10)) - 1;

        toggleButtonTwo.setOnAction(event -> {
            String firstName;
            String surname;
            String nic;
            int seat;

            System.out.println(index[0]);

            try {
                if (passengersArray[station][localDateInt][i - 1][3] != null) {
                    firstName = passengersArray[station][localDateInt][i - 1][0];
                    surname = passengersArray[station][localDateInt][i - 1][1];
                    nic = passengersArray[station][localDateInt][i - 1][2];
                    seat = parseInt(passengersArray[station][localDateInt][i - 1][3]);

                    waitingRoom[index[0]] = new Passenger();
                    waitingRoom[index[0]].setFirstName(firstName);
                    waitingRoom[index[0]].setSurname(surname);
                    waitingRoom[index[0]].setNic(nic);
                    waitingRoom[index[0]].setSeat(seat);
                    waitingRoom[index[0]].setSecondsInQueue(secondsInQueue());

                    toggleButtonOne.setOnAction(event1 -> waitingRoom[index[0]] = null);

                    index[0]++;
                }
            } catch (Exception e) {
                //
            }
        });
    }

    private void trainSeatsDisplay(int station, String[][][][] passengersArray, PassengerQueue
            passengerQueueOne, PassengerQueue passengerQueueTwo, Passenger[] waitingRoom, Passenger[] boardedPassengers, VBox
                                           rowOne, VBox
                                           rowTwo, VBox rowThree, VBox rowFour, VBox rowFive, VBox rowSix, HBox panelTwo, String actionType) {

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
                            for (Passenger p : passengerQueueOne.getQueueArray()) {
                                if ((p.getSeat() + 1) == i) {
                                    toggleButtonOne.setDisable(true);
                                    toggleButtonTwo.setDisable(true);
                                }
                            }
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
                        addPassengers(station, passengersArray, waitingRoom, toggleButtonOne, toggleButtonTwo, i);
                        bookingDetailsDisplay(station, passengersArray, passengerQueueOne, passengerQueueTwo, boardedPassengers, i, seat, toggleButtonOne, toggleButtonTwo, "", row);
                        row.getChildren().addAll(seat, panelTwo);
                    }

                    if (actionType.equals("displayAll")) {
                        row.getChildren().addAll(seat);
                        seat.setStyle("-fx-background-color: null; -fx-border-color: #E3236D; -fx-border-width: 3; -fx-border-radius: 8");
                        bookingDetailsDisplay(station, passengersArray, passengerQueueOne, passengerQueueTwo, boardedPassengers, i, seat, toggleButtonOne, toggleButtonTwo, "displaySlotName", row);
                    }

                    if (actionType.equals("trainSeatsDisplay")) {
                        row.getChildren().addAll(seat);
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

    private void addPassengersToWaitingRoom(int station, String[][][][] passengersArray, Passenger[]
            waitingRoom, PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo, Passenger[] boardedPassengers) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********************************");
        System.out.println("\033[1;93m" + "ADD PASSENGERS TO THE WAITING ROOM" + "\033[0m");
        System.out.println("**********************************");

        Stage addPassengersWaitingRoom = new Stage();
        if (station == 0) {
            addPassengersWaitingRoom.setTitle("Destination - Badulla to Colombo");
        } else {
            addPassengersWaitingRoom.setTitle("Destination - Colombo to Badulla");
        }
        addPassengersWaitingRoom.initModality(Modality.APPLICATION_MODAL);
        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        addPassengersWaitingRoom.getIcons().add(windowIcon);
        addPassengersWaitingRoom.setOnCloseRequest(event -> {
            event.consume();
            alertBoxWindowTypeOne(addPassengersWaitingRoom);
        });

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 650, 750);

        Label header = new Label("Add passengers to the Waiting Room");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 200, 30, 100));

        VBox rowOne = new VBox();
        VBox rowTwo = new VBox();
        VBox rowThree = new VBox();
        VBox rowFour = new VBox();
        VBox rowFive = new VBox();
        VBox rowSix = new VBox();

        HBox waitingRoomLowerPanel = new HBox();

        trainSeatsDisplay(station, passengersArray, passengerQueueOne, passengerQueueTwo, waitingRoom, boardedPassengers, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, waitingRoomLowerPanel, "seatsWithToggle");

        Button closeBtn = new Button("Close");
        new ButtonFX().closeBtn(closeBtn);
        closeBtn.setOnAction(event -> addPassengersWaitingRoom.close());

        flowPane.getChildren().addAll(rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, waitingRoomLowerPanel, closeBtn);

        addPassengersWaitingRoom.setScene(scene);
        addPassengersWaitingRoom.showAndWait();

    }

    private void trainQueueDisplay(VBox queueOrder, VBox nameBox, VBox seatBox, PassengerQueue passengerQueue) {
        int queueArrayLength = 0;

        for (Passenger p : passengerQueue.getQueueArray()) {
            if (p != null) {
                queueArrayLength++;
            }
        }
        System.out.println("queueArrayLength - " + queueArrayLength);

        class queueDisplay {
            private void selectedQueue(Passenger[] queue, int arrayLen) {
                try {
                    Label queueOrderLabel;
                    Label passengerSeatLabel;
                    Label passengerNameLabel;

                    for (int i = 1; i <= arrayLen; i++) {
                        queueOrderLabel = new Label(String.format("%02d", i));
                        queueOrderLabel.setFont(new Font("Arial Bold", 16));
                        queueOrder.getChildren().addAll(queueOrderLabel);
                    }
                    for (Passenger p : queue) {
                        if (p != null) {
                            if (p.getSeat() < 10) {
                                passengerSeatLabel = new Label("0" + p.getSeat());
                            } else {
                                passengerSeatLabel = new Label(String.valueOf(p.getSeat()));
                            }
                            passengerNameLabel = new Label(p.getFirstName() + " " + p.getSurname());

                            passengerNameLabel.setFont(new Font("Arial Bold", 16));
                            passengerSeatLabel.setFont(new Font("Arial Bold", 16));

                            nameBox.getChildren().addAll(passengerNameLabel);
                            seatBox.getChildren().addAll(passengerSeatLabel);
                        }
                    }
                } catch (Exception e) {
                    //
                }
            }
        }

        if (queueArrayLength != 0) {
            new queueDisplay().selectedQueue(passengerQueue.getQueueArray(), queueArrayLength);
        } else {
            alertBoxWindowTypeTwo("Alert!", "Train Queue is Empty!", "1");
        }

    }

    private void addPassengersToQueue(int station, String[][][][] passengersArray, Passenger[]
            waitingRoom, PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo) {
        int queueOneLen = 0;
        int queueTwoLen = 0;


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
            passengerQueueTwo.add(waitingRoom);
        } else if (queueOneLen < queueTwoLen) {
            passengerQueueOne.add(waitingRoom);
        } else {
            //adding passengers to the queue from queue01
            passengerQueueOne.add(waitingRoom);
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
        headerQueue.setPadding(new Insets(0, 200, 30, 110));

        Scene sceneQueue = new Scene(flowPaneQueue, 425, 600);

        Label emptySpace = new Label();
        emptySpace.setPadding(new Insets(0, 0, 0, 50));

        FlowPane trainQueue = new FlowPane();

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
        queueHeaders.setPadding(new Insets(15, 0, 0, 0));

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.setPrefWidth(360);
        buttons.getChildren().addAll(queueOne, queueTwo, emptySpace, closeBtnQueue);
        buttons.setPadding(new Insets(10));
        buttons.setStyle("-fx-background-color: rgba(0,0,0,0.04); -fx-background-radius: 30;");

        Label orderNumLabel = new Label("##");
        orderNumLabel.setFont(new Font("Arial Bold", 16));
        orderNumLabel.setStyle("-fx-underline: true;");

        Label nameLabel = new Label("Passenger's Name");
        nameLabel.setPadding(new Insets(0, 0, 25, 40));
        nameLabel.setFont(new Font("Arial Bold", 16));
        nameLabel.setStyle("-fx-underline: true;");

        Label seatNumLabel = new Label("Seat");
        seatNumLabel.setPadding(new Insets(0, 0, 25, 125));
        seatNumLabel.setFont(new Font("Arial Bold", 16));
        seatNumLabel.setStyle("-fx-underline: true;");

        queueHeaders.getChildren().addAll(orderNumLabel, nameLabel, seatNumLabel);
        trainQueue.getChildren().addAll(queueHeaders);

        VBox queueOrder = new VBox();
        queueOrder.setPadding(new Insets(0, 50, 0, 0));
        VBox nameBox = new VBox();
        VBox seatBox = new VBox();
        seatBox.setPadding(new Insets(0, 0, 0, 95));

        trainQueue.getChildren().addAll(queueOrder, nameBox, seatBox);

        try {
            queueOne.setOnMouseClicked(event -> {
                trainQueueDisplay(queueOrder, nameBox, seatBox, passengerQueueOne);
                trainQueue.getChildren().removeAll(queueOrder, nameBox, seatBox);
                //queueOne.setOnMouseClicked(event1 -> trainQueue.getChildren().removeAll(queueOrder, nameBox, seatBox));
            });
        } catch (Exception e) {
            //
        }

        try {
            queueTwo.setOnMouseClicked(event -> {
                trainQueueDisplay(queueOrder, nameBox, seatBox, passengerQueueTwo);
                //queueTwo.setOnMouseClicked(event1 -> trainQueue.getChildren().removeAll(queueOrder, nameBox, seatBox));
            });

            trainQueue.getChildren().addAll(queueOrder, nameBox, seatBox);
        } catch (Exception e) {
            //
        }

        flowPaneQueue.getChildren().addAll(headerQueue, buttons, trainQueue);

        viewTrainQueueStage.setScene(sceneQueue);
        viewTrainQueueStage.showAndWait();
    }

    private void deletePassengerFromQueue(PassengerQueue passengerQueueOne, PassengerQueue passengerQueueTwo) {
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

        System.out.print("Prompt your First Name : ");
        checkRemoveName = sc.next();

        if (checkRemoveNameListQueueOne.contains(checkRemoveName)) {
            passengerQueueOne.remove();
        } else if (checkRemoveNameListQueueTwo.contains(checkRemoveName)) {
            passengerQueueTwo.remove();
        } else {
            System.out.println("No bookings were made by " + checkRemoveName);
        }

        System.out.println("\nafter delete queue 01\n" + Arrays.toString(passengerQueueOne.getQueueArray()));
        System.out.println("\nafter delete queue 02\n" + Arrays.toString(passengerQueueTwo.getQueueArray()));

        System.out.println();
    }

    private void displayAll(int station, String[][][][] passengersArray, Passenger[] waitingRoom, Passenger[]
            boardedPassengers, PassengerQueue
                                    passengerQueueOne, PassengerQueue passengerQueueTwo) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*****************");
        System.out.println("\033[1;93m" + "DISPLAY ALL SLOTS" + "\033[0m");
        System.out.println("*****************\n");

        Stage window = new Stage();
        if (station == 0) {
            window.setTitle("Destination - Badulla to Colombo");
        } else {
            window.setTitle("Destination - Colombo to Badulla");
        }

        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        window.getIcons().add(windowIcon);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(40));

        Scene scene = new Scene(flowPane, 1725, 700);

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
        header3.setPadding(new Insets(0, 999, 25, 125));

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

        FlowPane trainQueue = new FlowPane();

        Label emptySpace = new Label();
        emptySpace.setPadding(new Insets(0, 0, 0, 325));

        Label emptySpaceTwo = new Label();
        emptySpaceTwo.setPadding(new Insets(0, 0, 0, 290));

        Button emptySpaceThree = new Button();
        emptySpaceThree.setStyle("-fx-background-color: null");
        emptySpaceThree.setPrefSize(1500, 30);

        trainSeatsDisplay(station, passengersArray, passengerQueueOne, passengerQueueTwo, waitingRoom, boardedPassengers, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, waitingRoomLowerPanel, "displayAll");
        trainSeatsDisplay(station, passengersArray, passengerQueueOne, passengerQueueTwo, waitingRoom, boardedPassengers, trainSeatsRowOne, trainSeatsRowTwo, trainSeatsRowThree, trainSeatsRowFour, trainSeatsRowFive, trainSeatsRowSix, waitingRoomLowerPanelTwo, "trainSeatsDisplay");

        HBox queueHeaders = new HBox();

        Label orderNumLabel = new Label("##");
        orderNumLabel.setFont(new Font("Arial Bold", 16));
        orderNumLabel.setStyle("-fx-underline: true;");

        Label nameLabel = new Label("Passenger's Name");
        nameLabel.setPadding(new Insets(0, 0, 25, 40));
        nameLabel.setFont(new Font("Arial Bold", 16));
        nameLabel.setStyle("-fx-underline: true;");

        Label seatNumLabel = new Label("Seat");
        seatNumLabel.setPadding(new Insets(0, 0, 25, 125));
        seatNumLabel.setFont(new Font("Arial Bold", 16));
        seatNumLabel.setStyle("-fx-underline: true;");

        queueHeaders.getChildren().addAll(orderNumLabel, nameLabel, seatNumLabel);
        trainQueue.getChildren().addAll(queueHeaders);

        VBox queueOrder = new VBox();
        queueOrder.setPadding(new Insets(0, 50, 0, 0));
        VBox nameBox = new VBox();
        VBox seatBox = new VBox();
        seatBox.setPadding(new Insets(0, 0, 0, 95));


        Button closeBtn = new Button("Close");
        new ButtonFX().closeBtn(closeBtn);
        closeBtn.setOnAction(event -> window.close());

        try {
            queueOne.setOnMouseClicked(event -> {
                trainQueue.getChildren().addAll(queueOrder, nameBox, seatBox);
                trainQueueDisplay(queueOrder, nameBox, seatBox, passengerQueueOne);
                //queueOne.setOnMouseClicked(event1 -> trainQueue.getChildren().removeAll(queueOrder, nameBox, seatBox));
            });

            queueTwo.setOnMouseClicked(event -> {
                //trainQueue.getChildren().addAll(queueOrder, nameBox, seatBox);
                trainQueueDisplay(queueOrder, nameBox, seatBox, passengerQueueTwo);
                queueTwo.setOnMouseClicked(event1 -> trainQueue.getChildren().removeAll(queueOrder, nameBox, seatBox));
            });
        } catch (IllegalArgumentException e) {
            //
        }

        flowPane.getChildren().addAll(headers, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, separator, trainQueue, separatorTwo, trainSeatsRowOne, trainSeatsRowTwo, trainSeatsRowThree, trainSeatsRowFour, trainSeatsRowFive, trainSeatsRowSix);
        flowPane.getChildren().addAll(emptySpaceThree, waitingRoomLowerPanel, emptySpace, closeBtn, emptySpaceTwo, waitingRoomLowerPanelTwo);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("--------------------------------------------------");
    }

    private void generateReport(int station, PassengerQueue passengerQueueOne, PassengerQueue
            passengerQueueTwo, Passenger[] boardedPassengers) throws Exception {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*******************");
        System.out.println("\033[1;93m" + "GENERATE THE REPORT" + "\033[0m");
        System.out.println("*******************");

        int maxStayInQueueOne = 0;
        int maxStayInQueueTwo = 0;
        int queueOneLen = 0;
        int queueTwoLen = 0;

        int minTimeQueueOne = passengerQueueOne.getQueueArray()[0].getSecondsInQueue();
        int minTimeQueueTwo = passengerQueueTwo.getQueueArray()[0].getSecondsInQueue();

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

    double sumOfTime = 0;
    int minTime = 0;

    private void generateReportMain(int station, PassengerQueue passengerQueueOne, PassengerQueue
            passengerQueueTwo, Passenger[] boardedPassengers, int minTimeQueueOne, int minTimeQueueTwo) throws Exception {
        LocalDate localDate = LocalDate.now();

        class queueReport {
            public void queue(PassengerQueue passengerQueue) throws InterruptedException {
                if (passengerQueue == passengerQueueOne) {
                    System.out.println("\033[4;37m" + "\nQueue 01\n" + "\033[0m");
                } else {
                    System.out.println("\033[4;37m" + "\nQueue 02\n" + "\033[0m");
                }
                wrapper:
                while (true) {
                    if (sumOfTime <= passengerQueue.getMaxStayInQueue()) {
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
                    } else {
                        System.out.println("\nTrain is about to depart, Boarding process has stopped now!");
                        break;
                    }
                }
            }
        }
        new queueReport().queue(passengerQueueOne);
        new queueReport().queue(passengerQueueTwo);

        int boardedArrayLen = 0;
        for (Passenger p : boardedPassengers) {
            if (p != null) {
                boardedArrayLen++;
            }
        }

        System.out.println("\nboardedPassengers\n" + Arrays.toString(boardedPassengers));
        System.out.println("\nqueue 01\n" + Arrays.toString(passengerQueueOne.getQueueArray()));
        System.out.println("\nqueue 02\n" + Arrays.toString(passengerQueueTwo.getQueueArray()));

        BufferedWriter bufferedWriter;
        File file;
        String destination;

        if (station == 0) {
            destination = "Badulla to Colombo";
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\BadullaToColomboQueue.txt");
        } else {
            destination = "Colombo to Badulla";
            file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW2\\v2\\src\\storeData\\ColomboToBadullaQueue.txt");
        }

        if (boardedArrayLen > 0) {
            while (true) {
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                    for (Passenger p : boardedPassengers) {
                        if (p != null) {
                            bufferedWriter.write("Destination - " + destination + " | Booked date - " + localDate +
                                    " | Passenger name - " + p.getFirstName() + " " + p.getSurname() +
                                    " | NIC - " + p.getNic() +
                                    " | Seat #" + p.getSeat());
                            bufferedWriter.newLine();
                        }
                    }

                    System.out.println("\nsumOfTime - " + sumOfTime);
                    System.out.println("boardedArrayLen - " + boardedArrayLen);

                    DecimalFormat f = new DecimalFormat("##.00");
                    double averageWaitingTimeQueueOne = sumOfTime / passengerQueueOne.getMaxLength();
                    double averageWaitingTimeQueueTwo = sumOfTime / passengerQueueTwo.getMaxLength();

                    bufferedWriter.write("\nQueue 01");
                    bufferedWriter.write("\nMaximum Queue Length          : " + passengerQueueOne.getMaxLength());
                    bufferedWriter.write("\nMaximum Waiting Time          : " + passengerQueueOne.getMaxStayInQueue());
                    bufferedWriter.write("\nMinimum Waiting Time          : " + minTimeQueueOne);
                    bufferedWriter.write("\nAverage Waiting Time          : " + f.format(averageWaitingTimeQueueOne));
                    bufferedWriter.newLine();
                    bufferedWriter.write("\nQueue 02");
                    bufferedWriter.write("\nMaximum Queue Length          : " + passengerQueueTwo.getMaxLength());
                    bufferedWriter.write("\nMaximum Waiting Time          : " + passengerQueueTwo.getMaxStayInQueue());
                    bufferedWriter.write("\nMinimum Waiting Time          : " + minTimeQueueTwo);
                    bufferedWriter.write("\nAverage Waiting Time          : " + f.format(averageWaitingTimeQueueTwo));
                    bufferedWriter.newLine();

                    bufferedWriter.flush();
                    bufferedWriter.close();

                    //reportGui(passengerQueue.getMaxLength(), passengerQueue.getMaxStayInQueue(), Math.round(minTime), averageWaitingTime, today, trip);
                    break;

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("File not found!\n");
                }
            }
        }
    }

}
