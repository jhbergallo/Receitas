import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Recipe {
    private String name;
    private HashMap <String,String> ingredients;
    private String howToPrepare;
    private String preparationTime;
    
    public Recipe(String name, HashMap<String,String> ingredients, String howToPrepare, String preparationTime){
        this.name = name;
        this.ingredients = ingredients;
        this.howToPrepare = howToPrepare;
        this.preparationTime = preparationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients.keySet().stream().collect(Collectors.toCollection(ArrayList::new));
    }

    public String getHowToPrepare() {
        return howToPrepare;
    }

    public void setModoDePreparo(String howToPrepare) {
        this.howToPrepare = howToPrepare;
    }

    public String getPreparationTime() {
        return preparationTime;    
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n"+ name);
        sb.append("\n\nIngredientes:");
        for(String ingredient : ingredients.keySet()){
            sb.append("\n- ");
            sb.append(String.format("%-20s %-10s", ingredient, ingredients.get(ingredient)));
        }
        sb.append("\n\nModo de preparo:\n" + howToPrepare);
        sb.append("\n\nTempo de preparo: " + preparationTime);
        return sb.toString();
    }

    public String toStringForFile(){
        StringBuilder sb = new StringBuilder();
        for(String ingredient : ingredients.keySet()){
            sb.append("\n"+ ingredient);
            sb.append("\n" + ingredients.get(ingredient));
        }
        return name + "\nIngredientes" +   sb.toString() + "\nModo de Preparo" + howToPrepare + "\nTempo de Preparo\n" + preparationTime + "\n;\n";
    }
}
