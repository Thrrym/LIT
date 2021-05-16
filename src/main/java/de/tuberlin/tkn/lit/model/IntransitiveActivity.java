package de.tuberlin.tkn.lit.model;

public class IntransitiveActivity extends Activity {


    public IntransitiveActivity() {
    }

    public IntransitiveActivity(String context, LitObject actor, LitObject object, LitObject target, LitObject result, LitObject origin, LitObject instrument) {
        super(context,actor,object,target,result,origin,instrument);

    }

    public IntransitiveActivity(Activity activity) {
        super(activity);

    }
}
