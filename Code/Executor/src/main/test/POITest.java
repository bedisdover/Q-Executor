import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.*;

/**
 * Created by 王栋 on 2016/8/13 0013.
 */
public class POITest {

    @Test
    public void test1() throws IOException {
        HSSFWorkbook workbook = null;
        //建立工作簿
        workbook = new HSSFWorkbook(new FileInputStream("test.xls"));
        //获取工作表的个数
        int numOfSheets = workbook.getNumberOfSheets();
        //获取第一个sheet 一般只有一个sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();

        for(int i = firstRowNum;i<firstRowNum;i++){
            HSSFRow row = sheet.getRow(i);
            System.out.print(row.getCell(0).getStringCellValue()+" ");
            System.out.print(row.getCell(1).getStringCellValue()+" ");
            System.out.print(row.getCell(2).getStringCellValue()+" ");
            System.out.print(row.getCell(3).getStringCellValue()+" ");
            System.out.print(row.getCell(4).getStringCellValue()+" ");
            System.out.print(row.getCell(5).getStringCellValue()+" ");
            System.out.println();
        }


    }

    @Test
    public void test2() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("test.xls");
        InputStreamReader reader = new InputStreamReader(fileInputStream,"GBK");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        while ((line=bufferedReader.readLine())!=null){
            String[] infos = line.split("\t");
            for(String str : infos){
                System.out.print(str+"*");
            }
            System.out.println();
        }

        bufferedReader.close();
    }
}
