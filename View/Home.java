package View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.projectf.Controller.MainApp;

//import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class Home {
     private StackPane root;
    private BorderPane layout;
    private HBox navBar;
    private Scene scene;
    private Stage mainStage;
    private MainApp mainApp;
    private File currentPdfFile;
    private File currentImageFile;
    private PDDocument document;
    private PDFRenderer pdfRenderer;
    private Pagination pagination;
    private VBox notificationPanel;
    private boolean notificationsVisible = false;
    private List<String> notifications = new ArrayList<>();

    public void start(Stage primaryStage, MainApp mainApp) {
        this.mainStage = primaryStage;
        this.mainApp = mainApp;

         notifications.add("Your print order #1234 is ready");
        notifications.add("New promotion: 20% off color prints");
        notifications.add("System maintenance scheduled for tomorrow");

        // Create notification panel
        notificationPanel = createNotificationPanel();

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
        bellIcon.setOnMouseClicked(e -> {
            notificationsVisible = !notificationsVisible;
            notificationPanel.setVisible(notificationsVisible);
            
            // Position the notification panel below the bell icon
            if (notificationsVisible) {
                Bounds bounds = bellIcon.localToScreen(bellIcon.getBoundsInLocal());
                notificationPanel.setLayoutX(bounds.getMinX() - 250);
                notificationPanel.setLayoutY(bounds.getMaxY());
            }
        });

        ImageView profileIcon = new ImageView(new Image("/Assests/Images/user.png"));
        profileIcon.setFitHeight(30);
        profileIcon.setFitWidth(30);
        profileIcon.setOnMouseClicked(e -> {
            UserPage userPage = new UserPage();
            Scene profileScene = userPage.getScene(mainStage, () -> start(mainStage, mainApp));
            mainStage.setScene(profileScene);
        });

        HBox topBar = new HBox(profileIcon);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(10));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox logoBox = new HBox(10, logoLeft, spacer, bellIcon, profileIcon);
        logoBox.setAlignment(Pos.TOP_LEFT);

        // Upload Section
        Label uploadLabel = new Label("Upload Files To Order Printouts");
        uploadLabel.setStyle("-fx-font-weight: bold; ; -fx-text-fill: rgb(107, 174, 213); -fx-padding: 10;");
        uploadLabel.setFont(Font.font(20));

        GridPane fileGrid = new GridPane();
        fileGrid.setHgap(20);
        fileGrid.setVgap(20);
        fileGrid.setAlignment(Pos.CENTER);

        String[] files = {"All Files", "Gallery", "Camera", "PDF"};
        String[] icons = {
                "/Assests/Images/open-folder.png",
                "/Assests/Images/gallery.png",
                "/Assests/Images/camera.png",
                "/Assests/Images/pdf-file.png"
        };

        for (int i = 0; i < files.length; i++) {
            ImageView iconView = new ImageView(new Image(icons[i]));
            iconView.setFitHeight(40);
            iconView.setFitWidth(40);

            Button fileBtn = new Button(files[i]);
            fileBtn.setPrefWidth(80);

            final int buttonIndex = i;
            fileBtn.setOnAction(e -> {
                switch (files[buttonIndex]) {
                    case "All Files":
                    case "Gallery":  // Combined case for both All Files and Gallery
                        FileChooser allChooser = new FileChooser();
                        allChooser.setTitle(files[buttonIndex].equals("Gallery") ? "Select from Gallery" : "Select Any File");
                        allChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "."),
                            new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                            new FileChooser.ExtensionFilter("Image Files", ".jpg", ".png", "*.jpeg")
                        );
                        File allFile = allChooser.showOpenDialog(mainStage);
                        if (allFile != null) {
                            if (allFile.getName().toLowerCase().endsWith(".pdf")) {
                                currentPdfFile = allFile;
                                try {
                                    if (document != null) document.close();
                                    document = PDDocument.load(allFile);
                                    pdfRenderer = new PDFRenderer(document);
                                    showPdfViewer();
                                } catch (IOException ex) {
                                    showAlert("Could not open PDF: " + ex.getMessage());
                                }
                            } else if (isImageFile(allFile)) {
                                currentImageFile = allFile;
                                showImageViewer();
                            } else {
                                System.out.println(files[buttonIndex] + " selected: " + allFile.getAbsolutePath());
                                showAlert("Selected File:\n" + allFile.getName());
                            }
                        }
                        break;

                    case "Camera":
                        showAlert("Camera feature not yet implemented.\nYou can integrate webcam code here.");
                        System.out.println("Camera feature - placeholder click.");
                        break;

                    case "PDF":
                        showPdfViewer();
                        break;

                    default:
                        showAlert("Unknown file type selected!");
                        break;
                }
            });

            VBox fileBox = new VBox(10, iconView, fileBtn);
            fileBox.setAlignment(Pos.CENTER);
            fileGrid.add(fileBox, i % 4, i / 4);
        }

        VBox uploadBox = new VBox(30, uploadLabel, fileGrid);
        uploadBox.setStyle("-fx-border-color: black; -fx-border-radius: 5; ");
        uploadBox.setPadding(new Insets(15));
        uploadBox.setAlignment(Pos.CENTER);
        VBox.setMargin(uploadBox, new Insets(30, 40, 30, 40));

        // Pricing Section
        VBox blackPrice = new VBox(10,
                new Label("Black"),
                new Label("₹0.50"),
                new Label("Dual-side")
        );
        blackPrice.getChildren().forEach(node -> ((Label) node).setFont(Font.font("Arial", FontWeight.BOLD, 20)));
        blackPrice.setAlignment(Pos.CENTER);
        blackPrice.setPadding(new Insets(15));
        blackPrice.setStyle("-fx-border-color: black; -fx-border-radius: 20;");

        VBox colorPrice = new VBox(10,
                new Label("Colour"),
                new Label("₹3.00"),
                new Label("Single Page")
        );
        colorPrice.getChildren().forEach(node -> ((Label) node).setFont(Font.font("Arial", FontWeight.BOLD, 20)));
        colorPrice.setAlignment(Pos.CENTER);
        colorPrice.setPadding(new Insets(15));
        colorPrice.setStyle("-fx-border-color: black; -fx-border-radius: 20;");

        HBox priceBox = new HBox(20, blackPrice, colorPrice);
        priceBox.setAlignment(Pos.CENTER);
        VBox.setMargin(priceBox, new Insets(15, 0, 0, 0));

        // Pincode Section
        Label deliveryLabel = new Label("Worry About Delivery Charge? Let's Check");
        deliveryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        deliveryLabel.setStyle("-fx-text-fill:Black");

        TextField pincodeField = new TextField();
        pincodeField.setPromptText("Enter Pincode For Delivery Charge");
        pincodeField.setMaxWidth(300);

        Button searchBtn = new Button("SEARCH");
        Button resetBtn = new Button("RESET");

        Label pinDisplay = new Label();
        pinDisplay.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        pinDisplay.setTextFill(Color.BLACK);

        searchBtn.setOnAction(e -> {
            String enteredPin = pincodeField.getText().trim();
            boolean isValidPin = enteredPin.matches("\\d{6}");
            if (isValidPin) {
                pinDisplay.setText("Delivery charge for Pincode " + enteredPin + " is 5.0/-");
            } else {
                pinDisplay.setText("Enter a valid 6-digit pincode!!!");
            }
        });

        resetBtn.setOnAction(e -> {
            pincodeField.clear();
            pinDisplay.setText("");
        });

        HBox pincodeInput = new HBox(30, pincodeField, searchBtn, resetBtn);
        pincodeInput.setAlignment(Pos.CENTER);

        VBox pincodeBox = new VBox(30, deliveryLabel, pincodeInput, pinDisplay);
        pincodeBox.setPadding(new Insets(15));
        pincodeBox.setAlignment(Pos.CENTER);
        pincodeBox.setSpacing(10);
        pincodeBox.setStyle("-fx-border-color: black; -fx-border-radius: 5; ");
        VBox.setMargin(pincodeBox, new Insets(30, 40, 30, 40));

        // Navigation bar
        Button homeBtn = new Button("Home");
        Button ordersBtn = new Button("Orders");
        Button explorebtn = new Button("Explore");
        Button referBtn = new Button("Refer");

        for (Button btn : Arrays.asList(homeBtn, ordersBtn, explorebtn, referBtn)) {
            btn.setPrefWidth(100);
            btn.setStyle("-fx-background-color: #37a9dbff; -fx-text-fill: white; -fx-background-radius: 20;");
        }

        homeBtn.setOnAction(e -> mainApp.showHomepage());
        ordersBtn.setOnAction(e -> mainApp.showOrdersPage());
        referBtn.setOnAction(e -> mainApp.showReferPage());
        explorebtn.setOnAction(e -> mainApp.showExplorePage());

        navBar = new HBox(30, homeBtn, ordersBtn, explorebtn, referBtn);
        navBar.setAlignment(Pos.CENTER);
        navBar.setPadding(new Insets(20));

        VBox mainContent = new VBox(10, logoBox, uploadBox, priceBox, pincodeBox);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.TOP_CENTER);

        layout = new BorderPane();
        layout.setCenter(mainContent);
        layout.setBottom(navBar);

        root = new StackPane(layout, notificationPanel);

        Image bgImage = new Image(getClass().getResource("/Assests/Images/background.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(
                bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        root.setBackground(new Background(backgroundImage));

        scene = new Scene(root, 1000, 750);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PrintWise UI");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private VBox createNotificationPanel() {
        VBox notificationBox = new VBox(10);
        notificationBox.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 1;");
        notificationBox.setPadding(new Insets(10));
        notificationBox.setPrefWidth(300);
        notificationBox.setVisible(false);
        
        Label title = new Label("Notifications");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        
        // Add notification items
        for (String notification : notifications) {
            Label notifLabel = new Label(notification);
            notifLabel.setWrapText(true);
            notifLabel.setMaxWidth(280);
            notificationBox.getChildren().add(notifLabel);
        }
        
        // Add close button
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> {
            notificationBox.setVisible(false);
            notificationsVisible = false;
        });
        
        notificationBox.getChildren().addAll(title, closeBtn);
        return notificationBox;
    }

    public void addNotification(String message) {
        notifications.add(message);
        refreshNotificationPanel();
    }

    private void refreshNotificationPanel() {
        notificationPanel.getChildren().clear();
        
        Label title = new Label("Notifications");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        notificationPanel.getChildren().add(title);
        
        for (String notification : notifications) {
            Label notifLabel = new Label(notification);
            notifLabel.setWrapText(true);
            notifLabel.setMaxWidth(280);
            notificationPanel.getChildren().add(notifLabel);
        }
        
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> {
            notificationPanel.setVisible(false);
            notificationsVisible = false;
        });
        notificationPanel.getChildren().add(closeBtn);
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
    }

    private void showImageViewer() {
        ImageViewerPane imageViewer = new ImageViewerPane(mainStage, () -> start(mainStage, mainApp));
        layout.setCenter(imageViewer);
        layout.setBottom(navBar);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showPdfViewer() {
        PdfViewerPane pdfViewer = new PdfViewerPane(mainStage, () -> start(mainStage, mainApp));
        layout.setCenter(pdfViewer);
        layout.setBottom(navBar);
    }

    class ImageViewerPane extends VBox {
        private String selectedSize = "A4";
        private int copies = 1;

        ImageViewerPane(Stage stage, Runnable onBack) {
            setAlignment(Pos.CENTER);
            setPadding(new Insets(10));
            setSpacing(10);

            Button openButton = new Button("Open Image");
            Button downloadButton = new Button("Download");
            Button printButton = new Button("Print File");
            Button backButton = new Button("Back to Home");
        

           /* Label copiesLabel = new Label("Copies:");
            ComboBox<Integer> copiesComboBox = new ComboBox<>();
            copiesComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            copiesComboBox.setValue(1);
            copiesComboBox.setOnAction(e -> copies = copiesComboBox.getValue());/* */

           

            HBox topBar = new HBox(10, openButton, downloadButton, printButton, backButton);
            topBar.setAlignment(Pos.CENTER);

    

           // HBox copiesBar = new HBox(10, copiesLabel, copiesComboBox);
           // copiesBar.setAlignment(Pos.CENTER);

            StackPane imageContainer = new StackPane();
            imageContainer.setAlignment(Pos.CENTER);
            imageContainer.setPadding(new Insets(10));

            ImageView imageView = new ImageView();
            if (currentImageFile != null) {
                Image image = new Image(currentImageFile.toURI().toString());
                imageView.setImage(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(500);
            }

            imageContainer.getChildren().add(imageView);

            ScrollPane scrollPane = new ScrollPane(imageContainer);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setPrefViewportWidth(500);
            scrollPane.setPrefViewportHeight(500);

            openButton.setOnAction(e -> openImage(stage));
            downloadButton.setOnAction(e -> downloadImage(stage));
            printButton.setOnAction(e -> printImage(stage));
            backButton.setOnAction(e -> onBack.run());

            getChildren().addAll(topBar, scrollPane);
        }

        private void openImage(Stage stage) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".jpg", ".png", "*.jpeg")
            );
            File file = fileChooser.showOpenDialog(stage);
            if (file != null && isImageFile(file)) {
                currentImageFile = file;
                Image image = new Image(file.toURI().toString());
                ((ImageView)((ScrollPane)getChildren().get(3)).getContent()).setImage(image);
            }
        }

        private void downloadImage(Stage stage) {
            if (currentImageFile == null) {
                showAlert("No image file open");
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image As");
            fileChooser.setInitialFileName(currentImageFile.getName());
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
            );
            File destination = fileChooser.showSaveDialog(stage);

            if (destination != null) {
                try {
                    Files.copy(currentImageFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    showAlert("Downloaded to: " + destination.getAbsolutePath());
                } catch (IOException e) {
                    showAlert("Download failed: " + e.getMessage());
                }
            }
        }

        private void printImage(Stage stage) {
            if (currentImageFile == null) {
                showAlert("No image file selected for printing");
                return;
            }

            // Show print options dialog
            Alert printDialog = new Alert(Alert.AlertType.CONFIRMATION);
            printDialog.setTitle("Print Options");
            printDialog.setHeaderText("Print Options for " + currentImageFile.getName());
            
            // Create print options UI
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            Label sizeLabel = new Label("Paper Size:");
            ComboBox<String> sizeCombo = new ComboBox<>();
            sizeCombo.getItems().addAll("A4", "A4", "A5" ,"A2");
            sizeCombo.setValue(selectedSize);
            
            Label copiesLabel = new Label("Copies:");
            Spinner<Integer> copiesSpinner = new Spinner<>(1, 100, copies);
            
            Label colorLabel = new Label("Print Color:");
            ComboBox<String> colorCombo = new ComboBox<>();
            colorCombo.getItems().addAll("Black & White", "Color");
            colorCombo.setValue("Black & White");
            
            grid.addRow(0, sizeLabel, sizeCombo);
            grid.addRow(1, copiesLabel, copiesSpinner);
            grid.addRow(2, colorLabel, colorCombo);
            
            printDialog.getDialogPane().setContent(grid);
            
            // Add custom buttons
            printDialog.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            
            printDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Get selected options
                    selectedSize = sizeCombo.getValue();
                    copies = copiesSpinner.getValue();
                    String colorMode = colorCombo.getValue();
                    
                    // Calculate price
                    double pricePerPage = colorMode.equals("Color") ? 3.00 : 0.50;
                    double totalPrice = pricePerPage * copies;
                    
                    // Show confirmation
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Print Order Confirmation");
                    confirmation.setHeaderText("Print Order Submitted");
                    confirmation.setContentText(String.format(
                        "File: %s\nSize: %s\nCopies: %d\nColor: %s\nTotal Price: ₹%.2f",
                        currentImageFile.getName(),
                        selectedSize,
                        copies,
                        colorMode,
                        totalPrice
                    ));
                    confirmation.showAndWait();
                    
                    // In a real application, you would send this to a print service
                    System.out.println("Printing: " + currentImageFile.getName());
                    
                    // Add to notifications
                    addNotification(String.format(
                        "Print order for %s (%d copies) submitted",
                        currentImageFile.getName(),
                        copies
                    ));
                }
            });
        }
    }

    class PdfViewerPane extends VBox {
        private String selectedSize = "A4";
        private int copies = 1;
        private ImageView pdfImageView = new ImageView();
        private ScrollPane scrollPane = new ScrollPane();

        PdfViewerPane(Stage stage, Runnable onBack) {
            setAlignment(Pos.CENTER);
            setPadding(new Insets(10));
            setSpacing(10);

            Button openButton = new Button("Open PDF");
            Button downloadButton = new Button("Download");
            Button printButton = new Button("Print File");
            Button backButton = new Button("Back to Home");

            HBox topBar = new HBox(10, openButton, downloadButton, printButton, backButton);
            topBar.setAlignment(Pos.CENTER);


            pdfImageView.setPreserveRatio(true);
            pdfImageView.setFitWidth(300);

            scrollPane.setContent(pdfImageView);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefViewportWidth(300);
            scrollPane.setPrefViewportHeight(500);

            pagination = new Pagination(1, 0);
            pagination.setPageFactory(this::createPage);

            openButton.setOnAction(e -> openPdf(stage));
            downloadButton.setOnAction(e -> downloadPdf(stage));
            printButton.setOnAction(e -> printPdf(stage));
            backButton.setOnAction(e -> {
                if (document != null) {
                    try {
                        document.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                onBack.run();
            });
            getChildren().addAll(topBar, scrollPane, pagination);
        }

        private VBox createPage(int pageIndex) {
            VBox pageBox = new VBox();
            pageBox.setAlignment(Pos.CENTER);
            if (document == null || pdfRenderer == null) {
                pageBox.getChildren().add(new Label("No PDF loaded"));
                return pageBox;
            }

            try {
                BufferedImage bufferedImage = pdfRenderer.renderImage(pageIndex, 1.5f);
              Image fxImg = SwingFXUtils.toFXImage(bufferedImage, null);
                ImageView dynamicImageView = new ImageView(fxImg);
                dynamicImageView.setPreserveRatio(true);
                dynamicImageView.setFitWidth(300);
                pageBox.getChildren().add(dynamicImageView);
            } catch (IOException e) {
                pageBox.getChildren().add(new Label("Error loading page: " + e.getMessage()));
            }

            return pageBox;
        }

        private void openPdf(Stage stage) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                currentPdfFile = file;
                try {
                    if (document != null) document.close();
                    document = PDDocument.load(file);
                    pdfRenderer = new PDFRenderer(document);
                    pagination.setPageCount(document.getNumberOfPages());
                } catch (IOException e) {
                    showAlert("Could not open PDF: " + e.getMessage());
                }
            }
        }

        private void downloadPdf(Stage stage) {
            if (currentPdfFile == null) {
                showAlert("No PDF file open");
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF As");
            fileChooser.setInitialFileName(currentPdfFile.getName());
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File destination = fileChooser.showSaveDialog(stage);

            if (destination != null) {
                try {
                    Files.copy(currentPdfFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    showAlert("Downloaded to: " + destination.getAbsolutePath());
                } catch (IOException e) {
                    showAlert("Download failed: " + e.getMessage());
                }
            }
        }

        private void printPdf(Stack stage) {
            if (currentPdfFile == null || document == null) {
                showAlert("No PDF file selected for printing");
                return;
            }

            // Show print options dialog
            Alert printDialog = new Alert(Alert.AlertType.CONFIRMATION);
            printDialog.setTitle("Print Options");
            printDialog.setHeaderText("Print Options for " + currentPdfFile.getName());
            
            // Create print options UI
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            Label sizeLabel = new Label("Paper Size:");
            ComboBox<String> sizeCombo = new ComboBox<>();
            sizeCombo.getItems().addAll("A4", "A3", "A5","A6");
            sizeCombo.setValue(selectedSize);
            
            Label copiesLabel = new Label("Copies:");
            Spinner<Integer> copiesSpinner = new Spinner<>(1, 100, copies);
            
            Label colorLabel = new Label("Print Color:");
            ComboBox<String> colorCombo = new ComboBox<>();
            colorCombo.getItems().addAll("Black & White", "Color");
            colorCombo.setValue("Black & White");
            
            Label pagesLabel = new Label("Pages:");
            TextField pagesField = new TextField("1-" + document.getNumberOfPages());
            
            grid.addRow(0, sizeLabel, sizeCombo);
            grid.addRow(1, copiesLabel, copiesSpinner);
            grid.addRow(2, colorLabel, colorCombo);
            grid.addRow(3, pagesLabel, pagesField);
            
            printDialog.getDialogPane().setContent(grid);
            
            // Add custom buttons
            printDialog.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            
            printDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Get selected options
                    selectedSize = sizeCombo.getValue();
                    copies = copiesSpinner.getValue();
                    String colorMode = colorCombo.getValue();
                    String pages = pagesField.getText();
                    
                    // Calculate price
                    double pricePerPage = colorMode.equals("Color") ? 3.00 : 0.50;
                    int pageCount = document.getNumberOfPages(); // Simple count - in reality should parse pagesField
                    double totalPrice = pricePerPage * pageCount * copies;
                    
                    // Show confirmation
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Print Order Confirmation");
                    confirmation.setHeaderText("Print Order Submitted");
                    confirmation.setContentText(String.format(
                        "File: %s\nSize: %s\nPages: %s\nCopies: %d\nColor: %s\nTotal Price: ₹%.2f",
                        currentPdfFile.getName(),
                        selectedSize,
                        pages,
                        copies,
                        colorMode,
                        totalPrice
                    ));
                    confirmation.showAndWait();
                    
                    // In a real application, you would send this to a print service
                    System.out.println("Printing: " + currentPdfFile.getName());
                    
                    // Add to notifications
                    addNotification(String.format(
                        "Print order for %s (%d pages, %d copies) submitted",
                        currentPdfFile.getName(),
                        pageCount,
                        copies
                    ));
                }
            });
        }
    }

  
}


