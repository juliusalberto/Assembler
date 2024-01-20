import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    public HashMap<String, Integer> symbolTable;
    public int firstFreeAddress = 16;
    public int offset = 0;

    public SymbolTable() {
        this.symbolTable = new HashMap<>();
        for (int i = 0; i <= 15; i++) {
            symbolTable.put("R" + i, i);
        }

        // Adding other predefined symbols
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
    }

    public void addEntry(String symbol) {
        symbolTable.put(symbol, firstFreeAddress++);
    }

    public boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    public int getAddress(String symbol) {
        return symbolTable.getOrDefault(symbol, -1);
    }

    public void populateTable(ArrayList<Instruction> instructions) {
        for (int i = 0; i < instructions.size(); i++) {
            Instruction currInst = instructions.get(i);

            if (currInst.getType() == InstructionType.LINST) {
                symbolTable.put(currInst.getSymbol(), i - offset);
                offset++;
            }
        }
    }
}
