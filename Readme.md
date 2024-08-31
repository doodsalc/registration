# Registration API

## Reference Documentation
For further reference, please see the shared documentation:

🔗 [Google Drive Documentation](https://docs.google.com/document/d/1dNHAZaJOHZ02p2WRI67ORJg2nmLMd2g-tIHKhMxHHAs)

## Starting the Application

To start the application:

1. **Clone the Repository**  
   Clone this repository locally, using an IDE like IntelliJ.

2. **Start Docker Desktop**  
   Ensure Docker Desktop is running on your machine. For details, visit [docker.com](https://www.docker.com).

3. **Run the Application**  
   - **On MacOS:**  
     Open a terminal and navigate to the root folder of the registration application, then run:
     ```sh
     ./docker_run.sh
     ```
   - **On Windows:**  
     ```sh
     docker_run.bat
     ```

## Testing and Validation

When the application is started, you can use POSTMAN or the Swagger UI to test
http://localhost:8080/swagger-ui/index.html

You can check the fake smtp UI for the welcome emails
http://localhost:8090/

**Fake SMTP UI:** Check the welcome emails at [http://localhost:8090/](http://localhost:8090/)
