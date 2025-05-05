package subredes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IPv4GUI extends JFrame {
	private final JTextField[] octetos = new JTextField[4];
	private final JComboBox<Integer> cidrBox = new JComboBox<>();
	private final JLabel resultadoIP = new JLabel();
	private final JLabel resultadoClasse = new JLabel();
	private final JLabel resultadoMascaraDecimal = new JLabel();
	private final JLabel resultadoMascaraBinario = new JLabel();
	private final JLabel resultadoHosts = new JLabel();
	private final JLabel resultadoRedes = new JLabel();
	private final JLabel tituloErro = new JLabel();

	public IPv4GUI() {
		setTitle("Calculadora IPv4");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// IP Octetos
		for (int i = 0; i < 4; i++) {
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			gbc.anchor = GridBagConstraints.WEST; // <<< Aqui está o segredo
			add(new JLabel("Octeto " + (i + 1) + ":"), gbc);


			octetos[i] = new JTextField();
			gbc.gridx = 1;
			gbc.weightx = 1.0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			add(octetos[i], gbc);
		}

		// CIDR
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(new JLabel("CIDR:"), gbc);

		for (int i = 8; i <= 32; i++)
			cidrBox.addItem(i);
		cidrBox.setSelectedItem(8);
		gbc.gridx = 1;
		add(cidrBox, gbc);

		// Botão Calcular
		JButton calcular = new JButton("Calcular");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		add(calcular, gbc);
		calcular.addActionListener(e -> calcularResultado());

		// Resultados
		String[] labels = { "IP:", "Classe:", "Máscara Decimal:", "Máscara Binária:", "Nº Hosts:", "Nº Sub-redes:" };
		JLabel[] outputs = { resultadoIP, resultadoClasse, resultadoMascaraDecimal, resultadoMascaraBinario,
				resultadoHosts, resultadoRedes };

		for (int i = 0; i < labels.length; i++) {
			gbc.gridy = 6 + i;
			gbc.gridx = 0;
			gbc.gridwidth = 1;
			add(new JLabel(labels[i]), gbc);
			gbc.gridx = 1;
			add(outputs[i], gbc);
		}

		// Título de erro
		gbc.gridy = 12;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		tituloErro.setForeground(Color.RED);
		add(tituloErro, gbc);

		setSize(450, 400); // Define tamanho fixo inicial
		setLocationRelativeTo(null); // Centraliza na tela
		setVisible(true);

	}

	private void calcularResultado() {
		try {
			int[] ip = new int[4];
			for (int i = 0; i < 4; i++) {
				ip[i] = Integer.parseInt(octetos[i].getText().trim());
				if (ip[i] < 0 || ip[i] > 255)
					throw new NumberFormatException();
			}

			int cidr = (int) cidrBox.getSelectedItem();
			String classe = IPv4Calculator.identificarClasse(ip[0]);

			if (classe.equals("Fora do Range")) {
				tituloErro.setText("Classe de IP inválida.");
				limparResultados();
				return;
			}

			int bitsClasse = IPv4Calculator.getBitsClasse(classe);
			resultadoIP.setText(ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3]);
			resultadoClasse.setText(classe);
			resultadoMascaraDecimal.setText(IPv4Calculator.calcularMascaraDecimal(cidr));
			resultadoMascaraBinario.setText(IPv4Calculator.calcularMascaraBinaria(cidr));
			resultadoHosts.setText(String.valueOf(IPv4Calculator.calcularHosts(cidr)));
			resultadoRedes.setText(String.valueOf(IPv4Calculator.calcularSubRedes(cidr, bitsClasse)));
			tituloErro.setText("");

		} catch (NumberFormatException ex) {
			tituloErro.setText("Insira valores válidos entre 0 e 255 para os octetos.");
			limparResultados();
		}
	}

	private void limparResultados() {
		resultadoIP.setText("");
		resultadoClasse.setText("");
		resultadoMascaraDecimal.setText("");
		resultadoMascaraBinario.setText("");
		resultadoHosts.setText("");
		resultadoRedes.setText("");
	}
}
