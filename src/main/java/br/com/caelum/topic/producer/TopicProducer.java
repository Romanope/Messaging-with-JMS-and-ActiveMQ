package br.com.caelum.topic.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TopicProducer {

	private static final String PEDIDO = "pedido";
	private static final String CONNECTION_FACTORY = "ConnectionFactory";

	public static void main(String[] args) throws Exception, Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY);
		
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup(PEDIDO);
		
		MessageProducer producer = session.createProducer(fila);

		Message message = session.createTextMessage("{\"id\": 2, \"nome\":\"Carlos Romano\"}");
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();
	}
}
