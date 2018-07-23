package sqlgen.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import sqlgen.ConversorExcel;

public class MainWindow {
    @FXML
    private BorderPane dropPanel;
    @FXML
    private Label dropLabel;

    @FXML
    public void handleDragOver(DragEvent event) {
        /* data is dragged over the target */
        System.out.println("onDragOver");

        /* accept it only if it is  not dragged from the same node
         * and if it has a string data */
        if (event.getGestureSource() != dropPanel &&
                event.getDragboard().hasString()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    public void handleDragEntered(DragEvent event) {
        /* the drag-and-drop gesture entered the target */
        System.out.println("onDragEntered");
        /* show to the user that it is an actual gesture target */
        if (event.getGestureSource() != dropPanel &&
                event.getDragboard().hasString()) {
            dropPanel.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
        }

        event.consume();
    }

    public void handleDragExited(DragEvent event) {
        /* mouse moved away, remove the graphical cues */
        dropPanel.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        event.consume();
    }

    public void handleDragDropped(DragEvent event) {
        /* data dropped */
        System.out.println("onDragDropped");
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            String fileURL = extraerURL(db.getString());
            String labelText = "Archivo seleccionado:\n"+ extraerNombreDeArchivo(fileURL);
            dropLabel.setText(labelText);
            success = true;

            if(urlValida(fileURL)) ConversorExcel.convertir(fileURL);
        }

        /* let the source know whether the string was successfully
         * transferred and used */
        event.setDropCompleted(success);

        event.consume();
    }

    ////////////////////////
    ////////////////////////
    ////////////////////////

    private boolean urlValida(String rawFileUrl) {
        String fileName = extraerNombreDeArchivo(rawFileUrl);
        if(!fileName.contains("xls")){System.out.printf(fileName);return false;}

        return true;
    }

    private String extraerNombreDeArchivo(String fileURL) {
        String[] fileURLArray = fileURL.split("/");
        return fileURLArray[fileURLArray.length-1];
    }

    private String extraerURL(String rawFileURL) {
        return rawFileURL.split("://")[1].trim();
    }
}


