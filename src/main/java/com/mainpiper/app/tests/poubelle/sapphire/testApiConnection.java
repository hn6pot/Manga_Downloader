package com.mainpiper.app.tests.poubelle.sapphire;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Iterator;

import org.json.JSONObject;

public class testApiConnection {
	private final String USER_AGENT =
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11";
	
	private static String url = "http://192.168.1.23/API/api.php";
	
	public testApiConnection(){
		try {
			sendPost(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// HTTP GET request
		private void sendGet(String url) throws Exception {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			String basicAuth = "Basic " + Base64.getEncoder().encodeToString("Ohana:berebert".getBytes());

			// optional default is GET
			con.setRequestMethod("GET");
			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Authorization", basicAuth);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());

		}
		
		private void sendPost(String url) throws Exception {
			
			String basicAuth = "Basic " + Base64.getEncoder().encodeToString("Ohana:berebert".getBytes());
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Authorization", basicAuth);

			String urlParameters = "action=get_list_manga";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());
			String content = response.toString();
			JSONObject jObject  = new JSONObject(content);
			
			for(Iterator<String> it = jObject.keys(); it.hasNext();){
				String c = it.next();
				System.out.println(c);
				
			}
			System.out.println(jObject.keys());
			System.out.println(jObject.getJSONObject("666 Satan").toMap().values());

		}
		

		
	public static void main(String[] args) {
		testApiConnection t = new testApiConnection();
		
	}
}
