package model.report;


import java.util.HashMap;
import java.util.Map;

/**
 * In app memory representation of report of a search algorithm run.
 * These values will be directly mapped in report file creation which is why it's important to have understandable String values.
 */
public class Report {

    private Map<ReportCodeKey, String> valueMap;

    /**
     * In app memory representation of report of a search algorithm run.
     * Upon constructor sets all ReportCodeKey keys with null as value.
     */
    public Report() {
        valueMap = new HashMap<>();
        for (ReportCodeKey key : ReportCodeKey.values()) {
            valueMap.put(key, null);
        }
    }

    /**
     *
     * @return all values mapped by ReportCodeKey.getStringValue
     */
    public Map<String, String> valuesToMap() {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<ReportCodeKey, String> entry : valueMap.entrySet()) {
            map.put(entry.getKey().getStringValue(), entry.getValue());
        }
        return map;
    }

    /**
     *
     * @param reportCodeKey which key's values is received
     * @return value of the key
     */
    public String getValueOf(ReportCodeKey reportCodeKey) {
        return valueMap.get(reportCodeKey);
    }

    /**
     *
     * @param reportCodeKey which key's values is replaced
     * @param value key's value replacement
     */
    public void setValueOf(ReportCodeKey reportCodeKey, String value) {
        if (valueMap.containsKey(reportCodeKey)) {
            valueMap.put(reportCodeKey, value);
        } else {
            throw new IllegalArgumentException("No such value in report!");
        }
    }

    /**
     *
     * @return true if all keys are non-null
     */
    public boolean isValid() {
        for (Map.Entry<ReportCodeKey, String> entry : valueMap.entrySet()) {
            if (entry.getValue() == null) {
                System.out.println("Missing writing key: " + entry.getKey().getStringValue());
                return false;
            }
        }
        return true;
    }


}
