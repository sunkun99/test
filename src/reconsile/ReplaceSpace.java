package reconsile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sk on 15/11/5.
 */


public class ReplaceSpace {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            Double totalAmount = 0.0d;
            List<String> list = new ArrayList<String>();
            list.add("0218");
            list.add("0219");
            list.add("0220");
            list.add("0221");
            list.add("0222");
            list.add("0223");
            list.add("0224");
            list.add("0225");
            for(String date: list) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sk/Downloads/tmp/final")));
                String data = null;
                Double income = 0.0d;
                Double outcome = 0.0d;
                while((data = br.readLine())!=null) {
                    if(!data.equals("")) {
                        String[] items = data.split("\\s+");
                        if (items[4].startsWith(date) && items[16].equals("01")) {
                            income += Double.valueOf(items[6].replace("^0+", "")) / 100;
                        } else if (items[4].startsWith(date) && items[18].equals("04")) {
                            outcome += Double.valueOf(items[6].replace("^0+", "")) / 100;
                        }
                    }
                }
                br.close();
                System.out.println("date: " + date + "\n" + income + " " + outcome);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("end!");
    }
}
