package tarce.myodoo.bean;

/**
 * Created by zouwansheng on 2017/12/18.
 */

public class ProcessBean {
    private String name;
    private int process;
    private int num;

    public ProcessBean(String name, int process, int num) {
        this.name = name;
        this.process = process;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
