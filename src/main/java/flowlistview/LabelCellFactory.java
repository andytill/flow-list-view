package flowlistview;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.util.Callback;

/**
 * A simple cell factory that creates {@link Label} nodes from strings. 
 */
public class LabelCellFactory<T> implements Callback<Void, NodeCellPair<T>> {
    
    private String customStyle = "-fx-border-color: #b2b2b2;";

    public NodeCellPair<T> call(Void v) {
        final Label node;
        
        node = new Label();
        node.setPadding(new Insets(2d));
        
        if(customStyle  != null) {
            node.setStyle(customStyle);
        }
        
        // the Cellable just sets the string as the label text
        Cellable<T> cell = new Cellable<T>() {
            public void updateItem(T item, boolean empty) {
                if(item != null)
                    node.setText(item.toString());
                else
                    node.setText("null");
            }
        };
        
        return new NodeCellPair<T>(node, cell);
    }

    public String getCustomStyle() {
        return customStyle;
    }

    public void setCustomStyle(String customStyle) {
        assert Platform.isFxApplicationThread();
        
        this.customStyle = customStyle;
    }
}