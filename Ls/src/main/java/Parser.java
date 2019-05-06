import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;

public class Parser {
    @Option(name = "-l", usage = "Long format")
    private static boolean l = false;

    @Option(name = "-h",usage = "Human-readable")
    private static boolean h = false;

    @Option(name = "-r", usage = "Reverse")
    private static boolean r = false;

    @Option(name = "-o",usage = "Output file name")
    private static String outputPath;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ls [-l] [-h] [-r] [-o output.file] directory_or_file");
            return;
        }
        Ls.item = new File(args[args.length - 1]);
        if (Ls.item.isDirectory()) {

            String[] names = Ls.item.list();
            if (names == null) {
                throw new Ls.MyException("Не введены аргументы");
            }
            if (names.length == 0) {
                Ls.answer.put("Пусто", "");
                return;
            }

            for (String str : names) {
                Ls.answer.put(str, "");
            }
        } else {
            Ls.answer.put(Ls.item.getName(), "");
        }

        new Parser().launch(args);
        final int last = args.length - 1;
        int i = 0;
        while(i < last) {
            if (args[i].contains("-l")) {
                l = true;
                Ls.getRWX(false);
                i++;
                continue;

            }
            if (args[i].contains("-h")) {
                h = true;
                Ls.getRWX(true);
                i++;
                continue;

            }
            if (args[i].contains("-r")) {
                r = true;
                Ls.reverseTreeMap(Ls.answer);

            }
            if (args[i].equals("-o")) {
                outputPath = args[i++].substring(0, args[i].length() - 1);
            }
            return;
        }
        Ls.printAnswer();
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        }
        catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Ls [-l] [-h] [-r] [-o output.file] directory_or_file");
            parser.printUsage(System.err);
        }

    }
}