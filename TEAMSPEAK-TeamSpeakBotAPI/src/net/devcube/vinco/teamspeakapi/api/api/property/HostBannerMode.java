package net.devcube.vinco.teamspeakapi.api.api.property;

public enum HostBannerMode {

    NOADJUST(0),
    IGNOREASPECT(1),
    KEEPASPECT(2);

    private int value;

    HostBannerMode(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

}
