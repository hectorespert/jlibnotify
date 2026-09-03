# Java binding for libnotify (jlibnotify)

![Maven Central](https://img.shields.io/maven-central/v/es.blackleg/jlibnotify?style=flat-square)
![Java CI with Maven](https://github.com/hectorespert/jlibnotify/workflows/Java%20CI%20with%20Maven/badge.svg)

[Libnotify](https://developer.gnome.org/libnotify/) Java binding using [Java JNA](https://github.com/java-native-access/jna).

Based in the [IntelliJ LibNotify wrapper](https://github.com/JetBrains/intellij-community/blob/master/platform/platform-impl/src/com/intellij/ui/LibNotifyWrapper.java) and the [OpenBeans notification module](https://github.com/OpenBeans/OpenBeans/blob/master/pkgsrc-coolbeans/ide/files/platform/ro.emilianbold.notifications/src/ro/emilianbold/notifications/linux/jna/LibNotifyLibrary.java).

## Documentation

The project site publishes the [usage guide](https://hectorespert.github.io/jlibnotify/usage.html)
and the [Javadoc](https://hectorespert.github.io/jlibnotify/apidocs/index.html):
<https://hectorespert.github.io/jlibnotify/>

## Requirements

The library needs `libnotify.so.4` at runtime, so it only works on Linux with libnotify installed
(the `libnotify4` package on Debian and Ubuntu). Displaying notifications also needs a running
notification server.

## Usage
### Import the library

- Maven dependency
```
<dependency>
    <groupId>es.blackleg</groupId>
    <artifactId>jlibnotify</artifactId>
    <version>1.3.0</version>
</dependency>
```

- Gradle dependency
```
implementation group: 'es.blackleg', name: 'jlibnotify', version: '1.3.0'
```

### Show a notification

```java
JLibnotify jLibnotify = DefaultJLibnotifyLoader.init().load();
jLibnotify.init("My Application");
jLibnotify.createNotification("Summary", "Body", "dialog-information").show();
jLibnotify.unInit();
```

The [usage guide](https://hectorespert.github.io/jlibnotify/usage.html) covers the full lifecycle,
error handling and how to query the notification server.

### Examples of usage

- [Netbeans Native Notifications](https://github.com/hectorespert/nb-native-notifications)
- [MediathekView](https://github.com/mediathekview/MediathekView)
- [SoulSearching](https://github.com/enteraname74/SoulSearching)
- [obelisk-sp-client](https://github.com/sefiracz/obelisk-sp-client)
- [MCAntiMalware](https://github.com/OpticFusion1/MCAntiMalware)

## Contributing

Architecture notes, build and test commands, and project conventions are documented in
[AGENTS.md](AGENTS.md).
