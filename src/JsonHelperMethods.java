import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonHelperMethods {

	static ArrayList<Song> readSongsJSON() {
		try {
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader("music.json"));

			ArrayList<Song> songs = new ArrayList<Song>();

			reader.beginArray();

			while (reader.hasNext())
				songs.add(gson.fromJson(reader, Song.class));

			reader.close();

			return songs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static ArrayList<User> readUsersJSON() {
		try {
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader("users.json"));

			ArrayList<User> users = new ArrayList<User>();

			reader.beginArray();

			while (reader.hasNext())
				users.add(gson.fromJson(reader, User.class));

			reader.close();

			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static void writeUsersJSON(ArrayList<User> users) {
		try (final FileWriter writer = new FileWriter("users.json")) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(users, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static boolean deleteAccount(User deleteMe) {
		ArrayList<User> users = readUsersJSON();
		for (User tempUser : users) {
			System.out.println(tempUser.getUsername());
			if (tempUser.equals(deleteMe)) {
				users.remove(deleteMe);
				writeUsersJSON(users);
				return true;
			}
		}
		return false;
	}
}
