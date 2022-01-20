package com.ocaml.ide.sdk.providers.windows;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.wsl.WSLCommandLineOptions;
import com.intellij.execution.wsl.WSLDistribution;
import com.intellij.execution.wsl.WslPath;
import com.ocaml.compiler.OCamlSdkVersionManager;
import com.ocaml.ide.sdk.providers.utils.AssociatedBinaries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class WSLSdkProvider extends AbstractWindowsBaseProvider {

    @Override protected boolean canUseProviderForOCamlBinary(@NotNull String path) {
        LOG.warn("Should not be called on a WSL.");
        return false;
    }

    /**
     * It's worth noting that we could use the one in the BaseProvider, but
     * "Files.exists" is failing for every symbolic link, and I don't want that.
     */
    @Override public @Nullable AssociatedBinaries getAssociatedBinaries(@NotNull String ocamlBinary) {
        // is ocaml
        if (!ocamlBinary.endsWith("ocaml")) return null;
        // is wsl
        WslPath path = WslPath.parseWindowsUncPath(ocamlBinary);
        if (path == null) return null;
        // OK let's start
        LOG.debug("Detected WSL "+path.getDistribution()+" for "+ocamlBinary);
        WSLDistribution distribution = path.getDistribution();
        // get path to ocamlc
        String ocamlc = distribution.getWslPath(ocamlBinary + "c");
        if (ocamlc == null) {
            LOG.debug("ocamlc not found for "+ocamlBinary);
            return null;
        }
        // get sources
        String root = ocamlc.replace("bin/ocamlc", "");
        String sourcesFolder = null;
        for (String s:getOCamlSourcesFolders()) {
            String sourcePath = root + s;
            // try to convert to WSL path
            String sourceCandidate = distribution.getWindowsPath(sourcePath);
            // Exists?
            if (!Files.exists(Path.of(sourceCandidate))) continue;
            // OK
            sourcesFolder = sourceCandidate;
            break;
        }
        if (sourcesFolder == null) {
            LOG.debug("No sources folder");
            return null;
        }

        try {
            GeneralCommandLine cli = new GeneralCommandLine(ocamlc, "-version");
            cli = distribution.patchCommandLine(cli, null, new WSLCommandLineOptions());
            LOG.debug("CLI is: "+cli.getCommandLineString());
            Process process = cli.createProcess();
            InputStream inputStream = process.getInputStream();
            String version = new String(inputStream.readAllBytes()).trim();
            LOG.debug("Version of "+ocamlc+" is '"+version+"'.");
            // if we got something better
            String alt = OCamlSdkVersionManager.parse(ocamlBinary);
            if (!OCamlSdkVersionManager.isUnknownVersion(alt)) version = alt;
            return new AssociatedBinaries(ocamlBinary, ocamlBinary + "c", sourcesFolder, version);
        } catch (ExecutionException | IOException e) {
            LOG.debug(e.getMessage());
        }
        return null;
    }

    @Override public @Nullable GeneralCommandLine getCompilerVersionCLI(String ocamlcCompilerPath) {
        return null;
    }

    //
    // SDK
    //

    @Override public @NotNull Set<String> getInstallationFolders() {
        // todo: ...
        return Set.of();
    }
}
