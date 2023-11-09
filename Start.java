public class Start{
    private static Model model;
    public static void main(String[] args) {
        args = new String[]{"./TestDirectory"};

        if (args.length == 0)
        {
            System.out.println("Requires Folder Path to be given for use by server.");
        }

        model = new Model(args[0]);
    }
}