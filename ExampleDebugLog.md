# Example Code
```java
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryTestBot {
    
	public static void main(String[] args) {
        Ts3ServerQuery query = new Ts3ServerQuery();
        query.getConfig().addDebugItem(DebugOutputType.EVENTMANAGER);
        query.getConfig().addDebugItem(DebugOutputType.QUERYREADER);
        query.getConfig().addDebugItem(DebugOutputType.QUERYWRITER);
        query.getConfig().setDebugType(DebugType.CONSOLE);
        query.getConfig().setShowTimeMilliseconds(true);
        query.getConfig().setShowDate(true);
        query.getConfig().setEventCallType(EventCallType.NEW);
        try {
            query.connect("localhost", 10011, "Test", "PASSWORD", 1, "Test", 3);
        } catch (IOException | QueryLoginException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        query.getSyncAPI().getHelp();

        query.getEventManager().addTs3Listener(new Events());
	}
    
}
```
**Note:**
*Changed the Information of the answer packet of **whoami** and removed personal information.*

# First Debug Log Example
**Used DebugTypes:**
- EVENTMANAGER
- QUERYREADER
- QUERYWRITER

As you can see, very much information in the log:

```
[main] [01/04/2023] [16:02:30.851] [QUERY READER] : Starting listening in QueryReader
[QURT] [01/04/2023] [16:02:30.914] [QUERY READER] : Added to Packets: TS3
[QURT] [01/04/2023] [16:02:30.926] [QUERY READER] : Added to Packets: Welcome to the TeamSpeak 3 ServerQuery interface, type "help" for a list of commands and "help <command>" for information on a specific command.
[main] [01/04/2023] [16:02:30.927] [QUERY WRITER] : Executing Command > (login Test PASSWORD)
[QURT] [01/04/2023] [16:02:30.938] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:30.939] [QUERY WRITER] : Executing Command > (use 1)
[QURT] [01/04/2023] [16:02:30.943] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:30.945] [QUERY WRITER] : Executing Command > (clientupdate client_nickname=Test)
[QURT] [01/04/2023] [16:02:30.947] [QUERY READER] : Added to Errors: error id=513 msg=nickname\sis\salready\sin\suse
[main] [01/04/2023] [16:02:30.957] [QUERY WRITER] : Executing Command > (servernotifyregister event=server)
[QURT] [01/04/2023] [16:02:30.962] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:30.963] [QUERY WRITER] : Executing Command > (servernotifyregister event=channel id=0)
[QURT] [01/04/2023] [16:02:30.970] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:30.980] [QUERY WRITER] : Executing Command > (servernotifyregister event=textserver)
[QURT] [01/04/2023] [16:02:30.982] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:30.984] [QUERY WRITER] : Executing Command > (servernotifyregister event=textchannel)
[QURT] [01/04/2023] [16:02:31.022] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:31.024] [QUERY WRITER] : Executing Command > (servernotifyregister event=textprivate)
[QURT] [01/04/2023] [16:02:31.035] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:31.041] [QUERY WRITER] : Executing Command > (servernotifyregister event=tokenused)
[QURT] [01/04/2023] [16:02:31.043] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:31.046] [QUERY WRITER] : Executing Command > (whoami)
[QURT] [01/04/2023] [16:02:31.048] [QUERY READER] : Added to Packets: virtualserver_status=online virtualserver_id=1 virtualserver_unique_identifier=VUUID virtualserver_port=9987 client_id=700 client_channel_id=1 client_nickname=Test client_database_id=DBID client_login_name=Test client_unique_identifier=UUID client_origin_server_id=1
[QURT] [01/04/2023] [16:02:31.055] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:31.061] [QUERY WRITER] : Executing Command > (clientmove clid=700 cid=3)
[QURT] [01/04/2023] [16:02:31.066] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:31.067] [QUERY WRITER] : Executing Command > (help)
[EVMA] [01/04/2023] [16:02:31.075] [QUERY READER] : Called New Event: notifyclientmoved ctid=3 reasonid=0 clid=700
[EVMA] [01/04/2023] [16:02:31.081] [Event Manager] : notifyclientmoved was called
[QURT] [01/04/2023] [16:02:31.083] [QUERY READER] : Added to Packets: TeamSpeak 3 Server :: ServerQuery
[QURT] [01/04/2023] [16:02:31.087] [QUERY READER] : Added to Packets: (c) TeamSpeak Systems GmbH
[QURT] [01/04/2023] [16:02:31.088] [QUERY READER] : Added to Packets: 
[QURT] [01/04/2023] [16:02:31.089] [QUERY READER] : Added to Packets: ServerQuery is a command-line interface built into the TeamSpeak 3 Server which
[QURT] [01/04/2023] [16:02:31.090] [QUERY READER] : Added to Packets: allows powerful scripting and automation tools to be built based on the exact
[QURT] [01/04/2023] [16:02:31.091] [QUERY READER] : Added to Packets: same instruction set and functionality provided by the TeamSpeak 3 Client. For
[QURT] [01/04/2023] [16:02:31.092] [QUERY READER] : Added to Packets: example, you can use scripts to automate the management of virtual servers or
[QURT] [01/04/2023] [16:02:31.093] [QUERY READER] : Added to Packets: nightly backups. In short, you can perform operations more efficiently by using
[QURT] [01/04/2023] [16:02:31.094] [QUERY READER] : Added to Packets: ServerQuery scripts than you can by using a user interface.
[QURT] [01/04/2023] [16:02:31.120] [QUERY READER] : Added to Packets: 
[QURT] [01/04/2023] [16:02:31.120] [QUERY READER] : Added to Packets: Command Overview:
[QURT] [01/04/2023] [16:02:31.121] [QUERY READER] : Added to Packets:    apikeyadd                   | create a apikey
[QURT] [01/04/2023] [16:02:31.122] [QUERY READER] : Added to Packets:    apikeydel                   | delete a apikey
[QURT] [01/04/2023] [16:02:31.123] [QUERY READER] : Added to Packets:    apikeylist                  | list apikeys
[QURT] [01/04/2023] [16:02:31.123] [QUERY READER] : Added to Packets:    banadd                      | create a ban rule
[QURT] [01/04/2023] [16:02:31.124] [QUERY READER] : Added to Packets:    banclient                   | ban a client
[QURT] [01/04/2023] [16:02:31.125] [QUERY READER] : Added to Packets:    bandel                      | delete a ban rule
[QURT] [01/04/2023] [16:02:31.126] [QUERY READER] : Added to Packets:    bandelall                   | delete all ban rules
[QURT] [01/04/2023] [16:02:31.127] [QUERY READER] : Added to Packets:    banlist                     | list ban rules on a virtual server
[QURT] [01/04/2023] [16:02:31.127] [QUERY READER] : Added to Packets:    bindinglist                 | list IP addresses used by the server instance
[QURT] [01/04/2023] [16:02:31.128] [QUERY READER] : Added to Packets:    channeladdperm              | assign permission to channel
[QURT] [01/04/2023] [16:02:31.129] [QUERY READER] : Added to Packets:    channelclientaddperm        | assign permission to channel-client combi
[QURT] [01/04/2023] [16:02:31.130] [QUERY READER] : Added to Packets:    channelclientdelperm        | remove permission from channel-client combi
[QURT] [01/04/2023] [16:02:31.131] [QUERY READER] : Added to Packets:    channelclientpermlist       | list channel-client specific permissions
[QURT] [01/04/2023] [16:02:31.132] [QUERY READER] : Added to Packets:    channelcreate               | create a channel
[QURT] [01/04/2023] [16:02:31.133] [QUERY READER] : Added to Packets:    channeldelete               | delete a channel
[QURT] [01/04/2023] [16:02:31.134] [QUERY READER] : Added to Packets:    channeldelperm              | remove permission from channel
[QURT] [01/04/2023] [16:02:31.135] [QUERY READER] : Added to Packets:    channeledit                 | change channel properties
[QURT] [01/04/2023] [16:02:31.138] [QUERY READER] : Added to Packets:    channelfind                 | find channel by name
[QURT] [01/04/2023] [16:02:31.138] [QUERY READER] : Added to Packets:    channelgroupadd             | create a channel group
[QURT] [01/04/2023] [16:02:31.139] [QUERY READER] : Added to Packets:    channelgroupaddperm         | assign permission to channel group
[QURT] [01/04/2023] [16:02:31.140] [QUERY READER] : Added to Packets:    channelgroupclientlist      | find channel groups by client ID
[QURT] [01/04/2023] [16:02:31.141] [QUERY READER] : Added to Packets:    channelgroupcopy            | copy a channel group
[QURT] [01/04/2023] [16:02:31.141] [QUERY READER] : Added to Packets:    channelgroupdel             | delete a channel group
[QURT] [01/04/2023] [16:02:31.142] [QUERY READER] : Added to Packets:    channelgroupdelperm         | remove permission from channel group
[QURT] [01/04/2023] [16:02:31.143] [QUERY READER] : Added to Packets:    channelgrouplist            | list channel groups
[QURT] [01/04/2023] [16:02:31.144] [QUERY READER] : Added to Packets:    channelgrouppermlist        | list channel group permissions
[QURT] [01/04/2023] [16:02:31.145] [QUERY READER] : Added to Packets:    channelgrouprename          | rename a channel group
[QURT] [01/04/2023] [16:02:31.152] [QUERY READER] : Added to Packets:    channelinfo                 | display channel properties
[QURT] [01/04/2023] [16:02:31.153] [QUERY READER] : Added to Packets:    channellist                 | list channels on a virtual server
[QURT] [01/04/2023] [16:02:31.154] [QUERY READER] : Added to Packets:    channelmove                 | move channel to new parent
[QURT] [01/04/2023] [16:02:31.155] [QUERY READER] : Added to Packets:    channelpermlist             | list channel specific permissions
[QURT] [01/04/2023] [16:02:31.156] [QUERY READER] : Added to Packets:    clientaddperm               | assign permission to client
[QURT] [01/04/2023] [16:02:31.156] [QUERY READER] : Added to Packets:    clientdbdelete              | delete client database properties
[QURT] [01/04/2023] [16:02:31.157] [QUERY READER] : Added to Packets:    clientdbedit                | change client database properties
[QURT] [01/04/2023] [16:02:31.160] [QUERY READER] : Added to Packets:    clientdbfind                | find client database ID by nickname or UID
[QURT] [01/04/2023] [16:02:31.161] [QUERY READER] : Added to Packets:    clientdbinfo                | display client database properties
[QURT] [01/04/2023] [16:02:31.162] [QUERY READER] : Added to Packets:    clientdblist                | list known client UIDs
[QURT] [01/04/2023] [16:02:31.165] [QUERY READER] : Added to Packets:    clientdelperm               | remove permission from client
[QURT] [01/04/2023] [16:02:31.166] [QUERY READER] : Added to Packets:    clientedit                  | change client properties
[QURT] [01/04/2023] [16:02:31.167] [QUERY READER] : Added to Packets:    clientfind                  | find client by nickname
[QURT] [01/04/2023] [16:02:31.172] [QUERY READER] : Added to Packets:    clientgetdbidfromuid        | find client database ID by UID
[QURT] [01/04/2023] [16:02:31.172] [QUERY READER] : Added to Packets:    clientgetids                | find client IDs by UID
[QURT] [01/04/2023] [16:02:31.173] [QUERY READER] : Added to Packets:    clientgetnamefromdbid       | find client nickname by database ID
[QURT] [01/04/2023] [16:02:31.174] [QUERY READER] : Added to Packets:    clientgetnamefromuid        | find client nickname by UID
[QURT] [01/04/2023] [16:02:31.176] [QUERY READER] : Added to Packets:    clientgetuidfromclid        | find client UID by client ID
[QURT] [01/04/2023] [16:02:31.182] [QUERY READER] : Added to Packets:    clientinfo                  | display client properties
[QURT] [01/04/2023] [16:02:31.184] [QUERY READER] : Added to Packets:    clientkick                  | kick a client
[QURT] [01/04/2023] [16:02:31.185] [QUERY READER] : Added to Packets:    clientlist                  | list clients online on a virtual server
[QURT] [01/04/2023] [16:02:31.253] [QUERY READER] : Added to Packets:    clientmove                  | move a client
[QURT] [01/04/2023] [16:02:31.254] [QUERY READER] : Added to Packets:    clientpermlist              | list client specific permissions
[QURT] [01/04/2023] [16:02:31.255] [QUERY READER] : Added to Packets:    clientpoke                  | poke a client
[QURT] [01/04/2023] [16:02:31.260] [QUERY READER] : Added to Packets:    clientsetserverquerylogin   | set own login credentials
[QURT] [01/04/2023] [16:02:31.262] [QUERY READER] : Added to Packets:    clientupdate                | set own properties
[QURT] [01/04/2023] [16:02:31.262] [QUERY READER] : Added to Packets:    complainadd                 | create a client complaint
[QURT] [01/04/2023] [16:02:31.263] [QUERY READER] : Added to Packets:    complaindel                 | delete a client complaint
[QURT] [01/04/2023] [16:02:31.265] [QUERY READER] : Added to Packets:    complaindelall              | delete all client complaints
[QURT] [01/04/2023] [16:02:31.269] [QUERY READER] : Added to Packets:    complainlist                | list client complaints on a virtual server
[QURT] [01/04/2023] [16:02:31.277] [QUERY READER] : Added to Packets:    custominfo                  | display custom client properties
[QURT] [01/04/2023] [16:02:31.279] [QUERY READER] : Added to Packets:    customsearch                | search for custom client properties
[QURT] [01/04/2023] [16:02:31.282] [QUERY READER] : Added to Packets:    customset                   | add or update a custom client property.
[QURT] [01/04/2023] [16:02:31.283] [QUERY READER] : Added to Packets:    customdelete                | remove a custom client property.
[QURT] [01/04/2023] [16:02:31.320] [QUERY READER] : Added to Packets:    ftcreatedir                 | create a directory
[QURT] [01/04/2023] [16:02:31.322] [QUERY READER] : Added to Packets:    ftdeletefile                | delete a file
[QURT] [01/04/2023] [16:02:31.324] [QUERY READER] : Added to Packets:    ftgetfileinfo               | display details about a file
[QURT] [01/04/2023] [16:02:31.325] [QUERY READER] : Added to Packets:    ftgetfilelist               | list files stored in a channel filebase
[QURT] [01/04/2023] [16:02:31.326] [QUERY READER] : Added to Packets:    ftinitdownload              | init a file download
[QURT] [01/04/2023] [16:02:31.327] [QUERY READER] : Added to Packets:    ftinitupload                | init a file upload
[QURT] [01/04/2023] [16:02:31.328] [QUERY READER] : Added to Packets:    ftlist                      | list active file transfers
[QURT] [01/04/2023] [16:02:31.329] [QUERY READER] : Added to Packets:    ftrenamefile                | rename a file
[QURT] [01/04/2023] [16:02:31.330] [QUERY READER] : Added to Packets:    ftstop                      | stop a file transfer
[QURT] [01/04/2023] [16:02:31.331] [QUERY READER] : Added to Packets:    gm                          | send global text message
[QURT] [01/04/2023] [16:02:31.332] [QUERY READER] : Added to Packets:    help                        | read help files
[QURT] [01/04/2023] [16:02:31.332] [QUERY READER] : Added to Packets:    hostinfo                    | display server instance connection info
[QURT] [01/04/2023] [16:02:31.333] [QUERY READER] : Added to Packets:    instanceedit                | change server instance properties
[QURT] [01/04/2023] [16:02:31.334] [QUERY READER] : Added to Packets:    instanceinfo                | display server instance properties
[QURT] [01/04/2023] [16:02:31.335] [QUERY READER] : Added to Packets:    logadd                      | add custom entry to log
[QURT] [01/04/2023] [16:02:31.335] [QUERY READER] : Added to Packets:    login                       | authenticate with the server
[QURT] [01/04/2023] [16:02:31.336] [QUERY READER] : Added to Packets:    logout                      | deselect virtual server and log out
[QURT] [01/04/2023] [16:02:31.337] [QUERY READER] : Added to Packets:    logview                     | list recent log entries
[QURT] [01/04/2023] [16:02:31.337] [QUERY READER] : Added to Packets:    messageadd                  | send an offline message
[QURT] [01/04/2023] [16:02:31.338] [QUERY READER] : Added to Packets:    messagedel                  | delete an offline message from your inbox
[QURT] [01/04/2023] [16:02:31.339] [QUERY READER] : Added to Packets:    messageget                  | display an offline message from your inbox
[QURT] [01/04/2023] [16:02:31.340] [QUERY READER] : Added to Packets:    messagelist                 | list offline messages from your inbox
[QURT] [01/04/2023] [16:02:31.341] [QUERY READER] : Added to Packets:    messageupdateflag           | mark an offline message as read
[QURT] [01/04/2023] [16:02:31.342] [QUERY READER] : Added to Packets:    permfind                    | find permission assignments by ID
[QURT] [01/04/2023] [16:02:31.342] [QUERY READER] : Added to Packets:    permget                     | display client permission value for yourself
[QURT] [01/04/2023] [16:02:31.343] [QUERY READER] : Added to Packets:    permidgetbyname             | find permission ID by name
[QURT] [01/04/2023] [16:02:31.344] [QUERY READER] : Added to Packets:    permissionlist              | list permissions available
[QURT] [01/04/2023] [16:02:31.345] [QUERY READER] : Added to Packets:    permoverview                | display client permission overview
[QURT] [01/04/2023] [16:02:31.346] [QUERY READER] : Added to Packets:    permreset                   | delete all server and channel groups and
[QURT] [01/04/2023] [16:02:31.346] [QUERY READER] : Added to Packets:                                | restore default permissions
[QURT] [01/04/2023] [16:02:31.347] [QUERY READER] : Added to Packets:    privilegekeyadd             | creates a new privilege key
[QURT] [01/04/2023] [16:02:31.348] [QUERY READER] : Added to Packets:    privilegekeydelete          | delete an existing privilege key
[QURT] [01/04/2023] [16:02:31.348] [QUERY READER] : Added to Packets:    privilegekeylist            | list all existing privilege keys on this server
[QURT] [01/04/2023] [16:02:31.349] [QUERY READER] : Added to Packets:    privilegekeyuse             | use a privilege key
[QURT] [01/04/2023] [16:02:31.350] [QUERY READER] : Added to Packets:    queryloginadd               | add a query client login
[QURT] [01/04/2023] [16:02:31.350] [QUERY READER] : Added to Packets:    querylogindel               | remove a query client login
[QURT] [01/04/2023] [16:02:31.356] [QUERY READER] : Added to Packets:    queryloginlist              | list all query client logins
[QURT] [01/04/2023] [16:02:31.357] [QUERY READER] : Added to Packets:    quit                        | close connection
[QURT] [01/04/2023] [16:02:31.363] [QUERY READER] : Added to Packets:    sendtextmessage             | send text message
[QURT] [01/04/2023] [16:02:31.373] [QUERY READER] : Added to Packets:    servercreate                | create a virtual server
[QURT] [01/04/2023] [16:02:31.376] [QUERY READER] : Added to Packets:    serverdelete                | delete a virtual server
[QURT] [01/04/2023] [16:02:31.377] [QUERY READER] : Added to Packets:    serveredit                  | change virtual server properties
[QURT] [01/04/2023] [16:02:31.381] [QUERY READER] : Added to Packets:    servergroupadd              | create a server group
[QURT] [01/04/2023] [16:02:31.382] [QUERY READER] : Added to Packets:    servergroupaddclient        | add client to server group
[QURT] [01/04/2023] [16:02:31.383] [QUERY READER] : Added to Packets:    servergroupaddperm          | assign permissions to server group
[QURT] [01/04/2023] [16:02:31.383] [QUERY READER] : Added to Packets:    servergroupautoaddperm      | globally assign permissions to server groups
[QURT] [01/04/2023] [16:02:31.384] [QUERY READER] : Added to Packets:    servergroupautodelperm      | globally remove permissions from server group
[QURT] [01/04/2023] [16:02:31.385] [QUERY READER] : Added to Packets:    servergroupclientlist       | list clients in a server group
[QURT] [01/04/2023] [16:02:31.419] [QUERY READER] : Added to Packets:    servergroupcopy             | create a copy of an existing server group
[QURT] [01/04/2023] [16:02:31.422] [QUERY READER] : Added to Packets:    servergroupdel              | delete a server group
[QURT] [01/04/2023] [16:02:31.425] [QUERY READER] : Added to Packets:    servergroupdelclient        | remove client from server group
[QURT] [01/04/2023] [16:02:31.427] [QUERY READER] : Added to Packets:    servergroupdelperm          | remove permissions from server group
[QURT] [01/04/2023] [16:02:31.428] [QUERY READER] : Added to Packets:    servergrouplist             | list server groups
[QURT] [01/04/2023] [16:02:31.429] [QUERY READER] : Added to Packets:    servergrouppermlist         | list server group permissions
[QURT] [01/04/2023] [16:02:31.430] [QUERY READER] : Added to Packets:    servergrouprename           | rename a server group
[QURT] [01/04/2023] [16:02:31.431] [QUERY READER] : Added to Packets:    servergroupsbyclientid      | get all server groups of specified client
[QURT] [01/04/2023] [16:02:31.432] [QUERY READER] : Added to Packets:    serveridgetbyport           | find database ID by virtual server port
[QURT] [01/04/2023] [16:02:31.433] [QUERY READER] : Added to Packets:    serverinfo                  | display virtual server properties
[QURT] [01/04/2023] [16:02:31.437] [QUERY READER] : Added to Packets:    serverlist                  | list virtual servers
[QURT] [01/04/2023] [16:02:31.439] [QUERY READER] : Added to Packets:    servernotifyregister        | register for event notifications
[QURT] [01/04/2023] [16:02:31.440] [QUERY READER] : Added to Packets:    servernotifyunregister      | unregister from event notifications
[QURT] [01/04/2023] [16:02:31.441] [QUERY READER] : Added to Packets:    serverprocessstop           | shutdown server process
[QURT] [01/04/2023] [16:02:31.445] [QUERY READER] : Added to Packets:    serverrequestconnectioninfo | display virtual server connection info
[QURT] [01/04/2023] [16:02:31.450] [QUERY READER] : Added to Packets:    serversnapshotcreate        | create snapshot of a virtual server
[QURT] [01/04/2023] [16:02:31.451] [QUERY READER] : Added to Packets:    serversnapshotdeploy        | deploy snapshot of a virtual server
[QURT] [01/04/2023] [16:02:31.451] [QUERY READER] : Added to Packets:    serverstart                 | start a virtual server
[QURT] [01/04/2023] [16:02:31.452] [QUERY READER] : Added to Packets:    serverstop                  | stop a virtual server
[QURT] [01/04/2023] [16:02:31.453] [QUERY READER] : Added to Packets:    servertemppasswordadd       | create a new temporary server password
[QURT] [01/04/2023] [16:02:31.454] [QUERY READER] : Added to Packets:    servertemppassworddel       | delete an existing temporary server password
[QURT] [01/04/2023] [16:02:31.454] [QUERY READER] : Added to Packets:    servertemppasswordlist      | list all existing temporary server passwords
[QURT] [01/04/2023] [16:02:31.455] [QUERY READER] : Added to Packets:    setclientchannelgroup       | set a clients channel group
[QURT] [01/04/2023] [16:02:31.456] [QUERY READER] : Added to Packets:    tokenadd                    | alias for privilegekeyadd
[QURT] [01/04/2023] [16:02:31.456] [QUERY READER] : Added to Packets:    tokendelete                 | alias for privilegekeydelete
[QURT] [01/04/2023] [16:02:31.457] [QUERY READER] : Added to Packets:    tokenlist                   | alias for privilegekeylist
[QURT] [01/04/2023] [16:02:31.458] [QUERY READER] : Added to Packets:    tokenuse                    | alias for privilegekeyuse
[QURT] [01/04/2023] [16:02:31.458] [QUERY READER] : Added to Packets:    use                         | select virtual server
[QURT] [01/04/2023] [16:02:31.459] [QUERY READER] : Added to Packets:    version                     | display version information
[QURT] [01/04/2023] [16:02:31.463] [QUERY READER] : Added to Packets:    whoami                      | display current session info
[QURT] [01/04/2023] [16:02:31.469] [QUERY READER] : Added to Packets: 
[QURT] [01/04/2023] [16:02:31.470] [QUERY READER] : Added to Packets: 
[QURT] [01/04/2023] [16:02:31.471] [QUERY READER] : Added to Errors: error id=0 msg=ok
[main] [01/04/2023] [16:02:31.478] [Event Manager] : Registering Event : net.devcube.vinco.testbot.testbot.Events
[KALT] [01/04/2023] [16:02:31.484] [QUERY WRITER] : Executing Command > (version)
[QURT] [01/04/2023] [16:02:31.485] [QUERY READER] : Added to Packets: version=3.13.7 build=1655727713 platform=Linux
[QURT] [01/04/2023] [16:02:31.486] [QUERY READER] : Added to Errors: error id=0 msg=ok
```

