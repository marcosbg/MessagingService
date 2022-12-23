package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.pipelines.CodePipeline;
import software.amazon.awscdk.pipelines.CodePipelineSource;
import software.amazon.awscdk.pipelines.ShellStep;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.util.List;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class InfraStack extends Stack {
    public InfraStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public InfraStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        Function.Builder.create(this, "hello-world-lambda")
                .runtime(Runtime.JAVA_11)
                .handler("org.gomes.HelloWorldLambda")
                .memorySize(128)
                .timeout(Duration.seconds(20))
                .functionName("HelloWorldLambda")
                .code(Code.fromAsset("../assets/function.jar"))
                .build();

        Table.Builder.create(this, "SentMessages")
                .partitionKey(Attribute.builder()
                        .name("sent_from")
                        .type(AttributeType.STRING)
                        .build())
                .sortKey(Attribute.builder()
                        .name("sent_to")
                        .type(AttributeType.STRING)
                        .build())
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        CodePipeline.Builder.create(this, "Pipeline")
                .pipelineName("MessageServicePipeline")
                .synth(ShellStep.Builder.create("Synth")
                        .input(CodePipelineSource.gitHub("marcosbg/MessagingService","main"))
                        .commands(List.of(
                                "mvn clean install",
                                "cd infra",
                                "cdk synth"))
                        .build())
                .build();
    }
}
