module com.example.fluxo_de_cliente {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires java.prefs;


    opens com.example.fluxo_de_cliente to javafx.fxml;
    exports com.example.fluxo_de_cliente;
}