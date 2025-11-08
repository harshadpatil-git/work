import java.util.*;

public class IntermediateCode {
    //pass 1
    public static void main(String[] args) {
        String[] code = {
            "START 200","MOVER AREG,='5'","MOVEM AREG,X","L1 MOVER BREG,='2'",
            "ORIGIN L1+3","LTORG","NEXT ADD AREG,='1'","SUB BREG,='2'","BC LT,BACK",
            "LTORG","BACK EQU L1","ORIGIN NEXT+5","MULT CREG,='4'","STOP","X DS 1","END"
        };
        int LC=0; 
        Map<String,Integer> SYMTAB=new LinkedHashMap<>();
        Map<String,Integer> LITTAB=new LinkedHashMap<>();
        List<Integer> POOLTAB=new ArrayList<>();
        int litCount=1;
        System.out.println("Intermediate Code:");
        for(String line:code){
            String[] p=line.split("[ ,]+");
            String op=p[0].toUpperCase();
            if(op.equals("START")) LC=Integer.parseInt(p[1]);
            else if(op.equals("END")||op.equals("LTORG")){
                POOLTAB.add(litCount);
                for(String l:LITTAB.keySet()) if(LITTAB.get(l)==-1) LITTAB.put(l,LC++);
            }
            else if(op.equals("ORIGIN")) LC=SYMTAB.get(p[1].split("\\+")[0])+Integer.parseInt(p[1].split("\\+")[1]);
            else if(op.equals("EQU")) SYMTAB.put(p[0],SYMTAB.get(p[2]));
            else if(op.equals("DS")){ SYMTAB.put(p[0],LC); System.out.println(LC+" (DL,01) (C,"+p[2]+")"); LC+=Integer.parseInt(p[2]); }
            else if(op.equals("MOVER")||op.equals("MOVEM")||op.equals("ADD")||op.equals("SUB")||op.equals("MULT")||op.equals("BC")||op.equals("STOP")){
                if(!op.equals("STOP")){
                    if(line.contains("='")){String lit=line.substring(line.indexOf("='")); LITTAB.putIfAbsent(lit,-1);}
                    if(Character.isLetter(p[p.length-1].charAt(0))) SYMTAB.putIfAbsent(p[p.length-1],0);
                }
                System.out.println(LC+" (IS) "+Arrays.toString(p)); LC++;
            } else SYMTAB.putIfAbsent(op,LC);
        }
        System.out.println("\nSYMTAB: "+SYMTAB);
        System.out.println("LITTAB: "+LITTAB);
        System.out.println("POOLTAB: "+POOLTAB);
        System.out.println("Final LC="+LC);
    }
}
