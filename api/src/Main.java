import com.google.gson.Gson;
import model.User;
import spark.Request;
import spark.Response;
import spark.Spark;
import java.sql.SQLException;
import java.util.Map;
import static spark.Spark.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Main {

    private static final Gson json = new Gson();
    private static DAO DAO;
    private static Play play;

    public static void main(String[] args) {

        Spark.port(5150);

        Spark.before((_, res) -> {
            res.header("Access-Control-Allow-Origin",
                    "*");
            res.header("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers",
                    "Content-Type, Authorization, X-Requested-With");
        });

        DAO = new DAO();
        play = new Play(DAO);

        get("/", (_, res) -> {
            res.type("application/json");
            return play.getScenesAndItems("root");
        });

        get("/:scene", (req, res) -> {
            String scene = req.params(":scene");
            res.type("application/json");
            return play.getScenesAndItems(scene);
        });

        post("/insert-user", (req, res) -> {
            try {
                User user = json.fromJson(req.body(), User.class);

                if (user.getName() == null || user.getPassword() == null) {
                    res.status(400);
                    return json.toJson(new Play.ErrorResponse("Erro ao criar usuário."));
                }

                DAO.insertUser(user);

                res.status(201);
                return json.toJson( new Play.ResponseOk("Usuário criado com sucesso."));
            }
                catch (SQLException e) {
                    return json.toJson(new Play.ErrorResponse("Erro ao criar usuário."));
                }
        });

        post("/login", Main::handle);

        // Comandos
        post("/execute-command", (req, res) -> {
            Type mapType = new TypeToken<Map<String, String>>(){}.getType(); // Verificar se está certo
            Map<String, String> commandJson = json.fromJson(req.body(), mapType);

            String command = commandJson.get("command");
            res.type("application/json");
            return play.executeCommand(command);
        });

    }

    private static Object handle(Request req, Response res) throws SQLException {
        Map<String, String> credentials = json.fromJson(req.body(), Map.class);

        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            res.status(400);
            return json.toJson(new Play.ErrorResponse("Erro de login."));
        }

        int currentScene = DAO.getCurrentScene(username, password);
        res.status(200);
        return "{\"current_scene\": " + currentScene + "}";
    }
}
