import java.util.ArrayList;

public class User {
    ArrayList<Playlist> playlists;
    private String username;
    private String password;

    public User() {
        this.username = null;
        this.password = null;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.playlists = new ArrayList<Playlist>();
    }

    
//  ------------- START getters section -------------
    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public Playlist getPlaylist(String playlistName) {
        for (Playlist playlist : this.playlists)
            if (playlistName.equals(playlist.getPlaylistName()))
                return playlist;
        return null;
    }

    public ArrayList<Playlist> getPlaylists() {
        return this.playlists;
    }

//  ------------- END getters section -------------
    
    
    public void addPlaylist(Playlist playlist) {
        if (this.playlists == null)
            this.playlists = new ArrayList<Playlist>();
        if (this.playlists.contains(playlist)) {
            System.out.println("Playlist exists already! Adding songs to old play list.");
            for (Playlist p : this.playlists) {
                if (p.equals(playlist)) {
                    for (Song song : playlist.getSongs())
                        p.addSongToPlaylist(song);
                    break;
                }
            }
        } else {
            System.out.println("Adding new playlist.");
            this.playlists.add(playlist);
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        User u = (User) obj;
        return username.compareTo(u.getUsername()) == 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }
}

