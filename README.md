## HR System With Spring Microservices

# Introduction 
Welcome to HR System Repository this repo contains services for HR System which developed with Spring java.


## Initial Configuration
1.	Apache Maven (http://maven.apache.org)  All the code have been compiled with Java version 11.
2.	Git Client (http://git-scm.com)
3.  Docker(https://www.docker.com/products/docker-desktop)


## How To Use

To clone and run this application, you'll need [Git](https://git-scm.com), [Maven](https://maven.apache.org/), [Java 11](https://www.oracle.com/java/technologies/downloads/#java11). From your command line:

```bash
# Clone this repository
$ git clone https://github.com/AbdulrahmanQerawani/hr-system

# To build the code as a docker image, open a command-line
# window and execute the following command:
$ mvn clean package dockerfile:build

# Now we are going to use docker-compose to start the actual image.  To start the docker image, stay in the directory containing  your source code and  Run the following command: 
$ docker-compose -f docker/docker-compose.yml up
```

# The build command

Will execute the [Spotify dockerfile plugin](https://github.com/spotify/dockerfile-maven) defined in the pom.xml file.  

 Running the above command at the root of the project directory will build all of the projects.  If everything builds successfully you should see a message indicating that the build was successful.

# The Run command

This command will run our services using the docker-compose.yml file located in the /docker directory. 

If everything starts correctly you should see a bunch of Spring Boot information fly by on standard out.  At this point all of the services will be running.

# Database
You can find the database script as well in the docker directory.

## Contact

I'd like you to send me an email on <abdqarawani@gmail.com> about anything you'd want to say about this software.

### Contributing
Feel free to file an issue if it doesn't work for your code sample. Thanks.


<br/><br/>

---

<br/>

**Abdulrahman**

