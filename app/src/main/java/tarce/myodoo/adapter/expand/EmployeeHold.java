package tarce.myodoo.adapter.expand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

import tarce.myodoo.R;

/**
 * Created by zouozu on 2017/6/29.
 * 员工hold
 */

public class EmployeeHold extends TreeNode.BaseNodeViewHolder<EmployeeHold.Employee>{

    private Context context;
    public EmployeeHold(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View createNodeView(TreeNode node, Employee value) {
        View view = LayoutInflater.from(context).inflate(R.layout.componyone, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_name);
        textView.setText(value.getName());
        return view;
    }

    public static class Employee{
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            private String name;
            private String email;

            public Employee(String name, String email) {
                this.name = name;
                this.email = email;
            }
        }
}
