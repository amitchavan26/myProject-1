package com.example.Home;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OwnerPage extends Application {

    private BorderPane root;
    private List<Property> properties = new ArrayList<>();
    private static final Color PRIMARY_COLOR = Color.web("#1E3A8A");      
    private static final Color SECONDARY_COLOR = Color.web("#2563EB");    
    private static final Color BACKGROUND_COLOR = Color.web("#F3F4F6");   
    private static final Color CARD_COLOR = Color.web("#FFFFFF");         
    private static final Color TEXT_COLOR = Color.web("#1F2937");         

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: " + toHex(BACKGROUND_COLOR) + ";");

        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        
        HomeContent homeContent = new HomeContent(
            () -> showAddPropertyForm(),
            () -> showPropertyListings()
        );
        
        
        StackPane centerContainer = new StackPane();
        centerContainer.getChildren().add(homeContent.getContent());
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setStyle("-fx-background-color: " + toHex(BACKGROUND_COLOR) + ";");
        
        
        centerContainer.prefWidthProperty().bind(root.widthProperty().subtract(sidebar.widthProperty()));
        centerContainer.prefHeightProperty().bind(root.heightProperty());
        root.setCenter(centerContainer);

        Scene scene = new Scene(root, 900, 650);
        
        
        stage.fullScreenProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                centerContainer.setPadding(new Insets(40));
            } else {
                centerContainer.setPadding(new Insets(20));
            }
        });
        
        stage.setScene(scene);
        stage.setTitle("Accommates - Owner Dashboard");
        stage.show();
    }

    private String toHex(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setStyle("-fx-background-color: " + toHex(SECONDARY_COLOR) + ";");
        sidebar.setPrefWidth(220);
        sidebar.setPadding(new Insets(20));

        
        Text logo = new Text("Accommates");
        logo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        logo.setFill(Color.WHITE);
        sidebar.getChildren().add(logo);


        String[] navItems = {"Home", "Our Services", "Chat"};
        VBox navContainer = new VBox(5);
        
        for (int i = 0; i < navItems.length; i++) {
            navContainer.getChildren().add(createNavItem(navItems[i], i, navItems, navContainer));
        }

        sidebar.getChildren().add(navContainer);
        
    
        Pane spacer = new Pane();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        sidebar.getChildren().add(spacer);
        
    
        HBox profileBox = new HBox(10);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.setPadding(new Insets(15));
        profileBox.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 5;");
        
        Circle profileIcon = new Circle(18, PRIMARY_COLOR);
        Text profileInitial = new Text("U");
        profileInitial.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        profileInitial.setFill(Color.WHITE);
        StackPane iconContainer = new StackPane(profileIcon, profileInitial);
        
        Label profileName = new Label("User Name");
        profileName.setTextFill(Color.WHITE);
        profileName.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
        
        profileBox.getChildren().addAll(iconContainer, profileName);
        sidebar.getChildren().add(profileBox);
        
    
        Text version = new Text("v1.0.0");
        version.setFill(Color.WHITE.deriveColor(1, 1, 1, 0.6));
        version.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        sidebar.getChildren().add(version);
        
        return sidebar;
    }

    private HBox createNavItem(String name, int index, String[] navItems, VBox navContainer) {
        HBox navItem = new HBox(10);
        navItem.setPadding(new Insets(12));
        navItem.setAlignment(Pos.CENTER_LEFT);
        navItem.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-background-radius: 5;");
        navItem.setOnMouseEntered(e -> navItem.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-cursor: hand; -fx-background-radius: 5;"));
        navItem.setOnMouseExited(e -> navItem.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-background-radius: 5;"));

        Label label = new Label(name);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
        navItem.getChildren().add(label);

        navItem.setOnMouseClicked(e -> {
            for (var child : navContainer.getChildren()) {
                if (child instanceof HBox) {
                    child.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                }
            }
            navItem.setStyle("-fx-background-color: " + toHex(PRIMARY_COLOR) + "; -fx-cursor: hand; -fx-background-radius: 5;");

            switch (navItems[index]) {
                case "Home":
                    showHomeContent();
                    break;
                case "Our Services":
                    OurServicesPage servicesPage = new OurServicesPage();
                    root.setCenter(servicesPage.getContent());
                    break;
                default:
                    showDefaultContent(navItems[index]);
            }
        });
        
        return navItem;
    }

    private void showHomeContent() {
        HomeContent homeContent = new HomeContent(
            () -> showAddPropertyForm(),
            () -> showPropertyListings()
        );
        StackPane centerContainer = (StackPane) root.getCenter();
        centerContainer.getChildren().setAll(homeContent.getContent());
    }

    private void showDefaultContent(String itemName) {
        Text msg = new Text("You clicked: " + itemName);
        msg.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        msg.setFill(TEXT_COLOR);
        StackPane pane = new StackPane(msg);
        pane.setStyle("-fx-background-color: " + toHex(BACKGROUND_COLOR) + ";");
        root.setCenter(pane);
    }

    private void showAddPropertyForm() {
        StackPane centerContainer = (StackPane) root.getCenter();
        centerContainer.getChildren().setAll(createAddPropertyForm());
    }

    private void showPropertyListings() {
        HomeContent homeContent = new HomeContent(
            () -> showAddPropertyForm(),
            () -> showPropertyListings()
        );
        homeContent.showProperties(properties);
        StackPane centerContainer = (StackPane) root.getCenter();
        centerContainer.getChildren().setAll(homeContent.getContent());
    }

    private StackPane createAddPropertyForm() {
        VBox form = new VBox(15);
        form.setPadding(new Insets(30));
        form.setStyle("-fx-background-color: " + toHex(CARD_COLOR) + "; -fx-background-radius: 10;");
        form.maxWidthProperty().bind(root.widthProperty().subtract(240).multiply(0.8));
        form.minWidthProperty().bind(root.widthProperty().subtract(240).multiply(0.6));

        Label title = new Label("Add New Property");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(TEXT_COLOR);
        form.getChildren().add(title);

        TextField locationField = createStyledTextField("Enter Location");
        TextField rentField = createStyledTextField("Enter Rent (₹)");

        List<File> selectedImages = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) selectedImages.add(null); 
        
        HBox imageBox = new HBox(15);
        imageBox.setAlignment(Pos.CENTER_LEFT);
        
        for (int i = 0; i < 3; i++) {
            final int index = i;
            VBox imageSlot = new VBox(8);
            imageSlot.setAlignment(Pos.CENTER);
        
            ImageView preview = new ImageView();
            preview.setFitWidth(80);
            preview.setFitHeight(60);
            preview.setPreserveRatio(true);
            preview.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 5;");
        
            Button uploadBtn = createStyledButton("Upload Image " + (i + 1));
        
            uploadBtn.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image");
                fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
                );
        
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    selectedImages.set(index, file);
                    preview.setImage(new Image(file.toURI().toString()));
                    uploadBtn.setText("Uploaded ✓");
                    uploadBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
                }
            });
        
            imageSlot.getChildren().addAll(preview, uploadBtn);
            imageBox.getChildren().add(imageSlot);
        }
        
        ComboBox<String> occupancyBox = createStyledComboBox("Single", "Double", "Triple");
        ComboBox<String> lookingForBox = createStyledComboBox("Male", "Female", "Anyone");

        Button submitBtn = createStyledButton("Submit Property");
        submitBtn.setStyle("-fx-background-color: " + toHex(PRIMARY_COLOR) + "; -fx-text-fill: white;");
        submitBtn.setOnAction(e -> handlePropertySubmission(locationField, rentField, selectedImages, occupancyBox, lookingForBox));

        Button backButton = createOutlinedButton("Back");
        backButton.setOnAction(e -> showHomeContent());

        form.getChildren().addAll(
            createFormLabel("Location"), locationField,
            createFormLabel("Rent"), rentField,
            createFormLabel("Upload Images (3 max)"), imageBox,
            createFormLabel("Occupancy"), occupancyBox,
            createFormLabel("Looking For"), lookingForBox,
            new HBox(10, submitBtn, backButton)
        );

        ScrollPane scrollPane = new ScrollPane(form);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: aqua;");

        StackPane container = new StackPane(scrollPane);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: " + toHex(BACKGROUND_COLOR) + ";");
        container.prefWidthProperty().bind(root.widthProperty().subtract(220));
        return container;
    }

    private void handlePropertySubmission(TextField locationField, TextField rentField, 
                                        List<File> selectedImages, 
                                        ComboBox<String> occupancyBox, 
                                        ComboBox<String> lookingForBox) {
        if (locationField.getText().isEmpty() || rentField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return;
        }

        Property property = new Property(
            locationField.getText(),
            rentField.getText(),
            selectedImages,
            occupancyBox.getValue(),
            lookingForBox.getValue()
        );
        properties.add(property);
        showAlert(Alert.AlertType.INFORMATION, "Property Added Successfully!");
        showHomeContent();
    }

    private TextField createStyledTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle("-fx-background-color: white; -fx-padding: 8; -fx-background-radius: 5;");
        return field;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + toHex(PRIMARY_COLOR) + "; " +
                       "-fx-text-fill: white; " +
                       "-fx-padding: 8 15; " +
                       "-fx-background-radius: 5;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + toHex(SECONDARY_COLOR) + "; " +
                                                    "-fx-text-fill: white; " +
                                                    "-fx-padding: 8 15; " +
                                                    "-fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + toHex(PRIMARY_COLOR) + "; " +
                                                   "-fx-text-fill: white; " +
                                                   "-fx-padding: 8 15; " +
                                                   "-fx-background-radius: 5;"));
        return button;
    }

    private Button createOutlinedButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent; " +
               "-fx-text-fill: " + toHex(PRIMARY_COLOR) + "; " +
               "-fx-padding: 8 15; " + "-fx-background-radius: 5; " +
               "-fx-border-color: " + toHex(PRIMARY_COLOR) + "; " +
               "-fx-border-width: 1;" + "-fx-border-radius: 5;");

        
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + toHex(SECONDARY_COLOR) + "; " +
             "-fx-text-fill: white; " + "-fx-padding: 8 15; " + "-fx-background-radius: 5;"));

            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + toHex(PRIMARY_COLOR) + "; " +
             "-fx-text-fill: white; " + "-fx-padding: 8 15; " + "-fx-background-radius: 5;"));

        return button;
    }

    private <T> ComboBox<T> createStyledComboBox(T... items) {
        ComboBox<T> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setValue(items[0]);
        comboBox.setStyle("-fx-background-color: white; " + "-fx-padding: 5; " +"-fx-background-radius: 5; " +
                "-fx-border-color: #D1D5DB; " +"-fx-border-radius: 5;");

        return comboBox;
    }

    private Label createFormLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(TEXT_COLOR);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        return label;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.getDialogPane().setStyle("-fx-background-color: " + toHex(CARD_COLOR) + ";");
        alert.getDialogPane().lookup(".content.label").setStyle("-fx-text-fill: " + toHex(TEXT_COLOR) + ";");
        alert.showAndWait();
    }

    public static class Property {
        private String location, rent, occupancy, lookingFor;
        private List<File> images;

        public Property(String location, String rent, List<File> images, String occupancy, String lookingFor) {
            this.location = location;
            this.rent = rent;
            this.images = new ArrayList<>(images);
            this.occupancy = occupancy;
            this.lookingFor = lookingFor;
        }

        public String getLocation() { return location; }
        public String getRent() { return rent; }
        public List<File> getImages() { return images; }
        public String getOccupancy() { return occupancy; }
        public String getLookingFor() { return lookingFor; }
    }
}