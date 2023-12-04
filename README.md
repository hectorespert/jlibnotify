# Java binding for libnotify (jlibnotify)

![Maven Central](https://img.shields.io/maven-central/v/es.blackleg/jlibnotify?style=flat-square)
![Java CI with Maven](https://github.com/hectorespert/jlibnotify/workflows/Java%20CI%20with%20Maven/badge.svg)

[Libnotify](https://developer.gnome.org/libnotify/) Java binding using [Java JNA](https://github.com/java-native-access/jna).

Based in the [IntelliJ LibNotify wrapper](https://github.com/JetBrains/intellij-community/blob/master/platform/platform-impl/src/com/intellij/ui/LibNotifyWrapper.java) and the [OpenBeans notification module](https://github.com/OpenBeans/OpenBeans/blob/master/pkgsrc-coolbeans/ide/files/platform/ro.emilianbold.notifications/src/ro/emilianbold/notifications/linux/jna/LibNotifyLibrary.java).

## Usage
### Import the library

- Maven dependency
```
<dependency>
    <groupId>es.blackleg</groupId>
    <artifactId>jlibnotify</artifactId>
    <version>1.1.0</version>
</dependency>
```

- Gradle dependency
```
implementation group: 'es.blackleg', name: 'jlibnotify', version: '1.1.1'
```

### Examples of usage

- [Netbeans Native Notifications](https://github.com/hectorespert/nb-native-notifications)
- [MediathekView](https://github.com/mediathekview/MediathekView)


