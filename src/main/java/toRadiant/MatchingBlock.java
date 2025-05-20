package toRadiant;

public class MatchingBlock {
    public static String Get(String s){
        String withoutPrefix = s.startsWith("minecraft:") ? s.substring("minecraft:".length()) : s;

        int bracketIndex = withoutPrefix.indexOf('[');
        if (bracketIndex != -1) {
            withoutPrefix = withoutPrefix.substring(0, bracketIndex);
        }

        if(s.equals("stone")){
            String fs = "_mc_block_" + withoutPrefix;
            return fs;
        }

        return withoutPrefix;
    }
}
