package dao;

import entity.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private  Connection connection;
    private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public TaskDAO() {
        try {
            this.connection = DatabaseUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * The connection field is shared among all methods in the class.
     * Database connections are generally not thread-safe.
     * If multiple threads attempt to use the same connection simultaneously,
     * it can lead to issues. One approach is to use a separate connection for each thread or employ connection pooling.
     * */
    private Connection getConnection() throws SQLException {
        Connection connection = connectionThreadLocal.get();

        if (connection == null || connection.isClosed()) {
            connection = DatabaseUtil.getConnection();
            connectionThreadLocal.set(connection);
        }

        return connection;
    }


    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTask_name(rs.getString("task_name"));
                task.setStatus(rs.getBoolean("status"));

                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }



    public void addTask(Task task) {
        String sql = "INSERT INTO tasks (task_name, status) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, task.getTask_name());
            ps.setBoolean(2, task.isStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param task
     */
    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET task_name = ?, status = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, task.getTask_name());
            ps.setBoolean(2, task.isStatus());
            ps.setInt(3, task.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param taskId
     */
    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
