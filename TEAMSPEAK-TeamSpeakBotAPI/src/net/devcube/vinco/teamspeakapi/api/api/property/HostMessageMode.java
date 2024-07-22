package net.devcube.vinco.teamspeakapi.api.api.property;

public enum HostMessageMode {

    NONE(0),
    CHATLOG(1),
    MODAL(2),
    MODAL_AND_QUIT(3);

    private int value;

    HostMessageMode(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
