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

/**
 * A notification created by {@link JLibnotify#createNotification(String, String, String)}.
 *
 * <p>Creating a notification does not display it: it is sent to the notification server by
 * {@link #show()}, and the same instance can be reused afterwards to
 * {@link #update(String, String, String) update} or {@link #close()} the notification already on
 * screen.</p>
 *
 * <p>Instances hold a pointer to a native structure that belongs to the libnotify session that
 * created them, so they must not be used after {@link JLibnotify#unInit()}.</p>
 *
 * @author Hector Espert
 */
public interface JLibnotifyNotification {

    /**
     * Sends the notification to the notification server so it is displayed.
     *
     * <p>Calling it again on a notification already displayed refreshes it instead of displaying a
     * second one.</p>
     *
     * @throws RuntimeException if the notification server rejects the notification
     */
    void show();

    /**
     * Sets how long the notification stays on screen.
     *
     * <p>The value is a hint: notification servers are free to ignore it. Beyond a duration in
     * milliseconds, libnotify accepts {@code 0} to keep the notification until the user dismisses
     * it, and {@code -1} to use the default of the server.</p>
     *
     * @param timeout duration in milliseconds, {@code 0} to never expire, or {@code -1} for the
     *                default of the server
     */
    void setTimeOut(int timeout);

    /**
     * Replaces the content of the notification.
     *
     * <p>Only updates the notification in memory; {@link #show()} has to be called again for the
     * change to reach the screen.</p>
     *
     * @param summary short title of the notification
     * @param body    text of the notification, may be {@code null} for a notification with title
     *                only
     * @param icon    name of an icon of the current theme, or path to an image file, may be
     *                {@code null} for a notification without an icon
     * @throws RuntimeException if the notification cannot be updated
     */
    void update(String summary, String body, String icon);

    /**
     * Removes the notification from the screen.
     *
     * @throws RuntimeException if the notification server rejects the request
     */
    void close();

}
