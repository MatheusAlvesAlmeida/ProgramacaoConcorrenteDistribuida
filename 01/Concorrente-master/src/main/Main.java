package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import utils.ExecTasks;
import utils.Task;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {
		int sum = 0;
		int qtdThreads = 10;

		for (int k = 0; k < 30; k++) {
			// Cria as instancias das classes e popula o array de caminhos
			double tempo = 0;
			ExecTasks tasks = new ExecTasks(qtdThreads);
			String[][] paths = new String[qtdThreads][10 / qtdThreads];
			String targetFolder = "assets/f2";
			Files.createDirectories(Paths.get(targetFolder));

			int j = 0;
			for (int i = 0; i < qtdThreads; i++) {
				for (int x = 0; x < 10 / qtdThreads; x++) {
					paths[i][x] = "assets/f1/" + 1 + " (" + j + ")" + ".pdf";
					j++;
				}
			}

			double tempoInicio = System.currentTimeMillis();
			// Adiciona as tasks para o array de runnables
			for (String[] path : paths) {
				tasks.add(new Task(path));
			}
			// Executa todas as tasks
			tasks.runAll();
			double tempoFim = System.currentTimeMillis();
			tempo += (tempoFim - tempoInicio);
			// Apaga todos os arquivos da pasta de destino
			for (File file : new File(targetFolder).listFiles())
				if (!file.isDirectory())
					file.delete();

			// System.out.println("Tempo de execução para " + qtdThreads + " threads: " +
			// tempo);

			sum += tempo;
		}
		System.out.println("Tempo médio total para executar " + qtdThreads + " threads: " + sum / 30 + "ms");
	}
}
