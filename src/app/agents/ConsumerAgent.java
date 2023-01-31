package app.agents;

import app.ConsumerContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

public class ConsumerAgent extends GuiAgent{
	private ConsumerContainer gui;
	
	@Override
	protected void setup() {
		gui = (ConsumerContainer) getArguments()[0];
		gui.setConsumerAgent(this);
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

	@Override
	public void onGuiEvent(GuiEvent guiEvent) {
		
		if (guiEvent.getType() == 1 ) {
			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			String livre = guiEvent.getParameter(0).toString();
			aclMessage.setContent(livre);
			aclMessage.addReceiver(new AID("rma", AID.ISLOCALNAME));
			send(aclMessage);
		}
	}
}
