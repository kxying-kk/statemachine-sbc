package ca.kk.ssm;

import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public class StateMachineListener extends StateMachineListenerAdapter<OrderStates, OrderEvents>{

    @Override
    public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
        System.out.println("entering stateChanged method");
        System.out.println("state changed from [" + from + "]");
        System.out.println("state changed to [" + to + "]");
        // if (from != null) {
        //     log.trace("state changed from [" + from.toString() + "]");
        // }

        // if (to != null) {
        //     log.trace("state changed to [" + to.toString() + "]");
        // }
        System.out.println("leaving stateChanged method");
    }
    
}
