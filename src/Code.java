import java.util.HashMap;
import java.util.Map;

public class Code {

    static Map<String, String> compMap = new HashMap<>();
    static Map<String, String> jumpMap = new HashMap<>();

    static {
        // Fill the compMap with binary codes
        compMap.put("0", "0101010");
        compMap.put("1", "0111111");
        compMap.put("-1", "0111010");
        compMap.put("D", "0001100");
        compMap.put("A", "0110000");
        compMap.put("!D", "0001101");
        compMap.put("!A", "0110001");
        compMap.put("-D", "0001111");
        compMap.put("-A", "0110011");
        compMap.put("D+1", "0011111");
        compMap.put("A+1", "0110111");
        compMap.put("D-1", "0001110");
        compMap.put("A-1", "0110010");
        compMap.put("D+A", "0000010");
        compMap.put("D-A", "0010011");
        compMap.put("A-D", "0000111");
        compMap.put("D&A", "0000000");
        compMap.put("D|A", "0010101");
        // For the 'M' variations, just replace 'A' with 'M' in the binary
        compMap.put("M", "1110000");
        compMap.put("!M", "1110001");
        compMap.put("-M", "1110011");
        compMap.put("M+1", "1110111");
        compMap.put("M-1", "1110010");
        compMap.put("D+M", "1000010");
        compMap.put("D-M", "1010011");
        compMap.put("M-D", "1000111");
        compMap.put("D&M", "1000000");
        compMap.put("D|M", "1010101");

        // Fill the jumpMap with binary codes
        jumpMap.put("", "000");
        jumpMap.put("JGT", "001");
        jumpMap.put("JEQ", "010");
        jumpMap.put("JGE", "011");
        jumpMap.put("JLT", "100");
        jumpMap.put("JNE", "101");
        jumpMap.put("JLE", "110");
        jumpMap.put("JMP", "111");
    }

    private static String destHelper(String dest) {
        StringBuilder binaryTrans = new StringBuilder("000");

        if (dest.contains("A")) {
            binaryTrans.setCharAt(0, '1');
        }
        if (dest.contains("D")) {
            binaryTrans.setCharAt(1, '1');
        }
        if (dest.contains("M")) {
            binaryTrans.setCharAt(2, '1');
        }

        return binaryTrans.toString();
    }

    private static String compHelper(String comp) {
        return compMap.getOrDefault(comp, "0000000");
    }

    private static String jumpHelper(String jump) {
        return jumpMap.getOrDefault(jump, "000");
    }

    private static String toBinary(String numStr) {
        int number = Integer.parseInt(numStr);

        String binary = Integer.toBinaryString(number);

        binary = String.format("%15s", binary).replace(' ', '0');

        return binary;
    }

    public static String getBinaryCode(Instruction instruction, SymbolTable table) {
        if (instruction.getType() == InstructionType.CINST) {
            String comp = compHelper(instruction.getComp());
            String dest = destHelper(instruction.getDest());
            String jump = jumpHelper(instruction.getJump());

            return "111" + comp + dest + jump;
        } else if (instruction.getType() == InstructionType.AINST) {
            String currentSymbol = instruction.getSymbol();

            if (!Character.isDigit(currentSymbol.charAt(0))) {
                if (table.contains(currentSymbol)) {
                    String value = String.valueOf(table.getAddress(currentSymbol));
                    return "0" + toBinary(value);
                }
                table.addEntry(instruction.getSymbol());
                return "0" + toBinary(String.valueOf(table.getAddress(currentSymbol)));
            } else {
                return "0" + toBinary(instruction.getSymbol());
            }
        }

        return "";
    }

    public static void main(String[] args) {
    }
}
