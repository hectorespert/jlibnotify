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

import com.sun.jna.Pointer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Hector Espert
 */
public class DefaultServerCapabilitiesReader implements ServerCapabilitiesReader {
    
    @Override
    public Collection<String> getServerCapabilitiesFromPointer(Pointer pointer) {
        List<String> serverCapabilities = new ArrayList<>();
        Pointer readedPointer = pointer;
        while (Objects.nonNull(readedPointer)) {
            Pointer[] readedPointers = readedPointer.getPointerArray(0);
            if (readedPointers.length >= 1) {
                String readedString = readedPointers[0].getString(0);
                serverCapabilities.add(readedString);
            }
            if (readedPointers.length >= 2) {
                readedPointer = readedPointers[1];
            } else {
                readedPointer = null;
            }
        }
        return serverCapabilities;
    }
    
}
