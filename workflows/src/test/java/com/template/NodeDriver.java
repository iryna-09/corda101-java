package com.template;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.corda.core.identity.CordaX500Name;
import net.corda.testing.driver.*;
import net.corda.testing.node.TestCordapp;
import net.corda.testing.node.User;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import static net.corda.testing.driver.Driver.driver;
/**
 * Allows you to run your nodes through an IDE (as opposed to using deployNodes). Do not use in a production
 * environment.
 */
public class NodeDriver {
    volatile static int aPort;
    public static void main(String[] args) {
        final List<User> rpcUsers =
                ImmutableList.of(new User("user1", "test", ImmutableSet.of("ALL")));
        driver(new DriverParameters()
                        .withCordappsForAllNodes( ImmutableSet.of(TestCordapp.findCordapp("com.template.flows")))
                        .withStartNodesInProcess(true)
                        .withWaitForAllNodesToFinish(true), dsl -> {
                    try {
                        NodeHandle nodeA = dsl.startNode(new NodeParameters()
                                .withCustomOverrides(ImmutableMap.of("rpcSettings.address", "localhost:10055"))
                                .withProvidedName(new CordaX500Name("PartyA", "London", "GB"))
                                .withRpcUsers(rpcUsers)).get();
                        NodeHandle nodeB = dsl.startNode(new NodeParameters()
                                .withCustomOverrides(ImmutableMap.of("rpcSettings.address", "localhost:10075"))
                                .withProvidedName(new CordaX500Name("PartyB", "New York", "US"))
                                .withRpcUsers(rpcUsers)).get();
                    } catch (Throwable e) {
                        System.err.println("Encountered exception in node startup: " + e.getMessage());
                        e.printStackTrace();
                    }
                    return null;
                }
        );
    }
}