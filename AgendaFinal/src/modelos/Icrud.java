/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos;

import java.util.ArrayList;

/**
 *
 * @author samue_99dfzlt
 */
public interface Icrud {
    void incluir(Contato objeto) throws Exception;
    void excluir (String nome) throws Exception;
    void alterar (Contato objeto) throws Exception;
    Contato consultar (String nomeCompleto) throws Exception;
    ArrayList<Contato> listar() throws Exception;
}
