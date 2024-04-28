package proj.inue.posis.recyclerview;

import com.google.gson.Gson;

import proj.inue.posis.utils.Helper;

public class PViewInventoryItem {
    String title, category;
    String[] label, content;
    int image, edit, delete;

    public PViewInventoryItem(String title, String category, String[] label, String[] content, int image, int edit, int delete) {
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

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
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

    public String getLabelString() {
        return Helper.getArrayToString(label, ':', Character.MIN_VALUE, '\n');
    }

    public String getContentString() {
        return Helper.getArrayToString(content, Character.MIN_VALUE, Character.MIN_VALUE, '\n');
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
