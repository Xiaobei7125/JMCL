package jsonAnalysis.download.minecraft.library;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.net.URL;

public class VersionJson {
    private String minecraftArguments;
    private Arguments arguments;
    private AssetIndex assetIndex;
    private String assets;
    private int complianceLevel;
    private Downloads downloads;
    private String id;
    private JavaVersion javaVersion;
    private Libraries[] libraries;
    private Logging logging;
    private String mainClass;
    private int minimumLauncherVersion;
    private String releaseTime;
    private String time;
    private String type;
    public static class Arguments {
        private Game game;
        private Jvm jvm;
        public static Arguments newArgumentObject(Game game,Jvm jvm){
            Arguments newArguments = new Arguments();
            newArguments.game = game;
            newArguments.jvm = jvm;
            return newArguments;
        }

        public Game getGame() {
            return game;
        }

        public Jvm getJvm() {
            return jvm;
        }


        public static class Game {
            private String[] game;
            private GameRules[] rules;
            public GameRules[] getRules() {
                return rules;
            }
            public String[] getGame() {
                return game;
            }
            public static Game newGameObject(String[] game, GameRules[] rules) {
                Game newGame = new Game();
                newGame.game = game;
                newGame.rules = rules;
                return newGame;
            }
            public static class GameRules {
                private Rules[] rules;
                private String[] value;
                public Rules[] getRules() {
                    return rules;
                }
                public String[] getValue() {
                    return value;
                }
                public static class Rules {
                    private String action;
                    private Features features;

                    public Features getFeatures() {
                        return features;
                    }
                    public String getAction() {
                        return action;
                    }

                    public static class Features {
                        @SerializedName("is_demo_user")
                        private boolean isDemoUser;
                        @SerializedName("has_custom_resolution")
                        private boolean hasCustomResolution;

                        public boolean isDemoUser() {
                            return isDemoUser;
                        }

                        public boolean isHasCustomResolution() {
                            return hasCustomResolution;
                        }
                    }
                }
            }
        }

        public static class Jvm {
            private JvmRules[] rules;
            private String[] jvm;
            public static Jvm newJvmObject(JvmRules[] rules, String[] jvm) {
                Jvm newJvm = new Jvm();
                newJvm.rules = rules;
                newJvm.jvm = jvm;
                return newJvm;
            }
            public JvmRules[] getRules() {
                return rules;
            }
            public String[] getJvm() {
                return jvm;
            }

            public static class JvmRules {
                private Rules[] rules;
                private String[] value;

                public Rules[] getRules() {
                    return rules;
                }

                public String[] getValue() {
                    return value;
                }

                public static class Rules {
                    private String action;
                    private Os os;

                    public String getAction() {
                        return action;
                    }

                    public Os getOs() {
                        return os;
                    }

                    public static class Os {
                        private String name;
                        private String version;

                        public String getName() {
                            return name;
                        }

                        public String getVersion() {
                            return version;
                        }
                    }

                }
            }
        }
    }

    public static class AssetIndex {
        private String id;
        private String sha1;
        private int size;
        private int totalSize;
        private URL url;

        public String getId() {
            return id;
        }

        public int getSize() {
            return size;
        }

        public int getTotalSize() {
            return totalSize;
        }

        public String getSha1() {
            return sha1;
        }

        public URL getUrl() {
            return url;
        }
    }

    public static class Downloads {
        private Client client;
        @SerializedName("client_mappings")
        private ClientMappings clientMappings;
        private Server server;
        @SerializedName("server_mappings")
        private ServerMappings serverMappings;

        public Client getClient() {
            return client;
        }

        public ClientMappings getClientMappings() {
            return clientMappings;
        }

        public Server getServer() {
            return server;
        }

        public ServerMappings getServerMappings() {
            return serverMappings;
        }

        public class Client{
            private String sha1;
            private int size;
            private URL url;

            public String getSha1() {
                return sha1;
            }

            public URL getUrl() {
                return url;
            }

            public int getSize() {
                return size;
            }
        }
        public class ClientMappings{
            private String sha1;
            private int size;
            private URL url;
            public String getSha1() {
                return sha1;
            }

            public URL getUrl() {
                return url;
            }

            public int getSize() {
                return size;
            }
        }
        public class Server{
            private String sha1;
            private int size;
            private URL url;
            public String getSha1() {
                return sha1;
            }

            public URL getUrl() {
                return url;
            }

            public int getSize() {
                return size;
            }
        }
        public class ServerMappings{
            private String sha1;
            private int size;
            private URL url;
            public String getSha1() {
                return sha1;
            }

            public URL getUrl() {
                return url;
            }

            public int getSize() {
                return size;
            }
        }
    }

    public static class JavaVersion {
        private String component;
        private int majorVersion;

        public int getMajorVersion() {
            return majorVersion;
        }

        public String getComponent() {
            return component;
        }
    }

    public static class Libraries {
        private Downloads downloads;
        private String name;
        private Natives natives;
        private Rules[] rules;

