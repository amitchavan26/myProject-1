package com.example.Home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OurServicesPage {

    public VBox getContent() {
        VBox servicesBox = new VBox(10);
        servicesBox.setPadding(new Insets(20));
        servicesBox.setAlignment(Pos.TOP_CENTER);
        servicesBox.setStyle("-fx-background-color: #F8FAFC;");

        // Title
        Label heading = new Label("Our Services");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        heading.setTextFill(Color.web("#0f172a"));

        // Subtitle
        Label subheading = new Label("Comprehensive property management solutions");
        subheading.setFont(Font.font("Arial", 14));
        subheading.setTextFill(Color.GRAY);

        // Card Grid
        GridPane servicesGrid = new GridPane();
        servicesGrid.setHgap(30);
        servicesGrid.setVgap(30);
        servicesGrid.setAlignment(Pos.CENTER);
        servicesGrid.setPadding(new Insets(30));

        //
        servicesGrid.add(createServiceCard(
                "https://cdn-icons-png.flaticon.com/512/3580/3580148.png", 
                "Tenant Verification", 
                "Thorough background checks for potential tenants"), 0, 0);
        
        servicesGrid.add(createServiceCard(
                "https://cdn-icons-png.flaticon.com/512/1065/1065181.png", 
                "Rental Agreement", 
                "Legally compliant rental contracts"), 1, 0);
        
        servicesGrid.add(createServiceCard(
                "https://cdn-icons-png.flaticon.com/512/1065/1065181.png", 
                "Rental Receipt", 
                "Automated payment receipts and records"), 0, 1);
        
        servicesGrid.add(createServiceCard(
                "https://cdn-icons-png.flaticon.com/512/609/609803.png", 
                "Manage Flat", 
                "Complete property management solutions"), 1, 1);

        // Assemble the full layout
        servicesBox.getChildren().addAll(heading, subheading, servicesGrid);
        return servicesBox;
    }

    private VBox createServiceCard(String imageUrl, String title, String description) {
    VBox card = new VBox(10);
    card.setAlignment(Pos.CENTER);
    card.setPadding(new Insets(20));
    card.setPrefSize(220, 160);
    card.setStyle("""
        -fx-background-color: white;
        -fx-border-radius: 10;
        -fx-background-radius: 10;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0.3, 0, 2);
        -fx-cursor: hand;
    """);

    ImageView icon = new ImageView(new Image(imageUrl, 50, 50, true, true));
    icon.setPreserveRatio(true);

    Label titleLabel = new Label(title);
    titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    titleLabel.setTextFill(Color.web("#0f172a"));

    Label descLabel = new Label(description);
    descLabel.setFont(Font.font("Arial", 12));
    descLabel.setTextFill(Color.GRAY);
    descLabel.setWrapText(true);
    descLabel.setAlignment(Pos.CENTER);
    descLabel.setMaxWidth(180);

    card.getChildren().addAll(icon, titleLabel, descLabel);

    // Click action — show a popup or navigate
    card.setOnMouseClicked(event -> {
        // You can replace this with a page switch or modal
        System.out.println("Clicked on: " + title);

        // Optional: Show an alert for demonstration
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Service Clicked");
        alert.setHeaderText(title);
        alert.setContentText("You clicked on: " + description);
        alert.showAndWait();
    });

    return card;
}

}
