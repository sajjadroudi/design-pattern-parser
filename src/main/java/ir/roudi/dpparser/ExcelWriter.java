package ir.roudi.dpparser;

import ir.roudi.dpparser.core.ClassContainer;
import ir.roudi.dpparser.core.ClassParser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExcelWriter {

    private static final List<String> columns = List.of(
            "Package_Name",
            "Class_Name",
            "Class_Type",
            "Class_Visibility",
            "Class_Is_Abstract",
            "Class_Is_Static",
            "Class_Is_Final",
            "Class_Is_Interface",
            "Extends",
            "Implements",
            "Children",
            "Constructors",
            "Fields",
            "Methods",
            "Override",
            "Has_Static_Method",
            "Has_Final_Method",
            "Has_Abstract_Method",
            "Association",
            "Aggregation",
            "Delegation",
            "Composition",
            "Instantiation",
            "API",
            "Pattern",
            "Role",
            "Description"
    );

    private final XSSFWorkbook workbook = new XSSFWorkbook();
    private final ClassContainer classContainer;
    private final File excelFile;

    public ExcelWriter(ClassContainer classContainer, String excelFileName) {
        this.classContainer = classContainer;
        this.excelFile = new File(excelFileName);
    }

    public void createOutput() {
        var sheet = workbook.createSheet();
        fillHeader(sheet.createRow(0));
        fillClassInformation(sheet);
        writeToFile();
    }

    private void fillHeader(Row headerRow) {
        for(int index = 0; index < columns.size(); index++) {
            headerRow.createCell(index).setCellValue(columns.get(index));
        }
    }

    private void fillClassInformation(Sheet sheet) {
        var allClasses = classContainer.getAllClasses();
        for(int index = 1; index < allClasses.size(); index++) {
            var row = sheet.createRow(index);
            var parser = new ClassParser(allClasses.get(index), classContainer);
            writeClassInformation(row, parser);
        }
    }

    private void writeToFile() {
        try (FileOutputStream fos = new FileOutputStream(excelFile)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeClassInformation(Row row, ClassParser parser) {
        var information = Stream.of(
                parser.extractPackageName(),
                parser.extractClassName(),
                parser.extractClassType(),
                parser.extractClassVisibility(),
                parser.isAbstract(),
                parser.isStatic(),
                parser.isFinal(),
                parser.isClassInterface(),
                parser.extractExtendedClasses(),
                parser.extractImplementedClasses(),
                parser.extractChildren(),
                parser.extractConstructors(),
                parser.extractFields(),
                parser.extractMethodsDetails(),
                parser.findOverriddenMethods(),
                parser.extractStaticMethods(),
                parser.findFinalMethods(),
                parser.extractAbstractMethods(),
                parser.findAssociatedClasses(),
                parser.findAssociatedClasses(),
                parser.getDelegatedClasses(),
                parser.findAssociatedClasses(),
                parser.extractInstantiatedClasses(),
                "API",
                "pattern",
                "role",
                "description"
        )
                .map(Object::toString)
                .collect(Collectors.toList());

        for(int index = 0; index < information.size(); index++) {
            row.createCell(index).setCellValue(information.get(index));
        }
    }

}
