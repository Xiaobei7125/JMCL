package jsonAnalysis.login.microsoft;

public class MinecraftOwnershipObject {
    private Items[] items;
    private String signature;
    private int keyId;

    protected static class Items {
        private String name;
        private String signature;
    }


    public int getItemsLength() {
        return items.length;
    }
}
