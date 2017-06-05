package reconsile;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by sk on 15/11/5.
 */


public class ReplaceComma {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            Double totalAmount = 0.0d;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/sk/Downloads/a")));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/sk/Downloads/b")));
            String data = null;
            List<Integer> list = new ArrayList<Integer>();
            while((data = br.readLine())!=null) {
                if(data.contains("\"消费充值\"") || data.contains("\"单纯充值\"")) {
                    int pos1 = data.indexOf("(");
                    int pos2 = data.indexOf(",");
                    list.add(Integer.parseInt(data.substring(pos1 + 1, pos2)));
                    bw.write(data + "\n");
                }
            }
            br.close();
            bw.close();
            Collections.sort(list);
            System.out.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("end!");
    }
}
