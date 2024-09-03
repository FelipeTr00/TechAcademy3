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

    public static void main(String[] args) throws SQLException {

        DAO scenesDAO = new DAO();
        DAO itemsDAO = new DAO();

        // Recupera as cenas e itens do banco de dados
        List<Scene> scenes = scenesDAO.findAllScene();
        List<Item> items = itemsDAO.findAllItem();

        // Cria um mapa para combinar cenas e itens
        Map<String, Object> combinedData = new HashMap<>();
        combinedData.put("scenes", scenes);
        combinedData.put("items", items);

        // Converte o mapa em um único JSON
        String combinedJson = json.toJson(combinedData);

        // Configura a rota raiz ("/") para retornar o JSON combinado
        Spark.get("/", (req, res) -> {
            res.type("application/json");
            return combinedJson;
        });
    }
}

