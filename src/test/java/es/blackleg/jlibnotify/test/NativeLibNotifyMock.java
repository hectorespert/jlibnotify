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
package es.blackleg.jlibnotify.test;

import com.sun.jna.Pointer;
import es.blackleg.jlibnotify.jna.GBoolean;
import es.blackleg.jlibnotify.jna.NativeLibnotify;

/**
 *
 * @author Hector Espert <hectorespertpardo@gmail.com>
 */
public class NativeLibNotifyMock implements NativeLibnotify {

    private String appName;
    private int timeout;
    
    private boolean initted = false;

    @Override
    public GBoolean notify_init(String appName) {
        if ("failOnInit".equals(appName)) {
            return GBoolean.FALSE;
        }
        this.appName = appName;
        this.initted = true;
        return GBoolean.TRUE;
    }

    @Override
    public GBoolean notify_is_initted() {
        if (initted) {
            return GBoolean.TRUE;
        }
        return GBoolean.FALSE;
    }

    @Override
    public void notify_uninit() {
        this.initted = false;
    }

    @Override
    public Pointer notify_notification_new(String summary, String body, String icon) {
        return Pointer.NULL;
    }

    @Override
    public GBoolean notify_notification_show(Pointer notification, Pointer error) {
        return GBoolean.TRUE;
    }

    @Override
    public GBoolean notify_notification_update(Pointer notification, String summary, String body, String icon) {
        return GBoolean.TRUE;
    }

    @Override
    public GBoolean notify_notification_close(Pointer notification, Pointer error) {
        return GBoolean.TRUE;
    }

    @Override
    public String notify_get_app_name() {
        return appName;
    }

    @Override
    public void notify_set_app_name(String appName) {
        this.appName = appName;
    }

    @Override
    public void notify_notification_set_timeout(Pointer notification, int timeout) {
        this.timeout = timeout;
    }

    @Override
    public GBoolean notify_get_server_info(String[] ret_name, String[] ret_vendor, String[] ret_version, String[] ret_spec_version) {
        return GBoolean.TRUE;
    }

    @Override
    public Pointer notify_get_server_caps() {
        return Pointer.NULL;
    }
    
}
