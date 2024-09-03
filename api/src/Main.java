import com.google.gson.Gson;
import model.Scene;
import spark.Spark;

import java.sql.SQLException;
import java.util.List;

public class Main {

    private static final Gson json = new Gson();

    public static void main(String[] args) throws SQLException {

        DAO scenesDAO = new DAO();

        List<Scene> scenes = scenesDAO.findAllScene();

        String scenesJson = json.toJson(scenes);

        Spark.get("/", (req, res) -> scenesJson);
    }
}

