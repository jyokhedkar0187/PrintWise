package com.projectf.view;

import com.projectf.Controller.MainApp;
import com.projectf.Model.PrintOrder;
import com.projectf.configuration.FirebaseInit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.InputStream;
import java.nio.file.*;
import java.awt.Desktop;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.pdfbox.pdmodel.PDDocument;
//import com.projectf.model.PrintOrder;
//import com.projectf.utils.FirebaseInit;
import com.google.firebase.database.FirebaseDatabase;

public class Explore {
    private Stage mainStage;


    private TextField searchBox;
    private ListView<PrintableItem> listView;
    private ObservableList<PrintableItem> fullList;
    private ObservableList<PrintableItem> filteredList;
    private String selectedSize = "A4";
    private int copies = 1;

    // Mapping Year -> (Branch -> List of Files)
    private static final Map<String, Map<String, List<FileReference>>> FILES_MAP = new HashMap<>();

    static {
        initializeFileReferences();
    }

    private static void initializeFileReferences() {
        Map<String, List<FileReference>> firstYear = new HashMap<>();
        firstYear.put("CSE", Arrays.asList(
                new FileReference("AI NOTES", "/Assests/files/AI - Unit 4 (Knowledge) (2).pdf", "PDF"),
                new FileReference("LOGO", "/Assests/Images/Printwise.jpg", "Image")
        ));
        firstYear.put("IT", Arrays.asList(
                new FileReference("CC ", "/Assests/files/CC - Unit 4 (Cloud Platforms and Cloud Applications).pdf", "PDF")
        ));
        firstYear.put("ENTC", Arrays.asList(
                new FileReference("DSBDA", "/Assests/files/DSBDA - Unit 6 (Data Visualization and Hadoop) - PPT.pdf", "PDF")
        ));
        firstYear.put("MECH", Arrays.asList(
                new FileReference("WT NOTES", "/Assests/files/unit 4 wt short notes.pdf", "PDF")
        ));
        FILES_MAP.put("FIRST YEAR", firstYear);

        Map<String, List<FileReference>> secondYear = new HashMap<>();
        secondYear.put("CSE", Arrays.asList(
                new FileReference("CC pyq", "/Assests/files/CC PYQ 2022 to 24.pdf", "PDF")
        ));
        secondYear.put("ENTC", Arrays.asList(
                new FileReference("Circuits", "/files/circuits.pdf", "PDF")
        ));
        secondYear.put("MECH", Arrays.asList(
                new FileReference("Thermodynamics", "/files/thermodynamics.pdf", "PDF")
        ));
        secondYear.put("IT", Arrays.asList(
                new FileReference("Networking", "/files/networking.pdf", "PDF")
        ));
        FILES_MAP.put("SECOND YEAR", secondYear);

        Map<String, List<FileReference>> thirdYear = new HashMap<>();
        thirdYear.put("CSE", Arrays.asList(
                new FileReference("TE PYQ", "/Assests/files/T.E nov 2024 PYQ.pdf", "PDF")
        ));
        thirdYear.put("IT", Arrays.asList(
                new FileReference("Network Security", "/files/security.pdf", "PDF")
        ));
        thirdYear.put("ENTC", Arrays.asList(
                new FileReference("Signal Processing", "/files/signal_processing.pdf", "PDF")
        ));
        thirdYear.put("MECH", Arrays.asList(
                new FileReference("Fluid Mechanics", "/files/fluid_mechanics.pdf", "PDF")
        ));
        FILES_MAP.put("THIRD YEAR", thirdYear);
    }

    public void start(Stage primaryStage, MainApp mainApp) {
        try {
            initializeUI(primaryStage, mainApp);
        } catch (Exception e) {
            showErrorAlert("Failed to initialize Explore screen", e);
        }
    }

    private void initializeUI(Stage primaryStage, MainApp mainApp) {
        fullList = FXCollections.observableArrayList(
                new PrintableItem("FIRST YEAR", "CSE", "All Subjects", "/Assests/Images/first-year.png"),
                new PrintableItem("SECOND YEAR", "ENTC", "Core Subjects", "/Assests/Images/two.png"),
                new PrintableItem("THIRD YEAR", "CSE", "Specializations", "/Assests/Images/3rd-place.png"),
                new PrintableItem("FOURTH YEAR", "ALL DEPT", "Placement Material", "/Assests/Images/programming.png")
        );
        filteredList = FXCollections.observableArrayList(fullList);

        BorderPane root = new BorderPane();
        root.setTop(createTopBar(mainApp));
        root.setCenter(createCenterContent());
        root.setBottom(createNavigationBar(mainApp));

        setBackground(root);

        Scene scene = new Scene(root, 1000, 750);
        primaryStage.setTitle("PrintWise - Explore");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private HBox createTopBar(MainApp mainApp) {
        ImageView backIcon = loadImage("/Assests/Images/back.png", 20, 20);
        backIcon.setOnMouseClicked(e -> mainApp.showHomepage());

        Label titleLabel = new Label("Explore");
        titleLabel.setFont(new Font(18));

        HBox topBar = new HBox(10, backIcon, titleLabel);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));

