package frame;

import controller.CRUController;
import dao.Task;
import dao.TaskDAO;
import dao.TaskTableModel;
import service.CenterTableCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TodoListGUI extends JFrame{
    private TaskDAO taskDAO;
    private final JTable table;
    private final JTextField taskNameField;
    private final JCheckBox taskStatusField;
    private final JButton addButton;
    private final JButton updateButton;
    private final JButton deleteButton;

    public TodoListGUI() {
        try {
            taskDAO = new TaskDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("To-Do List");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Table
        table = new JTable();
        refreshTasksView();

        // Controls panel
        JPanel controlsPanel = new JPanel();
        taskNameField = new JTextField(20);
        taskStatusField = new JCheckBox("Completed");
        addButton = new JButton("Add Task");
        updateButton = new JButton("Update Task");
        deleteButton = new JButton("Delete Task");

        addButton.setBackground(Color.GREEN);
        updateButton.setBackground(Color.YELLOW);
        deleteButton.setBackground(Color.RED);

        addButton.setOpaque(true);
        updateButton.setOpaque(true);
        deleteButton.setOpaque(true);

        // Set foreground (text) color for buttons
        addButton.setForeground(Color.BLACK);
        updateButton.setForeground(Color.BLACK);
        deleteButton.setForeground(Color.magenta);

       //  Set background color for buttons
        addButton.setBackground(Color.GREEN);
        updateButton.setBackground(Color.YELLOW);
        deleteButton.setBackground(Color.RED);

        controlsPanel.add(taskNameField);
        controlsPanel.add(taskStatusField);
        controlsPanel.add(addButton);
        controlsPanel.add(updateButton);
        controlsPanel.add(deleteButton);

        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(controlsPanel, BorderLayout.NORTH);

        // Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTask();
            }
        });
        deleteButton.addActionListener(e -> deleteTask());
        setVisible(true);
    }


    private void refreshTasksView() {
        try {
            List<Task> tasks = taskDAO.getAllTasks();
            TaskTableModel model = new TaskTableModel(tasks);
            table.setModel(model);

            CenterTableCellRenderer centerRenderer = new CenterTableCellRenderer();
            for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
                table.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addTask() {
        String name = taskNameField.getText();
        boolean status = taskStatusField.isSelected();

        try {
            taskDAO.addTask(new Task(name, status));
            refreshTasksView();


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public  void updateTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
            String name = taskNameField.getText();
            boolean status = taskStatusField.isSelected();

            try {
                taskDAO.updateTask(new Task(id, name, status));
                refreshTasksView();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

            try {
                taskDAO.deleteTask(id);
                refreshTasksView();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
