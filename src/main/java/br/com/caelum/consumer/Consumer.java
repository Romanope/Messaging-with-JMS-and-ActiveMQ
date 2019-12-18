package br.com.caelum.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class Consumer {

	private static final String CONNECTION_FACTORY = "ConnectionFactory";
	private static final String FINANCEIRO = "financeiro";

	public static void main(String[] args) throws Exception, Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY);
		
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup(FINANCEIRO);
		
		MessageConsumer consumerFinanceiro = session.createConsumer(fila);
		consumerFinanceiro.setMessageListener(new MessageListenerFinanceiro());
		
		session.close();
		connection.close();
		context.close();
	}
}
