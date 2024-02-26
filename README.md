# IP as Server Name

## Overview

When adding a new server, if a name isn't provided, the mod will save the server with its IP address as the name.

## How It Works

The underlying code simply injects into the `addAndClose()` method inside the `AddServerScreen` class to check whether the server name equals `"Minecraft Server"` (the default server name), and if so sets the name to the address
```java
private void addAndClose(CallbackInfo ci) {
    if (server.name.equals("Minecraft Server")) {
        server.name = server.address;
    }
}
```
(the server variable comes from a `@Shadow`)

## Get the Mod

Simply head over to the [Releases](https://github.com/RedVortexDev/IPAsServerName/releases/latest) page and download the mod!
