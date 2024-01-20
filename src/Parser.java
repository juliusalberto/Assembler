import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    private ArrayList<Instruction> instructions;
    private SymbolTable symbolTable;
    public Parser(String filePath) {
        this.symbolTable = new SymbolTable();

        this.instructions = new ArrayList<>();
        parseFile(filePath);

        symbolTable.populateTable(this.instructions);
    }

    private void parseFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String cleanedLine = cleanCode(line);

                if (cleanedLine.isBlank()) {
                    continue;
                }

                Instruction currentInst = new Instruction(cleanedLine);
                instructions.add(currentInst);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    private String cleanCode(String line) {
        if (line.startsWith("//")) {
            return "";
        }

        //remove single line comments
        line = line.split("//")[0];

        //remove multi line comments
        line = line.replaceAll("/\\*.*?\\*/", "");

        //remove whitespace
        line = line.replaceAll("\\s+", "");

        return line;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }
}
