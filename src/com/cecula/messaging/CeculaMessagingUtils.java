package com.cecula.messaging;

public final class CeculaMessagingUtils {
	
	private CeculaMessagingUtils() {
		
	}
	
	protected static final String BASE_URL = "https://api.cecula.com";//base url for the Cecula api
	protected static final String A2P_MESSAGE_ENDPOINT = "/send/a2p";//endpoint for s2p messging
	protected static final String P2P_MESSAGE_ENDPOINT = "/send/p2p";//endpoint for p2p messaging
	protected static final String A2P_BALANCE_ENDPOINT = "/account/a2pbalance";//endpoint for a2p balance
	protected static final String SC_BALANCE_ENDPOINT = "/account/scbalance";//endpoint for p2p balance
}
