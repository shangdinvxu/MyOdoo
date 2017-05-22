package tarce.model.inventory;

/**
 * Created by rose.zou on 2017/5/19.
 * 简单作用，用来做一个对象，展示两个信息
 */

public class ProcessShowBean {
    public ProcessShowBean() {

    }

    public String getProcess_name() {
        return process_name;
    }

    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    public int getProcess_num() {
        return process_num;
    }

    public void setProcess_num(int process_num) {
        this.process_num = process_num;
    }

    private String process_name;

    public ProcessShowBean(String process_name, int process_num) {
        this.process_name = process_name;
        this.process_num = process_num;
    }

    private int process_num;
}