        public Rules[] getRules() {
            return rules;
        }

        public Downloads getDownloads() {
            return downloads;
        }

        public String getName() {
            return name;
        }
        public class Natives{
            private String osx;
            private String linux;
            private String windows;
        }

        public class Rules{
            private String action;
            private Os os;

            public String getAction() {
                return action;
            }

            public Os getOs() {
                return os;
            }

            public class Os{
                private String name;

                public String getName() {
                    return name;
                }
            }
        }

        public class Downloads{
            private ArtifactAndNatives artifact;
            private Classifiers classifiers;

            public ArtifactAndNatives getArtifact() {
                return artifact;
            }

            public Classifiers getClassifiers() {
                return classifiers;
            }

            public class ArtifactAndNatives{
                private String path;
                private String sha1;
                private int size;
                private URL url;

                public URL getUrl() {
                    return url;
                }

                public int getSize() {
                    return size;
                }

                public String getSha1() {
                    return sha1;
                }

                public String getPath() {
                    return path;
                }
            }
            public class Classifiers{
                @SerializedName("natives-macos")
                private ArtifactAndNatives nativesMacos;
                @SerializedName("natives-linux")
                private ArtifactAndNatives nativesLinux;
                @SerializedName("natives-windows")
                private ArtifactAndNatives nativesWindows;

                public ArtifactAndNatives getNativesLinux() {
                    return nativesLinux;
                }

                public ArtifactAndNatives getNativesMacos() {
                    return nativesMacos;
                }

                public ArtifactAndNatives getNativesWindows() {
                    return nativesWindows;
                }
            }
        }
    }

    public static class Logging {
        private Client client;

        public Client getClient() {
            return client;
        }

        public class Client {
            private String argument;
            private File file;
            private String type;

            public String getType() {
                return type;
            }

            public File getFile() {
                return file;
            }

            public String getArgument() {
                return argument;
            }

            public class File{
                private String id;
                private String sha1;
                private int size;
                private URL url;

                public int getSize() {
                    return size;
                }

                public URL getUrl() {
                    return url;
                }

                public String getSha1() {
                    return sha1;
                }

