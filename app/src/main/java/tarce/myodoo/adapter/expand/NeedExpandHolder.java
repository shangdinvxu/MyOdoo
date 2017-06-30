package tarce.myodoo.adapter.expand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.unnamed.b.atv.model.TreeNode;

import tarce.myodoo.R;

/**
 * Created by zouzou on 2017/6/29.
 * 需要展开的item
 */

public class NeedExpandHolder extends TreeNode.BaseNodeViewHolder<NeedExpandHolder.NeedExpandBean>{

    private Context context;
    public NeedExpandHolder(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View createNodeView(final TreeNode node, NeedExpandBean value) {
        View view = LayoutInflater.from(context).inflate(R.layout.componyone, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_arrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreeNode newFolder = new TreeNode(new NeedExpandHolder.NeedExpandBean("sdf","sadfw"));
                getTreeView().addNode(node, newFolder);
            }
        });
        return null;
    }


    public static class NeedExpandBean{
        public NeedExpandBean(String zhiwei, String name) {
            this.zhiwei = zhiwei;
            this.name = name;
        }

        public String getZhiwei() {
            return zhiwei;
        }

        public void setZhiwei(String zhiwei) {
            this.zhiwei = zhiwei;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String zhiwei;
        private String name;
    }
}
