package br.com.conjmc.jsf;
import br.com.conjmc.despesa.DespesasLoja;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@RooSerializable
@RooJsfManagedBean(entity = DespesasLoja.class, beanName = "despesasLojaBean")
public class DespesasLojaBean {
}
