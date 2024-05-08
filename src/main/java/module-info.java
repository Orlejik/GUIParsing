module org.example.demo_one {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.jsoup;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens org.example.demo1 to javafx.fxml;
    exports org.example.demo1;
}