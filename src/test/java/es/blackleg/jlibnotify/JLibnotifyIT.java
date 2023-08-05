
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

import es.blackleg.jlibnotify.core.DefaultJLibnotifyLoader;
import es.blackleg.jlibnotify.exception.JLibnotifyInitException;
import es.blackleg.jlibnotify.exception.JLibnotifyLoadException;
import java.util.Collection;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Hector Espert
 */
public class JLibnotifyIT {
    
    private JLibnotify libNotify;
    
    @Before
    public void setUp() throws JLibnotifyLoadException, JLibnotifyInitException {
        if (Objects.isNull(libNotify)) {
            libNotify = DefaultJLibnotifyLoader.init().load();
        }
        
        assertThat(libNotify.isInitted()).isFalse();
        libNotify.init("LibNotifyIT");
        assertThat(libNotify.isInitted()).isTrue();
    }
    
    @After
    public void tearDown() {
        assertThat(libNotify.isInitted()).isTrue();
        libNotify.unInit();
        assertThat(libNotify.isInitted()).isFalse();
    }
    
    
    @Test
    public void testAppName() {
        assertThat(libNotify.getAppName()).isEqualTo("LibNotifyIT");
        libNotify.setAppName("LibNotifyTest");
        assertThat(libNotify.getAppName()).isEqualTo("LibNotifyTest");
    }
    
    @Test
    public void testServerInfo() {
        ServerInfo serverInfo = libNotify.getServerInfo();
        assertThat(serverInfo).isNotNull();
        assertThat(serverInfo).extracting(ServerInfo::getName).isNotNull();
        assertThat(serverInfo).extracting(ServerInfo::getVendor).isNotNull();
        assertThat(serverInfo).extracting(ServerInfo::getVersion).isNotNull();
        assertThat(serverInfo).extracting(ServerInfo::getSpecVersion).isNotNull();
    }
    
    @Test
    public void testServerCapabilities() {
        Collection<String> capabilities = libNotify.getServerCapabilities();
        assertThat(capabilities).isNotEmpty();
        assertThat(capabilities).contains("actions", "body", "body-markup", "icon-static", "persistence", "sound");
    }

    @Test
    public void testNotification() throws InterruptedException {
        JLibnotifyNotification notification = libNotify.createNotification("LibNotify IT", "LibNotify Integration test", "dialog-information");
        assertThat(notification).isNotNull();
        
        notification.show();
        notification.setTimeOut(6000);
        Thread.sleep(1000);
        notification.update("LibNotify IT override","LibNotify Integration test override", "dialog-information");
        notification.show();
        Thread.sleep(1000);
        notification.close();
    }

}
