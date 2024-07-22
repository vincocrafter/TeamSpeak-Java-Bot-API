/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2024  
 *
 * Datum : 07.02.2024
 * 
 * Uhrzeit : 20:01:43
 */
package net.devcube.vinco.teamspeakapi.api.api.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class CommandFuture<I> {
	
	
	private FutureTask<Command> task;
	private I value;
	private Transformator<I> transformator;
	private Consumer<I> onFinish;

	public CommandFuture(Transformator<I> transformator) {
		this.transformator = transformator;
	}

	public void setTask(FutureTask<Command> task) {
		this.task = task;
	}

	public I get() {
		Command cmd;
		try {
			cmd = getCommand();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
		
		if (value == null)
			this.value = transformator.transformResult(cmd.getResult());
		
		return value;
	}
	
	public I get(long time, TimeUnit unit)  {
		Command cmd;
		try {
			cmd = getCommand(time, unit);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
			return null;
		}
		if (value == null)
			this.value = transformator.transformResult(cmd.getResult());
	
		return value;
	}
	
	public String getErrorResult() {
		Command cmd;
		try {
			cmd = getCommand();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
		
		return cmd.getError();
	}
	
	public String getErrorResult(long time, TimeUnit unit)  {
		Command cmd;
		try {
			cmd = getCommand(time, unit);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
			return null;
		}
		
		return cmd.getError();
	}
	
	private Command getCommand() throws InterruptedException, ExecutionException {
		return task.get();
	}
	
	private Command getCommand(long time, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return task.get(time, unit);
	}

	public CommandFuture onFinish(Consumer<I> onFinish) {
		this.onFinish = onFinish;
		return this;
	}

	protected Consumer<I> getOnFinish() {
		return onFinish;
	}

	/**
	 * @return the task
	 */
	public FutureTask<Command> getTask() {
		return task;
	}
	
	public boolean isDone() {
		return task.isDone();
	}
	
	public boolean isCancelled() {
		return task.isCancelled();
	}
	
	public boolean cancel(boolean mayInterruptIfRunning) {
		return task.cancel(mayInterruptIfRunning);
	}
	
	@FunctionalInterface
	public interface Transformator<I> {

		
		I transformResult(String result);

	}
	
}
