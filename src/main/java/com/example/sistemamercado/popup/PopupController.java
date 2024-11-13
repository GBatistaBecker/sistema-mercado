package com.example.sistemamercado.popup;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class PopupController {

    @FXML
    private void closePopup() {
        // Fecha todas as janelas e encerra o aplicativo
        Platform.exit();
    }
}