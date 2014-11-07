//package ca.ualberta.app.adapter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import ca.ualberta.app.activity.R;
//import ca.ualberta.app.models.Question;
//import ca.ualberta.app.models.Reply;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class ReplyAdapter extends BaseExpandableListAdapter {
//
//	@Override
//	public Object getChild(int groupPosition, int childPosition) {
//		// TODO Auto-generated method stub
//		return children.get(groupPosition).get(childPosition);
//	}
//
//	@Override
//	public long getChildId(int groupPosition, int childPosition) {
//		// TODO Auto-generated method stub
//		return childPosition;
//	}
//
//	@Override
//	public View getChildView(int groupPosition, int childPosition,
//			boolean isLastChild, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		View cv;
//		if (convertView == null)
//			cv = newChildView(parent);
//		else
//			cv = convertView;
//		bindChildView(cv, children.get(groupPosition).get(childPosition),
//				childFrom, childTo);
//		return cv;
//	}
//
//	private View newChildView(ViewGroup parent) {
//		// TODO Auto-generated method stub
//		return inflater.inflate(R.layout.single_reply, parent, false);
//	}
//
//	private void bindChildView(View cv, Map<String, Object> map,
//			String[] childFrom, int[] childTo) {
//		// TODO Auto-generated method stub
//		TextView tv = (TextView) cv.findViewById(childTo[0]);
//		tv.setText((String) map.get(childFrom[0]));
//	}
//
//	@Override
//	public int getChildrenCount(int groupPosition) {
//		// TODO Auto-generated method stub
//		return children.get(groupPosition).size();
//	}
//
//	@Override
//	public Object getGroup(int groupPosition) {
//		// TODO Auto-generated method stub
//		return group.get(groupPosition);
//	}
//
//	@Override
//	public int getGroupCount() {
//		// TODO Auto-generated method stub
//		return group.size();
//	}
//
//	@Override
//	public long getGroupId(int groupPosition) {
//		// TODO Auto-generated method stub
//		return groupPosition;
//	}
//
//	@Override
//	public View getGroupView(int groupPosition, boolean isExpanded,
//			View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		View gv;
//		if (convertView == null)
//			gv = newGroupView(parent);
//		else
//			gv = convertView;
//		bindGroupView(gv, group.get(groupPosition), groupFrom, groupTo);
//		return gv;
//	}
//
//	private void bindGroupView(View gv, Map<String, Object> map,
//			String[] groupFrom, int[] groupTo) {
//		// TODO Auto-generated method stub
//		ImageView iv = (ImageView) gv.findViewById(groupTo[0]);
//		iv.setImageResource((Integer) map.get(groupFrom[0]));
//	}
//
//	private View newGroupView(ViewGroup parent) {
//		// TODO Auto-generated method stub
//		return inflater.inflate(glayout, parent, false);
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isChildSelectable(int groupPosition, int childPosition) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//}