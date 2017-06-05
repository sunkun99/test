package reconsile;

import java.io.*;
import java.util.*;

/**
 * Created by sk on 15/11/5.
 */


public class ReconcileRemoveDuplicated {

    public static void calThirdPartyDiff() {
        try {
            System.out.println("ThirdParty diff begin!");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sk/Documents/业务/对账/对账辅助/DianpingMore")));
            Map<String, Long> hashMap = new HashMap<String, Long>();
            String data = null;
            while((data = br.readLine())!=null) {
                String dpOrderIDAndAmount = data.replaceAll("\\s+", "");
                if(!hashMap.containsKey(dpOrderIDAndAmount)) {
                    hashMap.put(dpOrderIDAndAmount, 1L);
                }else {
                    Long num = hashMap.get(dpOrderIDAndAmount);
                    num++;
                    hashMap.put(dpOrderIDAndAmount, num);
                }
            }

            br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sk/Documents/业务/对账/对账辅助/ThirdPartyMore")));
            BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/sk/Documents/业务/对账/对账辅助/ThirdPartyDiff")));
            while((data = br.readLine())!=null) {
                String dpOrderIDAndAmount = data.replaceAll("\\s+", "");
                if(!hashMap.containsKey(dpOrderIDAndAmount)) {
                    bw1.write(data);
                    bw1.write("\n");
                } else {
                    Long num = hashMap.get(dpOrderIDAndAmount);
                    num--;
                    if(num != 0) {
                        hashMap.put(dpOrderIDAndAmount, num);
                    } else {
                        hashMap.remove(dpOrderIDAndAmount);
                    }
                }
            }
            br.close();
            bw1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("ThirdParty diff end!");
    }

    public static void calDianpingDiff() {
        try {
            System.out.println("Dianping diff begin!");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sk/Documents/业务/对账/对账辅助/ThirdPartyMore")));
            Map<String, Long> hashMap = new HashMap<String, Long>();
            String data = null;
            while((data = br.readLine())!=null) {
                String dpOrderIDAndAmount = data.replaceAll("\\s+", "");
                if(!hashMap.containsKey(dpOrderIDAndAmount)) {
                    hashMap.put(dpOrderIDAndAmount, 1L);
                }else {
                    Long num = hashMap.get(dpOrderIDAndAmount);
                    num++;
                    hashMap.put(dpOrderIDAndAmount, num);
                }
            }

            br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sk/Documents/业务/对账/对账辅助/DianpingMore")));
            BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/sk/Documents/业务/对账/对账辅助/DianpingDiff")));
            while((data = br.readLine())!=null) {
                String dpOrderIDAndAmount = data.replaceAll("\\s+", "");
                if(!hashMap.containsKey(dpOrderIDAndAmount)) {
                    bw1.write(data);
                    bw1.write("\n");
                } else {
                    Long num = hashMap.get(dpOrderIDAndAmount);
                    num--;
                    if(num != 0) {
                        hashMap.put(dpOrderIDAndAmount, num);
                    } else {
                        hashMap.remove(dpOrderIDAndAmount);
                    }
                }
            }
            br.close();
            bw1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Dianping diff end!");
    }


    public static void main(String[] args) throws FileNotFoundException {
        calThirdPartyDiff();
        calDianpingDiff();
    }
}
