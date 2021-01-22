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

import es.blackleg.jlibnotify.core.DefaultJLibnotify;
import es.blackleg.jlibnotify.core.DefaultJLibnotifyLoader;
import es.blackleg.jlibnotify.exception.JLibnotifyLoadException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Hector Espert
 */
public class LibNotifyLoaderIT {

    @Test
    public void testLoad() throws JLibnotifyLoadException {
        JLibnotifyLoader libNotifyLoader = DefaultJLibnotifyLoader.init();
        assertThat(libNotifyLoader).isNotNull();
        assertThat(libNotifyLoader).isInstanceOf(DefaultJLibnotifyLoader.class);
        assertThat(libNotifyLoader.load()).isInstanceOf(DefaultJLibnotify.class);
    }
    
    @Test(expected = JLibnotifyLoadException.class)
    public void testLoadBadLibrary() throws JLibnotifyLoadException {
        JLibnotifyLoader libNotifyLoader = new DefaultJLibnotifyLoader("badLibnotify.so");
        libNotifyLoader.load();
    }

}
