import java.util.ArrayList;

public class User {
	ArrayList<Playlist> playlists;
	private String username;
	private String password;

	/**
	 * Default user constructor
	 */
	public User() {
		this.username = null;
		this.password = null;
	}

	/**
	 * User constructor that takes in a name and password.
	 * @param username
	 * @param password
	 */
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
			if (playlistName.equals(Playlist.getPlaylistName(playlist)))
				return playlist;
		return null;
	}

	public ArrayList<Playlist> getPlaylists() {
		return this.playlists;
	}

//  ------------- END getters section -------------
	
	/**
	 * Adds a Playlist to the current user.
	 * @param playlist
	 */
	public void addPlaylist(Playlist playlist) {
		if (this.playlists == null)
			this.playlists = new ArrayList<Playlist>();
		if (this.playlists.contains(playlist)) {
			for (Playlist p : this.playlists) {
				if (p.equals(playlist)) {
					for (Song song : Playlist.getSongs(playlist))
						Playlist.addSongToPlaylist(p, song);
					break;
				}
			}
		} else {
			this.playlists.add(playlist);
		}

	}
	
	/**
	 * Updates the user by loading the latest user data into the main user list.
	 */
	public void updateUser() {
		ArrayList<User> users = JsonHelperMethods.readUsersJSON();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(this)) {
				users.set(i, this);
				JsonHelperMethods.writeUsersJSON(users);
				break;
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			User toCompare = (User) obj;
			return this.username.toLowerCase().equals(toCompare.username.toLowerCase());
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
}
