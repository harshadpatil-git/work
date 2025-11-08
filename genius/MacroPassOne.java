import java.util.*;

public class MacroPassOne {
    public static void main(String[] args) {
        String[] code = {
            "START",
            "MACRO",
            "INCR &ARG1,&ARG2",
            "ADD AREG,&ARG2",
            "MEND",
            "MACRO",
            "DECR &ARG3,&ARG4",
            "SUB AREG,&ARG3",
            "MOVER CREG,&ARG4",
            "MEND",
            "INCR N1,N2",
            "DECR N3,N4",
            "END"
        };

        Map<String, Integer> MNT = new LinkedHashMap<>();
        List<String> MDT = new ArrayList<>();
        Map<String, Integer> ALA = new LinkedHashMap<>();

        boolean inMacro = false;
        String macroName = "";
        int mdtp = 0, alaIndex = 1;

        for (String line : code) {
            line = line.trim();
            if (line.equals("MACRO")) { inMacro = true; continue; }
            if (line.equals("MEND")) {
                MDT.add("MEND");
                inMacro = false;
                mdtp++;
                continue;
            }
            if (inMacro) {
                String[] parts = line.split("[ ,]+");
                if (!MNT.containsKey(parts[0])) { // first line after MACRO
                    macroName = parts[0];
                    MNT.put(macroName, mdtp);
                    for (int i = 1; i < parts.length; i++) {
                        if (!ALA.containsKey(parts[i]))
                            ALA.put(parts[i], alaIndex++);
                    }
                } else {
                    for (String key : ALA.keySet())
                        line = line.replace(key, "#" + ALA.get(key));
                    MDT.add(line);
                    mdtp++;
                }
            }
        }

        // --- Display Tables ---
        System.out.println("MNT (Macro Name Table):");
        MNT.forEach((k, v) -> System.out.println(k + "\t-> MDT#" + v));

        System.out.println("\nMDT (Macro Definition Table):");
        for (int i = 0; i < MDT.size(); i++)
            System.out.println(i + "\t" + MDT.get(i));

        System.out.println("\nALA (Argument List Array):");
        ALA.forEach((k, v) -> System.out.println(v + "\t" + k));
    }
}
