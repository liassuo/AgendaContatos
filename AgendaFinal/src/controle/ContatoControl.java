/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelos.Contato;
import persistencia.ContatoDao;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import java.util.ArrayList;
import modelos.Icrud;
/**
 *
 * @author samue_99dfzlt
 */
public class ContatoControl implements Icrud{
    ContatoDao dao = null;
    
    public ContatoControl(ContatoDao dao){
    this.dao = dao;
}
    @Override
    public void incluir(Contato contato) throws Exception{
         PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    try {
        String telefone = "+" + contato.getTelefone().getDdi() + contato.getTelefone().getDdd() + contato.getTelefone().getNumero();
        com.google.i18n.phonenumbers.Phonenumber.PhoneNumber numeroTelefone = phoneNumberUtil.parse(telefone, null);
        
        if (!phoneNumberUtil.isValidNumber(numeroTelefone)) throw new Exception("Número de telefone inválido");
        
    } catch (NumberParseException e) {
        throw new Exception("Erro ao analisar o número de telefone: " + e.getMessage());
    }
        String email = contato.getEmail();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) throw new Exception("Email inválido");
        if(contato.getNome().isEmpty()) throw new Exception("Nome nao pode estar vazio");
        dao.incluir(contato);
    }
    
     @Override
    public void excluir(String nome) throws Exception{
        if(nome.isEmpty()) throw new Exception("Nome nao pode estar vazio");
        dao.excluir(nome);
    }

  
    @Override
    public Contato consultar(String nome) throws Exception{
        if(nome.isEmpty())
        {
            throw new Exception("Nome nao pode estar vazio");
        }
        return dao.consultar(nome);
    }
    
    @Override
    public void alterar(Contato contato) throws Exception{
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    try {
        String telefone = "+" + contato.getTelefone().getDdi() + contato.getTelefone().getDdd() + contato.getTelefone().getNumero();
        com.google.i18n.phonenumbers.Phonenumber.PhoneNumber numeroTelefone = phoneNumberUtil.parse(telefone, null);
        
        if (!phoneNumberUtil.isValidNumber(numeroTelefone))throw new Exception("Número de telefone inválido");
        
    } catch (NumberParseException e) {
        throw new Exception("Erro ao analisar o número de telefone: " + e.getMessage());
    }
        String email = contato.getEmail();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        
        if (!matcher.matches()) throw new Exception("Email inválido");
        if(contato.getNome().isEmpty()) throw new Exception("Nome nao pode estar vazio");

        dao.alterar(contato);
    }
    
    @Override
    public  ArrayList<Contato> listar() throws Exception{
        return dao.listar();
    }
}
