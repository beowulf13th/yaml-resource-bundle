package ru.maksimvoloshin.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

class BundleTest {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", YamlResourceBundleControl.INSTANCE);

    @Test
    public void doSomething() {
        List<String> keys = Collections.list(bundle.getKeys());
        Assertions.assertEquals(13, keys.size());
    }
}
