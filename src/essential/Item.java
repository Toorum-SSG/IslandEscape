package essential;

public class Item {
    private String name;
    private String description;
    private int size;

    public Item(String name, String description, int size) {
        this.name = name;
        this.description = description;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSize() {
        return size;
    }
}
