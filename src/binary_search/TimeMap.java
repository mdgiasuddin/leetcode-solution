package binary_search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeMap {

    Map<String, List<Value>> map;

    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        List<Value> values = map.getOrDefault(key, new ArrayList<>());
        values.add(new Value(value, timestamp));

        map.put(key, values);
    }

    public String get(String key, int timestamp) {
        if (!map.containsKey(key))
            return "";

        List<Value> values = map.get(key);

        int l = 0;
        int r = values.size() - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;
            if (values.get(m).timestamp == timestamp) {
                return values.get(m).value;
            } else if (timestamp < values.get(m).timestamp) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        // If r < 0, then there is no value with timestamp <= expected timestamp.
        return r < 0 ? "" : values.get(r).value;
    }
}

class Value {
    String value;
    int timestamp;

    public Value(String value, int timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }
}
