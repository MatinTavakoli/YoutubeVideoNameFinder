import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;

public class Main {
    public static void main(String[] args) {

        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "41675");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showDialog(null, "Select");


        File folder = new File(fileChooser.getSelectedFile().getPath());

        String[] filesNames = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("HDVIDEO");
            }
        });


        int counter = 0;
        while (counter != filesNames.length) {
            for (String fileName : filesNames) {
                fileName = fileName.replace(".mp4", "");
                fileName = fileName.replace("HDVIDEO", "");
                String youtubeURL = "https://www.youtube.com/watch?v=" + fileName;
                System.out.println(youtubeURL);
                java.lang.System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
                Document doc = null;

//            try {
//                System.out.println(getURLSource(youtubeURL));
//            } catch (Exception e) {
//                System.out.println(e);
//            }
                try {
                    doc = Jsoup.connect(youtubeURL).userAgent("Mozilla/5.0").get();
                    String name = doc.title();
                    name = name.replace(" - YouTube", "");
                    name = name.replace("/", "");
                    name = name.replace(":", "");
                    name = name.replace("*", "");
                    name = name.replace("?", "");
                    name = name.replace("<", "");
                    name = name.replace(">", "");
                    name = name.replace("|", "");
                    name = name.replace("\"", "");
                    name = name.replace("\\", "");
                    if (name.contains("#")) {//This means that it is a crash course video!
                        String fileIndex = name.substring(name.indexOf('#') + 1);
                        name = fileIndex + ". " + name;
                    }
                    System.out.println(name);
                    File previousFile = new File(folder.getPath() + "\\" + "HDVIDEO" + fileName + ".mp4");
                    File newFile = new File(folder.getPath() + "\\" + name + ".mp4");
                    if (previousFile.renameTo(newFile)) {
                        counter++;
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        }

    }
}
