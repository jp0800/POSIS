package proj.inue.posis.recyclerview;

public class PPointOfSaleItem extends PViewInventoryItem{

    int multiplier = 1;
    public PPointOfSaleItem(int id, String title, String category, String[] label, String[] content, String image) {
        super(id, title, category, label, content, image);
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
