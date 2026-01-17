public class Quest {

    public Quest(String id, String name, String description) {

    }

    public Quest(String id, String name, String description, String requiredItemId, String rewardItemId) {

    }

    public String getId() {}

    public String getName() {}

    public String getDescription() {}

    public boolean isCompleted() {}

    public void complete() {}

    public String getRequiredItemId() {}

    public String getRewardItemId() {}

    public String getPrerequisiteQuestId() {}

    public void setPrerequisiteQuestId(String id) {}
}
