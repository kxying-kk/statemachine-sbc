package ca.kk.ssm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class StateMachineApplication implements CommandLineRunner{

	@Autowired
	private StateMachineFactory<OrderStates, OrderEvents> stateMachineFactory;

	public static void main(String[] args) {
		log.info("STARTING THE APPLICATION");
		SpringApplication.run(StateMachineApplication.class, args);
		log.info("APPLICATION FINISHED");
	}

    @Override
    public void run(String... args) {
		log.info("EXECUTING : command line runner");
		StateMachine<OrderStates, OrderEvents> stateMachine = stateMachineFactory.getStateMachine();
		Message<OrderEvents> orderMessage = 
		MessageBuilder.withPayload(OrderEvents.PAY)
					  .setHeader("ORDER_ID", "1234")
					  .build();
		stateMachine.sendEvent(orderMessage);

		Message<OrderEvents> fullfillMessage = 
		MessageBuilder.withPayload(OrderEvents.FULFILL)
					  .setHeader("ORDER_ID", "1234")
					  .build();
		stateMachine.sendEvent(fullfillMessage);
	}
	


}
