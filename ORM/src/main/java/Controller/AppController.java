package Controller;

import Service.ServiceApp;
import javafx.fxml.FXML;

public class AppController {
    private ServiceApp service;

    public void setService(ServiceApp service){
        this.service = service;
    }

    @FXML
    public void initialize(){

    }
}
