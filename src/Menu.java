import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private GerenciadorReceitas receitas = new GerenciadorReceitas();
    Scanner sc = new Scanner(System.in);
    public void iniciarMenu() {
        System.out.println("------Bem vindo ao aplicativo de receitas!-------");

        System.out.println("1. Adicionar uma receita");
        System.out.println("2. Procurar receita");
        System.out.println("3. Remover uma receita");
        System.out.println("4. Procurar receitas por um ingrediente");
        System.out.println("5. Ver todas as receitas");
        System.out.println("0. Sair");

        System.out.print("\nOpção: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        switch(opcao){
            case 1: 
            adicionarReceita();
            break;

            case 2:
            procurarReceita();
            break;

            case 3:
            removerReceita();
            break;

            case 4:
            procurarPorIngrediente();
            break;

            case 5:
            verReceitas();
            break;

            case 0: 
            receitas.addTudo();
            System.exit(0);
            break;

            default:
            System.out.println("Opção inválida!");
            break;
        }
    }

    public void adicionarReceita(){
        System.out.print("\nDigite o nome para a receita: ");
        String nome = sc.nextLine();

        System.out.println("\nAdicione os ingredientes da receita (Digite P para parar de adicionar)");
        String [] ingredientes = new String[20];
        String [] qtds = new String[20];
        int i = 0;
        String op = "";

        while(true){
            System.out.print("\nDigite o ingrediente (sem a quantidade): ");
            op = sc.nextLine();
            if(op.equalsIgnoreCase("P")){
                if( i == 0){
                    System.out.println("\nVocê precisa adicionar pelo menos um ingrediente! ");
                    continue;
                }
                break;
            }
            while(true){
                System.out.print("\nDigite a quantidade: ");
                qtds[i] = sc.nextLine();
                if(qtds[i] == null){
                    System.out.println("\nVocê precisa digitar uma quantidade!");
                    continue;
                } else{
                    break;
                }
            }
            

            sc.nextLine();
            ingredientes[i] = op;
            i++;
        }

        StringBuilder sb = new StringBuilder();

        System.out.println("\nAdicione os passos para o Modo de Preparo (Digite P para parar de adicionar)");
        String p = "";
        int in = 1;
        while(!p.equalsIgnoreCase("P")){
            System.out.print((in + ". "));
            p = sc.nextLine();
            if(p.equalsIgnoreCase("P")){
                if(in == 1){
                    System.out.println("\nVocê precisa adicionar pelo menos um passo!");
                    p = "";
                    continue;
                }
                break;
            }
            sb.append("\n" + in + ". "+ p);
            in++;
        }

        System.out.print("\nDigite o tempo de preparo: ");
        String tempo = sc.nextLine();

        receitas.add(new Receita(nome, ingredientes, qtds, sb.toString(), tempo));
        System.out.println("\nReceita adicionada com sucesso!");

        voltarMenu();
    }

    public void procurarReceita(){
        System.out.print("\nDigite o nome da receita: ");
        String nome = sc.nextLine();
        String result = receitas.get(nome);

        if(result.equals("")){
            System.out.println("Receita não encontrada!");
        } else{
            System.out.println(result);
        }

        System.out.println("\nGostaria de pesquisar outra receita? (S/N)");
        String sn = sc.nextLine();

        if(sn.equalsIgnoreCase("S")){
            procurarReceita();
        } else{
            voltarMenu();
        }
    }

    public void removerReceita(){
        System.out.print("Digite o nome da receita: ");
        String nome = sc.nextLine();
        System.out.println("Deseja mesmo remover a receita? (S/N)");
        String s = sc.nextLine();

        if(s.equalsIgnoreCase("S")){
            receitas.remove(nome);
            System.out.println("Receita removida com sucesso!");
        } 
        voltarMenu();
    }

    public void procurarPorIngrediente(){
        System.out.print("\nDigite um ingrediente: ");
        String ing = sc.nextLine();

        String [] ingredientes = receitas.searchIngredients(ing);

        for(String s : ingredientes){
            System.out.println(s);
        }

        System.out.println("Gostaria de procurar com outro ingrediente? (S/N)");
        String op = sc.nextLine();

        if(op.equalsIgnoreCase("S")){
            procurarPorIngrediente();
        } else {
            voltarMenu();
        }
    }

    public void verReceitas(){
        if(receitas.size() == 0){
            System.out.println("\nNão há receitas adicionadas!");
            voltarMenu();
        } else{
            receitas.getTudo();
            voltarMenu();
        }
    }

    public void voltarMenu(){
        System.out.println("\nDeseja voltar para o menu inicial? (S/N)");
        String n = sc.nextLine();

        if(n.equalsIgnoreCase("S")){
            iniciarMenu();
        } else{
            
            receitas.addTudo();
            System.exit(0);
        }
    }

    public void carregaArquivo(){
        try{
        BufferedReader reader = new BufferedReader(new FileReader("src/files/receitas.csv"));
        String in = reader.readLine();
        while(true){

            String nome = in;
            reader.readLine(); // Ingredientes

            String[] ingredientes = new String[15];
            String[] qtds = new String[15];

            int i = 0;
            in = reader.readLine();
            while(true){
                ingredientes[i] = in;
                qtds[i] = reader.readLine();
                i++;
                in = reader.readLine();
                if(in.equals("Modo de Preparo")){
                    break;
                }
            }

            StringBuilder sb = new StringBuilder();

            in = reader.readLine();
            while(true){
                sb.append("\n" + in);
                in = reader.readLine();
                if(in.equals("Tempo de Preparo")){
                    break;
                }
            }

            String tempo = reader.readLine();

            reader.readLine(); // -

            receitas.add(new Receita(nome, ingredientes, qtds, sb.toString(), tempo));

            in = reader.readLine();
            if(in == null){
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
