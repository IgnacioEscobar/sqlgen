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
                if(filaValida(row)) {
                    Cell firstCell = row.getCell(0);
                    System.out.println(getStringContent(firstCell));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private static String getStringContent(Cell cell){
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

    private static Boolean filaValida(Row fila){
        Cell celdaCodigo = fila.getCell(0);
        if (celdaCodigo == null) return false;
        String codigo = getStringContent(celdaCodigo);
        return codigoValido(codigo.trim());
    }

    private static Boolean codigoValido(String codigo){
        if(codigo.length() > 9) return false;
        return (Character.isDigit(codigo.charAt(0)) ||
           Character.isDigit(codigo.charAt(codigo.length()-1)) ||
           codigo.charAt(codigo.length()-1) == 'W');
    }
}

