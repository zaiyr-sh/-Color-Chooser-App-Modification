package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.text.NumberFormat;

public class PainterAppController {

    private enum PenSize {
        SMALL(2),
        MEDIUM(4),
        LARGE(6);

        private final int radius;

        PenSize(int radius) {
            this.radius = radius;
        }

        public int getRadius() {
            return radius;
        }
    };

    @FXML
    private RadioButton blackRadioButton;

    @FXML
    private ToggleGroup colorToggleGroup;

    @FXML
    private RadioButton redRadioButton;

    @FXML
    private RadioButton greenRadioButton;

    @FXML
    private RadioButton blueRadioButton;

    @FXML
    private RadioButton smallRadioButton;

    @FXML
    private ToggleGroup sizeToggleGroup;

    @FXML
    private RadioButton mediumRadioButton;

    @FXML
    private RadioButton largeRadioButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button clearButton;

    @FXML
    private Pane drawingAreaPane;

    @FXML
    private Slider redSlider;

    @FXML
    private Slider greenSlider;

    @FXML
    private Slider blueSlider;

    @FXML
    private Slider alphaSlider;

    @FXML
    private TextField redTextField;

    @FXML
    private TextField greenTextField;

    @FXML
    private TextField blueTextField;

    @FXML
    private TextField alphaTextField;

    @FXML
    private Rectangle colorRectangle;

    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private double alpha = 1.0;

    private PenSize radius = PenSize.MEDIUM;
    private Paint brushColor = Color.BLACK;

    public void initialize() {
        blackRadioButton.setUserData(Color.BLACK);
        redRadioButton.setUserData(Color.RED);
        greenRadioButton.setUserData(Color.GREEN);
        blueRadioButton.setUserData(Color.BLUE);
        smallRadioButton.setUserData(PenSize.SMALL);
        mediumRadioButton.setUserData(PenSize.MEDIUM);
        largeRadioButton.setUserData(PenSize.LARGE);

        redSlider.setValue(red);
        redTextField.setText(new Integer(red).toString());
        redTextField.textProperty().bindBidirectional(redSlider.valueProperty(), NumberFormat.getNumberInstance());

        greenSlider.setValue(green);
        greenTextField.setText(new Integer(green).toString());
        greenTextField.textProperty().bindBidirectional(greenSlider.valueProperty(), NumberFormat.getNumberInstance());

        blueSlider.setValue(blue);
        blueTextField.setText(new Integer(blue).toString());
        blueTextField.textProperty().bindBidirectional(blueSlider.valueProperty(), NumberFormat.getNumberInstance());

        alphaSlider.setValue(alpha);
        alphaTextField.setText(new Double(alpha).toString());
        alphaTextField.textProperty().bindBidirectional(alphaSlider.valueProperty(), NumberFormat.getNumberInstance());

        redSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                        red = newValue.intValue();
                        colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                        brushColor = colorRectangle.getFill();
                    }
                }
        );
        greenSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                        green = newValue.intValue();
                        colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                        brushColor = colorRectangle.getFill();
                    }
                }
        );
        blueSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                        blue = newValue.intValue();
                        colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                        brushColor = colorRectangle.getFill();
                    }
                }
        );
        alphaSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                        alpha = newValue.doubleValue();
                        colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                        brushColor = colorRectangle.getFill();
                    }
                }
        );
    }

    @FXML
    void clearButtonPressed(ActionEvent event) {
        drawingAreaPane.getChildren().clear();
    }

    @FXML
    void colorRadioButtonSelected(ActionEvent event) {
        brushColor = (Color) colorToggleGroup.getSelectedToggle().getUserData();
    }

    @FXML
    void colorRectangleChoosing(MouseEvent event) {
        brushColor = colorRectangle.getFill();
    }

    @FXML
    void drawingAreaMouseDragged(MouseEvent event) {
        if (event.getX() > 0 && event.getY() > 0){
            Circle newCircle = new Circle(event.getX(), event.getY(), radius.getRadius(), brushColor);
            drawingAreaPane.getChildren().add(newCircle);
        }
    }

    @FXML
    void sizeRadioButtonSelected(ActionEvent event) {
        radius = (PenSize) sizeToggleGroup.getSelectedToggle().getUserData();
    }

    @FXML
    void undoButtonPressed(ActionEvent event) {
        int count = drawingAreaPane.getChildren().size();
        if (count > 0) {
            drawingAreaPane.getChildren().remove(count - 1);
        }
    }

}