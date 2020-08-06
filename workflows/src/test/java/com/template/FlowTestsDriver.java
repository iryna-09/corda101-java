package com.template;
import com.google.common.collect.ImmutableList;
import com.template.flows.*;
import net.corda.client.rpc.CordaRPCClient;
import net.corda.client.rpc.CordaRPCClientConfiguration;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.utilities.NetworkHostAndPort;
import net.corda.testing.driver.Driver;
import net.corda.testing.driver.DriverParameters;
import net.corda.testing.driver.NodeHandle;
import net.corda.testing.driver.NodeParameters;
import net.corda.testing.node.User;
import org.junit.Ignore;
import org.junit.Test;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
public class FlowTestsDriver {
    /**
     * Test for persisting driver
     */
    //You need to start NodeDriver.main() to run this test,
    @Ignore
    @Test
    public void SleepyFlowTest() {
        String userName = "user1";
        String password = "test";
        CordaRPCClientConfiguration config = new CordaRPCClientConfiguration(Duration.ofMinutes(3), 4);
        CordaRPCOps partyArpcProxy = new CordaRPCClient(NetworkHostAndPort.parse("localhost:10055"), config).start(userName, password).getProxy();
        CordaRPCOps partyBrpcProxy = new CordaRPCClient(NetworkHostAndPort.parse("localhost:10075"), config).start(userName, password).getProxy();
        partyArpcProxy.startFlowDynamic(SleepyFlow.class, "Bing!", 10, 500L);
        partyBrpcProxy.startFlowDynamic(SleepyFlow.class, "Bong!", 10, 500L);
    }
    @Test
    public void PingFlowTest() throws ExecutionException, InterruptedException {
        String userName = "user1";
        String password = "test";
        CordaRPCClientConfiguration config = new CordaRPCClientConfiguration(Duration.ofMinutes(3), 4);
        CordaRPCOps partyArpcProxy = new CordaRPCClient(NetworkHostAndPort.parse("localhost:10055"), config).start(userName, password).getProxy();
        CordaRPCOps partyBrpcProxy = new CordaRPCClient(NetworkHostAndPort.parse("localhost:10075"), config).start(userName, password).getProxy();
        partyArpcProxy.startFlowDynamic(PingFlow.class, partyBrpcProxy.nodeInfo().getLegalIdentities().get(0)).getReturnValue().get();
        //partyBrpcProxy.startFlowDynamic(PongFlow.class);
    }

    /**
     * Test inline driver
     */
    @Test
    public void SleepyFlowTestDriver(){
        List<User> rpcUsers = ImmutableList.of(new User("user1","test", Collections.singleton("ALL")));
        Driver.driver(new DriverParameters().withStartNodesInProcess(true).withWaitForAllNodesToFinish(true), driverDSL -> {
            try {
                NodeHandle notary = driverDSL.getDefaultNotaryNode().get();
                NodeHandle partyA = driverDSL.startNode(new NodeParameters()
                        .withProvidedName(new CordaX500Name("PartyA", "London", "GB")).withRpcUsers(rpcUsers)).get();
               // NodeHandle partyB = driverDSL.startNode(new NodeParameters()
                //        .withProvidedName(new CordaX500Name("PartyB", "New York", "US")).withRpcUsers(rpcUsers)).get();
                CompletableFuture.allOf(
                        partyA.getRpc().startFlowDynamic(SleepyFlow.class, "Bing!", 10, 500L).getReturnValue().toCompletableFuture(),
                        partyA.getRpc().startFlowDynamic(SleepyFlow.class, "Bong!", 10, 500L).getReturnValue().toCompletableFuture()
                ).thenRun(
                        () -> {
                            notary.getRpc().shutdown();
                            partyA.getRpc().shutdown();
                            //partyB.getRpc().shutdown();
                        }
                );
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        });
        System.out.println("Demonstration Complete");
    }
}