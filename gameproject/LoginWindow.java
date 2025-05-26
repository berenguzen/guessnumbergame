package com.example.gameproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginWindow extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Set the window title
        primaryStage.setTitle("✨ Login / Register ✨");

        // Title label with styling
        Label titleLabel = new Label("🎯 NUMBER GUESSING GAME 🎯");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: black; -fx-effect: dropshadow(gaussian, #ff99cc, 5, 0.5, 1, 1);");

        // Username label
        Label usernameLabel = new Label("👤 Username:");
        usernameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        // Username input field
        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-background-color: #ffffffcc; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10px;");

        // Password label
        Label passwordLabel = new Label("🔐 Password:");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        // Password input field
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-background-color: #ffffffcc; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10px;");

        // Register and Login buttons
        Button registerButton = new Button("📝 Register");
        Button loginButton = new Button("🔓 Login");

        // Message label for status updates
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        // Drop shadow effect for buttons
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);

        // Styling for buttons
        registerButton.setStyle("-fx-background-color: linear-gradient(to right, #a1c4fd, #c2e9fb); -fx-text-fill: black; -fx-font-size: 14px;");
        loginButton.setStyle("-fx-background-color: linear-gradient(to right, #fbc2eb, #a6c1ee); -fx-text-fill: black; -fx-font-size: 14px;");

        // Apply shadow effect
        registerButton.setEffect(shadow);
        loginButton.setEffect(shadow);

        // Register button event handling
        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            // Validate username format (only letters)
            if (!username.matches("[a-zA-Z]+")) {
                messageLabel.setText("❌ Username must only contain letters! No spaces, numbers, or symbols.");
                return;
            }

            // Attempt to register the user
            if (UserManager.registerUser(username, password)) {
                messageLabel.setText("✅ Registration successful! You can now log in.");
            } else {
                messageLabel.setText("❌ This username is already taken!");
            }
        });

        // Login button event handling
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            // Validate username format (only letters)
            if (!username.matches("[a-zA-Z]+")) {
                messageLabel.setText("❌ Username must only contain letters! No spaces, numbers, or symbols.");
                return;
            }

            // Attempt authentication
            if (UserManager.authenticateUser(username, password)) {
                new GameWindow(username).start(primaryStage); // Open the game window on success
            } else {
                messageLabel.setText("❌ Login failed! Incorrect username or password.");
            }
        });

        // Layout for the UI elements
        VBox layout = new VBox(15, titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, registerButton, loginButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #ffd6e8, #ffe3f3);" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-color: #ffb6c1; -fx-border-width: 2px;");

        // Set up scene and stage
        Scene scene = new Scene(layout, 420, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch the application
    }
}
