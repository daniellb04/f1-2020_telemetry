package com.eh7n.f1telemetry;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eh7n.f1telemetry.packet.Packet;
import com.eh7n.f1telemetry.packet.PacketType;
import com.eh7n.f1telemetry.util.PacketProcessor;

/**
 * The base class for the F1 2020 Telemetry app. Starts up a non-blocking I/O
 * UDP server to read packets from the F1 2020 video game and then hands those
 * packets off to a parallel thread for processing based on the lambda function
 * defined. Leverages a fluent API for initialization. 
 * 
 * Also exposes a main method for starting up a default server
 * 
 * @author eh7n
 *
 */
public class F12020TelemetryUDPServer {

	private static final Logger log = LoggerFactory.getLogger(F12020TelemetryUDPServer.class);

	private static final String DEFAULT_BIND_ADDRESS = "0.0.0.0";
	private static final int DEFAULT_PORT = 20777;
	private static final int MAX_PACKET_SIZE = 1464;
	private static final List<PacketType> DEFAULT_PROCESSING_PACKETS = Arrays.asList(
			PacketType.MOTION,
			PacketType.SESSION,
			PacketType.LAP_DATA,
			PacketType.EVENT,
			PacketType.PARTICIPANTS,
			PacketType.CAR_SETUP,
			PacketType.CAR_TELEMETRY,
			PacketType.CAR_STATUS,
			PacketType.FINAL_CLASSIFICATION,
			PacketType.LOBBY_INFO);

	private String bindAddress;
	private int port;
	private String dbFile;
	private List<PacketType> processingPacketTypes;
	private Consumer<Packet> packetConsumer;

	private F12020TelemetryUDPServer() {
		bindAddress = DEFAULT_BIND_ADDRESS;
		port = DEFAULT_PORT;
		processingPacketTypes = DEFAULT_PROCESSING_PACKETS;
	}

	/**
	 * Create an instance of the UDP server
	 * 
	 * @return
	 */
	public static F12020TelemetryUDPServer create() {
		return new F12020TelemetryUDPServer();
	}

	/**
	 * Set the bind address
	 * 
	 * @param bindAddress
	 * @return the server instance
	 */
	public F12020TelemetryUDPServer bindTo(String bindAddress) {
		this.bindAddress = bindAddress;
		return this;
	}
	
	/**
	 * Set the filename of recording sqlite db
	 * 
	 * @param dbfile
	 * @return the server instance
	 */
	public F12020TelemetryUDPServer recordingOn(String dbfile) {
		this.dbFile = dbfile;
		return this;
	}
	
	/**
	 * Set the list of processing packet types
	 * 
	 * @param processingPacketTypes    List of packet types to be processed
	 * @return the server instance
	 */
	public F12020TelemetryUDPServer processing(List<PacketType> processingPacketTypes) {
		this.processingPacketTypes = processingPacketTypes;
		return this;
	}

	/**
	 * Set the bind port
	 * 
	 * @param port
	 * @return the server instance
	 */
	public F12020TelemetryUDPServer onPort(int port) {
		this.port = port;
		return this;
	}

	/**
	 * Set the consumer via a lambda function
	 * 
	 * @param consumer
	 * @return the server instance
	 */
	public F12020TelemetryUDPServer consumeWith(Consumer<Packet> consumer) {
		packetConsumer = consumer;
		return this;
	}

