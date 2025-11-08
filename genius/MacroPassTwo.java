import java.util.*;

public class MacroPassTwo {
    public static void main(String[] args) {
        // ---------- Macro Definition Table ----------
        String[] MDT = {
            "MOVER AREG, &ARG1",
            "ADD AREG, &ARG2",
            "MEND",
            "MOVER AREG, &ARG3",
            "SUB AREG, &ARG4",
            "MEND"
        };

        // ---------- Macro Name Table ----------
        Map<String, Integer> MNT = Map.of(
            "INCR", 0,
            "DECR", 3
        );

        // ---------- Intermediate Code ----------
        String[] code = {
            "START",
            "INCR N1, N2",
            "DECR N3, N4",
            "END"
        };

        System.out.println("Expanded Code (Pass-II Output):\n");

        for (String line : code) {
            String[] parts = line.split("[ ,]+");
            String name = parts[0];

            // If it's a macro name
            if (MNT.containsKey(name)) {
                int mdtIndex = MNT.get(name);
                Map<String, String> ALA = new HashMap<>();

                // Bind arguments from call to macro parameters
                if (name.equals("INCR")) {
                    ALA.put("&ARG1", parts[1]);
                    ALA.put("&ARG2", parts[2]);
                } else if (name.equals("DECR")) {
                    ALA.put("&ARG3", parts[1]);
                    ALA.put("&ARG4", parts[2]);
                }

                // Expand macro from MDT until MEND
                for (int i = mdtIndex; i < MDT.length && !MDT[i].equals("MEND"); i++) {
                    String expanded = MDT[i];
                    for (Map.Entry<String, String> e : ALA.entrySet())
                        expanded = expanded.replace(e.getKey(), e.getValue());
                    System.out.println(expanded);
                }
            } else {
                // Non-macro line directly printed
                System.out.println(line);
            }
        }
    }
}
