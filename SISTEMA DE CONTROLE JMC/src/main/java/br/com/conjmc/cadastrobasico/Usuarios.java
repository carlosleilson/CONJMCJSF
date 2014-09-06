package br.com.conjmc.cadastrobasico;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.Security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Enumerated;

@Configurable
@Entity
public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;
	
	/**
     */
    @NotNull
    @OneToOne
    private Funcionarios nome;

    /**
     */
    @NotNull
    @Size(min = 6)
    private String senha;

    /**
     */
    @NotNull
    @Enumerated
    private Perfil perfil;

	public Funcionarios getNome() {
        return this.nome;
    }

	public void setNome(Funcionarios nome) {
        this.nome = nome;
    }

	public String getSenha() {
        return this.senha;
    }

	public void setSenha(String senha) {
        this.senha = Security.sha256(senha);
    }

	public Perfil getPerfil() {
        return this.perfil;
    }

	public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
