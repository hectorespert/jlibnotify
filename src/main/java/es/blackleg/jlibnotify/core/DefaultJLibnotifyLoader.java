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
 *
 * @author Hector Espert
 */
public class DefaultJLibnotifyLoader implements JLibnotifyLoader {
    
    private final String libraryName;
    
    private final ServerCapabilitiesReader serverCapabilitiesReader;
    
    public DefaultJLibnotifyLoader() {
        this.libraryName = "libnotify.so.4";
        this.serverCapabilitiesReader = new DefaultServerCapabilitiesReader();
    }
    
    public DefaultJLibnotifyLoader(String libraryName) {
        this.libraryName = libraryName;
        this.serverCapabilitiesReader = new DefaultServerCapabilitiesReader();
    }

    public DefaultJLibnotifyLoader(String libraryName, ServerCapabilitiesReader serverCapabilitiesReader) {
        this.libraryName = libraryName;
        this.serverCapabilitiesReader = serverCapabilitiesReader;
    }

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
    
    public static JLibnotifyLoader init() {
        return new DefaultJLibnotifyLoader();
    }
    
}
