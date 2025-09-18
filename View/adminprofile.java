package View;

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

 public class adminprofile extends Application {

     @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin_Profile Page ");


        VBox profileBox = new VBox(20);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setPadding(new Insets(20));

        ImageView profileImage = new ImageView(new Image("image\\user.png"));

        profileImage.setFitWidth(120);
        profileImage.setFitHeight(120);

        Label nameLabel = new Label("Hi, Admin");
       
        nameLabel.setFont(Font.font("Arial",FontWeight.BOLD, 23));
        nameLabel.setTextFill(Color.GREEN);

        Label phoneLabel = new Label("‪+91 8788899188‬");
        phoneLabel.setFont(Font.font("Arial", 15));
        phoneLabel.setTextFill(Color.BLACK);

        profileBox.getChildren().addAll(profileImage, nameLabel, phoneLabel);

        
        VBox menuBox = new VBox(15);
        menuBox.setPadding(new Insets(10, 30, 10, 30));

        String[] options = {
                "Manage Account",
                "Feedback",
                "Help & FAQ",
                "Logout"
        };
       
        menuBox.setAlignment(Pos.CENTER );

        for (String option : options) {
            Button btn = new Button(option);
            btn.setPrefWidth(300);
            btn.setAlignment(Pos.CENTER);
            btn.setStyle("-fx-background-color: rgba(222, 235, 243, 1); -fx-border-color: #60a7c3ff;");
           
          
            btn.setFont(Font.font("Arial", 15));
            if (option.equals("Logout")) {
                btn.setTextFill(Color.RED);
            }
            btn.setOnAction(e -> {
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

        Label supportPhone = new Label("‪+91 7385312474‬");
        supportPhone.setFont(Font.font("Arial", 15));
        Label supportTime = new Label("Mon to Fri, 11 AM to 6 PM");
        supportTime.setFont(Font.font("Arial", 15));
        supportTime.setTextFill(Color.BLACK);

        
        HBox socialIcons = new HBox(10);
        socialIcons.setAlignment(Pos.CENTER);
        Label fb = new Label("🌐 Facebook");
        Label insta = new Label("📷 Instagram");
        Label x = new Label("🐦 X");
        Label yt = new Label("▶ YouTube");
        socialIcons.getChildren().addAll(fb, insta, x, yt);

        supportBox.getChildren().addAll(supportEmail, supportPhone, supportTime, socialIcons);

    
        BorderPane root = new BorderPane();
        root.setTop(profileBox);
        root.setCenter(menuBox);
        root.setBottom(supportBox);
       // root.setStyle("-fx-alignment: center; -fx-background-color : rgb(107, 174, 213);");

         Image backgroundImage = new Image(getClass().getResourceAsStream("/image/background.jpg"));
         ImageView backgroundView = new ImageView(backgroundImage);
          backgroundView.setPreserveRatio(false);
          backgroundView.setSmooth(true);
          backgroundView.setCache(true);
          backgroundView.fitWidthProperty().bind(primaryStage.widthProperty());
          backgroundView.fitHeightProperty().bind(primaryStage.heightProperty());
        

          StackPane stack = new StackPane(backgroundView, root);
        Scene scene = new Scene(stack, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

}
}
