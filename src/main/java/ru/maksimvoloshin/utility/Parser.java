package ru.maksimvoloshin.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public final class Parser {
    private static final Logger log = LoggerFactory.getLogger(Parser.class);

    /**
     * Returns map as representation key -> value like in properties file
     *
     * @param stream input stream from basename file
     * @return bundle
     */
    public Map<String, Object> parseDoc(InputStream stream) {
        log.trace("parseDoc");
        final Yaml yaml = new Yaml();
        final LinkedHashMap<String, ?> structureFromFile = yaml.load(stream);
        final Map<String, Object> result = new HashMap<>();

        for (String key : structureFromFile.keySet()) {
            LinkedList<String> joiner = new LinkedList<>();
            joiner.add(key);
            fillResult(key, structureFromFile, result, joiner);
        }

        return result;
    }

    private void fillResult(String key, Map<String, ?> currentNode, Map<String, Object> result, LinkedList<String> joiner) {
        final Object value = currentNode.get(key);
        final String currentKey = String.join(".", joiner);

        if (value instanceof LinkedHashMap) {
            LinkedHashMap<String, ?> castedNode = (LinkedHashMap<String, ?>) value;
            for (String castedNodeKey : castedNode.keySet()) {
                joiner.add(castedNodeKey);
                fillResult(castedNodeKey, castedNode, result, joiner);
                joiner.pollLast();
            }
        }
        if (value instanceof ArrayList) {
            ArrayList array = (ArrayList) value;
            result.put(currentKey, array);
            final int size = array.size();
            for (int i = 0; i < size; i++) {
                Object item = array.get(i);
                if (item instanceof LinkedHashMap) {
                    LinkedHashMap<String, ?> castedArrayObj = (LinkedHashMap<String, ?>) item;
                    for (Map.Entry<String, ?> arrayObjEntry : castedArrayObj.entrySet()) {
                        result.put(currentKey + "[" + i + "]" + "." + arrayObjEntry.getKey(), arrayObjEntry.getValue());
                    }
                } else {
                    result.put(currentKey + "[" + i + "]", item);
                }
            }
            result.put(currentKey + ".size", array.size());
            joiner.add(key);
        }
        if (value instanceof String) {
            result.put(currentKey, value);
        }
    }
}
