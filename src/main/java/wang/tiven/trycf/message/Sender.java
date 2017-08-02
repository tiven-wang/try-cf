package wang.tiven.trycf.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

	private final RabbitTemplate rabbitTemplate;

	public Sender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void send(String message) throws Exception {
		System.out.println("Sending villains events...");
		rabbitTemplate.convertAndSend(MQConfig.queueName, message);
	}
}
