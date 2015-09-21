package aulas1;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class PingPong extends Agent 
{

	@Override
	protected void setup() 
	{
		super.setup();
		System.out.println(this.getLocalName() + " a começar!");
		
		this.addBehaviour(new ReceiveBehaviour());
		//this.addBehaviour(new AnswerBehaviour());
	}

	@Override
	protected void takeDown() 
	{
		super.takeDown();
		System.out.println(this.getLocalName() + " a morrer...");
	}
	
	
	// Comportamento de receção de mensagem
	public class ReceiveBehaviour extends CyclicBehaviour
	{
		
		@Override
		public void action()
		{
			ACLMessage msg = receive();
			if(msg!=null)
			{
				System.out.println("Recebi uma mensagem de "+msg.getSender()+", Conteúdo: "+msg.getContent());
			}
			block();
		}
	}
	
	// Comportamento de resposta de mensagem
	public class AnswerBehaviour extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			ACLMessage msg = receive();
			if(msg!=null)
			{
				System.out.println("Recebi uma mensagem de "+msg.getSender()+", Conteúdo: "+msg.getContent());
				ACLMessage resp = msg.createReply();
				
				if(msg.getContent().equals("ping"))
				{
					resp.setContent("pong");
					resp.setPerformative(ACLMessage.INFORM);
					System.out.println("Pong");
				}
				else
				{
					resp.setContent("não percebi...");
					resp.setPerformative(ACLMessage.NOT_UNDERSTOOD);
				}
				
				send(resp);
			}
			block();
		}
	}
}
