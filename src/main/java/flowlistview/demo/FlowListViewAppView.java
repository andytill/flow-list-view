package flowlistview.demo;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import flowlistview.FlowListView;

public class FlowListViewAppView implements Initializable {
    @FXML
    private FlowListView<String> flowListController;
    @FXML
    private TextField nameField;

    public void initialize(URL url, ResourceBundle r) {
        flowListController.selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> o, String old, String _new) {
                if(_new != null) {
                    nameField.setText(_new);
                }
            }});
    }
    
    public void onAdd() {
        flowListController.getItems().add(nameField.getText());
    }
    
    public void onRemove() {
        flowListController.getItems().remove(nameField.getText());
    }
}
