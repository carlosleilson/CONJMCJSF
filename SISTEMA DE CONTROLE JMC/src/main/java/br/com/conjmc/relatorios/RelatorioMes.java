package br.com.conjmc.relatorios;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.regexp.internal.RESyntaxException;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.relatorioImpl;
import br.com.conjmc.relatorios.relatoriodiadodes.RelatorioDoMes;

@ManagedBean(name = "relatorioMesBean")
@ViewScoped
public class RelatorioMes extends relatorioImpl {
	private List<ResumoVO> resumos;
	private List<Sangria> allSangrias;
	private String dataLabel;
	private SimpleDateFormat sdf;
	private int mesTemp;
	private Date dataTemp;
	private Integer ultimoDiaDoMes;

	@PostConstruct
	public void init() {
		sdf = new SimpleDateFormat("MM/yyyy");
		relatorio();
	}

	public String relatorio() {
		Calendar c = Calendar.getInstance();
		setDataTemp(c.getTime());
		dataLabel = sdf.format(getDataTemp()).toString();
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		this.resumos = new RelatorioDoMes(getDataTemp()).criarRelatorio();
		if (mesTemp == 0)
			mesTemp = getDataTemp().getMonth();
		return null;
	}

	public void anterior() {
		Calendar c = Calendar.getInstance();
		getDataTemp().setMonth(mesTemp - 1);
		dataLabel = sdf.format(getDataTemp()).toString();
		this.resumos = new RelatorioDoMes(getDataTemp()).criarRelatorio();
		mesTemp = getDataTemp().getMonth();
		c.setTime(getDataTemp());
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public void proximo() {
		Calendar c = Calendar.getInstance();
		getDataTemp().setMonth(mesTemp + 1);
		dataLabel = sdf.format(getDataTemp()).toString();
		this.resumos = new RelatorioDoMes(getDataTemp()).criarRelatorio();
		mesTemp = getDataTemp().getMonth();
		c.setTime(getDataTemp());
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public void gerarRelatorio() throws MalformedURLException {
		List<DrmVO> drmTmpList = new ArrayList<DrmVO>();
		for (ResumoVO resTmp : this.resumos) {
			DrmVO drmTmp = new DrmVO();
			drmTmp.setName(resTmp.getName().toString());
			drmTmp.setTitulo(resTmp.getTitulo().toString());
			drmTmp.setPorcentagem(resTmp.getPorcentagem().toString());
			drmTmp.setValorTemp(resTmp.getValorTemp().toString());
			drmTmpList.add(drmTmp);
			for (ClassificacaoVO itenTmp : resTmp.getClassificacoes()) {
				DrmVO drmTmp2 = new DrmVO();
				drmTmp2.setName(itenTmp.getCodigo().toString());
				drmTmp2.setTitulo(itenTmp.getName().toString());
				drmTmp2.setPorcentagem(itenTmp.getItens().get(0).getCampos()[itenTmp
						.getItens().get(0).getCampos().length - 2].toString());
				drmTmp2.setValorTemp(itenTmp.getItens().get(0).getCampos()[itenTmp
						.getItens().get(0).getCampos().length - 1].toString());
				drmTmpList.add(drmTmp2);
			}
		}
		this.gerarRelatorio(drmTmpList, "drm.jrxml");
	}

	public List<Sangria> getAllSangrias() {
		return allSangrias;
	}

	public void setAllSangrias(List<Sangria> allSangrias) {
		this.allSangrias = allSangrias;
	}

	public String getDataLabel() {
		return dataLabel;
	}

	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}

	public List<ResumoVO> getResumos() {
		return resumos;
	}

	public void setResumos(List<ResumoVO> resumos) {
		this.resumos = resumos;
	}

	public Integer getUltimoDiaDoMes() {
		return ultimoDiaDoMes;
	}

	public void setUltimoDiaDoMes(Integer ultimoDiaDoMes) {
		this.ultimoDiaDoMes = ultimoDiaDoMes;
	}

	public Date getDataTemp() {
		return dataTemp;
	}

	public void setDataTemp(Date dataTemp) {
		this.dataTemp = dataTemp;
	}
}
