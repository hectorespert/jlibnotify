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

import es.blackleg.jlibnotify.exception.JLibnotifyInitException;
import java.util.Collection;

/**
 *
 * @author Hector Espert
 */
public interface JLibnotify {
    
    void init(String appName) throws JLibnotifyInitException;
    
    boolean isInitted();

    void unInit();
    
    String getAppName();
    
    void setAppName(String appName);
    
    ServerInfo getServerInfo();
    
    Collection<String> getServerCapabilities();

    JLibnotifyNotification createNotification(String summary, String body, String icon);
    
}
