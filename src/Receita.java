public class Receita {
    private String nome;
    private String[] ingredientes;
    private String[] quantidadeIngredientes;
    private String modoDePreparo;
    private String tempoDePreparo;
    
    public Receita(String nome, String[] ingredientes, String[] quantidadeIngredientes, String modoDePreparo, String tempoDePreparo){
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.quantidadeIngredientes = quantidadeIngredientes;
        this.modoDePreparo = modoDePreparo;
        this.tempoDePreparo = tempoDePreparo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String[] getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String[] getQuantidadeIngredientes() {
        return quantidadeIngredientes;
    }

    public void setQuantidadeIngredientes(String[] quantidadeIngredientes) {
        this.quantidadeIngredientes = quantidadeIngredientes;
    }

    public String getModoDePreparo() {
        return modoDePreparo;
    }

    public void setModoDePreparo(String modoDePreparo) {
        this.modoDePreparo = modoDePreparo;
    }

    public String getTempoDePreparo() {
        return tempoDePreparo;
    }

    public void setTempoDePreparo(String tempoDePreparo) {
        this.tempoDePreparo = tempoDePreparo;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n"+ nome);
        sb.append("\n\nIngredientes:");
        for(int i = 0; i < ingredientes.length; i++){
            if(ingredientes[i] == null){
                break;
            }
            sb.append("\n- ");
            sb.append(String.format("%-20s %-10s", ingredientes[i], quantidadeIngredientes[i]));
            
        }
        sb.append("\n\nModo de preparo:\n" + modoDePreparo);
        sb.append("\n\nTempo de preparo: " + tempoDePreparo);
        return sb.toString();
    }

    public String toStringEsp(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < ingredientes.length; i++){
            if(ingredientes[i] == null){
                break;
            }
            sb.append("\n" + ingredientes[i] + "\n" + quantidadeIngredientes[i]);
        }
        return nome + "\nIngredientes" +   sb.toString() + "\nModo de Preparo" + modoDePreparo + "\nTempo de Preparo\n" + tempoDePreparo + "\n-\n";
    }
}
