package View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AboutUs extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene scene = getScene(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene getScene(Stage primaryStage) {
        primaryStage.setTitle("About Us - PrintWise");

        // Load background image
        Image backgroundImage = new Image(getClass().getResource("/Assests/Images/background.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false);
        backgroundView.setSmooth(true);
        backgroundView.setCache(true);
        backgroundView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundView.fitHeightProperty().bind(primaryStage.heightProperty());

        // Create main content layout
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("About Us");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        
        VBox  Description  = new VBox(5);
       Description.setAlignment(Pos.CENTER);
        Label description = new Label("PrintWise is an innovative online document printing platform that brings professional printing services to your fingertips. Our team is dedicated to making printing easier, faster, and more accessible.PrintWise is a modern online document printing platform designed to make professional printing services more accessible and efficient. It caters to individuals, students, and businesses by offering. Easy online file submission for quick processing, High-quality prints for academic, business, and personal use, Affordable pricing with customizable options, Fast turnaround times and reliable delivery, User-friendly interface across devices.");
        description.setWrapText(true);
        description.setStyle("fx-font-size: 25px");
      //description.setAlignment(Pos.CENTER);

        VBox guideBox = new VBox(5);
        guideBox.setAlignment(Pos.CENTER);
        ImageView guideImage = new ImageView(new Image(getClass().getResource("/Assests/Images/sir.jpg").toExternalForm()));
        guideImage.setFitHeight(190);
        guideImage.setFitWidth(170);
        Label guideName = new Label("Guided by: Prof.Sashi Bagal");
        guideName.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        Label guideRole = new Label("Core2Web");
        guideBox.getChildren().addAll(guideImage, guideName, guideRole);

        HBox teamBox = new HBox(30);
        teamBox.setAlignment(Pos.CENTER);
        teamBox.getChildren().addAll(
            createTeamMember("/Assests/Images/tanvi.jpg", "Tanvi Aher", "Computer Science"),
            createTeamMember("/Assests/Images/jyoti.jpg", "Jyoti Khedkar", "Computer Science"),
            createTeamMember("/Assests/Images/shreya.jpg", "Shreya Kachate", "Information Technology"),
            createTeamMember("/Assests/Images/shreyaM.jpg", "Shreya Malshirashkar", "Computer Science")
        );

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            VBox buttonBox = new VBox(15);
            buttonBox.setPadding(new Insets(20));
            buttonBox.setAlignment(Pos.TOP_CENTER);
            // Button backButton = new Button("← Back to Home");
       // backButton.setStyle("-fx-background-color: #60a7c3; -fx-text-fill: white; -fx-font-size: 14px;");
       // backButton.setOnAction(ev -> {
        UserPage homePage = new UserPage();
        homePage.start(primaryStage);
        });

        VBox addressBox = new VBox(5);
        addressBox.setAlignment(Pos.CENTER);
        Label mentorTitle = new Label("Mentor:");
        mentorTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label mentor = new Label("Mansi Jadhav");
        addressBox.getChildren().addAll(mentorTitle, mentor);

        content.getChildren().addAll(title, description, guideBox, teamBox, addressBox,backButton);

        // Layer background and content
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, content);

        return new Scene(root, 1000, 750);
    }

    private VBox createTeamMember(String imagePath, String name, String role) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        ImageView image = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        image.setFitHeight(150);
        image.setFitWidth(130);
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold ;-fx-font-Size:18;");
        Label roleLabel = new Label(role);
        box.getChildren().addAll(image, nameLabel, roleLabel);
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