                public String getId() {
                    return id;
                }
            }
        }
    }
    public static class ArgumentsDeserializer implements JsonDeserializer<Arguments>{
        @Override
        public Arguments deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Arguments.Game game = gameDeserialize(json.getAsJsonObject().get("game"));
            Arguments.Jvm jvm = jvmDeserialize(json.getAsJsonObject().get("jvm"));
            return Arguments.newArgumentObject(game,jvm);
        }
        public Arguments.Game gameDeserialize(JsonElement json) throws JsonParseException {
            JsonArray jsonArray = json.getAsJsonArray();
            int a = 0;
            int b = 0;
            int c = 0;
            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.get(i).isJsonPrimitive()) {
                    a++;
                } else if (jsonArray.get(i).isJsonObject()) {
                    b++;
                }
            }
            String[] game = new String[a];
            Arguments.Game.GameRules[] rules = new Arguments.Game.GameRules[b];
            for(int i = 0;i < rules.length;i++){
                rules[i] = new Arguments.Game.GameRules();
                rules[i].rules = new Arguments.Game.GameRules.Rules[1];
                rules[i].rules[0] = new Arguments.Game.GameRules.Rules();
                rules[i].rules[0].action = "";
                rules[i].rules[0].features = new Arguments.Game.GameRules.Rules.Features();
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.get(i).isJsonObject() && jsonArray.get(i).getAsJsonObject().get("value").isJsonPrimitive()) {
                    rules[c].value = new String[1];
                    c++;
                } else if (jsonArray.get(i).isJsonObject() && jsonArray.get(i).getAsJsonObject().get("value").isJsonArray()) {
                    rules[c].value = new String[jsonArray.get(i).getAsJsonObject().get("value").getAsJsonArray().size()];
                    c++;
                }
            }
            a = 0;
            b = 0;
            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.get(i).isJsonPrimitive()) {
                    game[a] = jsonArray.get(i).getAsString();
                    a++;
                } else if (jsonArray.get(i).isJsonObject() && !jsonArray.get(i).getAsJsonObject().has("value")) {
                    rules[b].rules[0].action = String.valueOf(jsonArray.get(i).getAsJsonObject().get("rules")
                            .getAsJsonArray().get(0).getAsJsonObject().get("action"));
                    rules[b].rules[0].features.hasCustomResolution = Boolean.parseBoolean(String.valueOf(jsonArray.get(i).getAsJsonObject().get("rules")
                            .getAsJsonArray().get(0).getAsJsonObject().get("features").getAsJsonObject().get("has_custom_resolution")));
                    rules[b].rules[0].features.isDemoUser = Boolean.parseBoolean(String.valueOf(jsonArray.get(i).getAsJsonObject().get("rules")
                            .getAsJsonArray().get(0).getAsJsonObject().get("features").getAsJsonObject().get("is_demo_user")));
                    if (jsonArray.get(i).getAsJsonObject().get("value").isJsonObject()) {
                        rules[b].value[0] = jsonArray.get(i).getAsJsonObject().get("value").getAsString();
                    } else if (jsonArray.get(i).getAsJsonObject().get("value").isJsonArray()) {
                        for (c = 0; c < jsonArray.get(i).getAsJsonObject().get("value").getAsJsonArray().size(); c++) {
                            rules[b].value[c] = String.valueOf(jsonArray.get(i).getAsJsonObject().get("value").getAsJsonArray().get(c));
                        }
                    }
                    b++;
                }
            }
            return Arguments.Game.newGameObject(game, rules);
        }
        public Arguments.Jvm jvmDeserialize(JsonElement json) throws JsonParseException {
            JsonArray jsonArray = json.getAsJsonArray();
            int a = 0;
            int b = 0;
            int c = 0;
            for (int i = 0; i < jsonArray.size(); i++){
                if (jsonArray.get(i).isJsonObject()){
                    a++;
                } else if (jsonArray.get(i).isJsonPrimitive()) {
                    b++;
                }
            }

            Arguments.Jvm.JvmRules[] rules = new Arguments.Jvm.JvmRules[a];
            for(int i = 0;i < rules.length;i++){
                rules[i] = new Arguments.Jvm.JvmRules();
                rules[i].rules = new Arguments.Jvm.JvmRules.Rules[1];
                rules[i].rules[0] = new Arguments.Jvm.JvmRules.Rules();
                rules[i].rules[0].os = new Arguments.Jvm.JvmRules.Rules.Os();
            }
            for (int i = 0; i < jsonArray.size(); i++){

                if(jsonArray.get(i).isJsonObject() && jsonArray.get(i).getAsJsonObject().get("value").isJsonPrimitive()) {
                    rules[c].value = new String[1];
                    c++;
                }
                else if (jsonArray.get(i).isJsonObject() && jsonArray.get(i).getAsJsonObject().get("value").isJsonArray()) {
                    rules[c].value = new String[jsonArray.get(i).getAsJsonObject().get("value").getAsJsonArray().size()];
                    c++;
                }
            }
            String[] jvm = new String[b];
            a = 0;
            b = 0;
            for (int i = 0; i < jsonArray.size(); i++){
                if (jsonArray.get(i).isJsonObject()){
                    rules[a].rules[0].action = String.valueOf(jsonArray.get(i).getAsJsonObject().get("rules")
                            .getAsJsonArray().get(0).getAsJsonObject().get("action"));
                    rules[a].rules[0].os.name = String.valueOf(jsonArray.get(i).getAsJsonObject().get("rules")
                            .getAsJsonArray().get(0).getAsJsonObject().get("os").getAsJsonObject().get("name"));
                    rules[a].rules[0].os.version = String.valueOf(jsonArray.get(i).getAsJsonObject().get("rules")
                            .getAsJsonArray().get(0).getAsJsonObject().get("os").getAsJsonObject().get("version"));
                    if (jsonArray.get(i).getAsJsonObject().get("value").isJsonPrimitive()) {
                        rules[a].value[0] = jsonArray.get(i).getAsJsonObject().get("value").getAsString();
                    } else if (jsonArray.get(i).getAsJsonObject().get("value").isJsonArray()) {
                        for (c = 0; c < jsonArray.get(i).getAsJsonObject().get("value").getAsJsonArray().size(); c++){
                            rules[a].value[c] = String.valueOf(jsonArray.get(i).getAsJsonObject().get("value").getAsJsonArray().get(c));
                        }

                    }
                    a++;
                } else if (jsonArray.get(i).isJsonPrimitive()) {
                    jvm[b] = jsonArray.get(i).getAsString();
                    b++;
                }
            }
            return Arguments.Jvm.newJvmObject(rules,jvm);

        }
    }
    public static Gson getGsonObject(){
        return new GsonBuilder()
                .registerTypeAdapter(Arguments.class,new ArgumentsDeserializer())
                .create();
    }
    public Arguments getArguments () {
        return arguments;
    }
    public AssetIndex getAssetIndex () {
        return assetIndex;
    }
    public String getAssets () {
        return assets;
    }
    public Downloads getDownloads() {
        return downloads;
    }
    public int getComplianceLevel () {
        return complianceLevel;
    }
    public int getMinimumLauncherVersion () {
        return minimumLauncherVersion;
    }
    public JavaVersion getJavaVersion () {
        return javaVersion;
    }
    public Libraries[] getLibraries() {
        return libraries;
    }
    public Logging getLogging () {
        return logging;
    }
    public String getId () {
        return id;
    }
    public String getMainClass () {
        return mainClass;
    }
    public String getReleaseTime () {
        return releaseTime;
    }
    public String getTime () {
        return time;
    }
    public String getType () {
        return type;
        }
}
