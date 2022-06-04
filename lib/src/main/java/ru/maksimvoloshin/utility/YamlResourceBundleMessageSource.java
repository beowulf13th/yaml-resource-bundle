package ru.maksimvoloshin.utility;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.Assert;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A little copy of Spring class
 */
public class YamlResourceBundleMessageSource extends ResourceBundleMessageSource {
    @Override
    protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
        ClassLoader classLoader = getBundleClassLoader();
        Assert.state(classLoader != null, "No bundle ClassLoader set");

        YamlResourceBundleControl control = YamlResourceBundleControl.INSTANCE;
        try {
            return ResourceBundle.getBundle(basename, locale, classLoader, control);
        } catch (UnsupportedOperationException ex) {
            // Probably in a Jigsaw environment on JDK 9+
            String encoding = getDefaultEncoding();
            if (encoding != null && logger.isInfoEnabled()) {
                logger.info("ResourceBundleMessageSource is configured to read resources with encoding '" +
                        encoding + "' but ResourceBundle.Control not supported in current system environment: " +
                        ex.getMessage() + " - falling back to plain ResourceBundle.getBundle retrieval with the " +
                        "platform default encoding. Consider setting the 'defaultEncoding' property to 'null' " +
                        "for participating in the platform default and therefore avoiding this log message.");
            }
        }

        // Fallback: plain getBundle lookup without Control handle
        return ResourceBundle.getBundle(basename, locale, classLoader);
    }
}
