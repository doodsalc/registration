# Registration API

## Reference Documentation
For further reference, please see the shared documentation:

🔗 [Google Drive Documentation](https://drive.google.com/drive/folders/1i7n88s4XE_qGfYK-K7VUcPlXZkT7BEcb)

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

Once the application is running, you can test it using POSTMAN or access the Swagger UI:

**Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

**Fake SMTP UI:** Check the welcome emails at [http://localhost:8090/](http://localhost:8090/)
