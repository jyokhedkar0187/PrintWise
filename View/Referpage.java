package com.projectf.view;

import java.util.Arrays;

import com.projectf.Controller.MainApp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ReferPage extends Application {

    @Override
    public void start(Stage ReferStage) {
        ReferStage.setTitle("Refer Page");
        ReferStage.show();
    }
        public void start(Stage ReferStage, MainApp mainapp) {

        // Sharing message text and encoding
        String title = "PrintWise Referral";
        String message = "Check out this awesome PrintWise referral program! Use code: 16981\n"
                + "Earn rewards when your friends place their first order.";

        String encodedMessage;
        String encodedSubject;
        try {
            encodedMessage = java.net.URLEncoder.encode(message, "UTF-8");
            encodedSubject = java.net.URLEncoder.encode("Referral: " + title, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            encodedMessage = message; // fallback to unencoded
            encodedSubject = "Referral: " + title;
        }
        final String finalEncodedMessage = encodedMessage;
        final String finalEncodedSubject = encodedSubject;

        // Create share menu items
        MenuItem whatsapp = new MenuItem("Share via WhatsApp");
        whatsapp.setOnAction(e -> {
            openInBrowser("https://wa.me/?text=" + finalEncodedMessage);
        });

        MenuItem facebook = new MenuItem("Share on Facebook");
        facebook.setOnAction(e -> {
            String url = "https://youreventhub.com"; // Replace with your actual app/site URL
            openInBrowser("https://www.facebook.com/sharer/sharer.php?u=" + url + "&quote=" + finalEncodedMessage);
        });

        MenuItem instagram = new MenuItem("Share on Instagram");
        instagram.setOnAction(e -> {
            // Opens Instagram homepage, since Instagram doesn't support direct text sharing via URL
            openInBrowser("https://www.instagram.com/");
        });

        MenuItem email = new MenuItem("Share via Email");
        email.setOnAction(e -> {
            openInBrowser("mailto:?subject=" + finalEncodedSubject + "&body=" + finalEncodedMessage);
        });

        ContextMenu shareMenu = new ContextMenu(whatsapp, facebook, instagram, email);

        // --------------------- Start UI setup --------------------------

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

        VBox content = new VBox(20);
        content.setPadding(new Insets(10));
        content.setAlignment(Pos.TOP_CENTER);

        Image bgImage = new Image(getClass().getResource("/Assests/Images/background.jpg").toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );

        StackPane root = new StackPane(content);
        root.setBackground(new Background(backgroundImage));

        // Top Box for earn coins banner
        VBox earnCoinsBox = new VBox(10);
        earnCoinsBox.setStyle("-fx-background-color: #b1def5ff; -fx-background-radius: 10;");
        earnCoinsBox.setPadding(new Insets(10));
        earnCoinsBox.setAlignment(Pos.CENTER);
        earnCoinsBox.setEffect(new DropShadow(10, Color.BLACK));

        Label earnCoinsText = new Label("Earn 100 Coins!");
        earnCoinsText.setTextFill(Color.BLACK);
        earnCoinsText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label description = new Label("Invite Your Friends to PrintWise - Earn When Their First Order is Delivered!!");
        description.setTextFill(Color.BLACK);
        description.setFont(Font.font("Arial", 16));
        description.setAlignment(Pos.CENTER);
        description.setWrapText(true);

        Button inviteButton = new Button("Invite Now");
        inviteButton.setStyle("-fx-background-color: #7da3dcff; -fx-text-fill: white; -fx-font-size: 17px;");

        earnCoinsBox.getChildren().addAll(earnCoinsText, description, inviteButton);

        // Refer a friend image placeholder
        ImageView referImage = new ImageView(new Image("https://img.icons8.com/ios-filled/100/megaphone.png"));
        referImage.setFitHeight(90);
        referImage.setFitWidth(90);

        Label referText = new Label("REFER A FRIEND");
        referText.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        referText.setTextFill(Color.BLUE);

        VBox imageBox = new VBox(10, referImage, referText);
        imageBox.setAlignment(Pos.CENTER);

        // Referral code box
        Label codeLabel = new Label("16981");
        codeLabel.setFont(Font.font("Arial", 24));
        codeLabel.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 10;");

        // Description under referral code
        Label note = new Label(
                "Share this code to refer your friends. Once their order is successfully delivered, you'll earn 100 Coins\n" +
                        "(worth ₹10 = 20 Prints B/W Duplex).");
        note.setFont(Font.font("Arial", 13));
        note.setWrapText(true);
        note.setAlignment(Pos.CENTER);
        note.setTextFill(Color.BLACK);

        // How it works
        Label howItWorks = new Label("How It Works");
        howItWorks.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        howItWorks.setTextFill(Color.BLACK);
        howItWorks.setAlignment(Pos.CENTER);
        howItWorks.setStyle("-fx-underline: false; -fx-padding: 8;");

        VBox step1 = createStep("Share referral", "code with your friends", "/Assests/Images/refer.png");
        VBox step2 = createStep("Friend joins", "EarnReward app", "/Assests/Images/credit-card.png");
        VBox step3 = createStep("You earn", "₹15.00 reward", "/Assests/Images/gift.png");

        HBox stepsBox = new HBox(step1, step2, step3);
        stepsBox.setSpacing(20);
        stepsBox.setAlignment(Pos.CENTER);
        stepsBox.setPadding(new Insets(10));

        VBox howBox = new VBox(howItWorks, stepsBox);
        howBox.setAlignment(Pos.CENTER);
        howBox.setStyle("-fx-background-color: #b1def5ff; -fx-background-radius: 12;");
        howBox.setPadding(new Insets(10));
        howBox.setEffect(new DropShadow(10, Color.BLACK));

        // Refer now button with ContextMenu share popup
        Button referNowButton = new Button("Refer Now");
        referNowButton.setStyle("-fx-background-color: #2ec6e4ff; -fx-text-fill: White; -fx-font-size: 20px; -fx-padding: 5 10 5 10; ");
        referNowButton.setMaxWidth(150);

        referNowButton.setOnAction(e -> {
            shareMenu.show(referNowButton,
                    referNowButton.localToScreen(0, referNowButton.getHeight()).getX(),
                    referNowButton.localToScreen(0, referNowButton.getHeight()).getY());
        });

        // Navigation buttons
        Button homeBtn = new Button("Home");
        homeBtn.setOnAction(e -> mainapp.showHomepage());

        Button ordersBtn = new Button("Orders");
        ordersBtn.setOnAction(e -> mainapp.showOrdersPage());

        Button explorebtn = new Button("Explore");
        explorebtn.setOnAction(e -> mainapp.showExplorePage());

        Button referBtn = new Button("Refer");
        // You can add action if needed

        for (Button btn : Arrays.asList(homeBtn, ordersBtn, explorebtn, referBtn)) {
            btn.setPrefWidth(100);
            btn.setStyle("-fx-background-color: #37a9dbff; -fx-text-fill: white; -fx-background-radius: 20;");
        }

        HBox navBar = new HBox(30, homeBtn, ordersBtn, explorebtn, referBtn);
        navBar.setAlignment(Pos.BOTTOM_CENTER);
        navBar.setPadding(new Insets(20));

        content.getChildren().addAll(logoBox, earnCoinsBox, howBox, imageBox, codeLabel, note, referNowButton, navBar);

        Scene Referscene = new Scene(root, 1000, 750);
        ReferStage.setTitle("EwayPrint - Refer & Earn");
        ReferStage.setScene(Referscene);
        ReferStage.show();
    }

    private VBox createStep(String title, String desc, String imagePath) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", 16));
        titleLabel.setTextFill(Color.BLACK);

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Arial", 12));
        descLabel.setTextFill(Color.BLACK);
        descLabel.setWrapText(true);
        descLabel.setAlignment(Pos.CENTER);

        ImageView icon = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        icon.setFitHeight(40);
        icon.setFitWidth(40);

        VBox stepBox = new VBox(5, icon, titleLabel, descLabel);
        stepBox.setAlignment(Pos.CENTER);
        stepBox.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-background-radius: 8;");
        stepBox.setPadding(new Insets(10));

        return stepBox;
    }

    private void openInBrowser(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}


