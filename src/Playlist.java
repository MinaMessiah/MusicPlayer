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

    public void addSongToPlaylist(Song song) {
        if (this.songs == null)
            this.songs = new ArrayList<Song>();
        if (!this.songs.contains(song)){
			System.out.println("Song doesnt exist!");
        	this.songs.add(song);}
        else System.out.println("Song already exists!");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Playlist newPlaylist = (Playlist) obj;
        return name.compareTo(newPlaylist.name) == 0;
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
		return "Playlist{" +
				"name='" + name + '\'' +
				'}';
	}
}