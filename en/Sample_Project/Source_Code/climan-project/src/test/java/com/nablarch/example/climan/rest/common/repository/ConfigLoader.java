package com.nablarch.example.climan.rest.common.repository;

import nablarch.core.repository.SystemRepository;
import nablarch.core.repository.di.DiContainer;
import nablarch.core.repository.di.config.xml.XmlComponentDefinitionLoader;

public final class ConfigLoader {

    private static boolean initialized = false;

    public static void load() {
        if (initialized) {
            return;
        }
        SystemRepository.load(new DiContainer(
                new XmlComponentDefinitionLoader(
                        "com/nablarch/example/climan/rest/common/repository/config-loader.xml")));
        initialized = true;
    }
}
