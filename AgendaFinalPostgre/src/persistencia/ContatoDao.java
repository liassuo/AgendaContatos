/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
import controle.ContatoControl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.ConexaoPostgreSQL;
import modelos.Contato;
import modelos.Endereco;
import modelos.Icrud;
import modelos.Telefone;
import visao.TelaPrincipal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;


/**
 *
 * @author samue_99dfzlt
 */
public class ContatoDao implements Icrud{
    
  public void incluir(Contato contato) throws Exception {
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
        // Estabelecer conexão com o banco de dados
        conn = ConexaoPostgreSQL.conectar();
        
        // Verificar se a conexão foi estabelecida com sucesso
        if (conn != null) {
            // Query SQL para inserir os dados na tabela de contatos
            String sql = "INSERT INTO contatos (nome, telefone, email, logradouro, numero, complemento, cep, cidade, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            // Preparar a declaração SQL
            pstmt = conn.prepareStatement(sql);
            
            // Definir os parâmetros da declaração SQL com os dados do objeto Contato
            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getTelefone().imprimirCelular());
            pstmt.setString(3, contato.getEmail());
            pstmt.setString(4, contato.getEndereco().getLogradouro());
            pstmt.setString(5, String.valueOf(contato.getEndereco().getNumero())); // Converter para String
            pstmt.setString(6, contato.getEndereco().getComplemento());
            pstmt.setString(7, contato.getEndereco().getCep());
            pstmt.setString(8, contato.getEndereco().getCidade());
            pstmt.setString(9, contato.getEndereco().getEstado());
            
            // Executar a declaração SQL para inserir os dados na tabela
            pstmt.executeUpdate();
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao inserir no banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        // Fechar a conexão e a declaração SQL
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão com o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

    @Override
    public LinkedList<Contato> listar() throws Exception {
        LinkedList<Contato> contatos = new LinkedList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoPostgreSQL.conectar();
            String sql = "SELECT id, nome, telefone, email, logradouro, numero, complemento, cep, cidade, estado FROM contatos";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nomeCompleto = rs.getString("nome");

                String telefoneCompleto = rs.getString("telefone");
                String ddi = telefoneCompleto.substring(1, 3);
                String ddd = telefoneCompleto.substring(5, 7);
                String numero = telefoneCompleto.substring(8).replaceAll("[^0-9]", "");
                Telefone telefone = new Telefone(Integer.parseInt(ddi), Integer.parseInt(ddd), Integer.parseInt(numero));

                String email = rs.getString("email");

                String logradouro = rs.getString("logradouro");
                int numeroEndereco = rs.getInt("numero");
                String complemento = rs.getString("complemento");
                String cep = rs.getString("cep");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");

                Endereco endereco = new Endereco(logradouro, numeroEndereco, complemento, cep, cidade, estado);
                Contato contato = new Contato(nomeCompleto, telefone, email, endereco);
                contato.setId(id);
                contatos.add(contato);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao listar contatos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        contatos.sort(Comparator.comparing(Contato::getNome));
        return contatos;
}

    
    public void excluir(int id) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = ConexaoPostgreSQL.conectar();
            
            // Query SQL para excluir o contato pelo ID
            String sql = "DELETE FROM contatos WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            // Executar a declaração SQL para excluir o contato
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new Exception("Nenhum contato foi excluído. ID não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao excluir o contato: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    @Override
    public Contato consultar(String telefoneOuEmail) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoPostgreSQL.conectar(); 
            if (conn != null) {
                // Prepara a consulta SQL
                String sql = "SELECT id, nome, telefone, email, logradouro, numero, complemento, cep, cidade, estado FROM contatos WHERE telefone = ? OR email = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, telefoneOuEmail);
                pstmt.setString(2, telefoneOuEmail);

                // Executa a consulta
                rs = pstmt.executeQuery();

                // Processa o resultado
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    String logradouro = rs.getString("logradouro");
                    int numero = rs.getInt("numero");
                    String complemento = rs.getString("complemento");
                    String cep = rs.getString("cep");
                    String cidade = rs.getString("cidade");
                    String estado = rs.getString("estado");

                    // Processa o telefone
                    String ddi = telefone.substring(1, 3);
                    String ddd = telefone.substring(5, 7);
                    String numeroTel = telefone.substring(7).replaceAll("[^0-9]", "");
                    Telefone telefoneObj = new Telefone(Integer.parseInt(ddi), Integer.parseInt(ddd), Integer.parseInt(numeroTel));

                    //Cria o objeto Endereco
                    Endereco endereco = new Endereco(logradouro, numero, complemento, cep, cidade, estado);

                    //Retorna o objeto Contato
                    Contato contato = new Contato(nome, telefoneObj, email, endereco);
                    contato.setId(id); //Define o ID
                    return contato;
                } else {
                    throw new Exception("Contato não encontrado: " + telefoneOuEmail);
                }
            } else {
                throw new Exception("Falha na conexão com o banco de dados");
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao consultar no banco de dados: " + e.getMessage(), e);
        } finally {
            //Fecha os recursos
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
}

    @Override   
    public void alterar(Contato contato) throws Exception{
       Connection conn = null;
    PreparedStatement pstmt = null;
    try {
        conn = ConexaoPostgreSQL.conectar(); 
        if (conn != null) {
            String sql = "UPDATE contatos SET nome = ?, telefone = ?, email = ?, logradouro = ?, numero = ?, complemento = ?, cep = ?, cidade = ?, estado = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getTelefone().imprimirCelular());
            pstmt.setString(3, contato.getEmail());
            pstmt.setString(4, contato.getEndereco().getLogradouro());
            pstmt.setInt(5, contato.getEndereco().getNumero());
            pstmt.setString(6, contato.getEndereco().getComplemento());
            pstmt.setString(7, contato.getEndereco().getCep());
            pstmt.setString(8, contato.getEndereco().getCidade());
            pstmt.setString(9, contato.getEndereco().getEstado());
            pstmt.setInt(10, contato.getId());
            pstmt.executeUpdate();
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao alterar no banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão com o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    }
    
}
    
