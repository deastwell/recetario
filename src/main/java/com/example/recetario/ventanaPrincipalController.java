package com.example.recetario;

import com.example.recetario.models.Receta;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ventanaPrincipalController implements Initializable {

    @FXML
    private TextField txtnombre;
    @FXML
    private Label labelduracion;
    @FXML
    private Slider sliderduracion;
    @FXML
    private ComboBox<String> combodificultad;
    @FXML
    private ListView<String> listtipo;
    @FXML
    private Button btnanadir;
    @FXML
    private TableView<Receta> tabla;
    @FXML
    private TableColumn<Receta,String> cduracion;
    @FXML
    private TableColumn<Receta,String> cdificultad;
    @FXML
    private TableColumn<Receta,String> ctipo;
    @FXML
    private Label info;
    @FXML
    private TableColumn<Receta,String> cnombre;

    @FXML
    protected void onHelloButtonClick() {

    }

    @FXML
    public void insertarReceta(ActionEvent actionEvent) {
        if(!txtnombre.getText().isEmpty()){
            Receta receta = new Receta();
            receta.setNombre(txtnombre.getText());
            receta.setTipo(listtipo.getSelectionModel().getSelectedItem());
            receta.setDuracion((int) sliderduracion.getValue());
            receta.setDificultad( combodificultad.getSelectionModel().getSelectedItem());
            tabla.getItems().add(receta);
            info.setText(receta.toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        combodificultad.getItems().add("Facil");
        combodificultad.getItems().add("Medio");
        combodificultad.getItems().add("Dificil");
        combodificultad.getSelectionModel().selectFirst();

        sliderduracion.setValue(60);
        labelduracion.setText(Math.round(sliderduracion.getValue())+ " min");

        listtipo.getItems().addAll("Desayuno", "Segundo desayuno", "Almuerzo", "Sobrealmuerzo", "Merienda", "Cena", "ReCena","PostCena");
        listtipo.getSelectionModel().select(0);

        sliderduracion.valueProperty().addListener((observableValue, number, t1) -> labelduracion.setText(t1.intValue()+ " min"));

        //txtnombre.textProperty().addListener((ob, vold, vnew)->(info.setText("antiguo: "+vold" Nuevo: "+vnew)));

        tabla.getSelectionModel().selectedItemProperty().addListener(
                (observable, vold, vnew)->{
                    info.setText(vnew.toString());
                    txtnombre.setText(vnew.getNombre());
                    sliderduracion.setValue(vnew.getDuracion());
                    listtipo.getSelectionModel().select(vnew.getTipo());
                    combodificultad.getSelectionModel().select(vnew.getDificultad());
                    
                }
        );

        /*
        ObservableList<String> datos = FXCollections.observableArrayList();
        datos.addAll("","");
        comboDificultad.setItems(datos);
        */



        cnombre.setCellValueFactory((fila)-> {
            String nombre = fila.getValue().getNombre();
            return new SimpleStringProperty(nombre);
        } );

        ctipo.setCellValueFactory((fila)-> new SimpleStringProperty(fila.getValue().getTipo()));

        cduracion.setCellValueFactory((fila)-> {
            String duracion = fila.getValue().getDuracion().toString()+" min";
            return new SimpleStringProperty(duracion);
        });

        cdificultad.setCellValueFactory((fila)-> new SimpleStringProperty(fila.getValue().getDificultad()));

        tabla.getItems().add(new Receta("Tacos de carne asada", "Almuerzo", 45, "Fácil"));
        tabla.getItems().add(new Receta("Huevos revueltos con tocino", "Desayuno", 15, "Moderada"));
        tabla.getItems().add(new Receta("Sándwich de jamón y queso", "Merienda", 10, "Fácil"));
        tabla.getItems().add(new Receta("Pollo a la parrilla con verduras", "Almuerzo", 60, "Moderada"));
        tabla.getItems().add(new Receta("Avena con frutas", "Desayuno", 20, "Fácil"));
        tabla.getItems().add(new Receta("Ensalada de atún", "Almuerzo", 30, "Moderada"));
        tabla.getItems().add(new Receta("Pizza casera", "Cena", 35, "Moderada"));
        tabla.getItems().add(new Receta("Batido de frutas", "Merienda", 5, "Fácil"));
        tabla.getItems().add(new Receta("Sopa de pollo casera", "Cena", 40, "Difícil"));
        tabla.getItems().add(new Receta("Pancakes con sirope de arce", "Desayuno", 25, "Moderada"));







    }

    @FXML
    public void actualizarDuracion(Event event) {
        //labelduracion.setText(Math.round(sliderduracion.getValue())+ " min");

    }






}