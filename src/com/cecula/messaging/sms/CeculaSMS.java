/**
 CeculaMessaging Class, this class handles the messaging part of the Cecula Library. It has a constructor
 which takes the apiKey of the developer, and four(4) major methods - sendA2PMessage(), sendP2PMessage()
 getA2PBalance(),getP2PBalance()*/

package com.cecula.messaging.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CeculaSMS {

	private Logger logger = Logger.getAnonymousLogger(); //Logger displays output to console

	private static final String GET_METHOD = "GET";//Get method for http request to server
	private static final String POST_METHOD = "POST";//Post method for http request to server

	private String result;

	
	private String apiKey;

	/**
	 * CeculaMessaging Constructor, the constructor takes a single paramter, the API_KEY of type String.*/
	public CeculaSMS(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * This method handles Cecula A2P messaging , it takes three paramters and returns a String
	 * @param
	 * @return String*/
	public String sendA2PMessage(String sender, String message, String[] recipients) {
		String returnString = "";
		try {

			String a2pMessageEndpoint = CeculaMessagingUtils.BASE_URL + CeculaMessagingUtils.A2P_MESSAGE_ENDPOINT;
			String convertedParameters = convertToJSON(sender, message, recipients);
			returnString = makeRequestToServer(convertedParameters, a2pMessageEndpoint, POST_METHOD);

		} catch (Exception e) {
			logger.log(Level.SEVERE, null, e);
		}

		return returnString;
	}
	/**
	 * This method handles Cecula Two-Way messaging , it takes three paramters and returns a String
	 * @param
	 * @return String*/
	public String sendP2PMessage(String sender, String message, String[] recipients) {
		String returnString = "";
		try {

			String p2pMessageEndpoint = CeculaMessagingUtils.BASE_URL + CeculaMessagingUtils.P2P_MESSAGE_ENDPOINT;
			String convertedParameters = convertToJSON(sender, message, recipients);
			returnString = makeRequestToServer(convertedParameters, p2pMessageEndpoint, POST_METHOD);

		} catch (Exception e) {
			logger.log(Level.SEVERE, null, e);
		}

		return returnString;
	}
	
	/**
	 * This method fetches the Cecula A2P balance, it takes no parameter and returns a JSON String containing the balance
	 * @return String*/
	public String getA2PBalance() {
		String returnString ="";
		try {
			String a2pBalanceEndpoint = CeculaMessagingUtils.BASE_URL + CeculaMessagingUtils.A2P_BALANCE_ENDPOINT;
			returnString = makeRequestToServer(null, a2pBalanceEndpoint, GET_METHOD);
		}catch(Exception e) {
			logger.log(Level.SEVERE, null, e);
		}
		return returnString;
	}
	/**
	 * This method fetches the Cecula Sync Cloud balance for a particular identity, it takes a Single String parameter
	 *  containing the identity and returns a JSON String containing the balance
	 * @return String*/
	public String getSyncCloudBalance(String identity) {
		String returnString ="";
		
		try {
			String p2pBalanceEndpoint = CeculaMessagingUtils.BASE_URL + CeculaMessagingUtils.SC_BALANCE_ENDPOINT+"?identity="+identity;
			returnString = makeRequestToServer(null, p2pBalanceEndpoint, GET_METHOD);
		}catch(Exception e) {
			logger.log(Level.SEVERE, null, e);
		}
		return returnString;
	}

	
	/*This method converts the given parameters to JSON object*/
	private String convertToJSON(String sender, String message, String[] recipients) {
		String convertedString = "";
		try {
			JSONObject messageObject = new JSONObject();
			messageObject.put("origin", sender);
			messageObject.put("message", message);
			int recipientsSize = recipients.length;
			JSONArray recipientArray = new JSONArray();
			for (int i = 0; i < recipientsSize; i++) {
				recipientArray.put(i);
			}
			messageObject.put("recipients", recipientArray);
			convertedString = messageObject.toString();
		} catch (JSONException e) {
			logger.log(Level.SEVERE, null, e);
		}

		return convertedString;
	}

	/*This method handles request to server it takes 3 String parameters and returns a String containing the server response*/
	private String makeRequestToServer(String jsonParameters, String url, String requestMethod) {
		 HttpURLConnection conn = null;
		 OutputStreamWriter writer = null;
		 BufferedReader reader = null;
		 StringBuilder resultStringBuilder = null;

		try {
			URL requestUrl = new URL(url);

			conn = (HttpURLConnection) requestUrl.openConnection();
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + apiKey);
			conn.setConnectTimeout(30000);
			conn.setDoOutput(true);

			if (requestMethod == "POST") {
				writer = new OutputStreamWriter(conn.getOutputStream());
				writer.write(jsonParameters);
				writer.flush();
			}

			String line;
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			resultStringBuilder = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				resultStringBuilder.append(line);
			}

			result = resultStringBuilder.toString();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Something went wrong while making request", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {

					logger.log(Level.SEVERE, "Something went wrong while closing output stream writer", e);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {

					logger.log(Level.SEVERE, "Something went wrong while closing input stream reader", e);
				}
			}
		}

		return result;
	}

}
