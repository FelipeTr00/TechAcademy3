import model.Scene;
import model.Item;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private Connection conn;

    public DAO() {
        this.conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/game",
                    "root",
                    "123"
            );
            System.out.println("Conexão com MySQL OK! Link: http://localhost:5150");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro de Driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Falha na conexão MySQL.");
            e.printStackTrace();
        }
    }

    public List<Scene> findAllScene() throws SQLException {
        List<Scene> scenes = new ArrayList<>();
        String sql = "SELECT * FROM scenes s WHERE scene_id IS NOT NULL;";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
            System.out.println("Scenes: " + scenes.size());

        } catch (SQLException e) {
            System.out.println("Falha na consulta com o banco.");
            e.printStackTrace();
        }

        return scenes;

    }


    public List<Item> findAllItem() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items s WHERE item_id IS NOT NULL;";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item();

                item.setItemId(rs.getInt("item_id"));
                item.setItem(rs.getString("item"));
                item.setDescrItem(rs.getString("descr_item"));
                item.setSceneId(rs.getInt("scene_id"));

                items.add(item);
            }

            System.out.println("Items: " + items.size());

        } catch (SQLException e) {
            System.out.println("Falha na consulta com o banco.");
            e.printStackTrace();
        }

        return items;

    }

    public List<User> createUser() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "INSERT INTO users (name, password) VALUES (?,?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();

                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setCurrentScene(rs.getInt("current_scene"));
                user.setPassword(rs.getString("password"));

                users.add(user);
            }

            System.out.println("User: " + users.size());

        } catch (SQLException e) {
            System.out.println("Falha na consulta com o banco.");
            e.printStackTrace();
        }

        return users;
    }

}


