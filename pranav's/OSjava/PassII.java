import java.util.*;

public class PassII {
    public static void main(String[] args) {
        // --- Symbol Table with index mapping ---
        Map<Integer, String> symIndex = Map.of(
            1, "X", 2, "L1", 3, "NEXT", 4, "BACK"
        );
        Map<String, Integer> SYMTAB = Map.of(
            "X", 214, "L1", 202, "NEXT", 207, "BACK", 202
        );

        // --- Literal Table ---
        Map<Integer, Integer> LITTAB = Map.of(
            1, 205, 2, 206, 3, 210, 4, 211, 5, 215
        );

        // --- Intermediate Code ---
        String[] IC = {
            "(AD,01) (C,200)", "(IS,04) 1 (L,1)", "(IS,05) 1 (S,1)", "(IS,04) 2 (L,2)",
            "(AD,03) (S,2)+3", "(AD,05)", "(L,1)", "(L,2)", "(IS,01) 1 (L,3)",
            "(IS,02) 2 (L,4)", "(IS,07) 1 (S,4)", "(AD,05)", "(L,3)", "(L,4)",
            "(AD,04) (S,2)", "(IS,03) 3 (L,5)", "(IS,00)", "(DL,02) (C,1)", "(AD,02)"
        };

        System.out.println("Machine Code:\n");
        for (String line : IC) {
            line = line.replaceAll("[()]", "");
            String[] parts = line.trim().split("\\s+");
            if (parts[0].startsWith("AD")) continue; // skip directives

            String mc = "";
            if (parts[0].startsWith("IS")) {
                String opcode = parts[0].split(",")[1]; // e.g. IS,04 → 04
                String reg = (parts.length > 1) ? parts[1] : "0";
                String operand = "000";

                if (parts.length > 2) {
                    String op = parts[2];
                    if (op.startsWith("S")) {
                        int index = getNum(op);
                        String sym = symIndex.get(index);
                        operand = String.valueOf(SYMTAB.get(sym));
                    } else if (op.startsWith("L")) {
                        operand = String.valueOf(LITTAB.get(getNum(op)));
                    }
                }
                mc = opcode + " " + reg + " " + operand;
            } 
            else if (parts[0].startsWith("DL")) {
                String val = parts[1].split(",")[1];
                mc = "00 0 " + val;
            }

            if (!mc.isEmpty()) System.out.println(mc);
        }
    }

    static int getNum(String s) {
        return Integer.parseInt(s.split(",")[1]);
    }
}
