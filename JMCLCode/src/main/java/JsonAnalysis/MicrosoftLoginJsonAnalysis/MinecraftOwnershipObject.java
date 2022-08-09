package JsonAnalysis.MicrosoftLoginJsonAnalysis;

public class MinecraftOwnershipObject {
    private Items[] items;
    private String signature;
    private int keyId;
    protected class Items{
        private String name;
        private String signature;
    }

    public Items[] getItems() {
        return items;
    }

    public int getItemsLength() {
        return items.length;
    }
}
