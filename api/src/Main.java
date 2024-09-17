import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import model.Item;
import model.Scene;
import model.User;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    private static final Gson json = new Gson();
    private static DAO DAO;

    public static void main(String[] args) {

        Spark.setPort(5150);


        Spark.before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
        }); // Tentando corrigir ploblema de acesso no navegador, não deu certo ainda...

        DAO = new DAO();

        get("/", (req, res) -> {

            String username = "root";
            Integer currentScene;
            List<Scene> scenes;
            List<Item> items;

            try {
                currentScene = DAO.getCurrentSceneForUser(username);

                if (currentScene != null) {
                    scenes = DAO.findScenesByCurrentScene(currentScene);
                    items = DAO.findItemsByScene(currentScene);

                Map<String, Object> responseApi = new HashMap<>();
                responseApi.put("scenes", scenes);
                responseApi.put("items", items);

                res.type("application/json");
                    return json.toJson(responseApi);
                }
                else {
                    return json.toJson(new ErrorResponse("Usuário não encontrado."));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return json.toJson(new ErrorResponse("Erro de conexão MySQL."));
            }
        });

        get("/:username", (req, res) -> {
            String username = req.params(":username");
            Integer currentScene;

            try {
                currentScene = DAO.getCurrentSceneForUser(username);
                if (currentScene != null) {
                List<Scene> scenes = DAO.findScenesByCurrentScene(currentScene);
                List<Item> items = DAO.findItemsByScene(currentScene);

                Map<String, Object> responseApi = new HashMap<>();
                responseApi.put("scenes", scenes);
                responseApi.put("items", items);

                res.type("application/json");
                    return json.toJson(responseApi);
                }
                else {
                    return json.toJson(new ErrorResponse("Usuário não encontrado."));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return json.toJson(new ErrorResponse("Erro de conexão MySQL."));
            }
        });

        // INSERIR USUÁRIO - ajustar Json e CORS 
        post("/insert-user", (req, res) -> {
            try {
                User user = json.fromJson(req.body(), User.class);

                if (user.getName() == null || user.getPassword() == null) {
                    res.status(400);
                    return json.toJson(new ErrorResponse("Nome ou senha inválido."));
                }

                DAO.insertUser(user);

                res.status(201);
                return json.toJson(new SuccessResponse("Usuário criado com sucesso."));
            } catch (JsonSyntaxException e) {
                res.status(400);
                return json.toJson(new ErrorResponse("JSON inválido."));
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return json.toJson(new ErrorResponse("Erro ao criar usuário."));
            }
        });

        post("/login", Main::handle);

        post("/execute-command", (req, res) -> {
            try {
                Map<String, String> commandJson = json.fromJson(req.body(), Map.class);
                String command = commandJson.get("command");

                if (command == null || command.isEmpty()) {
                    res.status(400);
                    return json.toJson(new ErrorResponse("Insira um comando."));
                }

                String[] commandUse = command.split(" ");

                // COMANDO USE <- ATUALIZAR CHECK E START
                if (commandUse[0].equalsIgnoreCase("use")) {
                    String rightCommand = command.substring(command.indexOf(" ") + 1).trim();
                    int targetScene = DAO.getTargetScene(0, rightCommand);

                    if (targetScene == -1) {
                        res.status(404);
                        return json.toJson(new ErrorResponse("Tente outro comando."));
                    }

                    Map<String, Object> responseApi = new HashMap<>();
                    responseApi.put("target", targetScene);

                    res.type("application/json");
                    return json.toJson(responseApi);
                } else {
                    res.status(400);
                    return json.toJson(new ErrorResponse("Tente outro comando."));
                }
            } catch (JsonSyntaxException e) {
                res.status(400);
                return json.toJson(new ErrorResponse("Erro no JSON."));
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return json.toJson(new ErrorResponse("Erro no banco de dados."));
            }
        });

    }

    private static Object handle(Request req, Response res) {
        try {
            Map<String, String> credentials = json.fromJson(req.body(), Map.class);
                    String username = credentials.get("username");
                    String password = credentials.get("password");

            if (username == null || password == null) {
                res.status(400);
                return json.toJson(new ErrorResponse("Erro de login."));
            }

            int currentScene = DAO.getCurrentScene(username, password);
            res.status(200);
            return "{\"current_scene\": " + currentScene + "}";

        } catch (SQLException e) {
            res.status(401);
            return json.toJson(new ErrorResponse("Erro de login."));
        } catch (JsonSyntaxException e) {
            res.status(400);
            return json.toJson(new ErrorResponse("JSON inválido."));
        }
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

            private static class SuccessResponse {
                private String message;

                public SuccessResponse(String message) {
                    this.message = message;
                }

                public String getMessage() {
                    return message;
                }
            }
}
