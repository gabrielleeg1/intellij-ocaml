package com.ocaml.sdk;

import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.ui.configuration.projectRoot.SdkDownload;
import com.intellij.openapi.roots.ui.configuration.projectRoot.SdkDownloadTask;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.ocaml.OCamlBundle;
import com.ocaml.icons.OCamlIcons;
import com.ocaml.sdk.doc.OCamlSdkAdditionalData;
import com.ocaml.sdk.doc.OCamlSdkAdditionalDataConfigurable;
import com.ocaml.sdk.providers.utils.InvalidHomeError;
import com.ocaml.sdk.utils.OCamlSdkHomeManager;
import com.ocaml.sdk.utils.OCamlSdkRootsManager;
import com.ocaml.sdk.utils.OCamlSdkVersionManager;
import com.ocaml.utils.adaptor.SinceIdeVersion;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * OCaml SDK
 * <ul>
 *     <li>.opam-switch/sources/*</li>
 *     <li>bin/ocaml (.exe allowed)</li>
 *     <li>bin/ocamlc (.exe allowed)</li>
 *     <li>lib/*</li>
 * </ul>
 */
public class OCamlSdkType extends SdkType implements SdkDownload {

    private static final String OCAML_SDK = "OCaml SDK";

    public OCamlSdkType() {
        super(OCAML_SDK);
    }

    public static OCamlSdkType getInstance() {
        return SdkType.EP_NAME.findExtension(OCamlSdkType.class);
    }

    public static @NotNull String getApiURL(String version) {
        version = getMajorAndMinorVersion(version);
        if (version == null) version = "4.12";
        if (OCamlSdkVersionManager.isNewerThan("4.12", version))
            return "https://ocaml.org/releases/" + version + "/api/index.html";
        return "https://ocaml.org/releases/" + version + "/htmlman/libref/index.html";
    }

    public static @NotNull String getManualURL(String version) {
        version = getMajorAndMinorVersion(version);
        if (version == null) version = "4.12";
        if (OCamlSdkVersionManager.isNewerThan("4.12", version))
            return "https://ocaml.org/releases/" + version + "/manual/index.html";
        return "https://ocaml.org/releases/" + version + "/htmlman/index.html";
    }

    //
    // Sources
    //

    public static void addSources(@NotNull File sdkHomeFile, @NotNull SdkModificator sdkModificator) {
        List<String> sources = OCamlSdkRootsManager.getSourcesFolders(sdkHomeFile.getPath());
        for (String source : sources) {
            addSources(source, sdkHomeFile, sdkModificator);
        }
    }

    private static void addSources(String sourceName, File sdkHomeFile, SdkModificator sdkModificator) {
        File rootFolder = new File(sdkHomeFile, sourceName);
        if (!rootFolder.exists()) return;

        File[] files = rootFolder.listFiles();
        if (files == null) return;
        for (File file : files) {
            VirtualFile rootCandidate = LocalFileSystem.getInstance()
                    .findFileByPath(FileUtil.toSystemIndependentName(file.getAbsolutePath()));
            if (rootCandidate == null) continue;
            sdkModificator.addRoot(rootCandidate, OrderRootType.CLASSES);
        }
    }

    //
    // Name + Icon
    //

    @NotNull @Override public String getPresentableName() {
        return "OCaml";
    }

    @NotNull @Override public Icon getIcon() {
        return OCamlIcons.Nodes.OCAML_SDK;
    }

    //
    // Home path
    //

    @Override public @NotNull Collection<String> suggestHomePaths() {
        return OCamlSdkHomeManager.suggestHomePaths();
    }

    @Nullable @Override public String suggestHomePath() {
        return OCamlSdkHomeManager.defaultOCamlLocation();
    }

    //
    // suggestSdkName, getVersionString
    //

    @NotNull
    @Override public final String suggestSdkName(String currentSdkName, @NotNull String sdkHome) {
        return "OCaml-" + getVersionString(sdkHome);
    }

    @NotNull @Override public String getVersionString(@NotNull String sdkHome) {
        return OCamlSdkVersionManager.parse(sdkHome);
    }

    //
    // Valid
    //

    @Override public boolean isValidSdkHome(@NotNull String sdkHome) {
        return OCamlSdkHomeManager.isValid(sdkHome);
    }

