/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import javafx.mvc.model.Cliente;
import javafx.mvc.model.Usuario;

/**
 *
 * @author Alisson H. Silva
 */
public class DaoUsuario {
        private Connection conn;

    public DaoUsuario(Connection conn) {
        this.conn = conn;
    }

    public Usuario salvar(Usuario u) throws Exception {
        long id = u.getId();
        String nome = u.getNome();
        String login = u.getLogin();
        String status = u.getStatus();
        String senha = u.getSenha();
        StringBuilder sql = new StringBuilder();
        if (id > 0) {
            sql.append("update usuario u set u.nomeUsuario = ?, ");
            sql.append("u.loginUsuario = ?, u.senhaUsuario = SHA2(?,'256'), u.statusUsuario = ? where u.idUsuario = ?");
        } else {
            sql.append("insert into usuario (nomeUsuario, loginUsuario, senhaUsuario,statusUsuario,idUsuario) ");
            sql.append("values (?,?,SHA2(?,'256'),?,?) ");
        }
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nome);
            ps.setString(2, login);
            ps.setString(3, senha);
            ps.setString(4, status);
            ps.setLong(5, id);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                u.setId(rs.getLong(1));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
        return u;
    }

    public void remover(Usuario u) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from usuario where idUsuario = ?");
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql.toString());
            ps.setLong(1, u.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public Collection<Usuario> getByCriterios(Criterios c) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from usuario ");
        sql.append(c.getCriterio());
        ArrayList<Usuario> al = new ArrayList();
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                do {
                    Usuario user = new Usuario();
                    user.setId(rs.getLong("idUsuario"));
                    user.setNome(rs.getString("nomeUsuario"));
                    user.setLogin(rs.getString("loginUsuario"));
                    user.setSenha(rs.getString("senhaUsuario"));
                    user.setStatus(rs.getString("statusUsuario"));
                    al.add(user);
                    System.out.println(user.getId());
                } while (rs.next());
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
        return al;
    }
}
