package sqlgen;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;

public class ConversorExcel {
    public static void convertir(String fileURL){
        // Use a file
        try {
            Workbook workbook = WorkbookFactory.create(new File(fileURL));
            Sheet sheet1 = workbook.getSheetAt(0);
            for (Row row : sheet1) {
                Cell firstCell = row.getCell(0);
                System.out.println(getStringContent(firstCell));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private static String getStringContent(Cell cell){
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    return cell.getRichStringCellValue().getString();
                case NUMERIC:
                    return Double.toString(cell.getNumericCellValue());
                case BOOLEAN:
                    return cell.getBooleanCellValue()? "1":"0";
                case FORMULA:
                    return cell.getCellFormula();
                case BLANK:
                    return "";
                default:
                    return "";
            }
        }
        return " ";
    }
}

