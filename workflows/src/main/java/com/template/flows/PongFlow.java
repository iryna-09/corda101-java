package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.sun.istack.Nullable;
import net.corda.core.flows.*;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.utilities.UntrustworthyData;

// ******************
// * Responder flow *
// ******************

@InitiatedBy(PingFlow.class)
public class PongFlow extends FlowLogic<String> {
    private FlowSession session;

    public PongFlow(FlowSession session) {
        this.session = session;
    }

    @Suspendable
    @Override
    public String call() throws FlowException {
       // UntrustworthyData<String> ping = session.receive(String.class);
       // String recievedMsg = ping.unwrap(message -> {
       //     assert(message.equals("ping"));
       //     System.out.println(message);
       //     return message;
       // });
        session.send("pong");
        return "pong";
    }
}
