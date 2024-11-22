package com.example.sistemamercado.popup;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class PopupController {

    @FXML
    private void closePopup() {
        Platform.exit();
    }
}