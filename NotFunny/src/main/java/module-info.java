module make_a_square {
    requires javafx.controls;
    requires javafx.fxml;


    opens make_a_square to javafx.fxml;
    exports make_a_square;
}