    @Override public String getInvalidHomeMessage(@NotNull String path) {
        InvalidHomeError kind = OCamlSdkHomeManager.invalidHomeErrorMessage(Path.of(path));
        if (kind == null)
            return OCamlBundle.message("sdk.home.error.no.provider");
        switch (kind) {
            case INVALID_HOME_PATH: return OCamlBundle.message("sdk.home.error.invalid");
            case NO_TOP_LEVEL: return OCamlBundle.message("sdk.home.error.no.top.level");
            case NO_COMPILER: return OCamlBundle.message("sdk.home.error.no.compiler");
            case NO_SOURCES: return OCamlBundle.message("sdk.home.error.no.sources");
            case NONE:
            case GENERIC:
            default:
                return super.getInvalidHomeMessage(path);
        }
    }

    //
    // Data
    //

    @Override public boolean isRootTypeApplicable(@NotNull OrderRootType type) {
        return type == OrderRootType.CLASSES;
    }

    @Override @Nullable
    public AdditionalDataConfigurable createAdditionalDataConfigurable(@NotNull SdkModel sdkModel,
                                                                       @NotNull SdkModificator sdkModificator) {
        return new OCamlSdkAdditionalDataConfigurable();
    }

    @Override
    public @Nullable SdkAdditionalData loadAdditionalData(@NotNull Sdk currentSdk, @NotNull Element additional) {
        OCamlSdkAdditionalData sdkAdditionalData = new OCamlSdkAdditionalData();
        sdkAdditionalData.ocamlManualURL = additional.getAttributeValue("ocamlManualURL");
        sdkAdditionalData.ocamlApiURL = additional.getAttributeValue("ocamlApiURL");
        if (sdkAdditionalData.shouldFillWithDefaultValues()) {
            sdkAdditionalData.ocamlApiURL = getDefaultAPIUrl(currentSdk);
            sdkAdditionalData.ocamlManualURL = getDefaultDocumentationUrl(currentSdk);
        }
        return sdkAdditionalData;
    }

    @Override public void saveAdditionalData(@NotNull SdkAdditionalData additionalData,
                                             @NotNull Element additional) {
        OCamlSdkAdditionalData sdkAdditionalData = (OCamlSdkAdditionalData) additionalData;
        additional.setAttribute("ocamlManualURL", sdkAdditionalData.ocamlManualURL);
        additional.setAttribute("ocamlApiURL", sdkAdditionalData.ocamlApiURL);
    }

    //
    // Setup
    //

    @Override public void setupSdkPaths(@NotNull Sdk sdk) {
        String homePath = sdk.getHomePath();
        assert homePath != null : sdk;
        SdkModificator sdkModificator = sdk.getSdkModificator();
        sdkModificator.removeRoots(OrderRootType.CLASSES);
        addSources(new File(homePath), sdkModificator);
        // 0.0.6 - added by default
        String url = getDefaultDocumentationUrl(sdk);
        if (url != null) sdkModificator.addRoot(url, OrderRootType.DOCUMENTATION);
        url = getDefaultAPIUrl(sdk);
        if (url != null) sdkModificator.addRoot(url, OrderRootType.DOCUMENTATION);
        sdkModificator.commitChanges();
    }

    //
    // Documentation
    //

    @Override public @Nullable String getDefaultDocumentationUrl(@NotNull Sdk sdk) {
        return getManualURL(sdk.getVersionString());
    }

    public @Nullable String getDefaultAPIUrl(@NotNull Sdk sdk) {
        return getApiURL(sdk.getVersionString());
    }

    public static @Nullable String getMajorAndMinorVersion(@NotNull String version) {
        if (!OCamlSdkVersionManager.isValid(version)) return null;
        // if we got two ".", then we trunc the patch number
        int last = version.lastIndexOf('.');
        if (last != version.indexOf('.'))
            version = version.substring(0, last);
        return version;
    }

    //
    // Download
    //

    @Override public boolean supportsDownload(@NotNull SdkTypeId sdkTypeId) {
        return false;
    }

    @Override public void showDownloadUI(@NotNull SdkTypeId sdkTypeId, @NotNull SdkModel sdkModel,
                                         @NotNull JComponent parentComponent, @Nullable Sdk selectedSdk,
                                         @NotNull Consumer<? super SdkDownloadTask> sdkCreatedCallback) {
    }

    //
    // WSL
    //

    @SinceIdeVersion(release = "213")
    @SuppressWarnings("unused")
    public boolean allowWslSdkForLocalProject() {
        return true;
    }
}
