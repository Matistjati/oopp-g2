package oopp.cli;

import java.util.ArrayList;

public class CLIArgs {
    private ArrayList<String> args = new ArrayList<>();
    public CLIArgs()
    {

    }
    public CLIArgs(ArrayList<String> args)
    {
        this.args = args;
    }
    public void addArg(String arg)
    {
        args.add(arg);
    }

    public String getArg(int i)
    {
        return args.get(i);
    }

    public int argSize()
    {
        return args.size();
    }
}
