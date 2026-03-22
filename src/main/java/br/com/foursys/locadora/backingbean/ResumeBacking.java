package br.com.foursys.locadora.backingbean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.foursys.locadora.bean.Locacao;
import br.com.foursys.locadora.bean.LocacaoJogo;
import br.com.foursys.locadora.controller.LocacaoController;
import br.com.foursys.locadora.controller.LocacaoJogoController;

@ManagedBean(name = "resumeBacking")
@ViewScoped
public class ResumeBacking implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String[] MESES = { "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out",
			"Nov", "Dez" };

	private BarChartModel lucroMensalModel;
	private BarChartModel lucroAnualModel;
	private PieChartModel locacoesPorCategoriaModel;
	private boolean render;
	private int anoCorrente;

	public ResumeBacking() {
		anoCorrente = LocalDate.now().getYear();
		carregarResumo();
	}

	public BarChartModel getLucroMensalModel() {
		return lucroMensalModel;
	}

	public void setLucroMensalModel(BarChartModel lucroMensalModel) {
		this.lucroMensalModel = lucroMensalModel;
	}

	public BarChartModel getLucroAnualModel() {
		return lucroAnualModel;
	}

	public void setLucroAnualModel(BarChartModel lucroAnualModel) {
		this.lucroAnualModel = lucroAnualModel;
	}

	public PieChartModel getLocacoesPorCategoriaModel() {
		return locacoesPorCategoriaModel;
	}

	public void setLocacoesPorCategoriaModel(PieChartModel locacoesPorCategoriaModel) {
		this.locacoesPorCategoriaModel = locacoesPorCategoriaModel;
	}

	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public int getAnoCorrente() {
		return anoCorrente;
	}

	public void setAnoCorrente(int anoCorrente) {
		this.anoCorrente = anoCorrente;
	}

	private void carregarResumo() {
		List<Locacao> locacoes = new ArrayList<Locacao>(new LocacaoController().buscarTodos());
		Map<Integer, Double> lucroMensal = inicializarLucroMensal();
		Map<Integer, Double> lucroAnual = new TreeMap<Integer, Double>();
		Map<String, Number> locacoesPorCategoria = new TreeMap<String, Number>();
		LocacaoJogoController locacaoJogoController = new LocacaoJogoController();

		for (Locacao locacao : locacoes) {
			LocalDate dataLocacao = parseData(locacao.getDataLocacao());
			if (dataLocacao == null) {
				continue;
			}

			if (dataLocacao.getYear() == anoCorrente) {
				int mes = dataLocacao.getMonthValue();
				lucroMensal.put(mes, lucroMensal.get(mes) + locacao.getValor());
			}

			int ano = dataLocacao.getYear();
			double totalAno = lucroAnual.containsKey(ano) ? lucroAnual.get(ano) : 0d;
			lucroAnual.put(ano, totalAno + locacao.getValor());

			List<LocacaoJogo> itensLocacao = locacaoJogoController.buscarPorLocacao(locacao);
			for (LocacaoJogo item : itensLocacao) {
				String categoria = "Sem categoria";
				if (item.getJogoIdJogo() != null && item.getJogoIdJogo().getGenero() != null
						&& !item.getJogoIdJogo().getGenero().trim().isEmpty()) {
					categoria = item.getJogoIdJogo().getGenero();
				}

				int quantidade = locacoesPorCategoria.containsKey(categoria)
						? locacoesPorCategoria.get(categoria).intValue()
						: 0;
				locacoesPorCategoria.put(categoria, quantidade + 1);
			}
		}

		setLucroMensalModel(criarGraficoLucroMensal(lucroMensal));
		setLucroAnualModel(criarGraficoLucroAnual(lucroAnual));
		setLocacoesPorCategoriaModel(criarGraficoCategorias(locacoesPorCategoria));
		setRender(!locacoes.isEmpty());
	}

	private Map<Integer, Double> inicializarLucroMensal() {
		Map<Integer, Double> lucroMensal = new LinkedHashMap<Integer, Double>();
		for (int i = 1; i <= 12; i++) {
			lucroMensal.put(i, 0d);
		}
		return lucroMensal;
	}

	private LocalDate parseData(String data) {
		try {
			return LocalDate.parse(data, DATE_FORMATTER);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	private BarChartModel criarGraficoLucroMensal(Map<Integer, Double> lucroMensal) {
		BarChartModel model = new BarChartModel();
		ChartSeries serie = new ChartSeries();
		serie.setLabel("Lucro");

		double maiorValor = 0d;
		for (int i = 1; i <= 12; i++) {
			double valor = lucroMensal.get(i);
			serie.set(MESES[i - 1], valor);
			if (valor > maiorValor) {
				maiorValor = valor;
			}
		}

		model.addSeries(serie);
		model.setTitle("Lucros por Mes de " + anoCorrente);
		model.setLegendPosition("ne");
		model.setAnimate(true);
		model.setShadow(false);
		model.setExtender("financeBarExtender");

		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setLabel("Valor");
		yAxis.setMin(0);
		yAxis.setMax(definirMaximo(maiorValor));

		return model;
	}

	private BarChartModel criarGraficoLucroAnual(Map<Integer, Double> lucroAnual) {
		BarChartModel model = new BarChartModel();
		ChartSeries serie = new ChartSeries();
		serie.setLabel("Lucro");

		double maiorValor = 0d;
		for (Map.Entry<Integer, Double> entry : lucroAnual.entrySet()) {
			double valor = entry.getValue();
			serie.set(String.valueOf(entry.getKey()), valor);
			if (valor > maiorValor) {
				maiorValor = valor;
			}
		}

		model.addSeries(serie);
		model.setTitle("Lucros por Ano");
		model.setLegendPosition("ne");
		model.setAnimate(true);
		model.setShadow(false);
		model.setExtender("financeBarExtender");

		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setLabel("Valor");
		yAxis.setMin(0);
		yAxis.setMax(definirMaximo(maiorValor));

		return model;
	}

	private PieChartModel criarGraficoCategorias(Map<String, Number> locacoesPorCategoria) {
		PieChartModel model = new PieChartModel();
		for (Map.Entry<String, Number> entry : locacoesPorCategoria.entrySet()) {
			model.set(entry.getKey(), entry.getValue());
		}
		model.setTitle("Locacoes por Categoria de Jogos");
		model.setLegendPosition("e");
		model.setShowDataLabels(true);
		model.setDiameter(180);
		model.setShadow(false);
		return model;
	}

	private double definirMaximo(double maiorValor) {
		if (maiorValor <= 0) {
			return 10;
		}
		return Math.ceil(maiorValor * 1.15);
	}
}
