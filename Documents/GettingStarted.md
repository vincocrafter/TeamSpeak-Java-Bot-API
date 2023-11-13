# Getting Started
You want to use the API, but have no idea of how to start.

*(If you are lazy just search for [Q])*

**Here is your introduction**

## 1.Starting the API
To start and use the API, you have to create an Instance
of the Class `Ts3ServerQuery`
*(from net.devcube.vinco.teamspeakapi.query)*.

Then use the `connect` Method to connect the Query
with your TeamSpeak-Server, by providing the needed
Information and catching the Exceptions.

**Like:**

```java
public class Test {
    
    public static void main(String[] args) {
        Ts3ServerQuery query = new Ts3ServerQuery();
        try {
            query.connect("hostname", 10011, "Username", "Passwort", 1, "Ts³-Bot", 1);
        } catch (IOException | QueryLoginException e) {
            e.printStackTrace();
        }
        query.stopQuery();
    }
}
```
To **stop** the **API** (*Query*), just use the `stopQuery()` Method.

**Now you have successfully started and stopped the API.**

[**Q**] But how to use it for automation on TeamSpeak?

[**A**] **Solution**: Use the EventManagement System to execute Code, when Events are triggered.

If you want to know how to use the API in general, skip to **3. Using the API**.  

## 2.Event Management

You can register an Event using the `EventManager` Class, you can
find it in the `Ts3ServerQuery` Class with the Method `getEventManager`.
This Class contains all Methods used in the EventSystem.
To **register** an Event use the `registerEvent` Method, or if
you know what are you doing use the `addTs3Listener` Method.

[**Q**] But what is this `TsListener` Class?

To use this you have to decide which Type of EventCalling you want,
new or old one.

[**Q**] Should I use the **Old** One or the **New** One?

You can choose which one you want, the difference is only in the Codestyle.

Be aware that the **new** one is set as Default, to change
this Setting use the `QueryConfig` Class and change the Type to your preferred one.
By using the Method `setEventCallType()` in the `QueryConfig` Class. 
Which you can find in the `Ts3ServerQuery` Class with the Method `getConfig()`.

### A. Using the Old EventSystem
To use the Old EventSystem you need a separate Class which implements the
`TsEvent` *(from net.devcube.vinco.teamspeakapi.api.api.event)*.
First, use you IDEA to create all needed Methods.

**At this point your Code should look like this:**

```java
public class ExampleEventOld implements TsEvent {

	@Override
	public void onChannelCreate(ChannelCreateEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChannelDeleted(ChannelDeletedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChannelEdit(ChannelEditedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChannelPasswordChanged(ChannelPasswordChangedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClientJoin(ClientJoinEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClientLeave(ClientLeaveEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClientMove(ClientMoveEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerEdit(ServerEditedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextMessage(TextMessageEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChannelMoved(ChannelMovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
```

Now you can replace every "arg0" to a more precise value name.

Second, use these value name
to get the Information provided by the Event in the Method.
Third, use these Event specific Methods and the
general API (see **3. Using the API**).

At last, you register your seperated Class with the `registerEvent` Method
in the `EventManager`, *(in this Example ExampleEventOld)* and now it is done!

[**Q**] But what is if I don't want to implement all Methods in one Class?

**Solution:** Create a Class that extends from the `TsEventAdapter` Class
*(also from net.devcube.vinco.teamspeakapi.api.api.event)*.
Then you can override the Methods you want to use and place your Code in them.

**By now Code should look like this:**
```java
public class OtherExampleEventOld extends TsEventAdapter {

	@Override
	public void onClientJoin(ClientJoinEvent e) {
		
	}
	
	@Override
	public void onClientLeave(ClientLeaveEvent e) {
		
	}
}
```

At last, you register your seperated Class in the `registerEvent` Method
in the `EventManager`, *(in this Example OtherExampleEventOld)* and now you have it done!

### B. Using the New EventSystem
It is nearly the same as using the Old EventSystem.

At first, create a Class that extends from the `TsEventAdapter` Class
*(also from net.devcube.vinco.teamspeakapi.api.api.event)*.

But then do not override the given Methods, just create a **new** Method
with your favorite **Methode name**.

