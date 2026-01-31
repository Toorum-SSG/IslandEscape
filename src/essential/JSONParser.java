package essential;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParser {
    public Map<String, Object> parse(String json){
        json = json.trim();
        if (json.startsWith("{")) {
            return parseObject(json);
        }
        return null;
    }

    public Map<String, Object> parseObject(String json){
        Map<String, Object> result = new HashMap<>();
        json = json.substring(1, json.length() - 1).trim();

        int pos = 0;
        while (pos < json.length()){
            while (pos < json.length() && Character.isWhitespace(json.charAt(pos))){
                pos++;
            }
            if (pos >= json.length()){
                break;
            }
            if (json.charAt(pos) != '"'){
                break;
            }
            int keyStart = pos + 1;
            int keyEnd = json.indexOf('"', keyStart);
            String key = json.substring(keyStart, keyEnd);
            pos = keyEnd + 1;
            while (pos < json.length() && json.charAt(pos) != ':'){
                pos++;
            }
            pos++;
            while (pos < json.length() && Character.isWhitespace(json.charAt(pos))){
                pos++;
            }

            Object value;
            if (json.charAt(pos) == '"'){
                int valueStart = pos + 1;
                int valueEnd = json.indexOf('"', valueStart);
                value = json.substring(valueStart, valueEnd);
                pos = valueEnd + 1;
            } else if (json.charAt(pos) == '[') {
                int arrayEnd = findMatchingBracket(json, pos);
                value = parseArray(json.substring(pos, arrayEnd + 1));
                pos = arrayEnd + 1;
            } else if (json.charAt(pos) == '{') {
                int objEnd = findMatchingBrace(json, pos);
                value = parseObject(json.substring(pos, objEnd + 1));
                pos = objEnd + 1;
            } else if (json.substring(pos).startsWith("true")) {
                value = true;
                pos += 4;
            } else if (json.substring(pos).startsWith("false")) {
                value = false;
                pos += 5;
            }else if (json.substring(pos).startsWith(null)){
                value = null;
                pos += 4;
            } else {
                int numEnd = pos;
                while (numEnd < json.length() && (Character.isDigit(json.charAt(numEnd)) || json.charAt(numEnd) == '.' || json.charAt(numEnd) == '-')){
                    numEnd++;
                }
                value = json.substring(pos, numEnd);
                pos = numEnd;
            }

            result.put(key, value);
            while (pos < json.length() && (Character.isWhitespace(json.charAt(pos)) || json.charAt(pos) == ',')){
                pos++;
            }
        }
        return result;
    }

    public List<Object> parseArray(String json){
        List<Object> result = new ArrayList<>();
        json = json.substring(1, json.length() - 1).trim();
        if (json.isEmpty()){
            return result;
        }

        int pos = 0;
        while (pos < json.length()){
            while (pos < json.length() && Character.isWhitespace(json.charAt(pos))){
                pos++;
            }
            if(pos >= json.length()){
                break;
            }
            Object value;
            if (json.charAt(pos) == '"'){
                int valueStart = pos + 1;
                int valueEnd = json.indexOf('"', valueStart);
                value = json.substring(valueStart, valueEnd);
                pos = valueEnd + 1;
            } else if (json.charAt(pos) == '{') {
                int objEnd = findMatchingBrace(json, pos);
                value = parseObject(json.substring(pos, objEnd + 1));
                pos = objEnd + 1;
            } else {
                int valueEnd = pos;
                while (valueEnd < json.length() && json.charAt(valueEnd) != ','){
                    valueEnd++;
                }
                value = json.substring(pos, valueEnd).trim();
                pos = valueEnd;
            }
            result.add(value);
            while (pos < json.length() && (Character.isWhitespace(json.charAt(pos)) || json.charAt(pos) == ',')){
                pos++;
            }
        }
        return result;
    }

    public int findMatchingBracket(String json, int start){
        int count = 1;
        int pos = start + 1;
        while (pos < json.length() && count > 0){
            if (json.charAt(pos) == '['){
                count++;
            } else if (json.charAt(pos) == ']') {
                pos++;
            }
        }
        return pos - 1;
    }

    public int findMatchingBrace(String json, int start){
        int count = 1;
        int pos = start + 1;
        while (pos < json.length() && count > 0){
            if (json.charAt(pos) == '{'){
                count++;
            } else if (json.charAt(pos) == '}') {
                pos++;
            }
        }
        return pos - 1;
    }
}
