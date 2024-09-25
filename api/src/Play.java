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

    public String getScenesAndItems(String thisscene) {
        Integer currentScene;
        List<Scene> scenes;
        List<Item> items;

        try {
            currentScene = DAO.getCurrentSceneForUser(thisscene);

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

    // ########## CONSTRUÇÃO DOS COMANDOS ##########
    public String executeCommand(String command) {
        try {
            switch (command) {
                case "help":
                    command = "help";
                    break;
                case "start":
                    command = "use start";
                    break;
                case "restart":
                    command = "use start";
                    break;
                case "check itens":
                    return json.toJson(new ResponseOk("Itens visualizados"));
            }

            if (command.startsWith("use ") || command.startsWith("get ")) { // Comandos USE e GET
                String rightCommand = command;

                int targetScene = DAO.getTargetScene(-1, rightCommand);

                Map<String, Object> responseApi = new HashMap<>();
                responseApi.put("target", targetScene);

                return json.toJson(responseApi);
            } else {
                return json.toJson(new ErrorResponse("Tente outro comando."));
            }
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
        public static class ResponseOk {
        private String message;

        public ResponseOk(String message) {
            this.message = message;
            }
        public String getMessage() {
            return message;
            }
        }
}
