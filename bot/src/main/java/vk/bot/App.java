package vk.bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.ParseException;

import com.vk.api.sdk.actions.Messages;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.Actor;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;

/**
 * Hello world!
 *
 */
public class App {
	public static boolean isLong(String s)
	{
	    try
	    {
	        Long.parseLong(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }

	}
	public static void main( String[] args ) throws ClientProtocolException, IOException, ClientException, ApiException, InterruptedException, URISyntaxException, ParseException
    {

		Properties prop = new Properties();
	    InputStream input = null;

	    input = new FileInputStream("config.properties");
	    prop.load(input);
	    String apikey = prop.getProperty("apiKey");
		String groupId = prop.getProperty("groupIp");
		TransportClient transportClient = new HttpTransportClient();
		VkApiClient vk = new VkApiClient(transportClient);
		GroupActor actor = new GroupActor(Integer.parseInt(groupId), apikey);
		
		
		while(true){
			
		List<Message> msg = vk.messages().get(actor).count(200).execute().getItems();
		
		for (int i = 0; i < msg.size(); i++) {
			System.out.println(msg.get(i).getBody());
			if(isLong(msg.get(i).getBody())){
			String response = Tracking.TrackingDocument(msg.get(i).getBody());
			vk.messages()
			.send(actor)
			.message(response)
			.userId(msg.get(i).getUserId())
			.randomId((int)Math.random())
//			.lat((float) 48.4708055)
//			.lng((float) 25.2996523)
			.peerId(Integer.parseInt(groupId))
			.forwardMessages(Integer.toString(msg.get(i).getId()))
			.execute();
			
			}
			else {
				vk.messages()
				.send(actor)
				.message("Неправильный номер посылки, попробуйте ещё раз")
				.userId(msg.get(i).getUserId())
				.randomId((int)Math.random())
				.peerId(Integer.parseInt(groupId))
				.forwardMessages(Integer.toString(msg.get(i).getId()))
				.execute();
			}
			
		vk.messages().delete(actor).messageIds(msg.get(i).getId()).executeAsString();
		}
		
		
		Thread.sleep(4000);
		
		}
    }
}
