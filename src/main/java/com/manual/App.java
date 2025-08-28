package com.manual;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private final String[][] BUTTON_TEXTS = {
            { "7", "8", "9", "/" },
            { "4", "5", "6", "*" },
            { "1", "2", "3", "-" },
            { "0", ".", "=", "+" },
            { "", "", "", "C" },
    };

    private Text previewText = new Text("Realiza un cálculo");
    private TextField display = new TextField();
    private double firstNumber;
    private double secondNumber;
    private String operator;

    @Override
    public void start(Stage stage) throws IOException {

        VBox container = createContainer();

        Scene scene = createScene(container);

        configureStage(stage, scene);
    }

    private VBox createContainer() {
        VBox container = new VBox();
        container.setPadding(new Insets(10));
        container.setSpacing(10);

        container.getChildren().add(previewText);

        display.styleProperty().setValue("-fx-font-size: 24px; -fx-padding: 10px;");
        container.getChildren().add(display);

        GridPane buttons = createButtons();
        container.getChildren().addAll(buttons);

        return container;
    }

    private GridPane createButtons() {

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);

        for (int row = 0; row < BUTTON_TEXTS.length; row++) {
            for (int col = 0; col < BUTTON_TEXTS[row].length; col++) {
                String buttonText = BUTTON_TEXTS[row][col];
                if (!buttonText.isEmpty()) {
                    Button btn = new Button(buttonText);
                    btn.setOnAction(e -> handleInput(buttonText));
                    btn.setPrefWidth(60);
                    btn.setPrefHeight(45);
                    btn.setStyle(
                            "-fx-font-size: 18px; -fx-background-radius: 8px; -fx-background-color: #eaeaea; -fx-border-color: #cccccc;");
                    grid.add(btn, col, row);
                }
            }
        }

        return grid;
    }

    private void handleInput(String input) {

        switch (input) {
            case "C":
                display.clear();
                firstNumber = 0;
                secondNumber = 0;
                operator = null;
                updatePreviewText();
                break;
            case "+":
            case "-":
            case "/":
            case "*":
                operator = input;
                firstNumber = Double.parseDouble(display.getText());
                display.clear();
                updatePreviewText();
                break;
            case "=":
                secondNumber = Double.parseDouble(display.getText());
                double result = calculateResult();
                display.setText(String.valueOf(result));
                updatePreviewText();
                break;
            default:
                display.appendText(input);
                break;
        }

    }

    private double calculateResult() {
        switch (operator) {
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return firstNumber - secondNumber;
            case "*":
                return firstNumber * secondNumber;
            case "/":
                return firstNumber / secondNumber;
            default:
                return 0;
        }
    }

    private void updatePreviewText() {
        StringBuilder sb = new StringBuilder();
        if (firstNumber != 0)
            sb.append(firstNumber);
        if (operator != null)
            sb.append(" ").append(operator);
        if (secondNumber != 0)
            sb.append(" ").append(secondNumber);
        
        previewText.setText(sb.toString().isEmpty() ? "Realiza un cálculo" : sb.toString());
        previewText.setVisible(operator != null);
    }

    private Scene createScene(VBox container) {
        Scene scene = new Scene(container, 295, 375);

        return scene;
    }

    private void configureStage(Stage stage, Scene scene) {
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}