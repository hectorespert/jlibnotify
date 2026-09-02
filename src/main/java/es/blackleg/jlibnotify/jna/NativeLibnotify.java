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
package es.blackleg.jlibnotify.jna;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

/**
 * JNA declaration of the native libnotify functions.
 *
 * <p>Each method mirrors one C symbol of the library and deliberately keeps its snake_case name,
 * so the binding can be read against the libnotify documentation; the names must not be
 * refactored. Functions returning a C {@code gboolean} are declared as {@link GBoolean}, and those
 * returning a native structure as a raw {@link Pointer}.</p>
 *
 * <p>Implementation detail: the interface performs no validation and reports failures as native
 * return values. Application code should use {@link es.blackleg.jlibnotify.JLibnotify}, which owns
 * the pointers and turns those failures into exceptions.</p>
 *
 * @author Hector Espert
 */
public interface NativeLibnotify extends Library {
    
    /**
     * Initialises the libnotify session, binding {@code notify_init}.
     *
     * @param app_name name identifying the application before the notification server
     * @return {@link GBoolean#TRUE} if the session was initialised
     */
    GBoolean notify_init(String app_name);
    
    /**
     * Tells whether the session is initialised, binding {@code notify_is_initted}.
     *
     * @return {@link GBoolean#TRUE} if the session is initialised
     */
    GBoolean notify_is_initted();
    
    /**
     * Closes the session and frees its resources, binding {@code notify_uninit}.
     */
    void notify_uninit();
    
    /**
     * Returns the registered application name, binding {@code notify_get_app_name}.
     *
     * @return the application name, or {@code null} if the session is not initialised
     */
    String notify_get_app_name();
    
    /**
     * Replaces the registered application name, binding {@code notify_set_app_name}.
     *
     * @param app_name name identifying the application before the notification server
     */
    void notify_set_app_name(String app_name);

    /**
     * Sets how long a notification stays on screen, binding
     * {@code notify_notification_set_timeout}.
     *
     * @param notification pointer to the notification structure
     * @param timeout      duration in milliseconds, {@code 0} to never expire, or {@code -1} for
     *                     the default of the server
     */
    void notify_notification_set_timeout(Pointer notification, int timeout);

    /**
     * Reads the description of the notification server, binding {@code notify_get_server_info}.
     *
     * <p>The C function returns its values through output parameters, so each argument is a single
     * element array the native call writes into.</p>
     *
     * @param ret_name         array receiving the product name of the server
     * @param ret_vendor       array receiving the vendor of the server
     * @param ret_version      array receiving the version of the server
     * @param ret_spec_version array receiving the supported specification version
     * @return {@link GBoolean#TRUE} if the information could be read
     */
    GBoolean notify_get_server_info(String[] ret_name, String[] ret_vendor, String[] ret_version, String[] ret_spec_version);

    /**
     * Returns the capabilities of the notification server, binding
     * {@code notify_get_server_caps}.
     *
     * @return pointer to the first node of a {@code GList} of capability strings, {@code null} if
     *         the list is empty
     */
    Pointer notify_get_server_caps();

    /**
     * Creates a notification structure, binding {@code notify_notification_new}.
     *
     * @param summary short title of the notification
     * @param body    text of the notification, may be {@code null}
     * @param icon    icon name or image path, may be {@code null}
     * @return pointer to the new notification structure
     */
    Pointer notify_notification_new(String summary, String body, String icon);

    /**
     * Sends a notification to the server, binding {@code notify_notification_show}.
     *
     * @param notification pointer to the notification structure
     * @param error        pointer receiving a {@code GError}, may be {@link Pointer#NULL} to
     *                     ignore the details of a failure
     * @return {@link GBoolean#TRUE} if the notification was accepted
     */
    GBoolean notify_notification_show(Pointer notification, Pointer error);

    /**
     * Replaces the content of a notification, binding {@code notify_notification_update}.
     *
     * @param notification pointer to the notification structure
     * @param summary      short title of the notification
     * @param body         text of the notification, may be {@code null}
     * @param icon         icon name or image path, may be {@code null}
     * @return {@link GBoolean#TRUE} if the notification was updated
     */
    GBoolean notify_notification_update(Pointer notification, String summary, String body, String icon);

    /**
     * Removes a notification from the screen, binding {@code notify_notification_close}.
     *
     * @param notification pointer to the notification structure
     * @param error        pointer receiving a {@code GError}, may be {@link Pointer#NULL} to
     *                     ignore the details of a failure
     * @return {@link GBoolean#TRUE} if the notification was closed
     */
    GBoolean notify_notification_close(Pointer notification, Pointer error);

}
