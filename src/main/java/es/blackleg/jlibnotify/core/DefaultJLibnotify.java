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
import es.blackleg.jlibnotify.ServerInfo;
import es.blackleg.jlibnotify.jna.GBoolean;
import java.util.Collection;
import es.blackleg.jlibnotify.JLibnotify;
import es.blackleg.jlibnotify.exception.JLibnotifyInitException;
import es.blackleg.jlibnotify.jna.NativeLibnotify;
import es.blackleg.jlibnotify.JLibnotifyNotification;

/**
 * Reference implementation of {@link JLibnotify} on top of the JNA binding.
 *
 * <p>Delegates every call to {@link NativeLibnotify}, converting the {@link GBoolean} results of
 * the native functions into Java types or into exceptions. Reading the server capabilities is
 * delegated to a {@link ServerCapabilitiesReader}, the only part that walks native memory.</p>
 *
 * <p>Instances are built by {@link DefaultJLibnotifyLoader#load()} once the native library is
 * loaded; application code should depend on the {@link JLibnotify} interface instead.</p>
 *
 * @author Hector Espert
 */
public class DefaultJLibnotify implements JLibnotify {

    private final NativeLibnotify nativeLibnotify;

    private final ServerCapabilitiesReader serverCapabilitiesReader;

    /**
     * Creates a binding over an already loaded native library.
     *
     * @param libnotify                the loaded native library
     * @param serverCapabilitiesReader reader used to walk the capabilities returned by the
     *                                 notification server
     */
    public DefaultJLibnotify(NativeLibnotify libnotify, ServerCapabilitiesReader serverCapabilitiesReader) {
        this.nativeLibnotify = libnotify;
        this.serverCapabilitiesReader = serverCapabilitiesReader;
    }

    @Override
    public void init(String appName) throws JLibnotifyInitException {
        if (this.nativeLibnotify.notify_init(appName) == GBoolean.FALSE) {
            throw new JLibnotifyInitException(String.format("Unable to init %s app", appName));
        }
    }

    @Override
    public boolean isInitted() {
        return nativeLibnotify.notify_is_initted() == GBoolean.TRUE;
    }

    @Override
    public void unInit() {
        nativeLibnotify.notify_uninit();
    }

    @Override
    public String getAppName() {
        return nativeLibnotify.notify_get_app_name();
    }

    @Override
    public void setAppName(String appName) {
        nativeLibnotify.notify_set_app_name(appName);
    }

    @Override
    public ServerInfo getServerInfo() {
        String[] name = new String[1];
        String[] vendor = new String[1];
        String[] version = new String[1];
        String[] specVersion = new String[1];

        if (nativeLibnotify.notify_get_server_info(name, vendor, version, specVersion) == GBoolean.FALSE) {
            throw new RuntimeException("Error when get server info");
        }

        return new BasicServerInfo(name[0], vendor[0], version[0], specVersion[0]);
    }

    @Override
    public Collection<String> getServerCapabilities() {
        return serverCapabilitiesReader.getServerCapabilitiesFromPointer(nativeLibnotify.notify_get_server_caps());
    }

    @Override
    public JLibnotifyNotification createNotification(String summary, String body, String icon) {
        Pointer pointer = nativeLibnotify.notify_notification_new(summary, body, icon);
        return new DefaultJLibnotifyNotification(pointer, nativeLibnotify);
    }

}
