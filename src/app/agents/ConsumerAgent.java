package app.agents;

import jade.core.Agent;

public class ConsumerAgent extends Agent{
	@Override
	protected void setup() {
		System.out.println("Run the agent : " + this.getAID().getName());
	}
	
	@Override
	protected void beforeMove() {
		System.out.println("Before move...");
	}
	
	@Override
	protected void afterMove() {
		System.out.println("After move...");
	}
	
	@Override
	protected void takeDown() {
		System.out.println("Before Die...");
	}
}
