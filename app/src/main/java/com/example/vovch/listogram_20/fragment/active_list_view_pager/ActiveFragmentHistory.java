package com.example.vovch.listogram_20.fragment.active_list_view_pager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vovch.listogram_20.R;
import com.example.vovch.listogram_20.activities.complex.ActiveListsActivity;
import com.example.vovch.listogram_20.data_types.HistoryScrollView;
import com.example.vovch.listogram_20.data_types.Item;
import com.example.vovch.listogram_20.data_types.ItemButton;
import com.example.vovch.listogram_20.data_types.SList;

/**
 * Created by vovch on 14.12.2017.
 */

public class ActiveFragmentHistory extends Fragment {
    private View rootView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void checkRootView(ViewGroup container, LayoutInflater inflater) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.group_fragment_history, container, false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.group_fragment_history, container, false);
        SwipeRefreshLayout refreshLayout = getRefresher();
        refreshLayout.setFocusable(false);
        refreshLayout.setRefreshing(false);
        ActiveListsActivity activity = (ActiveListsActivity) getActivity();
        activity.onHistoryFragmentStart();
        return rootView;
    }

    protected void listsLayoutDrawer(SList list) {
        LinearLayout basicLayout;
        basicLayout = (LinearLayout) rootView.findViewById(R.id.passedlistogramslayout);
        CardView listCard = (CardView) LayoutInflater.from(basicLayout.getContext()).inflate(R.layout.list_card, basicLayout, false);
        list.setCardView(listCard);
        LinearLayout listogramLayout = (LinearLayout) LayoutInflater.from(listCard.getContext()).inflate(R.layout.list_layout, listCard, false);
        Item[] items = list.getItems();
        int length = items.length;
        for (int i = 0; i < length; i++) {
            makeListogramLine(items[i], listogramLayout);
        }
        FrameLayout disButtonFrameLayout = (FrameLayout) LayoutInflater.from(listogramLayout.getContext()).inflate(R.layout.dis_button_frame_layout, listogramLayout, false);
        final ImageButton disactivateListButton = (ImageButton) LayoutInflater.from(listogramLayout.getContext()).inflate(R.layout.done_button, disButtonFrameLayout, false);
        list.setDisButton(disactivateListButton);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            disactivateListButton.setImageDrawable(getActivity().getDrawable(R.drawable.done_button_drawble));
        } else {
            disactivateListButton.setImageDrawable(getResources().getDrawable(R.drawable.done_button_drawble));
        }
        Uri uri = Uri.parse("android.resource://com.example.vovch.listogram_20/drawable/confirm_button_64");
        disactivateListButton.setImageURI(uri);
        disactivateListButton.setAlpha(0.5f);
        disButtonFrameLayout.addView(disactivateListButton);
        listogramLayout.addView(disButtonFrameLayout);
        listCard.addView(listogramLayout);
        basicLayout.addView(listCard);
    }

    private void makeListogramLine(Item item, LinearLayout listogramLayout) {
        FrameLayout addingFrameLayout = (FrameLayout) LayoutInflater.from(listogramLayout.getContext()).inflate(R.layout.list_element_frame_layout, listogramLayout, false);
        LinearLayout addingLayout = (LinearLayout) LayoutInflater.from(addingFrameLayout.getContext()).inflate(R.layout.list_element_linear_layout, addingFrameLayout, false);

        TextView itemName = (TextView) LayoutInflater.from(addingLayout.getContext()).inflate(R.layout.list_element_text_view, addingLayout, false);
        itemName.setText(item.getName());
        TextView itemComment = (TextView) LayoutInflater.from(addingLayout.getContext()).inflate(R.layout.list_element_text_view, addingLayout, false);
        itemComment.setText(item.getComment());
        ItemButton groupButton = (ItemButton) LayoutInflater.from(addingLayout.getContext()).inflate(R.layout.list_element_button, addingFrameLayout, false);
        groupButton.setItem(item);
        if (item.getState()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                addingLayout.setBackground(getActivity().getDrawable(R.drawable.no_corners_layout_color_1));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                addingLayout.setBackground(getResources().getDrawable(R.drawable.no_corners_layout_color_1));
            } else {
                addingLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.no_corners_layout_color_1));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                addingLayout.setBackground(getActivity().getDrawable(R.drawable.no_corners_layout_color_2));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                addingLayout.setBackground(getResources().getDrawable(R.drawable.no_corners_layout_color_2));
            } else {
                addingLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.no_corners_layout_color_2));
            }
        }
        addingLayout.setAlpha(0.5f);
        groupButton.setFocusable(false);
        groupButton.setClickable(false);
        addingLayout.addView(itemName);
        addingLayout.addView(itemComment);
        item.setLayout(addingLayout);
        item.setButton(groupButton);
        addingFrameLayout.addView(addingLayout);
        addingFrameLayout.addView(groupButton);
        listogramLayout.addView(addingFrameLayout);
    }

    public void listsListMaker(SList[] result) {
        int listsNumber = result.length;
        for (int i = 0; i < listsNumber; i++) {
            listsLayoutDrawer(result[i]);
        }
    }

    public void historyFragmentCleaner() {
        LinearLayout layoutScrollingActiveListsContainer = (LinearLayout) rootView.findViewById(R.id.passedlistogramslayout);
        layoutScrollingActiveListsContainer.removeAllViews();
    }

    public void fragmentShowGood(SList[] result) {
        historyFragmentCleaner();
        listsListMaker(result);
    }

    public void fragmentShowBad(SList[] result) {
        historyFragmentCleaner();
        LinearLayout parentLayout = (LinearLayout) rootView.findViewById(R.id.passedlistogramslayout);
        TextView emptyInformer = (TextView) LayoutInflater.from(parentLayout.getContext()).inflate(R.layout.no_listograms_text_view, parentLayout, false);
        if (!(result == null || result.length == 0)) {
            emptyInformer.setText("We Failed");
        }
        parentLayout.addView(emptyInformer);
    }

    public void setRefresher() {
        SwipeRefreshLayout refreshLayout = getRefresher();
        refreshLayout.setFocusable(true);
        refreshLayout.setRefreshing(false);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    public void unsetRefresher() {
        SwipeRefreshLayout refreshLayout = getRefresher();
        refreshLayout.setFocusable(false);
        refreshLayout.setRefreshing(false);
    }

    public void refresh() {
        ActiveListsActivity activity = (ActiveListsActivity) getActivity();
        activity.refreshOfflineHistory();
    }

    public SwipeRefreshLayout getRefresher() {
        return (SwipeRefreshLayout) rootView.findViewById(R.id.group_history_fragment_refresher);
    }
}
