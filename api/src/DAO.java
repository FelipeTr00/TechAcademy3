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
            System.out.println("Conexão com MySQL OK! Link: http://localhost:4567");
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

    }



