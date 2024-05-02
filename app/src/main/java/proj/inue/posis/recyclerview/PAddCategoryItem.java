package proj.inue.posis.recyclerview;

public class PAddCategoryItem {
    String categoryName;
    long id;


    public PAddCategoryItem(String categoryName, long id) {
        this.categoryName = categoryName;
        this.id = id;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }
}