Now, you have to set an `EventHandler`Annotation, this is done
by setting an `@EventHandler`*(also from net.devcube.vinco.teamspeakapi.api.api.event)* before your Method, 
just like the @Override Annotation.
To select which Event you want to use, just set it as an Argument for the Method.

**Then your Code should look like this:**
```java
public class ExampleEventNew extends TsEventAdapter {

	@EventHandler
	public void testClientJoin(ClientJoinEvent event) {
		
	}
}
```
You can repeat this Process for all Events, just by changing the Argument
to any **EventClass** *(from net.devcube.vinco.teamspeakapi.api.api.events)*.

At last, register your seperated Class in the `registerEvent`Method
in the `EventManager`*(in this Example ExampleEventNew)* and it is finished!

## 3.Using the API
### A. Ts3SyncAPI
Everything you should use is in `Ts3SyncAPI` Class 
*(from net.devcube.vinco.teamspeakapi.api.sync)* by using the
`getSyncAPI()` Method, or in the `Ts3AnsycAPI` Class *(from net.devcube.vinco.teamspeakapi.api.async)* 
by using `getAnsycAPI()` Method, both from the `Ts3ServerQuery` Class.

You can only use these Methods also after connecting to the QueryClient.
There are plenty of Methods that require no Arguments. Or Methods which
need Arguments, but you can get these Arguments by using Methods that do not require Arguments. 

Example: use getChannels(), then
use getChannelInfo().

Let us assume you want to do something when a User joins, by using
the joinEvent-EventSystem, to do so you have the full access to Methods from this Event-Class.
With `getClientID()` in the `ClientJoinEvent`-Class you can get the ID of the Client, which joined to the Server.
The Method `getClient(clientID)` from `Ts3SyncAPI` Class, provides all Information
about the Client, **note: this getter can be null if the ClientID is invalid**.
To prevent this, just use `getClientInfo(clientID)` from the `Ts3SyncAPI` Class
and **catch the Exception**, which is called when the Client is **null**.

If you want more information about the Channel, just use the `getChannelInfo()`
Method.

If you want to poke the Client, just use the `pokeClient()` Method.


**By now, your Code could look like this:**

```java
public class OtherExampleEventOld extends TsEventAdapter {

    @Override
    public void onClientJoin(ClientJoinEvent e) {
        Ts3SyncAPI api = QueryTestBot.query.getSyncAPI();

        // "normal" Method of getting a Client
        ClientInfo client = api.getClient(e.getClientID());

        try {
            //save Method of getting a Client
            ClientInfo sameClient = api.getClientInfo(e.getClientID());
        } catch (UnknownClientInfoException e1) {
            //Exception handling if Client is null
        }

        for (int serverGroups : client.getServerGroups()) {
            System.out.println("Client " + client.getName() + " has Servergroup: " + serverGroups);
            ServerGroupInfo serverGroup = api.getServerGroupByID(serverGroups);
            System.out.println("Servergroupname is: " + serverGroup.getName());
        }

        ChannelInfo currentChannel = api.getChannel(client.getChannelID());
        System.out.println("Client is in Channel " + currentChannel.getChannelID());
        System.out.println("Channelname is: " + currentChannel.getName());

        api.pokeClient(client, "Message");
        api.moveClient(client, 0);
        api.addClientToServerGroup(10, client);
        api.removeClientFromServerGroup(10, client);
        api.kickClientFromChannel(client, "Reason");
        api.kickClientFromServer(client, "Reason");
    }
}
```
### B. Ts3BasicAPI
[**Q**] Then what is the Ts3BasicAPI Class?

For a better overview you could use the `Ts3BasicAPI` Class *(from net.devcube.vinco.teamspeakapi.api.sync)*.
This Class only provides Methods with the primitive Data Types and not much method overriding, so it is
easier to start with.

The `Ts3SyncAPI` Class extends from the `Ts3BasicAPI` Class, because of this every method in the `Ts3SyncAPI`
will use a method from the `Ts3BasicAPI`. The `Ts3SyncAPI` Class is only an extension with some special Methods
shown in [AdvancedAPI usage](AdvancedAPI.md).

## List of things you should only touch if you know what you are doing
- Ts3ServerQuery.debug()
- **Anything** in the QueryReader Class (because it sends and receives Packets)
- **Anything** in the QueryWriter Class
- **Anything** in the KeepAliveThread Class (because it keeps the Query connected)