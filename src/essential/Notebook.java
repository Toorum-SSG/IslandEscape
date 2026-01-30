package essential;
import java.util.ArrayList;
import java.util.List;

public class Notebook {
    private List<String> notes;

    public Notebook() {
        this.notes = new ArrayList<>();
    }

    public void addNote(String note) {
        notes.add(note);
        System.out.println("Zapsáno do poznámek: " + note);
    }

    public void showNotes() {
        System.out.println("\n=== Poznámky ===");
        if (notes.isEmpty()){
            System.out.println("Zatím jsi si nic nezapsal.");
        } else {
            for (int i = 0; i < notes.size(); i++){
                System.out.println((i + 1) + ". " + notes.get(i));
            }
        }
    }
}
