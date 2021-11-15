# TeamSpeak-Java-Bot-API
Java solution for using the Teamspeak Query Interface and a Socket connection.

Usage:
```java
public class Test {
    
    public static void main(String[] args) {
        Ts3ServerQuery query = new Ts3ServerQuery();
        try {
            query.connect("hostname", 10011, "Username", "Passwort", 1, "Ts³-Bot", 1);
        } catch (IOException | QueryLoginException e) {
            e.printStackTrace();
        }

    }
}
```
Using the Teamspeak Events:
```java
query.getEventManager().addTs3Listener(new TsEventAdapter() {
			//Override Methods here,
			@Override
			public void onChannelCreate(ChannelCreateEvent e) {
				
			}
		});
```
