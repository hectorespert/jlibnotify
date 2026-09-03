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

/**
 * Java mapping of the C {@code gboolean} type used by libnotify.
 *
 * <p>JNA converts it by ordinal through an {@code EnumConverter} registered in the type mapper of
 * the loader, so {@link #FALSE} maps to {@code 0} and {@link #TRUE} to any other value.
 * <strong>The declaration order is therefore part of the binding and must not be changed.</strong></p>
 *
 * <p>Implementation detail of the native binding: the public API exposes Java {@code boolean}
 * instead.</p>
 *
 * @author Hector Espert
 */
public enum GBoolean {

    /**
     * The C value {@code FALSE}, mapped by JNA to the ordinal {@code 0}.
     */
    FALSE,

    /**
     * The C value {@code TRUE}, mapped by JNA to the ordinal {@code 1}.
     */
    TRUE
}