	/**
	 * Start the F1 2020 Telemetry UDP server
	 * 
	 * @throws IOException           if the server fails to start
	 * @throws IllegalStateException if you do not define how the packets should be
	 *                               consumed
	 */
	public void start() throws IOException {
		if (packetConsumer == null) {
			throw new IllegalStateException("You must define how the packets will be consumed.");
		}

		log.info("F1 2020 - Telemetry UDP Server");

		// Create an executor to process the Packets in a separate thread
		// To be honest, this is probably an over-optimization due to the use of NIO,
		// but it was done to provide a simple way of providing back pressure on the
		// incoming UDP packet handling to allow for long-running processing of the
		// Packet object, if required.
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		try (DatagramChannel channel = DatagramChannel.open()) {
			String ip = (DEFAULT_BIND_ADDRESS.equals(bindAddress))? 
					getLocalHostLANAddress().getHostAddress(): bindAddress;
			channel.socket().bind(new InetSocketAddress(bindAddress, port));
			log.info("Listening on " + ip + ":" + port + "...");
			ByteBuffer buf = ByteBuffer.allocate(MAX_PACKET_SIZE);
			buf.order(ByteOrder.LITTLE_ENDIAN);
			PacketProcessor packetProcessor = PacketProcessor.create(dbFile, processingPacketTypes);
			while (true) {
				try {
					channel.receive(buf);
					byte[] data = buf.array();
					final Packet packet = packetProcessor.process(data);
					executor.submit(() -> {
						packetConsumer.accept(packet);
					});
					buf.clear();
				}
				catch(Exception e) {
					e.printStackTrace();
					buf.clear();
					continue;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}

	/**
	 * Main class in case you want to run the server independently. Uses defaults
	 * for bind address and port, and just logs the incoming packets as a JSON
	 * object to the location defined in the logback config
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		F12020TelemetryUDPServer.create()
							.bindTo("0.0.0.0")
							.onPort(20777)
							.recordingOn("telemetry.db")
							.consumeWith((p) -> {
									log.trace(p.toJSON());
								})
							.start();
	}
	
	/**
	 * Returns an <code>InetAddress</code> object encapsulating what is most likely the machine's LAN IP address.
	 * <p/>
	 * This method is intended for use as a replacement of JDK method <code>InetAddress.getLocalHost</code>, because
	 * that method is ambiguous on Linux systems. Linux systems enumerate the loopback network interface the same
	 * way as regular LAN network interfaces, but the JDK <code>InetAddress.getLocalHost</code> method does not
	 * specify the algorithm used to select the address returned under such circumstances, and will often return the
	 * loopback address, which is not valid for network communication. Details
	 * <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4665037">here</a>.
	 * <p/>
	 * This method will scan all IP addresses on all network interfaces on the host machine to determine the IP address
	 * most likely to be the machine's LAN address. If the machine has multiple IP addresses, this method will prefer
	 * a site-local IP address (e.g. 192.168.x.x or 10.10.x.x, usually IPv4) if the machine has one (and will return the
	 * first site-local address if the machine has more than one), but if the machine does not hold a site-local
	 * address, this method will return simply the first non-loopback address found (IPv4 or IPv6).
	 * <p/>
	 * If this method cannot find a non-loopback address using this selection algorithm, it will fall back to
	 * calling and returning the result of JDK method <code>InetAddress.getLocalHost</code>.
	 * <p/>
	 *
	 * @throws UnknownHostException If the LAN address of the machine cannot be found.
	 */
	private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
	    try {
	        InetAddress candidateAddress = null;
	        // Iterate all NICs (network interface cards)...
	        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
	        while (ifaces.hasMoreElements()) {
	            NetworkInterface iface = ifaces.nextElement();
	            // Iterate all IP addresses assigned to each card...
	            Enumeration<InetAddress> inetAddrs = iface.getInetAddresses();
	            while (inetAddrs.hasMoreElements()) {
	                InetAddress inetAddr = inetAddrs.nextElement();
	                if (!inetAddr.isLoopbackAddress()) {

	                    if (inetAddr.isSiteLocalAddress()) {
	                        // Found non-loopback site-local address. Return it immediately...
	                        return inetAddr;
	                    }
	                    else if (candidateAddress == null) {
	                        // Found non-loopback address, but not necessarily site-local.
	                        // Store it as a candidate to be returned if site-local address is not subsequently found...
	                        candidateAddress = inetAddr;
	                        // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
	                        // only the first. For subsequent iterations, candidate will be non-null.
	                    }
	                }
	            }
	        }
	        if (candidateAddress != null) {
	            // We did not find a site-local address, but we found some other non-loopback address.
	            // Server might have a non-site-local address assigned to its NIC (or it might be running
	            // IPv6 which deprecates the "site-local" concept).
	            // Return this non-loopback candidate address...
	            return candidateAddress;
	        }
	        // At this point, we did not find a non-loopback address.
	        // Fall back to returning whatever InetAddress.getLocalHost() returns...
	        InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
	        if (jdkSuppliedAddress == null) {
	            throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
	        }
	        return jdkSuppliedAddress;
	    }
	    catch (Exception e) {
	        UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
	        unknownHostException.initCause(e);
	        throw unknownHostException;
	    }
	}
}
