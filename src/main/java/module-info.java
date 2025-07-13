module org.example.discordbomber {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires java.sql;


    opens org.example.discordbomber to javafx.fxml;
    exports org.example.discordbomber;
}