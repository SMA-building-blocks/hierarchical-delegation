package hierarchical_delegation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.UnexpectedArgumentCount;
import jade.lang.acl.ACLMessage;

public class Subordinate extends BaseAgent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {

		logger.log(Level.INFO, "I'm the subordinate!");
		this.registerDF(this, "Subordinate", "subordinate");
		
		addBehaviour(handleMessages());
	}
	
	@Override
	protected OneShotBehaviour handleInform ( ACLMessage msg ) {
		return new OneShotBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action () {
				if (msg.getContent().startsWith(START)) {
					logger.log(Level.INFO, String.format("%s SUBORDINATE AGENT RECEIVED A TASK!", getLocalName()));
					
					ACLMessage msg2 = msg.createReply();

					msg2.setContent(THANKS);

					send(msg2);
					logger.log(Level.INFO,  String.format("%s SENT THANKS MESSAGE TO %s", getLocalName(), msg.getSender().getLocalName()));
				} else {
					logger.log(Level.INFO, 
							String.format("%s RECEIVED AN UNEXPECTED MESSAGE FROM %s", getLocalName(), msg.getSender().getLocalName()));
				}
			}
		};
	}
}
