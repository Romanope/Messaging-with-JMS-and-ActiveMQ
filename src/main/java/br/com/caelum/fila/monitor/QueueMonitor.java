package br.com.caelum.consumer;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class MessageMonitor {

	private static final String CONNECTION_FACTORY = "ConnectionFactory";
	private static final String FINANCEIRO = "financeiro";

	public static void main(String[] args) throws Exception, Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY);
		
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup(FINANCEIRO);
		
		QueueBrowser messageMonitor = session.createBrowser((Queue)fila);
		
		Enumeration e = messageMonitor.getEnumeration();
        while (e.hasMoreElements()) {
            TextMessage message = (TextMessage) e.nextElement();
            System.out.println("Browse [" + message.getText() + "]");
        }
				
		session.close();
		connection.close();
		context.close();
	}
}
