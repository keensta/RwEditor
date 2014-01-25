package me.keensta.colonists.enums;

public enum Skill {

    CONSTRUCTION("Level_Construction", "Construction"), GROWING("Level_Growing", "Growing"), RESEARCH("Level_Research",
            "Research"), MINING("Level_Mining", "Mining"), SHOOTING("Level_Shooting", "Shooting"), MELEE("Level_Melee",
            "Melee"), SOCIAL("Level_Social", "Social"), COOKING("Level_Cooking", "Cooking"), MEDICINE("Level_Medicine",
            "Medicine"), ARTISTIC("Level_Artistic", "Artistic"), CRAFTING("Level_Crafting", "Crafting");

    private String tag;
    private String friendlyName;

    private Skill(String tag, String friendlyName) {
        this.tag = tag;
        this.friendlyName = friendlyName;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return friendlyName;
    }

}
