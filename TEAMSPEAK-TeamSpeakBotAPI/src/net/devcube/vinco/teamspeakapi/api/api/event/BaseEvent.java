package net.devcube.vinco.teamspeakapi.api.api.event;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AsyncAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;

/**
 * The {@code BaseEvent} class represents the base structure for events
 * within the TeamSpeak API. It extends {@link DefaultInfo} to 
 * provide additional information and integrates with the {@link Ts3ServerQuery}.
 */
public class BaseEvent extends DefaultInfo {

    private Ts3ServerQuery serverQuery;

    /**
     * Constructs a new {@code BaseEvent} with the specified information and server query.
     *
     * @param infos       an array of strings containing information about the event
     * @param serverQuery an instance of {@link Ts3ServerQuery} associated with this event
     */
    public BaseEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos);
        this.serverQuery = serverQuery;
    }

    /**
     * Gets the {@link Ts3ServerQuery} associated with this event.
     *
     * @return the serverQuery
     */
    public Ts3ServerQuery getServerQuery() {
        return serverQuery;
    }

    /**
     * Gets the {@link QueryConfig} from the associated {@link Ts3ServerQuery}.
     *
     * @return the configuration settings of the server query
     */
    public QueryConfig getConfig() {
        return serverQuery.getConfig();
    }

    /**
     * Gets the synchronous API interface from the associated {@link Ts3ServerQuery}.
     *
     * @return an instance of {@link Ts3SyncAPI} for synchronous operations
     */
    public Ts3SyncAPI getSyncAPI() {
        return serverQuery.getSyncAPI();
    }

    /**
     * Gets the basic API interface from the associated {@link Ts3ServerQuery}.
     *
     * @return an instance of {@link Ts3BasicAPI} for basic operations
     */
    public Ts3BasicAPI getBasicAPI() {
        return serverQuery.getBasicAPI();
    }

    /**
     * Gets the asynchronous API interface from the associated {@link Ts3ServerQuery}.
     *
     * @return an instance of {@link Ts3AsyncAPI} for asynchronous operations
     */
    public Ts3AsyncAPI getAsyncAPI() {
        return serverQuery.getAsyncAPI();
    }
}
