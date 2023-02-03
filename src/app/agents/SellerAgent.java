package app.agents;

import app.ConsumerContainer;
import app.SellerContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
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

public class SellerAgent extends GuiAgent{
	private SellerContainer gui;
	
	@Override
	protected void setup() {
		
		gui = (SellerContainer) getArguments()[0];
		gui.setSellerAgent(this);
		System.out.println("Run the agent : " + this.getAID().getName());
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
			
			@Override
			public void action() {
				DFAgentDescription dfa = new DFAgentDescription();
				dfa.setName(getAID());
				ServiceDescription sd = new ServiceDescription();
				sd.setType("Sell");
				sd.setName("Sell-Books");
				dfa.addServices(sd);
				try {
					DFService.register(myAgent, dfa);
				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage aclMessage = receive();
				if (aclMessage != null) {
					switch (aclMessage.getPerformative()) {
					case ACLMessage.CFP: {
						GuiEvent guiEvent = new GuiEvent(this, 1);
						guiEvent.addParameter(aclMessage.getContent());
						gui.viewMessage(guiEvent);
						break;
					}
					case ACLMessage.ACCEPT_PROPOSAL: {
						
						break;
					}
					default:
						break;
					}
				} else {
					block();
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
