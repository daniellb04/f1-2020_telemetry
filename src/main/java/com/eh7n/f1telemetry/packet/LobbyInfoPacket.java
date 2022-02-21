package com.eh7n.f1telemetry.packet;

import java.util.ArrayList;
import java.util.List;

import com.eh7n.f1telemetry.packet.data.LobbyInfoData;
import com.eh7n.f1telemetry.packet.enums.ReadyStatus;
import com.eh7n.f1telemetry.util.PacketBuffer;

/**
 * LOBBY INFO PACKET
 * 
 * This packet details the players currently in a multiplayer lobby. It details each player’s 
 * selected car, any AI involved in the game and also the ready status of each of the participants.
 *
 * Frequency: Two every second when in the lobby
 * 
 * Size: 1169 bytes
 * 
 * <pre>
 * {@code 
		struct LobbyInfoData
		{
		    uint8     m_aiControlled;            // Whether the vehicle is AI (1) or Human (0) controlled
		    uint8     m_teamId;                  // Team id - see appendix (255 if no team currently selected)
		    uint8     m_nationality;             // Nationality of the driver
		    char      m_name[48];                // Name of participant in UTF-8 format – null terminated
		                                         // Will be truncated with ... (U+2026) if too long
		    uint8     m_readyStatus;             // 0 = not ready, 1 = ready, 2 = spectating
		};
		
		struct PacketLobbyInfoData
		{
		    PacketHeader    m_header;                       // Header
		
		    // Packet specific data
		    uint8               m_numPlayers;               // Number of players in the lobby data
		    LobbyInfoData       m_lobbyPlayers[22];
		};
 * }
 * </pre>
 */
public class LobbyInfoPacket extends Packet {
	
	private int numPlayers;
	private List<LobbyInfoData> lobbyPlayers;
	
	public int getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	public List<LobbyInfoData> getLobbyPlayers() {
		return lobbyPlayers;
	}
	public void setLobbyPlayers(List<LobbyInfoData> lobbyPlayers) {
		this.lobbyPlayers = lobbyPlayers;
	}
	@Override
	public Packet build(PacketBuffer buffer) {
		setNumPlayers(buffer.getNextUInt8AsInt());
		List<LobbyInfoData> lobbyInfos = new ArrayList<>();
		for (int k = 0; k < TOTAL_NBR_CARS; k++) {
			lobbyInfos.add(buildLobbyInfoData(buffer, k, k == getHeader().getPlayerCarIndex()));
		}
		setLobbyPlayers(lobbyInfos);
		return this;
	}

	private LobbyInfoData buildLobbyInfoData(PacketBuffer buffer, int carIndex, boolean playersCar) {
		LobbyInfoData lobbyInfoData = new LobbyInfoData();
		lobbyInfoData.setCarIndex(carIndex);
		lobbyInfoData.setPlayersCar(playersCar);
		lobbyInfoData.setAiControlled(buffer.getNextUInt8AsBoolean());
		lobbyInfoData.setTeamId(buffer.getNextUInt8AsInt());
		lobbyInfoData.setNationality(buffer.getNextUInt8AsInt());
		lobbyInfoData.setName(buffer.getNextCharArrayAsString(48));
		lobbyInfoData.setReadyStatus(ReadyStatus.fromInt(buffer.getNextUInt8AsInt()));
		return lobbyInfoData;
	}
}
