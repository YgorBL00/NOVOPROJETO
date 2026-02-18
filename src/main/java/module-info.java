module com.example.fluxo_de_cliente {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires java.desktop;
    requires java.prefs;

    // Permite que o FXML acesse os controllers
    opens com.example.fluxo_de_cliente.controller to javafx.fxml;

    // Se usar @FXML em classes dentro do pacote principal
    opens com.example.fluxo_de_cliente to javafx.fxml;

    // Se usar Properties no model (recomendado)
    opens com.example.fluxo_de_cliente.model to javafx.base;

    exports com.example.fluxo_de_cliente;
    exports com.example.fluxo_de_cliente.model;
    opens com.example.fluxo_de_cliente.controller.admin to javafx.fxml;
    opens com.example.fluxo_de_cliente.controller.vendedor to javafx.fxml;
}
