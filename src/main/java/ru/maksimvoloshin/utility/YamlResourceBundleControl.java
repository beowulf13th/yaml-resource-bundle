package ru.maksimvoloshin.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Control for Yaml resource
 */
public class YamlResourceBundleControl extends ResourceBundle.Control {
    private static final Logger log = LoggerFactory.getLogger(YamlResourceBundleControl.class);

    private final List<String> formats = Arrays.asList("yml", "yaml");

    public static final YamlResourceBundleControl INSTANCE = new YamlResourceBundleControl();

    private YamlResourceBundleControl() {
    }

    @Override
    public List<String> getFormats(String baseName) {
        return formats;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IOException {
        log.trace("Create new ResourceBundle for baseName {}", baseName);
        if (CustomUtil.ifAnyNull(baseName, locale, format, loader)) {
            log.debug("Some of one arguments is null baseName = {}, locale = {}, format ={}, loader = {}", baseName, locale, format, loader);
            throw new NullPointerException();
        }
        if (!formats.contains(format.toLowerCase())) {
            log.warn("Only for .yml or .yaml formats");
            return null;
        }

        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, format);
        URL resource = loader.getResource(resourceName);
        if (resource == null) {
            log.warn("Resource for {}.{} is null", bundleName, format);
            return null;
        }
        URLConnection connection = resource.openConnection();
        if (reload) {
            connection.setUseCaches(false);
        }
        return new YamlResourceBundle(connection.getInputStream());
    }

    private final static class YamlResourceBundle extends ResourceBundle {
        private final Map<String, Object> entries;
        private final Parser parser = new Parser();

        private YamlResourceBundle(Map<String, Object> entries) {
            super();
            this.entries = entries;
        }

        public YamlResourceBundle() {
            this(Collections.emptyMap());
        }

        public YamlResourceBundle(InputStream stream) {
            entries = parser.parseDoc(stream);
        }

        @Override
        protected Object handleGetObject(String key) {
            return entries.get(key);
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.enumeration(entries.keySet());
        }

        @Override
        public Set<String> keySet() {
            return entries.keySet();
        }
    }
}
