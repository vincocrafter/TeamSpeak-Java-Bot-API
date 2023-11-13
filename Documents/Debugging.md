# Debugging

You have no idea what the API is currently doing? Or simply need more
information because of a Problem or Error.

Your solution is the **internal debugging Tool** provided by the TS-API:

This works simply by using the *QueryConfig* Class with the getter
in the *Ts3ServerQuery* Class.

By adding **Enums** with in the **addDebugItem** Method to
the Debug List you can exactly choose what Type of Actions done
by the API you want to see.


**DebugOutputType Enum** (List of Enums to choose from)**:**
```java
public enum DebugOutputType {
    
    EVERYTHING, GENERAL, EVENTMANAGER, KEEPALIVETHREAD, QUERY, QUERYREADER,
    QUERYWRITER, QUERYREADERQUEUE,

    E_CHANNEL_CREATED, E_CHANNEL_DELETED, E_CHANNEL_DESCRIPTION_EDITED, E_CHANNEL_EDITED, E_CHANNEL_PASSWORD_CHANGED,
    E_CHANNEL_MOVED,E_CLIENT_JOIN, E_CLIENT_LEAVE, E_CLIENT_MOVE, E_PRIVILEGEKEY_USED, E_SERVER_EDITED, E_TEXT_MESSAGE;
}
```

**Selecting which Type of Debug you want to see:**

*(Specifying which Actions done by the API you want to be logged)*

```java
// To select Everything to be debugged, instead of the different selected Types
query.getConfig().addDebugItem(DebugOutputType.EVERYTHING);

query.getConfig().addDebugItem(DebugOutputType.GENERAL); // General Information
query.getConfig().addDebugItem(DebugOutputType.EVENTMANAGER);
//Shows only which EventListener was added/removed or Event was called
query.getConfig().addDebugItem(DebugOutputType.KEEPALIVETHREAD); // Information about the KeepAliveThread
query.getConfig().addDebugItem(DebugOutputType.QUERY); //Query in general
query.getConfig().addDebugItem(DebugOutputType.QUERYREADER); // Query reading incoming Packets and incoming Events
query.getConfig().addDebugItem(DebugOutputType.QUERYREADERQUEUE);
// Query Queue management for Packets | more precise than QUERYREADER for seeing how Packets are handled, but no debug for Events included
query.getConfig().addDebugItem(DebugOutputType.QUERYWRITER); // Query outputs, executed Commands
query.getConfig().addDebugItem(DebugOutputType.CACHEMANAGER); // Actions done by the cache manager which handles storing data
```

**Select which Type of Event related debug you want to see in your Debug-Log:**

*Note: EventManager must not be enabled to see specified Events*
```java
// Debugs the information of the called Event
// Using the Prefix "E_"
query.getConfig().addDebugItem(DebugOutputType.E_CHANNEL_CREATED);
query.getConfig().addDebugItem(DebugOutputType.E_CHANNEL_DELETED);
query.getConfig().addDebugItem(DebugOutputType.E_CHANNEL_DESCRIPTION_EDITED);
query.getConfig().addDebugItem(DebugOutputType.E_CHANNEL_EDITED);
query.getConfig().addDebugItem(DebugOutputType.E_CHANNEL_MOVED);
query.getConfig().addDebugItem(DebugOutputType.E_CHANNEL_PASSWORD_CHANGED);
query.getConfig().addDebugItem(DebugOutputType.E_CLIENT_JOIN);
query.getConfig().addDebugItem(DebugOutputType.E_CLIENT_LEAVE);
query.getConfig().addDebugItem(DebugOutputType.E_CLIENT_MOVE);
query.getConfig().addDebugItem(DebugOutputType.E_PRIVILEGEKEY_USED);
query.getConfig().addDebugItem(DebugOutputType.E_SERVER_EDITED);
query.getConfig().addDebugItem(DebugOutputType.E_TEXT_MESSAGE);
```
**Select which Type of Display you want for your Debug-Log:**
```java
// Select, if the debugging should be written in the Console or in a File
query.getConfig().setDebugType(DebugType.BOTH); // Debugs in File and Console
query.getConfig().setDebugType(DebugType.CONSOLE); //Debugs only in Console | is the default Setting
query.getConfig().setDebugType(DebugType.FILE); //Debugs only in a File | Location is /Logs/<YYYY.MM.dd>.txt
```

**Select more Information in your Debug-Log:**
```java
// Settings for the Log in general, no difference between Console and File
query.getConfig().setShowTimeMilliseconds(true); // Logs Milliseconds in Time
query.getConfig().setShowDate(true); // Logs the current Date
```
## Note:

You can change these Settings them at any point in your running Code to
provide the most flexible debugging.

Avoid using the **QUERYREADER** and **QUERYREADERQUEUE** together, because
the debug of them for logging incoming Packets is the same
(Same for the **EVERYTHING** DebugType, because then are both enabled).
- The **QUERYREADER** informs you about incoming Packets **and Event callings**.
- The **QUERYREADERQUEUE** informs you about incoming Packets **and Removal of them**.

Also, if you use these DebugTypes for debugging be aware that your Log
can easily very long. First because of the length of
Packets used by Methods like **getClientInfo, getClients, getChannelInfo, getChannels, getServerGroups, 
getVirtualServerInfo** and because of the repeated use of these Basic Methods.

**For more information see the ExampleDebugLog** File.

## The Debug-Log Format:

(**See:** Select more Information in your Debug-Log:)

### Defaulformat:

[THREAD] [TIME] [TYPE OF INFORMATION] : INFORMATION

[THREAD] [HH:mm:ss] [TYPE OF INFORMATION] : INFORMATION

**Example:**

[main] [14:33:50] [QUERY READER] : Starting listening in QueryReader

### Most Information:

[THREAD] [DATE] [TIME] [TYPE OF INFORMATION] : INFORMATION

[THREAD] [DD/MM/YYYY] [HH:mm:ss.SSS] [TYPE OF INFORMATION] : INFORMATION

**Example:**

[main] [01/04/2023] [14:33:50.800] [QUERY READER] : Starting listening in QueryReader

## Shortcodes for Threadnames:
- main = **Main**thread
- KALT = **K**eep**Al**ive**T**hread 
- QURT = **Qu**ery**R**eader**T**hread 
- EVMA = **Ev**ent**Ma**nager
