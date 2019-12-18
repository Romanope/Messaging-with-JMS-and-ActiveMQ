package br.com.caelum.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class Producer {

	private static final String CONNECTION_FACTORY = "ConnectionFactory";
	private static final String FINANCEIRO = "financeiro";

	public static void main(String[] args) throws Exception, Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY);
		
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup(FINANCEIRO);
		
		MessageProducer producer = session.createProducer(fila);
		
		for (int i = 0; i < 1000; i++) {
			Message message = session.createTextMessage("{\"id\": " + i + ", \"nome\":\"Carlos Romano\"}");
			producer.send(message);
		}
				
		session.close();
		connection.close();
		context.close();
	}
}
