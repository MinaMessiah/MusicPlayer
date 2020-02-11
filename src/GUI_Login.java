import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI_Login {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private int xMouse, yMouse;
	protected int xLocation, yLocation;

	/**
	 * Create the application.
	 */
	public GUI_Login() {
		initialize();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Login window = new GUI_Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Welcome to StreamX");
		frame.setBounds(100, 100, 800, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setOpacity(0.9f);
		try {frame.setIconImage(ImageIO.read(new File("icons/icon.png")));}
		catch (IOException e1) {e1.printStackTrace();}

		
		JPanel container = new JPanel();
		container.setBackground(Color.DARK_GRAY);
		container.setBounds(0, 0, 800, 650);
		frame.getContentPane().add(container);
		container.setLayout(null);

		JLabel titlelbl = new JLabel("Welcome to StreamX");
		titlelbl.setBounds(123, 59, 515, 94);
		titlelbl.setForeground(Color.WHITE);
		titlelbl.setFont(new Font("Cooper Black", Font.PLAIN, 48));
		container.add(titlelbl);

		usernameField = new JTextField();
		usernameField.setBounds(399, 224, 176, 28);
		usernameField.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		usernameField.setColumns(10);
		container.add(usernameField);

		passwordField = new JPasswordField();
		passwordField.setBounds(399, 276, 176, 28);
		passwordField.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		container.add(passwordField);

		JLabel label_1 = new JLabel("Username: ");
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(233, 227, 103, 22);
		label_1.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		container.add(label_1);

		JLabel label_2 = new JLabel("Password: ");
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(235, 279, 101, 22);
		label_2.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		container.add(label_2);

		JButton signinButton = new JButton("Login");
		signinButton.setForeground(Color.WHITE);
		signinButton.setBackground(Color.GRAY);
		signinButton.setBounds(123, 379, 213, 47);
		signinButton.setFont(new Font("Cooper Black", Font.PLAIN, 15));
		container.add(signinButton);

		JButton signupButton = new JButton("Create Account");
		signupButton.setForeground(Color.WHITE);
		signupButton.setBackground(Color.GRAY);
		signupButton.setBounds(399, 379, 213, 47);
		signupButton.setFont(new Font("Cooper Black", Font.PLAIN, 15));
		container.add(signupButton);

		JLabel messageLabel = new JLabel("");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(new Color(178, 34, 34));
		messageLabel.setFont(new Font("Cooper Black", Font.PLAIN, 14));
		messageLabel.setBounds(209, 330, 392, 25);
		container.add(messageLabel);

		JLabel exitLabel = new JLabel();
		try { exitLabel = new JLabel(new ImageIcon(ImageIO.read(new File("icons/close.png"))));} 
		catch (IOException e1) {e1.printStackTrace();}		
		exitLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		exitLabel.setForeground(new Color(255, 0, 0));
		exitLabel.setFont(new Font("Haettenschweiler", Font.PLAIN, 17));
		exitLabel.setBounds(759, 11, 31, 28);
		container.add(exitLabel);

		JLabel minimizeLabel = new JLabel();
		try { minimizeLabel = new JLabel(new ImageIcon(ImageIO.read(new File("icons/min.png"))));} 
		catch (IOException e1) {e1.printStackTrace();}
		minimizeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setState(Frame.ICONIFIED);
			}
		});
		minimizeLabel.setBounds(718, 11, 31, 28);
		container.add(minimizeLabel);

//	    ------------- START Signin button listener section -------------
		signinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if (username.compareTo("") == 0 || password.compareTo("") == 0) {
					messageLabel.setText("You must ener Username and Password");
					messageLabel.setForeground(new Color(178, 34, 34));
					return;
				}
				User currentUSer = new User();
				ArrayList<User> users = JsonHelperMethods.readUsersJSON();

				for (User user : users) {
					// Verify username matches what user entered
					if (user.getUsername().compareTo(username) == 0) {
						// If we find a matching username, then verify password entered matches what's
						// stored in the JSON file
						if (BCrypt.checkpw(password, user.getPassword())) {
							currentUSer = user;
							break;
						}
					}
				}
				// Check if user logged in successfully
				if (currentUSer.getUsername() == null) {
					messageLabel.setText("Username or password is incorrect.");
					messageLabel.setForeground(new Color(178, 34, 34));
					passwordField.setText("");
//					JOptionPane.showMessageDialog(null, "Username or password is incorrect.");
				} else {
					frame.getContentPane().setVisible(false);
					frame.dispose();
					GUI_Main.loggedInUser = currentUSer;
					GUI_Main.main(null);
				}
			}
		});
//	    ------------- END Signin button listener section -------------

		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String username = usernameField.getText().replace(" ", "");
				String password = String.valueOf(passwordField.getPassword()).replace(" ", "");
				if (username.isEmpty() && password.isEmpty()) {
					messageLabel.setText("You must enter Username and Password");
					messageLabel.setForeground(new Color(178, 34, 34));
					return;
				} else if (username.length() < 6 && password.length() < 6) {
					messageLabel.setText("Usernanme and password must be greater than 5 characters.");
					messageLabel.setForeground(new Color(178, 34, 34));
					return;
				}
				// Add a new user to JSON file
				ArrayList<User> users = JsonHelperMethods.readUsersJSON();
				//Create a user with a hashed password 
				User u = new User(username, BCrypt.hashpw(password, BCrypt.gensalt()));
				//Check if username is NOT taken already 
				if (!users.contains(u)) {
					users.add(u);
					JsonHelperMethods.writeUsersJSON(users);
					messageLabel.setForeground(Color.GREEN);
					messageLabel.setText("You're signed up successfully.");
				} else {
					messageLabel.setText("Username is already taken.");
					messageLabel.setForeground(new Color(178, 34, 34));
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
