package app;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
		Scene scene = new Scene(borderPane, 400,500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
