import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Song implements Runnable {
	private SongRelease release;
	private SongArtist artist;
	private SongDetails song;

	/**
	 * Song constructor requiring a release, artist, and song.
	 * @param release
	 * @param artist
	 * @param song
	 */
	public Song(SongRelease release, SongArtist artist, SongDetails song) {
		this.release = release;
		this.artist = artist;
		this.song = song;
	}

	/**
	 * Default song constructor
	 */
	public Song() {
	}

//   ------------- START Songs look up section -------------- 
	/**
	 * Looks up a song by name and returns it's matches via an array list
	 * @param songName
	 * @param songs
	 * @return
	 */
	public static ArrayList<Song> lookUpSongBySongName(String songName, ArrayList<Song> songs) {
		ArrayList<Song> matches = new ArrayList<Song>();
		for (Song song : songs)
			if (song.getSongName().toLowerCase().startsWith(songName.toLowerCase()))
				matches.add(song);

		return matches;
	}
	
	/**
	 * Look up song name by artist's name and returns it's matches via an array list.
	 * @param artistName
	 * @param songs
	 * @return
	 */
	public static ArrayList<Song> lookUpSongByArtistName(String artistName, ArrayList<Song> songs) {
		ArrayList<Song> matches = new ArrayList<Song>();
		for (Song song : songs)
			if (song.getSongArtistName().toLowerCase().startsWith(artistName.toLowerCase()))
				matches.add(song);

		return matches;
	}
	
	/**
	 * Look up song by genre returns matches via an array list.
	 * @param genre
	 * @param songs
	 * @return
	 */
	public static ArrayList<Song> lookUpSongByGenre(String genre, ArrayList<Song> songs) {
		ArrayList<Song> matches = new ArrayList<Song>();
		for (Song song : songs)
			if (song.getSongGenre().toLowerCase().contains(genre.toLowerCase()))
				matches.add(song);

		return matches;
	}
	
	/**
	 * Looks up a song by ID and returns the respective song.
	 * @param songID
	 * @param songs
	 * @return
	 */
	public static Song lookUpSongBySongID(String songID, ArrayList<Song> songs) {
		for (Song song : songs)
			if (song.getSongId().toLowerCase().equals(songID.toLowerCase()))
				return song;

		return null;
	}
	
	/**
	 * Get's similar songs by comparing their similarity. If they are similar it adds them to an array list and returns them.
	 * @param similarityId
	 * @param songs
	 * @return
	 */
	public static ArrayList<Song> getSimilarSongs(String similarityId, ArrayList<Song> songs) {
		ArrayList<Song> matches = new ArrayList<Song>();
		for (Song song : songs)
			if (similarityId.compareTo(song.getSimilarityId()) == 0)
				matches.add(song);

		return matches;
	}
//  ------------- END Songs look up section -------------- 

//  -------------- START getters section -----------------
	public String getSongName() {
		return this.release.name;
	}

	public String getSimilarityId() {
		return this.artist.similar;
	}

	public String getSongMatches() {
		return this.artist.similar;
	}

	public String getSongId() {
		return this.song.id;
	}

	public String getSongGenre() {
		return this.artist.terms;
	}

	public String getSongArtistName() {
		return this.artist.name;
	}

//  ---------------- END getters section -----------------

	/**
	 * Try's playing a song and if unable to play catches the exception.
	 * @param songID
	 */
	public static void playSong(String songID) {
		try {
			InputStream is = new CECS327RemoteInputStream(songID);
			Player mp3player = new Player(is);
			mp3player.play();
		} catch (JavaLayerException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Returns a song by it's ID.
	 * @param songId
	 * @return
	 */
	public static Song getSongById(String songId) {
		ArrayList<Song> songs = JsonHelperMethods.readSongsJSON();
		for (Song s : songs) {
			if (s.getSongId().equals(songId))
				return s;
		}
		return new Song();
	}

	@Override
	public void run() {
		try {
//			InputStream is = new CECS327InputStream(this.getSongId() + ".mp3");
			InputStream is = new CECS327RemoteInputStream("imperial.mp3");
			Player mp3player = new Player(is);
			mp3player.play();
		} catch (JavaLayerException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Song) {
			Song toCompare = (Song) obj;
			return this.getSongName().equals(toCompare.getSongName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getSongId() == null) ? 0 : getSongId().hashCode());
		return result;
	}

	@SuppressWarnings("unused")
	private class SongRelease {
		private String id, name;

		public SongRelease(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getID() {
			return Integer.valueOf(this.id);
		}
	}

	@SuppressWarnings("unused")
	private class SongArtist {
		String terms_freq;
		String terms;
		String name;
		String familiarity;
		String longitude;
		String id;
		String location;
		String latitude;
		String similar;
		String hotttnesss;

		public SongArtist(String terms_freq, String terms, String name, String familiarity, String longitude, String id,
				String location, String latitude, String similar, String hotttnesss) {
			this.terms_freq = terms_freq;
			this.terms = terms;
			this.name = name;
			this.familiarity = familiarity;
			this.longitude = longitude;
			this.id = id;
			this.location = location;
			this.latitude = latitude;
			this.similar = similar;
			this.hotttnesss = hotttnesss;
		}
	}

	@SuppressWarnings("unused")
	private class SongDetails {
		String key;
		String mode_confidence;
		String artist_mbtags_count;
		String key_confidence;
		String tatums_start;
		String year;
		String duration;
		String hotttnesss;
		String beats_start;
		String time_signature_confidence;
		String title;
		String bars_confidence;
		String id;
		String bars_start;
		String artist_mbtags;
		String start_of_fade_out;
		String tempo;
		String end_of_fade_in;
		String beats_confidence;
		String tatums_confidence;
		String mode;
		String time_signature;
		String loudness;

		public SongDetails(String key, String mode_confidence, String artist_mbtags_count, String key_confidence,
				String tatums_start, String year, String duration, String hotttnesss, String beats_start,
				String time_signature_confidence, String title, String bars_confidence, String id, String bars_start,
				String artist_mbtags, String start_of_fade_out, String tempo, String end_of_fade_in,
				String beats_confidence, String tatums_confidence, String mode, String time_signature,
				String loudness) {
			this.key = key;
			this.mode_confidence = mode_confidence;
			this.artist_mbtags_count = artist_mbtags_count;
			this.key_confidence = key_confidence;
			this.tatums_start = tatums_start;
			this.year = year;
			this.duration = duration;
			this.hotttnesss = hotttnesss;
			this.beats_start = beats_start;
			this.time_signature_confidence = time_signature_confidence;
			this.title = title;
			this.bars_confidence = bars_confidence;
			this.id = id;
			this.bars_start = bars_start;
			this.artist_mbtags = artist_mbtags;
			this.start_of_fade_out = start_of_fade_out;
			this.tempo = tempo;
			this.end_of_fade_in = end_of_fade_in;
			this.beats_confidence = beats_confidence;
			this.tatums_confidence = tatums_confidence;
			this.mode = mode;
			this.time_signature = time_signature;
			this.loudness = loudness;
		}
	}
}
