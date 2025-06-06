package hierarchical_delegation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public abstract class BaseAgent extends Agent {

	private static final long serialVersionUID = 1L;

	public static final String REQUEST = "REQUEST";
	public static final String ANSWER = "ANSWER";
	public static final String THANKS = "THANKS";
	public static final String START = "START";
	public static final String INVITE = "INVITE";
	public static final String REGISTERED = "REGISTERED";
	public static final String INFORM = "INFORM";
	public static final String UNEXPECTED_MSG = "RECEIVED AN UNEXPECTED MESSAGE FROM";
	public static final String DATA = "DATA";

	// available operations
	public static final String AVERAGE = "AVERAGE";
	public static final String MODE = "MODE";
	public static final String MEDIAN = "MEDIAN";
	public static final String STD_DEVIATION = "STD_DEVIATION";
	public static final String SORT = "SORT";

	protected static final List<String> originalOperations = new ArrayList<>(Arrays.asList(
			AVERAGE, MEDIAN, MODE, STD_DEVIATION, SORT));

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\033[1;93m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	protected static final Random rand = new Random();

	protected int dataSize;

	protected ArrayList<Double> workingData = new ArrayList<>();

	protected static final Logger logger = Logger.getLogger(BaseAgent.class.getName());

	@Override
	protected void setup() {
	}

	protected CyclicBehaviour handleMessages() {
		return new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				ACLMessage msg = receive();

				if (msg == null)
					block();
				else {
					switch (msg.getPerformative()) {
						case ACLMessage.INFORM:
							addBehaviour(handleInform(msg));
							break;
						case ACLMessage.REQUEST:
							addBehaviour(handleRequest(msg));
							break;
						default:
							logger.log(Level.INFO,
									String.format("%s RECEIVED UNEXPECTED MESSAGE PERFORMATIVE FROM %s", getLocalName(),
											msg.getSender().getLocalName()));
					}
				}
			}
		};
	}

	protected OneShotBehaviour handleInform(ACLMessage msg) {
		return new OneShotBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				/*
				 * TO-DO:
				 * IMPLEMENT THIS METHOD BEHAVIOUR ON CONCRETE CLASS
				 */
				msg.createReply();
			}
		};
	}

	protected OneShotBehaviour handleRequest(ACLMessage msg) {
		return new OneShotBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				/*
				 * TO-DO:
				 * IMPLEMENT THIS METHOD BEHAVIOUR ON CONCRETE CLASS
				 */
				msg.createReply();
			}
		};
	}

	protected void registerDF(Agent regAgent, String sdName, String sdType) {
		try {
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());

			ServiceDescription sd = new ServiceDescription();
			sd.setType(sdType);
			sd.setName(sdName);

			DFAgentDescription[] found = DFService.search(this, dfd);

			dfd.addServices(sd);

			if (found.length == 0) {
				DFService.register(regAgent, dfd);
			} else {
				found[0].addServices(sd);
				DFService.modify(regAgent, found[0]);
			}

			logger.log(Level.INFO, String.format("%s REGISTERED WITH THE DF", getLocalName()));
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	protected DFAgentDescription[] searchAgentByType(String type) {
		SearchConstraints sc = new SearchConstraints();
		sc.setMaxResults(-1L);
		DFAgentDescription search = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		DFAgentDescription[] foundAgents = null;

		sd.setType(type);
		search.addServices(sd);

		try {
			foundAgents = DFService.search(this, search, sc);
		} catch (Exception any) {
			any.printStackTrace();
		}

		return foundAgents;
	}

	protected DFAgentDescription[] searchAgentByType(String[] type) {
		SearchConstraints sc = new SearchConstraints();
		sc.setMaxResults(-1L);
		DFAgentDescription search = new DFAgentDescription();

		DFAgentDescription[] foundAgents = null;

		for (int i = 0; i < type.length; ++i) {
			ServiceDescription sd = new ServiceDescription();
			sd.setType(type[i]);
			search.addServices(sd);
		}

		try {
			foundAgents = DFService.search(this, search, sc);
		} catch (Exception any) {
			any.printStackTrace();
		}

		return foundAgents;
	}

	@Override
	protected void takeDown() {
		// Deregister with the DF
		try {
			DFService.deregister(this);
			logger.log(Level.INFO, String.format("%s DEREGISTERED WITH THE DF", getLocalName()));

		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	protected void sendMessage(String agentName, int performative, String content) {
		ACLMessage msg = new ACLMessage(performative);
		msg.setContent(content);
		msg.addReceiver(new AID(agentName, AID.ISLOCALNAME));
		send(msg);
	}

	protected void loggerSetup() {
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new LogFormatter());
		logger.setUseParentHandlers(false);
		logger.addHandler(handler);
	}

	protected ArrayList<Double> parseData(ACLMessage msg) {
		String msgContent = msg.getContent();

		msgContent = msgContent.replaceAll("[\\[\\],]", "").trim();

		ArrayList<String> splitedMsg = new ArrayList<>(Arrays.asList(msgContent.split(" ")));

		int start = splitedMsg.indexOf(DATA);

		int returnDataSize = Integer.parseInt(splitedMsg.get(++start));

		ArrayList<Double> returnData = new ArrayList<>();

		for (int i = start + 1; i <= start + returnDataSize; i++) {
			returnData.add(Double.parseDouble(splitedMsg.get(i)));
		}

		return returnData;
	}
}
