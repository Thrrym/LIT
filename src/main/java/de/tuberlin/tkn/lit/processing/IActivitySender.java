package de.tuberlin.tkn.lit.processing;

import java.util.concurrent.Future;
import de.tuberlin.tkn.lit.model.Activity;

public interface IActivitySender{
	Future<Boolean> send(Activity activity);
}