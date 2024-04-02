# Use the official openjdk image as the base image
FROM openjdk:8

# Set the working directory in the Docker container
WORKDIR /src

# Copy the HelloWorld.java file into the Docker container
COPY /src/BTMain.java .
COPY /src/Login.java .
COPY /src/Status.java .
COPY /src/Util.java .
COPY input.txt .

# Compile the HelloWorld.java file
RUN javac Login.java
RUN javac Status.java
RUN javac Util.java
RUN javac BTMain.java

# Specify the command to run when the Docker container starts
ENTRYPOINT ["java", "BTMain"]