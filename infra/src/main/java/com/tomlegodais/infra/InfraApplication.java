package com.tomlegodais.infra;

import com.tomlegodais.infra.stacks.AppStack;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class InfraApplication {

    public static void main(String[] args) {
        var app = new App();
        var environment = Environment.builder()
                .account(System.getenv("CDK_DEFAULT_ACCOUNT"))
                .region(System.getenv("CDK_DEFAULT_REGION"))
                .build();

        var projectName = (String) app.getNode().getContext("project-name");
        new AppStack(app, projectName, StackProps.builder().env(environment).build());

        app.synth();
    }
}
