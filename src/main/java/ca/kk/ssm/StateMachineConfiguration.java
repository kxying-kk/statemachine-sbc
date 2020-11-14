package ca.kk.ssm;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Getter
@EnableStateMachineFactory
@RequiredArgsConstructor
@Slf4j
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    private ProcessEngine processEngine;

    @Override
    public void configure (StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception{
        states.withStates().initial(OrderStates.SUBMITTED)
                           .state(OrderStates.PAID)
                           .end(OrderStates.CANCELLED)
                           .end(OrderStates.FULFILLED);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer <OrderStates, OrderEvents> states) throws Exception{
        states.withConfiguration()
              .autoStartup(true)
              .listener(new StateMachineListener());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer <OrderStates, OrderEvents> transitions) throws Exception{
        transitions
        .withExternal().source(OrderStates.SUBMITTED)
                        .target(OrderStates.PAID)
                        .event(OrderEvents.PAY)
                        .action(this.paymentAction(OrderStates.SUBMITTED, this.developersWakeUpAction()))
        .and()
        .withExternal().source(OrderStates.PAID)
                        .target(OrderStates.FULFILLED)
                        .event(OrderEvents.FULFILL)
        .and()
        .withExternal().source(OrderStates.SUBMITTED)
                        .target(OrderStates.CANCELLED)
                        .event(OrderEvents.CANCEl)
        .and()
        .withExternal().source(OrderStates.PAID)
                        .target(OrderStates.CANCELLED)
                        .event(OrderEvents.CANCEl)
        .and()
        .withExternal().source(OrderStates.FULFILLED)
                        .target(OrderStates.CANCELLED)
                        .event(OrderEvents.CANCEl);
    }

    private Action<OrderStates, OrderEvents> paymentAction(OrderStates states, Action<OrderStates, OrderEvents> chain) {
        return context -> {
            chain.execute(context);
        };
    }

    private Action<OrderStates, OrderEvents> developersWakeUpAction() {
        return stateContext -> log.warn("developersWakeUpAction");
    }

}
