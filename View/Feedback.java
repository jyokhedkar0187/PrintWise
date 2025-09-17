package View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Feedback {

    public Scene getScene(Stage stage) {
        Label heading = new Label("Rate your experience !!");
        heading.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        Label subHeading = new Label("How satisfied are you with PrintWise?");
        subHeading.setStyle("-fx-font-size: 20px;");

        HBox starsBox = new HBox(10);
        starsBox.setAlignment(Pos.CENTER);

        int[] rating = {0};

        for (int i = 1; i <= 5; i++) {
            Button starButton = new Button("☆");
            starButton.setStyle("-fx-font-size: 40px; -fx-text-fill: gold; -fx-background-color: transparent;");
            int starValue = i;

            starButton.setOnAction(event -> {
                rating[0] = starValue;
                for (int j = 0; j < 5; j++) {
                    Button b = (Button) starsBox.getChildren().get(j);
                    b.setText(j < starValue ? "★" : "☆");
                }
            });

            starsBox.getChildren().add(starButton);
        }

        Label improvementLabel = new Label("Tell us what can be improved?");
        improvementLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        improvementLabel.setAlignment(Pos.CENTER);

        String[] options = {
            "Issue with file uploading", "Printing quality",
            "Delivery service", "Customer support", "Other"
        };

        FlowPane chipPane = new FlowPane(10, 10);
        chipPane.setPadding(new Insets(10));
        chipPane.setPrefWrapLength(300);
        chipPane.setAlignment(Pos.CENTER);

        for (String option : options) {
            ToggleButton chip = new ToggleButton(option);
            chip.setStyle("-fx-background-radius: 15; -fx-padding: 5 15 5 15;");
            chipPane.getChildren().add(chip);
        }

        Label uploadLabel = new Label("Upload Screenshot!");
        uploadLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label uploadHint = new Label("(If you're facing issue please upload screenshot!)");
        Button uploadButton = new Button("+");
        uploadButton.setPrefSize(80, 80);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose an Image");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", ".jpg", ".jpeg", ".png", ".gif")
            );
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                uploadButton.setText("✔");
            }
        });

        VBox uploadBox = new VBox(5, uploadLabel, uploadHint, uploadButton);
        uploadBox.setAlignment(Pos.CENTER);

        Button submitButton = new Button("SUBMIT FEEDBACK");
        submitButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 15px;");
        submitButton.setMaxWidth(300);
        submitButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Feedback");
            alert.setHeaderText("Thank you for your feedback!");
            alert.showAndWait();
        });
        Button backButton = new Button("← Back to Home");
        backButton.setStyle("-fx-background-color: #60a7c3; -fx-text-fill: white; -fx-font-size: 14px;");
        backButton.setOnAction(ev -> {
        UserPage homePage = new UserPage();
        homePage.start(stage);
});


        VBox content = new VBox(20,
            heading,
            subHeading,
            starsBox,
            new Separator(),
            improvementLabel,
            chipPane,
            uploadBox,
            imageView,
            submitButton ,backButton
        );

        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        Image backgroundImage = new Image(getClass().getResourceAsStream("/Assests/Images/background.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(700);
        backgroundView.setFitHeight(800);
        backgroundView.setPreserveRatio(false);
        backgroundView.setSmooth(true);
        backgroundView.setCache(false);

        StackPane layeredRoot = new StackPane(backgroundView, content);
        return new Scene(layeredRoot, 700, 800);
    }
}
