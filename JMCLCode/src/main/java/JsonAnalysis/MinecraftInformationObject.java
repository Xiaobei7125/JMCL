package JsonAnalysis;

public class MinecraftInformationObject {
    private String id;
    private String name;
    private SkinsAndCapes[] skins;
    private SkinsAndCapes[] capes;
    public class SkinsAndCapes{
        private String id;
        private String state;
        private String url;
        private String variant;
        private String alias;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
