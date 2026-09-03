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
 * Checked exceptions raised by the library.
 *
 * <p>{@link es.blackleg.jlibnotify.exception.JLibnotifyException} is the common parent;
 * {@link es.blackleg.jlibnotify.exception.JLibnotifyLoadException} reports a failure while
 * loading the native library, and
 * {@link es.blackleg.jlibnotify.exception.JLibnotifyInitException} a failure while initialising
 * the notification session. They cover the two operations that can fail before any notification
 * is created.</p>
 *
 * @author Hector Espert
 */
package es.blackleg.jlibnotify.exception;
