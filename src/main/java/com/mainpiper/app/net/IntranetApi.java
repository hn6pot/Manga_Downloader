package com.mainpiper.app.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mainpiper.app.display.Display;
import com.mainpiper.app.exceptions.TerminateBatchException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j

public class IntranetApi {
	
	private static final String authSeparator = ":";
	private static final String urlSeparator = "?";
	private static final String apiExtension =".cbz";
	
	private final String apiUrl;
	private final String apiFunction = "action=get_list_manga";
	public final String USER_AGENT;
	private final String apiAuth; 
	
	public IntranetApi(String apiUser, String apiPassword, String apiUrl, String USER_AGENT){
		this.apiUrl = apiUrl;
		this.USER_AGENT = USER_AGENT;
		this.apiAuth = makeBasicAuth(apiUser, apiPassword);
	}
	
	public List<String> getChapterList(String mangaName){
		return responseTreatment(mangaName, sendPost());
	}
	
	public Map<String, List<String>> getMangaList(){
		return responseTreatmentAll(sendPost());
	}
	
	private String makeBasicAuth (String apiUser, String apiPassword){
		return apiUser + IntranetApi.authSeparator + apiPassword;
	}
	
	private Map<String, List<String>> responseTreatmentAll(StringBuffer response){
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		JSONObject content  = new JSONObject(response.toString());
		for(Iterator<String> it = content.keys(); it.hasNext();){
			String key = it.next();
			try{
				List<String> chapters = content.getJSONObject(key).toMap().values().stream()
						.map(chapterNumber -> chapterNameTreatment(chapterNumber.toString())).collect(Collectors.toList());
						result.put(key, chapters);
			}catch(JSONException e){
				List<String> chapters =  ((JSONArray) content.get(key)).toList().stream()
						.map(chapterNumber -> chapterNameTreatment(chapterNumber.toString())).collect(Collectors.toList());
				result.put(key, chapters);
			}
		}
		
		return result;
	
	}
	
	private List<String> responseTreatment(String mangaName, StringBuffer response){
		List<String> result = new ArrayList<>();
		JSONObject content  = new JSONObject(response.toString());
		try{
		result =  content.getJSONObject(mangaName).toMap().values().stream()
				.map(chapterNumber -> chapterNameTreatment(chapterNumber.toString())).collect(Collectors.toList());
		return result;
		}catch(JSONException e){
			try{
				result =  ((JSONArray) content.get(mangaName)).toList().stream()
						.map(chapterNumber -> chapterNameTreatment(chapterNumber.toString())).collect(Collectors.toList());
				return result;
			}
			catch(JSONException ex){
				log.error("This manga is not on the API : ", mangaName);
                throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_MANGA_NAME,
                        "This manga is not on the API : " + mangaName , ex);
			}
		}
	}
	
	private String chapterNameTreatment(String chapterName){
		return chapterName.replaceAll(".cbz", "").replaceAll("\\.0", "");
	}

	private StringBuffer sendPost() {
		
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString(this.apiAuth.getBytes());
	
		URL obj;
		try {
			obj = new URL(apiUrl);
		} catch (MalformedURLException e) {
			log.error("There is an error with the apiUrl provided : ", apiUrl);
			throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_URL_CORRUPTED,
					"There is an error with the apiUrl provided : " + apiUrl, e);
		}
		
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) obj.openConnection();
		} catch (IOException e) {
			log.error("Unknow error during the connection to the url : ", apiUrl);
			throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_CONNECTION_FAIL,
					"Unknow error during the connection to the url : " + apiUrl, e);
		}

		//add request header
		try {
			con.setRequestMethod("POST");
		} catch (ProtocolException e) {
			log.error("POST Protocol is apparently not supported yet by your device");
			throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_CONNECTION_FAIL,
					"POST Protocol is apparently not supported yet by your device", e);
		}
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Authorization", basicAuth);

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr;
		try {
			wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(apiFunction);
			wr.flush();
			wr.close();
		} catch (IOException e) {
			log.error("Unknow error during the connection to the url : ", apiUrl);
			throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_CONNECTION_FAIL,
					"Unknow error during the connection to the url : " + apiUrl, e);
		}

		int responseCode;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			log.error("The Api does not provide any response code on : ", apiUrl);
			throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_CONNECTION_FAIL,
					"The Api does not provide any response code on " + apiUrl, e);
		}
		
		String msg = "Sending request to the Api...    [POST protocol selected]";
		log.info(msg);
		Display.displayInfo(msg);
		log.info("Sending POST request to URL : ", apiUrl);
		
		log.debug("Post parameters : ", apiFunction);
		log.debug("Response Code : " + responseCode);

		try(BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));){
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response;
			
			}catch(IOException e){
				log.error("Unknow error while collecting data : ", apiUrl);
				throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_CONNECTION_FAIL,
						"Unknow error while collecting data", e);
			}
	}
	
	private StringBuffer sendGet() {
		
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString(this.apiAuth.getBytes());
		
		String url = apiUrl + IntranetApi.urlSeparator + this.apiFunction;
		URL obj;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e) {
			log.error("There is an error with the apiUrl provided : ", apiUrl);
			throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_URL_CORRUPTED,
					"There is an error with the apiUrl provided : " + apiUrl, e);
		}
		
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) obj.openConnection();
		} catch (IOException e) {
			log.error("Unknow error during the connection to the url : ", apiUrl);
			throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_CONNECTION_FAIL,
					"Unknow error during the connection to the url : " + apiUrl, e);
		}

		//add request header
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			log.error("POST Protocol is apparently not supported yet by your device");
			throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_CONNECTION_FAIL,
					"POST Protocol is apparently not supported yet by your device", e);
		}
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Authorization", basicAuth);

		int responseCode;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			log.error("The Api does not provide any response code on : ", apiUrl);
			throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_CONNECTION_FAIL,
					"The Api does not provide any response code on " + apiUrl, e);
		}
		
		String msg = "Sending request to the Api...    [GET protocol selected]";
		log.info(msg);
		Display.displayInfo(msg);
		log.info("Sending GET request to URL : ", url);
		log.debug("Response Code : " + responseCode);

		try(BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));){
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response;
			
			}catch(IOException e){
				log.error("Unknow error while collecting data : ", apiUrl);
				throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_API_CONNECTION_FAIL,
						"Unknow error while collecting data", e);
			}
	}
	
	public static void main(String[] args) {
		IntranetApi t = new IntranetApi("Ohana", "berebert", "http://192.168.1.23/API/api.php", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
		System.out.println(t.getChapterList("One Piece"));
		System.out.println(t.getMangaList());
	}
	

}
