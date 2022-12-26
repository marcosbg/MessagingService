package com.myorg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;

import java.util.Map;

public class LambdaStack extends Stack {
    public LambdaStack(@NotNull Construct scope, @NotNull String id, @NotNull String stageName, @Nullable StackProps props) {
        Function.Builder.create(this, "hello-world-lambda")
                .runtime(Runtime.JAVA_11)
                .handler("org.gomes.HelloWorldLambda")
                .memorySize(128)
                .timeout(Duration.seconds(20))
                .functionName("HelloWorldLambda")
                .code(Code.fromAsset("../assets/function.jar"))
                .environment(Map.of("stageName", stageName))
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
    }
}
