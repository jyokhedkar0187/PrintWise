package View;

import javax.swing.text.html.ImageView;

public class LoginView {
    
  private Stage loginStage;
    private MainApp mainApp;

    private Scene loginScene;
    private Scene signupScene;
   // private Scene AdminHomeScene;
    private Scene forgotPasswordScene;

    private logincontroller authController = new logincontroller();

    
    private StackPane wrapWithBackground(Pane content, String imagePath) {
        Image bgImage = new Image(imagePath);
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(1000);
        bgView.setFitHeight(800);
        bgView.setPreserveRatio(false);
        bgView.setOpacity(1.0);
        StackPane stack = new StackPane();
        
bgView.fitWidthProperty().bind(stack.widthProperty());
bgView.fitHeightProperty().bind(stack.heightProperty());

          stack.getChildren().addAll(bgView, content);
    
        stack.setAlignment(Pos.CENTER);
        return stack;
    }

    public void start(Stage primaryStage, MainApp mainApp) {
        this.loginStage = primaryStage;
        this.mainApp = mainApp;
     // App Logo and Title
        Image logo = new Image("/Assests/Images/pw3.jpg");
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        Label title = new Label("PrintWise");
        title.setFont(Font.font("Verdana", FontPosture.ITALIC, 24));
        title.setTextFill(Color.BLACK);
        Label taglinLabel = new Label("Print It Your Way—with PrintWise.");
        taglinLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 18));
        taglinLabel.setTextFill(Color.BLACK);
         // Login Fields
        Label userLabel = new Label("Username");
        userLabel.setFont(Font.font(20));
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Your Username");
        usernameField.setMaxWidth(200);

        Label passLabel = new Label("Password");
        passLabel.setFont(Font.font(20));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Your Password");
        passwordField.setMaxWidth(200);

        // Role Toggle
        Label roleLabel = new Label("Login as:");
        RadioButton userRadio = new RadioButton("User");
        RadioButton adminRadio = new RadioButton("Admin");
        ToggleGroup roleGroup = new ToggleGroup();
        userRadio.setToggleGroup(roleGroup);
        adminRadio.setToggleGroup(roleGroup);
        userRadio.setSelected(true);
        HBox roleBox = new HBox(20, userRadio, adminRadio);
        roleBox.setAlignment(Pos.CENTER);

        // Buttons
        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #056a8bff; -fx-text-fill: white;");
        Button signupBtn = new Button("Sign Up");
        signupBtn.setStyle("-fx-background-color: #056a8bff; -fx-text-fill: white;");
        Hyperlink forgotLink = new Hyperlink("Forgot Password???");
        forgotLink.setTextFill(Color.BLACK);
        forgotLink.setOnAction(e -> {
            initializeForgotPasswordPage();
            loginStage.setScene(forgotPasswordScene);
        });

        // Login Button Handler
        loginBtn.setOnAction(e -> {
            String email = usernameField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                showAlert(AlertType.WARNING, "Please fill in all fields.");
                return;
            }

            boolean success = authController.signInWithEmailAndPassword(email, password);
            if (success) {
                Toggle selectedToggle = roleGroup.getSelectedToggle();
                if (selectedToggle == null) {
                    showAlert(AlertType.WARNING, "Please select a role.");
                    return;
                }
                String role = ((RadioButton) selectedToggle).getText();
                if (role.equals("User")) {
                    mainApp.showHomepage();
                } else {
                    mainApp.showAdmin();
                  // initializeAdminDashboard();
                  //loginStage.setScene(adminDashboardScene);
                }
            } else {
                showAlert(AlertType.ERROR, "Login failed. Please check your credentials.");
            }
        });

        // Sign Up Handler
        signupBtn.setOnAction(e -> {
            initializeSignUpPage();
            loginStage.setScene(signupScene);
        });

        // Login Layout
        VBox inputBox = new VBox(10, userLabel, usernameField, passLabel, passwordField, roleLabel, roleBox, loginBtn, signupBtn, forgotLink);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(20));

        VBox centerBox = new VBox(20, logoView, title, inputBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setCenter(centerBox);

        //StackPane backgroundRoot = wrapWithBackground(root, "/Assests/Images/background.jpg");
         StackPane backgroundRoot = wrapWithBackground (root,"/Assests/Images/tanu.jpg");
        loginScene = new Scene(backgroundRoot,1000,800);

        loginStage.setScene(loginScene);
        loginStage.setTitle("PrintWise Login");
        loginStage.setFullScreen(true); 
        loginStage.show();



    }


    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ------ Sign Up Scene -------
    public void initializeSignUpPage() {
             //public Scene getSignupScene() {
   // return signupScene;

        Image logo = new Image("/Assests/Images/logo.jpg");
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        Label signupTitle = new Label("Create Your Account");
        signupTitle.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        signupTitle.setTextFill(Color.DARKBLUE);

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        nameField.setMaxWidth(200);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        phoneField.setMaxWidth(200);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);

        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setMaxWidth(200);

        TextField pinCodeField = new TextField();
        pinCodeField.setPromptText("Pincode");
        pinCodeField.setMaxWidth(200);

        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("Create Password");
        newPassword.setMaxWidth(200);

        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm Password");
        confirmPassword.setMaxWidth(200);

        Label roleLabel = new Label("Sign up as:");
        RadioButton userRadio = new RadioButton("User");
        RadioButton adminRadio = new RadioButton("Admin");
        ToggleGroup roleToggle = new ToggleGroup();
        userRadio.setToggleGroup(roleToggle);
        adminRadio.setToggleGroup(roleToggle);
        userRadio.setSelected(true);

        HBox roleBox = new HBox(20, userRadio, adminRadio);
        roleBox.setAlignment(Pos.CENTER);

        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color: #056a8bff; -fx-text-fill: white;");
        submitBtn.setOnAction(e -> {
           
            if (
                nameField.getText().isEmpty() || phoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
                addressField.getText().isEmpty() || pinCodeField.getText().isEmpty() ||
                newPassword.getText().isEmpty() || confirmPassword.getText().isEmpty()
            ) {
                showAlert(AlertType.ERROR, "Please fill in all fields.");
                return;
            }
            if (!newPassword.getText().equals(confirmPassword.getText())) {
                showAlert(AlertType.ERROR, "Passwords do not match.");
                return;
            }
            showAlert(AlertType.INFORMATION, "Registration Successful as " + ((RadioButton)roleToggle.getSelectedToggle()).getText());
            loginStage.setScene(loginScene);
        });

        Button backBtn = new Button("Back to Login");
        backBtn.setStyle("-fx-background-color: #056a8bff; -fx-text-fill: white;");
        backBtn.setOnAction(e -> loginStage.setScene(loginScene));

        VBox signupBox = new VBox(10, logoView, signupTitle, nameField, phoneField, emailField,
                addressField, pinCodeField, newPassword, confirmPassword, roleLabel, roleBox, submitBtn, backBtn);
        signupBox.setAlignment(Pos.CENTER);
        signupBox.setPadding(new Insets(20));
        BorderPane signupLayout = new BorderPane(signupBox);
        signupScene = new Scene(wrapWithBackground(signupLayout, "/Assests/Images/background.jpg"), 1000, 800);

   

    }


    // ----- Forgot Password Page -------
    private void initializeForgotPasswordPage() {
        Image logo = new Image("/Assests/Images/logo.jpg");
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);

        Label resetTitle = new Label("Reset Your Password");
        resetTitle.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        resetTitle.setTextFill(Color.DARKMAGENTA);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter Your Registered Mobile Number");
        phoneField.setMaxWidth(250);

        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("Create new Password");
        newPassword.setMaxWidth(250);

        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm Password");
        confirmPassword.setMaxWidth(250);

        Button resetBtn = new Button("Set Password");
        resetBtn.setStyle("-fx-background-color: #056a8bff; -fx-text-fill: white;");
        resetBtn.setOnAction(e -> {
            showAlert(AlertType.INFORMATION, "Your password has been set!\nGo to Login Page " + phoneField.getText());
            loginStage.setScene(loginScene);
        });
        
        Button backBtn = new Button("Back to Login");
        backBtn.setStyle("-fx-background-color: #056a8bff; -fx-text-fill: white;");
        backBtn.setOnAction(e -> loginStage.setScene(loginScene));

        VBox forgotBox = new VBox(15, logoView, resetTitle, phoneField, newPassword, confirmPassword, resetBtn, backBtn);
        forgotBox.setAlignment(Pos.CENTER);
        forgotBox.setPadding(new Insets(20));

        BorderPane forgotLayout = new BorderPane(forgotBox);
        forgotPasswordScene = new Scene(wrapWithBackground(forgotLayout, "/Assests/Images/background.jpg"), 1000, 800);
    }
         public Scene getSignupScene() {
   return signupScene;
         }
         public Scene getScene(Stage primaryStage) {
    
            if (loginScene == null) {
               start(primaryStage, mainApp != null ? mainApp : new MainApp());
            }
            return loginScene; 
       }

     
}
