import com.google.gson.Gson;
import model.Item;
import model.Scene;
import model.User;
import spark.Spark;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final Gson json = new Gson();

    public static void main(String[] args) throws SQLException {

        Spark.port(5150);
        DAO userDAO = new DAO();
        DAO scenesDAO = new DAO();
        DAO itemsDAO = new DAO();

        List<User> users = userDAO.createUser();
        List<Scene> scenes = scenesDAO.findAllScene();
        List<Item> items = itemsDAO.findAllItem();

        Map<String, Object> combinedData = new HashMap<>();
        combinedData.put("scenes", scenes);
        combinedData.put("items", items);

        String combinedJson = json.toJson(combinedData);

        Spark.get("/", (req, res) -> {
            res.type("application/json");
            return combinedJson;
        });
    }
}

