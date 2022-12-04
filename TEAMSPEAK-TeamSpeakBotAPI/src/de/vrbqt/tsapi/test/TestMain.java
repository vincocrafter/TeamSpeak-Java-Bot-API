package de.vrbqt.tsapi.test;

import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

import java.io.IOException;

public class TestMain {

    private static Ts3ServerQuery ts_api = new Ts3ServerQuery();

    public static void main(String[] args) {
        try {
            ts_api.connect("192.168.1.2", 10011, "serveradmin", "96HXa5WM", 1, "qumpus", 1);
        } catch (IOException | QueryLoginException e) {
            throw new RuntimeException(e);
        }
    }

}
