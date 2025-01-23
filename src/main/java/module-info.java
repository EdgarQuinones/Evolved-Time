module com.edgarquinones.evolvedtime.evolvedtime {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.edgarquinones.evolvedtime.evolvedtime to javafx.fxml;
    exports com.edgarquinones.evolvedtime.evolvedtime;

}

