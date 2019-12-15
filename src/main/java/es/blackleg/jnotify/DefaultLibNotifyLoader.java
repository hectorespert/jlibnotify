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
package es.blackleg.jnotify;

import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.EnumConverter;
import es.blackleg.jnotify.jna.GBoolean;
import es.blackleg.jnotify.jna.NativeLibNotify;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Hector Espert <hectorespertpardo@gmail.com>
 */
public class DefaultLibNotifyLoader implements LibNotifyLoader {

    @Override
    public LibNotify load() {
        EnumConverter enumConverter = new EnumConverter(GBoolean.class);
        DefaultTypeMapper defaultTypeMapper = new DefaultTypeMapper();
        defaultTypeMapper.addTypeConverter(GBoolean.class, enumConverter);
        Map<String, Object> options = new HashMap<>();
        options.put(Library.OPTION_TYPE_MAPPER, defaultTypeMapper);
        return new DefaultLibNotify(Native.load("libnotify.so.4", NativeLibNotify.class, options));
    }
    
    public static LibNotifyLoader getInstance() {
        return new DefaultLibNotifyLoader();
    }
    
}
