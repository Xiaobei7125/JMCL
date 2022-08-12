package JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public class MinecraftVersionJsonFileObject {
    private Arguments arguments;
    private AssetIndex assetIndex;
    public static class Arguments{
        private Game[] game;
        private Jvm[] jvm;
        Arguments(){}
        public static class Game{
            private String[] game;
            private GameRules[] rules;
            private String value;

            public GameRules[] getRules() {
                return rules;
            }

            public String getValue() {
                return value;
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
            public static Game getNewGameObject(String[] game, GameRules[] rules, String value){
                Game newGame = new Game();
                newGame.game = game;
                newGame.value = value;
                newGame.rules = rules;
                return newGame;
            }

            public void setValue(String value) {
                this.value = value;
            }
            public class GameRules{
                private Rules[] rules;

                public Rules[] getRules() {
                    return rules;
                }

                public class Rules{
                    private String action;
                    private Features features;

                    public Features getFeatures() {
                        return features;
                    }

                    public class Features{
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
        protected class Jvm{
            private Rules[] rules;
            private String[] value;
            protected class Rules{
                private String action;
                private Os os;
                protected class Os{
                    private String name;
                    private String version;
                    private String[] value;
                }
            }
        }
    }
    protected class AssetIndex{
        private String id;
        private String sha1;
        private int size;
        private int totalSize;
        private String url;
    }
    public static class GameDeserializer implements JsonDeserializer<Arguments.Game>{
        @Override
        public Arguments.Game deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray jsonArray = json.getAsJsonObject().get("game").getAsJsonArray();
            int a = 0;
            int b = 0;
            for (int i = 0; i < jsonArray.size(); i++){
                if(jsonArray.get(i).isJsonPrimitive()){
                    a++;
                } else if (jsonArray.get(i).isJsonObject() && !jsonArray.get(i).getAsJsonObject().has("value")) {
                    b++;
                }
            }
            String[] game = new String[a];
            Arguments.Game.GameRules[] rules = new Arguments.Game.GameRules[b];
            String value = "";
            a = 0;
            b = 0;
            for (int i = 0; i < jsonArray.size(); i++){
                if(jsonArray.get(i).isJsonPrimitive()){
                    game[a] = jsonArray.get(i).getAsString();
                    a++;
                } else if (jsonArray.get(i).isJsonObject() && !jsonArray.get(i).getAsJsonObject().has("value")) {
                    rules[b].rules[1].action = String.valueOf(jsonArray.get(i).getAsJsonObject().get("rules")
                            .getAsJsonArray().get(1).getAsJsonObject().get("action"));
                    rules[b].rules[1].features.hasCustomResolution = Boolean.parseBoolean(String.valueOf(jsonArray.get(i).getAsJsonObject().get("rules")
                            .getAsJsonArray().get(1).getAsJsonObject().get("action").getAsJsonObject().get("has_custom_resolution")));
                    rules[b].rules[1].features.isDemoUser = Boolean.parseBoolean(String.valueOf(jsonArray.get(i).getAsJsonObject().get("rules")
                            .getAsJsonArray().get(1).getAsJsonObject().get("action").getAsJsonObject().get("is_demo_user")));
                    b++;
                }else if (jsonArray.get(i).isJsonObject() && jsonArray.get(i).getAsJsonObject().has("value")){
                    value = String.valueOf(jsonArray.get(i).getAsJsonObject().get("value"));
                }
            }
            return Arguments.Game.getNewGameObject(game,rules,value);
        }
    }
}
