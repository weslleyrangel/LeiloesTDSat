/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listaProdutos = new ArrayList<>();
    ArrayList<ProdutosDTO> listaProdutosVendidos = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
    // Validações adicionais de segurança
    if (produto == null) {
        JOptionPane.showMessageDialog(null, "Produto não pode ser nulo", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String sql = "INSERT INTO produtos (nome, valor, status) values (?,?,?)";
    Connection conn = null;
    PreparedStatement prep = null;
    
    try {
        conn = new conectaDAO().connectDB();
        prep = conn.prepareStatement(sql);
        prep.setString(1, produto.getNome());
        prep.setDouble(2, produto.getValor());
        prep.setString(3, produto.getStatus());

        prep.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "Produto inserido com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    } finally {
        // Fechar recursos
        try {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.err.println("Erro ao fechar conexão: " + ex.getMessage());
        }
    }
}

    public ArrayList<ProdutosDTO> listarProdutos() {

        String sql = "SELECT * FROM produtos;";

        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listaProdutos.add(produto);
            }

            return listaProdutos;

        } catch (SQLException e) {
            return null;
        }
    }

    public void venderProduto(int id) {

        String sql = "UPDATE produtos SET status = ? where id = ?";

        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, id);

            prep.executeUpdate();
            prep.close();

            JOptionPane.showMessageDialog(null, "Produto vendido!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na ProdutosDAO " + e.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {

        try {

            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement("SELECT * FROM produtos WHERE status=?");

            prep.setString(1, "Vendido");

            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listaProdutosVendidos.add(produto);

            }

            return listaProdutosVendidos;

        } catch (SQLException e) {

            return null;

        }
    }
}
