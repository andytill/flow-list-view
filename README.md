
#FlowListView

FlowListView is a JavaFX FlowPane with an API that is similar to the ListView control.

##Use Case

The aim of of FlowListView is to maximise horizontal line space for wide screen monitors.  

When using a ListView control with a large width, the content of a single cell is likely to be a lot smaller than than the cell width, leaving a lot of unused spaced.  FlowPane will layout controls horizontally by default, and then like a grid if all of the controls are too big to fit horizontally.

The demo below shows an example where each item is a small piece of text.  Using FlowListView all items are visible in a small space, in a ListView a vertical scrollbar would be required. 

![Flow Demo 1](http://farm6.staticflickr.com/5550/11159400254_528c9d35f8.jpg)

##Adding the FlowListView Dependency

FlowListView is Mavenised but not in Maven Central.  To install it locally, run this on the command line:

    mvn install install:install-file -Dfile=./target/flow-list-view-1.0.0.jar -DgroupId=andytill -DartifactId=flowlistview -Dversion=1.0.0 -Dpackaging=jar

The jar that was built in the target directory is runnable.  In windows, double click it run the demo and test out the control!

You can now add this flow-list-view dependency to your pom, under the dependencies element:

```xml
<dependency>
    <groupId>andytill</groupId>
    <artifactId>flow-list-view</artifactId>
    <version>1.0.0</version>
</dependency>
```

Alternatively you can simply download the release and add it to your class path.

##Adding the FlowListView to your Layout

FlowListView is FXML so first load the control:

```java
URL resource = getClass().getResource("/flowlistview/demo/flow-list-view.fxml");
FXMLLoader fxmlLoader = new FXMLLoader(resource);
```

And then add it to your layout.  **Alternatively** it can be included in another FXML control:
 
```xml
<fx:include fx:id="myFlowList" source="/flowlistview/flow-list-view.fxml" />
```

The controller can be injected into the parent controller:

```java
@FXML
private FlowListView<String> myFlowListController;
```

The generic type is the type of the items that the control will hold, just like a ListView.

## Using FlowListView

To use FlowListView just start adding objects to the list at `FlowListView#getItems()`.  A Label will be added for every object, the label text will be set to the toString of the object.

First we need to set the cell factory.  If you have used the JavaFx ListView then the cell factory here is almost the same.  The cell factory is defined as the type:

```java
Callback<Void, NodeCell<String>>
```

This means a `Callable` is required that requires no argument and returns a `NodeCellPair`.  See the `LabelCellFactory` for guidance.

The `NodeCellPair` simply contains the `Node` instance that will display a list item and a `Cellable` that sets the item on the Node.  

In the [ListView](http://docs.oracle.com/javafx/2/api/javafx/scene/control/ListView.html) implementation, this is rolled into the [ListCell](http://docs.oracle.com/javafx/2/api/javafx/scene/control/Cell.html) class.  It is separated in FlowListView so that providing FXML cells is trivial if the controller implements `Cellable`.