package com.example.gameproject;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

public class GameWindow {
    private int secretNumber; // The randomly generated number that the user has to guess
    private int attempts = 0; // Counter for the number of guesses made
    private final int maxAttempts = 5; // Maximum number of allowed attempts

    // Constructor initializes the game by generating a secret number
    public GameWindow(String username) {
        restartGame();
    }

    public void start(Stage primaryStage) {
        // Set the game window title
        primaryStage.setTitle("🎯 Number Guessing Game");

        // Title label with styling
        Label titleLabel = new Label("🌟 Let's Make a Guess! 🌟");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Instruction label
        Label instruction = new Label("Enter a number between 1 and 100:");
        instruction.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        // Text field for user input
        TextField guessField = new TextField();
        guessField.setStyle("-fx-background-color: #ffffffcc; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px;");

        // Feedback label to give hints on the guesses
        Label feedback = new Label("You have 5 attempts!");
        feedback.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        // Label to show the number of attempts used
        Label attemptsLabel = new Label("Attempts: 0");
        attemptsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        // Add a glow effect to feedback text
        Glow glow = new Glow();
        glow.setLevel(0.6);
        feedback.setEffect(glow);

        // Handle the user's guess when they press Enter
        guessField.setOnAction(e -> handleGuess(guessField, feedback, attemptsLabel, primaryStage));

        // Layout for the UI components
        VBox layout = new VBox(15, titleLabel, instruction, guessField, feedback, attemptsLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #ffd6e8, #ffe3f3);" +
                        "-fx-border-radius: 20;" +
                        "-fx-background-radius: 20;" +
                        "-fx-background-image: url('https://i.imgur.com/8sG6y6e.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-position: center center;"
        );

        // Set up the scene and show the game window
        Scene scene = new Scene(layout, 420, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to handle the user's guess
    private void handleGuess(TextField guessField, Label feedback, Label attemptsLabel, Stage primaryStage) {
        if (!guessField.isEditable()) return; // Prevent input if the game has ended

        String guessText = guessField.getText().trim(); // Get the user's input
        if (guessText.isEmpty()) {
            feedback.setText("⚠️ Please enter a valid number!");
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(guessText); // Convert input to an integer
        } catch (NumberFormatException e) {
            feedback.setText("⚠️ Please enter numbers only!");
            return;
        }

        attempts++; // Increase attempt counter
        feedback.setText("");

        // Check if the guess is correct
        if (guess == secretNumber) {
            feedback.setText("🎉 You guessed it in " + attempts + " attempt(s)! The number was: " + secretNumber);
            showEndOptions(primaryStage, true, secretNumber);
        } else if (attempts >= maxAttempts) {
            feedback.setText("❌ You've used all your attempts. The number was: " + secretNumber);
            showEndOptions(primaryStage, false, secretNumber);
        } else {
            feedback.setText(guess < secretNumber ? "⬆️ Try a higher number!" : "⬇️ Try a lower number!");
        }

        attemptsLabel.setText("Attempts: " + attempts);
        guessField.setText(""); // Clear input field for next guess
    }

    // Display game over options to restart or return to login
    private void showEndOptions(Stage primaryStage, boolean isWin, int number) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("🎮 Game Over");

            // Set game result message
            String resultMessage = isWin
                    ? "🎉 Congratulations!\nYou guessed the number in " + attempts + " attempt(s)! 🎯\nThe number was: " + number + "."
                    : "❌ You’ve used all your attempts. 💔\nThe correct number was: " + number + ".";

            alert.setHeaderText(null);
            alert.setContentText(resultMessage + "\n\nWhat would you like to do next?");

            // Create buttons for restarting or going back to login
            ButtonType btnRestart = new ButtonType("🔁 Play Again");
            ButtonType btnLogin = new ButtonType("🔙 Back to Login");

            alert.getButtonTypes().setAll(btnRestart, btnLogin);

            // Style the dialog box
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(
                    "-fx-background-color: linear-gradient(to right, #ffd6e8, #ffe3f3);" +
                            "-fx-border-color: #ffb6c1; -fx-border-width: 3px;" +
                            "-fx-border-radius: 12; -fx-background-radius: 12;" +
                            "-fx-font-size: 16px; -fx-text-fill: black;");

            Button restartButton = (Button) dialogPane.lookupButton(btnRestart);
            restartButton.setStyle(
                    "-fx-background-color: linear-gradient(to right, #fbc2eb, #a6c1ee);" +
                            "-fx-text-fill: black; -fx-font-size: 14px;" +
                            "-fx-background-radius: 10; -fx-padding: 6 12;");

            Button loginButton = (Button) dialogPane.lookupButton(btnLogin);
            loginButton.setStyle(
                    "-fx-background-color: linear-gradient(to right, #fbc2eb, #a6c1ee);" +
                            "-fx-text-fill: black; -fx-font-size: 14px;" +
                            "-fx-background-radius: 10; -fx-padding: 6 12;");

            Label content = (Label) dialogPane.lookup(".content.label");
            if (content != null) {
                content.setStyle("-fx-text-fill: black; -fx-font-size: 16px; -fx-alignment: center;");
            }

            // Handle user choice
            alert.showAndWait().ifPresent(response -> {
                if (response == btnRestart) {
                    restartGame();
                    start(primaryStage);
                } else {
                    new LoginWindow().start(primaryStage);
                }
            });
        });
    }

    // Method to restart the game by generating a new random number
    private void restartGame() {
        secretNumber = new Random().nextInt(100) + 1; // Generate a number between 1 and 100
        attempts = 0; // Reset attempt counter
    }
}
