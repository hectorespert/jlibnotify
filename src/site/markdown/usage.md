# Usage

## The lifecycle

Using the library is always the same four steps: load the native library, initialise a session,
create and show notifications, close the session.

```java
import es.blackleg.jlibnotify.JLibnotify;
import es.blackleg.jlibnotify.JLibnotifyNotification;
import es.blackleg.jlibnotify.core.DefaultJLibnotifyLoader;
import es.blackleg.jlibnotify.exception.JLibnotifyException;

public class Example {

    public static void main(String[] args) throws JLibnotifyException {
        // 1. Load libnotify.so.4 into the process.
        JLibnotify jLibnotify = DefaultJLibnotifyLoader.init().load();

        // 2. Open a session under the name the notification server will display.
        jLibnotify.init("My Application");
        try {
            // 3. Create a notification and send it to the server.
            JLibnotifyNotification notification =
                    jLibnotify.createNotification("Build finished", "All tests passed", "dialog-information");
            notification.setTimeOut(5000);
            notification.show();
        } finally {
            // 4. Close the session.
            jLibnotify.unInit();
        }
    }

}
```

The libnotify session is global to the process, so a single `JLibnotify` instance should be shared
by the whole application rather than created per notification. Instances are not thread safe.

`DefaultJLibnotifyLoader.init()` is the only entry point of the library. Everything else is reached
through the interfaces of the `es.blackleg.jlibnotify` package, which is the only package
applications should depend on: `es.blackleg.jlibnotify.core` and `es.blackleg.jlibnotify.jna` are
implementation detail.

## Updating and closing a notification

A `JLibnotifyNotification` can be reused. Updating it changes the notification already on screen
instead of stacking a new one, as long as `show()` is called again:

```java
JLibnotifyNotification notification = jLibnotify.createNotification("Downloading", "0%", null);
notification.setTimeOut(0); // stays until it is closed or the user dismisses it
notification.show();

notification.update("Downloading", "50%", null);
notification.show();

notification.close();
```

The timeout is a hint the notification server is free to ignore. Besides a duration in
milliseconds, `0` keeps the notification until it is dismissed and `-1` uses the default of the
server.

Notifications hold a pointer owned by the session that created them, so they must not be used after
`unInit()`.

## Icons

The third argument of `createNotification` is either the name of an icon of the current theme, such
as `dialog-information`, `dialog-warning` or `dialog-error`, or the path to an image file. It can
be `null` for a notification without an icon, and so can the body for a notification with a title
only.

## Asking the notification server what it supports

Not every desktop environment implements the whole freedesktop.org notification specification. The
capabilities report which optional features are available, and the server information identifies
the implementation:

```java
ServerInfo serverInfo = jLibnotify.getServerInfo();
System.out.println(serverInfo.getName() + " " + serverInfo.getVersion()
        + " implements the specification " + serverInfo.getSpecVersion());

if (jLibnotify.getServerCapabilities().contains("body-markup")) {
    jLibnotify.createNotification("Report", "<b>42</b> files processed", null).show();
}
```

Both calls need a running notification server answering over D-Bus, so they fail in a headless
session.

## Handling failures

The two operations that can fail before any notification exists report it as checked exceptions,
both extending `JLibnotifyException`:

* `JLibnotifyLoadException`, raised by `load()` when `libnotify.so.4` is missing or cannot be
  loaded. This is what happens on macOS and Windows, and on Linux systems without libnotify
  installed. The underlying `UnsatisfiedLinkError` is kept as the cause.
* `JLibnotifyInitException`, raised by `init(String)` when libnotify refuses to open the session.

An application that has to run on several platforms should degrade gracefully instead of failing:

```java
JLibnotify jLibnotify = null;
try {
    jLibnotify = DefaultJLibnotifyLoader.init().load();
    jLibnotify.init("My Application");
} catch (JLibnotifyException e) {
    // No desktop notifications available here, fall back to another channel.
    jLibnotify = null;
}
```

Failures that happen later, while showing, updating or closing a notification, are reported as
unchecked `RuntimeException`.

## Loading a different library

`DefaultJLibnotifyLoader` also accepts a library name, which is useful when libnotify is installed
outside the standard library path or when another version has to be used:

```java
JLibnotify jLibnotify = new DefaultJLibnotifyLoader("/opt/gnome/lib/libnotify.so.4").load();
```

The name is passed to JNA as is, so anything `com.sun.jna.Native.load` understands works.
