docker buildx build -t register-here .
docker run -p 8080:8080 -p 8090:8090 register-here
