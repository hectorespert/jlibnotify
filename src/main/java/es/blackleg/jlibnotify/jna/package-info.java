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
 * JNA binding of the native libnotify symbols.
 *
 * <p>{@link es.blackleg.jlibnotify.jna.NativeLibnotify} mirrors the C functions one for one and
 * deliberately keeps their snake_case names, and {@link es.blackleg.jlibnotify.jna.GBoolean}
 * maps the C {@code gboolean} type. This package is the boundary with native code: it performs
 * no validation and offers no abstraction over libnotify.</p>
 *
 * <p>It is implementation detail. Application code should use {@link es.blackleg.jlibnotify}
 * instead, which owns the pointers returned by the native calls and turns native failures into
 * exceptions.</p>
 *
 * @author Hector Espert
 */
package es.blackleg.jlibnotify.jna;
