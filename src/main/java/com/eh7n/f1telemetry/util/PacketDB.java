package com.eh7n.f1telemetry.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.eh7n.f1telemetry.packet.Header;

public class PacketDB {
	
	private Connection conn;

	public PacketDB(String dbfile) throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:" + dbfile);
	}

	public void initDB() throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("DROP TABLE IF EXISTS packets");

		stmt.executeUpdate("CREATE TABLE packets ("
				+ "            pkt_id            INTEGER  PRIMARY KEY,"
				+ "            timestamp         REAL     NOT NULL,"
				+ "            packetFormat      INTEGER  NOT NULL,"
				+ "            gameMajorVersion  INTEGER  NOT NULL,"
				+ "            gameMinorVersion  INTEGER  NOT NULL,"
				+ "            packetVersion     INTEGER  NOT NULL,"
				+ "            packetId          INTEGER  NOT NULL,"
				+ "            sessionUID        CHAR(16) NOT NULL,"
				+ "            sessionTime       REAL     NOT NULL,"
				+ "            frameIdentifier   INTEGER  NOT NULL,"
				+ "            playerCarIndex    INTEGER  NOT NULL,"
				+ "            packet            BLOB     NOT NULL"
				+ "        );");
		
		stmt.close();
	}
	
	public void insert(Header header, byte[] packet) throws SQLException {
		String insertSql = "INSERT INTO packets(timestamp, packetFormat, gameMajorVersion, "
				+ "                             gameMinorVersion, packetVersion, packetId, sessionUID, "
				+ "                             sessionTime, frameIdentifier, playerCarIndex, packet) "
				+ "         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pstmt = conn.prepareStatement(insertSql);
		pstmt.setLong(1, System.currentTimeMillis()/1000);
		pstmt.setInt(2, header.getPacketFormat());
		pstmt.setInt(3, header.getGameMajorVersion());
		pstmt.setInt(4, header.getGameMinorVersion());
		pstmt.setInt(5, header.getPacketVersion());
		pstmt.setInt(6, header.getPacketId());
		pstmt.setString(7, header.getSessionUID().toString());
		pstmt.setFloat(8, header.getSessionTime());
		pstmt.setLong(9, header.getFrameIdentifier());
		pstmt.setInt(10, header.getPlayerCarIndex());
		pstmt.setBytes(11, packet);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
}
