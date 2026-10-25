package mg.itu.frame;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper autour de HttpSession pour éviter l'exposition directe de l'API Servlet.
 * Permet de manipuler les attributs de session via une Map<String, Object>.
 */
public class MySession {

    private Map<String, Object> attributes;

    public MySession() {
        this.attributes = new HashMap<>();
    }

    /**
     * Récupère un attribut de session.
     */
    public Object get(String key) {
        return attributes.get(key);
    }

    /**
     * Définit un attribut de session.
     */
    public void set(String key, Object value) {
        attributes.put(key, value);
    }

    /**
     * Supprime un attribut de session.
     */
    public void remove(String key) {
        attributes.remove(key);
    }

    /**
     * Retourne la map interne des attributs (lecture/écriture).
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Initialise la session à partir d'une map existante (copie).
     */
    public void loadFrom(Map<String, Object> source) {
        if (source != null) {
            this.attributes = new HashMap<>(source);
        }
    }

    /**
     * Retourne tous les noms d'attributs présents dans la session.
     */
    public java.util.Set<String> getAttributeNames() {
        return attributes.keySet();
    }
}
