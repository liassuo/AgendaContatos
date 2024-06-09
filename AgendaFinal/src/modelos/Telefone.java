/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author liass
 */
public class Telefone {
    //Atributos
    private int ddi = 0;
    private int ddd = 0;
    private int numero = 0;  
  
    //Métodos
    
    //Construtor padrão
    public Telefone(){

    }
  
    //Construtor
    public Telefone(int ddi, int ddd, int numero) {
        if (ddi < 0 || ddi > 999) throw new IllegalArgumentException("DDI inválido");
        this.ddi=ddi;
        if (ddd < 0 || ddd > 999) throw new IllegalArgumentException("DDD inválido");
        this.ddd=ddd;
        if (numero < 0 || numero > 999999999) throw new IllegalArgumentException("Número de telefone inválido");
        this.numero=numero;
    }
    
    //Getters e Setters
    public int getDdi() {
        return ddi;
    }

    public void setDdi(int ddi) throws Exception {
        if(ddi<0)throw new Exception("O DDI não pode ser menor que zero!");
        this.ddi = ddi;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) throws Exception {
        if(ddd<0) throw new Exception("O DDD nao pode ser menor que zero!");
        this.ddd = ddd;
    }

    public int getNumero() {
        return numero;
    }
    
    public void setNumero(int numero) throws Exception {
        if(numero<0) throw new Exception("O numero não pode ser menor que zero!");
        this.numero = numero;
    }

    //Metodo para imprimir o celular
    public String imprimirCelular() {
        return "+" + ddi + " (" + ddd +") "+ numero ;
    }
}
