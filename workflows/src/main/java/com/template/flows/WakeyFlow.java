package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import java.lang.*;
import java.lang.InterruptedException;

@StartableByRPC
public class WakeyFlow extends FlowLogic<Void> {
    private String message;
    private int limit = 10;

    public WakeyFlow(String message, int limit) {
        this.message = message;
        this.limit = limit;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException{
        for (int i = 0; i < limit; i++){
            System.out.println(message + " " + i + "!");
        }
        return null;
    }
}
