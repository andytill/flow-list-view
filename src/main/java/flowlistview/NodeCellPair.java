package flowlistview;

import javafx.scene.Node;

/**
 * Simple struct containing a {@link Node} that will be displayed and a
 * {@link Cellable} that is able to update the node UI when it is required to
 * display a list item.
 * 
 * @param <E>
 *            Type of the list item.
 */
public class NodeCellPair<E> {
    
    private final Node node;
    
    private final Cellable<E> cell;

    public NodeCellPair(Node node, Cellable<E> cell) {
        this.node = node;
        this.cell = cell;
    }

    public Node getNode() {
        return node;
    }

    public Cellable<E> getCell() {
        return cell;
    }
}
