package engine;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String title;
    private String text;
    private List<String> options;

    public Quiz() {
        options = new ArrayList<>(4);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public boolean addOption(String opt) {
        if (options.size() > 4) {
            return false;
        }
        return options.add(opt);
    }
}
