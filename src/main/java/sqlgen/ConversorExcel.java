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
                    artculoDesdeFila(row);
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

    private static Articulo artculoDesdeFila(Row fila){
        String codigo = getStringContent(fila.getCell(0));
        String descripcion = descripcionSanitizadaArticulo(fila);
        String unidadMagentica = "1x10";
        Double precio = fila.getCell(3).getNumericCellValue();
        Boolean importado = esImportado(fila);

        return new Articulo (codigo,descripcion,unidadMagentica,precio,importado);
    }

    private static Boolean esImportado(Row fila) {
        String descripcionRaw = descripcionDesdeFila(fila);
        if(containsIgnoreCase(descripcionRaw,"impor")) return true;
        if(containsIgnoreCase(descripcionRaw,"china")) return true;
        if(containsIgnoreCase(descripcionRaw,"origen")) return true;
        return false;
    }

    private static String descripcionSanitizadaArticulo(Row fila){
        String descripcion =  descripcionDesdeFila(fila);
        if(proximaFila(fila)!= null && proximaFila(fila).getCell(0)==null){ // La descripcion esta cortada
            descripcion = descripcion.concat(descripcionDesdeFila(proximaFila(fila)));
        }
        descripcion = descripcion.split("\\.")[0].split("  ")[0];
        return  descripcion;
    }

    private static String descripcionDesdeFila(Row fila){
        return getStringContent(fila.getCell(1));
    }

    private static Row proximaFila(Row fila) {
        int numeroFila = fila.getRowNum();
        Sheet hoja = fila.getSheet();
        return hoja.getRow(numeroFila + 1);
    }



    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }
}

