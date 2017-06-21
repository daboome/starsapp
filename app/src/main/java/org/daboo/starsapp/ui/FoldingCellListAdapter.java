package org.daboo.starsapp.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import org.daboo.starsapp.R;
import org.daboo.starsapp.model.ui.Star;

import java.util.HashSet;
import java.util.List;

public class FoldingCellListAdapter extends ArrayAdapter<Star> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;


    public FoldingCellListAdapter(Context context, List<Star> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get star for selected view
        Star star = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.tvTitleStarName = (TextView) cell.findViewById(R.id.title_star_name);
            viewHolder.tvContentStarName = (TextView) cell.findViewById(R.id.content_star_name);
            viewHolder.tvTitleXcoord = (TextView) cell.findViewById(R.id.title_star_coord_x);
            viewHolder.tvTitleYcoord = (TextView) cell.findViewById(R.id.title_star_coord_y);
            viewHolder.tvContentXcoord = (TextView) cell.findViewById(R.id.content_star_coord_x);
            viewHolder.tvContentYcoord = (TextView) cell.findViewById(R.id.content_star_coord_y);
            viewHolder.tvTitleStarType = (TextView) cell.findViewById(R.id.title_star_type_value);
            viewHolder.tvContentStarType = (TextView) cell.findViewById(R.id.content_star_type_value);
            viewHolder.tvDiscoveredPerson = (TextView) cell.findViewById(R.id.content_person_name);
            viewHolder.tvContentRequestBtn = (TextView) cell.findViewById(R.id.content_request_btn);
            viewHolder.rlTitleStarColor = (RelativeLayout) cell.findViewById(R.id.title_star_color);
            viewHolder.rlContentStarColor = (RelativeLayout) cell.findViewById(R.id.content_star_color);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }
        // bind data from selected element to view through view holder
        viewHolder.tvTitleStarName.setText(star.getStarName());
        viewHolder.tvContentStarName.setText(star.getStarName());
        viewHolder.tvTitleXcoord.setText(star.getXcoord());
        viewHolder.tvTitleYcoord.setText(star.getYcoord());
        viewHolder.tvContentXcoord.setText(star.getXcoord());
        viewHolder.tvContentYcoord.setText(star.getYcoord());
        viewHolder.tvTitleStarType.setText(star.getStarType().getValue());
        viewHolder.tvContentStarType.setText(star.getStarType().getValue());
        viewHolder.tvDiscoveredPerson.setText(star.getDiscoveredPerson());
        viewHolder.rlTitleStarColor.setBackgroundColor(Color.parseColor(star.getStarType().getColor()));
        viewHolder.rlContentStarColor.setBackgroundColor(Color.parseColor(star.getStarType().getColor()));
        // set custom btn handler for list star from that star
        if (star.getEditStarBtnClickListener() != null) {
            viewHolder.tvContentRequestBtn.setOnClickListener(star.getEditStarBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in star
            viewHolder.tvContentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }
        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        RelativeLayout rlTitleStarColor;
        RelativeLayout rlContentStarColor;
        TextView tvTitleStarName;
        TextView tvContentStarName;
        TextView tvTitleXcoord;
        TextView tvTitleYcoord;
        TextView tvContentXcoord;
        TextView tvContentYcoord;
        TextView tvTitleStarType;
        TextView tvContentStarType;
        TextView tvDiscoveredPerson;
        TextView tvContentRequestBtn;
    }
}
