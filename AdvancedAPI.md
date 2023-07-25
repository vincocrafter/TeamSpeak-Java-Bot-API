# Using the Advanced API
Some tricks and tipps to improve the performance of your Code.
All Methods shown are provided the `Ts3SyncAPI` Class *(from net.devcube.vinco.teamspeakapi.api.sync)*.
For simplicity the arguments are not shown, use the help of your
IDEA or Documentation in the Code.

## 1. Getting Lists

Be aware how much or what information you actually need in your Case, otherwise
it needs much more time to retrieve unused information.

For reference also see: https://yat.qa/ressourcen/variablen-parameter/
(Client/Channel-Variables -> ~list,~info)
### A. Client List
Using `getOnlineClients()` or `getClients()` only provides
the information from the List of Clients and contains less information 
about every single Client compared to `getClient()`.
But the Method send only one Command to the Server which is much faster.

Using `getOnlineClientsDetailed()` or `getClientsDetailed()` provides
the information from `getClient()` for every Client. It provides
more information compared to above. But instead send much more
Commands to the Server, because for every single Client
a Command is executed to retrieve the information about it.

### B. Channel List
Same as above for getting Clients.

Using `getChannels()` provides less information about every single Channel
than `getChannel()` but only send one Command.

Using `getChannelsDetailed()` provides the information from
`getChannel()` for every single Channel. It provides
more information compared to above. But instead send much more
Commands to the server, because for every single Channel
a Command is sent by `getChannel()` to retrieve the information.

### C. DataBaseClient List

Same as above for getting Clients.

Using `getDataBaseClients()` provides less information about every single DataBaseClient
than `getDataBaseClientInfo()` but only send one Command.

Using `getDataBaseClientsDetailed()` provides the information from
`getDataBaseClientInfo()` for every single DataBaseClient. It provides
more information compared to above. But instead send much more
Commands to the server, because for every single DataBaseClient
a Command is sent by `getDataBaseClientInfo()` to retrieve the information.

## 2. Getting Database Information
These Methods deal different *(instead of normal Getters)* with retrieved information.
And also the way they store them to improve performance.
Be aware that every Method answers a different Question about
(mostly) the same Database information.

### A. Getting Clients with a ServerGroup
Provides a List of DatabaseClient(ID)s
with a specific ServerGroup.
- getDatabaseIDsByServerGroup
- getDatabaseClientInfosByServerGroup

### B. Getting Clients with a ChannelGroup
Provides a Map which contains every DatabaseClient(ID)
as a Key and the Value is a List of ChannelIDs
in which the DatabaseClient(ID) has the ChannelGroup.
*To only get the DatabaseClient(ID)s just use keySet().*
- getDatabaseIDsByChannelGroup
- getDatabaseClientInfosByChannelGroup

### C. Getting Clients with a ChannelGroup by Channel
Provides a List of DatabaseClient(ID)s which have
a specific ChannelGroup in a specific Channel.
- getDatabaseIDsByChannelAndGroup
- getDatabaseClientInfosByChannelAndGroup

### D. Getting ChannelGroups by Channel
Provides a Map which contains every ChannelGroup(ID) 
as a Key and the Value is a List of DatabaseClientIDs which
are in the ChannelGroup.
Note that getChannelGroupInfosByChannelID caches
the List of ChannelGroups to improve the performance and
only returns ChannelGroups which are set to any Client.
*To get only the ChannelGroup(ID)s just use keySet().*
- getChannelGroupsByChannelID
- getChannelGroupInfosByChannelID

### E. Getting ChannelGroups by ClientDatabaseID
Provides a Map which contains every ChannelGroup(ID)
as a Key and the Value is a List of ChannelIDs in which
the Client has the ChannelGroup(ID).
Note that getChannelGroupInfosByDatabaseID caches
the List of ChannelGroups to improve the performance.
*To get only the ChannelGroup(ID)s just use keySet().*
- getChannelGroupsByDatabaseID
- getChannelGroupInfosByDatabaseID

## 3. Providing Lists for Actions
These methods execute one action for more than
just one Element. Instead of iterate through
a List and execute the Action for every Element, these Methods
execute the Action for multiple Elements at once.
And send only one Command to the Server which is much faster.
Good for interacting with multiple (maybe filtered) Elements at once.

*(Element means Classes from net.devcube.vinco.teamspeakapi.api.api.wrapper or Basic Datatype)*

*Some Methods provide optional forms of arguments.
These are shown in the Name of the Method and are different
type of Data.
Like Permissions and PermissionIDs, Clients and ClientIDs or
DBClient and ClientDBIDs.*

### A. Add multiple Clients to a ServerGroup
- addClientDBIDsToServerGroup
- addDBClientsToServerGroup
- addClientsToServerGroup

### B. Remove multiple Clients from a ServerGroup
- removeClientDBIDsFromServerGroup
- removeDBClientsFromServerGroup
- removeClientsFromServerGroup

### C. Manage multiple Permissions from a Client in a Channel
- addChannelClientPermissions
- removeChannelClientPermissions

### D. Manage multiple Permissions from a ChannelGroup
- addChannelGroupPermissions
- removeChannelGroupPermissionIDs
- removeChannelGroupPermissions

### E. Manage multiple Permissions from a Channel
- addChannelPermissions
- removeChannelPermissionIDs
- removeChannelPermissions

### F. Manage multiple Permissions from a Client
- addClientPermissions
- removeClientPermissionIDs
- removeClientPermissions

### G. Manage multiple Permissions from a ServerGroup
- addServerGroupPermissions
- removeServerGroupPermissionIDs
- removeServerGroupPermissions

### H. Ban multiple Clients
- banClientIDs
- banClients

### I. Kick multiple Clients from a Channel
- kickClientIDsFromChannel
- kickClientsFromChannel

### J. Kick multiple Clients from the Server
- kickClientIDsFromServer
- kickClientsFromServer

### K. Move multiple Clients in a Channel
- moveClientIDs
- moveClients

### Example:
Instead of:
```java
public class Test {
    
    public static void main(String[] args) {
        List<ClientInfo> commanderClients = syncAPI.getClients().stream().filter(client -> client.isChannelCommander()).toList();

        for (ClientInfo clients : commanderClients) {
            syncAPI.addClientToServerGroup(0, clients); //Adds every Client one by one the ServerGroup
        }
    }
}
```
Use:
```java
public class Test {
    
    public static void main(String[] args) {
        List<ClientInfo> commanderClients = syncAPI.getClients().stream().filter(client -> client.isChannelCommander()).toList();
        syncAPI.addClientsToServerGroup(0, commanderClients); //Adds every Client at once the ServerGroup
    }
}
```
