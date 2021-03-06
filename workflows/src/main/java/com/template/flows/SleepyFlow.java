package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import java.lang.*;
import java.lang.InterruptedException;
import java.time.Duration;

@StartableByRPC
public class SleepyFlow extends FlowLogic<Void> {
    private String message;
    private int limit = 10;
    private long sleepTime = 500;

    public SleepyFlow(String message, int limit, long sleepTime) {
        this.message = message;
        this.limit = limit;
        this.sleepTime = sleepTime;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException{
        for (int i = 0; i < limit; i++){
            System.out.println(message + " " + i + "!");
            try {
                sleep(Duration.ofMillis(sleepTime));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }
}
