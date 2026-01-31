package essential;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldLoader {
    public WorldLoader(){
    }

    public static Map<String, Location> loadWorld(String jsonFilePath) throws IOException{

        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        Map<String, Object> worldData = JSONParser.parse(jsonContent);
        if (worldData == null){
            throw new IOException("Chyba při analyzaci JSON souboru.");
        }
        Map<String, Location> locations = new HashMap<>();

        @SuppressWarnings("unchecked")
        List<Object> locationsList = (List<Object>) worldData.get("locations");
        for (Object locObj : locationsList){
            @SuppressWarnings("unchecked")
            Map<String, Object> locData = (Map<String, Object>) locObj;

            String id = (String) locData.get("id");
            String name = (String) locData.get("name");
            String description = (String) locData.get("description");
            Location location = new Location(name, description);
            location.setId(id);
            boolean requiresQuest = (Boolean) locData.get("requiresQuest");
            if (requiresQuest){
                String questId = (String) locData.get("requiredQuestId");
                if (questId != null){
                    location.setRequiresQuest(questId);
                }
            }
            boolean requiresItem = (Boolean) locData.get("requiresItem");
            if (requiresItem){
                String itemId = (String) locData.get("requiredItemId");
                if (itemId != null){
                    location.setRequiresQuest(itemId);
                }
            }
            locations.put(id, location);
        }

        for (Object locObj : locationsList){
            @SuppressWarnings("unchecked")
            Map<String, Object> locData = (Map<String, Object>) locObj;
            String id = (String) locData.get("id");
            Location location = locations.get(id);

            @SuppressWarnings("unchecked")
            Map<String, Object> connections = (Map<String, Object>) locData.get("connections");

            for (Map.Entry<String, Object> entry : connections.entrySet()){
                location.addConnection(entry.getKey(), (String) entry.getValue());
            }
        }
        return locations;
    }

    public static String getStartLocation(String jsonFilePath) throws IOException{
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        Map<String, Object> worldData = JSONParser.parse(jsonContent);

        return (String) worldData.get("startLocation");
    }
}
