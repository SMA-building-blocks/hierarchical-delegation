package hierarchical_delegation;

import java.util.logging.Level;

import jade.core.behaviours.OneShotBehaviour;
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

				logger.log(Level.INFO, String.format("%s SUBORDINATE AGENT RECEIVED A TASK (%s)!",
						getLocalName(), reqOperation));

				switch (reqOperation) {
					case AVERAGE:
						/*
						 * TO-DO: implement average method over data
						 */
						break;
					case MEDIAN:
						/*
						 * TO-DO: implement median method over data
						 */
						break;
					case MODE:
						/*
						 * TO-DO: implement mode method over data
						 */
						break;
					case STD_DEVIATION:
						/*
						 * TO-DO: implement std deviation method over data
						 */
						break;
					case SORT:
						/*
						 * TO-DO: implement sort method over data
						 */
						break;
					default:
						logger.log(Level.INFO,
								String.format("%s %s %s", getLocalName(), UNEXPECTED_MSG,
										msg.getSender().getLocalName()));
						break;
				}

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
