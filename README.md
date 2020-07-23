<p align="center">
  <img src="https://www.corda.net/wp-content/uploads/2016/11/fg005_corda_b.png" alt="Corda" width="500">
</p>

# Corda 101

Welcome to the Corda 101 webinars

**This is the Java version of the first CorDapp we will make today. **

## Running the nodes

See https://docs.corda.net/tutorial-cordapp.html#running-the-example-cordapp.

We use Gradle as our build automation tool.
Run the `./gradlew deployNodes` Gradle task to build four nodes with the CorDapp installed on them. After the build finishes, you will see the following output in the `workflows-java/build/nodes` folder:

•	A folder for each generated node

•	A `runnodes` shell script for running all the nodes simultaneously on osX

## Interacting with the nodes

### Shell

When started via the command line, each node will display an interactive shell:

    Welcome to the Corda interactive shell.
    Useful commands include 'help' to see what is available, and 'bye' to shut down the node.
    
    Tue Nov 06 11:58:13 GMT 2018>>>

You can use this shell to interact with your node. 
You can find out more about the node shell [here](https://docs.corda.net/shell.html).
