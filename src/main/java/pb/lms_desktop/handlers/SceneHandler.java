package pb.lms_desktop.handlers;

import javafx.scene.Scene;
import javafx.util.Pair;

import java.util.HashMap;

public class SceneHandler {
    private Scene currentScene;
    private HashMap<String, Scene> scenes;

    public SceneHandler(Scene currentScene) {
        this.currentScene = currentScene;
        this.scenes = new HashMap<>();
        scenes.put("dashboard", currentScene);
    }

    public boolean switchScene(String label) {
        Scene newScene = this.scenes.get(label);
        if (newScene == null) return false;
        this.currentScene = newScene;
        return true;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public HashMap<String, Scene> getScenes() {
        return scenes;
    }

    public void addScene(String label, Scene scene) {
        scenes.put(label, scene);
    }
}
