package JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis;

public class MinecraftVersionJsonFileObject {
    private Arguments arguments;
    private AssetIndex assetIndex;
    protected class Arguments{
        private String[] game;
        private Jvm[] jvm;
        protected class Jvm{
            private Rules[] rules;
            private String[] value;
            protected class Rules{
                private String action;
                private Os os;
                protected class Os{
                    private String name;
                }
            }
        }
    }
    protected class AssetIndex{


    }

}
