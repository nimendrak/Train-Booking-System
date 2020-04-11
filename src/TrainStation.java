import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Integer.parseInt;

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

        LocalDate localDate = LocalDate.now();
        Scanner sc = new Scanner(System.in);

        //store booking data in this multi-dimensional array
        String[][][][] passengersArray = new String[2][31][SEATING_CAPACITY][4];

        //creating 42 different objects as passengers for each destination
        Passenger[] badullaToColomboWaitingRoom = new Passenger[42];
        Passenger[] colomboToBadullaWaitingRoom = new Passenger[42];

        //PassengerQueue
        PassengerQueue passengerQueue = new PassengerQueue();

        //Passenger
        Passenger passenger = new Passenger();

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
                        addPassengersToWaitingRoom(station, passengersArray, badullaToColomboWaitingRoom, passengerQueue, passenger);
                    } else {
                        addPassengersToWaitingRoom(station, passengersArray, colomboToBadullaWaitingRoom, passengerQueue, passenger);
                    }
                    break;

                case "A":
                case "a":
                    if (station == 0) {
                        addPassengersToQueue(badullaToColomboWaitingRoom, passengerQueue, passenger);
                    } else {
                        addPassengersToQueue(colomboToBadullaWaitingRoom, passengerQueue, passenger);
                    }
                    break;

                case "V":
                case "v":
                    //displayAll();
                    break;

                case "D":
                case "d":
                    //deletePassengerFromQueue(passengerQueue);
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
                    //generateReport();
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

        int randomSecondsOne = random.nextInt((6 - 1) + 1) + 1;
        int randomSecondsTwo = random.nextInt((6 - 1) + 1) + 1;
        int randomSecondsThree = random.nextInt((6 - 1) + 1) + 1;

        return randomSecondsOne + randomSecondsTwo + randomSecondsThree;
    }

    private void bookingDetailsDisplay(int station, String[][][][] passengersArray, int i, ToggleButton seat, ToggleButton btnOne, ToggleButton btnTwo) {
        LocalDate localDate = LocalDate.now();
        int localDateInt = parseInt(String.valueOf(localDate).substring(8, 10)) - 1;

        List<Integer> bookedSeats = new ArrayList<>();

        for (int j = 1; j <= SEATING_CAPACITY; j++) {
            if (passengersArray[station][localDateInt][i - 1][3] != null) {
                bookedSeats.add(Integer.parseInt(passengersArray[station][localDateInt][i - 1][3]));
                seat.setStyle("-fx-background-color: #00A69C; -fx-background-radius: 8; -fx-border-color: #00A69C; -fx-border-radius: 8; -fx-border-width: 2;");
            } else {
                btnOne.setDisable(true);
                btnTwo.setDisable(true);
                seat.setStyle("-fx-background-color: #E3236D; -fx-background-radius: 8; -fx-border-color: #E3236D; -fx-border-radius: 8; -fx-border-width: 2;");


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

                                status.setFont(new Font("Arial Bold", 16));
                                seatLabel.setPadding(new Insets(-40, 0, 0, 0));
                                seatLabel.setFont(new Font("Arial Bold", 165));

                                destinationLabel = new Label(destination);
                                bookedDateLabel = new Label(String.valueOf(localDate));

                                passengerNameLabel = new Label(passengersArray[station][localDateInt][i - 1][0] + " " + passengersArray[station][localDateInt][i - 1][1]);
                                passengerNicLabel = new Label(passengersArray[station][localDateInt][i - 1][2]);
                            }

                            destinationBox.getChildren().addAll(destinationText, destinationLabel);
                            bookedDateBox.getChildren().addAll(bookedDateText, bookedDateLabel);
                            passengerNameBox.getChildren().addAll(passengerNameText, passengerNameLabel);
                            passengerNicBox.getChildren().addAll(passengerNicText, passengerNicLabel);
                            seatBox.getChildren().addAll(status, seatText, seatLabel);

                        } catch (IndexOutOfBoundsException | NullPointerException | IllegalArgumentException e) {
                            //
                        }
                    }

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

    private void addPassengers(int station, String[][][][] passengersArray, Passenger[] waitingRoom,
                               ToggleButton toggleButtonOne, ToggleButton toggleButtonTwo, int i) {

        LocalDate localDate = LocalDate.now();
        int localDateInt = parseInt(String.valueOf(localDate).substring(8, 10)) - 1;

        toggleButtonTwo.setOnAction(event -> {
            String firstName;
            String surname;
            String nic;
            int seat;

            if (passengersArray[station][localDateInt][i - 1][3] != null) {
                firstName = passengersArray[station][localDateInt][i - 1][0];
                surname = passengersArray[station][localDateInt][i - 1][1];
                nic = passengersArray[station][localDateInt][i - 1][2];
                seat = parseInt(passengersArray[station][localDateInt][i - 1][3]);

                waitingRoom[i] = new Passenger();
                waitingRoom[i].setFirstName(firstName);
                waitingRoom[i].setSurname(surname);
                waitingRoom[i].setNic(nic);
                waitingRoom[i].setSeat(seat);
                waitingRoom[i].setSecondsInQueue(secondsInQueue());

                System.out.println(waitingRoom[i].getFirstName() + " " + waitingRoom[i].getSurname());
                System.out.println(waitingRoom[i].getNic());
                System.out.println(waitingRoom[i].getSeat());
            }
            toggleButtonOne.setOnAction(event1 -> waitingRoom[i] = null);
        });
    }

    private void trainSeatsDisplay(int station, String[][][][] passengersArray, Passenger[] waitingRoom, VBox rowOne, VBox
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
                        //check
                        //add karala ayeth waiting room ekata daddi methana toggle button eke color eka change wenne na
                        try {
                            if (waitingRoom[i - 1] != null) {
                                panelTwo.setStyle("-fx-background-color: #00A69C; -fx-background-radius: 30");
                                toggleButtonOne.setDisable(true);
                                toggleButtonTwo.setDisable(true);
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            //
                        }
                        selectionPanels(i);
                        addPassengers(station, passengersArray, waitingRoom, toggleButtonOne, toggleButtonTwo, i);
                        bookingDetailsDisplay(station, passengersArray, i, seat, toggleButtonOne, toggleButtonTwo);
                    }

                    row.getChildren().addAll(seat, panelTwo);
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
        colorOneButton.setStyle("-fx-background-color: rgba(227,35,109,0.8); -fx-background-radius: 5");
        colorOneButton.setPrefSize(34, 5);

        Label colorOneLabel = new Label("Available Seats");
        colorOneLabel.setPadding(new Insets(7, 0, 0, 3));

        Button colorTwoButton = new Button();
        colorTwoButton.setStyle("-fx-background-color: rgba(0,166,156,0.8); -fx-background-radius: 5");
        colorTwoButton.setPrefSize(34, 5);

        Label colorTwoLabel = new Label("Booked Seats");
        colorTwoLabel.setPadding(new Insets(7, 0, 0, 3));

        panelTwo.setStyle("-fx-background-color: rgba(0,0,0,0.04); -fx-background-radius: 15;");
        panelTwo.setPadding(new Insets(15));
        panelTwo.setSpacing(10);
        panelTwo.getChildren().addAll(colorOneButton, colorOneLabel, colorTwoButton, colorTwoLabel);
    }

    private void addPassengersToWaitingRoom(int station, String[][][][] passengersArray, Passenger[] waitingRoom, PassengerQueue passengerQueue, Passenger passenger) {
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

        trainSeatsDisplay(station, passengersArray, waitingRoom, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, waitingRoomLowerPanel, "seatsWithToggle");

        Button closeBtn = new Button("Close");
        new ButtonFX().closeBtn(closeBtn);
        closeBtn.setOnAction(event -> addPassengersWaitingRoom.close());

        flowPane.getChildren().addAll(rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, waitingRoomLowerPanel, closeBtn);

        System.out.println("\nwaiting Room ; \n" + Arrays.toString(waitingRoom));

        addPassengersWaitingRoom.setScene(scene);
        addPassengersWaitingRoom.showAndWait();

    }

    private void trainQueueDisplay(VBox trainQueue, PassengerQueue passengerQueue, Passenger[] waitingRoom) {
        HBox queueHeaders = new HBox();

        Label orderNumLabel = new Label("##");
        orderNumLabel.setPadding(new Insets(0, 0, 20, 0));
        orderNumLabel.setFont(new Font("Arial Bold", 16));
        orderNumLabel.setStyle("-fx-underline: true;");

        Label nameLabel = new Label("Passenger's Name");
        nameLabel.setPadding(new Insets(0, 0, 20, 40));
        nameLabel.setFont(new Font("Arial Bold", 16));
        nameLabel.setStyle("-fx-underline: true;");

        Label seatNumLabel = new Label("Seat");
        seatNumLabel.setPadding(new Insets(0, 0, 20, 70));
        seatNumLabel.setFont(new Font("Arial Bold", 16));
        seatNumLabel.setStyle("-fx-underline: true;");

        queueHeaders.getChildren().addAll(orderNumLabel, nameLabel, seatNumLabel);
        trainQueue.getChildren().addAll(queueHeaders);

        Label passengerNameQueueLabel = new Label();
        int queueCount = 0;

        for (Passenger p : passengerQueue.getQueueArray()) {
            if (p != null) {
                System.out.println(p);
                queueCount++;
            }
        }

        System.out.println("queueCount - " + queueCount);

        try {
            for (int i = 0; i < queueCount; i++) {
                for (Passenger p : passengerQueue.getQueueArray()) {
                    if (p != null) {
                        if (p.getSeat() < 10) {
                            passengerNameQueueLabel = new Label(String.format("%02d", queueCount) + "         " + p.getFirstName() + " " + p.getSurname() + "         #0" + p.getSeat());
                        } else {
                            passengerNameQueueLabel = new Label(String.format("%02d", queueCount) + "         " + p.getFirstName() + " " + p.getSurname() + "         #" + p.getSeat());
                        }
                    }
                }
            }
            passengerNameQueueLabel.setFont(new Font("Arial Bold", 16));
            trainQueue.getChildren().addAll(passengerNameQueueLabel);
        } catch (Exception e) {
            //
        }
    }

    private void addPassengersToQueue(Passenger[] waitingRoom, PassengerQueue passengerQueue, Passenger
            passenger) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method slotAction = PassengerQueue.class.getDeclaredMethod("add", Passenger[].class);
        slotAction.setAccessible(true);
        for (Passenger p : waitingRoom) {
            if (p != null) {
                slotAction.invoke(passengerQueue, (Object) waitingRoom);
            }
        }

        System.out.println("\nwaiting room - " + Arrays.toString(waitingRoom) + "\n");
        System.out.println("\nqueue room - " + Arrays.toString(passengerQueue.getQueueArray()) + "\n");

        Stage viewTrainQueueStage = new Stage();
        FlowPane flowPaneQueue = new FlowPane();
        flowPaneQueue.setHgap(10);
        flowPaneQueue.setVgap(10);
        flowPaneQueue.setPadding(new Insets(30));

        Label headerQueue = new Label("Train Queue");
        headerQueue.setFont(new Font("Arial Bold", 22));
        headerQueue.setTextFill(Paint.valueOf("#414141"));
        headerQueue.setPadding(new Insets(0, 200, 30, 100));

        Scene sceneQueue = new Scene(flowPaneQueue, 500, 750);

        VBox trainQueue = new VBox();

        Label emptySpace = new Label();
        emptySpace.setPadding(new Insets(750, 0, 0, 200));

        Button closeBtnQueue = new Button("Close");
        new ButtonFX().closeBtn(closeBtnQueue);
        closeBtnQueue.setOnAction(event1 -> viewTrainQueueStage.close());

        trainQueueDisplay(trainQueue, passengerQueue, waitingRoom);

        flowPaneQueue.getChildren().addAll(headerQueue, trainQueue, emptySpace, closeBtnQueue);

        viewTrainQueueStage.setScene(sceneQueue);
        viewTrainQueueStage.showAndWait();
    }

}
