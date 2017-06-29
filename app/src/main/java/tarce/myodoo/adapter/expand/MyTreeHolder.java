package tarce.myodoo.adapter.expand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

import tarce.myodoo.R;

/**
 * Created by zouzou on 2017/6/29.
 */

public class MyTreeHolder extends TreeNode.BaseNodeViewHolder<MyTreeHolder.ParentBean> {
    private Context context;

    public MyTreeHolder(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View createNodeView(final TreeNode node, ParentBean value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.componyone, null, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_name);
        textView.setText(value.getName());
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_arrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*TreeNode newFolder = new TreeNode(new MyTreeHolder.ParentBean("sadfw"));
                getTreeView().addNode(node, newFolder);*/
            }
        });


        return view;
    }

    public static class ParentBean {
        public int getDepart_id() {
            return depart_id;
        }

        public void setDepart_id(int depart_id) {
            this.depart_id = depart_id;
        }

        public ParentBean(int depart_id, String name) {
            this.depart_id = depart_id;
            this.name = name;
        }

        private int depart_id;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
    }

    public interface SetItemClick{
        void setItemClick();
    }
    public void setOnItemClick(){

    }
}
