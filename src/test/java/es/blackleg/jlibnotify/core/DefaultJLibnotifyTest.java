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
package es.blackleg.jlibnotify.core;

import es.blackleg.jlibnotify.test.NativeLibNotifyMock;
import org.junit.Before;
import org.junit.Test;
import es.blackleg.jlibnotify.JLibnotify;
import es.blackleg.jlibnotify.exception.JLibnotifyInitException;
import es.blackleg.jlibnotify.jna.NativeLibnotify;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Hector Espert
 */
public class DefaultJLibnotifyTest {
    
    private JLibnotify libNotify;
    
    @Before
    public void setUp() {
        NativeLibnotify nativeLibNotify = new NativeLibNotifyMock();
        libNotify = new DefaultJLibnotify(nativeLibNotify, null);
    }

    @Test
    public void testInit() throws JLibnotifyInitException {
        libNotify.init("test-init");
    }
    
    @Test(expected = JLibnotifyInitException.class)
    public void testFailedInit() throws JLibnotifyInitException {
        libNotify.init("failOnInit");
    }
    
    @Test
    public void testIsInitted() throws JLibnotifyInitException {
        libNotify.init("test-init");
        assertThat(libNotify.isInitted()).isTrue();
        
        libNotify.unInit();
        assertThat(libNotify.isInitted()).isFalse();
    }


}
