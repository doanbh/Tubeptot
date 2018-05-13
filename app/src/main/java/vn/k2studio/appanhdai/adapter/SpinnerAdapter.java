package vn.k2studio.appanhdai.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import vn.k2studio.appanhdai.R;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> arrChoiceRhythm;
    private ListFilter listFilter = new ListFilter();
    private List<String> dataListAllItems;
    private int viewResourceId;
    private onClickCityHandler mCityHandler;

    public void setCityHandler(onClickCityHandler cityHandler) {
        mCityHandler = cityHandler;
    }

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource,
            ArrayList<String> arrChoiceRhythm) {
        super(context, resource, arrChoiceRhythm);
        this.context = context;
        this.arrChoiceRhythm = arrChoiceRhythm;
    }

    public int getCount() {
        return arrChoiceRhythm.size();
    }

    public String getItem(int position) {
        return arrChoiceRhythm.get(position).toString();
    }

    public long getItemId(int position) {
        return position;
    }

    //    @NonNull
    //    @Override
    //    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    //        //        TextView view = new TextView(context);
    //        //        view.setGravity(Gravity.CENTER);
    //        //        return view;
    //        View v = convertView;
    //        if (v == null) {
    //            LayoutInflater vi =
    //                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //            v = vi.inflate(viewResourceId, null);
    //        }
    //        final String city = arrChoiceRhythm.get(position);
    //        if (city != null) {
    //            TextView customerNameLabel = v.findViewById(R.id.txt_choice_span);
    //            if (customerNameLabel != null) {
    //                customerNameLabel.setText(city);
    //            }
    //            customerNameLabel.setOnClickListener(new View.OnClickListener() {
    //                @Override
    //                public void onClick(View v) {
    //                    mCityHandler.onClickCity(city);
    //                }
    //            });
    //        }
    //        return v;
    //    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = new TextView(context);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
            @NonNull ViewGroup parent) {
        String s = arrChoiceRhythm.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        TextView txtChoiceSpan = (TextView) view.findViewById(R.id.txt_choice_span);
        txtChoiceSpan.setText(s);
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<String>(arrChoiceRhythm);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<String> matchValues = new ArrayList<String>();

                for (String dataItem : dataListAllItems) {
                    //*This is the actual line where you can change your logic for startWith or Contains*
                    if (dataItem.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                arrChoiceRhythm = (ArrayList<String>) results.values;
            } else {
                arrChoiceRhythm = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    public interface onClickCityHandler {
        void onClickCity(String city);
    }
}
