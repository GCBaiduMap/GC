package gc.com.gcmapapp.bean;

import java.util.List;

/**
 * Created by jiguijun on 2017/12/11.
 */

public class Menu {
    private String id;
    private String parent_id;
    private String menuName;
    private  boolean  leaf;
    private boolean checked;
    private List<SecondMenu> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<SecondMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SecondMenu> children) {
        this.children = children;
    }

    public static class SecondMenu{
        private String id;
        private String parent_id;
        private String menuName;
        private  boolean  leaf;
        private boolean checked;
        private List<ThirdMenu> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public List<ThirdMenu> getChildren() {
            return children;
        }

        public void setChildren(List<ThirdMenu> children) {
            this.children = children;
        }
    }

    public static class ThirdMenu{
        private String id;
        private String parent_id;
        private String menuName;
        private  boolean  leaf;
        private boolean checked;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

}

