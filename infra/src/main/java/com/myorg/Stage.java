package com.myorg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StageProps;
import software.constructs.Construct;

public class Stage extends software.amazon.awscdk.Stage {
    public Stage(@NotNull Construct scope, @NotNull String id, @Nullable StageProps props) {
        super(scope, id, props);
        Stack lambdaStack = new LambdaStack(scope, "LambdaStack", "dev", null);
    }
}
