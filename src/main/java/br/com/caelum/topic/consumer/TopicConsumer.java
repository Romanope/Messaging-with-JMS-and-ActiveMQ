package br.com.caelum.topic.consumer;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class TopicConsumer {

	private static final String SIGNATURE_NAME = "signature name";
	private static final String TOPIC_NAME = "pedido";
	private static final String CLIENT_ID = "estoque";
	private static final String CONNECTION_FACTORY = "ConnectionFactory";

	public static void main(String[] args) throws Exception, Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY);
		
		Connection connection = connectionFactory.createConnection();
		connection.setClientID(CLIENT_ID);
		
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topic = (Topic) context.lookup(TOPIC_NAME);
		
		MessageConsumer consumerFinanceiro = session.createDurableSubscriber(topic, SIGNATURE_NAME);
		consumerFinanceiro.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		new Scanner(System.in).nextInt();
		
		session.close();
		connection.close();
		context.close();
	}
}
