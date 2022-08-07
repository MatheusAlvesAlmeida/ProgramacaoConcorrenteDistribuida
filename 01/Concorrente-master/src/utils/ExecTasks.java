package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecTasks {
	// Cria uma coleção de runnables pra executar várias threads
	int qtdThreads;

	public ExecTasks(int qtdThreads) {
		this.qtdThreads = qtdThreads;
	}

	private final Collection<Runnable> tasks = new ArrayList<Runnable>();

	// Adiciona as tasks para o array de runnables
	public void add(final Runnable task) {
		tasks.add(task);
	}

	// Executa todas as tasks
	public void runAll() throws InterruptedException {
		/*
		 * Executor service é uma abstração que permite o uso de threads sem a
		 * necessidade de sincrinização manual. Thread pool é um reservatório de threads
		 * e ele vai criar threads até que atinga o limite passado como parâmetro à
		 * medida
		 * que forem submetidas as threads.
		 */

		// Instancia o pool de threads
		final ExecutorService threads = Executors.newFixedThreadPool(qtdThreads);
		try {
			/*
			 * O countLatch serve pra que o main aguarde a execução de tasks.size() threads.
			 */
			final CountDownLatch latch = new CountDownLatch(tasks.size());
			// Executa todas as tasks e decrementa o latch quando terminar
			for (final Runnable task : tasks)
				threads.execute(new Runnable() {
					public void run() {
						try {
							task.run();
						} finally {
							latch.countDown();
						}
					}
				});
			// Após o inícios das threads, a main fica aqui aguardando.
			latch.await();
		} finally {
			threads.shutdown();
		}
	}
}
