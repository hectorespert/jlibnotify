# AGENTS.md

Guidance for AI coding agents working in this repository.

## What this is

`es.blackleg:jlibnotify` is a small Java 8 library that wraps the native GNOME
[libnotify](https://developer.gnome.org/libnotify/) C library through
[JNA](https://github.com/java-native-access/jna), so JVM applications can raise desktop
notifications on Linux. It is published to Maven Central and consumed by external projects
(NetBeans Native Notifications, MediathekView), so the public API in
`es.blackleg.jlibnotify` is effectively frozen — adding to it is fine, changing or removing
from it is a breaking change for downstream users.

## Build and test

The Maven wrapper is checked in; always use `./mvnw`.

```bash
./mvnw verify                 # full build: unit tests + integration tests + sources/javadoc jars
./mvnw test                   # unit tests only (Surefire, *Test classes)
./mvnw verify -DskipITs       # skip the native-dependent integration tests
./mvnw site                   # generate the Maven site (also checked in CI)
./mvnw clean                  # also cleans the generated .flattened-pom.xml
```

Running a single test:

```bash
./mvnw test -Dtest=DefaultJLibnotifyTest
./mvnw test -Dtest=DefaultJLibnotifyTest#testInit
./mvnw verify -Dit.test=JLibnotifyLoaderIT     # single integration test (Failsafe)
```

### Integration tests need a real libnotify

`*IT` classes load `libnotify.so.4` through JNA and will fail anywhere the shared library is
absent — including macOS and Windows. CI installs `libnotify4` and `libnotify-bin` on
ubuntu-latest before building. On a non-Linux machine, run `./mvnw test` (or `verify -DskipITs`)
and rely on CI for the integration layer; the repo also ships a `.devcontainer` (Java 21) for a
Linux environment.

Several assertions in `JLibnotifyIT` (server info, server capabilities, showing a notification)
are `@Ignore`d because they need a live D-Bus notification daemon, which headless CI does not have.

## Architecture

Four layers, top to bottom:

1. **Public API** — `es.blackleg.jlibnotify.*`: the interfaces `JLibnotifyLoader`,
   `JLibnotify`, `JLibnotifyNotification`, `ServerInfo`, plus the checked exception hierarchy
   `JLibnotifyException` → `JLibnotifyLoadException` / `JLibnotifyInitException`. Consumers should
   only ever touch these types plus `DefaultJLibnotifyLoader.init()`.
2. **Implementations** — `es.blackleg.jlibnotify.core.*`: `Default*` classes implementing the
   interfaces above, plus `BasicServerInfo` and `ServerCapabilitiesReader`.
3. **JNA binding** — `es.blackleg.jlibnotify.jna.NativeLibnotify`: a `com.sun.jna.Library`
   interface whose methods mirror the C symbols one-for-one (`notify_init`,
   `notify_notification_new`, …). Method names deliberately keep C snake_case; do not rename them.
4. **Native libnotify** itself.

### Entry point and object flow

`DefaultJLibnotifyLoader.init().load()` is the only documented way in. `load()` builds a JNA
`DefaultTypeMapper` with an `EnumConverter` for `GBoolean`, calls `Native.load("libnotify.so.4", …)`,
and wraps the result in a `DefaultJLibnotify`. Any `Throwable` from that (including
`UnsatisfiedLinkError`) is translated into a checked `JLibnotifyLoadException` — that
catch-Throwable is intentional, since native loading failures are `Error`s, not `Exception`s.

From there: `init(appName)` → `createNotification(summary, body, icon)` returns a
`DefaultJLibnotifyNotification` holding the raw `Pointer` from `notify_notification_new`, and every
`show`/`update`/`close` call is delegated straight to the native pointer. Finish with `unInit()`.

### Two conventions that matter

- **`GBoolean` instead of `boolean`.** libnotify returns C `gboolean`; JNA maps it via the enum
  `GBoolean { FALSE, TRUE }` (ordinal-based, so the declaration order is load-bearing) registered
  through the type mapper in the loader. Native-facing methods must return `GBoolean`, and
  implementations compare with `== GBoolean.FALSE` / `== GBoolean.TRUE` before converting to Java
  `boolean` at the API boundary.
- **`ServerCapabilitiesReader` walks a GList by hand.** `notify_get_server_caps()` returns a raw
  `Pointer` to a `GList`; `DefaultServerCapabilitiesReader` reads it as a pointer array where index 0
  is the string payload and index 1 is the `next` node. This is the one place doing manual native
  memory traversal — it is deliberately isolated behind an interface so `DefaultJLibnotify` stays
  testable.

### Testing strategy

Unit tests substitute `NativeLibNotifyMock` (`src/test/java/es/blackleg/jlibnotify/test/`), a pure-Java
implementation of the `NativeLibnotify` interface, so no native library is required. It has magic
inputs — passing the app name `"failOnInit"` makes `notify_init` return `GBoolean.FALSE`, which is how
the failure path is exercised. Extend that mock rather than introducing a mocking framework; there is
no Mockito on the classpath.

Tests are written against the **JUnit 4** API (`org.junit.Test`, `@Before`, `@Ignore`) and run on the
JUnit Platform via `junit-vintage-engine` (the POM tracks the JUnit 6 BOM). Assertions use AssertJ (`assertThat(...)`). Match the
existing style in new tests rather than mixing in JUnit 5 annotations.

## Versioning and release

The POM uses Maven CI-friendly versions: `${revision}${sha1}${changelist}`, defaulting to
`1.0.1-SNAPSHOT`, resolved at build time by `flatten-maven-plugin` into `.flattened-pom.xml`
(committed, regenerated by the build — don't hand-edit it). Releases are cut by creating a GitHub
Release; `.github/workflows/publish.yml` then runs
`./mvnw deploy -P release -Drevision=$GITHUB_REF_NAME -Dchangelist=`, which GPG-signs and publishes to
Maven Central via the Sonatype central plugin, and deploys the Maven site to GitHub Pages. **Never
bump a version by editing `<version>`** — the tag name is the version.

## Conventions

- Java 8 source/target (`maven.compiler.source/target` = `1.8`), even though CI builds on JDK 21.
  No lambdas-only APIs, no `var`, no `List.of` — stay inside the Java 8 language and library surface.
- Every source file starts with the Apache 2.0 license header and carries an `@author Hector Espert`
  javadoc tag. Copy the header from a neighbouring file when adding one.
- 4-space indent, no wildcard imports, fields `private final` and constructor-injected.
- Dependency bumps arrive as monthly Dependabot PRs; that is the normal shape of commits on `master`.
