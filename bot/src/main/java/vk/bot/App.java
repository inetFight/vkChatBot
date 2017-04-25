package vk.bot;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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

	public static void main( String[] args ) throws ClientProtocolException, IOException, ClientException, ApiException, InterruptedException
    {
    	final String apiKey = "ec3914024348b8d86d7c9fec6adf00650b3b0b8879ab4eb79ad2dab0c6ec717df5f0ccad1f4bf42240614";
    	final Integer groupIp = 145794448;
//    	final String redirectPage = "http://oauth.vk.com/blank.html";
//        System.out.println( "Hello orld!" );
		
		TransportClient transportClient = new HttpTransportClient();
		VkApiClient vk = new VkApiClient(transportClient);
		GroupActor actor = new GroupActor(groupIp, apiKey);
		while(true){
			
		List<Message> msg = vk.messages().get(actor).count(200).execute().getItems();
		for (int i = 0; i < msg.size(); i++) {
			vk.messages().delete(actor).messageIds(msg.get(i).getId()).executeAsString();
			vk.messages().send(actor).message(msg.get(i).getBody()).userId(msg.get(i).getUserId()).randomId((int)Math.random()).peerId(groupIp).execute();
		}
		
		
		Thread.sleep(5000);
		
		}
    }
}
