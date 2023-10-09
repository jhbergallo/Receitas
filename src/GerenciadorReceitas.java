import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GerenciadorReceitas {
    private ArrayList<Receita> receitas;

    public GerenciadorReceitas(){
        this.receitas = new ArrayList<>();
    }
    
    public void add(Receita receita){
        receitas.add(receita);
        
    }

    public int size(){
        return receitas.size();
    }

    public boolean remove(String nome){
        Receita r = searchName(nome);
        if(r == null){
            return false;
        } else{
            receitas.remove(r);
            return true;
        }
    }

    public Receita get(int index){
        return receitas.get(index);
    }

    public String get(String nome){
        Receita r = searchName(nome);
        if(r == null){
            return "";
        } else{
            return r.toString();
        }
    }

    public ArrayList<Receita> getReceitas(){
        return receitas;
    }

    private Receita searchName(String nome){
        for(Receita r : receitas){
            String nome1 = r.getNome().toLowerCase();

            if(nome1.contains(nome.toLowerCase())){
                return r;
            }
        }
        return null;
    }

    public String[] searchIngredients(String ingrediente){
        String [] rec = new String[receitas.size()];
        int index = 0;
        for(Receita r : receitas){
            for(int i = 0; i < r.getIngredientes().length; i++){
                if(r.getIngredientes()[i] == null){
                    break;
                } else if(r.getIngredientes()[i].equalsIgnoreCase(ingrediente)){
                    rec[index] = r.toString();
                    index++;
                }
            }
        }
        return rec;
    }

    public void getTudo(){
        System.out.print("\n\nRECEITAS: ");
        for(Receita r : receitas){
            System.out.println("\n" + r.toString());
        }
    }

    public void addTudo(){
        try{
        FileWriter writer = new FileWriter("src/files/receitas.csv");
        FileWriter backup = new FileWriter("src/files/backup.csv");
        for(Receita r : receitas){
            writer.write(r.toStringEsp());
            backup.write(r.toStringEsp());
        }
        writer.close();
        backup.close();
        } catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado!");
        } catch(IOException c){
            System.err.println(c);
        }
    }
}
