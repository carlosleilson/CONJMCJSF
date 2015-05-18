package br.com.conjmc.jsf;

import java.awt.Toolkit;
import java.util.Timer;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.ContasFuncionario;
import br.com.conjmc.cadastrobasico.ControleValoresPendentes;
import br.com.conjmc.cadastrobasico.Fechamento;
import br.com.conjmc.cadastrobasico.ItemFaturamento;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class FechamentoBean {

	private Fechamento fechamento;
	private Double totalCaixaInical;
	private ControleValoresPendentes controle;
	private double saldo;
	Toolkit toolkit;
    Timer timer;
	
	@PostConstruct
	public void init() {
		fechamento = new Fechamento();
		controle = new ControleValoresPendentes();
		calcularTotal();
	}
	
	public String persist() {
		long validar = new Fechamento().validarfechamento(fechamento.getData(), fechamento.getTurno());
		if(validar == 0) {
			fechamento.setLoja(ObejctSession.loja());
			fechamento.setUsuarios(ObejctSession.getUsuarioLogado());
			fechamento.persist();
			String message = "message_successfully_created";        
			FacesMessage facesMessage = MessageFactory.getMessage(message, "Fechamento");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			init();			
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O fechamento não pode ser incluindo porque já existe fechamento com a mesma data e período", "O fechamento não pode ser incluindo porque já existe fechamento com a mesma data e período"));
		}
        return "fechamento.xhtml";
    }
	
	public void calcularTotalCaixaInicial() {
		this.totalCaixaInical= 0.0;
		this.totalCaixaInical += this.fechamento.getCaixaInicial();
		this.totalCaixaInical += this.fechamento.getTrocadoDinheiro();
		this.totalCaixaInical += this.fechamento.getTrocadoMoeda();
	} 
	
	public void calcularSaldo (){
		this.saldo = 0.0;
		this.saldo += fechamento.getDinheiro();
		this.saldo += fechamento.getMoeda();
		this.saldo += fechamento.getTrocado();
		
	}
	
	
	public void calcularTotal() {
		calcularSaldo();
		calcularTotalCaixaInicial();
		double totalDiferenca = new ItemFaturamento().valorTotal(fechamento.getData(), fechamento.getData(), fechamento.getTurno(), "");
		totalDiferenca = totalDiferenca - (totalDiferenca * 2);
		double total = 0;
		total += fechamento.getSangriaCaixa();
		total += fechamento.getSangriaGastos();
		total += fechamento.getCartao();
		total += fechamento.getDinheiro();
		total += fechamento.getMoeda();
		total += fechamento.getTrocado();
		total += fechamento.getCheque();
		total += fechamento.getReceber();
		total += fechamento.getCobrar();
		total += fechamento.getWeb();
		total += fechamento.getCaixaFinal();
		total -= totalCaixaInical;
		fechamento.setTotalFechamento(total);
		fechamento.setDiferenca(totalDiferenca + total);
	}
	
	public void calcularContas(){
		totalContasCobrar();
		totalContasReceber();
		totalDespespas();
		totalSangria();
		calcularTotal();
	}
	
	public void totalSangria() {
		/*calcularContas();
        FooRunnable r = new FooRunnable();
        Thread t = new Thread(r);
        t.start();*/
				
	}

	private void totalContasReceber(){
		fechamento.setReceber(new ControleValoresPendentes().TotalContasPendentes(fechamento.getData(), fechamento.getTurno(), 1));
	}
	
	private void totalContasCobrar(){
		fechamento.setCobrar(new ControleValoresPendentes().TotalContasPendentes(fechamento.getData(), fechamento.getTurno(), 0));
	}
	
	private void totalDespespas() {
		double sangriaGastos = 0;
		sangriaGastos += new Sangria().TotalDespesa(fechamento.getData(), fechamento.getTurno(), "SANGRIA CAIXA", true);
		sangriaGastos += new ContasFuncionario().TotalDespesa(fechamento.getData(), fechamento.getTurno(), "SANGRIA CAIXA");
		fechamento.setSangriaGastos(sangriaGastos);
		//fechamento.setSangriaCaixa(new Sangria().TotalDespesa(fechamento.getData(), fechamento.getTurno(), "SANGRIA CAIXA", false));
		
	}
	
	public void inserirValoresPendentes() {
        String message = "";
        if (controle.getId() != null) {
        	controle.merge();
        	message = "message_successfully_created";
        	FacesMessage facesMessage = MessageFactory.getMessage(message, "ControleValoresPendentes");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } else {
    		controle.setData(fechamento.getData());
    		controle.setTurno(fechamento.getTurno());
    		if(controle.getData() != null && controle.getTurno() != null) {
    			controle.setLoja(ObejctSession.loja());
            	if(new ControleValoresPendentes().validarValoresPendentes(controle.getData(), controle.getTurno(), controle.getNumeroPedido()) == 0) {
            		controle.setBaixado(false);
            		controle.persist();
            		message = "message_successfully_created";
            		FacesMessage facesMessage = MessageFactory.getMessage(message, "ControleValoresPendentes");
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            	} else {
            		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi poissivel cadastrar o item porque ele já esta cadastrado", "Não foi poissivel cadastrar o item porque ele já esta cadastrado"));
            		message = "";
            	}
    		} else {
    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, informar a data e o turno", "Por favor, informar a data e o turno"));
        		message = "";

    		}
        }
        controle = new ControleValoresPendentes();
        calcularContas();
        /*return "controleValores.xhtml";*/
    }

	//Getters and Setters
	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}

	public Double getTotalCaixaInical() {
		return totalCaixaInical;
	}

	public void setTotalCaixaInical(Double totalCaixaInical) {
		this.totalCaixaInical = totalCaixaInical;
	}

	public ControleValoresPendentes getControle() {
		return controle;
	}

	public void setControle(ControleValoresPendentes controle) {
		this.controle = controle;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	/*class FooRunnable implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i <6; i++) {
				try {
					Thread.sleep(10000);
					System.out.println("**********************************************************************************************************************************");
					calcularContas();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}*/
}

