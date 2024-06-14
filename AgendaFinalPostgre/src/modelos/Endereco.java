/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

public class Endereco {
    //Atributos
    private String logradouro = "";
    private int numero = 0;
    private String complemento = "";
    private String cep = "";
    private String cidade = "";
    private String estado = "";
    
    //metodos
    
    //construtor padrão
    public Endereco(){

    }
    
    //construtor
    public Endereco(String logradouro, int numero, String complemento, String cep, String cidade,String estado) throws Exception{
        if(logradouro.isEmpty()) throw new Exception("O logradouro nao pode ser vazio!");
        this.logradouro = logradouro;
        if(numero<0) throw new Exception("O numero nao pode ser menor que zero!");
        this.numero = numero;
        this.complemento = complemento;
        if (cep.trim().equals("-") || cep.trim().matches("^-+$")) 
        {
            throw new Exception("O CEP nao pode estar vazio");
        }
        this.cep = cep;
        if(cidade.isEmpty()) throw new Exception("O campo de cidade nao pode ser vazio!");
        this.cidade = cidade;
        if(estado.isEmpty()) throw new Exception("O campo de estado nao pode ser vazio!");
        this.estado = estado;
    }
    
    //Getters e setters
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) throws Exception {
        if(logradouro.isEmpty()) throw new Exception("O logradouro nao pode ser vazio!");
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) throws Exception {
        if(numero<0) throw new Exception("O numero nao pode ser menor que zero!");
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) throws Exception {
        if(complemento.isEmpty()) throw new Exception("O Complemento nao pode ser vazio!");
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) throws Exception {
    if (cep == null || cep.trim().isEmpty()) {
        throw new Exception("O CEP não pode ser vazio!");
    } else if (cep.trim().equals("-") || cep.trim().matches("^-+$")) {
        throw new Exception("Formato de CEP inválido!");
    }
    this.cep = cep;
}

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) throws Exception{
        if(cidade.isEmpty()) throw new Exception("O campo de cidade nao pode ser vazio!");
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) throws Exception {
        if(estado.isEmpty()) throw new Exception("O campo de estado nao pode ser vazio!");
        this.estado = estado;
    }
     
    //Imprimir o endereço
    public String imprimirEndereco() {
      return logradouro + "/ " + numero + "/ " + complemento + "/ " + cep + "/ " + cidade + "/ "+ estado;
    }
  }
