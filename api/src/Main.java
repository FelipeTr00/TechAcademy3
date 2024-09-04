import com.google.gson.Gson;
import model.Item;
import model.Scene;
import spark.Spark;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final Gson json = new Gson();
    private static DAO DAO;

    public static void main(String[] args) throws SQLException {

        Spark.setPort(5150);
        DAO = new DAO();

        Spark.get("/", (req, res) -> {
            String username = "root";

            Integer currentScene;
            List<Scene> scenes;
            List<Item> items;

            try {
                currentScene = DAO.getCurrentSceneForUser(username);

                if (currentScene != null) {
                    scenes = DAO.findScenesByCurrentScene(currentScene);
                    items = DAO.findItemsByScene(currentScene);

                    Map<String, Object> responseData = new HashMap<>();
                    responseData.put("scenes", scenes);
                    responseData.put("items", items);

                    res.type("application/json");
                    return json.toJson(responseData);
                }
                else return json.toJson(new ErrorResponse("Usuário não encontrado."));

            } catch (SQLException e) {
                e.printStackTrace();

                return json.toJson(new ErrorResponse("Erro de conexão MySQL."));
            }
        });

        Spark.get("/:username", (req, res) -> {
            String username = req.params(":username");
            Integer currentScene;

            try {
                currentScene = DAO.getCurrentSceneForUser(username);
            } catch (SQLException e) {
                e.printStackTrace();

                return json.toJson(new ErrorResponse("Erro de conexão MySQL."));
            }

            if (currentScene != null) {
                List<Scene> scenes = DAO.findScenesByCurrentScene(currentScene);
                List<Item> items = DAO.findItemsByScene(currentScene);

                Map<String, Object> responseData = new HashMap<>();
                responseData.put("scenes", scenes);
                responseData.put("items", items);

                res.type("application/json");
                return json.toJson(responseData);
            }
            else return json.toJson(new ErrorResponse("Usuário não encontrado."));

        });
    }

    private static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
