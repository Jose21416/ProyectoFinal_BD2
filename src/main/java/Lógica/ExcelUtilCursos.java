
package Lógica;

import Datos.DCursos;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilCursos {

    public List<DCursos> leerCursosDesdeExcel(String rutaArchivo) throws Exception {
        List<DCursos> lista = new ArrayList<>();

        try (FileInputStream inputStream = new FileInputStream(new File(rutaArchivo));
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet firstSheet = workbook.getSheetAt(0);
            
            int rowStart = firstSheet.getFirstRowNum() + 1; 
            int rowEnd = firstSheet.getLastRowNum();

            for (int i = rowStart; i <= rowEnd; i++) {
                Row nextRow = firstSheet.getRow(i);
                if (nextRow == null) continue;

                String nombre = getStringCellValue(nextRow.getCell(0));
                
                String descripcion = getStringCellValue(nextRow.getCell(1));

                if (nombre.isEmpty()) {
                    continue;
                }
                
                DCursos curso = new DCursos();
                curso.setNombre(nombre);
                curso.setDescripcion(descripcion);
                
                lista.add(curso);
            }
        } catch (IOException e) {
            throw new Exception("Error de I/O al leer el archivo Excel: " + e.getMessage());
        } catch (Exception e) {
             throw new Exception("Error al procesar el archivo Excel. Verifique que las columnas 1 (Nombre) y 2 (Descripción) estén en formato de texto.", e);
        }

        return lista;
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) return "";
        
        try {
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue().trim();
                case NUMERIC -> String.valueOf((long) cell.getNumericCellValue()).trim();
                case FORMULA -> cell.getStringCellValue().trim();
                default -> "";
            };
        } catch (Exception e) {
             return "";
        }
    }
}