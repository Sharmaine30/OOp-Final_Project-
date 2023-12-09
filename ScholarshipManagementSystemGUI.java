
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScholarshipManagementSystemGUI {
    private static final String ADMIN_EMAIL = "admin";
    private static final String ADMIN_PASSWORD = "adminpass";
    private static final String USER_EMAIL = "user";
    private static final String USER_PASSWORD = "userpass";

    private static List<Scholarship> scholarships = new ArrayList<>();
    private static List<Applicant> applicants = new ArrayList<>();
    private static List<User> users = new ArrayList<>();

    private static User currentUser;

    public static void main(String[] args) {
        initializeData();

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }

    private static void initializeData() {
        scholarships.add(new Scholarship("Scholarship A", "Description A", "Open", 100, 50, "2023-12-31"));
        scholarships.add(new Scholarship("Scholarship B", "Description B", "Closed", 50, 10, "2023-11-30"));

        applicants.add(new Applicant("Applicant 1", "Scholarship A", "Pending", "2023-11-15"));
        applicants.add(new Applicant("Applicant 2", "Scholarship B", "Approved", "2023-10-25"));

        users.add(new User("admin", "Admin Program", "Admin Year", ""));
        users.add(new User("user", "User Program", "User Year", ""));
    }

    static class LoginFrame extends JFrame {
        private JTextField emailField;
        private JPasswordField passwordField;
    
        public LoginFrame() {
            setTitle("Login");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(300, 150);
            setLocationRelativeTo(null);
    
            JPanel panel = new JPanel(new BorderLayout());
    
            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            JLabel emailLabel = new JLabel("Email:");
            emailField = new JTextField();
            JLabel passwordLabel = new JLabel("Password:");
            passwordField = new JPasswordField();
            JButton loginButton = new JButton("Login");
    
            loginButton.addActionListener(e -> {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
    
                if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                    currentUser = new User(ADMIN_EMAIL, "Admin Program", "Admin Year", "");
                    showAdminInterface();
                    dispose();
                } else if (email.equals(USER_EMAIL) && password.equals(USER_PASSWORD)) {
                    currentUser = new User(USER_EMAIL, "User Program", "User Year", "");
                    showUserInterface();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
                }
            });
    
            inputPanel.add(emailLabel);
            inputPanel.add(emailField);
            inputPanel.add(passwordLabel);
            inputPanel.add(passwordField);
    
            panel.add(inputPanel, BorderLayout.CENTER);
            panel.add(loginButton, BorderLayout.SOUTH);
    
            add(panel);
        }
    
    
        private static void showUserInterface() {
            SwingUtilities.invokeLater(() -> {
                UserInterfaceFrame userInterfaceFrame = new UserInterfaceFrame();
                userInterfaceFrame.setVisible(true);
            });
        }
    

    static class AdminInterfaceFrame extends JFrame {
        private static AdminInterfaceFrame instance;
        public AdminInterfaceFrame() {
            instance = this;
            setTitle("Admin Interface");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);

            JButton updateScholarshipButton = new JButton("Update Scholarship and Educational Assistance");
            JButton scholarshipStatusButton = new JButton("Scholarship and Educational Status");
            JButton applicantsListButton = new JButton("Applicants List");
            JButton manageUserButton = new JButton("Manage User");
            JButton logOutButton = new JButton("Log Out");

            updateScholarshipButton.addActionListener(e -> showUpdateScholarshipInterface());
            scholarshipStatusButton.addActionListener(e -> showScholarshipStatusInterface());
            applicantsListButton.addActionListener(e -> showApplicantsListInterface());
            manageUserButton.addActionListener(e -> showManageUserInterface());
            logOutButton.addActionListener(e -> showLoginInterface());

            JPanel panel = new JPanel(new GridLayout(5, 1));
            panel.add(updateScholarshipButton);
            panel.add(scholarshipStatusButton);
            panel.add(applicantsListButton);
            panel.add(manageUserButton);
            panel.add(logOutButton);

            add(panel);
        }
        public static AdminInterfaceFrame getInstance() {
            return instance;
        }
    

        private static void showLoginInterface() {
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            });
        }
    }

    private static void showAdminInterface() {
        SwingUtilities.invokeLater(() -> {
            AdminInterfaceFrame adminInterfaceFrame = new AdminInterfaceFrame();
            adminInterfaceFrame.setVisible(true);
        });
    }

    static class UpdateScholarshipFrame extends JFrame {
        private AdminInterfaceFrame adminInterfaceFrame;
        private JTextField scholarshipNameField;
        private JTextField descriptionField;
        private JTextField statusField;
        private JTextField totalSlotsField;
        private JTextField availableSlotsField;
        private JTextField deadlineField;

        public UpdateScholarshipFrame(AdminInterfaceFrame adminInterfaceFrame, int scholarshipIndex) {
            this.adminInterfaceFrame = adminInterfaceFrame;
            setTitle("Update Scholarship");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);

            Scholarship scholarship = scholarships.get(scholarshipIndex);

            scholarshipNameField = new JTextField(scholarship.getName());
            descriptionField = new JTextField(scholarship.getDescription());
            statusField = new JTextField(scholarship.getStatus());
            totalSlotsField = new JTextField(String.valueOf(scholarship.getTotalSlots()));
            availableSlotsField = new JTextField(String.valueOf(scholarship.getAvailableSlots()));
            deadlineField = new JTextField(scholarship.getDeadline());

            JButton backButton = new JButton("Back");
            JButton saveButton = new JButton("Save");

            backButton.addActionListener(e -> goBackToAdminInterface());
            saveButton.addActionListener(e -> {
                // Update scholarship details
                scholarship.setName(scholarshipNameField.getText());
                scholarship.setDescription(descriptionField.getText());
                scholarship.setStatus(statusField.getText());
                scholarship.setTotalSlots(Integer.parseInt(totalSlotsField.getText()));
                scholarship.setAvailableSlots(Integer.parseInt(availableSlotsField.getText()));
                scholarship.setDeadline(deadlineField.getText());

                JOptionPane.showMessageDialog(this, "Scholarship updated successfully.");
            });


            JPanel panel = new JPanel(new GridLayout(7, 2));
            panel.add(new JLabel("Scholarship Name:"));
            panel.add(scholarshipNameField);
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Status:"));
            panel.add(statusField);
            panel.add(new JLabel("Total Slots:"));
            panel.add(totalSlotsField);
            panel.add(new JLabel("Available Slots:"));
            panel.add(availableSlotsField);
            panel.add(new JLabel("Deadline:"));
            panel.add(deadlineField);
            panel.add(backButton);
            panel.add(saveButton);

            add(panel);
        }
        private void goBackToAdminInterface() {
            SwingUtilities.invokeLater(() -> {
                adminInterfaceFrame.setVisible(true); // Show the AdminInterfaceFrame
                dispose(); // Close the UpdateScholarshipFrame
            });
        }
    }

    private static void showUpdateScholarshipInterface() {
        SwingUtilities.invokeLater(() -> {
            String[] scholarshipNames = scholarships.stream().map(Scholarship::getName).toArray(String[]::new);
    
            JComboBox<String> scholarshipComboBox = new JComboBox<>(scholarshipNames);
            JButton viewButton = new JButton("View");
    
            viewButton.addActionListener(e -> {
                int selectedScholarshipIndex = scholarshipComboBox.getSelectedIndex();
                AdminInterfaceFrame adminInterfaceFrame = AdminInterfaceFrame.getInstance();
                adminInterfaceFrame.dispose(); // Close the AdminInterfaceFrame
                UpdateScholarshipFrame updateScholarshipFrame = new UpdateScholarshipFrame(adminInterfaceFrame, selectedScholarshipIndex);
                updateScholarshipFrame.setVisible(true);
            });
    
            JPanel panel = new JPanel(new GridLayout(2, 1));
            panel.add(scholarshipComboBox);
            panel.add(viewButton);
    
            JOptionPane.showMessageDialog(null, panel, "Select Scholarship", JOptionPane.PLAIN_MESSAGE);
        });
    }

    
       static class ScholarshipStatusFrame extends JFrame {
        public ScholarshipStatusFrame() {
            setTitle("Scholarship and Educational Status");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);

            JTextArea statusArea = new JTextArea();
            statusArea.setEditable(false);

            // Populate status information
            for (Scholarship scholarship : scholarships) {
                statusArea.append("Scholarship: " + scholarship.getName() + "\n");
                statusArea.append("Description: " + scholarship.getDescription() + "\n");
                statusArea.append("Status: " + scholarship.getStatus() + "\n");
                statusArea.append("Total Slots: " + scholarship.getTotalSlots() + "\n");
                statusArea.append("Available Slots: " + scholarship.getAvailableSlots() + "\n");
                statusArea.append("Deadline: " + scholarship.getDeadline() + "\n\n");
            }

            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> dispose());

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JScrollPane(statusArea), BorderLayout.CENTER);
            panel.add(backButton, BorderLayout.SOUTH);

            add(panel);
        }
    }

    private static void showScholarshipStatusInterface() {
        SwingUtilities.invokeLater(() -> {
            ScholarshipStatusFrame scholarshipStatusFrame = new ScholarshipStatusFrame();
            scholarshipStatusFrame.setVisible(true);
        });
    }
}

    static class ApplicantsListFrame extends JFrame {
        public ApplicantsListFrame() {
            setTitle("Applicants List");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);

            JTextArea applicantsArea = new JTextArea();
            applicantsArea.setEditable(false);

            // Populate applicants list
            for (Applicant applicant : applicants) {
                applicantsArea.append("Applicant Name: " + applicant.getName() + "\n");
                applicantsArea.append("Scholarship: " + applicant.getScholarship() + "\n");
                applicantsArea.append("Application Status: " + applicant.getStatus() + "\n");
                applicantsArea.append("Date: " + applicant.getDate() + "\n\n");
            }

            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> dispose());

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JScrollPane(applicantsArea), BorderLayout.CENTER);
            panel.add(backButton, BorderLayout.SOUTH);

            add(panel);
        }
    }

    private static void showApplicantsListInterface() {
        SwingUtilities.invokeLater(() -> {
            ApplicantsListFrame applicantsListFrame = new ApplicantsListFrame();
            applicantsListFrame.setVisible(true);
        });
    }

    static class ManageUserFrame extends JFrame {
        public ManageUserFrame() {
            setTitle("Manage User");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);

            JTextArea usersArea = new JTextArea();
            usersArea.setEditable(false);

            // Populate user list
            for (User user : users) {
                usersArea.append("Username: " + user.getUsername() + "\n");
                usersArea.append("Program: " + user.getProgram() + "\n");
                usersArea.append("Year Level: " + user.getYearLevel() + "\n");
                usersArea.append("Scholarship: " + user.getScholarship() + "\n\n");
            }

            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> dispose());

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JScrollPane(usersArea), BorderLayout.CENTER);
            panel.add(backButton, BorderLayout.SOUTH);

            add(panel);
        }
    }

    private static void showManageUserInterface() {
        SwingUtilities.invokeLater(() -> {
            ManageUserFrame manageUserFrame = new ManageUserFrame();
            manageUserFrame.setVisible(true);
        });
    }

    static class Scholarship {
        private String name;
        private String description;
        private String status;
        private int totalSlots;
        private int availableSlots;
        private String deadline;

        public Scholarship(String name, String description, String status, int totalSlots, int availableSlots, String deadline) {
            this.name = name;
            this.description = description;
            this.status = status;
            this.totalSlots = totalSlots;
            this.availableSlots = availableSlots;
            this.deadline = deadline;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getTotalSlots() {
            return totalSlots;
        }

        public void setTotalSlots(int totalSlots) {
            this.totalSlots = totalSlots;
        }

        public int getAvailableSlots() {
            return availableSlots;
        }

        public void setAvailableSlots(int availableSlots) {
            this.availableSlots = availableSlots;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }
    }

    static class Applicant {
        private String name;
        private String scholarship;
        private String status;
        private String date;

        public Applicant(String name, String scholarship, String status, String date) {
            this.name = name;
            this.scholarship = scholarship;
            this.status = status;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public String getScholarship() {
            return scholarship;
        }

        public String getStatus() {
            return status;
        }

        public String getDate() {
            return date;
        }
    }

    static class User {
        private String username;
        private String program;
        private String yearLevel;
        private String scholarship;

        public User(String username, String program, String yearLevel, String scholarship) {
            this.username = username;
            this.program = program;
            this.yearLevel = yearLevel;
            this.scholarship = scholarship;
        }

        public String getUsername() {
            return username;
        }

        public String getProgram() {
            return program;
        }

        public String getYearLevel() {
            return yearLevel;
        }

        public String getScholarship() {
            return scholarship;
        }
    }


        static class UserInterfaceFrame extends JFrame {
            public UserInterfaceFrame() {
                setTitle("User Interface");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(400, 300);
                setLocationRelativeTo(null);
    
                JButton scholarshipButton = new JButton("Scholarship and Educational Assistance");
                JButton applicationsButton = new JButton("Applications");
                JButton logOutButton = new JButton("Log Out");
    
                scholarshipButton.addActionListener(e -> showScholarshipSelectionInterface());
                applicationsButton.addActionListener(e -> showApplicationsInterface());
                logOutButton.addActionListener(e -> {
                    System.exit(0);
                });
                
            
    
                JPanel panel = new JPanel(new GridLayout(3, 1));
                panel.add(scholarshipButton);
                panel.add(applicationsButton);
                panel.add(logOutButton);
    
                add(panel);
            }
    
            private static void showLoginInterface() {
                SwingUtilities.invokeLater(() -> {
                    LoginFrame loginFrame = new LoginFrame();
                    loginFrame.setVisible(true);
                    UserInterfaceFrame userInterfaceFrame = new UserInterfaceFrame();
                    userInterfaceFrame.dispose(); // Close the UserInterfaceFrame
                });
            }
        }
    
        private static void showUserInterface() {
            SwingUtilities.invokeLater(() -> {
                UserInterfaceFrame userInterfaceFrame = new UserInterfaceFrame();
                userInterfaceFrame.setVisible(true);
            });
        }
    
        private static void showScholarshipSelectionInterface() {
            SwingUtilities.invokeLater(() -> {
                String[] scholarshipNames = scholarships.stream().map(Scholarship::getName).toArray(String[]::new);
    
                JComboBox<String> scholarshipComboBox = new JComboBox<>(scholarshipNames);
                JButton applyNowButton = new JButton("Apply Now");

                
                applyNowButton.addActionListener(e -> {
    // Close the current interface
    Window window = SwingUtilities.windowForComponent(applyNowButton);
    if (window != null) {
        window.dispose();
    }
    
    // Open the ApplicationFormInterface
    showApplicationFormInterface(scholarshipComboBox.getSelectedItem().toString());
});

    
                JPanel panel = new JPanel(new GridLayout(2, 1));
                panel.add(scholarshipComboBox);
                panel.add(applyNowButton);
    
                JOptionPane.showMessageDialog(null, panel, "Select Scholarship", JOptionPane.PLAIN_MESSAGE);
            });
        }
    
        private static void showApplicationFormInterface(String selectedScholarship) {
    SwingUtilities.invokeLater(() -> {
        JFrame applicationFormFrame = new JFrame("Application Form");
        applicationFormFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        applicationFormFrame.setSize(400, 500);
        applicationFormFrame.setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(0, 2)); // 0 makes the number of rows dynamic

        // Personal Data Fields
        formPanel.add(new JLabel("Full Name:"));
        JTextField fullNameField = new JTextField();
        formPanel.add(fullNameField);

        formPanel.add(new JLabel("Date of Birth:"));
        JTextField dobField = new JTextField();
        formPanel.add(dobField);

        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        formPanel.add(emailField);

        // Academic Data Fields
        formPanel.add(new JLabel("School Name:"));
        JTextField schoolNameField = new JTextField();
        formPanel.add(schoolNameField);

        formPanel.add(new JLabel("Program:"));
        JTextField programField = new JTextField();
        formPanel.add(programField);

        formPanel.add(new JLabel("Year Level:"));
        JTextField yearLevelField = new JTextField();
        formPanel.add(yearLevelField);

        // Family Data Fields
        formPanel.add(new JLabel("Parent/Guardian Name:"));
        JTextField guardianNameField = new JTextField();
        formPanel.add(guardianNameField);

        formPanel.add(new JLabel("Family Income:"));
        JTextField familyIncomeField = new JTextField();
        formPanel.add(familyIncomeField);

        // Buttons
        JButton backButton = new JButton("Back");
backButton.addActionListener(e -> {
    applicationFormFrame.dispose(); // Close the application form frame
    showUserInterface(); // Open the user interface
});
formPanel.add(backButton);


        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Logic to handle submission
            // For example, create an Applicant object and add to the applicants list
            String fullName = fullNameField.getText();
            String dob = dobField.getText();
            String email = emailField.getText();
            String schoolName = schoolNameField.getText();
            String program = programField.getText();
            String yearLevel = yearLevelField.getText();
            String guardianName = guardianNameField.getText();
            String familyIncome = familyIncomeField.getText();

            
            // You would then create an Applicant object with this data
            // For instance: applicants.add(new Applicant(fullName, selectedScholarship, "Pending", new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
            // Create an Applicant object and add it to the applicants list
Applicant newApplicant = new Applicant(fullName, selectedScholarship, "Pending", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
applicants.add(newApplicant);


            JOptionPane.showMessageDialog(applicationFormFrame, "You have succesfully submitted your application for Scholarship! Please\r\n" + //
                    "wait for further notice. Thank you for your understanding.");
            applicationFormFrame.dispose();
        });
        formPanel.add(submitButton);

        applicationFormFrame.add(formPanel);
        applicationFormFrame.setVisible(true);
    });
}
    
       private static void showApplicationsInterface() {
    SwingUtilities.invokeLater(() -> {
        // Create a JFrame for displaying user-specific applications
        JFrame applicationsFrame = new JFrame("Your Applications");
        applicationsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        applicationsFrame.setSize(800, 600);
        applicationsFrame.setLocationRelativeTo(null);

        // Create a table model to hold the data
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Scholarship Name");
        tableModel.addColumn("Date");
        tableModel.addColumn("Status");
        tableModel.addColumn("Deadline");

        // Populate the table model with applications relevant to the current user
        String currentUserUsername = currentUser.getUsername();
        for (Applicant applicant : applicants) {
            if (applicant.getName().equals(currentUserUsername)) {
                // Find the scholarship associated with the application
                Scholarship relevantScholarship = null;
                for (Scholarship scholarship : scholarships) {
                    if (scholarship.getName().equals(applicant.getScholarship())) {
                        relevantScholarship = scholarship;
                        break;
                    }
                }

                // Add application data to the table model
                tableModel.addRow(new Object[] {
                    applicant.getScholarship(),
                    applicant.getDate(),
                    applicant.getStatus(),
                    relevantScholarship.getDeadline()
                });
            }
        }

        // Create a JTable with the table model
        JTable table = new JTable(tableModel);

        // Add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the JScrollPane to the applicationsFrame
        applicationsFrame.add(scrollPane);

        // Make the applicationsFrAame visible
        applicationsFrame.setVisible(true);
    });
}

    }
