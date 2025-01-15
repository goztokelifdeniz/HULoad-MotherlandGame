public class Ground {
    private String path;
    private boolean drillable;

    private boolean kills;
    private boolean ground_is_valuable;

    public void setKills(boolean kills) {
        this.kills = kills;
    }

    public void setDrillable(boolean drillable) {
        this.drillable = drillable;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setGround_is_valuable(boolean ground_is_valuable) {
        this.ground_is_valuable = ground_is_valuable;
    }

    public boolean isGround_is_valuable() {
        return ground_is_valuable;
    }

    public String getPath() {
        return path;
    }

    public boolean isDrillable() {
        return drillable;
    }

    public boolean isKills() {
        return kills;
    }



}
