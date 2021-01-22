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

import es.blackleg.jlibnotify.ServerInfo;

/**
 *
 * @author Hector Espert
 */
public class BasicServerInfo implements ServerInfo {

    private final String name;
    
    private final String vendor;
    
    private final String version;
    
    private final String specVersion;

    public BasicServerInfo(String name, String vendor, String version, String specVersion) {
        this.name = name;
        this.vendor = vendor;
        this.version = version;
        this.specVersion = specVersion;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVendor() {
        return vendor;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getSpecVersion() {
        return specVersion;
    }

    @Override
    public String toString() {
        return "BasicServerInfo{" + "name=" + name + ", vendor=" + vendor + ", version=" + version + ", specVersion=" + specVersion + '}';
    }

}
