package provab.herdman.beans;

/**
 * Created by PTBLR-1057 on 6/13/2016.
 */
public class MultiSelectItem {

    private String name = "";
    private boolean checked = false;

    public MultiSelectItem() {
    }

    public MultiSelectItem(String name) {
        this.name = name;
    }

    public MultiSelectItem(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String toString() {
        return name+"  "+checked ;
    }

    public void toggleChecked() {
        checked = !checked;
    }

}