# Second Debug Log Example

Used the same Code, but removed the QueryReader and EventManager Debug.

**Used DebugTypes:**
- QUERYWRITER

As you can see, a good readable log:
```
[main] [01/04/2023] [16:07:13.534] [QUERY WRITER] : Executing Command > (login login Test PASSWORD)
[main] [01/04/2023] [16:07:13.681] [QUERY WRITER] : Executing Command > (use 1)
[main] [01/04/2023] [16:07:13.721] [QUERY WRITER] : Executing Command > (clientupdate client_nickname=Test)
[main] [01/04/2023] [16:07:13.730] [QUERY WRITER] : Executing Command > (servernotifyregister event=server)
[main] [01/04/2023] [16:07:13.733] [QUERY WRITER] : Executing Command > (servernotifyregister event=channel id=0)
[main] [01/04/2023] [16:07:13.734] [QUERY WRITER] : Executing Command > (servernotifyregister event=textserver)
[main] [01/04/2023] [16:07:13.745] [QUERY WRITER] : Executing Command > (servernotifyregister event=textchannel)
[main] [01/04/2023] [16:07:13.747] [QUERY WRITER] : Executing Command > (servernotifyregister event=textprivate)
[main] [01/04/2023] [16:07:13.748] [QUERY WRITER] : Executing Command > (servernotifyregister event=tokenused)
[main] [01/04/2023] [16:07:13.755] [QUERY WRITER] : Executing Command > (whoami)
[main] [01/04/2023] [16:07:13.767] [QUERY WRITER] : Executing Command > (clientmove clid=920 cid=3)
[main] [01/04/2023] [16:07:13.785] [QUERY WRITER] : Executing Command > (help)
[KALT] [01/04/2023] [16:07:13.828] [QUERY WRITER] : Executing Command > (version)
```

