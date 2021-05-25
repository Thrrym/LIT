package de.tuberlin.tkn.lit.processing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import de.tuberlin.tkn.lit.model.Activity;
import org.springframework.stereotype.Service;

@Service
public class ServerToServerCommunicator implements IActivitySender{
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	@Override
	public Future<Boolean> send(Activity activity) {
		return executor.submit(() -> {return false;});
	}
}