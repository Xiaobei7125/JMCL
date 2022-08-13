package JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;


import java.lang.reflect.Type;

public class MinecraftVersionJsonFileObject {
    private Arguments arguments;
    private AssetIndex assetIndex;
    private String assets;
    private int complianceLevel;
    private Downloads downloads;
    private String id;
    private JavaVersion javaVersion;
    private Libraries libraries;
    private Logging logging;
    private String mainClass;
    private int minimumLauncherVersion;
    private String releaseTime;
    private String time;
    private String type;
    protected static class Arguments {
        private Game game;
        private Jvm jvm;
        public static Arguments newArgumentObject(Game game,Jvm jvm){
            Arguments newArguments = new Arguments();
            newArguments.game = game;
            newArguments.jvm = jvm;
            return newArguments;
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


            public void setGame(String[] game) {
                this.game = game;
            }

            public void setRules(GameRules[] rules) {
                this.rules = rules;
            }

            public static Game newGameObject(String[] game, GameRules[] rules) {
                Game newGame = new Game();
                newGame.game = game;
                newGame.rules = rules;
                return newGame;
            }


            public class GameRules {
                private Rules[] rules;
                private String[] value;

                public Rules[] getRules() {
                    return rules;
                }

                public String[] getValue() {
                    return value;
                }

                public class Rules {
                    private String action;
                    private Features features;

                    public Features getFeatures() {
                        return features;
                    }

                    public class Features {
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
            public static class JvmRules {
                private Rules[] rules;
                private String[] value;
                public static class Rules {
                    private String action;
                    private Os os;

                    public static class Os {
                        private String name;
                        private String version;
                    }
                }
            }
        }
    }
    protected class AssetIndex {
        private String id;
        private String sha1;
        private int size;
        private int totalSize;
        private String url;
    }
    protected class Downloads{}
    protected class JavaVersion{}
    protected class Libraries{}
    protected class Logging{}
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
            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.get(i).isJsonPrimitive()) {
                    a++;
                } else if (jsonArray.get(i).isJsonObject()) {
                    b++;
                }
            }
            String[] game = new String[a];
            Arguments.Game.GameRules[] rules = new Arguments.Game.GameRules[b];
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
                        for (int c = 0; c < jsonArray.get(i).getAsJsonObject().get("value").getAsJsonArray().size(); c++) {
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
                if(jsonArray.get(i).isJsonObject() && jsonArray.get(i).getAsJsonObject().get("value").isJsonPrimitive()){rules[i].value = new String[1];}
                else if (jsonArray.get(i).isJsonObject() && jsonArray.get(i).getAsJsonObject().get("value").isJsonArray()) {
                    rules[i].value = new String[jsonArray.get(i).getAsJsonObject().get("value").getAsJsonArray().size()];
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
                            System.out.println(jsonArray.get(i).getAsJsonObject().get("value").getAsJsonArray().size());
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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Arguments.class,new  ArgumentsDeserializer())
                .create();
        return gson;
    }
}
