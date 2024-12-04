import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Task {
    private String description;
    private String dueTime;
    private boolean isCompleted;

    public Task(String description, String dueTime) {
        this.description = description;
        this.dueTime = dueTime;
        this.isCompleted = false;
    }

    public String getDescription() {
        return description;
    }

    public String getDueTime() {
        return dueTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markCompleted() {
        isCompleted = true;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[Completed] " : "[Pending] ") +
               "Task: " + description + " | Due: " + dueTime;
    }
}

public class TaskScheduler extends JFrame {
    private ArrayList<Task> tasks = new ArrayList<>();
    private DefaultListModel<String> taskListModel = new DefaultListModel<>();
    private JList<String> taskList = new JList<>(taskListModel);

    public TaskScheduler() {
        setTitle("Daily Task Scheduler");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new BorderLayout());

        // Task List Panel
        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setBorder(BorderFactory.createTitledBorder("Tasks"));
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList);
        taskPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton addTaskButton = new JButton("Add Task");
        JButton markCompletedButton = new JButton("Mark Completed");
        JButton deleteTaskButton = new JButton("Delete Task");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(addTaskButton);
        buttonPanel.add(markCompletedButton);
        buttonPanel.add(deleteTaskButton);
        buttonPanel.add(exitButton);

        add(taskPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addTaskButton.addActionListener(e -> addTask());
        markCompletedButton.addActionListener(e -> markTaskCompleted());
        deleteTaskButton.addActionListener(e -> deleteTask());
        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void addTask() {
        JTextField descriptionField = new JTextField();
        JTextField dueTimeField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Task Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Due Time (e.g., HH:MM):"));
        inputPanel.add(dueTimeField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Add Task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String description = descriptionField.getText().trim();
            String dueTime = dueTimeField.getText().trim();

            if (!description.isEmpty() && !dueTime.isEmpty()) {
                Task newTask = new Task(description, dueTime);
                tasks.add(newTask);
                taskListModel.addElement(newTask.toString());
                JOptionPane.showMessageDialog(this, "Task added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void markTaskCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            Task selectedTask = tasks.get(selectedIndex);
            if (!selectedTask.isCompleted()) {
                selectedTask.markCompleted();
                taskListModel.set(selectedIndex, selectedTask.toString());
                JOptionPane.showMessageDialog(this, "Task marked as completed!");
            } else {
                JOptionPane.showMessageDialog(this, "Task is already completed!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as completed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            taskListModel.remove(selectedIndex);
            JOptionPane.showMessageDialog(this, "Task deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskScheduler::new);
    }
}