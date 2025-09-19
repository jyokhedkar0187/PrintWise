package com.projectf.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
public class AdminProfileSidebar {

    public VBox getSidebar() {
        VBox profileBox = new VBox(20);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setPadding(new Insets(20));

        ImageView profileImage = new ImageView(new Image("/assests/images/profile-user.png"));
        profileImage.setFitWidth(120);
        profileImage.setFitHeight(120);

        Label nameLabel = new Label("Hi, Admin");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 23));
        nameLabel.setTextFill(Color.GREEN);

        Label phoneLabel = new Label("+91 8788899188");
        phoneLabel.setFont(Font.font("Arial", 15));
        phoneLabel.setTextFill(Color.BLACK);

        VBox menuBox = new VBox(15);
        menuBox.setPadding(new Insets(10, 30, 10, 30));
        String[] options = {
                "Manage Account",
                "Feedback",
                "Admin Feedback",
                "Help & FAQ",
                "Logout"
        };
        menuBox.setAlignment(Pos.CENTER);
        for (String option : options) {
            Button btn = new Button(option);
            btn.setPrefWidth(200);
            btn.setAlignment(Pos.CENTER);
            btn.setStyle("-fx-background-color: rgba(222, 235, 243, 1); -fx-border-color: #60a7c3ff;");
            btn.setOnAction(e -> {
                // Add relevant event handling here as needed
                Alert alert = new Alert(Alert.AlertType.INFORMATION, option + " clicked.");
                alert.showAndWait();
            });
            menuBox.getChildren().add(btn);
        }

        VBox supportBox = new VBox(5);
        supportBox.setAlignment(Pos.CENTER);
        supportBox.setPadding(new Insets(20));
        Label supportEmail = new Label("support@printwise.com");
        supportEmail.setFont(Font.font("Arial", 15));
        Label supportPhone = new Label("+91 7385312474");
        supportPhone.setFont(Font.font("Arial", 15));
        Label supportTime = new Label("Mon to Fri, 11 AM to 6 PM");
        supportTime.setFont(Font.font("Arial", 15));
        supportTime.setTextFill(Color.BLACK);
        HBox socialIcons = new HBox(10, 
            new Label("🌐 Facebook"),
            new Label("📷 Instagram"),
            new Label("🐦 X"),
            new Label("▶ YouTube")
        );
        socialIcons.setAlignment(Pos.CENTER);

        supportBox.getChildren().addAll(supportEmail, supportPhone, supportTime, socialIcons);

        VBox sidebar = new VBox(25, profileImage, nameLabel, phoneLabel, menuBox, supportBox);
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #686ff4ff;");

        return sidebar;
    }
}