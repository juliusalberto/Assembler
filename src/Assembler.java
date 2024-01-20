import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Assembler {

    public static void writeFile(String filename, String fileDest) {
        ArrayList<String> toWrite = new ArrayList<>();
        Parser parser = new Parser(filename);
        SymbolTable table = parser.getSymbolTable();
        ArrayList<Instruction> instructions = parser.getInstructions();

        instructions.forEach(instruction -> {
            toWrite.add(Code.getBinaryCode(instruction, table));
        });

        toWrite.removeIf(String::isEmpty);

        fileWrite(fileDest, toWrite);
    }

    public static void fileWrite(String path, ArrayList<String> toBeWritten) {
        try {
            Files.write(Paths.get(path), toBeWritten);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
