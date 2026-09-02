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
 * Implementations of the public API declared in {@link es.blackleg.jlibnotify}.
 *
 * <p>Every class here translates calls of the public interfaces into calls on the JNA binding
 * {@link es.blackleg.jlibnotify.jna.NativeLibnotify}, converting the native {@code gboolean}
 * results into Java types and native failures into exceptions.</p>
 *
 * <p>Application code should reach this package only through
 * {@link es.blackleg.jlibnotify.core.DefaultJLibnotifyLoader#init()}, which is the entry point of
 * the library; the remaining types are implementation detail and may change between releases.</p>
 *
 * @author Hector Espert
 */
package es.blackleg.jlibnotify.core;
