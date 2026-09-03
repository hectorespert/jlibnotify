/*
 * Copyright 2026 Hector Espert.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Public API of jlibnotify, a Java binding for the native GNOME
 * <a href="https://gitlab.gnome.org/GNOME/libnotify">libnotify</a> library.
 *
 * <p>The library lets a JVM application raise desktop notifications on Linux. It requires the
 * shared library {@code libnotify.so.4} at runtime, so it only works on Linux; on any other
 * platform loading fails with a
 * {@link es.blackleg.jlibnotify.exception.JLibnotifyLoadException}.</p>
 *
 * <p>The binding is organised in layers. This package holds the interfaces application code is
 * expected to use; {@link es.blackleg.jlibnotify.core} holds their implementations, and
 * {@link es.blackleg.jlibnotify.jna} holds the JNA declaration of the native symbols. Only the
 * types in this package, the exceptions in {@link es.blackleg.jlibnotify.exception} and the
 * static factory {@link es.blackleg.jlibnotify.core.DefaultJLibnotifyLoader#init()} are meant to
 * be referenced from outside the library.</p>
 *
 * <p>A complete session looks like this:</p>
 *
 * <pre>{@code
 * JLibnotify jLibnotify = DefaultJLibnotifyLoader.init().load();
 * jLibnotify.init("My Application");
 * JLibnotifyNotification notification =
 *         jLibnotify.createNotification("Summary", "Body", "dialog-information");
 * notification.show();
 * jLibnotify.unInit();
 * }</pre>
 *
 * @author Hector Espert
 */
package es.blackleg.jlibnotify;
