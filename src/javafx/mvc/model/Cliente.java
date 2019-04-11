package javafx.mvc.model;

/**
 *
 * @author Adriano Barbosa
 */
public class Cliente {

    private long id;
    private String nome;
    private String cnpj;
    private String situacao;

    public Cliente() {
        this.id = 0;
        this.situacao = "Ativo";
    }
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

}
