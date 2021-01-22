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

import com.sun.jna.Pointer;
import es.blackleg.jlibnotify.JLibnotifyNotification;
import es.blackleg.jlibnotify.jna.GBoolean;
import es.blackleg.jlibnotify.jna.NativeLibnotify;

/**
 *
 * @author Hector Espert <hectorespertpardo@gmail.com>
 */
public class DefaultJLibnotifyNotification implements JLibnotifyNotification {
    
    private final Pointer pointer;
    
    private final NativeLibnotify nativeLibnotify;
    
    public DefaultJLibnotifyNotification(Pointer pointer, NativeLibnotify nativeLibnotify) {
        this.pointer = pointer;
        this.nativeLibnotify = nativeLibnotify;
    }
    
    @Override
    public void show() {
        if (nativeLibnotify.notify_notification_show(pointer, Pointer.NULL) == GBoolean.FALSE) {
            throw new RuntimeException("Error when show notification"); //TODO: Capture error and throw exception
        }
    }

    @Override
    public void setTimeOut(int timeout) {
        nativeLibnotify.notify_notification_set_timeout(pointer, timeout);
    }

    @Override
    public void update(String summary, String body, String icon) {
        if ( nativeLibnotify.notify_notification_update(pointer, summary, body, icon) == GBoolean.FALSE) {
            throw new RuntimeException("Error when showing notification");
        }
    }

    @Override
    public void close() {
        if (nativeLibnotify.notify_notification_close(pointer, Pointer.NULL) == GBoolean.FALSE) {
            throw new RuntimeException("Error when show notification");
        }
    }

}
