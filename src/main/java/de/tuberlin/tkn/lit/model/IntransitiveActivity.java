package de.tuberlin.tkn.lit.model;

public abstract class IntransitiveActivity extends Activity {


    public IntransitiveActivity() {
    }

    public IntransitiveActivity(String context, LinkOrObject actor, LinkOrObject object, LinkOrObject target, LinkOrObject result, LinkOrObject origin, LinkOrObject instrument) {
        super(context,actor,object,target,result,origin,instrument);

    }

    public IntransitiveActivity(Activity activity) {
        super(activity);
    }
}
