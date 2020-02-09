import java.util.ArrayList;

public class Playlist {
	private String name;
	private ArrayList<Song> songs;

	public Playlist(String name) {
		this.name = name;
		this.songs = new ArrayList<Song>();
	}

	public static void addSongToPlaylist(Playlist playlist, Song song) {
		playlist.songs.add(song);
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}

	public String getPlaylistName() {
		return this.name;
	}

	public boolean addSongToPlaylist(Song song) {
		if (this.songs == null)
			this.songs = new ArrayList<Song>();
		if (!this.songs.contains(song)) {
			System.out.println("Song added!");
			this.songs.add(song);
			return true;
		} else
			System.out.println("Song already exists!");
		return false;
	}

	public void addSongToPlaylist(String songId) {
		Song song = Song.getSongById(songId);
		addSongToPlaylist(song);
	}
	

	public void addSongToPlaylist(String playlistName, Song song) {

	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Playlist) {
			Playlist toCompare = (Playlist) obj;
			return this.name.equals(toCompare.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Playlist{" + "name='" + name + '\'' + '}';
	}
}