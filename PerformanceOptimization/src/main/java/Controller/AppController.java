package Controller;

import Service.ServiceApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AppController {
    private ServiceApp service;
    
    @FXML
    private void initialize(){

    }
    
    public void setService(ServiceApp service){
        this.service = service;
    }

    public void handleNplus1Lazy(ActionEvent actionEvent) {
        try{
            service.Nplus1Lazy();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleNplus1Eager(ActionEvent actionEvent) {
        try{
            service.Nplus1Eager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleBenchmarkWithoutIndex(ActionEvent actionEvent){
        try{
            service.BenchmarkWithoutIndex();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleBenchmarkIndex(ActionEvent actionEvent){
        try{
            service.BenchmarkIndex();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
