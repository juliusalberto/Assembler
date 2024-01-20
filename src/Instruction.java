public class Instruction {
    String input;
    InstructionType type;
    String symbol;
    String dest;
    String comp;
    public Instruction(String input) {
        this.input = input;
        checkType();
    }

    public void checkType() {
        char firstChar = this.input.charAt(0);

        if (firstChar == '@') {
            this.type = InstructionType.AINST;
        } else if (firstChar == '(') {
            this.type = InstructionType.LINST;
        } else {
           this.type = InstructionType.CINST;
        }
    }

    public InstructionType getType() {
        return type;
    }

    public String getSymbol() {
        if (this.type == InstructionType.CINST) {
            return "";
        } else if (this.type == InstructionType.LINST) {
            return this.input.substring(1, input.length() - 1);
        } else {
            return this.input.substring(1, input.length());
        }
    }

    public String getDest() {
        if (this.type != InstructionType.CINST || !this.input.contains("=")) {
            return "";
        }

        String[] splittedString = this.input.split("[=;]");

        return splittedString[0];
    }

    public String getComp() {
        if (this.type != InstructionType.CINST) {
            return "";
        }

        String[] splittedString = this.input.split("[=;]");

        switch (splittedString.length) {
            case 1: return splittedString[0];
            default:
                if (this.input.contains("=")) {
                    return splittedString[1];
                } else {
                    return splittedString[0];
                }
        }
    }

    public String getJump() {
        if (this.type != InstructionType.CINST || !this.input.contains(";")) {
            return "";
        }

        String[] splittedString = this.input.split("[=;]");

        switch (splittedString.length) {
            case 2:
                return splittedString[1];
            default:
                return splittedString[2];
        }
    }

    @Override
    public String toString() {
        return this.input + " " + "\nType: " + this.getType() + "\nSymbol: " + this.getSymbol()
                + "\nDest: " + this.getDest() + "\nComp: " + this.getComp() + "\nJump: " + this.getJump() + "\n";
    }
}
