package aulas1;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class CyclicSender extends Agent {
	
	@Override
	protected void setup()
	{
		super.setup();
		this.addBehaviour(new SendMessage(this,1000));
		this.addBehaviour(new ReceiveBehaviour());
	}
	
	private class ReceiveBehaviour extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			ACLMessage msg = receive();
			if (msg!=null)
			{
				System.out.println("Recebi uma mesangem de "+msg.getSender()+", Conteúdo: "+msg.getContent());
			}
		}
	}
	
	
	private class SendMessage extends TickerBehaviour
	{
		public SendMessage(Agent a, long timeout)
		{
			super(a,timeout);
		}
		
		@Override
		public void onTick()
		{
			AID receiver = new AID();
			receiver.setLocalName("pingpong");
			
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			long time = System.currentTimeMillis();
			msg.setConversationId(""+time);
			msg.addReceiver(receiver);
			
			if(time % 2 == 0)
			{
				msg.setContent("ping");
			}
				
			else
			{
				msg.setContent("not ping");
			}
			
			myAgent.send(msg);
			block(1000);
		}
	}

}
