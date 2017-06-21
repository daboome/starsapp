package org.daboo.starsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.daboo.starsapp.R;
import org.daboo.starsapp.base.BaseMainActivityInterface;
import org.daboo.starsapp.fragment.StarsFragment;
import org.daboo.starsapp.fragment.LoginFragment;
import org.daboo.starsapp.helper.SharedPrefUtils;


public class MainActivity extends AppCompatActivity implements BaseMainActivityInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean logged = SharedPrefUtils.getBooleanFromPrefs("logged", false);
        if (savedInstanceState == null) {
            if (!logged) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.root_layout, LoginFragment.newInstance(), "loginFragment")
                        .commit();
            } else {
                String username = SharedPrefUtils.getFromPrefs("username", "anonymous");
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.root_layout, StarsFragment.newInstance(username), "starsFragment")
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stars, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            if (SharedPrefUtils.getBooleanFromPrefs("logged", false)) {
                SharedPrefUtils.saveToPrefs("logged", false);
                this.logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void authSuccess(String username) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, StarsFragment.newInstance(username), "starsFragment")
                .commit();
    }

    @Override
    public void logout() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, LoginFragment.newInstance(), "loginFragment")
                .commit();
    }

}
