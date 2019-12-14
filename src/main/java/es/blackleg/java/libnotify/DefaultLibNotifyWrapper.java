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
package es.blackleg.java.libnotify;

import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.EnumConverter;
import es.blackleg.java.libnotify.jna.GBoolean;
import es.blackleg.java.libnotify.jna.LibNotify;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Hector Espert
 */
public class DefaultLibNotifyWrapper implements LibNotifyWrapper {

    private LibNotify libNotify;

    public DefaultLibNotifyWrapper() {
    }

    public DefaultLibNotifyWrapper(LibNotify libNotify) {
        this.libNotify = libNotify;
    }

    @Override
    public void init(String appName) {
        if (Objects.isNull(libNotify)) {
            EnumConverter enumConverter = new EnumConverter(GBoolean.class);
            DefaultTypeMapper defaultTypeMapper = new DefaultTypeMapper();
            defaultTypeMapper.addTypeConverter(GBoolean.class, enumConverter);
            Map<String, Object> options = new HashMap<>();
            options.put(Library.OPTION_TYPE_MAPPER, defaultTypeMapper);
            this.libNotify = Native.load("libnotify.so.4", LibNotify.class, options);
        }

        if (this.libNotify.notify_init(appName) == GBoolean.FALSE) {
            throw new RuntimeException("Error when init libnotify");
        }
    }

    @Override
    public boolean isAvailable() {
        return Optional.ofNullable(libNotify)
                .map(lib -> lib.notify_is_initted())
                .map(gBoolean -> GBoolean.TRUE == gBoolean)
                .orElse(false);
    }

    @Override
    public void unInit() {
        if (isAvailable()) {
            libNotify.notify_uninit();
        }
    }

    @Override
    public LibNotifyNotification createNotification(String summary, String body, String icon) {
        Pointer pointer = libNotify.notify_notification_new(summary, body, icon);
        return new DefaultLibNotifyNotification(pointer, summary, body, icon);
    }

    @Override
    public void showNotification(LibNotifyNotification notification) {
        if (libNotify.notify_notification_show(notification.getPointer(), Pointer.NULL) == GBoolean.FALSE) {
            throw new RuntimeException("Error when show notification");
        }
    }

    @Override
    public void closeNotification(LibNotifyNotification notification) {
        if (libNotify.notify_notification_close(notification.getPointer(), Pointer.NULL) == GBoolean.FALSE) {
            throw new RuntimeException("Error when show notification");
        }
    }

}
