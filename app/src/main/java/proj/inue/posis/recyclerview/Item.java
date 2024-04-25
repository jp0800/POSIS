package proj.inue.posis.recyclerview;

public class Item {
    String title, category, label, content;
    int image, edit, delete;

    public Item(String title, String category, String label, String content, int image, int edit, int delete) {
        this.title = title;
        this.category = category;
        this.label = label;
        this.content = content;
        this.image = image;
        this.edit = edit;
        this.delete = delete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
