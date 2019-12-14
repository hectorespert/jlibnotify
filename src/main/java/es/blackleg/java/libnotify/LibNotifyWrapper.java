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

/**
 *
 * @author Hector Espert
 */
public interface LibNotifyWrapper {
    
    public void init(String appName);
    
    public boolean isAvailable();

    public void unInit();

    public LibNotifyNotification createNotification(String summary, String body, String icon);

    public void showNotification(LibNotifyNotification notification);

    public void closeNotification(LibNotifyNotification notification);
    
}
