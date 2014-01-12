package flowlistview;

import static org.junit.Assert.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ItemModelTest {
    
    private static final String ITEM = "Hello";

    @Rule 
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private ItemModel<String> itemModel;

    private ObservableList<String> strings;
    
    @Before
    public void setup() throws Exception {
        itemModel = new ItemModel<String>();
        itemModel.setCellFactory(new LabelCellFactory());

        strings = FXCollections.observableArrayList();
        strings.addListener(itemModel);
    }

    @Test
    public void addingAnItemCreatesANode() {
        assertEquals(0, itemModel.getFlowNodes().size());        
        strings.add(ITEM);        
        assertEquals(1, itemModel.getFlowNodes().size());
    }

    @Test
    public void removingAnItemRemovesItsNode() {
        strings.add(ITEM);        
        assertEquals(1, itemModel.getFlowNodes().size());
        strings.remove(ITEM);        
        assertEquals(0, itemModel.getFlowNodes().size());
    }
    
    @Test
    public void theSameNodeAddedTwiceCreatesTwoNodes() {
        strings.add(ITEM);        
        strings.add(ITEM);        
        assertEquals(2, itemModel.getFlowNodes().size());
    }
    
    @Test
    public void whenTwoNodesAreTheSameRemovingItRemovesOnlyOneNode() {
        strings.add(ITEM);        
        strings.add(ITEM);        
        assertEquals(2, itemModel.getFlowNodes().size());
        strings.remove(ITEM);
        assertEquals(1, itemModel.getFlowNodes().size());
    }
    
    @Test
    public void addItemUsingAtIndex() {
        // the nodes should have the same order as the item list
        
        String hello0 = "hello 0";     
        String hello1 = "hello 1";
        String hello2 = "hello 2";
        
        strings.add(hello1);      
        strings.add(hello2);
        strings.add(0, hello0); 
        
        Label label0, label1, label2;
        
        label0 = (Label) itemModel.getFlowNodes().get(0);
        label1 = (Label) itemModel.getFlowNodes().get(1);
        label2 = (Label) itemModel.getFlowNodes().get(2);
        
        assertEquals(hello0, label0.getText());
        assertEquals(hello1, label1.getText());
        assertEquals(hello2, label2.getText());
    }
    
    @Test
    public void clickingOnANodeSelectsIt() {
        strings.add(ITEM);        
        
        itemModel.nodeClicked(ITEM, itemModel.getFlowNodes().get(0));
        
        assertEquals(ITEM, itemModel.selectedItemProperty().get());
    }
}
