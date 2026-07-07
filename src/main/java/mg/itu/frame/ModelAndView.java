package mg.itu.frame;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private String url;
    private Map<String, Object> data;

    public ModelAndView() {
        this.data = new HashMap<>();
    }

    public ModelAndView(String url) {
        this.url = url;
        this.data = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void addObject(String key, Object value) {
        data.put(key, value);
    }

    // Alias, au cas où on préfère cette écriture
    public void setAttribute(String key, Object value) {
        data.put(key, value);
    }
}
