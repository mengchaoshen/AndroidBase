package com.smc.androidbase;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：adapter
 * 创建人：yangxd
 * 创建时间：2016/9/18 15:55
 */
public abstract class BaseHolderAdapter<T> extends BaseAdapter {

	public List<T> dataList;
	public Context context;

	public BaseHolderAdapter(Context context) {
		init(context, new ArrayList<T>());
	}

	public BaseHolderAdapter(Context context, List<T> list) {
		init(context, list);
	}

	private void init(Context context, List<T> dataList) {
		this.context = context;
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		return dataList == null ? 0 : dataList.size();
	}

	@Override
	public T getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			int layoutId = getContentView(position);
			convertView = inflate(layoutId);
		}
		onInitView(convertView, position);
		return convertView;
	}

	/**
	 * 加载布局
	 * 
	 * @param layoutResID
	 * @return
	 */
	private View inflate(int layoutResID) {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, null);
		return view;
	}

	public abstract int getContentView(int position);

	public abstract void onInitView(View view, int position);

	/**
	 * 
	 * @param view
	 *            converView
	 * @param id
	 *            控件的id
	 * @return 返回<t extends="" view="">
	 */
	@SuppressWarnings("unchecked")
	protected <E extends View> E get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (null == viewHolder) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (null == childView) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (E) childView;
	}

}