# Third Debug Log Example

Used the same Code, but removed the QueryReader and the QueryWriter Debug.

**Used DebugTypes:**
- EVENTMANAGER

As you can see, a nearly empty log:
```
[EVMA] [01/04/2023] [16:12:49.465] [Event Manager] : notifyclientmoved was called
[main] [01/04/2023] [16:12:49.651] [Event Manager] : Registering Event : net.devcube.vinco.testbot.testbot.Events
```

# Fourth Debug Log Example

Used the same Code, but showing only the QueryReader Debug.

**Used DebugTypes:**
- QUERYREADER

As you can see, a log only with the QueryReader:
```
[main] [01/04/2023] [16:21:55.690] [QUERY READER] : Starting listening in QueryReader
[QURT] [01/04/2023] [16:21:55.762] [QUERY READER] : Added to Packets: TS3
[QURT] [01/04/2023] [16:21:55.764] [QUERY READER] : Added to Packets: Welcome to the TeamSpeak 3 ServerQuery interface, type "help" for a list of commands and "help <command>" for information on a specific command.
[QURT] [01/04/2023] [16:21:55.767] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:55.769] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:55.785] [QUERY READER] : Added to Errors: error id=513 msg=nickname\sis\salready\sin\suse
[QURT] [01/04/2023] [16:21:55.791] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:55.825] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:55.827] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:55.828] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:55.830] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:55.832] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:55.835] [QUERY READER] : Added to Packets: virtualserver_status=online virtualserver_id=1 virtualserver_unique_identifier=VUUID virtualserver_port=9987 client_id=700 client_channel_id=1 client_nickname=Test client_database_id=DBID client_login_name=Test client_unique_identifier=UUID client_origin_server_id=1
[QURT] [01/04/2023] [16:21:55.836] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:55.843] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:55.845] [QUERY READER] : Added to Packets: TeamSpeak 3 Server :: ServerQuery
[QURT] [01/04/2023] [16:21:55.846] [QUERY READER] : Added to Packets: (c) TeamSpeak Systems GmbH
[QURT] [01/04/2023] [16:21:55.847] [QUERY READER] : Added to Packets: 
[QURT] [01/04/2023] [16:21:55.848] [QUERY READER] : Added to Packets: ServerQuery is a command-line interface built into the TeamSpeak 3 Server which
[QURT] [01/04/2023] [16:21:55.849] [QUERY READER] : Added to Packets: allows powerful scripting and automation tools to be built based on the exact
[EVMA] [01/04/2023] [16:21:55.855] [QUERY READER] : Called New Event: notifyclientmoved ctid=3 reasonid=0 clid=922
[QURT] [01/04/2023] [16:21:55.856] [QUERY READER] : Added to Packets: same instruction set and functionality provided by the TeamSpeak 3 Client. For
[QURT] [01/04/2023] [16:21:55.865] [QUERY READER] : Added to Packets: example, you can use scripts to automate the management of virtual servers or
[QURT] [01/04/2023] [16:21:55.873] [QUERY READER] : Added to Packets: nightly backups. In short, you can perform operations more efficiently by using
[QURT] [01/04/2023] [16:21:55.874] [QUERY READER] : Added to Packets: ServerQuery scripts than you can by using a user interface.
[QURT] [01/04/2023] [16:21:55.875] [QUERY READER] : Added to Packets: 
[QURT] [01/04/2023] [16:21:55.876] [QUERY READER] : Added to Packets: Command Overview:
[QURT] [01/04/2023] [16:21:55.877] [QUERY READER] : Added to Packets:    apikeyadd                   | create a apikey
[QURT] [01/04/2023] [16:21:55.880] [QUERY READER] : Added to Packets:    apikeydel                   | delete a apikey
[QURT] [01/04/2023] [16:21:55.887] [QUERY READER] : Added to Packets:    apikeylist                  | list apikeys
[QURT] [01/04/2023] [16:21:55.924] [QUERY READER] : Added to Packets:    banadd                      | create a ban rule
[QURT] [01/04/2023] [16:21:55.929] [QUERY READER] : Added to Packets:    banclient                   | ban a client
[QURT] [01/04/2023] [16:21:55.931] [QUERY READER] : Added to Packets:    bandel                      | delete a ban rule
[QURT] [01/04/2023] [16:21:55.932] [QUERY READER] : Added to Packets:    bandelall                   | delete all ban rules
[QURT] [01/04/2023] [16:21:55.932] [QUERY READER] : Added to Packets:    banlist                     | list ban rules on a virtual server
[QURT] [01/04/2023] [16:21:55.933] [QUERY READER] : Added to Packets:    bindinglist                 | list IP addresses used by the server instance
[QURT] [01/04/2023] [16:21:55.934] [QUERY READER] : Added to Packets:    channeladdperm              | assign permission to channel
[QURT] [01/04/2023] [16:21:55.941] [QUERY READER] : Added to Packets:    channelclientaddperm        | assign permission to channel-client combi
[QURT] [01/04/2023] [16:21:55.950] [QUERY READER] : Added to Packets:    channelclientdelperm        | remove permission from channel-client combi
[QURT] [01/04/2023] [16:21:55.952] [QUERY READER] : Added to Packets:    channelclientpermlist       | list channel-client specific permissions
[QURT] [01/04/2023] [16:21:55.952] [QUERY READER] : Added to Packets:    channelcreate               | create a channel
[QURT] [01/04/2023] [16:21:55.953] [QUERY READER] : Added to Packets:    channeldelete               | delete a channel
[QURT] [01/04/2023] [16:21:55.954] [QUERY READER] : Added to Packets:    channeldelperm              | remove permission from channel
[QURT] [01/04/2023] [16:21:55.955] [QUERY READER] : Added to Packets:    channeledit                 | change channel properties
[QURT] [01/04/2023] [16:21:55.965] [QUERY READER] : Added to Packets:    channelfind                 | find channel by name
[QURT] [01/04/2023] [16:21:55.966] [QUERY READER] : Added to Packets:    channelgroupadd             | create a channel group
[QURT] [01/04/2023] [16:21:55.967] [QUERY READER] : Added to Packets:    channelgroupaddperm         | assign permission to channel group
[QURT] [01/04/2023] [16:21:55.968] [QUERY READER] : Added to Packets:    channelgroupclientlist      | find channel groups by client ID
[QURT] [01/04/2023] [16:21:55.969] [QUERY READER] : Added to Packets:    channelgroupcopy            | copy a channel group
[QURT] [01/04/2023] [16:21:55.970] [QUERY READER] : Added to Packets:    channelgroupdel             | delete a channel group
[QURT] [01/04/2023] [16:21:55.970] [QUERY READER] : Added to Packets:    channelgroupdelperm         | remove permission from channel group
[QURT] [01/04/2023] [16:21:55.971] [QUERY READER] : Added to Packets:    channelgrouplist            | list channel groups
[QURT] [01/04/2023] [16:21:55.972] [QUERY READER] : Added to Packets:    channelgrouppermlist        | list channel group permissions
[QURT] [01/04/2023] [16:21:55.973] [QUERY READER] : Added to Packets:    channelgrouprename          | rename a channel group
[QURT] [01/04/2023] [16:21:55.977] [QUERY READER] : Added to Packets:    channelinfo                 | display channel properties
[QURT] [01/04/2023] [16:21:55.980] [QUERY READER] : Added to Packets:    channellist                 | list channels on a virtual server
[QURT] [01/04/2023] [16:21:55.981] [QUERY READER] : Added to Packets:    channelmove                 | move channel to new parent
[QURT] [01/04/2023] [16:21:55.983] [QUERY READER] : Added to Packets:    channelpermlist             | list channel specific permissions
[QURT] [01/04/2023] [16:21:55.985] [QUERY READER] : Added to Packets:    clientaddperm               | assign permission to client
[QURT] [01/04/2023] [16:21:55.986] [QUERY READER] : Added to Packets:    clientdbdelete              | delete client database properties
[QURT] [01/04/2023] [16:21:55.989] [QUERY READER] : Added to Packets:    clientdbedit                | change client database properties
[QURT] [01/04/2023] [16:21:55.990] [QUERY READER] : Added to Packets:    clientdbfind                | find client database ID by nickname or UID
[QURT] [01/04/2023] [16:21:55.991] [QUERY READER] : Added to Packets:    clientdbinfo                | display client database properties
[QURT] [01/04/2023] [16:21:56.020] [QUERY READER] : Added to Packets:    clientdblist                | list known client UIDs
[QURT] [01/04/2023] [16:21:56.026] [QUERY READER] : Added to Packets:    clientdelperm               | remove permission from client
[QURT] [01/04/2023] [16:21:56.027] [QUERY READER] : Added to Packets:    clientedit                  | change client properties
[QURT] [01/04/2023] [16:21:56.028] [QUERY READER] : Added to Packets:    clientfind                  | find client by nickname
[QURT] [01/04/2023] [16:21:56.030] [QUERY READER] : Added to Packets:    clientgetdbidfromuid        | find client database ID by UID
[QURT] [01/04/2023] [16:21:56.031] [QUERY READER] : Added to Packets:    clientgetids                | find client IDs by UID
[QURT] [01/04/2023] [16:21:56.037] [QUERY READER] : Added to Packets:    clientgetnamefromdbid       | find client nickname by database ID
[QURT] [01/04/2023] [16:21:56.038] [QUERY READER] : Added to Packets:    clientgetnamefromuid        | find client nickname by UID
[QURT] [01/04/2023] [16:21:56.039] [QUERY READER] : Added to Packets:    clientgetuidfromclid        | find client UID by client ID
[QURT] [01/04/2023] [16:21:56.040] [QUERY READER] : Added to Packets:    clientinfo                  | display client properties
[QURT] [01/04/2023] [16:21:56.043] [QUERY READER] : Added to Packets:    clientkick                  | kick a client
[QURT] [01/04/2023] [16:21:56.044] [QUERY READER] : Added to Packets:    clientlist                  | list clients online on a virtual server
[QURT] [01/04/2023] [16:21:56.045] [QUERY READER] : Added to Packets:    clientmove                  | move a client
[QURT] [01/04/2023] [16:21:56.046] [QUERY READER] : Added to Packets:    clientpermlist              | list client specific permissions
[QURT] [01/04/2023] [16:21:56.053] [QUERY READER] : Added to Packets:    clientpoke                  | poke a client
[QURT] [01/04/2023] [16:21:56.056] [QUERY READER] : Added to Packets:    clientsetserverquerylogin   | set own login credentials
[QURT] [01/04/2023] [16:21:56.057] [QUERY READER] : Added to Packets:    clientupdate                | set own properties
[QURT] [01/04/2023] [16:21:56.058] [QUERY READER] : Added to Packets:    complainadd                 | create a client complaint
[QURT] [01/04/2023] [16:21:56.059] [QUERY READER] : Added to Packets:    complaindel                 | delete a client complaint
[QURT] [01/04/2023] [16:21:56.060] [QUERY READER] : Added to Packets:    complaindelall              | delete all client complaints
[QURT] [01/04/2023] [16:21:56.062] [QUERY READER] : Added to Packets:    complainlist                | list client complaints on a virtual server
[QURT] [01/04/2023] [16:21:56.063] [QUERY READER] : Added to Packets:    custominfo                  | display custom client properties
[QURT] [01/04/2023] [16:21:56.066] [QUERY READER] : Added to Packets:    customsearch                | search for custom client properties
[QURT] [01/04/2023] [16:21:56.067] [QUERY READER] : Added to Packets:    customset                   | add or update a custom client property.
[QURT] [01/04/2023] [16:21:56.069] [QUERY READER] : Added to Packets:    customdelete                | remove a custom client property.
[QURT] [01/04/2023] [16:21:56.070] [QUERY READER] : Added to Packets:    ftcreatedir                 | create a directory
[QURT] [01/04/2023] [16:21:56.071] [QUERY READER] : Added to Packets:    ftdeletefile                | delete a file
[QURT] [01/04/2023] [16:21:56.073] [QUERY READER] : Added to Packets:    ftgetfileinfo               | display details about a file
[QURT] [01/04/2023] [16:21:56.076] [QUERY READER] : Added to Packets:    ftgetfilelist               | list files stored in a channel filebase
[QURT] [01/04/2023] [16:21:56.077] [QUERY READER] : Added to Packets:    ftinitdownload              | init a file download
[QURT] [01/04/2023] [16:21:56.078] [QUERY READER] : Added to Packets:    ftinitupload                | init a file upload
[QURT] [01/04/2023] [16:21:56.079] [QUERY READER] : Added to Packets:    ftlist                      | list active file transfers
[QURT] [01/04/2023] [16:21:56.080] [QUERY READER] : Added to Packets:    ftrenamefile                | rename a file
[QURT] [01/04/2023] [16:21:56.081] [QUERY READER] : Added to Packets:    ftstop                      | stop a file transfer
[QURT] [01/04/2023] [16:21:56.082] [QUERY READER] : Added to Packets:    gm                          | send global text message
[QURT] [01/04/2023] [16:21:56.083] [QUERY READER] : Added to Packets:    help                        | read help files
[QURT] [01/04/2023] [16:21:56.084] [QUERY READER] : Added to Packets:    hostinfo                    | display server instance connection info
[QURT] [01/04/2023] [16:21:56.085] [QUERY READER] : Added to Packets:    instanceedit                | change server instance properties
[QURT] [01/04/2023] [16:21:56.121] [QUERY READER] : Added to Packets:    instanceinfo                | display server instance properties
[QURT] [01/04/2023] [16:21:56.123] [QUERY READER] : Added to Packets:    logadd                      | add custom entry to log
[QURT] [01/04/2023] [16:21:56.124] [QUERY READER] : Added to Packets:    login                       | authenticate with the server
[QURT] [01/04/2023] [16:21:56.126] [QUERY READER] : Added to Packets:    logout                      | deselect virtual server and log out
[QURT] [01/04/2023] [16:21:56.127] [QUERY READER] : Added to Packets:    logview                     | list recent log entries
[QURT] [01/04/2023] [16:21:56.128] [QUERY READER] : Added to Packets:    messageadd                  | send an offline message
[QURT] [01/04/2023] [16:21:56.129] [QUERY READER] : Added to Packets:    messagedel                  | delete an offline message from your inbox
[QURT] [01/04/2023] [16:21:56.130] [QUERY READER] : Added to Packets:    messageget                  | display an offline message from your inbox
[QURT] [01/04/2023] [16:21:56.131] [QUERY READER] : Added to Packets:    messagelist                 | list offline messages from your inbox
[QURT] [01/04/2023] [16:21:56.132] [QUERY READER] : Added to Packets:    messageupdateflag           | mark an offline message as read
[QURT] [01/04/2023] [16:21:56.134] [QUERY READER] : Added to Packets:    permfind                    | find permission assignments by ID
[QURT] [01/04/2023] [16:21:56.135] [QUERY READER] : Added to Packets:    permget                     | display client permission value for yourself
[QURT] [01/04/2023] [16:21:56.136] [QUERY READER] : Added to Packets:    permidgetbyname             | find permission ID by name
[QURT] [01/04/2023] [16:21:56.137] [QUERY READER] : Added to Packets:    permissionlist              | list permissions available
[QURT] [01/04/2023] [16:21:56.138] [QUERY READER] : Added to Packets:    permoverview                | display client permission overview
[QURT] [01/04/2023] [16:21:56.139] [QUERY READER] : Added to Packets:    permreset                   | delete all server and channel groups and
[QURT] [01/04/2023] [16:21:56.140] [QUERY READER] : Added to Packets:                                | restore default permissions
[QURT] [01/04/2023] [16:21:56.141] [QUERY READER] : Added to Packets:    privilegekeyadd             | creates a new privilege key
[QURT] [01/04/2023] [16:21:56.142] [QUERY READER] : Added to Packets:    privilegekeydelete          | delete an existing privilege key
[QURT] [01/04/2023] [16:21:56.143] [QUERY READER] : Added to Packets:    privilegekeylist            | list all existing privilege keys on this server
[QURT] [01/04/2023] [16:21:56.146] [QUERY READER] : Added to Packets:    privilegekeyuse             | use a privilege key
[QURT] [01/04/2023] [16:21:56.146] [QUERY READER] : Added to Packets:    queryloginadd               | add a query client login
[QURT] [01/04/2023] [16:21:56.147] [QUERY READER] : Added to Packets:    querylogindel               | remove a query client login
[QURT] [01/04/2023] [16:21:56.148] [QUERY READER] : Added to Packets:    queryloginlist              | list all query client logins
[QURT] [01/04/2023] [16:21:56.149] [QUERY READER] : Added to Packets:    quit                        | close connection
[QURT] [01/04/2023] [16:21:56.149] [QUERY READER] : Added to Packets:    sendtextmessage             | send text message
[QURT] [01/04/2023] [16:21:56.150] [QUERY READER] : Added to Packets:    servercreate                | create a virtual server
[QURT] [01/04/2023] [16:21:56.151] [QUERY READER] : Added to Packets:    serverdelete                | delete a virtual server
[QURT] [01/04/2023] [16:21:56.165] [QUERY READER] : Added to Packets:    serveredit                  | change virtual server properties
[QURT] [01/04/2023] [16:21:56.166] [QUERY READER] : Added to Packets:    servergroupadd              | create a server group
[QURT] [01/04/2023] [16:21:56.167] [QUERY READER] : Added to Packets:    servergroupaddclient        | add client to server group
[QURT] [01/04/2023] [16:21:56.168] [QUERY READER] : Added to Packets:    servergroupaddperm          | assign permissions to server group
[QURT] [01/04/2023] [16:21:56.169] [QUERY READER] : Added to Packets:    servergroupautoaddperm      | globally assign permissions to server groups
[QURT] [01/04/2023] [16:21:56.170] [QUERY READER] : Added to Packets:    servergroupautodelperm      | globally remove permissions from server group
[QURT] [01/04/2023] [16:21:56.171] [QUERY READER] : Added to Packets:    servergroupclientlist       | list clients in a server group
[QURT] [01/04/2023] [16:21:56.179] [QUERY READER] : Added to Packets:    servergroupcopy             | create a copy of an existing server group
[QURT] [01/04/2023] [16:21:56.189] [QUERY READER] : Added to Packets:    servergroupdel              | delete a server group
[QURT] [01/04/2023] [16:21:56.190] [QUERY READER] : Added to Packets:    servergroupdelclient        | remove client from server group
[QURT] [01/04/2023] [16:21:56.191] [QUERY READER] : Added to Packets:    servergroupdelperm          | remove permissions from server group
[QURT] [01/04/2023] [16:21:56.192] [QUERY READER] : Added to Packets:    servergrouplist             | list server groups
[QURT] [01/04/2023] [16:21:56.192] [QUERY READER] : Added to Packets:    servergrouppermlist         | list server group permissions
[QURT] [01/04/2023] [16:21:56.193] [QUERY READER] : Added to Packets:    servergrouprename           | rename a server group
[QURT] [01/04/2023] [16:21:56.193] [QUERY READER] : Added to Packets:    servergroupsbyclientid      | get all server groups of specified client
[QURT] [01/04/2023] [16:21:56.220] [QUERY READER] : Added to Packets:    serveridgetbyport           | find database ID by virtual server port
[QURT] [01/04/2023] [16:21:56.221] [QUERY READER] : Added to Packets:    serverinfo                  | display virtual server properties
[QURT] [01/04/2023] [16:21:56.222] [QUERY READER] : Added to Packets:    serverlist                  | list virtual servers
[QURT] [01/04/2023] [16:21:56.223] [QUERY READER] : Added to Packets:    servernotifyregister        | register for event notifications
[QURT] [01/04/2023] [16:21:56.224] [QUERY READER] : Added to Packets:    servernotifyunregister      | unregister from event notifications
[QURT] [01/04/2023] [16:21:56.224] [QUERY READER] : Added to Packets:    serverprocessstop           | shutdown server process
[QURT] [01/04/2023] [16:21:56.225] [QUERY READER] : Added to Packets:    serverrequestconnectioninfo | display virtual server connection info
[QURT] [01/04/2023] [16:21:56.226] [QUERY READER] : Added to Packets:    serversnapshotcreate        | create snapshot of a virtual server
[QURT] [01/04/2023] [16:21:56.227] [QUERY READER] : Added to Packets:    serversnapshotdeploy        | deploy snapshot of a virtual server
[QURT] [01/04/2023] [16:21:56.227] [QUERY READER] : Added to Packets:    serverstart                 | start a virtual server
[QURT] [01/04/2023] [16:21:56.228] [QUERY READER] : Added to Packets:    serverstop                  | stop a virtual server
[QURT] [01/04/2023] [16:21:56.229] [QUERY READER] : Added to Packets:    servertemppasswordadd       | create a new temporary server password
[QURT] [01/04/2023] [16:21:56.230] [QUERY READER] : Added to Packets:    servertemppassworddel       | delete an existing temporary server password
[QURT] [01/04/2023] [16:21:56.230] [QUERY READER] : Added to Packets:    servertemppasswordlist      | list all existing temporary server passwords
[QURT] [01/04/2023] [16:21:56.231] [QUERY READER] : Added to Packets:    setclientchannelgroup       | set a clients channel group
[QURT] [01/04/2023] [16:21:56.232] [QUERY READER] : Added to Packets:    tokenadd                    | alias for privilegekeyadd
[QURT] [01/04/2023] [16:21:56.233] [QUERY READER] : Added to Packets:    tokendelete                 | alias for privilegekeydelete
[QURT] [01/04/2023] [16:21:56.238] [QUERY READER] : Added to Packets:    tokenlist                   | alias for privilegekeylist
[QURT] [01/04/2023] [16:21:56.238] [QUERY READER] : Added to Packets:    tokenuse                    | alias for privilegekeyuse
[QURT] [01/04/2023] [16:21:56.239] [QUERY READER] : Added to Packets:    use                         | select virtual server
[QURT] [01/04/2023] [16:21:56.240] [QUERY READER] : Added to Packets:    version                     | display version information
[QURT] [01/04/2023] [16:21:56.240] [QUERY READER] : Added to Packets:    whoami                      | display current session info
[QURT] [01/04/2023] [16:21:56.241] [QUERY READER] : Added to Packets: 
[QURT] [01/04/2023] [16:21:56.241] [QUERY READER] : Added to Packets: 
[QURT] [01/04/2023] [16:21:56.249] [QUERY READER] : Added to Errors: error id=0 msg=ok
[QURT] [01/04/2023] [16:21:56.281] [QUERY READER] : Added to Packets: version=3.13.7 build=1655727713 platform=Linux
[QURT] [01/04/2023] [16:21:56.282] [QUERY READER] : Added to Errors: error id=0 msg=ok
```