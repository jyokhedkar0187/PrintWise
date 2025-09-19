package com.projectf.view;

import com.google.firebase.database.*;
import com.projectf.Main;
import com.projectf.Controller.MainApp;
import com.projectf.Model.PrintOrder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyOrdersScreen {
    private Stage primaryStage;
    private MainApp mainApp;
    private ObservableList<PrintOrder> orders = FXCollections.observableArrayList();
    private ObservableList<String> notifications = FXCollections.observableArrayList();
    private DatabaseReference ordersRef;
    private ValueEventListener ordersListener;
    
    public void start(Stage stage, MainApp mainApp) {
        this.primaryStage = stage;
        this.mainApp = mainApp;

        ImageView printwiseIcon = new ImageView(new Image("/Assests/Images/Printwise.jpg"));
        printwiseIcon.setFitHeight(50);
        printwiseIcon.setFitWidth(50);

        Label logo = new Label("PrintWise");
        logo.setTextFill(Color.web("Blue"));
        logo.setFont(Font.font("Verdana", FontPosture.ITALIC, 25));

        HBox logoLeft = new HBox(10, printwiseIcon, logo);
        logoLeft.setAlignment(Pos.CENTER_LEFT);

        ImageView bellIcon = new ImageView(new Image("/Assests/Images/notification-bell.png"));
        bellIcon.setFitHeight(30);
        bellIcon.setFitWidth(30);

        ImageView profileIcon = new ImageView(new Image("/Assests/Images/user.png"));
        profileIcon.setFitHeight(30);
        profileIcon.setFitWidth(30);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox logoBox = new HBox(10, logoLeft, spacer, bellIcon, profileIcon);
        logoBox.setAlignment(Pos.TOP_LEFT);

        //root.setTop(logoBox);

        //root.setCenter(vbox);
        //root.setBottom(footer);




        VBox content = new VBox(20);
        content.setPadding(new Insets(10));
        content.setAlignment(Pos.TOP_CENTER);

        BorderPane root = new BorderPane();
   
        
        ListView<PrintOrder> ordersListView = new ListView<>(orders);
        ordersListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(PrintOrder order, boolean empty) {
                super.updateItem(order, empty);
                if (empty || order == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText("File: " + order.fileName +
                            ", Size: " + order.paperSize +
                            ", Copies: " + order.copies +
                            ", Mode: " + order.colorMode +
                            ", Total: Rs." + order.totalPrice);

                    Button detailsBtn = new Button("View Details");
                    detailsBtn.setOnAction(e -> showOrderDetails(order));
                    setGraphic(detailsBtn);
                }
            }
        });
        
        ListView<String> notificationListView = new ListView<>(notifications);
        notificationListView.setPrefHeight(100);

        Label ordersLabel = new Label("Your Orders");
        ordersLabel.setFont(new Font(18)); 
        ordersLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 18));// Set font size to 18, change as needed

        VBox vbox = new VBox(10, ordersLabel, ordersListView);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

       
        Image bgImage=new Image(getClass().getResource("/Assests/Images/background.jpg").toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        root.setBackground(new Background(backgroundImage));


        Button homeBtn = new Button("Home");
        homeBtn.setOnAction(e -> mainApp.showHomepage());

        Button ordersBtn = new Button("Orders");
        ordersBtn.setOnAction(e -> mainApp.showOrdersPage());

        Button explorebtn = new Button("Explore");
        explorebtn.setOnAction(e -> mainApp.showExplorePage());

        Button referBtn = new Button("Refer");
        // You can add action if needed

        for (Button btn : Arrays.asList(homeBtn, ordersBtn, explorebtn, referBtn)) {
            btn.setPrefWidth(100);
            btn.setStyle("-fx-background-color: #37a9dbff; -fx-text-fill: white; -fx-background-radius: 20;");
        }

        HBox navBar = new HBox(30, homeBtn, ordersBtn, explorebtn, referBtn);
        navBar.setAlignment(Pos.BOTTOM_CENTER);
        navBar.setPadding(new Insets(20));

        content.getChildren().addAll(logoBox, navBar);

       root.setCenter(vbox);
       root.setBottom(navBar);
        root.setTop(logoBox);

        Scene scene = new Scene(root, 1000, 750);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        loadOrdersFromFirebase();
    }

   // private VBox createStep(String title, String desc, String imagePath) {

   

       // HBox footer = new HBox(navBar);
       // footer.setAlignment(Pos.CENTER);
        //footer.setPadding(new Insets(10));

      /*  root.setCenter(vbox);
       // root.setBottom(footer);
        root.setTop(logoBox);

        Scene scene = new Scene(root, 1000, 750);
        stage.setScene(scene);
        stage.show();

        loadOrdersFromFirebase();*/
   // }

    private void showOrderDetails(PrintOrder order) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Details");
        alert.setHeaderText("Details for: " + order.fileName);
        alert.setContentText(
                "File Name: " + order.fileName + "\n" +
                "Paper Size: " + order.paperSize + "\n" +
                "Copies: " + order.copies + "\n" +
                "Color Mode: " + order.colorMode + "\n" +
                "Total Price: Rs." + order.totalPrice + "\n" +
                "Status: " + (order.status != null ? order.status : "Completed")
        );
        alert.showAndWait();
    }

    public void addNewOrder(String fileName, String size, int copies, String colorMode, double totalPrice) {
        if (fileName == null || size == null || colorMode == null) {
            throw new IllegalArgumentException("Order parameters cannot be null");
        }
        
        if (copies <= 0 || totalPrice < 0) {
            throw new IllegalArgumentException("Invalid order values");
        }

        PrintOrder order = new PrintOrder(null, fileName, size, copies, colorMode, totalPrice, null, "Completed");
        this.orders.add(order);
        notifications.add("New order: " + fileName);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders");
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("fileName", fileName);
        orderMap.put("paperSize", size);
        orderMap.put("copies", copies);
        orderMap.put("colorMode", colorMode);
        orderMap.put("totalPrice", totalPrice);
        orderMap.put("status", "Completed");

        ref.push().setValueAsync(orderMap);

          }

    private void loadOrdersFromFirebase() {
        ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        ordersListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Platform.runLater(() -> {
                    orders.clear();
                    for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                        try {
                            Map<String, Object> map = (Map<String, Object>) orderSnapshot.getValue();
                            String fileName = map.get("fileName") != null ? map.get("fileName").toString() : "Unknown";
                            String size = map.get("paperSize") != null ? map.get("paperSize").toString() : "A4";
                            int copies = map.get("copies") != null ? Integer.parseInt(map.get("copies").toString()) : 1;
                            String colorMode = map.get("colorMode") != null ? map.get("colorMode").toString() : "Color";
                            double price = map.get("totalPrice") != null ? Double.parseDouble(map.get("totalPrice").toString()) : 0.0;
                            String status = map.get("status") != null ? map.get("status").toString() : "Completed";

                            PrintOrder order = new PrintOrder(orderSnapshot.getKey(), fileName, size, copies, colorMode, price, null, status);
                            orders.add(order);
                        } catch (Exception e) {
                            System.err.println("Error parsing order: " + e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Platform.runLater(() -> 
                    System.err.println("Failed to load orders: " + error.getMessage())
                );
            }
        };
        ordersRef.addValueEventListener(ordersListener);
    }

    private void cleanupFirebaseListeners() {
        if (ordersRef != null && ordersListener != null) {
            ordersRef.removeEventListener(ordersListener);
        }
    }
}