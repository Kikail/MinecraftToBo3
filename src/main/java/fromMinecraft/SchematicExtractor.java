package fromMinecraft;


import com.example.minecraftbo3.HelloController;
import javafx.concurrent.Task;
import net.sandrohc.schematic4j.SchematicLoader;
import net.sandrohc.schematic4j.schematic.Schematic;
import net.sandrohc.schematic4j.schematic.types.SchematicBlock;
import toRadiant.Map;
import toRadiant.ToRadiantPrefab;

import java.io.File;
import java.io.IOException;


public class SchematicExtractor {

    public void Extract(String path, Map map, File fileIDs, HelloController controller) {
        System.out.println("Extracting Schematic");

        // Création d'une tâche pour exécuter hors du thread JavaFX
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                parseFile(path, map, fileIDs,controller);
                return null;
            }
        };

        // Gestion des exceptions
        task.setOnFailed(event -> {
            Throwable e = task.getException();
            System.err.println("Erreur lors du chargement du schematic:");
            e.printStackTrace();
        });

        new Thread(task).start();
    }

    public void parseFile(String filepath, Map map, File fileIDs, HelloController controller) {
        File schemFile = new File(filepath);
        try {
            System.out.println("Chemin du fichier: " + schemFile.getAbsolutePath());

            if (!schemFile.exists()) {
                throw new IOException("Le fichier n'existe pas: " + schemFile.getAbsolutePath());
            }

            Schematic schematic = SchematicLoader.load(schemFile);

            controller.showProgressBar();
            int totalBlocks = schematic.width() * schematic.length() * schematic.height();
            int currentBlock = 0;

            for (int x = 0; x < schematic.width(); x++) {
                for (int y = 0; y < schematic.height(); y++) {
                    for (int z = 0; z < schematic.length(); z++) {
                        SchematicBlock block = schematic.block(x, y, z);
                        if(!block.name().equals("minecraft:air")){
                            //System.out.println("(x: " + x + ", y: " + y + ", z: " + z+"): "+block.name()+block.states());
                            ToRadiantPrefab.readBlockData(block.name()+block.states().values(),schematic.width() - x - 1,y,z,map,fileIDs );
                            controller.addLabel(block.name());
                        }
                        currentBlock ++;
                        controller.updateProgress(currentBlock, totalBlocks);
                    }
                }
            }

            ToRadiantPrefab.createPrefab(filepath,controller,map);
            controller.hideProgressBar();
            controller.setProcessing(false);

        } catch (Exception e) {
            throw new RuntimeException("Erreur dans parseFile", e);
        }
    }
}