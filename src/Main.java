//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.stream.JsonReader;
//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;
//
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//
//public class Main {
//	static void mp3play(String file) {
//		try {
//			// It uses CECS327InputStream as InputStream to play the song
//			InputStream is = new CECS327InputStream(file);
//			Player mp3player = new Player(is);
//			mp3player.play();
//		} catch (JavaLayerException exception) {
//			exception.printStackTrace();
//		} catch (IOException exception) {
//			exception.printStackTrace();
//		}
//	}
//
//	static ArrayList<Song> readSongsJSON() {
//		try {
//			Gson gson = new Gson();
//			JsonReader reader = new JsonReader(new FileReader("music.json"));
//
//			ArrayList<Song> songs = new ArrayList<Song>();
//
//			reader.beginArray();
//
//			while (reader.hasNext())
//				songs.add(gson.fromJson(reader, Song.class));
//
//			reader.close();
//
//			return songs;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	static ArrayList<User> readUsersJSON() {
//		try {
//			Gson gson = new Gson();
//			JsonReader reader = new JsonReader(new FileReader("users.json"));
//
//			ArrayList<User> users = new ArrayList<User>();
//
//			reader.beginArray();
//
//			while (reader.hasNext())
//				users.add(gson.fromJson(reader, User.class));
//
//			reader.close();
//
//			return users;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	static void writeUsersJSON(ArrayList<User> users) {
//		try (final FileWriter writer = new FileWriter("users.json")) {
//			Gson gson = new GsonBuilder().create();
//			gson.toJson(users, writer);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	static void serializeUser(User u) {
//		Gson gson = new Gson();
//		String jsonRepresentation = gson.toJson(u);
//		try {
//			String contents = new String(Files.readAllBytes(Paths.get("users.json")));
//			System.out.println(contents);
//			jsonRepresentation = contents.substring(0, contents.length() - 1) + "," + jsonRepresentation + "]";
//			System.out.println("Trying");
//		} catch (java.nio.file.NoSuchFileException NoSuchFileException) {
//			System.out.println("Catching");
//			jsonRepresentation = "[ " + jsonRepresentation + "]";
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				FileWriter filewriter = new FileWriter("users.json");
//				filewriter.write(jsonRepresentation);
//				filewriter.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public static void main(String[] args) {
////        ArrayList<Song> songs = readSongsJSON();
////        ArrayList<User> users = readUsersJSON();
////
////        Playlist chill = new Playlist("Chill");
////        chill.addSongToPlaylist(songs.get(0));
////        chill.addSongToPlaylist(songs.get(1));
////
////        users.get(0).addPlaylist(chill);
////
////        writeUsersJSON(users);
//
////        // Play a song by finding it's name
////        String songName = "Fake Plastic Trees";
////        ArrayList<Song> matches = lookUpSongBySongName(songName, songs);
////        Main player = new Main();
////        player.mp3play(matches.get(1).getSongId() + ".mp3");
//
////        // Add a new user to json file
////        User u = new User("bozo", "test");
////        serializeUser(u);
////
////
////        // Check if user exists (login)
////        String username = "myusername110";
////        String password = "mypassword011";
////        User currentUSer = new User();
////        ArrayList<User> users = readUsersJSON();
////        for (User user : users)
////            if (user.getUsername().compareTo(username) == 0 & user.getPassword().compareTo(password) == 0) 
////            {
////                currentUSer = user;
////                break;
////            }
////        if (currentUSer.getUsername() == null)
////            System.out.println("Not logged in");
////        else System.out.println("Logged IN :)");
//	}
//}
