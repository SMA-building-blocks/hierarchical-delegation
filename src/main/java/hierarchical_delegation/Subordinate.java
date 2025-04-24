package hierarchical_delegation;

import java.util.logging.Level;

import hierarchical_delegation.strategies.AverageStrategy;
import hierarchical_delegation.strategies.MedianStrategy;
import hierarchical_delegation.strategies.ModeStrategy;
import hierarchical_delegation.strategies.SortStrategy;
import hierarchical_delegation.strategies.StdDeviationStrategy;
import hierarchical_delegation.strategies.Strategy;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Subordinate extends BaseAgent {

	private static final long serialVersionUID = 1L;
	Strategy strategyOp;

	@Override
	protected void setup() {

		logger.log(Level.INFO, "I'm the subordinate!");
		this.registerDF(this, "Subordinate", "subordinate");

		addBehaviour(handleMessages());
	}

	@Override
	protected OneShotBehaviour handleInform(ACLMessage msg) {
		return new OneShotBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				if (msg.getContent().startsWith(START)) {
					logger.log(Level.INFO, String.format("%s SUBORDINATE AGENT RECEIVED A TASK!", getLocalName()));

					ACLMessage msg2 = msg.createReply();

					msg2.setContent(THANKS);

					send(msg2);
					logger.log(Level.INFO, String.format("%s SENT THANKS MESSAGE TO %s", getLocalName(),
							msg.getSender().getLocalName()));
				} else {
					logger.log(Level.INFO,
							String.format("%s RECEIVED AN UNEXPECTED MESSAGE FROM %s", getLocalName(),
									msg.getSender().getLocalName()));
				}
			}
		};
	}

	@Override
	protected OneShotBehaviour handleRequest(ACLMessage msg) {
		return new OneShotBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				String reqOperation = msg.getContent().split(" ")[0];

				parseData(msg);
				System.out.println(data);

				logger.log(Level.INFO, String.format("%s SUBORDINATE AGENT RECEIVED A TASK (%s)!",
						getLocalName(), reqOperation));

				switch (reqOperation) {
					case AVERAGE:
						strategyOp = new AverageStrategy();
						break;
					case MEDIAN:
						strategyOp = new MedianStrategy();
						break;
					case MODE:
						strategyOp = new ModeStrategy();
						break;
					case STD_DEVIATION:
						strategyOp = new StdDeviationStrategy();
						break;
					case SORT:
						strategyOp = new SortStrategy();
						break;
					default:
						logger.log(Level.INFO,
								String.format("%s %s %s", getLocalName(), UNEXPECTED_MSG,
										msg.getSender().getLocalName()));
						break;
				}

				Object ret = strategyOp.executeOperation(data);
				System.out.println(String.format("I'm %s and I performed %s on data: %s\n", getLocalName(), reqOperation, ret.toString()));

				ACLMessage msg2 = msg.createReply();

				msg2.setPerformative(ACLMessage.INFORM);
				msg2.setContent(THANKS);

				send(msg2);

				logger.log(Level.INFO, String.format("%s SENT THANKS MESSAGE TO %s", getLocalName(),
						msg.getSender().getLocalName()));
			}
		};
	}
}
