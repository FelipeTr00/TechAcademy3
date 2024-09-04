import model.Scene;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private Connection conn;

    public DAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/game",
                    "root",
                    "123"
            );
            System.out.println("Conexão com MySQL OK! Link: http://localhost:5150/");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro de Driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Falha na conexão MySQL.");
            e.printStackTrace();
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

                    scenes.add(scene);
                }
            }
        }

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

    public Integer getCurrentSceneForUser(String username) throws SQLException {
        String sql = "SELECT current_scene FROM users WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("current_scene");
                } else {
                    return null;
                }
            }
        }
    }
}
