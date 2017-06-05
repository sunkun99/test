package reconsile;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by sk on 15/11/13.
 */

public class ReplaceSpecific {
    public static void main(String[] args) {
        try {
            Double totalAmount = 0.0d;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sk/Downloads/a")));
//            BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/sk/Downloads/c")));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sk/Downloads/c")));
            Map<String, Integer> hashMap1 = new HashMap<String, Integer>();
            Map<String, Integer> hashMap2 = new HashMap<String, Integer>();
            String data = null;
            while((data = br.readLine())!=null) {
                data = data.replaceAll("/|2016", "");
                data = data.replaceAll("\\s+|:", "");
                data = data.replaceAll("/2/", "/02/");
                if(!hashMap1.containsKey(data)) {
                    hashMap1.put(data, 1);
                } else {
                    int temp = hashMap1.get(data);
                    temp++;
                    hashMap1.put(data, temp);
                }
            }
            while((data = br2.readLine())!=null) {
                data = data.replaceAll("/|2016", "");
                data = data.replaceAll("\\s+|:", "");
                data = data.replaceAll("/2/", "/02/");
                if(!hashMap2.containsKey(data)) {
                    hashMap2.put(data, 1);
                } else {
                    int temp = hashMap1.get(data);
                    temp++;
                    hashMap2.put(data, temp);
                }
            }
            Iterator<String> iter = hashMap1.keySet().iterator();

            while(iter.hasNext()) {
                String key = (String)iter.next();
                if(hashMap2.containsKey(key)) {
                    int tmp1 = hashMap1.get(key);
                    int tmp2 = hashMap2.get(key);
                    if(tmp1 == tmp2) {
                        iter.remove();
                        hashMap2.remove(key);
                    } else if(tmp1 < tmp2) {
                        iter.remove();
                        tmp2 -= tmp1;
                        hashMap2.put(key, tmp2);
                    } else {
                        iter.remove();
                        tmp1 -= tmp2;
                        hashMap1.put(key, tmp1);
                    }
                }
            }
            Set<String> keys = hashMap1.keySet();
            for(String key: keys) {
                System.out.println("HASHMAP1: " + hashMap1.get(key));
            }
            keys = hashMap2.keySet();
            for(String key: keys) {
                System.out.println("HASHMAP2: " + hashMap2.get(key));
            }
            br.close();
            br2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("end!");
    }
}
