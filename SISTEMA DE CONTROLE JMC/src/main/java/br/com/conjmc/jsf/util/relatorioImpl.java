package br.com.conjmc.jsf.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class relatorioImpl {

	/**
	 * Método que criar o relatorio.
	 * 
	 * @param List
	 *            <?> lista -- lista com os dados
	 * @param String
	 *            nomeArquivo -- nome do arquivo .jrxml, nunca jasper.
	 */
	public void gerarRelatorio(List<?> lista, String nomeArquivo)
			throws MalformedURLException {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
			this.imprimir(lista, nomeArquivo, parametros);
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Método que criar o relatorio.
	 * 
	 * @param List
	 *            <?> lista -- lista com os dados
	 * @param String
	 *            relatorio -- nome do arquivo .jrxml, nunca jasper.
	 * @param Map
	 *            <String, Object> parametros -- paramentros para criar o
	 *            relatorio.
	 */
	private void imprimir(List<?> lista, String relatorio,
			Map<String, Object> parametros) {
		OutputStream os = null;

		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance()
					.getExternalContext();
			ServletContext contextS = (ServletContext) externalContext
					.getContext();
			String reportUrlReal = contextS.getRealPath("jasperreport"
					+ File.separator + relatorio);

			JasperReport pathjrxml = JasperCompileManager
					.compileReport(reportUrlReal);

			JasperPrint jasperPrint = JasperFillManager.fillReport(pathjrxml,
					parametros, new JRBeanCollectionDataSource(lista));
			byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ relatorio.split("[.]")[0] + ".pdf\"");

			os = response.getOutputStream();
			os.write(pdf);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				FacesContext.getCurrentInstance().responseComplete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
