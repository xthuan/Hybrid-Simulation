# Hybrid-Simulation
Towards connecting diverse simulation worlds.

We take Cooja and Omnetpp as an example here.

1. Replace the SerialSocketServer.java in Cooja -- i.e., contiki/tools/cooja/apps/serial_socket/java/SerialSocketServer.java

2. Run Cooja simulation with simulte sensor node using the TinyOS code -- /TinyOS/PeriodicSender/build/telosb/main.exe

3. Right click the simulate sensor node and open the Mote tools -- Serial Socket (Server), then start the simulation.

4. Run omnetpp HTTPNET sample -- i.e., Omnetpp/sockets/HttpNet

5. Run Broker jar to connect Cooja and Omnetpp with inputting the required port number of each.
