package net.eazypg.eazypgmanager.Adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.eazypg.eazypgmanager.Interface.LoadMore;
import net.eazypg.eazypgmanager.Model.Item;
import net.eazypg.eazypgmanager.R;

import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder
{
    public static ProgressBar progressBar;

     public LoadingViewHolder(View itemView) {
        super(itemView);

    }
}

class ItemViewHolder extends RecyclerView.ViewHolder
{
    public TextView name;

    public ItemViewHolder(View itemView) {
        super(itemView);
        name=  itemView.findViewById(R.id.nameTextView);
    }
}

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

   private final int VIEW_TYPE_ITEM =0, VIEW_TYPE_LOADING=0;

    LoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Item> items;
    int visibleThreshold =5;
    int lastVisibleItem ,totalItemCount;

    public MyAdapter(RecyclerView recyclerView, Activity activity , List<Item> items)
    {
     this.activity= activity;
     this.items=items;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem= linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold))
                {
                    if(loadMore != null)
                        loadMore.onLoadMore();
                    isLoading=true;
                }

            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(LoadMore loadMore)
    {
        this.loadMore=loadMore;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(activity).inflate(R.layout.complaintitem,parent,false);
            return new ItemViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(activity).inflate(R.layout.complaintitem,parent,false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder)
        {
            Item item = items.get(position);
            ItemViewHolder viewHolder= (ItemViewHolder) holder;
            if(items.get(position)!= null)
            {
            viewHolder.name.setText(items.get(position).getName());
            }
            else
            {
                viewHolder.name.setText("  ");
            }
           // viewHolder.
        }
        else if(holder instanceof LoadingViewHolder)
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded()
    {
        isLoading= false;
    }


}
