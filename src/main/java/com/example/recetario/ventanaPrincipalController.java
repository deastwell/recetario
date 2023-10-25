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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.util.StringConverter;

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
    private MenuItem menusalir;
    @FXML
    private MenuItem menuacercade;
    @FXML
    private ComboBox<Receta> comborecetas;
    @FXML
    private ToggleGroup dificultad;
    @FXML
    private ImageView carita;
    private MediaPlayer mediaPlayer;

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

        Media sonido = new Media(HelloApplication.class.getClassLoader().getResource("com/example/recetario/audio/clic.wav").toExternalForm());
        mediaPlayer.seek(new Duration(0));
        mediaPlayer = new MediaPlayer(sonido);



        combodificultad.getItems().add("Facil");
        combodificultad.getItems().add("Medio");
        combodificultad.getItems().add("Dificil");
        combodificultad.getSelectionModel().selectFirst();

        combodificultad.valueProperty().addListener((observableValue, s, t1) -> {
                String imagen = "neutral.png";
                if(t1=="Facil"){
                    imagen = "Feliz.png";
                }
                else if(t1=="Dificil") {
                    imagen = "muerto.png";
                }
                carita.setImage(new Image("com/example/recetario/img/"+imagen));
               // mediaPlayer.play();

        });

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

        comborecetas.setConverter(new StringConverter<Receta>() {
            @Override
            public String toString(Receta receta) {
               if(receta!=null) {return receta.getNombre();}
               else {return null;}
            }

            @Override
            public Receta fromString(String s) {
                return null;
            }
        });

        comborecetas.getItems().addAll(tabla.getItems());




    }



    @FXML
    public void actualizarDuracion(Event event) {
        //labelduracion.setText(Math.round(sliderduracion.getValue())+ " min");

    }


    @FXML
    public void salir(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void acercade(ActionEvent actionEvent) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("El Creador");
        alert.setContentText("Er Danie");
        alert.showAndWait();

    }


    @FXML
    public void mostrarreceta(ActionEvent actionEvent) {

        //System.out.println(comborecetas.getSelectionModel().getSelectedItem());


        Session.setRecetaActual(comborecetas.getSelectionModel().getSelectedItem());
        HelloApplication.loadFXML("ventanaSecundaria.fxml");

    }
}