package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.StartableByRPC;


@StartableByRPC
public class Hello extends FlowLogic<String> {

    @Suspendable
    @Override
    public String call() throws FlowException {

        return "hello world";
    }
}


