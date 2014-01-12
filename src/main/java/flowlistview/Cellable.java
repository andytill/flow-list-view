package flowlistview;

/**
 * A {@link Cellable} instance is able to update it's nodes UI with the list
 * item object.
 * 
 * @param <E>
 *            The list item type.
 */
public interface Cellable<E> {
    void updateItem(E item, boolean empty);
}
