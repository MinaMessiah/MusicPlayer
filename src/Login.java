import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		usernameField = new JTextField();
		usernameField.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		usernameField.setBounds(150, 70, 157, 32);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		passwordField.setBounds(150, 123, 157, 32);
		frame.getContentPane().add(passwordField);

		JLabel lblNewLabel = new JLabel("Welcome to StreamX");
		lblNewLabel.setFont(new Font("Cooper Black", Font.PLAIN, 26));
		lblNewLabel.setBounds(63, 11, 327, 39);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Username: ");
		lblNewLabel_1.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 77, 136, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Password: ");
		lblNewLabel_2.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(10, 130, 136, 14);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel errorMessageLabel = new JLabel("");
		errorMessageLabel.setForeground(new Color(178, 34, 34));
		errorMessageLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
		errorMessageLabel.setBounds(20, 155, 392, 25);
		frame.getContentPane().add(errorMessageLabel);

//	    ------------- START Signin button listener section -------------
		JButton signinButton = new JButton("Login");
		signinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if (username.compareTo("") == 0 || password.compareTo("") == 0) {
					errorMessageLabel.setText("You must ener Username and Password");
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
							System.out.println("It matches");
							currentUSer = user;
							break;
						}
					}
				}
				// Check if user logged in successfully
				if (currentUSer.getUsername() == null) {
					errorMessageLabel.setText("Username or password is incorrect.");
					passwordField.setText("");
//					JOptionPane.showMessageDialog(null, "Username or password is incorrect.");
				} else {
					JOptionPane.showMessageDialog(null, "You're logged in successfully :)");
				}
			}
		});
//	    ------------- END Signin button listener section -------------

		signinButton.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		signinButton.setBounds(22, 190, 175, 39);
		frame.getContentPane().add(signinButton);

		JButton signupButton = new JButton("Create Account");
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if (username.compareTo("") == 0 || password.compareTo("") == 0) {
					errorMessageLabel.setText("You must ener Username and Password");
					return;
				}
				// Add a new user to JSON file
				ArrayList<User> users = JsonHelperMethods.readUsersJSON();
//				Create a user with a hashed password 
				User u = new User(username, BCrypt.hashpw(password, BCrypt.gensalt()));
//				Check if username is NOT taken already 
				if (!users.contains(u)) {
					users.add(u);
					JsonHelperMethods.writeUsersJSON(users);

					JOptionPane.showMessageDialog(null, "You're signed up successfully.");
				} else {
					errorMessageLabel.setText("Username is already taken.");

//					JOptionPane.showMessageDialog(null, "USername is taken");
				}

			}
		});
		signupButton.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		signupButton.setBounds(237, 190, 175, 39);
		frame.getContentPane().add(signupButton);

	}
}
