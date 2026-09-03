/*
 * Copyright 2019 Hector Espert <hectorespertpardo@gmail.com>.
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

import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.EnumConverter;
import es.blackleg.jlibnotify.exception.JLibnotifyLoadException;
import es.blackleg.jlibnotify.jna.GBoolean;
import java.util.HashMap;
import java.util.Map;
import es.blackleg.jlibnotify.JLibnotifyLoader;
import es.blackleg.jlibnotify.JLibnotify;
import es.blackleg.jlibnotify.jna.NativeLibnotify;

/**
 * Reference implementation of {@link JLibnotifyLoader}, and the entry point of the library.
 *
 * <p>{@link #init()} builds a loader for the default library name, so a complete start looks
 * like:</p>
 *
 * <pre>{@code
 * JLibnotify jLibnotify = DefaultJLibnotifyLoader.init().load();
 * jLibnotify.init("My Application");
 * }</pre>
 *
 * <p>This is the only class of {@link es.blackleg.jlibnotify.core} application code is expected to
 * name; everything else is reached through the interfaces of {@link es.blackleg.jlibnotify}.</p>
 *
 * @author Hector Espert
 */
public class DefaultJLibnotifyLoader implements JLibnotifyLoader {

    /**
     * Name of the shared library loaded when none is given: version 4 of libnotify.
     */
    private static final String DEFAULT_LIBRARY_NAME = "libnotify.so.4";

    private final String libraryName;

    private final ServerCapabilitiesReader serverCapabilitiesReader;

    /**
     * Creates a loader for the default library name, {@code libnotify.so.4}.
     */
    public DefaultJLibnotifyLoader() {
        this(DEFAULT_LIBRARY_NAME);
    }

    /**
     * Creates a loader for a given library name.
     *
     * <p>Useful to load another version of libnotify, or a copy of it installed outside the
     * standard library path.</p>
     *
     * @param libraryName name of the shared library, as understood by
     *                    {@link com.sun.jna.Native#load(String, Class, Map)}
     */
    public DefaultJLibnotifyLoader(String libraryName) {
        this(libraryName, new DefaultServerCapabilitiesReader());
    }

    /**
     * Creates a loader for a given library name and capabilities reader.
     *
     * @param libraryName              name of the shared library, as understood by
     *                                 {@link com.sun.jna.Native#load(String, Class, Map)}
     * @param serverCapabilitiesReader reader the loaded library will use to walk the capabilities
     *                                 returned by the notification server
     */
    public DefaultJLibnotifyLoader(String libraryName, ServerCapabilitiesReader serverCapabilitiesReader) {
        this.libraryName = libraryName;
        this.serverCapabilitiesReader = serverCapabilitiesReader;
    }

    /**
     * Loads the native library and binds it to a {@link DefaultJLibnotify}.
     *
     * <p>Registers a type mapper converting the C {@code gboolean} results into {@link GBoolean}
     * before handing the interface to JNA. Native loading fails with an {@link Error} rather than
     * an exception, so every {@link Throwable} is caught and reported as a checked
     * {@link JLibnotifyLoadException}.</p>
     *
     * @return a binding of the loaded library, not yet initialised
     * @throws JLibnotifyLoadException if the library is missing or cannot be loaded, which is the
     *                                 case on any platform other than Linux
     */
    @Override
    public JLibnotify load() throws JLibnotifyLoadException {
        try {
            EnumConverter enumConverter = new EnumConverter(GBoolean.class);
            DefaultTypeMapper defaultTypeMapper = new DefaultTypeMapper();
            defaultTypeMapper.addTypeConverter(GBoolean.class, enumConverter);
            Map<String, Object> options = new HashMap<>();
            options.put(Library.OPTION_TYPE_MAPPER, defaultTypeMapper);
            NativeLibnotify nativeLibNotify = Native.load(this.libraryName, NativeLibnotify.class, options);
            return new DefaultJLibnotify(nativeLibNotify, this.serverCapabilitiesReader);
        } catch (Throwable throwable) {
            throw new JLibnotifyLoadException(String.format("Unable to load %s library", this.libraryName), throwable);
        }
    }

    /**
     * Creates a loader for the default library name, {@code libnotify.so.4}.
     *
     * <p>Starting point of the library, meant to be chained with {@link #load()}.</p>
     *
     * @return a loader ready to load the default libnotify library
     */
    public static JLibnotifyLoader init() {
        return new DefaultJLibnotifyLoader();
    }

}
