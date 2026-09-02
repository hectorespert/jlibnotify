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
 * Description a notification server gives of itself, as returned by
 * {@link JLibnotify#getServerInfo()}.
 *
 * <p>Useful to adapt to the desktop environment in use, together with
 * {@link JLibnotify#getServerCapabilities()}.</p>
 *
 * @author Hector Espert
 */
public interface ServerInfo {

    /**
     * Returns the product name of the notification server.
     *
     * @return the server name, for instance {@code gnome-shell}
     */
    String getName();

    /**
     * Returns the vendor of the notification server.
     *
     * @return the vendor name, for instance {@code GNOME}
     */
    String getVendor();

    /**
     * Returns the version of the notification server.
     *
     * @return the server version
     */
    String getVersion();

    /**
     * Returns the version of the freedesktop.org notification specification the server implements.
     *
     * @return the supported specification version, for instance {@code 1.2}
     */
    String getSpecVersion();

}
