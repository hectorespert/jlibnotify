/*
 * Copyright 2021 Hector Espert.
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
package es.blackleg.jlibnotify.core;

import com.sun.jna.Pointer;
import java.util.Collection;

/**
 * Reads the list of capabilities the notification server reports.
 *
 * <p>Isolates the traversal of the native {@code GList} returned by
 * {@code notify_get_server_caps} so {@link DefaultJLibnotify} stays free of native memory
 * handling and testable without the native library.</p>
 *
 * <p>Implementation detail: the capabilities are exposed to application code through
 * {@link es.blackleg.jlibnotify.JLibnotify#getServerCapabilities()}.</p>
 *
 * @author Hector Espert
 */
public interface ServerCapabilitiesReader {

    /**
     * Reads the capability strings of a native {@code GList}.
     *
     * @param pointer pointer to the first node of the list, as returned by
     *                {@code notify_get_server_caps}, may be {@code null} for an empty list
     * @return the capabilities held by the list, empty if the list holds none
     */
    Collection<String> getServerCapabilitiesFromPointer(Pointer pointer);

}
