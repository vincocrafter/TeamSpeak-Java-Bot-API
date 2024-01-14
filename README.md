# TeamSpeak-Java-Bot-API
A Java solution using the Teamspeak Query Interface and a Socket connection.

## Features:
- Contains everything from the TeamSpeak Query Manual
- Keeps the connection to the server using a KeepAliveThread
- Event system using threads
- Advanced options for debugging and logging
- Synchronous and asynchronous API

## Usage:
Everything the API needs is in the `Ts3ServerQuery` Class and the
`Ts3SyncAPI` Class.
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
## Getting Started:
See [How to start with the API](https://github.com/vincocrafter/TeamSpeak-Java-Bot-API/wiki/Advanced-API).

## How to use debugging:
See [How to debug with the API](https://github.com/vincocrafter/TeamSpeak-Java-Bot-API/wiki/Debugging) 
or [Advanced Debugging](https://github.com/vincocrafter/TeamSpeak-Java-Bot-API/wiki/Advanced-Debugging).\
Or see [Example Log](https://github.com/vincocrafter/TeamSpeak-Java-Bot-API/wiki/Example-Debug-Log).

## Getting advanced Information:
See [AdvancedAPI usage](https://github.com/vincocrafter/TeamSpeak-Java-Bot-API/wiki/Advanced-API).

## FloodRate:
The Floodrate for the TsAPI is set to 0 ms per default (`FloodRate.DEFAULT_TSAPI`).\
TeamSpeaks default Floodrate is set to 350 ms (`FloodRate.DEFAULT`).\
(`FloodRate.UNLIMITED`) is set to 0 like the `FloodRate.DEFAULT_TSAPI` so it does not
matter which one you choose and you do not have to set this one in the most chases.\
Or set a custom value with `FloodRate.custom(300)`.