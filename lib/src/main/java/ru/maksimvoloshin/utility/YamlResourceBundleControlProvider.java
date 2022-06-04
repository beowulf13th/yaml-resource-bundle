package ru.maksimvoloshin.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;
import java.util.spi.ResourceBundleControlProvider;

public class YamlResourceBundleControlProvider implements ResourceBundleControlProvider {
    private static final Logger log = LoggerFactory.getLogger(YamlResourceBundleControlProvider.class);

    @Override
    public ResourceBundle.Control getControl(String baseName) {
        log.trace("Get control for base name: {}", baseName);

        if (baseName != null) {
            log.debug("baseName param");
            return YamlResourceBundleControl.INSTANCE;
        }

        log.debug("baseName is null");
        return null;
    }
}
