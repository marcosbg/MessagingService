package org.gomes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Hello world!
 *
 */
public class HelloWorldLambda implements RequestHandler<String, String>
{

    @Override
    public String handleRequest(String name, Context context) {
        return "Hello there " + name;
    }
}
