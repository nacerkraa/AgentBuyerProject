package app.agents;

import jade.core.Agent;
import jade.wrapper.ControllerException;

public class ConsumerAgent extends Agent{
	@Override
	protected void setup() {
		System.out.println("Run the agent : " + this.getAID().getName());
	}
	
	@Override
	protected void beforeMove() {
		try {
			System.out.println("Before move.... the : " + this.getContainerController().getContainerName());
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void afterMove() {
		try {
			System.out.println("After move... to : " + this.getContainerController().getContainerName());
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void takeDown() {
		System.out.println("Before Die...");
	}
}
