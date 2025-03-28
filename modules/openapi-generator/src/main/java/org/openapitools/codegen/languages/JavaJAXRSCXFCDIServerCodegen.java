/*
 * Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
 * Copyright 2018 SmartBear Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openapitools.codegen.languages;

import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenProperty;
import org.openapitools.codegen.SupportingFile;
import org.openapitools.codegen.languages.features.BeanValidationFeatures;
import org.openapitools.codegen.model.ModelMap;
import org.openapitools.codegen.model.OperationsMap;

import java.io.File;
import java.util.List;

/**
 * Generates a Java JAXRS Server according to JAXRS 2.0 specification, assuming an
 * Apache CXF runtime and a Java EE runtime with CDI enabled.
 * Similar to the original JAXRS generator, this creates API and Service classes
 * in /src/gen/java and a sample ServiceImpl in /src/main/java. The API uses CDI
 * to get an instance of ServiceImpl that implements the Service interface.
 */
public class JavaJAXRSCXFCDIServerCodegen extends JavaJAXRSSpecServerCodegen implements BeanValidationFeatures {

    /**
     * Default constructor
     */
    public JavaJAXRSCXFCDIServerCodegen() {
        outputFolder = "generated-code/JavaJaxRS-CXF-CDI";
        artifactId = "openapi-jaxrs-cxf-cdi-server";
        sourceFolder = "src/gen/java";
        useBeanValidation = true;

        // clioOptions default redefinition need to be updated
        updateOption(CodegenConstants.SOURCE_FOLDER, this.getSourceFolder());
        updateOption(CodegenConstants.ARTIFACT_ID, this.getArtifactId());

        // Three API templates to support CDI injection
        apiTemplateFiles.put("apiService.mustache", ".java");
        apiTemplateFiles.put("apiServiceImpl.mustache", ".java");

        // Use standard types
        typeMapping.put("DateTime", "java.util.Date");
        typeMapping.put("binary", "java.io.InputStream");
        typeMapping.put("file", "java.io.InputStream");

        // Updated template directory
        embeddedTemplateDir = templateDir = JAXRS_TEMPLATE_DIRECTORY_NAME + File.separator + "cxf-cdi";

        removeOption(JavaJAXRSSpecServerCodegen.GENERATE_JSON_CREATOR);
    }

    @Override
    public String getName() {
        return "jaxrs-cxf-cdi";
    }

    @Override
    public void processOpts() {
        super.processOpts();

        supportingFiles.clear(); // Don't need extra files provided by AbstractJAX-RS & Java Codegen

        // POM
        supportingFiles.add(new SupportingFile("pom.mustache", "", "pom.xml")
                .doNotOverwrite());

        // RestApplication into src/main/java
        supportingFiles.add(new SupportingFile("RestApplication.mustache",
                (implFolder + '/' + invokerPackage).replace(".", "/"), "RestApplication.java")
                .doNotOverwrite());

        // Make CDI work in containers with implicit archive scanning disabled
        supportingFiles.add(new SupportingFile("beans.mustache",
                "src/main/webapp/WEB-INF", "beans.xml")
                .doNotOverwrite());
    }

    @Override
    public void postProcessModelProperty(CodegenModel model, CodegenProperty property) {
        super.postProcessModelProperty(model, property);

        // Reinstate JsonProperty
        model.imports.add("JsonProperty");
    }

    @Override
    public OperationsMap postProcessOperationsWithModels(OperationsMap objs, List<ModelMap> allModels) {
        objs = super.postProcessOperationsWithModels(objs, allModels);
        removeImport(objs, "java.util.List");
        return objs;
    }

    @Override
    public String getHelp() {
        return "Generates a Java JAXRS Server according to JAXRS 2.0 specification, assuming an "
                + "Apache CXF runtime and a Java EE runtime with CDI enabled.";
    }
}
