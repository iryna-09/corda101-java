package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.utilities.UntrustworthyData;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@InitiatingFlow
// This annotation is required by any FlowLogic which has been designated
// to initiate communication with a counterparty and
// request they start their side of the flow communication.

@StartableByRPC
public class PingFlow extends FlowLogic<String> {
    Logger logger = LoggerFactory.getLogger(PingFlow.class);

    private final Party recipient ;

    public PingFlow(Party recipient){
        this.recipient = recipient;
    }

    @Suspendable
    @Override
    public String call() throws FlowException {

      final FlowSession recipientSession = initiateFlow(recipient);
       logger.info("from ping flow");


      final UntrustworthyData<String> recipientData = recipientSession.sendAndReceive(String.class, "ping");
      return recipientData.unwrap( response -> {
          assert(response.equals("pong"));
          System.out.print("pong");
          logger.info("woooo");
          return response;
      });

    //  return recipientSession.sendAndReceive(String.class,"ping").unwrap((it->it));

//      recipientSession.send("ping");
//      return recipientSession.receive(String.class).unwrap(it -> it);
    }
}


