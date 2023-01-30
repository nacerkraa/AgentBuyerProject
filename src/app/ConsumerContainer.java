package app;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class ConsumerContainer extends Application{

	public static void main(String[] args) {
		try {
			String book;
			book = "XML";
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			AgentContainer container = runtime.createAgentContainer(profileImpl);
			/* Create and agent and add it to the container */
			AgentController agentController = container.createNewAgent
					("Consumer-1", "app.agents.ConsumerAgent", new Object[] {book});
			agentController.start();
			launch(ConsumerContainer.class);
		} catch (ControllerException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Consumateur");
		BorderPane borderPane = new BorderPane();
		HBox hBox = new HBox(); 
		hBox.setPadding(new Insets(10));
		hBox.setSpacing(10);
		
		Label labelLivre = new Label("Livre: ");
		TextField textFieldLivre = new TextField();
		Button buttonBuyer = new Button("Buyer");
		hBox.getChildren().add(labelLivre);
		hBox.getChildren().add(textFieldLivre);
		hBox.getChildren().add(buttonBuyer);
		borderPane.setTop(hBox);
		
		VBox vBox = new VBox();
		GridPane gridPane = new GridPane();
		ObservableList<String> observbleList = FXCollections.observableArrayList();
		ListView<String> listViewMesseges = new ListView<String>();
		gridPane.add(listViewMesseges, 0, 0);
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);
		vBox.getChildren().add(gridPane);
		borderPane.setCenter(vBox);
		
		Scene scene = new Scene(borderPane, 400,500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		buttonBuyer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String livre = textFieldLivre.getText();
				observbleList.add(livre);
			}
		});
	}

}
