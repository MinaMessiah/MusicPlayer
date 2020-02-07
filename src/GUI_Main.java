import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.FocusTraversalPolicy;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridLayout;

public class GUI_Main {

	Thread player;
	private boolean playing;
	private JFrame frame;
	private JTextField searchBar;
	private JTable resultsTable;
	private ArrayList<Song> songs;
	private Song lastSelectedSong;
	public static User loggedInUser;

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

		playing = false;
		frame = new JFrame();
		frame.setBackground(Color.DARK_GRAY);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 800, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(new java.awt.Color(102, 102, 102));
		searchPanel.setBounds(0, 0, 795, 50);
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
		searchCombo.setModel(new DefaultComboBoxModel<String>(new String[] { "song", "artist", "genre" }));
		searchCombo.setBackground(new java.awt.Color(102, 102, 102));
		searchCombo.setBounds(116, 0, 96, 50);
		searchPanel.add(searchCombo);
		
		JLabel welcomelbl = new JLabel("Welcome " + loggedInUser.getUsername() );
		welcomelbl.setBounds(560, 8, 225, 34);
		welcomelbl.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		welcomelbl.setForeground(Color.WHITE);
		searchPanel.add(welcomelbl);

		songs = JsonHelperMethods.readSongsJSON();

		ResultsTableModel tableModel = new ResultsTableModel(songs);
		JTable resultsTable_1 = new JTable(tableModel);
		resultsTable_1.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		resultsTable_1.setBounds(10, 61, 764, 392);
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
		scrollPane.setBounds(10, 61, 774, 392);
		scrollPane.setVisible(true);
		frame.getContentPane().add(scrollPane);

		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.DARK_GRAY);
		infoPanel.setBounds(10, 456, 774, 154);
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
		infoPanel.add(playlistPanel, BorderLayout.EAST);

		JLabel lblAddToPlaylist = new JLabel("Add to playlist");
		playlistPanel.add(lblAddToPlaylist);
		lblAddToPlaylist.setVerticalAlignment(SwingConstants.TOP);
		lblAddToPlaylist.setForeground(Color.WHITE);
		lblAddToPlaylist.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblAddToPlaylist.setVisible(false);
		lblAddToPlaylist.setBorder(new EmptyBorder(12, 10, 10, 10));

		JComboBox playlistCombo = new JComboBox();
		playlistCombo.setModel(new DefaultComboBoxModel(new String[] { "Select" }));
		playlistCombo.setForeground(Color.WHITE);
		playlistCombo.setEditable(false);
		playlistCombo.getEditor().getEditorComponent().setBackground(new java.awt.Color(102, 102, 102));
		playlistCombo.setFont(new Font("Cooper Black", Font.PLAIN, 14));
		playlistCombo.setBackground(new java.awt.Color(102, 102, 102));
		playlistCombo.setBounds(116, 0, 96, 50);
		playlistCombo.setVisible(false);
		playlistPanel.add(playlistCombo);

		searchBar_1.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("HERE");
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (searchBar_1.getText().length() > 0)
					lblPlaceHolder.setText("");
				if (searchBar_1.getText().length() >= 3) {
					ArrayList<Song> results = new ArrayList<Song>();
					int selection = searchCombo.getSelectedIndex();
					switch (selection) {
					case 0:
						results = Song.lookUpSongBySongName(searchBar_1.getText(), songs);
						break;
					case 1:
						results = Song.lookUpSongByArtistName(searchBar_1.getText(), songs);
						break;
					case 2:
						results = Song.lookUpSongByGenre(searchBar_1.getText(), songs);
						break;
					default:
						results = null;
						Song.lookUpSongBySongName(searchBar_1.getText(), songs);
						break;
					}
					tableModel.updateTable(results);
				} else {
					lblPlaceHolder.setText(" Type here . . .");
					tableModel.updateTable(songs);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (searchBar_1.getText().length() > 0)
					lblPlaceHolder.setText("");
				if (searchBar_1.getText().length() >= 3) {
					ArrayList<Song> results = new ArrayList<Song>();
					int selection = searchCombo.getSelectedIndex();
					switch (selection) {
					case 0:
						results = Song.lookUpSongBySongName(searchBar_1.getText(), songs);
						break;
					case 1:
						results = Song.lookUpSongByArtistName(searchBar_1.getText(), songs);
						break;
					case 2:
						results = Song.lookUpSongByGenre(searchBar_1.getText(), songs);
						break;
					default:
						results = null;
						Song.lookUpSongBySongName(searchBar_1.getText(), songs);
						break;
					}
					tableModel.updateTable(results);
				} else {
					lblPlaceHolder.setText(" Type here . . .");
					tableModel.updateTable(songs);
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
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!playing) {
					player = new Thread(lastSelectedSong);
					System.out.println("HERE");
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
	}
}
