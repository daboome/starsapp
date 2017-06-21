package org.daboo.starsapp.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import org.daboo.starsapp.R;
import org.daboo.starsapp.api.RPC;
import org.daboo.starsapp.base.BaseFragment;
import org.daboo.starsapp.model.dto.StarsCollection;
import org.daboo.starsapp.model.ui.Star;
import org.daboo.starsapp.model.ui.StarType;
import org.daboo.starsapp.ui.FoldingCellListAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarsFragment extends BaseFragment implements Callback<StarsCollection> {
    @Bind(R.id.lvStars)
    ListView lvStars;
    @Bind(R.id.my_toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView mTitleView;
    @Bind(R.id.add_new_star)
    FloatingActionButton floatingActionButton;

    private Set<String> starTypes = new HashSet<>();

    public static StarsFragment newInstance(String username) {
        final Bundle args = new Bundle();
        args.putString("username", username);

        final StarsFragment starsFragment = new StarsFragment();
        starsFragment.setArguments(args);

        return starsFragment;
    }

    @Override
    public void onInitView() {
//        final Bundle args = getArguments();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        this.mTitleView.setText(getString(R.string.app_name));
        getActivity().supportInvalidateOptionsMenu();

        RPC.getAllStars(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String starTypeValue = starTypes.iterator().next();
                Star star = new Star("star name", "x coord", "y coord", new StarType(starTypeValue, "#00000"), "person");
                showEditStarDialog(star, starTypes);
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_stars;
    }

    @Override
    public void onResponse(Call<StarsCollection> call, Response<StarsCollection> response) {
        if (response.isSuccessful()) {
            StarsCollection starsCollection = response.body();
            List<Star> stars = starsCollection.getAllStars();
            for (final Star star : stars) {
                starTypes.add(star.getStarType().getValue());

                star.setEditStarBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showEditStarDialog(star, starTypes);
                    }
                });
            }

            final FoldingCellListAdapter adapter = new FoldingCellListAdapter(getActivity(), stars);

            lvStars.setAdapter(adapter);

            lvStars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    ((FoldingCell) view).toggle(false);
                    adapter.registerToggle(pos);
                }
            });
        }
    }

    @Override
    public void onFailure(Call<StarsCollection> call, Throwable t) {

    }

    private void showEditStarDialog(Star star, Set<String> starTypes) {
        final Dialog starEditDialog = new Dialog(getActivity());
        starEditDialog.setContentView(R.layout.star_edit_dialog);

        Spinner spinner = (Spinner) starEditDialog.findViewById(R.id.dg_start_type_value);
        EditText etStarName = (EditText) starEditDialog.findViewById(R.id.dg_star_name_value);
        EditText etStarXCoord = (EditText) starEditDialog.findViewById(R.id.dg_star_x_coord_value);
        EditText etStarYCoord = (EditText) starEditDialog.findViewById(R.id.dg_star_y_coord_value);
        etStarName.setText(star.getStarName());
        etStarXCoord.setText(star.getXcoord());
        etStarYCoord.setText(star.getYcoord());

        List<String> starTypeValues = new ArrayList<>();
        starTypeValues.addAll(starTypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, starTypeValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (!star.getStarType().getValue().equals(null)) {
            int spinnerPosition = adapter.getPosition(star.getStarType().getValue());
            spinner.setSelection(spinnerPosition);
        }

        Button okBtn = (Button) starEditDialog.findViewById(R.id.send_star);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starEditDialog.dismiss();
            }
        });

        Button cancelBtn = (Button) starEditDialog.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starEditDialog.dismiss();
            }
        });
        starEditDialog.show();
    }

}
