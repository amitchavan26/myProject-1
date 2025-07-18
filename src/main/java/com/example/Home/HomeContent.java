package com.example.Home;

import java.io.File;
import java.util.List;

import com.example.Home.OwnerPage.Property;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class HomeContent {
    private StackPane content;
    private VBox mainContainer;
    private Runnable onAddPropertyClick;
    private Runnable onViewPropertiesClick;

    public HomeContent(Runnable addPropertyHandler, Runnable viewPropertiesHandler) {
        this.onAddPropertyClick = addPropertyHandler;
        this.onViewPropertiesClick = viewPropertiesHandler;
        
        // Root container with background
        StackPane rootPane = new StackPane();
        rootPane.setAlignment(Pos.TOP_CENTER);
        
        // Try loading background image
        try {
            Image bgImage = new Image(getClass().getResourceAsStream("/assets/images/apartment1.png"));
            rootPane.setStyle("-fx-background-image: url('" + bgImage.getUrl() + "'); " +
                            "-fx-background-size: cover; " +
                            "-fx-background-position: center;");
        } catch (Exception e) {
            rootPane.setStyle("-fx-background-color: linear-gradient(to bottom, #f5f7fa, #e4e8f0);");
        }

        // Main content container
        mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(40));
        mainContainer.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-background-radius: 10;");
        mainContainer.setMaxWidth(800);

        // Title and subtitle
        Text title = new Text("Find Your Perfect Tenants");
        title.setStyle("-fx-font: bold 28px 'Arial'; -fx-fill: #2c3e50;");
        
        Text subtitle = new Text("You haven't posted your property yet");
        subtitle.setStyle("-fx-font: 16px 'Arial'; -fx-fill: #7f8c8d;");

        // Buttons
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        
        Button btnAdd = createButton("Add Property", "#3498db", true);
        Button btnView = createButton("View Properties", "#3498db", false);
        
        // Set actions for buttons
        btnAdd.setOnAction(e -> onAddPropertyClick.run());
        btnView.setOnAction(e -> onViewPropertiesClick.run());
        
        buttons.getChildren().addAll(btnAdd, btnView);
        mainContainer.getChildren().addAll(title, subtitle, buttons);
        
        rootPane.getChildren().add(mainContainer);
        this.content = rootPane;
    }

    public void showProperties(List<Property> properties) {
        mainContainer.getChildren().clear();
        
        Text title = new Text("Your Properties");
        title.setStyle("-fx-font: bold 28px 'Arial'; -fx-fill: #2c3e50;");
        
        if (properties.isEmpty()) {
            Text emptyText = new Text("No properties added yet");
            emptyText.setStyle("-fx-font: 16px 'Arial'; -fx-fill: #7f8c8d;");
            mainContainer.getChildren().addAll(title, emptyText);
        } else {
            VBox propertiesContainer = new VBox(15);
            
            for (Property property : properties) {
                VBox propertyCard = createPropertyCard(property);
                propertiesContainer.getChildren().add(propertyCard);
            }
            
            ScrollPane scrollPane = new ScrollPane(propertiesContainer);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background: transparent; -fx-border-color: transparent;");
            
            mainContainer.getChildren().addAll(title, scrollPane);
        }
        
        // Add back button
        Button backButton = createButton("Back to Home", "#7f8c8d", false);
        backButton.setOnAction(e -> {
            HomeContent homeContent = new HomeContent(onAddPropertyClick, onViewPropertiesClick);
            content.getChildren().set(0, homeContent.getContent());
        });
        mainContainer.getChildren().add(backButton);
    }

    private VBox createPropertyCard(Property property) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 10;");
        
        Text locationText = new Text("Location: " + property.getLocation());
        locationText.setStyle("-fx-font: bold 18px 'Arial'; -fx-fill: #2c3e50;");
        
        Text rentText = new Text("Rent: ₹" + property.getRent());
        rentText.setStyle("-fx-font: 16px 'Arial'; -fx-fill: #2c3e50;");
        
        Text detailsText = new Text(property.getOccupancy() + " occupancy • Looking for: " + property.getLookingFor());
        detailsText.setStyle("-fx-font: 14px 'Arial'; -fx-fill: #7f8c8d;");
        
        HBox imagesBox = new HBox(10);
        for (File imageFile : property.getImages()) {
            if (imageFile != null) {
                ImageView imageView = new ImageView(new Image(imageFile.toURI().toString()));
                imageView.setFitWidth(120);
                imageView.setFitHeight(90);
                imageView.setPreserveRatio(true);
                imageView.setStyle("-fx-border-radius: 5; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
                imagesBox.getChildren().add(imageView);
            }
        }
        
        card.getChildren().addAll(locationText, rentText, detailsText, imagesBox);
        return card;
    }

    private Button createButton(String text, String color, boolean isFilled) {
        Button btn = new Button(text);
        String baseStyle = "-fx-padding: 12 24; -fx-font: bold 14px 'Arial'; " +
                          "-fx-background-radius: 5; -fx-border-radius: 5; ";
        
        if (isFilled) {
            btn.setStyle(baseStyle + "-fx-background-color: " + color + "; " +
                         "-fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 1);");
            btn.setOnMouseEntered(e -> btn.setStyle(baseStyle + "-fx-background-color: derive(" + color + ", -20%);"));
            btn.setOnMouseExited(e -> btn.setStyle(baseStyle + "-fx-background-color: " + color + ";"));
        } else {
            btn.setStyle(baseStyle + "-fx-background-color: transparent; " +
                         "-fx-text-fill: " + color + "; -fx-border-color: " + color + "; -fx-border-width: 2;");
            btn.setOnMouseEntered(e -> btn.setStyle(baseStyle + "-fx-background-color: rgba(52,152,219,0.1);"));
            btn.setOnMouseExited(e -> btn.setStyle(baseStyle + "-fx-background-color: transparent;"));
        }
        return btn;
    }

    public StackPane getContent() {
        return content;
    }
}