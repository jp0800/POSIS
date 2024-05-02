package proj.inue.posis.recyclerview;

import com.google.gson.Gson;

import proj.inue.posis.utils.Helper;

public class PViewInventoryItem {
    String title, category, image;
    String[] label, content;
    int id;

    public PViewInventoryItem(int id, String title, String category, String[] label, String[] content, String image) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.label = label;
        this.content = content;
        this.image = image;
    }

    public int getId() {
        return id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
