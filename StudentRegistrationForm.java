import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentRegistrationForm extends JFrame implements ActionListener {
    private JButton submitButton = new JButton("Submit");
    private JLabel studentRegistrationFormLabel = new JLabel("STUDENT REGISTRATION FORM", SwingConstants.CENTER);
    private JLabel nameLabel = new JLabel("Name");
    private JLabel ageLabel = new JLabel("Age");
    private JLabel departmentLabel = new JLabel("Department");
    private JLabel genderLabel = new JLabel("Gender");
    private JLabel semesterLabel = new JLabel("Semester");
    private JLabel addressLabel = new JLabel("Address");
    private JLabel phoneLabel = new JLabel("Phone");
    private JLabel emailLabel = new JLabel("Email");
    private JTextField nameTextField = new JTextField(20);
    private JTextField ageTextField = new JTextField(3);
    private JTextField departmentTextField = new JTextField(20);
    private JTextField phoneTextField = new JTextField(15);
    private JTextField emailTextField = new JTextField(25);
    private JTextField addressTextField = new JTextField(25);
    private JRadioButton maleGenderRadioButton = new JRadioButton("Male");
    private JRadioButton femaleGenderRadioButton = new JRadioButton("Female");
    private JRadioButton otherGenderRadioButton = new JRadioButton("Other");
    private ButtonGroup genderButtonGroup = new ButtonGroup();
    private JComboBox<String> semesterComboBox = new JComboBox<>();

    public StudentRegistrationForm(String title) {
        this.setTitle(title);
        setupUI();
    }

    public void setupUI() {
        this.setSize(450, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(10, 2, 10, 10));
        this.setupObjects();
        this.setVisible(true);
    }

    private void setupObjects() {
        studentRegistrationFormLabel.setFont(new Font("Arial", Font.BOLD, 18));
        genderButtonGroup.add(maleGenderRadioButton);
        genderButtonGroup.add(femaleGenderRadioButton);
        genderButtonGroup.add(otherGenderRadioButton);
        for (int i = 1; i <= 8; i++)
            semesterComboBox.addItem(Integer.toString(i));

        this.add(studentRegistrationFormLabel);
        this.add(new JLabel());
        this.add(nameLabel);
        this.add(nameTextField);
        this.add(ageLabel);
        this.add(ageTextField);
        this.add(departmentLabel);
        this.add(departmentTextField);
        this.add(genderLabel);
        this.add(new JPanel(new FlowLayout(FlowLayout.LEFT)) {
            {
                add(maleGenderRadioButton);
                add(femaleGenderRadioButton);
                add(otherGenderRadioButton);
            }
        });
        this.add(semesterLabel);
        this.add(semesterComboBox);
        this.add(addressLabel);
        this.add(addressTextField);
        this.add(phoneLabel);
        this.add(phoneTextField);
        this.add(emailLabel);
        this.add(emailTextField);
        this.add(new JLabel());
        this.add(submitButton);

        submitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameTextField.getText();
            String age = ageTextField.getText();
            String department = departmentTextField.getText();
            String gender = getSelectedGender();
            String semester = (String) semesterComboBox.getSelectedItem();
            String address = addressTextField.getText();
            String phone = phoneTextField.getText();
            String email = emailTextField.getText();

            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/your_database_name";
            String user = "your_database_user";
            String password = "your_database_password";

            // SQL Insert query
            String query = "INSERT INTO student_registration (name, age, department, gender, semester, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, age);
                preparedStatement.setString(3, department);
                preparedStatement.setString(4, gender);
                preparedStatement.setString(5, semester);
                preparedStatement.setString(6, address);
                preparedStatement.setString(7, phone);
                preparedStatement.setString(8, email);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Student registered successfully!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String getSelectedGender() {
        if (maleGenderRadioButton.isSelected()) {
            return "Male";
        } else if (femaleGenderRadioButton.isSelected()) {
            return "Female";
        } else {
            return "Other";
        }
    }

    public static void main(String[] args) {
        new StudentRegistrationForm("Student Registration Form");
    }
}