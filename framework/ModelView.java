package etu1822.framework;

import java.util.HashMap;

public class ModelView {
    String view;
    HashMap<String, Object> data = new HashMap<String, Object>();

    public ModelView() {
    }

    public ModelView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void addItem(String key, Object data) {
        this.data.putIfAbsent(key, data);
    }

}
