package View;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class manageaccount {

    public Scene getScene(Stage stage) {
        BorderPane root = new BorderPane();

        VBox buttonBox = new VBox(15);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Manage Account");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button addressBtn = createIconButton("Your Address", "location.png");
        Button voucherBtn = createIconButton("Voucher History", "history.png");
        Button deleteBtn = createIconButton("Delete Account", "user.png");
        deleteBtn.setStyle("-fx-text-fill: red;");

        addressBtn.setOnAction(e -> root.setCenter(createAddressForm(root)));
        voucherBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Show History");
            confirm.setContentText("No History to Show !!!");
            confirm.showAndWait();
        });
        deleteBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Delete Account");
            confirm.setContentText("Are you sure you want to delete your account?");
            confirm.showAndWait();
        });
        Button backButton = new Button("← Back to Home");
        backButton.setStyle("-fx-background-color: #60a7c3; -fx-text-fill: white; -fx-font-size: 14px;");
        backButton.setOnAction(ev -> {
        UserPage homePage = new UserPage();
        homePage.start(stage);
});

        buttonBox.getChildren().addAll(title, addressBtn, voucherBtn, deleteBtn,backButton);
        root.setCenter(buttonBox);

        Image backgroundImage = new Image(getClass().getResourceAsStream("/Assests/Images/background.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false);
        backgroundView.setSmooth(true);
        backgroundView.setCache(true);
        backgroundView.fitWidthProperty().bind(stage.widthProperty());
        backgroundView.fitHeightProperty().bind(stage.heightProperty());

        StackPane stack = new StackPane(backgroundView, root);
        return new Scene(stack, 1000, 750);
    }

    private Button createIconButton(String text, String iconPath) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle("-fx-font-size: 15px;");
        return button;
    }

    private VBox createAddressForm(BorderPane root) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        TextField flatField = new TextField();
        flatField.setPromptText("Flat No, Floor No, Building Name");

        TextField areaField = new TextField();
        areaField.setPromptText("Area, Road Name");

        TextField landmarkField = new TextField();
        landmarkField.setPromptText("Landmark");

        TextField pincodeField = new TextField();
        pincodeField.setPromptText("Pincode");

        TextField cityField = new TextField();
        cityField.setPromptText("City");

        TextField stateField = new TextField("MAHARASHTRA");
        stateField.setEditable(false);

        TextField contactField = new TextField("+91 ");
        contactField.setPromptText("Contact No");

        Button saveButton = new Button("Save Address");
        saveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Saved");
            alert.setContentText("Address has been saved successfully!");
            alert.showAndWait();
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            VBox buttonBox = new VBox(15);
            buttonBox.setPadding(new Insets(20));
            buttonBox.setAlignment(Pos.TOP_CENTER);

            Label title = new Label("Manage Account");
            title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            Button addressBtn = createIconButton("Your Address", "location.png");
            Button voucherBtn = createIconButton("Voucher History", "history.png");
            Button deleteBtn = createIconButton("Delete Account", "user.png");
            deleteBtn.setStyle("-fx-text-fill: red;");

            addressBtn.setOnAction(ev -> root.setCenter(createAddressForm(root)));
            voucherBtn.setOnAction(ev -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Show History");
                confirm.setContentText("No History to Show !!!");
                confirm.showAndWait();
            });
            deleteBtn.setOnAction(ev -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Delete Account");
                confirm.setContentText("Are you sure you want to delete your account?");
                confirm.showAndWait();
            });

            buttonBox.getChildren().addAll(title, addressBtn, voucherBtn, deleteBtn);
            root.setCenter(buttonBox);
        });

        layout.getChildren().addAll(
            nameField, flatField, areaField, landmarkField,
            pincodeField, cityField, stateField, contactField,
            saveButton, backButton
        );

        return layout;
    }
}
