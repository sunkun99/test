package reconsile;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sk on 15/11/5.
 */


public class LianDong33 {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            Double totalAmount = 0.0d;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sk/Downloads/a")));
            Map<String, Long> hashMap = new HashMap<String, Long>();
            String data = null;
            while((data = br.readLine())!=null) {
                String[] items = data.split(",");
                if(items.length > 11 && items[10].equals("33")) {
                    totalAmount += Double.parseDouble(items[6]);
                }
            }
            totalAmount /= 100;
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            System.out.println(decimalFormat.format(totalAmount));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("end!");
    }
}
