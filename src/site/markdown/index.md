# JLibnotify

Java binding for the native GNOME [libnotify](https://gitlab.gnome.org/GNOME/libnotify) library,
built on [JNA](https://github.com/java-native-access/jna). It lets a JVM application raise desktop
notifications on Linux without shelling out to `notify-send`.

The binding is based on the [IntelliJ LibNotify wrapper](https://github.com/JetBrains/intellij-community/blob/master/platform/platform-impl/src/com/intellij/ui/LibNotifyWrapper.java)
and on the notification module of [OpenBeans](https://github.com/OpenBeans/OpenBeans).

## Requirements

* **Java 8 or later.** The library is compiled for Java 8 and has no dependency other than JNA.
* **Linux with libnotify installed.** The shared library `libnotify.so.4` is loaded at runtime, so
  the library does not work on macOS or Windows; on those platforms loading fails with a
  `JLibnotifyLoadException`. On Debian and Ubuntu the library comes in the `libnotify4` package.
* **A running notification server** to actually display the notifications. Reading the server
  information and its capabilities also needs one, since both answer over D-Bus.

## Installing

Maven:

```xml
<dependency>
    <groupId>es.blackleg</groupId>
    <artifactId>jlibnotify</artifactId>
    <version>1.3.0</version>
</dependency>
```

Gradle:

```groovy
implementation group: 'es.blackleg', name: 'jlibnotify', version: '1.3.0'
```

Releases are published to [Maven Central](https://central.sonatype.com/artifact/es.blackleg/jlibnotify).

## Getting started

```java
JLibnotify jLibnotify = DefaultJLibnotifyLoader.init().load();
jLibnotify.init("My Application");
jLibnotify.createNotification("Summary", "Body", "dialog-information").show();
jLibnotify.unInit();
```

The [usage page](usage.html) covers the full lifecycle, error handling and how to query the
notification server; the [Javadoc](apidocs/index.html) documents every type of the API.

## License

Released under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
