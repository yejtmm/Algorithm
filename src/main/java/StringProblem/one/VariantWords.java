package StringProblem.one;

/**
 给定两个字符串str1和str2，如果str1和str2中出现的字符种类一样且每种字符出现的次数也一样，那么str1与str2互为变形词。
 请实现函数判断两个字符串是否互为变形词。

 str1=＂123＂，str2=＂231＂，返回true。str1=＂123＂，str2=＂2331＂，返回false。

 */
public class VariantWords {

    /**
     如果字符串str1和str2长度不同，直接返回false。如果长度相同，假设出现字符的编码值在0～255之间，那么先申请一个长度为256的整型数组map，
     map[a]=b代表字符编码为a的字符出现了b次，初始时map[0..255]的值都是0。
     然后遍历字符串str1，统计每种字符出现的数量，比如遍历到字符'a'，其编码值为97，则令map[97]++。
     这样map就成了str1中每种字符的词频统计表。然后遍历字符串str2，每遍历到一个字符，都在map中把词频减下来，
     比如遍历到字符'a'，其编码值为97，则令map[97]--，如果减少之后的值小于0，直接返回false。如果遍历完str2，map中的值也没出现负值，则返回true。
     */

    public boolean idDeformation(String str1, String str2){
        if(str1 == null || str2 == null || str1.length() != str2.length()){
            return false;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int [] map = new int[256];
        for (int i = 0;  i < chars1.length; i++){
            map[chars1[i]]++;
        }
        for (int i = 0; i < chars2.length; i++){
            if (map[chars2[i]]-- == 0){
                return false;
            }
        }
        return true;
    }

    /**
     如果字符的类型有很多，可以用哈希表代替长度为256的整型数组，但整体过程不变。
     如果字符的种类为M，str1和str2的长度为N，那么该方法的时间复杂度为O（N），额外空间复杂度为O（M）。
     */


}
