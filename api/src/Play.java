import com.google.gson.Gson;
import model.Scene;
import model.Item;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ########## CLASSE PARA AÇÕES DO JOGO ##########

public class Play {

    private static final Gson json = new Gson();
    private static DAO DAO;

    public Play(DAO dao) {
        DAO = dao;
    }

    public String getScenesAndItems(String username) {
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

            return json.toJson(responseApi);
            }
            else return json.toJson(new ErrorResponse("Usuário não encontrado."));

        } catch (SQLException e) {
            return json.toJson(new ErrorResponse("Erro de conexão MySQL."));
        }
    }

    public String executeCommand(String command) {
        try {
            if (command == null || command.isEmpty()) {
            return json.toJson(new ErrorResponse("Insira um comando."));
            }
            String[] commandUse = command.split(" ");

            if (commandUse[0].equalsIgnoreCase("use")) {
            String rightCommand = command.substring(command.indexOf(" ") + 1).trim();
                int targetScene = DAO.getTargetScene(0, rightCommand);

                if (targetScene == -1) {
                    return json.toJson(new ErrorResponse("Tente outro comando."));
                }

            Map<String, Object> responseApi = new HashMap<>();
            responseApi.put("target", targetScene);

                return json.toJson(responseApi);
            } else return json.toJson(new ErrorResponse("Tente outro comando."));

        } catch (SQLException e) {
            return json.toJson(new ErrorResponse("Erro no banco de dados."));
        }
    }

//  ######## OPÇÕES DE RESPOSTAS PARA OS ERROS API, CORS E BANCO DE DADOS ##########

        public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }
        public static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
            }
        public String getMessage() {
            return message;
            }
        }
}
