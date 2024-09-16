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
        });

        DAO = new DAO();

        // Rota GET padrão
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

                    Map<String, Object> responseData = new HashMap<>();
                    responseData.put("scenes", scenes);
                    responseData.put("items", items);

                    res.type("application/json");
                    return json.toJson(responseData);
                } else {
                    return json.toJson(new ErrorResponse("Usuário não encontrado."));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return json.toJson(new ErrorResponse("Erro de conexão MySQL."));
            }
        });

        // Rota GET para usuário específico
        get("/:username", (req, res) -> {
            String username = req.params(":username");
            Integer currentScene;

            try {
                currentScene = DAO.getCurrentSceneForUser(username);
                if (currentScene != null) {
                    List<Scene> scenes = DAO.findScenesByCurrentScene(currentScene);
                    List<Item> items = DAO.findItemsByScene(currentScene);

                    Map<String, Object> responseData = new HashMap<>();
                    responseData.put("scenes", scenes);
                    responseData.put("items", items);

                    res.type("application/json");
                    return json.toJson(responseData);
                } else {
                    return json.toJson(new ErrorResponse("Usuário não encontrado."));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return json.toJson(new ErrorResponse("Erro de conexão MySQL."));
            }
        });

        // Rota POST para inserir usuário
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
                return json.toJson(new ErrorResponse("Formato JSON inválido."));
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return json.toJson(new ErrorResponse("Erro ao criar usuário."));
            }
        });

        // Rota POST para login
        post("/login", Main::handle);

        // Nova rota POST para /execute-command
        post("/execute-command", (req, res) -> {
            try {
                // Parse do JSON recebido
                Map<String, String> commandData = json.fromJson(req.body(), Map.class);
                String command = commandData.get("command");

                if (command == null || command.isEmpty()) {
                    res.status(400);
                    return json.toJson(new ErrorResponse("Insira um comando."));
                }

                // Verifica se a primeira palavra é "use"
                String[] commandParts = command.split(" ");
                if (!commandParts[0].equalsIgnoreCase("use")) {
                    res.status(400);
                    return json.toJson(new ErrorResponse("Comando inválido, tente novamente."));
                }

                // Extrai a parte relevante do comando para a consulta
                String rightCommand = command.substring(command.indexOf(" ") + 1).trim();

                // Executa a consulta no banco de dados
                int targetScene = DAO.getTargetScene(0, rightCommand); // Modifique o currentScene conforme necessário

                if (targetScene == -1) {
                    res.status(404);
                    return json.toJson(new ErrorResponse("Tente outro comando."));
                }

                // Prepara a resposta JSON
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("target", targetScene);

                res.type("application/json");
                return json.toJson(responseData);

            } catch (JsonSyntaxException e) {
                res.status(400);
                return json.toJson(new ErrorResponse("Formato JSON inválido."));
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return json.toJson(new ErrorResponse("Verifique o banco de dados."));
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
                return json.toJson(new ErrorResponse("Nome e senha são obrigatórios."));
            }

            int currentScene = DAO.getCurrentScene(username, password);
            res.status(200);
            return "{\"current_scene\": " + currentScene + "}";

        } catch (SQLException e) {
            res.status(401);
            return json.toJson(new ErrorResponse("Nome de usuário ou senha inválidos."));
        } catch (JsonSyntaxException e) {
            res.status(400);
            return json.toJson(new ErrorResponse("Formato JSON inválido."));
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
