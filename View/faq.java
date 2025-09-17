package View;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class faq {

    public Scene getScene(Stage stage) {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_LEFT);

        Label titleLabel = new Label("Frequently Asked Questions");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        mainLayout.getChildren().add(titleLabel);

        Accordion faqAccordion = new Accordion();
        faqAccordion.getPanes().addAll(
            createFAQPane("How to use PrintWise?", "PrintWise is very easy to use. Just select your file, select print options, add address & place the order."),
            createFAQPane("What File formats can I print?", "Supported formats: PDF, JPEG, PNG, DOCX, etc."),
            createFAQPane("Which paper sizes are supported?", "We support A4, A3, Letter, Legal sizes."),
            createFAQPane("Can I print multiple pages?", "Yes, we deliver multiple pages as per your order."),
            createFAQPane("How to retrieve a returned order?", "Go to the order page, select your returned order, and follow the steps."),
            createFAQPane("What payment methods are accepted?", "We accept Credit Card, Debit Card, Net Banking, UPI, Cash on Delivery."),
            createFAQPane("Delivery time?", "Typically 3-5 business days."),
            createFAQPane("Order placement queries?", "Select your file, print options, add address & place order."),
            createFAQPane("Refund or balance issues?", "Check transaction history or contact support."),
            createFAQPane("File upload issues?", "Ensure file meets format and size requirements."),
            createFAQPane("Why was my order returned?", "Possible reasons: incorrect address, payment failure, delivery issues."),
            createFAQPane("What if I cancel a paid order?", "Refund will be processed within 7–8 working days as per cancellation policy.")
        );
        
        Button backButton = new Button("← Back to Home");
        backButton.setStyle("-fx-background-color: #60a7c3; -fx-text-fill: white; -fx-font-size: 14px;");
        backButton.setOnAction(ev -> {
        UserPage homePage = new UserPage();
        homePage.start(stage);
});

        mainLayout.getChildren().addAll(faqAccordion,backButton);
       // mainLayout.getChildren().add(0, backButton);
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);

        Image bgImage = new Image(getClass().getResourceAsStream("/Assests/Images/background.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(
            bgImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(1.0, 1.0, true, true, false, false)
        );

        StackPane root = new StackPane(scrollPane);
        root.setBackground(new Background(backgroundImage));

        return new Scene(root, 1000, 750);
    }

    private TitledPane createFAQPane(String question, String answer) {
        Label answerLabel = new Label(answer);
        answerLabel.setWrapText(true);
        VBox content = new VBox(answerLabel);
        content.setPadding(new Insets(10));
        return new TitledPane(question, content);
    }
}

