package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import java.lang.*;
import java.lang.InterruptedException;

/*
@StartableByRPC
class SleepyFlow(val message: String, val limit: Int = 10, val
sleepTime: Long = 500) : FlowLogic<Unit>() {
    @Suspendable
    override fun call() {
        for (it in 0 .. limit) {
            println("$message $it!")
            sleep(Duration.ofMillis(sleepTime))
        }
    }
*/
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
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }
}
