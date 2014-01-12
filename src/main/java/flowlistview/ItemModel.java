package flowlistview;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import com.sun.javafx.collections.MappingChange.Map;

class ItemModel<E> implements ListChangeListener<E> {
    
    public static final String SELECTED_ITEM_CSS_CLASS = "flow-list-selected-item";

    /**
     * Lookup for the items to nodes in the flow pane so that we can remove a
     * node via it's item. Using a {@link Map} would prevent the same item being
     * added multiple times.
     */
    private final ArrayList<Entry<E>> itemNodes = new ArrayList<Entry<E>>();
    
    private final ObservableList<Node> flowNodes;
    
    private final SimpleObjectProperty<E> selectedItem;
    
    private Callback<Void, NodeCellPair<E>> cellFactory;

    private Node selectedNode;
    
    public ItemModel() {
        flowNodes = FXCollections.observableArrayList();
        selectedItem = new SimpleObjectProperty<E>();
        cellFactory = new LabelCellFactory<E>();
    }

    public void onChanged(ListChangeListener.Change<? extends E> c) {
        while (c.next()) {
            for (E item : c.getRemoved()) {
                removeItemsNode(item);
            }
            ObservableList<? extends E> list = c.getList();
            for (final E item : c.getAddedSubList()) {
                int index = list.indexOf(item);
                addItemsNode(index, item);
            }
        }
    }

    private void removeItemsNode(E item) {
        Entry<E> nodeCell = null;
        
        for (Entry<E> entry : itemNodes) {
            if(entry.it.equals(item)) {
                nodeCell = entry;
                break;
            }
        }
        
        if(nodeCell != null) {
            itemNodes.remove(nodeCell);
            flowNodes.remove(nodeCell.nodeCell.getNode());
        }
    }

    private void addItemsNode(int index, final E item) {
        NodeCellPair<E> nodeCell;
        
        nodeCell = cellFactory.call(null);
        nodeCell.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                Node source = null;
                
                if(e.getSource() instanceof Node) {
                    source = (Node) e.getSource();
                }
                
                nodeClicked(item, source);
            }});
        updateItem(item, false, nodeCell);
        itemNodes.add(new Entry<E>(item, nodeCell));
        flowNodes.add(index, nodeCell.getNode());
    }

    /**
     * Method public for testing.
     */
    public void nodeClicked(final E item, Node sourceNode) {
        if(!selectedItem.isBound()) {

            E oldItem = selectedItem.get();
            
            // if clicking the same item again then set the 
            // selection to null so once 
            if(selectedItem.get() != null && oldItem.equals(item)) {
                selectedItem.set(null);
            }
            selectedItem.set(item);
            
            // set the CSS class on the selected item
            sourceNode.getStyleClass().add(SELECTED_ITEM_CSS_CLASS);

            // and remove it from the old one
            if(selectedNode != null) {
                selectedNode.getStyleClass().remove(SELECTED_ITEM_CSS_CLASS);
            }
            
            selectedNode = sourceNode;
        }
    }

    private void updateItem(E item, boolean empty, NodeCellPair<E> node) {
        node.getCell().updateItem(item, empty);
    }
    
    public Callback<Void, NodeCellPair<E>> getCellFactory() {
        return cellFactory;
    }

    public void setCellFactory(Callback<Void, NodeCellPair<E>> cellFactory) {
        this.cellFactory = cellFactory;
    }
    
    public ObjectProperty<E> selectedItemProperty() {
        return selectedItem;
    }
    
    public ObservableList<Node> getFlowNodes() {
        return flowNodes;
    }
    
    private static class Entry<E> {
        final E it;
        final NodeCellPair<E> nodeCell;
        public Entry(E it, NodeCellPair<E> nodeCell) {
            this.it = it;
            this.nodeCell = nodeCell;
        }
    }
}