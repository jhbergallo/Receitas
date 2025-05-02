import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RecipeManager {
    private ArrayList<Recipe> recipes;

    public RecipeManager(){
        this.recipes = new ArrayList<>();
    }
    
    public void add(Recipe recipe){
        recipes.add(recipe);
    }

    public int size(){
        return recipes.size();
    }

    public boolean remove(String name){
        Recipe recipe = searchName(name);
        if(recipe == null){
            // Recipe is null
            return false;
        } else if(recipe.getName().equalsIgnoreCase(name)){
            // Recipe not found
            return false;
        } else {
            recipes.remove(recipe);
            return true;
        }
    }

    public String getString(String name){
        Recipe recipe = searchName(name);
        if(recipe == null){
            return "";
        } else{
            return recipe.toString();
        }
    }

    public ArrayList<Recipe> getRecipesByName(String name){
        return searchAllByName(name);
    }

    public ArrayList<Recipe> getRecipes(){
        return recipes;
    }

    private Recipe searchName(String receivedName){
        for(Recipe recipe : recipes){
            String savedName = recipe.getName().toLowerCase();

            if(savedName.startsWith(receivedName.toLowerCase())){
                return recipe;
            }
        }
        return null;
    }

    private ArrayList<Recipe> searchAllByName(String name){
        ArrayList<Recipe> foundRecipes = new ArrayList<>();

        for(Recipe recipe : recipes){
            String savedName = recipe.getName().toLowerCase();
            if(savedName.startsWith(name.toLowerCase())){
                foundRecipes.add(recipe);
            }
        }
        return foundRecipes;
    }

    public ArrayList<Recipe> searchByIngredient(String ingredient){
        ArrayList<Recipe> foundRecipes = new ArrayList<>();

        for(Recipe recipe : recipes){
            ArrayList<String> ingredients = recipe.getIngredients();
            for(String ingredientInRecipe : ingredients){
                if(ingredientInRecipe.toLowerCase().contains(ingredient.toLowerCase())){
                    foundRecipes.add(recipe);
                    break;
                }
            }
        }
        return foundRecipes;
    }

    public void getAll(){
        System.out.print("\n\nReceitas: ");
        for(Recipe recipes : recipes){
            System.out.println("\n" + recipes.toString());
        }
    }

    public void saveAllInFile(){
        try{
        FileWriter writer = new FileWriter("src/files/recipes.csv");
        FileWriter backup = new FileWriter("src/files/backup.csv");
        for(Recipe r : recipes){
            writer.write(r.toStringForFile());
            backup.write(r.toStringForFile());
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
