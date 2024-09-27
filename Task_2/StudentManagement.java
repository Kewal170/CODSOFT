import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManagement {

    private JFrame frame;
    private JTextField subjectField;
    private JPanel marksPanel;
    private JScrollPane scrollPane;
    private JLabel totalLabel, averageLabel, gradeLabel;
    private JButton calculateButton;
    private JTextField[] marksFields;

    public StudentManagement() {
        // Create main frame
        frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout(10, 10));

        // Left-side panel with input fields and buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(245, 245, 245));
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Add title
        JLabel titleLabel = new JLabel("Student Grade Calculator");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 150, 243));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        leftPanel.add(titleLabel, gbc);

        // Add input for number of subjects
        JLabel subjectLabel = new JLabel("Enter number of subjects:");
        subjectLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        leftPanel.add(subjectLabel, gbc);

        subjectField = new JTextField(10);
        gbc.gridx = 1;
        leftPanel.add(subjectField, gbc);

        // Button to create mark fields dynamically
        JButton generateButton = new RoundedButton("Generate Marks Fields");
        generateButton.setBackground(new Color(0, 153, 204));
        generateButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        leftPanel.add(generateButton, gbc);

        // Panel to hold marks fields dynamically, added in scrollable pane
        marksPanel = new JPanel();
        marksPanel.setLayout(new GridLayout(0, 2, 10, 10)); // Adjusted grid for labels and text fields
        marksPanel.setBackground(new Color(250, 250, 250));
        
        scrollPane = new JScrollPane(marksPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(300, 200)); // Set preferred size to avoid clipping

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        leftPanel.add(scrollPane, gbc);  // Adding scrollPane instead of marksPanel

        // Add Calculate button
        calculateButton = new RoundedButton("Calculate Grade");
        calculateButton.setBackground(new Color(76, 175, 80)); // Green button
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setEnabled(false); // Initially disabled
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        leftPanel.add(calculateButton, gbc);

        // Right-side panel to show results
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(3, 1, 10, 10));
        rightPanel.setBackground(new Color(255, 255, 255));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        totalLabel = new JLabel("Total Marks: ");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setForeground(new Color(33, 33, 33));
        rightPanel.add(totalLabel);

        averageLabel = new JLabel("Average Percentage: ");
        averageLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        averageLabel.setForeground(new Color(33, 33, 33));
        rightPanel.add(averageLabel);

        gradeLabel = new JLabel("Grade: ");
        gradeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gradeLabel.setForeground(new Color(33, 33, 33));
        rightPanel.add(gradeLabel);

        // Add left and right panels to the frame
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);

        // Action listener for Generate button
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberOfSubjects;
                try {
                    numberOfSubjects = Integer.parseInt(subjectField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number of subjects.");
                    return;
                }

                // Remove old mark fields and reset layout
                marksPanel.removeAll();
                marksFields = new JTextField[numberOfSubjects];
                for (int i = 0; i < numberOfSubjects; i++) {
                    JLabel subjectLabel = new JLabel("Subject " + (i + 1) + " Marks:");
                    subjectLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                    marksFields[i] = new JTextField(10);
                    marksPanel.add(subjectLabel);
                    marksPanel.add(marksFields[i]);
                }
                marksPanel.revalidate();
                marksPanel.repaint();

                calculateButton.setEnabled(true); // Enable Calculate button after fields are created
            }
        });

        // Action listener for Calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalMarks = 0;
                int numberOfSubjects = marksFields.length;

                // Calculate total marks
                for (int i = 0; i < numberOfSubjects; i++) {
                    try {
                        int marks = Integer.parseInt(marksFields[i].getText());
                        totalMarks += marks;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Please enter valid marks for all subjects.");
                        return;
                    }
                }

                // Calculate average and grade
                double average = (double) totalMarks / numberOfSubjects;
                char grade = calculateGrade(average);

                // Display results
                totalLabel.setText("Total Marks: " + totalMarks);
                averageLabel.setText("Average Percentage: " + String.format("%.2f", average));
                gradeLabel.setText("Grade: " + grade);
            }
        });

        // Set frame visible
        frame.setVisible(true);
    }

    // Method to calculate grade based on average percentage
    public static char calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return 'A';
        } else if (averagePercentage >= 80) {
            return 'B';
        } else if (averagePercentage >= 70) {
            return 'C';
        } else if (averagePercentage >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    // Custom RoundedButton class for rounded buttons
    public static class RoundedButton extends JButton {
        public RoundedButton(String label) {
            super(label);
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        new StudentManagement();
    }
}
