package de.d3adspace.caladrius.config.writer.yaml;

import de.d3adspace.caladrius.config.ConfigMeta;
import de.d3adspace.caladrius.config.writer.AbstractMapBasedConfigWriter;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class YAMLConfigWriter<ConfigObjectType> extends AbstractMapBasedConfigWriter<ConfigObjectType> {

    private static final Yaml YAML = new Yaml();

    public YAMLConfigWriter(ConfigMeta<ConfigObjectType> configMeta, Path path) {
        super(configMeta, path);
    }

    @Override
    protected void doWrite() {
        String dump = YAML.dumpAsMap(getContent());

        try {
            if (!Files.exists(getPath())) {
                Files.createFile(getPath());
            } else {
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(getPath())) {
                    bufferedWriter.write("");
                    bufferedWriter.flush();
                }
            }

            Files.writeString(getPath(), dump);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
