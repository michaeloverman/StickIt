package tech.michaeloverman.android.mscount;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Michael on 4/21/2016.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("SingleFragmentActivity onCreate()");
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
    public void nextStone() {
        Toast.makeText(this, "Activity Next Stone", Toast.LENGTH_SHORT).show();
    }
    public void previousStone() {
        Toast.makeText(this, "Activity Previous Stone", Toast.LENGTH_SHORT).show();
    }
}

