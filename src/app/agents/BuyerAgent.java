package app.agents;

import app.BuyerContainer;
import app.ConsumerContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.introspection.AddedBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;

public class BuyerAgent extends GuiAgent{
	private BuyerContainer gui;
	private AID[] listSellers;
	@Override
	protected void setup() {
		
		gui = (BuyerContainer) getArguments()[0];
		gui.setBuyerAgent(this);
		System.out.println("Run the agent : " + this.getAID().getName());
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new TickerBehaviour(this, 6000) {
			
			@Override
			protected void onTick() {
				try {
					DFAgentDescription description = new DFAgentDescription();
					ServiceDescription serviceDescription = new ServiceDescription();
					serviceDescription.setType("Sell");
					description.addServices(serviceDescription);
					DFAgentDescription[] agentDescriptions = DFService.search(myAgent, description);
					listSellers = new AID[agentDescriptions.length];
					for(int i=0; i< agentDescriptions.length; i++) {
						listSellers[i] = agentDescriptions[i].getName();
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			}
		});
		
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
				ACLMessage message = receive(messageTemplate);
				if (message != null) {
					System.out.println("Recieve a message "+ message.getContent());
					GuiEvent guiEvent = new GuiEvent(this, 1);
					String bookName = message.getContent();
					guiEvent.addParameter(bookName);
					gui.viewMessage(guiEvent);
					/*
					 * Book Buyer Operation
					 */
					ACLMessage aclMessage = new ACLMessage(ACLMessage.CFP);
					aclMessage.setContent(bookName);
					for(AID aid:listSellers) {
						aclMessage.addReceiver(aid);
					}
					send(aclMessage);
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
		
	}
}
