package JsonAnalysis;

public class CheckMinecraftOwnershipObject {
    private Items[] items;
    private String signature;
    private int keyId;
    public class Items{
        private String name;
        private String signature;
    }

    public Items[] getItems() {
        return items;
    }
}
