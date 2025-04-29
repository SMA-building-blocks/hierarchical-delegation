package hierarchical_delegation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class Manager extends BaseAgent {

	private static final long serialVersionUID = 1L;

	private Queue<String> operations;

	@Override
	protected void setup() {
		logger.log(Level.INFO, "I'm the manager!");
		this.registerDF(this, "Manager", "manager");

		addBehaviour(handleMessages());
	}

	@Override
	protected OneShotBehaviour handleInform(ACLMessage msg) {
		return new OneShotBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				if (msg.getContent().startsWith(START)) {
					logger.log(Level.INFO, String.format("%s MANAGER AGENT RECEIVED A START!", getLocalName()));
					if (msg.getContent().contains(DATA)) {
						workingData.clear();
						workingData = parseData(msg);
						dataSize = workingData.size();

						Collections.shuffle(originalOperations);
						operations = new LinkedList<>(originalOperations);

						StringBuilder builder = new StringBuilder();

						for (double val : workingData) {
							builder.append(String.format("%s ", Double.toString(val)));
						}

						String msgContentData = String.format("%s %d %s", DATA, workingData.size(), builder.toString().trim());

						ArrayList<DFAgentDescription> foundWorkers = new ArrayList<>(
								Arrays.asList(searchAgentByType("subordinate")));

						Collections.shuffle(foundWorkers);

						foundWorkers.forEach(ag -> {
							if (!ag.getName().equals(myAgent.getAID())) {
								sendMessage(ag.getName().getLocalName(), ACLMessage.REQUEST,
										String.format("%s %s", operations.remove(), msgContentData));
							}
						});

						logger.log(Level.INFO, String.format("%s SENT START MESSAGE TO WORKERS!", getLocalName()));
					} else {
						logger.log(Level.SEVERE,
								String.format("%s NO DATA TO PROCESS RECEIVED %s", ANSI_RED, ANSI_RESET));
					}
				} else if (msg.getContent().startsWith(THANKS)) {
					logger.log(Level.INFO, String.format("%s RECEIVED THANKS FROM %s!", 
						getLocalName(), msg.getSender().getLocalName()));
				} else if (msg.getContent().startsWith(INFORM)) {
					ArrayList<String> msgContent = new ArrayList<>(Arrays.asList(msg.getContent().split(" ")));
					String performedOp = msgContent.get(1);

					ArrayList<Double> recvData = parseData(msg);

					logger.log(Level.INFO, String.format("%s RECEIVED DATA FROM %s AFTER %s OPERATION: %s!", getLocalName(), msg.getSender().getLocalName(), performedOp, recvData.toString()));

					if(operations.isEmpty()){
						ACLMessage msg2 = msg.createReply();
						msg2.setPerformative(ACLMessage.INFORM);
						msg2.setContent(THANKS);
						send(msg2);
					}else{
						StringBuilder builder = new StringBuilder();

						for (double val : workingData) {
							builder.append(String.format("%s ", Double.toString(val)));
						}

						String msgContentData = String.format("%s %d %s", DATA, workingData.size(), builder.toString().trim());
						sendMessage(msg.getSender().getLocalName(), ACLMessage.REQUEST,
										String.format("%s %s", operations.remove(), msgContentData));
					}
				} else {
					logger.log(Level.INFO,
							String.format("%s %s %s", getLocalName(), UNEXPECTED_MSG,
									msg.getSender().getLocalName()));
				}
			}
		};
	}
}
