import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.net.URI;
import java.util.Base64;

public class UploadHandler implements RequestHandler<Request, Response> {

    private final S3Client s3Client = S3Client.builder()
            .httpClient(ApacheHttpClient.builder().build())
            .endpointOverride(URI.create("https://s3.localhost.localstack.cloud:4566"))
            .build();
    private static final String BUCKET_NAME = "my-local-bucket";

    @Override
    public Response handleRequest(Request request, Context context) {
        String base64Image = request.base64Image(); // Assuming input has base64 encoded image
        String fileName = request.fileName();       // File name for S3

        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();

        PutObjectResponse putResponse = s3Client.putObject(putRequest, RequestBody.fromBytes(imageBytes));

        return new Response(201, "Image uploaded successfully: " + fileName, putResponse.eTag());
    }
}

record Request(String base64Image, String fileName) {}

record Response(int status, String message, String eTag) {}