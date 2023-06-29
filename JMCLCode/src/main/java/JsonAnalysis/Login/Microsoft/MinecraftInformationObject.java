package JsonAnalysis.Login.Microsoft;

import java.net.URL;

public class MinecraftInformationObject {
    private String id;
    private String name;
    private SkinsAndCapes[] skins;
    private SkinsAndCapes[] capes;
    protected class SkinsAndCapes{
        private String id;
        private String state;
        private URL url;
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
