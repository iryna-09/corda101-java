package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.sun.istack.Nullable;
import net.corda.core.flows.*;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.utilities.UntrustworthyData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//        UntrustworthyData<String> ping = session.receive(String.class);
//        String s = ping.unwrap(message -> {
//            assert(message.equals("ping"));
//            return message;
//        });
//        session.send("pong");

        String s = session.receive(String.class).unwrap(it -> it);
        session.send("pong");

        return s;
    }
}
