package org.example.discordbomber;

import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class SliderClass extends Thread{
    @Override
    public void run() {
        super.run();
        while (true){
            try {


                sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void logicFunction(Slider slider, Text text, int textValue){
        textValue = (int) Math.round(slider.getValue());
        text.setText(String.valueOf(textValue));
    }

}
