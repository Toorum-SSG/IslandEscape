package essential;

public class Quest {
    private String id;
    private String name;
    private String description;
    private boolean completed;

    public Quest(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void complete() {
        this.completed = true;
    }
}
