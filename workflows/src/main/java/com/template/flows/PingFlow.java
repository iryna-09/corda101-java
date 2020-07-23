package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.utilities.UntrustworthyData;
import org.jetbrains.annotations.Nullable;

@InitiatingFlow
@StartableByRPC
public class PingFlow extends FlowLogic<String> {

    private final Party recipient ;

    public PingFlow(Party recipient){
        this.recipient = recipient;
    }

    @Suspendable
    @Override
    public String call() throws FlowException {
      final FlowSession recipientSession = initiateFlow(recipient);

      //  final UntrustworthyData<String> recipientData = recipientSession.sendAndReceive(String.class, "ping");
      //  return recipientData.unwrap(response -> {
      //    assert(response.equals("pong"));
      //    return response;
      //} );
        recipientSession.send("ping");
        return "ping flow";
    }
}


