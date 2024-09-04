package model;

import java.util.Objects;

public class User {
    private int userId;
    private String name;
    private String password;
    private int currentScene;

    public User() {}

    public User(int id, String name, String password, int currentScene) {
        this.userId = id;
        this.name = name;
        this.password = password;
        this.currentScene = currentScene;
    }

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(int currentScene) {
        this.currentScene = currentScene;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + userId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", current_scene='" + currentScene + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(name, user.name) && Objects.equals(password, user.password)
                && Objects.equals(currentScene, user.currentScene);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, password, currentScene);
    }
}
