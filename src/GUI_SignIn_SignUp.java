import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI_SignIn_SignUp {
    private JPanel MainPanel;
    private JPanel LoginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signinButton;
    private JButton signUpButton;

    public GUI_SignIn_SignUp() {
        // Login button listener
        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                User currentUSer = new User();
                ArrayList<User> users = JsonHelperMethods.readUsersJSON();
                for (User user : users)
                    if (user.getUsername().compareTo(username) == 0 & user.getPassword().compareTo(password) == 0) {
                        currentUSer = user;
                        break;
                    }
                if (currentUSer.getUsername() == null) {
                    JOptionPane.showMessageDialog(null, "Username or password is incorrect.");
                } else {
                    JOptionPane.showMessageDialog(null, "You're logged in successfully :)");
                }
            }
        });

        //Sign up button listener
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                // Add a new user to json file
                ArrayList<User> users = JsonHelperMethods.readUsersJSON();
                User u = new User("bozo", "test");
                if (!users.contains(u)) {
                    JsonHelperMethods.writeUsersJSON(users);
                    JOptionPane.showMessageDialog(null, "You're signed up successfully :)");
                } else {

                }
            }

    });
}

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI_SignIn_SignUp");
        frame.setContentPane(new GUI_SignIn_SignUp().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
