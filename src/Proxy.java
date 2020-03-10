import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class Proxy
{
	JsonArray jsonFileContent;
  	ClientCM cm;

	public Proxy() throws IOException
	{

		File file = new File("catalog.json");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		String temp = "";
		while ((st = br.readLine()) != null) {
			temp += st;
		}
		this.jsonFileContent = JsonParser.parseString(temp).getAsJsonArray();
  		InetAddress addr = InetAddress.getByName("127.0.0.1");
    		int port = 4445;
    		this.cm = new ClientCM(addr, port);
	}

	public Object execute(String methodName, Object ...args)
	{
		JsonObject jsonObj = null;
		for (JsonElement j: this.jsonFileContent)
		{
			if (j.getAsJsonObject().get("remoteMethod").getAsString().equals(methodName))
			{
				jsonObj = j.getAsJsonObject();
				jsonObj.addProperty("remoteMethod", "bbbb");
				break;
			}
		}

		int i = 0;
		for (Map.Entry<String, JsonElement>  entry  :  jsonObj.get("args").getAsJsonObject().entrySet())
		{
			switch (entry.getValue().getAsString().toLowerCase())
		    {
			case "long":
				jsonObj.get("args").getAsJsonObject().addProperty(entry.getKey(), Long.parseLong(args[i++].toString()));
			    break;
			case "integer":
				jsonObj.get("args").getAsJsonObject().addProperty(entry.getKey(), Integer.parseInt(args[i++].toString()));
			    break;
			case "int":
				jsonObj.get("args").getAsJsonObject().addProperty(entry.getKey(), Integer.parseInt(args[i++].toString()));
			    break;
			case "string":
				jsonObj.get("args").getAsJsonObject().addProperty(entry.getKey(), new String(args[i++].toString()));
			    break;
		    }
        	}
		
		Object ret = this.cm.send(jsonObj.toString());
		return ret;
	}

	// public static void main(String[] args) throws IOException
	// {
	// 	Proxy p = new Proxy();
	// 	p.execute("getSongChunk", 32352433, 4);
	// }
}
