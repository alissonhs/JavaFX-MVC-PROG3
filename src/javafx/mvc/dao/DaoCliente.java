package javafx.mvc.dao;

import javafx.mvc.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Adriano Barbosa
 */
public class DaoCliente {

    private Connection conn;

    public DaoCliente(Connection conn) {
        this.conn = conn;
    }

    public Cliente salvar(Cliente c) throws Exception {
        long id = c.getId();
        String nome = c.getNome();
        String cnpj = c.getCnpj();
        String situacao = c.getSituacao();
        StringBuilder sql = new StringBuilder();
        if (id > 0) {
            sql.append("update cliente c set c.nome = ?, ");
            sql.append("c.cnpj = ?, c.situacao = ? where c.idcliente = ?");
        } else {
            sql.append("insert into cliente (nome, cnpj, situacao,idcliente) ");
            sql.append("values (?,?,?,?) ");
        }
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nome);
            ps.setString(2, cnpj);
            ps.setString(3, situacao);
            ps.setLong(4, id);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setId(rs.getLong(1));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
        return c;
    }

    public void remover(Cliente c) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from cliente where idcliente = ?");
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql.toString());
            ps.setLong(1, c.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public Collection<Cliente> getByCriterios(Criterios c) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from cliente ");
        sql.append(c.getCriterio());
        ArrayList<Cliente> al = new ArrayList();
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                do {
                    Cliente cl = new Cliente();
                    cl.setId(rs.getLong("idcliente"));
                    cl.setNome(rs.getString("nome"));
                    cl.setCnpj(rs.getString("cnpj"));
                    cl.setSituacao(rs.getString("situacao"));
                    al.add(cl);
                } while (rs.next());
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
        return al;
    }

}
