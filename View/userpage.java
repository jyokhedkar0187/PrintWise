package View;

import com.projectf.Controller.MainApp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UserPage extends Application {

    private Runnable onBack;

    @Override
    public void start(Stage primaryStage) {
        Scene scene = buildScene(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Page - SuperX");
        primaryStage.show();
    }

    public Scene getScene(Stage stage, Runnable onBack) {
        this.onBack = onBack;
        return buildScene(stage);
    }

    private Scene buildScene(Stage primaryStage) {
        VBox profileBox = new VBox(20);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setPadding(new Insets(20));

        ImageView profileImage = new ImageView(new Image("/Assests/Images/user.png"));
        profileImage.setFitWidth(120);
        profileImage.setFitHeight(120);

        Label nameLabel = new Label("Hi, User");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 23));
        nameLabel.setTextFill(Color.GREEN);

        Label phoneLabel = new Label("+91 8788899188");
        phoneLabel.setFont(Font.font("Arial", 15));
        phoneLabel.setTextFill(Color.BLACK);

        profileBox.getChildren().addAll(profileImage, nameLabel, phoneLabel);

        VBox menuBox = new VBox(15);
        menuBox.setPadding(new Insets(10, 30, 10, 30));
        menuBox.setAlignment(Pos.CENTER);

        String[] options = {
            "Manage Account",
            "Feedback",
            "Help & FAQ",
            "About Us",
            "Logout",
            "Back"
        };

        for (String option : options) {
            Button btn = new Button(option);
            btn.setPrefWidth(300);
            btn.setAlignment(Pos.CENTER);
            btn.setStyle("-fx-background-color: rgba(222, 235, 243, 1); -fx-border-color: #60a7c3ff;");

            btn.setOnAction(e -> {
                if (option.equals("Manage Account")) {
                    manageaccount managePage = new manageaccount();
                    primaryStage.setScene(managePage.getScene(primaryStage));
                } else if (option.equals("Help & FAQ")) {
                    faq faqPage = new faq();
                    primaryStage.setScene(faqPage.getScene(primaryStage));
                } else if (option.equals("Feedback")) {
                    Feedback feedbackPage = new Feedback();
                    Scene feedbackScene = feedbackPage.getScene(primaryStage);
                    primaryStage.setScene(feedbackScene);
                  }  else if (option.equals("About Us")) {
                    AboutUs AboutUsPage = new AboutUs();
                    Scene AboutUsScene = AboutUsPage.getScene(primaryStage);
                    primaryStage.setScene(AboutUsScene);
                }else if(option.equals("Back")) {
                    UserPage homePage = new UserPage();
                    homePage.start(primaryStage); 
                  //  MainApp.showHomepage()                   

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, option + " clicked.");
                    alert.showAndWait();
                }
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

        HBox socialIcons = new HBox(10);
        socialIcons.setAlignment(Pos.CENTER);
        Label fb = new Label("🌐 Facebook");
        Label insta = new Label("📷 Instagram");
        Label x = new Label(" X");
        Label yt = new Label("▶ YouTube");
        socialIcons.getChildren().addAll(fb, insta, x, yt);

        supportBox.getChildren().addAll(supportEmail, supportPhone, supportTime, socialIcons);

        BorderPane root = new BorderPane();
        root.setTop(profileBox);
        root.setCenter(menuBox);
        root.setBottom(supportBox);
        // root.setStyle("-fx-alignment: center; -fx-background-color : rgb(107, 174, 213);");

        Image backgroundImage = new Image(getClass().getResourceAsStream("/Assests/Images/background.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false);
        backgroundView.setSmooth(true);
        backgroundView.setCache(true);
        backgroundView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundView.fitHeightProperty().bind(primaryStage.heightProperty());

        StackPane stack = new StackPane(backgroundView, root);
        return new Scene(stack, 1000, 750);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
