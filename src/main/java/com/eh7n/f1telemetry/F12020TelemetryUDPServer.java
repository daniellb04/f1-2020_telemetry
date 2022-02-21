package com.eh7n.f1telemetry;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eh7n.f1telemetry.packet.Packet;
import com.eh7n.f1telemetry.packet.PacketType;
import com.eh7n.f1telemetry.util.PacketReader;

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
	
	//Filter of types that must be processed
	private static final List<PacketType> PROCESSING_PACKETS = Arrays.asList(
			PacketType.MOTION,
			PacketType.SESSION,
			PacketType.LAP_DATA,
			PacketType.EVENT,
			PacketType.PARTICIPANTS,
			PacketType.CAR_SETUP,
			PacketType.CAR_TELEMETRY,
			PacketType.CAR_STATUS,
			PacketType.FINAL_CLASSIFICATION,
			PacketType.LOBBY_INFO
			);

	private String bindAddress;
	private int port;
	private Consumer<Packet> packetConsumer;

	private F12020TelemetryUDPServer() {
		bindAddress = DEFAULT_BIND_ADDRESS;
		port = DEFAULT_PORT;
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
			channel.socket().bind(new InetSocketAddress(bindAddress, port));
			log.info("Listening on " + bindAddress + ":" + port + "...");
			ByteBuffer buf = ByteBuffer.allocate(MAX_PACKET_SIZE);
			buf.order(ByteOrder.LITTLE_ENDIAN);
			while (true) {
				try {
				channel.receive(buf);
				final Packet packet = PacketReader.read(buf.array(), PROCESSING_PACKETS);
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
							.consumeWith((p) -> {
									log.trace(p.toJSON());
								})
							.start();
	}
}
