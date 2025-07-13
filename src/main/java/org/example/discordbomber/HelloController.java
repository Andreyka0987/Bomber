package org.example.discordbomber;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Text;

import java.sql.Time;
import java.util.Timer;

public class HelloController {
    public Text attentionText;
    int totalTime = 1;
    int time;
    public Slider timerSlider = new Slider();
    public Text sliderText;
    static Timer timer;
    public RadioButton imageRadio;
    SendLogic sendLogic = new SendLogic();
    public TextField accountId;
    public TextField idTextField;
    public TextField myTextField;
    public TextField myImageField;

    @FXML
    protected void startButton() {
        time = timerSlider.getMinorTickCount();
        sendLogic.choseFunction(myTextField.getText(), myImageField.getText(), accountId.getText(), idTextField.getText(), imageRadio.isSelected(), totalTime);
        attentionText.setText("");
    }
    public void stopButton(ActionEvent event) {
        if (timer!=null) {
            timer.cancel();
            attentionText.setText("Auto sender stopped");

        }
        else {
            System.out.println("UnLuck");
        }
    }
    public void onMouse(MouseEvent mouseEvent) {
                time = (int) Math.round(timerSlider.getValue());
                sliderText.setText("min "+time);
                totalTime = time;
    }
}