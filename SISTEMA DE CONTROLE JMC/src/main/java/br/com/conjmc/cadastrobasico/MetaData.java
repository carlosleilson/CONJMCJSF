package br.com.conjmc.cadastrobasico;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class MetaData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name = "id")
    private String id;
	
	@ManyToOne
	private Usuarios usuario;
	
	private Date dataHoraAlteracao;
	
	private Long dadoId;
	
	private String modulo;
	
	@Version
    @Column(name = "version")
    private Integer version;	
	
	@PersistenceContext
    transient EntityManager entityManager;

	public final EntityManager entityManager() {
        EntityManager em = this.entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }	

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
	
	public static MetaData gravarMetadata(Usuarios usuarioLogado, Long dadoId, String moduloSistema){
		MetaData dado = new MetaData();
		dado.setUsuario(usuarioLogado);
		dado.setDataHoraAlteracao(new Date());
		dado.setDadoId(dadoId);
		dado.setModulo(moduloSistema);
		dado.persist();
		return dado;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Date getDataHoraAlteracao() {
		return dataHoraAlteracao;
	}

	public void setDataHoraAlteracao(Date dataHoraAlteracao) {
		this.dataHoraAlteracao = dataHoraAlteracao;
	}

	public Long getDadoId() {
		return dadoId;
	}

	public void setDadoId(Long dadoId) {
		this.dadoId = dadoId;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
