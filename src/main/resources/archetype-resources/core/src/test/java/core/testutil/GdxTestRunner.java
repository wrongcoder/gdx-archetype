#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.testutil;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * JUnit test runner
 *
 * <p/>
 * Based on <a href="https://bitbucket.org/TomGrill/libgdx-testing-sample/src/df874d51ce9e19cc3b6515b8d71062bced026b33/core/src/com/libgdxtesting/GdxTestRunner.java">TomGrill/libgdx-testing-sample</a>
 * <p/>
 * See also <a href="http://java.dzone.com/articles/concurrent-junit-tests">Concurrent JUnit Tests With RunnerScheduler</a>
 */
public class GdxTestRunner extends BlockJUnit4ClassRunner {

	private final ConcurrentLinkedQueue<Runnable> testQueue = new ConcurrentLinkedQueue<Runnable>();

	private final CountDownLatch startTests = new CountDownLatch(1);
	private final CountDownLatch finishedTests = new CountDownLatch(1);

	public GdxTestRunner(final Class<?> clazz) throws InitializationError {
		super(clazz);

		setScheduler(new InRenderRunnerScheduler());

		final GdxTestRunnerApplicationListener applicationListener = new GdxTestRunnerApplicationListener();
		new HeadlessApplication(applicationListener);
	}

	private class InRenderRunnerScheduler implements RunnerScheduler {
		@Override
		public void schedule(final Runnable childStatement) {
			testQueue.add(childStatement);
		}

		@Override
		public void finished() {
			startTests.countDown();

			try {
				finishedTests.await();
			}
			catch (final InterruptedException e) {
				System.err.println("Interrupted while waiting for tests to finish running");
				e.printStackTrace(System.err);
			}
		}
	}

	private class GdxTestRunnerApplicationListener extends ApplicationAdapter {
		@Override
		public void render() {
			try {
				startTests.await();

				while (!testQueue.isEmpty()) {
					testQueue.poll().run();
				}
				finishedTests.countDown();
			}
			catch (final InterruptedException e) {
				System.err.println("Interrupted while waiting to start tests");
				e.printStackTrace(System.err);
			}
		}
	}

}
