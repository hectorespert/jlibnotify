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

import java.util.Collection;

/**
 *
 * @author Hector Espert
 */
public interface LibNotify {
    
    void init(String appName);
    
    boolean isAvailable();

    void unInit();
    
    String getAppName();
    
    void setAppName(String appName);
    
    ServerInfo getServerInfo();
    
    Collection<String> getServerCapabilities();

    Notification createNotification(String summary, String body, String icon);

    void showNotification(Notification notification);

    void updateNotification(Notification notification, String summary, String body, String icon);

    void setTimeOut(Notification notification, int timeout);

    void closeNotification(Notification notification);
    
}
