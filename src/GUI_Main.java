import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GUI_Main {

	Thread player;
	private boolean playing;
	private JFrame frame;
	private ArrayList<Song> songs;
	private Song lastSelectedSong;
	public static User loggedInUser = new User("", "");
	public ArrayList<Playlist> playLists;
	private int xMouse, yMouse;
	protected int xLocation, yLocation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Main window = new GUI_Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_Main() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		playLists = loggedInUser.getPlaylists();
		playing = false;
		frame = new JFrame();
		frame.setBackground(Color.DARK_GRAY);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 800, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setOpacity(0.9f);
		try {
			frame.setIconImage(ImageIO.read(new File("icons/icon.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(new java.awt.Color(102, 102, 102));
		searchPanel.setBounds(0, 45, 800, 40);
		frame.getContentPane().add(searchPanel);
		searchPanel.setLayout(null);

		JLabel lblSearch = new JLabel("Search by");
		lblSearch.setBounds(10, 0, 96, 50);
		lblSearch.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblSearch.setForeground(Color.WHITE);
		lblSearch.setHorizontalAlignment(SwingConstants.LEFT);
		searchPanel.add(lblSearch);

		JLabel lblPlaceHolder = new JLabel(" Type here . . .");
		lblPlaceHolder.setBounds(250, 0, 300, 50);
		lblPlaceHolder.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblPlaceHolder.setForeground(Color.LIGHT_GRAY);
		lblPlaceHolder.setHorizontalAlignment(SwingConstants.LEFT);
		searchPanel.add(lblPlaceHolder);

		JTextField searchBar_1 = new JTextField();
		searchBar_1.setBounds(233, 0, 317, 50);
		searchBar_1.setColumns(20);
		searchBar_1.setForeground(Color.WHITE);
		searchBar_1.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		searchBar_1.setBackground(new java.awt.Color(102, 102, 102));
		searchBar_1.setBorder(null);
		searchPanel.add(searchBar_1);

		JComboBox<String> searchCombo = new JComboBox<String>();
		searchCombo.setForeground(Color.WHITE);
		searchCombo.setEditable(false);
		searchCombo.getEditor().getEditorComponent().setBackground(new java.awt.Color(102, 102, 102));
		searchCombo.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		searchCombo.setModel(new DefaultComboBoxModel<String>(new String[] { "Song", "Artist", "Genre" }));
		searchCombo.setBackground(new java.awt.Color(102, 102, 102));
		searchCombo.setBounds(116, 0, 96, 50);
		searchPanel.add(searchCombo);

		JLabel welcomelbl = new JLabel("Welcome " + loggedInUser.getUsername());
		welcomelbl.setBounds(10, 10, 225, 34);
		welcomelbl.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		welcomelbl.setForeground(Color.WHITE);
		frame.getContentPane().add(welcomelbl);

		songs = JsonHelperMethods.readSongsJSON();

		ResultsTableModel tableModel = new ResultsTableModel(songs);
		JTable resultsTable_1 = new JTable(tableModel);
		resultsTable_1.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		resultsTable_1.setBounds(10, 85, 764, 375);
		resultsTable_1.setRowHeight(40);
		resultsTable_1.getColumnModel().getColumn(0).setMaxWidth(500);
		resultsTable_1.getColumnModel().getColumn(2).setMinWidth(0);
		resultsTable_1.getColumnModel().getColumn(2).setMaxWidth(0);
		resultsTable_1.getColumnModel().getColumn(2).setWidth(0);
		resultsTable_1.getColumnModel().getColumn(0).setMinWidth(500);
		resultsTable_1.setTableHeader(null);
		resultsTable_1.setBackground(Color.GRAY);

		JScrollPane scrollPane = new JScrollPane(resultsTable_1);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 95, 774, 365);
		scrollPane.setVisible(true);
		frame.getContentPane().add(scrollPane);

		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.DARK_GRAY);
		infoPanel.setBounds(10, 475, 774, 154);
		frame.getContentPane().add(infoPanel);
		infoPanel.setLayout(new BorderLayout(0, 0));

		JLabel songNameLabel = new JLabel("Song Name");
		songNameLabel.setForeground(Color.WHITE);
		songNameLabel.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		songNameLabel.setVisible(false);
		songNameLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		infoPanel.add(songNameLabel, BorderLayout.NORTH);

		JLabel selectSongLabel = new JLabel("Select song to view information");
		selectSongLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectSongLabel.setForeground(Color.WHITE);
		selectSongLabel.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		infoPanel.add(selectSongLabel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.DARK_GRAY);
		infoPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnPlayPause = new JButton("Play / Stop");
		btnPlayPause.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnPlayPause.setEnabled(false);
		buttonPanel.add(btnPlayPause);

		JButton btnGetSimilarSongs = new JButton("Get Similar Songs");
		btnGetSimilarSongs.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnGetSimilarSongs.setEnabled(false);
		buttonPanel.add(btnGetSimilarSongs);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnLogout.requestFocus();
		searchBar_1.transferFocus();
		buttonPanel.add(btnLogout);

		JButton btnDeleteAccount = new JButton("Delete Account");
		btnDeleteAccount.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPanel.add(btnDeleteAccount);

		JPanel infoSubPanel = new JPanel();
		infoSubPanel.setBackground(Color.DARK_GRAY);
		infoPanel.add(infoSubPanel, BorderLayout.WEST);
		infoSubPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel artistNameLabel = new JLabel("Artist Name");
		infoSubPanel.add(artistNameLabel);
		artistNameLabel.setVerticalAlignment(SwingConstants.TOP);
		artistNameLabel.setForeground(Color.WHITE);
		artistNameLabel.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		artistNameLabel.setVisible(false);
		artistNameLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblGenre = new JLabel("Genre");
		infoSubPanel.add(lblGenre);
		lblGenre.setVerticalAlignment(SwingConstants.TOP);
		lblGenre.setForeground(Color.WHITE);
		lblGenre.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblGenre.setVisible(false);
		lblGenre.setBorder(new EmptyBorder(12, 10, 10, 10));

		JPanel playlistPanel = new JPanel();
		playlistPanel.setBackground(Color.DARK_GRAY);
		playlistPanel.setLayout(new GridBagLayout());
		infoPanel.add(playlistPanel, BorderLayout.EAST);

		JLabel lblAddToPlaylist = new JLabel("Add to playlist");
		GridBagConstraints gbc_lblAddToPlaylist = new GridBagConstraints();
		gbc_lblAddToPlaylist.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddToPlaylist.gridx = 0;
		gbc_lblAddToPlaylist.gridy = 0;
		playlistPanel.add(lblAddToPlaylist, gbc_lblAddToPlaylist);
		lblAddToPlaylist.setVerticalAlignment(SwingConstants.CENTER);
		lblAddToPlaylist.setForeground(Color.WHITE);
		lblAddToPlaylist.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblAddToPlaylist.setVisible(false);
		lblAddToPlaylist.setBorder(new EmptyBorder(12, 10, 10, 10));

		JComboBox<String> playlistCombo = new JComboBox<String>();
		playlistCombo.setModel(new DefaultComboBoxModel<String>(new String[] { "Select" }));
		playlistCombo.setForeground(Color.WHITE);
		playlistCombo.setEditable(false);
		playlistCombo.getEditor().getEditorComponent().setBackground(new java.awt.Color(102, 102, 102));
		playlistCombo.setFont(new Font("Cooper Black", Font.PLAIN, 14));
		playlistCombo.setBackground(new java.awt.Color(102, 102, 102));
		playlistCombo.setBounds(116, 0, 96, 50);
		playlistCombo.setVisible(false);
		GridBagConstraints gbc_playlistCombo = new GridBagConstraints();
		gbc_playlistCombo.insets = new Insets(0, 0, 5, 0);
		gbc_playlistCombo.gridx = 1;
		gbc_playlistCombo.gridy = 0;
		playlistPanel.add(playlistCombo, gbc_playlistCombo);
		
		JLabel playlistMsg = new JLabel("Playlist Message");
		playlistMsg.setVerticalAlignment(SwingConstants.TOP);
		playlistMsg.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		playlistMsg.setVisible(false);
		playlistMsg.setBorder(new EmptyBorder(12, 10, 10, 10));
		GridBagConstraints gbc_playlistMsg = new GridBagConstraints();
		gbc_playlistMsg.gridwidth = 2;
		gbc_playlistMsg.anchor = GridBagConstraints.NORTH;
		gbc_playlistMsg.gridx = 0;
		gbc_playlistMsg.gridy = 1;
		playlistPanel.add(playlistMsg, gbc_playlistMsg);

		JLabel exitLabel = new JLabel();
		try {
			exitLabel = new JLabel(new ImageIcon(ImageIO.read(new File("icons/close.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		exitLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		exitLabel.setForeground(new Color(255, 0, 0));
		exitLabel.setFont(new Font("Haettenschweiler", Font.PLAIN, 17));
		exitLabel.setBounds(759, 11, 31, 28);
		frame.getContentPane().add(exitLabel);

		JLabel minimizeLabel = new JLabel();
		try {
			minimizeLabel = new JLabel(new ImageIcon(ImageIO.read(new File("icons/min.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		minimizeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setState(Frame.ICONIFIED);
			}
		});
		minimizeLabel.setBounds(718, 11, 31, 28);
		frame.getContentPane().add(minimizeLabel);

		for (Playlist p : loggedInUser.getPlaylists()) {
			searchCombo.addItem(p.getPlaylistName());
			playlistCombo.addItem(p.getPlaylistName());
		}
		playlistCombo.addItem("Create playlist");

		searchBar_1.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (searchBar_1.getText().length() > 0)
					lblPlaceHolder.setText("");
				if (searchBar_1.getText().length() >= 3) {
					ArrayList<Song> results = new ArrayList<Song>();
					if (searchCombo.getSelectedIndex() > 2) {
						int selection = searchCombo.getSelectedIndex();
						Playlist selectedPlaylist = loggedInUser.getPlaylist(searchCombo.getSelectedItem().toString());
						if (selection == 0)
							results = Song.lookUpSongBySongName(searchBar_1.getText(), songs);
						else {
							results = Song.lookUpSongBySongName(searchBar_1.getText(), selectedPlaylist.getSongs());
						}
					} else {
						int selection = searchCombo.getSelectedIndex();
						if (selection == 0)
							results = Song.lookUpSongBySongName(searchBar_1.getText(), songs);
						else if (selection == 1)
							results = Song.lookUpSongByArtistName(searchBar_1.getText(), songs);
						else if (selection == 2)
							results = Song.lookUpSongByGenre(searchBar_1.getText(), songs);
						else if (selection > 2) {
							results = loggedInUser.getPlaylist(searchCombo.getSelectedItem().toString()).getSongs();
						} else {
							results = Song.lookUpSongBySongName(searchBar_1.getText(), songs);
						}
					}
					tableModel.updateTable(results);
				} else {
					lblPlaceHolder.setText(" Type here . . .");
					if (searchCombo.getSelectedIndex() > 2) {
						ArrayList<Song> results = loggedInUser.getPlaylist(searchCombo.getSelectedItem().toString())
								.getSongs();
						tableModel.updateTable(results);
					} else {
						tableModel.updateTable(songs);
					}
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (searchBar_1.getText().length() > 0)
					lblPlaceHolder.setText("");
				if (searchBar_1.getText().length() >= 3) {
					ArrayList<Song> results = new ArrayList<Song>();
					if (searchCombo.getSelectedIndex() > 2) {
						int selection = searchCombo.getSelectedIndex();
						Playlist selectedPlaylist = loggedInUser.getPlaylist(searchCombo.getSelectedItem().toString());
						if (selection == 0)
							results = Song.lookUpSongBySongName(searchBar_1.getText(), songs);
						else {
							results = Song.lookUpSongBySongName(searchBar_1.getText(), selectedPlaylist.getSongs());
						}
					} else {
						int selection = searchCombo.getSelectedIndex();
						if (selection == 0)
							results = Song.lookUpSongBySongName(searchBar_1.getText(), songs);
						else if (selection == 1)
							results = Song.lookUpSongByArtistName(searchBar_1.getText(), songs);
						else if (selection == 2)
							results = Song.lookUpSongByGenre(searchBar_1.getText(), songs);
						else if (selection > 2) {
							results = loggedInUser.getPlaylist(searchCombo.getSelectedItem().toString()).getSongs();
						} else {
							results = Song.lookUpSongBySongName(searchBar_1.getText(), songs);
						}
					}
					tableModel.updateTable(results);
				} else {
					lblPlaceHolder.setText(" Type here . . .");
					if (searchCombo.getSelectedIndex() > 2) {
						ArrayList<Song> results = loggedInUser.getPlaylist(searchCombo.getSelectedItem().toString())
								.getSongs();
						tableModel.updateTable(results);
					} else {
						tableModel.updateTable(songs);
					}
				}
			}
		});

		searchBar_1.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				resultsTable_1.clearSelection();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				return;
			}

		});

		resultsTable_1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (resultsTable_1.getSelectedRow() > 0) {
					String lastSelectedSongId = resultsTable_1.getValueAt(resultsTable_1.getSelectedRow(), 2)
							.toString();
					lastSelectedSong = Song.lookUpSongBySongID(lastSelectedSongId, songs);
					selectSongLabel.setVisible(false);
					songNameLabel.setText("Song:     " + lastSelectedSong.getSongName());
					songNameLabel.setVisible(true);
					artistNameLabel.setText("Artist:   " + lastSelectedSong.getSongArtistName());
					artistNameLabel.setVisible(true);
					lblGenre.setText("Genre:   " + lastSelectedSong.getSongGenre());
					lblGenre.setVisible(true);
					btnPlayPause.setEnabled(true);
					btnGetSimilarSongs.setEnabled(true);
					lblAddToPlaylist.setVisible(true);
					playlistCombo.setVisible(true);
				}
			}
		});

		btnDeleteAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account??",
						"Select an Option...", JOptionPane.YES_NO_OPTION);
				if (input == 0) {
					if (JsonHelperMethods.deleteAccount(loggedInUser)) {
						frame.setVisible(false);
						frame.dispose();
						GUI_Login.main(null);
					} else
						JOptionPane.showMessageDialog(null, "Unexpected error!");

				}
			}

		});

		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				frame.dispose();
				GUI_Login.main(null);
			}

		});

		btnGetSimilarSongs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Song> similarSongs = Song.getSimilarSongs(lastSelectedSong.getSimilarityId(), songs);
				tableModel.updateTable(similarSongs);
			}
		});

		btnPlayPause.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!playing) {
					player = new Thread(lastSelectedSong);
					player.start();
					playing = true;
				} else {
					player.stop();
					playing = false;
				}

			}

		});

		for (int i = 0; i < searchCombo.getComponentCount(); i++) {
			if (searchCombo.getComponent(i) instanceof JComponent) {
				((JComponent) searchCombo.getComponent(i)).setBorder(new EmptyBorder(0, 0, 0, 0));
			}

			if (searchCombo.getComponent(i) instanceof AbstractButton) {
				((AbstractButton) searchCombo.getComponent(i)).setBorderPainted(false);
			}
		}

		playlistCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//User selects create new playlist 
				if (playlistCombo.getSelectedIndex() == playLists.size() + 1) {
					String playlistName = JOptionPane.showInputDialog(null, "Playlist name: ", JOptionPane.OK_OPTION);
					if (playlistName != null) {
						if (loggedInUser.getPlaylists().contains(new Playlist(playlistName)))
						{
							playlistMsg.setText("Playlist Exists");
							playlistMsg.setForeground(Color.RED);
							playlistMsg.setVisible(true);
						} else {
							playlistMsg.setText("Playlist Created");
							playlistMsg.setForeground(Color.GREEN);
							playlistMsg.setVisible(true);
							loggedInUser.addPlaylist(new Playlist(playlistName));
							loggedInUser.updateUser();
							searchCombo.addItem(playlistName);
							playlistCombo.insertItemAt(playlistName, playlistCombo.getItemCount() - 1);
						}
					}
				}
				//User selects a playlist to add a song to
				else if (playlistCombo.getSelectedIndex() > 0) {
					for (Playlist p : playLists) {
						if (p.getPlaylistName().equals(playlistCombo.getSelectedItem())) {
							if (p.addSongToPlaylist(lastSelectedSong))
							{
								loggedInUser.updateUser();
								playlistCombo.setSelectedIndex(0);
								playlistMsg.setText("Song Added");
								playlistMsg.setForeground(Color.GREEN);
								playlistMsg.setVisible(true);
								break;
							}
							else
							{
								playlistCombo.setSelectedIndex(0);
								playlistMsg.setText("Song Already Exists");
								playlistMsg.setForeground(Color.RED);
								playlistMsg.setVisible(true);
								break;
							}
						}
					}
				}
			}
		});

		searchCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (searchCombo.getSelectedIndex() > 2) {
					ArrayList<Song> results = loggedInUser.getPlaylist(searchCombo.getSelectedItem().toString())
							.getSongs();
					tableModel.updateTable(results);
					lblSearch.setText("Search in");
				} else {
					tableModel.updateTable(songs);
					searchBar_1.setText("");
					lblSearch.setText("Search by");
					searchBar_1.setEnabled(true);
				}

			}
		});

		JLabel lblDrag = new JLabel("");
		lblDrag.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				xMouse = evt.getX();
				yMouse = evt.getY();
			}
		});
		lblDrag.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				int x = evt.getXOnScreen();
				int y = evt.getYOnScreen();
				xLocation = x - xMouse;
				yLocation = y - yMouse;
				frame.setLocation(xLocation, yLocation);
			}
		});
		lblDrag.setBounds(0, 0, 800, 650);
		frame.getContentPane().add(lblDrag);

		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
