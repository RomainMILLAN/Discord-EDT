package fr.romainmillan.discordedt.object;

public class Configuration {
    private String groupe;
    private String nickgroupe;
    private String url;
    private String roleid;
    private String rolenotifid;
    private String channelId;

    public Configuration(String groupe) {
        this.groupe = groupe;
        this.nickgroupe = groupe;
    }

    public Configuration(String groupe, String nickgroupe, String url, String roleid, String rolenotifid, String channelId) {
        this.groupe = groupe;
        this.nickgroupe = nickgroupe;
        this.url = url;
        this.roleid = roleid;
        this.rolenotifid = rolenotifid;
        this.channelId = channelId;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getNickgroupe() {
        return nickgroupe;
    }

    public void setNickgroupe(String nickgroupe) {
        this.nickgroupe = nickgroupe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolenotifid() {
        return rolenotifid;
    }

    public void setRolenotifid(String rolenotifid) {
        this.rolenotifid = rolenotifid;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
