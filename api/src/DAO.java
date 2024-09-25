import model.Scene;
import model.Item;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private Connection conn;

    public DAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(
                    // "jdbc:mysql://localhost:3306/game"
                    "jdbc:mysql://localhost:3307/game",
                    "user",
                    "123"
            );
            System.out.println("Conexão com MySQL OK! Link: http://localhost:5150/");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro de Driver.");
        } catch (SQLException e) {
            System.err.println("Falha na conexão MySQL.");
        }
    }

    public List<Scene> findScenesByCurrentScene(int currentScene) throws SQLException {
        List<Scene> scenes = new ArrayList<>();
        String sql = "SELECT * FROM scenes WHERE scene_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, currentScene);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
            Scene scene = new Scene();
            scene.setSceneId(rs.getInt("scene_id"));
            scene.setTitle(rs.getString("title"));
            scene.setTarget(rs.getInt("target"));
            scene.setDescription(rs.getString("description"));
            scene.setRightCommand(rs.getString("right_command"));
            scene.setInventory(rs.getString("inventory"));

                scenes.add(scene);}}}

        return scenes;
    }

    public List<Item> findItemsByScene(int sceneId) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE scene_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sceneId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    Item item = new Item();
                    item.setItemId(rs.getInt("item_id"));
                    item.setItem(rs.getString("item"));
                    item.setDescrItem(rs.getString("descr_item"));
                    item.setSceneId(rs.getInt("scene_id"));
                    items.add(item);

                }
            }
        }

        return items;
    }

    public Integer getCurrentSceneForUser(String thisscene) throws SQLException {
        String sql = "SELECT current_scene FROM users WHERE current_scene = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, thisscene);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("current_scene");
                } else {
                    return null;
                }
            }
        }
    }

    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, password) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Sucesso!");
            } else {
                System.out.println("Erro ao criar usuário.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int getCurrentScene(String name, String password) throws SQLException {
        String sql = "SELECT current_scene FROM users WHERE name = ? AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, password);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("current_scene");
                } else {
                    throw new SQLException("Erro de login.");
                }
            }
        }
    }

    public Scene getSceneById(int sceneId) throws SQLException {
        String sql = "SELECT * FROM scenes WHERE scene_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sceneId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Scene scene = new Scene();
                    scene.setSceneId(rs.getInt("scene_id"));
                    scene.setDescription(rs.getString("description"));
                    scene.setTitle(rs.getString("title"));
                    scene.setTarget(rs.getInt("target"));
                    return scene;
                } else {
                    return null;
                }
            }
        }
    }

    // CONSTRUINDO OS COMANDOS

    // COMANDO USE

    public int getTargetScene(int currentScene, String command) throws SQLException {
        String sql = "SELECT target FROM scenes WHERE right_command = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, command);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("target");
                } else {
                    return -1; // reinicia o jogo
                }
            }
        }
    }

    public boolean updateUserCurrentScene(String username, int newSceneId) throws SQLException {
        String sql = "UPDATE users SET current_scene = ? WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newSceneId);
            stmt.setString(2, username);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna verdadeiro se deu certo.
        }
    }

    // COMANDO SAVE


}
