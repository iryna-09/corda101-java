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
    Logger logger;

    public PongFlow(FlowSession session) {
        logger = LoggerFactory.getLogger(InitiatedBy.class);

        this.session = session;
    }

    @Suspendable
    @Override
    public String call() throws FlowException {
        logger.info("from pong flow1");

        UntrustworthyData<String> ping = session.receive(String.class);
        logger.info("from pong flow2");

        String s = ping.unwrap(message -> {
            assert(message.equals("ping"));
            logger.info("hello");
            System.out.println(message);
            return message;
        });
        logger.info("hello1");
        session.send("pong");

//        String s = session.receive(String.class).unwrap(it -> it);
//        System.out.println(s);
//        session.send("pong");

        return s;
    }
}
