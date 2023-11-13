# Caching

Store often used information temporary to improve the performance of the API.

This works simple by using the `QueryConfig` Class with the getter
in the `Ts3ServerQuery` Class.

By adding `Enums` with in the `addCacheItem` Method to
the cache list you can exactly choose what type of objects retrieved
by the API you want to store.

If enabled the query will collect the specified information when connecting
to the Teamspeakserver (`selectVirtualServer` Method), be aware of this because it
could increase the time needed to connect to a server.

Methods returning cached objects are in the `Ts3BasicAPI` (and because of this also in the Ts3SyncAPI)
(*only methods which give an return value starting with "get"*).


**CacheType Enum** (List of Enums to choose from)**:**
```java
public enum CacheType {

    CLIENTS, CHANNELS, GROUPS, DATABASE, PERMISSION, QUERY, VIRTUALSERVER;
}
```
## Different types of caching
Examples are from the Ts3BasicAPI, but also include the Ts3SyncAPI because it is a subclass.

Updated means when is the stored data (like a channel or client) updated by
the api itself.


### 1. CLIENTS
Stores information about every single client and the clientlist.

**Methods**: `getClient()`, `getClients()`

**Updated**: ClientJoinEvent, ClientLeaveEvent and ClientMoveEvent

### 2. CHANNELS
Stores information about every single channel and the channellist.

**Methods**: `getChannel()`, `getChannels()`

**Updated**: ChannelCreateEvent, ChannelDeletedEvent, ChannelEditedEvent and ChannelMovedEvent

**Not updated**: ChannelDescriptionEditedEvent and ChannelPasswordChangedEvent because it is included in ChannelEditedEvent.


### 3. GROUPS
Stores information about the servergrouplist and the channelgrouplist.

**Methods**: `getServerGroups()`, `getChannelGroups()`

**Updated**: This is not updated by the api itself, you can turn it off temporary
to get updated information or update it by yourself.
(Using the Method `Ts3ServerQuery.getCache().updateServerGroupsCache()` or
`Ts3ServerQuery.getCache().updateChannelGroupsCache()`.)


### 4. DATABASE
Stores information about the databaseclients and the databaseclientslist.

**Methods**: `getDataBaseClientInfo()`, `getDatabaseClients()`

**Updated**: ClientJoinEvent


### 5. PERMISSION
Stores information about the permissionslist like permissionid, permissionname or permissiondescription.

**Methods**: `getPermissionList()`

**Updated**: This is not updated by the api itself, you can turn it off temporary
to get updated information or update it by yourself (definitely not needed).

### 6. QUERY
Stores information about the serverquery.

**Methods**: `getQueryInfo()`

**Updated**: ClientMoveEvent

### 7. VIRTUALSERVER
Stores information about the virtualserver and his properties.

**Methods**: `getServerInfo()`

**Updated**: ServerEditedEvent

## Example:
```java
public class Test {
    
    public static void main(String[] args) {
        Ts3ServerQuery query = new Ts3ServerQuery();
        query.getConfig().addCacheItem(CacheType.PERMISSION);
        query.getConfig().addCacheItem(CacheType.CHANNELS);
        query.getConfig().addCacheItem(CacheType.GROUPS);
        query.getConfig().addCacheItem(CacheType.CLIENTS);
        try {
            query.connect("hostname", 10011, "Username", "Passwort", 1, "Ts³-Bot", 1);
        } catch (IOException | QueryLoginException e) {
            e.printStackTrace();
        }
        query.stopQuery();
    }
}
```