/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author samue_99dfzlt
 */
public class Contato {
    private int id = 0;
    private String nome = "";
    private Telefone telefone = null;
    private Endereco endereco= null;
    private String email = "";
    
    //Métodos
    
    //Construtor Padrão
    public Contato() {
    }
  
    //Construtor
    public Contato(String nome, Telefone telefone, String email,Endereco endereco) throws Exception {
        if(nome.isEmpty()) throw new Exception("O nome não pode ser vazio!");
        this.nome = nome;
        if(telefone == null) throw new Exception("O telefone nao pode ser vazio!");
        this.telefone = telefone;
        if(email.isEmpty()) throw new Exception("O e-mail nao pode ser vazio!");
        this.email = email;
        if(endereco == null)throw new Exception("O endereco nao pode ser vazio!");
        this.endereco = endereco;
    }

    //Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if(nome.isEmpty()) throw new Exception("O nome não pode ser vazio!");
        this.nome = nome;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        if(id<0) throw new Exception ("id nao pode ser menor que zero");
        this.id = id;
    }
    
    public void setTelefone(Telefone telefone) throws Exception {
       if(telefone == null) throw new Exception("O telefone nao pode ser vazio!");
       this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) throws Exception {
       if(endereco == null)throw new Exception("O endereco nao pode ser vazio!");
       this.endereco = endereco;
    }

    public void setEmail(String email) throws Exception {
        if(email.isEmpty()) throw new Exception("O e-mail nao pode ser vazio!");
        this.email = email;
    }

    @Override
    public String toString() {
      return  nome + " " + telefone.imprimirCelular()+" "+  email + " "+ endereco.imprimirEndereco();
    }

}
