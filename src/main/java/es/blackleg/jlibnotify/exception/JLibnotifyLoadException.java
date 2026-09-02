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
package es.blackleg.jlibnotify.exception;

/**
 * Raised when the native libnotify library cannot be loaded.
 *
 * <p>Thrown by {@link es.blackleg.jlibnotify.JLibnotifyLoader#load()} when the shared library is
 * missing or unusable, which is the case on any platform other than Linux. The original failure,
 * usually an {@link UnsatisfiedLinkError}, is kept as the cause.</p>
 *
 * @author Hector Espert
 */
public class JLibnotifyLoadException extends JLibnotifyException {

    /**
     * Creates an exception with a message and a cause.
     *
     * @param string message describing the failure
     * @param thrwbl failure that caused this one, usually an {@link UnsatisfiedLinkError}
     */
    public JLibnotifyLoadException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

}
