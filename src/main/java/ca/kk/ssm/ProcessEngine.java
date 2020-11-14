package ca.kk.ssm;

import org.springframework.statemachine.action.Action;

public interface ProcessEngine {

    // String PRODUCT_ID = "PRODUCT_ID";
    String ORDER_ID = "ORDER_ID";
    
    default Action<OrderStates, OrderEvents> getPaymentAction() {
        return context -> {
            final String orderId = context.getExtendedState().get(ORDER_ID, String.class);
            System.out.println("Order Id ===> " + orderId);
        };
    }

    default Action<OrderStates, OrderEvents> getErrorAction() {
        return context -> {
            System.out.println("Error Happened");
        };
    }
}
