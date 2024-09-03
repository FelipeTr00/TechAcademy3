package model;

public class Scene {

    private int sceneId;
    private String title;
    private String description;
    private int target;
    private String rightCommand;
    private String inventory;

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getRightCommand() {
        return rightCommand;
    }

    public void setRightCommand(String rightCommand) {
        this.rightCommand = rightCommand;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
