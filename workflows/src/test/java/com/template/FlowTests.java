package com.template;

import com.google.common.collect.ImmutableList;
import com.template.flows.SleepyFlow;
import net.corda.core.concurrent.CordaFuture;
//import net.corda.testing.driver.DriverParameters;
import net.corda.core.identity.CordaX500Name;
import net.corda.testing.driver.Driver;
import net.corda.testing.driver.DriverParameters;
import net.corda.testing.driver.NodeHandle;
import net.corda.testing.driver.NodeParameters;
import net.corda.testing.node.*;
import org.junit.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FlowTests {
    /*
    private final MockNetwork network = new MockNetwork(new MockNetworkParameters(ImmutableList.of(
            TestCordapp.findCordapp("com.template.contracts"),
            TestCordapp.findCordapp("com.template.flows")
    )).withThreadPerNode(false));

    //private final Driver driver = new Driver(new DriverParameters());

   private StartedMockNode a;

    @Before
    public void setup() {a = network.createPartyNode(null);}

    @After
    public void tearDown() {network.stopNodes(); }

    @Test
    public void SleepyFlowTest() {
        SleepyFlow flowOne = new SleepyFlow("Bing!",10, 500);
        SleepyFlow flowTwo = new SleepyFlow("Bong!",10, 700);

        CordaFuture<Void> futureOne = a.startFlow(flowOne);
        CordaFuture<Void> futureTwo = a.startFlow(flowTwo);

        network.runNetwork();

        // If we don't block and wait for the flows to finish then the test
        // will end immediately because flows run on a different thread.
        CompletableFuture.allOf(
                futureOne.toCompletableFuture(),
                futureTwo.toCompletableFuture()).join();
    }*/
    @Test
    public void SleepyFlowTestDriver(){
        List<User> rpcUsers = ImmutableList.of(new User("user1","test", Collections.singleton("ALL")));
        Driver.driver(new DriverParameters().withStartNodesInProcess(true).withWaitForAllNodesToFinish(true), driverDSL -> {
            try{
                NodeHandle partyA = driverDSL.startNode(new NodeParameters().withProvidedName(new CordaX500Name("PartyA","London","GB")).withRpcUsers(rpcUsers)).get();
                NodeHandle partyB = driverDSL.startNode(new NodeParameters().withProvidedName(new CordaX500Name("PartyB","New York","US")).withRpcUsers(rpcUsers)).get();

                partyA.getRpc().startFlowDynamic(SleepyFlow.class, "Bing!", 10, 500L);
                partyB.getRpc().startFlowDynamic(SleepyFlow.class, "Bong!", 10, 500L);


            }catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
