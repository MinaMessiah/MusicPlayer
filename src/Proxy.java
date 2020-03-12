import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
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
		String addr = InetAddress.getLocalHost().getHostAddress().trim();
		int port = 8081;
		this.cm = new ClientCM(addr, port);
	}


	public Object execute(String methodName, Object returnType, Object ...args)
	{
		JsonObject jsonObj = null;
		for (JsonElement j: this.jsonFileContent)
		{
			if (j.getAsJsonObject().get("remoteMethod").getAsString().equals(methodName))
			{
				jsonObj = j.getAsJsonObject();
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

		String ret;
		try {
			ret = this.cm.send(jsonObj.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return ret;
	}
}

