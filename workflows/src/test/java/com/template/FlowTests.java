package com.template;
import com.template.flows.SleepyFlow;
import com.template.flows.WakeyFlow;
import com.template.flows.PingFlow;
import com.template.flows.PongFlow;

import net.corda.core.concurrent.CordaFuture;
import net.corda.testing.node.MockNetwork;
import net.corda.testing.node.MockNetworkParameters;
import net.corda.testing.node.StartedMockNode;
import net.corda.testing.node.TestCordapp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
public class FlowTests {
    private MockNetwork network;
    private StartedMockNode nodeA;
    private StartedMockNode nodeB;
    @Before
    public void setup() {
        network = new MockNetwork(
                new MockNetworkParameters(
                        Collections.singletonList(TestCordapp.findCordapp("com.template.flows"))
                ).withThreadPerNode(true)
        );
        nodeA = network.createPartyNode(null);
        nodeB = network.createPartyNode(null);
    }
    @After
    public void tearDown() {
        network.stopNodes();
    }
    @Test
    public void pingFlowTest() {
        PingFlow flow1 = new PingFlow(nodeB.getInfo().getLegalIdentities().get(0));
        CordaFuture<String> futureA = nodeA.startFlow(flow1);
    }
    @Test
    public void sleepyFlowTest() {
        SleepyFlow flow1 = new SleepyFlow("Bing!", 10, 500L);
        SleepyFlow flow2 = new SleepyFlow("Bong!", 10, 500L);
        CordaFuture<Void> futureA = nodeA.startFlow(flow1);
        CordaFuture<Void> futureB = nodeA.startFlow(flow2);
    }
    @Test
    public void wakeyFlowTest() {
        WakeyFlow flow3 = new WakeyFlow("Bing!", 10);
        WakeyFlow flow4 = new WakeyFlow("Bong!", 10);
        CordaFuture<Void> futureA = nodeA.startFlow(flow3);
        CordaFuture<Void> futureB = nodeB.startFlow(flow4);
    }
}