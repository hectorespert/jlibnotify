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

import com.sun.jna.Pointer;
import es.blackleg.jnotify.jna.GBoolean;
import es.blackleg.jnotify.jna.NativeLibNotify;

/**
 *
 * @author Hector Espert <hectorespertpardo@gmail.com>
 */
public class NativeLibNotifyMock implements NativeLibNotify {

    @Override
    public GBoolean notify_init(String appName) {
        return GBoolean.TRUE;
    }

    @Override
    public GBoolean notify_is_initted() {
        return GBoolean.TRUE;
    }

    @Override
    public void notify_uninit() {
        
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
    public GBoolean notify_notification_close(Pointer notification, Pointer error) {
        return GBoolean.TRUE;
    }
    
}