        return topBar;
    }

    private VBox createCenterContent() {
        searchBox = new TextField();
        searchBox.setPromptText("Search Printables...");
        searchBox.setStyle("-fx-background-color: transparent;");

        ImageView searchIcon = loadImage("/Assests/Images/search.png", 20, 20);
        HBox searchBar = new HBox(5, searchIcon, searchBox);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(5, 10, 5, 10));
        searchBar.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #f0f0f0;");
        HBox.setHgrow(searchBox, Priority.ALWAYS);

        Button facultyBtn = createFilterButton("Faculty", "/Assests/Images/teacher.png");
        Button yearBtn = createFilterButton("Year", "/Assests/Images/engineer.png");
        Button subjectBtn = createFilterButton("Subject", "/Assests/Images/books.png");
        Button departmentBtn = createFilterButton("Department", "/Assests/Images/first-year.png");

        Button resetBtn = new Button("Reset");
        resetBtn.setGraphic(loadImage("/Assests/Images/reset.png", 16, 16));
        resetBtn.setStyle("-fx-border-color: #ccc; -fx-background-color: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        resetBtn.setOnAction(e -> resetFilters());

        HBox filterBar = new HBox(5, facultyBtn, yearBtn, subjectBtn, departmentBtn, resetBtn);
        filterBar.setAlignment(Pos.CENTER_LEFT);
        filterBar.setPadding(new Insets(0, 0, 10, 0));

        listView = new ListView<>(filteredList);
        listView.setCellFactory(param -> new PrintableItemCell());

        setupSearchFilter();

        VBox centerContainer = new VBox(10,
                new Label("Looking for:"),
                searchBar,
                filterBar,
                listView
        );
        centerContainer.setPadding(new Insets(10));
        VBox.setVgrow(listView, Priority.ALWAYS);

        return centerContainer;
    }

    private HBox createNavigationBar(MainApp mainApp) {
        Button homeBtn = createNavButton("Home", e -> mainApp.showHomepage());
        Button ordersBtn = createNavButton("Orders", e -> mainApp.showOrdersPage());
        Button exploreBtn = createNavButton("Explore", e -> {});
        Button referBtn = createNavButton("Refer", e -> mainApp.showReferPage());

        HBox navBar = new HBox(30, homeBtn, ordersBtn, exploreBtn, referBtn);
        navBar.setAlignment(Pos.CENTER);
        navBar.setPadding(new Insets(20));
        navBar.setStyle("-fx-background-color: #f0f0f0;");

        return navBar;
    }

    private Button createNavButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button btn = new Button(text);
        btn.setPrefWidth(100);
        btn.setStyle("-fx-background-color: #37a9dbff; -fx-text-fill: white; -fx-background-radius: 20;");
        btn.setOnAction(handler);
        return btn;
    }

    private Button createFilterButton(String text, String iconPath) {
        Button btn = new Button(text);
        btn.setGraphic(loadImage(iconPath, 12, 12));
        btn.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        return btn;
    }

    private void setBackground(BorderPane root) {
        try {
            InputStream is = getClass().getResourceAsStream("/Assests/Images/background.jpg");
            if (is != null) {
                BackgroundImage backgroundImage = new BackgroundImage(
                        new Image(is),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(1.0, 1.0, true, true, false, false)
                );
                root.setBackground(new Background(backgroundImage));
            } else {
                root.setStyle("-fx-background-color: #f5f5f5;");
            }
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #f5f5f5;");
        }
    }

    private ImageView loadImage(String path, double width, double height) {
        ImageView imageView = new ImageView();
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is != null) {
                Image img = new Image(is);
                imageView.setImage(img);
            } else {
                System.err.println("Image not found: " + path);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
        }
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private void setupSearchFilter() {
        searchBox.textProperty().addListener((obs, oldText, newText) -> {
            if (newText == null || newText.isEmpty()) {
                filteredList.setAll(fullList);
                return;
            }

            String search = newText.toLowerCase();
            filteredList.setAll(fullList.filtered(item ->
                    item.getTitle().toLowerCase().contains(search) ||
                            item.getSubtitle1().toLowerCase().contains(search) ||
                            item.getSubtitle2().toLowerCase().contains(search)
            ));
        });
    }

    private void resetFilters() {
        searchBox.clear();
        filteredList.setAll(fullList);
    }

    private void handleYearClick(String year) {
        Map<String, List<FileReference>> branchMap = FILES_MAP.get(year.toUpperCase());
        if (branchMap == null || branchMap.isEmpty()) {
            showAlert("No data available for " + year);
            return;
        }

        List<String> branches = new ArrayList<>(branchMap.keySet());
        ChoiceDialog<String> dialog = new ChoiceDialog<>(branches.get(0), branches);
        dialog.setTitle("Select Branch");
        dialog.setHeaderText("Select branch for " + year);
        dialog.setContentText("Branch:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(selectedBranch -> {
            List<FileReference> files = branchMap.get(selectedBranch);
            if (files == null || files.isEmpty()) {
                showAlert("No files available for " + selectedBranch);
            } else {
                showFilesDialog(year, selectedBranch, files);
            }
        });
    }

    private void showFilesDialog(String year, String branch, List<FileReference> files) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Files for " + year + " - " + branch);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        for (FileReference fr : files) {
            if (fr == null) continue;

            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.CENTER_LEFT);

            Label typeLabel = new Label(fr.getFileType() != null ? fr.getFileType() : "Unknown");
            Label nameLabel = new Label(fr.getName() != null ? fr.getName() : "Unnamed file");

            Button openBtn = new Button("Open");
            openBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5;");
            openBtn.setOnAction(e -> openFile(fr));

            Button printBtn = new Button("Print");
            printBtn.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-background-radius: 5;");
            printBtn.setOnAction(e -> printFile(fr));

            hbox.getChildren().addAll(typeLabel, nameLabel, openBtn, printBtn);
            vbox.getChildren().add(hbox);
        }

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private void openFile(FileReference fileRef) {
        if (fileRef == null || fileRef.getFileUrl() == null) {
            showAlert("Invalid file reference");
            return;
        }

        System.out.println("Attempting to open: " + fileRef.getFileUrl());

        try (InputStream is = getClass().getResourceAsStream(fileRef.getFileUrl())) {
            if (is == null) {
                showAlert("File not found in resources: " + fileRef.getFileUrl() +
                        "\nMake sure the file exists in your resources folder.");
                return;
            }

            switch (fileRef.getFileType().toUpperCase()) {
                case "IMAGE":
                    openImageFile(is);
                    break;
                case "PDF":
                    openPdfFile(is, fileRef.getName());
                    break;
                default:
                    showAlert("Unsupported file type: " + fileRef.getFileType());
            }
        } catch (Exception e) {
            showErrorAlert("Failed to open file: " + fileRef.getName(), e);
        }
    }

    private void openImageFile(InputStream is) {
        try {
            Image image = new Image(is);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(600);

            Stage imageStage = new Stage();
            imageStage.setScene(new Scene(new StackPane(imageView), 1000, 750));
            imageStage.setTitle("Image Viewer");
            imageStage.show();
        } catch (Exception e) {
            throw new RuntimeException("Failed to display image", e);
        }
    }

    private void openPdfFile(InputStream is, String fileName) {
        try {
            Path tempFile = Files.createTempFile("printwise-", ".pdf");
            Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(tempFile.toFile());
            } else {
                showAlert("Cannot open PDF automatically. File saved at: " + tempFile);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to open PDF: " + fileName, e);
        }
    }

    private void printFile(FileReference fileRef) {
        try (InputStream is = getClass().getResourceAsStream(fileRef.getFileUrl())) {
            if (is == null) {
                showAlert("File not found in resources: " + fileRef.getFileUrl());
                return;
            }
            
            switch (fileRef.getFileType().toUpperCase()) {
                case "IMAGE":
                    showPrintOptionsDialog(fileRef, is, null);
                    break;
                case "PDF":
                    PDDocument document = PDDocument.load(is);
                    showPrintOptionsDialog(fileRef, null, document);
                    break;
                default:
                    showAlert("Unsupported file type: " + fileRef.getFileType());
            }
        } catch (Exception e) {
            showErrorAlert("Failed to print file: " + fileRef.getName(), e);
        }
    }

    private void showPrintOptionsDialog(FileReference fileRef, InputStream imageStream, PDDocument pdfDocument) {
        Alert printDialog = new Alert(Alert.AlertType.CONFIRMATION);
        printDialog.initOwner(mainStage); 
        printDialog.setTitle("Print Options");
        printDialog.setHeaderText("Print Options for " + fileRef.getName());
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        Label sizeLabel = new Label("Paper Size:");
        ComboBox<String> sizeCombo = new ComboBox<>();
        sizeCombo.getItems().addAll("A4", "A3", "A5", "A6");
        sizeCombo.setValue(selectedSize);
        
        Label copiesLabel = new Label("Copies:");
        Spinner<Integer> copiesSpinner = new Spinner<>(1, 100, copies);
        
        Label colorLabel = new Label("Print Color:");
        ComboBox<String> colorCombo = new ComboBox<>();
        colorCombo.getItems().addAll("Black & White", "Color");
        colorCombo.setValue("Black & White");
        
        Label pagesLabel = new Label("Pages:");
        TextField pagesField = new TextField();
        
        if (pdfDocument != null) {
            pagesField.setText("1-" + pdfDocument.getNumberOfPages());
        } else {
            pagesField.setText("1");
            pagesField.setDisable(true);
        }
        
        grid.addRow(0, sizeLabel, sizeCombo);
        grid.addRow(1, copiesLabel, copiesSpinner);
        grid.addRow(2, colorLabel, colorCombo);
        grid.addRow(3, pagesLabel, pagesField);
        
        printDialog.getDialogPane().setContent(grid);
        printDialog.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        
        printDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                selectedSize = sizeCombo.getValue();
                copies = copiesSpinner.getValue();
                String colorMode = colorCombo.getValue();
                String pages = pagesField.getText();
                
                double pricePerPage = colorMode.equals("Color") ? 3.00 : 0.50;
                int pageCount = pdfDocument != null ? pdfDocument.getNumberOfPages() : 1;
                double totalPrice = pricePerPage * pageCount * copies;
                
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
              confirmation.initOwner(mainStage);
                confirmation.setTitle("Print Order Confirmation");
                confirmation.setHeaderText("Print Order Submitted");
                confirmation.setContentText(String.format(
                    "File: %s\nSize: %s\nPages: %s\nCopies: %d\nColor: %s\nTotal Price: ₹%.2f",
                    fileRef.getName(),
                    selectedSize,
                    pages,
                    copies,
                    colorMode,
                    totalPrice
                ));
                confirmation.showAndWait();
                
                // Upload to Firebase
                try {
                    FirebaseInit.init();
                    String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                    String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                    PrintOrder order = new PrintOrder ( orderId, fileRef.getName(), selectedSize,copies,colorMode, totalPrice, dateTime, "Completed"
                     );

                    FirebaseDatabase.getInstance().getReference("orders")
                        .child(orderId)
                        .setValueAsync(order);

                    System.out.println("Order uploaded to Firebase");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                // Close PDF document if it was opened
                if (pdfDocument != null) {
                    try {
                        pdfDocument.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message + "\n" + e.getMessage());
        alert.showAndWait();
        e.printStackTrace();
    }

    private class PrintableItemCell extends ListCell<PrintableItem> {
        @Override
        protected void updateItem(PrintableItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                ImageView img = new ImageView();
                try {
                    InputStream is = getClass().getResourceAsStream(item.getImageUrl());
                    if (is != null) {
                        img.setImage(new Image(is));
                    }
                } catch (Exception e) {
                    System.err.println("Error loading cell image: " + item.getImageUrl());
                }
                img.setFitWidth(80);
                img.setFitHeight(60);
                img.setPreserveRatio(false);

                Label title = new Label(item.getTitle());
                title.setStyle("-fx-font-weight: bold;");

                VBox textBox = new VBox(2, title,
                        new Label(item.getSubtitle1()),
                        new Label(item.getSubtitle2())
                );

                Button viewBtn = new Button("View");
                viewBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 5;");
                viewBtn.setOnAction(e -> {
                    if (item != null) {
                        handleYearClick(item.getTitle());
                    }
                });

                HBox spacer = new HBox();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                HBox cell = new HBox(10, img, textBox, spacer, viewBtn);
                cell.setPadding(new Insets(10));
                cell.setAlignment(Pos.CENTER_LEFT);
                setGraphic(cell);
            }
        }
    }

    public static class PrintableItem {
        private final String title;
        private final String subtitle1;
        private final String subtitle2;
        private final String imageUrl;

        public PrintableItem(String title, String subtitle1, String subtitle2, String imageUrl) {
            this.title = title;
            this.subtitle1 = subtitle1;
            this.subtitle2 = subtitle2;
            this.imageUrl = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle1() {
            return subtitle1;
        }

        public String getSubtitle2() {
            return subtitle2;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

    public static class FileReference {
        private final String name;
        private final String fileUrl;
        private final String fileType;

        public FileReference(String name, String fileUrl, String fileType) {
            this.name = name;
            this.fileUrl = fileUrl;
            this.fileType = fileType;
        }

        public String getName() {
            return name;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public String getFileType() {
            return fileType;
        }
    }
}