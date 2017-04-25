package vk.bot;

import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Tracking {
	
	public static String TrackingDocument(String EW) throws URISyntaxException, ClientProtocolException, IOException, ParseException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder builder = new URIBuilder("https://api.novaposhta.ua/v2.0/json/");
		URI uri = builder.build();
		HttpPost request = new HttpPost(uri);
		StringEntity reqEntity = new StringEntity("{\"apiKey\": \"\","
				+ "\"modelName\": \"TrackingDocument\","
				+ "\"calledMethod\": \"getStatusDocuments\","
				+ "\"methodProperties\": {"
				+ "\"Documents\": ["
				+ "{\"DocumentNumber\":\"" + EW + "\","
		        + "\"Phone\": \"\"}"
				+ "],\"Language\": \"RU\"}}");
		request.setEntity(reqEntity);
		HttpResponse response = httpclient.execute(request);
		
		String otvet = EntityUtils.toString(response.getEntity(), "UTF-8");
//		System.out.println(EntityUtils.toString(reqEntity, "UTF-8"));
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(otvet);

		JSONArray data = (JSONArray) jsonObject.get("data");
		JSONObject innerObj = (JSONObject) data.get(0);
		String status = (String)innerObj.get("Status");
		return status;
	}

}
