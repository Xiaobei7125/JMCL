package JsonAnalysis.Login.Microsoft;

public class MinecraftOwnershipObject {
    private Items[] items;
    private String signature;
    private int keyId;
    protected class Items{
        private String name;
        private String signature;
    }


    public int getItemsLength() {
        return items.length;
    }
}
