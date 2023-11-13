# Debugging

You want to debug your own Code without using `System.println()`?

## 1. Enabling Debugtype:
For your own debugging you should add `DebugOutputType.GENERAL` using
`QueryConfig#addDebugItem(DebugOutputType.GENERAL)` to the list of Actions the API should debug.
Otherwise, nothing you debug with this DebugType is debugged and/or logged.
Be aware that you should not use any other DebugType for your own debugging because then
you get the query debugs its own debug messages and your own one, `DebugOutputType.GENERAL` is not used
by the API itself, so it is the best choice.

## 2. Debug:
Now you can use anywhere in your code `Ts3ServerQuery#debug(DebugOutputType.QUERYREADER, "")`
to debug your personal Information and later on you can remove the `DebugOutputType.GENERAL`
from your debuglist at any time.
Note that if you have Filelogging enabled (`DebugType.FILE` or `DebugType.BOTH`) all your debug messages a 
stored in the default querylogs (Logs/).

## 3. Logging:
Or you use `Ts3ServerQuery#getLogger().logFile()` to write your debug messages in a
selected logfile, be aware that for this you `DebugOutputType.GENERAL` should be added in the debuglist.