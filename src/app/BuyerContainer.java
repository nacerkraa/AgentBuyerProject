package app;

import app.agents.BuyerAgent;
import app.agents.ConsumerAgent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
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
public class BuyerContainer extends Application{
	
	private BuyerAgent buyerAgent;
	ObservableList<String> observbleList;
	
	
	

	public static void main(String[] args) {
		launch(BuyerContainer.class);
	}
	
	public void StartContainer() {
		try {
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			AgentContainer container = runtime.createAgentContainer(profileImpl);
			/* Create and agent and add it to the container */
			AgentController agentController = container.createNewAgent
					("Buyer", "app.agents.BuyerAgent", new Object[] {this});
			agentController.start();
			
		} catch (ControllerException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		StartContainer();
		primaryStage.setTitle("Buyer");
		BorderPane borderPane = new BorderPane();
		
		VBox vBox = new VBox();
		GridPane gridPane = new GridPane();
		observbleList = FXCollections.observableArrayList();
		ListView<String> listViewMesseges = new ListView<String>(observbleList);
		gridPane.add(listViewMesseges, 0, 0);
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);
		vBox.getChildren().add(gridPane);
		borderPane.setCenter(vBox);
		
		Scene scene = new Scene(borderPane, 400,500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public BuyerAgent getBuyerAgent() {
		return buyerAgent;
	}

	public void setBuyerAgent(BuyerAgent buyerAgent) {
		this.buyerAgent = buyerAgent;
	}

	public void viewMessage(GuiEvent guiEvent) {
		String message = guiEvent.getParameter(0).toString();
		observbleList.add(message);
	}

}
