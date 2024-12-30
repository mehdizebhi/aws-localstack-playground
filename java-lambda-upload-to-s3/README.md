# AWS Services Testing with LocalStack

This repository is dedicated to testing AWS services using LocalStack. It includes implementations and scenarios for various AWS services. The current focus is on testing file upload functionality to an S3 bucket using a Java 21 Lambda function.

## Features
- **Service**: AWS Lambda (Java 21)
- **Scenario**: File upload to an S3 bucket (`my-local-bucket`)
- **Environment**: LocalStack for simulating AWS services

## Prerequisites
Ensure the following tools are installed and configured:
- [LocalStack](https://docs.localstack.cloud/)
- [AWS CLI](https://aws.amazon.com/cli/)
- [awslocal](https://github.com/localstack/awscli-local)
- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Apache Maven](https://maven.apache.org/)

## Setup Instructions

### Step 1: Start LocalStack
Start LocalStack in your environment using Docker:
```bash
docker run --rm -d -p 4566:4566 -p 4510-4559:4510-4559 localstack/localstack
```

### Step 2: Create an S3 Bucket
Create a new S3 bucket named `my-local-bucket` using the AWS CLI:
```bash
awslocal s3 mb s3://my-local-bucket
```

### Step 3: List S3 Buckets (Optional)
Verify that the bucket was created successfully:
```bash
awslocal s3 ls
```

### Step 4: Build the Project
Build the project using Maven:
```bash
mvn clean package
```

### Step 5: Create the Lambda Function
Deploy the Lambda function using `awslocal`:
```bash
awslocal lambda create-function \
    --function-name upload-handler \
    --runtime java21 \
    --handler UploadHandler \
    --role arn:aws:iam::000000000000:role/lambda-execution-role \
    --zip-file fileb://target/java-lambda-upload-to-s3-1.0-SNAPSHOT.jar
```

### Step 6: Invoke the Lambda Function
Invoke the Lambda function using a sample input file `input.json`:
```bash
awslocal lambda invoke \
    --function-name upload-handler \
    --payload file://input.json \
    output.json
```
Check the `output.json` file for the Lambda's response.

## References
- [LocalStack Documentation](https://docs.localstack.cloud/)
- [AWS Lambda with Java](https://docs.aws.amazon.com/lambda/latest/dg/lambda-java.html)
- [AWS CLI Command Reference](https://docs.aws.amazon.com/cli/latest/reference/)

