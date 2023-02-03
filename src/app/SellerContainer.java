package app;

import app.agents.ConsumerAgent;
import app.agents.SellerAgent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
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
public class SellerContainer extends Application{
	
	private SellerAgent sellerAgent;
	private AgentContainer container;
	private ObservableList<String> observbleList;
	private SellerContainer sellerContainer;
	
	
	

	public static void main(String[] args) {
		launch(SellerContainer.class);
	}
	
	public void StartContainer() {
		try {
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			container = runtime.createAgentContainer(profileImpl);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		sellerContainer = this;
		StartContainer();
		primaryStage.setTitle("Seller 1");
		BorderPane borderPane = new BorderPane();
		HBox hBox = new HBox(); 
		hBox.setPadding(new Insets(10));
		hBox.setSpacing(10);
		
		Label labelSeller = new Label("Seller Name: ");
		TextField textFieldSeller = new TextField();
		Button buttonSeller = new Button("Deploy Agent");
		hBox.getChildren().add(labelSeller);
		hBox.getChildren().add(textFieldSeller);
		hBox.getChildren().add(buttonSeller);
		borderPane.setTop(hBox);
		
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
		
		buttonSeller.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String nomSeller = textFieldSeller.getText();
				try {
					AgentController agentController = container.createNewAgent(nomSeller, "app.agents.SellerAgent", new Object[]{sellerContainer});
					agentController.start();
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public SellerAgent getSellerAgent() {
		return sellerAgent;
	}

	public void setSellerAgent(SellerAgent sellerAgent) {
		this.sellerAgent = sellerAgent;
	}

	public void viewMessage(GuiEvent guiEvent) {
		String message = guiEvent.getParameter(0).toString();
		observbleList.add(message);
	}

}
