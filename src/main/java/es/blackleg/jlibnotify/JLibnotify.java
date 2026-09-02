/*
 * Copyright 2019 Hector Espert.
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
package es.blackleg.jlibnotify;

import es.blackleg.jlibnotify.exception.JLibnotifyInitException;
import java.util.Collection;

/**
 * A loaded libnotify library, and the entry point for creating notifications.
 *
 * <p>An instance is obtained from a {@link JLibnotifyLoader} and wraps a native library that has
 * already been loaded into the process. It must be initialised with {@link #init(String)} before
 * any notification is created, and released with {@link #unInit()} when the application no longer
 * needs it:</p>
 *
 * <pre>{@code
 * JLibnotify jLibnotify = DefaultJLibnotifyLoader.init().load();
 * jLibnotify.init("My Application");
 * try {
 *     jLibnotify.createNotification("Summary", "Body", "dialog-information").show();
 * } finally {
 *     jLibnotify.unInit();
 * }
 * }</pre>
 *
 * <p>The underlying libnotify session is global to the process, so a single instance should be
 * shared by the whole application. Implementations are not thread safe.</p>
 *
 * @author Hector Espert
 */
public interface JLibnotify {

    /**
     * Initialises the libnotify session under the given application name.
     *
     * <p>Must be called before {@link #createNotification(String, String, String)}. The name is
     * the one notification servers display as the origin of the notifications.</p>
     *
     * @param appName name identifying the application before the notification server
     * @throws JLibnotifyInitException if libnotify refuses to initialise the session
     */
    void init(String appName) throws JLibnotifyInitException;

    /**
     * Tells whether the libnotify session is currently initialised.
     *
     * @return {@code true} if {@link #init(String)} succeeded and {@link #unInit()} has not been
     *         called yet, {@code false} otherwise
     */
    boolean isInitted();

    /**
     * Closes the libnotify session and releases the resources it holds.
     *
     * <p>Notifications created before this call must not be used afterwards. Calling
     * {@link #init(String)} again starts a new session.</p>
     */
    void unInit();

    /**
     * Returns the application name currently registered in the libnotify session.
     *
     * @return the application name, or {@code null} if the session is not initialised
     */
    String getAppName();

    /**
     * Replaces the application name registered in the libnotify session.
     *
     * @param appName name identifying the application before the notification server
     */
    void setAppName(String appName);

    /**
     * Returns the description the notification server gives of itself.
     *
     * <p>Requires a running notification server, which answers over D-Bus.</p>
     *
     * @return name, vendor, version and specification version reported by the server
     * @throws RuntimeException if the server information cannot be read
     */
    ServerInfo getServerInfo();

    /**
     * Returns the optional features the notification server supports.
     *
     * <p>Capabilities are the identifiers of the freedesktop.org notification specification, such
     * as {@code body}, {@code actions} or {@code icon-static}. Requires a running notification
     * server, which answers over D-Bus.</p>
     *
     * @return capabilities reported by the server, empty if it reports none
     */
    Collection<String> getServerCapabilities();

    /**
     * Creates a notification, without displaying it.
     *
     * <p>The notification is displayed by {@link JLibnotifyNotification#show()}. The session must
     * have been initialised with {@link #init(String)} beforehand.</p>
     *
     * @param summary short title of the notification
     * @param body    text of the notification, may be {@code null} for a notification with title
     *                only
     * @param icon    name of an icon of the current theme, or path to an image file, may be
     *                {@code null} for a notification without an icon
     * @return a notification ready to be shown
     */
    JLibnotifyNotification createNotification(String summary, String body, String icon);

}
