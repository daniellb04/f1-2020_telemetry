package com.eh7n.f1telemetry;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class to replay a previously recorded file with F1 2020 Telemetry udp data.
 * 
 * @author daniellb
 *
 */
public class F12020TelemetryPlayer {
	
	private static final Logger log = LoggerFactory.getLogger(F12020TelemetryUDPServer.class);
	private static final String DEFAULT_DESTINATION_IP = "127.0.0.1";
	private static final int DEFAULT_DESTINATION_PORT = 20777;
	private static final String DEFAULT_DATAFILE = "telemetry.db";
	
	private String destinationIp;
	private int destinationPort;
	private String dataFile;

	private F12020TelemetryPlayer() {
		this.destinationIp = DEFAULT_DESTINATION_IP;
		this.destinationPort = DEFAULT_DESTINATION_PORT;
		this.dataFile = DEFAULT_DATAFILE;
	}
	
	/**
	 * Create an instance of the player
	 * 
	 * @return
	 */
	public static F12020TelemetryPlayer create() {
		return new F12020TelemetryPlayer();
	}

	/**
	 * Set the destination IP
	 * 
	 * @param destinationIp
	 * @return the player instance
	 */
	public F12020TelemetryPlayer sendToIp(String destinationIp) {
		this.destinationIp = destinationIp;
		return this;
	}
	
	/**
	 * Set the destination port
	 * 
	 * @param destinationPort
	 * @return the player instance
	 */
	public F12020TelemetryPlayer sendToPort(int destinationPort) {
		this.destinationPort = destinationPort;
		return this;
	}
	
	/**
	 * Set the data file
	 * 
	 * @param dataFile
	 * @return the player instance
	 */
	public F12020TelemetryPlayer readingFrom(String dataFile) {
		this.dataFile = dataFile;
		return this;
	}
	
	/**
	 * Start the F1 2020 Telemetry UDP player
	 * 
	 * @throws IOException           if the server fails to start
	 */
	public void start() throws IOException {
		//TO-DO
	}

	/**
	 * Main class in case you want to run the player with default values.
	 * Uses defaults for destination ip and port (127.0.0.1:20777) and reading
	 * a file named telemetry.dat on classpath.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		F12020TelemetryPlayer.create()
		.readingFrom("telemetria.db")
		.start();
	}

}
