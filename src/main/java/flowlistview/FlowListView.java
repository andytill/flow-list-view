package flowlistview;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

public class FlowListView<E> implements Initializable {
    
    private final ObservableList<E> items;
    
    @FXML
    private FlowPane flowPane;
    @FXML
    private ScrollPane scrollPane;

    private ItemModel<E> itemConverter;
    
    public FlowListView() {
        items = FXCollections.observableArrayList();

        itemConverter = new ItemModel<E>();
    }

    public void initialize(URL url, ResourceBundle r) {
        // minus this so that the flow pane is smaller than the scroll pane
        // by default, if it is the same size the bars will always show
        DoubleBinding contentWidth = scrollPane.widthProperty().add(-2);        
        flowPane.prefWidthProperty().bind(contentWidth);
        flowPane.setStyle("");
        Bindings.bindContentBidirectional(flowPane.getChildren(), itemConverter.getFlowNodes());

        items.addListener(itemConverter);
    }
    
    public ObservableList<E> getItems() {
        return items;
    }

    public Callback<Void, NodeCellPair<E>> getCellFactory() {
        return itemConverter.getCellFactory();
    }

    public void setCellFactory(Callback<Void, NodeCellPair<E>> cellFactory) {
        assert Platform.isFxApplicationThread();
        
        itemConverter.setCellFactory(cellFactory);
    }

    public ObjectProperty<E> selectedItemProperty() {
        return itemConverter.selectedItemProperty();
    }
    
}
