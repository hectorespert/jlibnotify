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

import es.blackleg.jlibnotify.exception.JLibnotifyLoadException;

/**
 * Loads the native libnotify library into the process.
 *
 * <p>This is the first step of any use of jlibnotify. The reference implementation is obtained
 * from {@code DefaultJLibnotifyLoader.init()}:</p>
 *
 * <pre>{@code
 * JLibnotify jLibnotify = DefaultJLibnotifyLoader.init().load();
 * }</pre>
 *
 * @author Hector Espert
 */
public interface JLibnotifyLoader {

    /**
     * Loads the native library and returns a usable binding of it.
     *
     * <p>Loading depends on the host: the shared library has to be present, which is only the
     * case on Linux systems with libnotify installed. The returned instance still has to be
     * initialised with {@link JLibnotify#init(String)}.</p>
     *
     * @return a binding of the loaded library, not yet initialised
     * @throws JLibnotifyLoadException if the native library is missing or cannot be loaded
     */
    JLibnotify load() throws JLibnotifyLoadException;

}
