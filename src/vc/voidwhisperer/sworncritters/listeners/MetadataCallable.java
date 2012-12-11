package vc.voidwhisperer.sworncritters.listeners;

import java.util.concurrent.Callable;

public class MetadataCallable implements Callable {
	@Override
	public Object call() throws Exception {
		return "true";
	}
}
