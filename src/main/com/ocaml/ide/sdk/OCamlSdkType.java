package com.ocaml.ide.sdk;

import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.ui.configuration.projectRoot.*;
import com.intellij.openapi.util.io.*;
import com.intellij.openapi.vfs.*;
import com.ocaml.compiler.*;
import com.ocaml.compiler.opam.*;
import com.ocaml.icons.*;
import org.jdom.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.regex.*;

public class OCamlSdkType extends LocalSdkType implements SdkDownload {

    private static final String UNKNOWN_VERSION = "unknown version";
    private static final String OCAML_SDK = "OCaml SDK";

    public OCamlSdkType() {
        super(OCAML_SDK);
    }

    public static OCamlSdkType getInstance() {
        return SdkType.EP_NAME.findExtension(OCamlSdkType.class);
    }

    //
    // Infos
    //

    @NotNull @Override public String getPresentableName() {
        return "OCaml";
    }

    @NotNull @Override public Icon getIcon() {
        return OCamlIcons.Nodes.OCAML_SDK;
    }

    //
    // Home Path
    //

    @Override public @NotNull Collection<String> suggestHomePaths() {
        return Collections.emptyList();
    }

    @Nullable @Override public String suggestHomePath() {
        return null;
    }

    //
    // Name
    //

    @NotNull @Override public final String suggestSdkName(String currentSdkName, @NotNull String sdkHome) {
        return suggestSdkName(getVersionString(sdkHome));
    }

    /**
     * Suggest a name for an SDK given its version. This method is the same
     * used for any OCaml SDK, uniformizing any names.
     */
    public static String suggestSdkName(String version) {
        return "OCaml-" + version;
    }

    //
    // Version
    //

    @NotNull @Override public String getVersionString(@NotNull String sdkHome) {
        // read the version in the name
        String serialized = sdkHome.replace("\\", "/");
        Matcher m1 = OCamlConstants.VERSION_PATH_REGEXP.matcher(serialized);
        if (m1.matches()) {
            return m1.group(1);
        }
        return UNKNOWN_VERSION;
    }

    //
    // Valid
    //

    @Override public boolean isValidSdkHome(@NotNull String sdkHome) {
        return !getVersionString(sdkHome).equals(UNKNOWN_VERSION);
    }

    //
    // Index sources
    //

    @Override public @Nullable AdditionalDataConfigurable createAdditionalDataConfigurable(@NotNull SdkModel sdkModel, @NotNull SdkModificator sdkModificator) {
        return null;
    }

    @Override public void saveAdditionalData(@NotNull SdkAdditionalData additionalData, @NotNull Element additional) {
    }

    @Override public void setupSdkPaths(@NotNull Sdk sdk) {
        String homePath = sdk.getHomePath();
        assert homePath != null : sdk;
        SdkModificator sdkModificator = sdk.getSdkModificator();
        sdkModificator.removeRoots(OrderRootType.CLASSES);
        addSources(new File(homePath), sdkModificator);
        sdkModificator.commitChanges();
    }

    public static void addSources(@NotNull File sdkHomeFile, @NotNull SdkModificator sdkModificator) {
        addSources(OCamlConstants.LIB_FOLDER_LOCATION_R, sdkHomeFile, sdkModificator, true);
        addSources(OpamConstants.SOURCES_FOLDER, sdkHomeFile, sdkModificator, false);
    }

    private static void addSources(String sourceName, File sdkHomeFile, SdkModificator sdkModificator, boolean allowCandidateAsRoot) {
        File rootFolder = new File(sdkHomeFile, sourceName);
        if (!rootFolder.exists()) return;

        File[] files = allowCandidateAsRoot ? new File[]{rootFolder} : rootFolder.listFiles();
        if (files == null) return;
        for (File file : files) {
            VirtualFile rootCandidate = LocalFileSystem.getInstance()
                    .findFileByPath(FileUtil.toSystemIndependentName(file.getAbsolutePath()));
            if (rootCandidate == null) continue;
            sdkModificator.addRoot(rootCandidate, OrderRootType.CLASSES);
        }
    }

    //
    // Documentation
    //
    @Override public @Nullable String getDefaultDocumentationUrl(@NotNull Sdk sdk) {
        return null; // https://www.ocaml.org/api/index.html for 4.13, but before?
    }

    //
    // Download
    //

    @Override public boolean supportsDownload(@NotNull SdkTypeId sdkTypeId) {
        return false;
    }

    @Override public void showDownloadUI(@NotNull SdkTypeId sdkTypeId, @NotNull SdkModel sdkModel, @NotNull JComponent parentComponent, @Nullable Sdk selectedSdk, @NotNull Consumer<SdkDownloadTask> sdkCreatedCallback) {
    }
}
