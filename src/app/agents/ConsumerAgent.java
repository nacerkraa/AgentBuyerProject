package app.agents;

import app.ConsumerContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.introspection.AddedBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;

public class ConsumerAgent extends GuiAgent{
	private ConsumerContainer gui;
	
	@Override
	protected void setup() {
		
		gui = (ConsumerContainer) getArguments()[0];
		gui.setConsumerAgent(this);
		System.out.println("Run the agent : " + this.getAID().getName());
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				MessageTemplate messageTemplate = MessageTemplate.or(
						MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
						MessageTemplate.MatchPerformative(ACLMessage.REFUSE)
						);
				ACLMessage message = receive(messageTemplate);
				if (message != null) {
					System.out.println("Reciption d'un message "+ message.getContent());
					GuiEvent guiEvent = new GuiEvent(this, 1);
					guiEvent.addParameter(message.getContent());
					gui.viewMessage(guiEvent);
				}
				
			}
		});
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
			aclMessage.addReceiver(new AID("Buyer", AID.ISLOCALNAME));
			send(aclMessage);
		}
	}
}
