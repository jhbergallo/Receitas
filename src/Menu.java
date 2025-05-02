import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private RecipeManager recipeManager = new RecipeManager();
    private Scanner scanner = new Scanner(System.in);

    public void startMenu() {
        System.out.println("------Bem vindo ao aplicativo de receitas!-------");

        System.out.println("1. Adicionar uma receita");
        System.out.println("2. Procurar receita");
        System.out.println("3. Remover uma receita");
        System.out.println("4. Procurar receitas por um ingrediente");
        System.out.println("5. Ver todas as receitas");
        System.out.println("0. Sair");

        System.out.print("\nOpção: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch(option){
            case 1: 
            addRecipe();
            break;

            case 2:
            searchRecipeByName();
            break;

            case 3:
            removeRecipe();
            break;

            case 4:
            searchByIngredient();
            break;

            case 5:
            listAllRecipes();
            break;

            case 0: 
            recipeManager.saveAllInFile();
            scanner.close();
            System.exit(0);
            break;

            default:
            System.out.println("Opção inválida!");
            break;
        }
    }

    // Adds a recipe
    public void addRecipe(){
        // Name
        System.out.print("\nDigite o nome para a receita: ");
        String name = scanner.nextLine();

        // Ingredients
        System.out.println("\nAdicione os ingredientes da receita (Digite P para parar de adicionar)");
        HashMap<String, String> ingredients = new HashMap<>();
        String ingredient = "";
        String amount = "";

        while(true){
            // Ingredient name
            System.out.print("\nDigite o ingrediente (sem a quantidade): ");
            ingredient = scanner.nextLine();
            if(ingredient.equalsIgnoreCase("P")){
                if(ingredients.isEmpty()){
                    System.out.println("\nVocê precisa adicionar pelo menos um ingrediente! ");
                    continue;
                }
                break;
            }
            while(true){
                // Ingredient amount
                System.out.print("\nDigite a quantidade: ");
                amount = scanner.nextLine();

                if(amount.equalsIgnoreCase("P")){
                    if(ingredients.isEmpty()){
                        System.out.println("\nVocê precisa adicionar pelo menos um ingrediente!");
                        continue;
                    }
                    break;
                }

                if(amount.equals("")){
                    System.out.println("\nVocê precisa digitar uma quantidade!");
                    continue;
                } else{
                    ingredients.put(ingredient, amount);
                    break;
                }
            }
        }

        // How to prepare
        StringBuilder howToPrepare = new StringBuilder();

        System.out.println("\nAdicione os passos para o Modo de Preparo (Digite P para parar de adicionar)");
        String step = "";
        int stepNumber = 1;
        while(!step.equalsIgnoreCase("P")){
            System.out.print((stepNumber + ". "));
            step = scanner.nextLine();
            if(step.equalsIgnoreCase("P")){
                if(stepNumber == 1){
                    System.out.println("\nVocê precisa adicionar pelo menos um passo!");
                    step = "";
                    continue;
                }
                break;
            }
            howToPrepare.append("\n" + stepNumber + ". "+ step);
            stepNumber++;
        }

        // Preparation time
        System.out.print("\nDigite o tempo de preparo: ");
        String preparationTime = scanner.nextLine();

        recipeManager.add(new Recipe(name, ingredients, howToPrepare.toString(), preparationTime));
        System.out.println("\nReceita adicionada com sucesso!");

        returnToMenu();
    }

    // Searches for a recipe by name
    public void searchRecipeByName(){
        System.out.print("\nDigite o nome da receita: ");
        String name = scanner.nextLine();
        ArrayList<Recipe> foundRecipes = recipeManager.getRecipesByName(name);

        // Looks for the recipe
        if(foundRecipes.isEmpty()){
            System.out.println("Nenhuma receita encontrada com esse nome! Tente novamente.");
            searchRecipeByName();
            return;
        } else{
            foundRecipes.stream().forEach(recipe -> System.out.println(recipe.toString()));
        }

        System.out.println("\nGostaria de pesquisar outra receita? (S/N)");
        String sn = scanner.nextLine();

        // If the user wants to search again
        if(sn.equalsIgnoreCase("S")){
            searchRecipeByName();
        } else{
            // If the user doesn't want to search again
            returnToMenu();
        }
    }

    // Removes a recipe by name
    public void removeRecipe(){
        System.out.print("Digite o nome da receita: ");
        String nome = scanner.nextLine();

        if(nome.length() < 3){
            System.out.println("O nome da receita deve ter pelo menos 3 caracteres!");
            removeRecipe();
            return;
        } else if(recipeManager.getString(nome).equals("")){
            System.out.println("Receita não encontrada! Tente novamente.");
            removeRecipe();
            return;
        }

        System.out.println("\nReceita encontrada: ");
        System.out.println(recipeManager.getString(nome));
        System.out.println("\n\nVocê tem certeza que deseja remover a receita " + nome + "? (S/N)");
        
        String option = scanner.nextLine();

        if(option.equalsIgnoreCase("S")){
            recipeManager.remove(nome);
            System.out.println("Receita removida com sucesso!");
        } 
        returnToMenu();
    }

    // Shows all recipes that contain the ingredient
    public void searchByIngredient(){
        System.out.print("\nDigite um ingrediente: ");
        String ingredient = scanner.nextLine();

        ArrayList<Recipe> foundRecipes = recipeManager.searchByIngredient(ingredient);

        if(foundRecipes.isEmpty()){
            System.out.println("Nenhuma receita encontrada com esse ingrediente! Tente novamente.");
            searchByIngredient();
            return;
        }

        for(Recipe recipe : foundRecipes){
            System.out.println(recipe.toString());
        }

        System.out.println("\n\nGostaria de procurar com outro ingrediente? (S/N)");
        String option = scanner.nextLine();

        if(option.equalsIgnoreCase("S")){
            searchByIngredient();
        } else {
            returnToMenu();
        }
    }

    // Shows all recipes
    public void listAllRecipes(){
        if(recipeManager.size() == 0){
            System.out.println("\nNão há receitas adicionadas!");
            returnToMenu();
        } else{
            recipeManager.getAll();
            returnToMenu();
        }
    }

    // Returns to the main menu
    public void returnToMenu(){
        System.out.println("\nDeseja voltar para o menu inicial? (S/N)");
        String option = scanner.nextLine();

        if(option.equalsIgnoreCase("S")){
            startMenu();
        } else{
            
            recipeManager.saveAllInFile();
            System.exit(0);
        }
    }

    // Loads the recipes from the file
    public void loadFile(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/files/recipes.csv"));
            String line = reader.readLine();
            while(true){
                HashMap<String, String> ingredients = new HashMap<>();
                String name = line;
                reader.readLine(); // Ingredients

                String currentIngredient = "";
                String currentAmount = "";

                line = reader.readLine();
                while(true){
                    currentIngredient = line;
                    currentAmount = reader.readLine();
                    ingredients.put(currentIngredient, currentAmount);
                    line = reader.readLine();
                    if(line.equals("Modo de Preparo")){
                        break;
                    }
                }

                StringBuilder howToPrepare = new StringBuilder();

                line = reader.readLine();
                while(true){
                    howToPrepare.append("\n" + line);
                    line = reader.readLine();
                    if(line.equals("Tempo de Preparo")){
                        break;
                    }
                }

                String preparationTime = reader.readLine();

                // Read the separator
                reader.readLine(); 

                recipeManager.add(new Recipe(name, ingredients, howToPrepare.toString(), preparationTime));

                // Read the next line
                line = reader.readLine();
                if(line == null){
                    break;
                }
            }
            reader.close();
        } catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado!");
        } catch(IOException c){
            System.err.println(c);
        }
    }
}
