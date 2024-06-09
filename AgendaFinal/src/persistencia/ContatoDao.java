/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.Contato;
import modelos.Endereco;
import modelos.Icrud;
import modelos.Telefone;
import visao.TelaPrincipal;

/**
 *
 * @author samue_99dfzlt
 */
public class ContatoDao implements Icrud{
    
    @Override
    public void incluir(Contato contato) throws Exception{
    FileWriter arq = null;
    try {
        String nomeArquivo = "arquivo.csv";
        String caminhoRelativo = "src/arquivo/" + nomeArquivo;
        arq = new FileWriter(caminhoRelativo, true);

        
        String registro = contato.getNome() +  "," + contato.getTelefone().imprimirCelular()+ "," + contato.getEmail() + "," + contato.getEndereco().imprimirEndereco() +  "\n";
        arq.write(registro);

    } catch (IOException erro) {
        JOptionPane.showMessageDialog(null, "Erro ao escrever no arquivo: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, erro);
    } finally {
        try {
            if (arq != null) {
                arq.close();
            }
        } catch (IOException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar o arquivo: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, erro);
        }
    }
    } 
   
    @Override
public ArrayList<Contato> listar() throws Exception {
    ArrayList<Contato> contatos = new ArrayList<>();
    String nomeArquivo = "arquivo.csv";
    String caminhoRelativo = "src/arquivo/" + nomeArquivo;
    try (BufferedReader br = new BufferedReader(new FileReader(caminhoRelativo))) {
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(",");

            // Verifique se há o número correto de campos
            if (campos.length != 4) {
                throw new Exception("Formato de linha inválido no arquivo: " + linha);
            }

            String nomeCompleto = campos[0].trim();
            String telefonePartes = campos[1].trim();
            String ddi = telefonePartes.substring(1, 3);
            String ddd = telefonePartes.substring(5, 7);
            String numero = telefonePartes.substring(8).replaceAll("[^0-9]", "");
            Telefone telefone = new Telefone(Integer.parseInt(ddi), Integer.parseInt(ddd), Integer.parseInt(numero));

            String email = campos[2].trim();

            String enderecoCompleto = campos[3].trim();
            String[] enderecoPartes = enderecoCompleto.split("/");

            
            if (enderecoPartes.length != 6) {
                throw new Exception("Formato de endereço inválido para o contato: " + nomeCompleto);
            }

            String logradouro = enderecoPartes[0].trim();
            int numeroEndereco = Integer.parseInt(enderecoPartes[1].trim());
            String complemento = enderecoPartes[2].trim();
            String cep = enderecoPartes[3].trim();
            String cidade = enderecoPartes[4].trim();
            String estado = enderecoPartes[5].trim();

            Endereco endereco = new Endereco(logradouro, numeroEndereco, complemento, cep, cidade, estado);
            Contato contato = new Contato(nomeCompleto, telefone, email, endereco);
            contatos.add(contato);
        }
    }
    contatos.sort(Comparator.comparing(Contato::getNome));
    return contatos;
}

      
    
    
    @Override
    public void excluir(String nomeCompleto) throws Exception {
        String nomeArquivo = "arquivo.csv";
        String caminhoRelativo = "src/arquivo/" + nomeArquivo;
        File arquivo = new File(caminhoRelativo);
        String novoArquivoNome = "novoArquivo.csv";
        String novoCaminhoRelativo = "src/arquivo/" + novoArquivoNome;
        File novoArquivo = new File(novoCaminhoRelativo);

        FileWriter writer;
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoRelativo))) {
            writer = new FileWriter(novoArquivo);
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] words = linha.split(",");
                if (!words[0].equals(nomeCompleto)) {
                    writer.write(linha + System.lineSeparator());
                }
            }
        }
        writer.close();
        if (arquivo.delete()) {
            if (!novoArquivo.renameTo(arquivo)) {
                throw new Exception(" Erro ao renomear  o arquivo");
            }
        } else {
            throw new Exception(" Erro ao deletar o arquivo");
        }
    }
    
    @Override
    public Contato consultar(String nomeCompleto) throws FileNotFoundException, Exception {
        String linha = "";
        String nomeArquivo = "arquivo.csv";
        String caminhoRelativo = "src/arquivo/" + nomeArquivo;
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoRelativo))) {
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos[0].equalsIgnoreCase(nomeCompleto)) {
                    String telefonePartes = campos[1].trim();
                    String ddi = telefonePartes.substring(1, 3);
                    String ddd = telefonePartes.substring(5, 7);
                    String numero = telefonePartes.substring(7).replaceAll("[^0-9]", "");
                    Telefone telefone = new Telefone(Integer.parseInt(ddi), Integer.parseInt(ddd), Integer.parseInt(numero));

                    
                    String enderecoCompleto = campos[3].trim();
                    String[] enderecoPartes = enderecoCompleto.split("/");

                   
                    if (enderecoPartes.length < 6) {
                        throw new Exception("Formato de endereço inválido para o contato: " + nomeCompleto);
                    }

                    String logradouro = enderecoPartes[0].trim();
                    int numeroEndereco = Integer.parseInt(enderecoPartes[1].trim());
                    String complemento = enderecoPartes[2].trim();
                    String cep = enderecoPartes[3].trim();
                    String cidade = enderecoPartes[4].trim();
                    String estado = enderecoPartes[5].trim();

                    Endereco endereco = new Endereco(logradouro, numeroEndereco, complemento, cep, cidade, estado);

                    return new Contato(campos[0].trim(), telefone, campos[2].trim(), endereco);
                }
            }
        throw new FileNotFoundException("Contato não encontrado: " + nomeCompleto);
        }
    }
    

    @Override   
    public void alterar(Contato contato) throws Exception{
        ContatoDao contatoTemporario = new ContatoDao();
        contatoTemporario.excluir(contato.getNome());
        contatoTemporario.incluir(contato);
        
    }
    
}
    
