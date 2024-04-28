package proj.inue.posis.recyclerview;

public class PAddCategoryItem {
    String categoryName;
    int edit, delete;

    public PAddCategoryItem(String categoryName, int edit, int delete) {
        this.categoryName = categoryName;
        this.edit = edit;
        this.delete = delete;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getEdit() {
        return edit;
    }

    public void setEdit(int edit) {
        this.edit = edit;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}
