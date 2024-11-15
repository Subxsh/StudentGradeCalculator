import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;

class Student {
    String name, rollNo, dob;
    int tamil, english, maths, science, social;
    double average;
    char averageGrade;
    char tamilGrade, englishGrade, mathsGrade, scienceGrade, socialGrade;

    Student(String name, String rollNo, String dob, int tamil, int english, int maths, int science, int social) {
        this.name = name;
        this.rollNo = rollNo;
        this.dob = dob;
        this.tamil = tamil;
        this.english = english;
        this.maths = maths;
        this.science = science;
        this.social = social;
        calculateGrades();
    }

    private void calculateGrades() {
        tamilGrade = calculateGrade(tamil);
        englishGrade = calculateGrade(english);
        mathsGrade = calculateGrade(maths);
        scienceGrade = calculateGrade(science);
        socialGrade = calculateGrade(social);
        int total = tamil + english + maths + science + social;
        average = total / 5.0;
        averageGrade = calculateGrade((int) average);
    }

    private char calculateGrade(int mark) {
        if (mark >= 90) return 'A';
        else if (mark >= 80) return 'B';
        else if (mark >= 70) return 'C';
        else if (mark >= 60) return 'D';
        else return 'F';
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n"
                + "Tamil: " + tamilGrade + "\n"
                + "English: " + englishGrade + "\n"
                + "Maths: " + mathsGrade + "\n"
                + "Science: " + scienceGrade + "\n"
                + "Social: " + socialGrade + "\n\n"
                + "Average Grade: " + averageGrade;
    }
}

public class StudentGradeCalculator extends JFrame implements ActionListener {
    LinkedList<Student> studentList = new LinkedList<>();
    Hashtable<String, Student> studentMap = new Hashtable<>();
    JTextField nameField, rollNoField, dobField, tamilField, englishField, mathsField, scienceField, socialField;
    JTextArea outputArea;
    JButton addStudentButton, viewResultButton, sortButton, searchByNameButton, sortByAverageButton;
    JPanel inputPanel;

    public StudentGradeCalculator() {
        // Frame settings
        setTitle("Student Grade Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // UI setup
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(34, 40, 49));

        // Top panel with buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(new Color(57, 62, 70));
        addStudentButton = new JButton("Add Student");
        viewResultButton = new JButton("View Result");
        sortButton = new JButton("Sort Options");
        styleButton(addStudentButton);
        styleButton(viewResultButton);
        styleButton(sortButton);
        topPanel.add(addStudentButton);
        topPanel.add(viewResultButton);
        topPanel.add(sortButton);
        addStudentButton.addActionListener(this);
        viewResultButton.addActionListener(this);
        sortButton.addActionListener(this);

        // Input panel for dynamic content
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(10, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(34, 40, 49));

        // Output area for results
        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        outputArea.setForeground(Color.WHITE);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        outputArea.setBackground(new Color(34, 40, 49));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 173, 181), 1));

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.SOUTH);
        add(inputPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 173, 181));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        inputPanel.removeAll();
        switch (command) {
            case "Add Student" -> setupAddStudentUI();
            case "View Result" -> setupViewResultUI();
            case "Sort Options" -> setupSortUI();
        }
        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void setupAddStudentUI() {
        nameField = new JTextField(15);
        rollNoField = new JTextField(15);
        dobField = new JTextField(15);
        tamilField = new JTextField(5);
        englishField = new JTextField(5);
        mathsField = new JTextField(5);
        scienceField = new JTextField(5);
        socialField = new JTextField(5);
        addLabeledField("Name:", nameField);
        addLabeledField("Roll Number:", rollNoField);
        addLabeledField("Date of Birth (YYYY-MM-DD):", dobField);
        addLabeledField("Tamil:", tamilField);
        addLabeledField("English:", englishField);
        addLabeledField("Maths:", mathsField);
        addLabeledField("Science:", scienceField);
        addLabeledField("Social:", socialField);
        JButton submitButton = new JButton("Submit");
        styleButton(submitButton);
        submitButton.addActionListener(e -> addStudent());
        inputPanel.add(submitButton);
    }

    private void setupViewResultUI() {
        rollNoField = new JTextField(15);
        dobField = new JTextField(15);
        addLabeledField("Roll Number:", rollNoField);
        addLabeledField("Date of Birth (YYYY-MM-DD):", dobField);
        JButton searchButton = new JButton("Search");
        styleButton(searchButton);
        searchButton.addActionListener(e -> viewResult());
        inputPanel.add(searchButton);
    }

    private void setupSortUI() {
        searchByNameButton = new JButton("Search by Name");
        sortByAverageButton = new JButton("Sort by Average");
        styleButton(searchByNameButton);
        styleButton(sortByAverageButton);
        searchByNameButton.addActionListener(e -> searchByName());
        sortByAverageButton.addActionListener(e -> sortByAverage());
        inputPanel.add(searchByNameButton);
        inputPanel.add(sortByAverageButton);
    }

    private void addLabeledField(String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(label);
        inputPanel.add(textField);
    }

    private void addStudent() {
        try {
            String name = nameField.getText().trim();
            String rollNo = rollNoField.getText().trim();
            String dob = dobField.getText().trim();
            int tamil = Integer.parseInt(tamilField.getText().trim());
            int english = Integer.parseInt(englishField.getText().trim());
            int maths = Integer.parseInt(mathsField.getText().trim());
            int science = Integer.parseInt(scienceField.getText().trim());
            int social = Integer.parseInt(socialField.getText().trim());
            Student student = new Student(name, rollNo, dob, tamil, english, maths, science, social);
            studentList.add(student);
            studentMap.put(rollNo, student);
            outputArea.setText("Student added successfully!");
            clearFields();
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter valid marks and fill all fields.");
        }
    }

    private void viewResult() {
        String rollNo = rollNoField.getText().trim();
        String dob = dobField.getText().trim();
        Student student = studentMap.get(rollNo);
        if (student != null && student.dob.equals(dob)) {
            outputArea.setText(student.toString());
        } else {
            outputArea.setText("Student not found or invalid credentials.");
        }
    }

    private void searchByName() {
        String name = JOptionPane.showInputDialog("Enter student name:");
        StringBuilder result = new StringBuilder("Search Results:\n\n");
        for (Student student : studentList) {
            if (student.name.equalsIgnoreCase(name)) {
                result.append(student.toString()).append("\n\n");
            }
        }
        outputArea.setText(result.length() > 0 ? result.toString() : "No students found with that name.");
    }

    private void sortByAverage() {
        studentList.sort(Comparator.comparingDouble(s -> -s.average)); // Descending
        displayStudents();
    }

    private void displayStudents() {
        StringBuilder output = new StringBuilder("Sorted Student List:\n\n");
        for (Student student : studentList) {
            output.append(student.toString()).append("\n\n");
        }
        outputArea.setText(output.toString());
    }

    private void clearFields() {
        nameField.setText("");
        rollNoField.setText("");
        dobField.setText("");
        tamilField.setText("");
        englishField.setText("");
        mathsField.setText("");
        scienceField.setText("");
        socialField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeCalculator::new);
    }
}
