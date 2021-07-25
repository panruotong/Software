package pojo;

public class StructureInfo {
    int authorityId;
    String authorityName;
    int isMenu;
    int parentId;

    public int getAuthorityId() {
        return authorityId;
    }

    public int getIsMenu() {
        return isMenu;
    }

    public int getParentId() {
        return parentId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public void setIsMenu(int isMenu) {
        this.isMenu = isMenu;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
